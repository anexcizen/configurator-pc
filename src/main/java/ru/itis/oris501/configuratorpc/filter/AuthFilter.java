package ru.itis.oris501.configuratorpc.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        boolean open = path.equals("/login")
                || path.equals("/register")
                || path.startsWith("/js")
                || path.startsWith("/css")
                || path.startsWith("/img")
                || path.startsWith("/template");

        if (open) {
            filterChain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            filterChain.doFilter(req, resp);
        } else {
            LOGGER.warning("Неавторизованный доступ к: " + path + ". Перенаправление на /login");

            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
