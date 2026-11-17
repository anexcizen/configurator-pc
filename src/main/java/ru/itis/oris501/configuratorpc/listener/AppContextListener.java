package ru.itis.oris501.configuratorpc.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.oris501.configuratorpc.service.BuildService;
import ru.itis.oris501.configuratorpc.service.CategoryService;
import ru.itis.oris501.configuratorpc.service.ComponentService;
import ru.itis.oris501.configuratorpc.service.UserService;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("userService", new UserService());
        ctx.setAttribute("componentService", new ComponentService());
        ctx.setAttribute("categoryService", new CategoryService());
        ctx.setAttribute("buildService", new BuildService((ComponentService) ctx.getAttribute("componentService"), (CategoryService) ctx.getAttribute("categoryService")));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }
}
