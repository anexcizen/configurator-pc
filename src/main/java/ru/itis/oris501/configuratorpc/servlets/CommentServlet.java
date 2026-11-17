package ru.itis.oris501.configuratorpc.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.oris501.configuratorpc.model.User;
import ru.itis.oris501.configuratorpc.service.CommentService;

import java.io.IOException;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {
    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        commentService = (CommentService) context.getAttribute("commentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        String buildIdStr = req.getParameter("buildId");
        String text = req.getParameter("commentText");

        try {
            int buildId = Integer.parseInt(buildIdStr);
            commentService.addComment(buildId, user.getId(), text);
            resp.sendRedirect(req.getContextPath() + "/builds?id=" + buildId);
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/builds?id=" + buildIdStr);
        }
    }
}
