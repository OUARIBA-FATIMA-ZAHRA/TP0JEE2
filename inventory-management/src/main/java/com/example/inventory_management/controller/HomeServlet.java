package com.example.inventory_management.controller;

import com.example.inventory_management.repository.CategoryRepositoryImpl;
import com.example.inventory_management.repository.VendorRepositoryImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/", "/home", "/dashboard"})
public class HomeServlet extends HttpServlet {

    private CategoryRepositoryImpl categoryRepo;
    private VendorRepositoryImpl vendorRepo;

    @Override
    public void init() {
        categoryRepo = new CategoryRepositoryImpl();
        vendorRepo = new VendorRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long categoryCount = categoryRepo.count();
        long vendorCount = vendorRepo.count();
        long activeCategories = categoryRepo.fetchActiveOnly().size();
        long activeVendors = vendorRepo.fetchActiveVendors().size();

        req.setAttribute("totalCategories", categoryCount);
        req.setAttribute("totalVendors", vendorCount);
        req.setAttribute("activeCategories", activeCategories);
        req.setAttribute("activeVendors", activeVendors);

        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
                .forward(req, resp);
    }
}