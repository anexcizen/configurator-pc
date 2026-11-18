package ru.itis.oris501.configuratorpc.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.oris501.configuratorpc.model.User;
import ru.itis.oris501.configuratorpc.service.UserService;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    private UserService userService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        LOGGER.info("Попытка входа пользователя: " + username);

        Optional<User> u = userService.login(username, password);
        if (!u.isEmpty()) {
            User user = u.get();
            req.getSession().setAttribute("currentUser", user);

            LOGGER.info("Пользователь " + username + " успешно вошёл в систему");

            resp.sendRedirect(req.getContextPath() + "/components");
        } else {
            LOGGER.warning("Неудачная попытка входа пользователя: " + username);

            req.setAttribute("error", "Неверный логин или пароль");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
        }
    }
}
