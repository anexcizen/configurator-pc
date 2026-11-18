package ru.itis.oris501.configuratorpc.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.oris501.configuratorpc.dao.*;
import ru.itis.oris501.configuratorpc.dao.impl.*;
import ru.itis.oris501.configuratorpc.service.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }
}
