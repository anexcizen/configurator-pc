package ru.itis.oris501.configuratorpc.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.oris501.configuratorpc.dao.*;
import ru.itis.oris501.configuratorpc.dao.impl.*;
import ru.itis.oris501.configuratorpc.service.*;

import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Инициализация приложения");

        ServletContext ctx = sce.getServletContext();

        UserDao userDao = new UserDaoJdbc();
        CategoryDao categoryDao = new CategoryDaoJdbc();
        ComponentDao componentDao = new ComponentDaoJdbc();
        BuildDao buildDao = new BuildDaoJdbc();
        CommentDao commentDao = new CommentDaoJdbc();

        UserService userService = new UserService(userDao);
        CategoryService categoryService = new CategoryService(categoryDao);
        ComponentService componentService = new ComponentService(componentDao, categoryDao);
        BuildService buildService = new BuildService(buildDao, componentService, categoryService);
        CommentService commentService = new CommentService(commentDao);

        ctx.setAttribute("userService", userService);
        ctx.setAttribute("categoryService", categoryService);
        ctx.setAttribute("componentService", componentService);
        ctx.setAttribute("buildService", buildService);
        ctx.setAttribute("commentService", commentService);

        LOGGER.info("Сервисы успешно инициализированы и добавлены в ServletContext");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Приложение остановлено");
    }
}
