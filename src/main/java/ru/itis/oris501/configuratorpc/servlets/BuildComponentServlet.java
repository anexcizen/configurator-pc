package ru.itis.oris501.configuratorpc.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.oris501.configuratorpc.model.User;
import ru.itis.oris501.configuratorpc.service.BuildService;

import java.io.IOException;

@WebServlet("/builds/component")
public class BuildComponentServlet extends HttpServlet {
    private BuildService buildService;

    @Override
    public void init() {
        buildService = (BuildService) getServletContext().getAttribute("buildService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        String action = req.getParameter("action");
        String buildIdStr = req.getParameter("buildId");
        String componentIdStr = req.getParameter("componentId");

        try {
            int buildId = Integer.parseInt(buildIdStr);
            int componentId = Integer.parseInt(componentIdStr);

            if ("add".equals(action)) {
                buildService.addComponent(buildId, componentId, user.getId());
            } else if ("remove".equals(action)) {
                buildService.removeComponent(buildId, componentId, user.getId());
            } else {
                resp.sendError(400, "Неизвестное действие");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/builds?id=" + buildId);
        } catch (SecurityException e) {
            resp.sendError(403, "Доступ запрещён");
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/builds?id=" + buildIdStr);
        }
    }
}
