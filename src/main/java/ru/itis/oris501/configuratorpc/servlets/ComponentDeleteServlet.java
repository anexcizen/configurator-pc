package ru.itis.oris501.configuratorpc.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.oris501.configuratorpc.service.ComponentService;

import java.io.IOException;

@WebServlet("/components/delete")
public class ComponentDeleteServlet extends HttpServlet {
    private ComponentService componentService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        componentService = (ComponentService) context.getAttribute("componentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        componentService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/components");
    }

}
