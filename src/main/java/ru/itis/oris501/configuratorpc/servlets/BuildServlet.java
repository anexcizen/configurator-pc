package ru.itis.oris501.configuratorpc.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.oris501.configuratorpc.model.Build;
import ru.itis.oris501.configuratorpc.model.Category;
import ru.itis.oris501.configuratorpc.model.Component;
import ru.itis.oris501.configuratorpc.model.User;
import ru.itis.oris501.configuratorpc.service.BuildService;
import ru.itis.oris501.configuratorpc.service.CategoryService;
import ru.itis.oris501.configuratorpc.service.ComponentService;

import java.io.IOException;
import java.util.*;

@WebServlet("/builds")
public class BuildServlet extends HttpServlet {
    private BuildService buildService;
    private ComponentService componentService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        buildService = (BuildService) context.getAttribute("buildService");
        componentService = (ComponentService) context.getAttribute("componentService");
        categoryService = (CategoryService) context.getAttribute("categoryService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        String id = req.getParameter("id");
        try {
            if (id != null) {
                int buildId;
                try {
                    buildId = Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath() + "/builds");
                    return;
                }

                try {
                    Build build = buildService.getBuildForUser(buildId, user);
                    req.setAttribute("build", build);
                    List<Component> buildComps = buildService.getComponents(buildId);
                    req.setAttribute("buildComponents", buildComps);
                    req.setAttribute("allComponents", componentService.findAll());

                    Map<String, List<Component>> grouped = new LinkedHashMap<>();
                    for (Category cat : categoryService.findAll()) {
                        List<Component> compsInCat = new ArrayList<>();
                        for (Component comp : buildComps) {
                            if (comp.getCategoryId() == cat.getId()) compsInCat.add(comp);
                        }
                        grouped.put(cat.getName(), compsInCat);
                    }
                    req.setAttribute("groupedComponents", grouped);
                    req.getRequestDispatcher("/WEB-INF/views/build-view.jsp").forward(req, resp);
                } catch (SecurityException e) {
                    resp.sendError(403, "Доступ запрещён");
                }

            } else {
                String pubParam = req.getParameter("public");
                if ("true".equals(pubParam)) {
                    req.setAttribute("builds", buildService.getPublicBuilds());
                    req.setAttribute("isPublicView", true);
                } else {
                    req.setAttribute("builds", buildService.getUserBuilds(user.getId()));
                }
                req.getRequestDispatcher("/WEB-INF/views/builds.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute("error", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/builds");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User user = (User) req.getSession().getAttribute("currentUser");

        if ("create".equals(action)) {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            boolean isPublic = "true".equals(req.getParameter("isPublic"));
            try {
                buildService.createBuild(user.getId(), title, description, isPublic);
                resp.sendRedirect(req.getContextPath() + "/builds");
            } catch (IllegalArgumentException e) {
                req.getSession().setAttribute("error", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/builds");
            }
        } else if ("addComponent".equals(action)) {
            try {
                int buildId = Integer.parseInt(req.getParameter("buildId"));
                int componentId = Integer.parseInt(req.getParameter("componentId"));

                buildService.addComponent(buildId, componentId);
                resp.sendRedirect(req.getContextPath() + "/builds?id=" + buildId);
            } catch (IllegalArgumentException e) {
                req.getSession().setAttribute("error", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/builds?id=" + req.getParameter("buildId"));
            }
        } else if ("delete".equals(action)) {
            try {
                int buildId = Integer.parseInt(req.getParameter("id"));
                buildService.delete(buildId, user.getId());
                resp.sendRedirect(req.getContextPath() + "/builds");
            } catch (IllegalArgumentException e) {
                req.getSession().setAttribute("error", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/builds");
            }
        } else if ("removeComponent".equals(action)) {
            int buildId = Integer.parseInt(req.getParameter("buildId"));
            int componentId = Integer.parseInt(req.getParameter("componentId"));
            buildService.removeComponent(buildId, componentId);
            resp.sendRedirect(req.getContextPath() + "/builds?id=" + buildId);
        } else if ("update".equals(action)) {
            try {
                int buildId = Integer.parseInt(req.getParameter("id"));
                String title = req.getParameter("title");
                String desc = req.getParameter("description");
                boolean isPublic = "true".equals(req.getParameter("isPublic"));
                buildService.updateBuild(buildId, user.getId(), title, desc, isPublic);
                resp.sendRedirect(req.getContextPath() + "/builds?id=" + buildId);
            } catch (IllegalArgumentException e) {
                req.getSession().setAttribute("error", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/builds?id=" + req.getParameter("id"));
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/builds");
        }
    }
}
