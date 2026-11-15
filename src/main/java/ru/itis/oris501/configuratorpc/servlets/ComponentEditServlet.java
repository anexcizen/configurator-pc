package ru.itis.oris501.configuratorpc.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.oris501.configuratorpc.model.Component;
import ru.itis.oris501.configuratorpc.service.CategoryService;
import ru.itis.oris501.configuratorpc.service.ComponentService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet("/components/edit")
public class ComponentEditServlet extends HttpServlet {
    private ComponentService componentService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        componentService = (ComponentService) context.getAttribute("componentService");
        categoryService = (CategoryService) context.getAttribute("categoryService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isBlank()) {
            try {
                int id = Integer.parseInt(idStr);
                Optional<Component> component = componentService.findById(id);
                if (!component.isEmpty()) {
                    req.setAttribute("component", component.get());
                } else {
                    resp.sendRedirect(req.getContextPath() + "/components");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/components");
            }
        }
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/component-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Component component = null;
        try {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String categoryIdStr= req.getParameter("categoryId");
            String priceStr = req.getParameter("price");
            String socket = req.getParameter("socket");
            String ramType = req.getParameter("ramType");
            String powerWattsStr = req.getParameter("powerWatts");
            try {
                BigDecimal price = new BigDecimal(priceStr);
                int categoryId = Integer.parseInt(categoryIdStr);
                Integer powerWatts = (powerWattsStr != null && !powerWattsStr.isBlank())
                        ? Integer.parseInt(powerWattsStr) : null;
                component = new Component(name, categoryId, price, null, socket, ramType, powerWatts);
            } catch (NumberFormatException e) {
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/component-edit.jsp").forward(req, resp);
                return;
            }
            if (idStr == null || idStr.isBlank()) {
                componentService.create(component);
            } else {
                component.setId(Integer.parseInt(idStr));
                componentService.update(component);
            }
            resp.sendRedirect(req.getContextPath() + "/components");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/component-edit.jsp").forward(req, resp);
        }

        req.setAttribute("component", component);
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/component-edit.jsp").forward(req, resp);
    }
}
