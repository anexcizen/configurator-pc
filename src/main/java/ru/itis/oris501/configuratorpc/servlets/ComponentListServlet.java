package ru.itis.oris501.configuratorpc.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.oris501.configuratorpc.model.Category;
import ru.itis.oris501.configuratorpc.model.Component;
import ru.itis.oris501.configuratorpc.service.CategoryService;
import ru.itis.oris501.configuratorpc.service.ComponentService;

import java.io.IOException;
import java.util.List;

@WebServlet("/components")
public class ComponentListServlet extends HttpServlet {

    private ComponentService componentService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        ServletContext ctx = getServletContext();
        componentService = (ComponentService) ctx.getAttribute("componentService");
        categoryService = (CategoryService) ctx.getAttribute("categoryService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryIdParam = req.getParameter("categoryId");

        List<Component> components;
        if (categoryIdParam != null && !categoryIdParam.isBlank()) {
            try {
                components = componentService.findByCategory(Integer.parseInt(categoryIdParam));
            } catch (NumberFormatException e) {
                components = componentService.findAll();
            }
        } else {
            components = componentService.findAll();
        }

        List<Category> categories = categoryService.findAll();

        req.setAttribute("categories", categories);
        req.setAttribute("components", components);
        req.setAttribute("selectedCategoryId", categoryIdParam);

        req.getRequestDispatcher("/WEB-INF/views/components.jsp").forward(req,resp);
    }
}
