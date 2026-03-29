package com.example.inventory_management.controller;

import com.example.inventory_management.entity.ItemCategory;
import com.example.inventory_management.repository.CategoryRepositoryImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

    private CategoryRepositoryImpl categoryRepo;

    @Override
    public void init() {
        categoryRepo = new CategoryRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            showForm(req, resp);
        } else if ("edit".equals(action)) {
            editForm(req, resp);
        } else if ("delete".equals(action)) {
            deleteCategory(req, resp);
        } else if ("search".equals(action)) {
            searchCategories(req, resp);
        } else {
            listCategories(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createCategory(req, resp);
        } else if ("update".equals(action)) {
            updateCategory(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/categories");
        }
    }

    private void listCategories(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<ItemCategory> categories = categoryRepo.fetchAll();
        req.setAttribute("categoryList", categories);
        req.getRequestDispatcher("/WEB-INF/views/categories/category-list.jsp")
                .forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("category", new ItemCategory());
        req.getRequestDispatcher("/WEB-INF/views/categories/category-form.jsp")
                .forward(req, resp);
    }

    private void editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = parseId(req.getParameter("id"));
        categoryRepo.fetchById(id).ifPresent(category ->
                req.setAttribute("category", category));
        req.getRequestDispatcher("/WEB-INF/views/categories/category-form.jsp")
                .forward(req, resp);
    }

    private void createCategory(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        ItemCategory category = extractCategoryFromRequest(req);

        if (categoryRepo.findByReference(category.getCategoryRef()).isPresent()) {
            req.setAttribute("error", "Category reference already exists!");
            req.setAttribute("category", category);
            req.getRequestDispatcher("/WEB-INF/views/categories/category-form.jsp")
                    .forward(req, resp);
            return;
        }

        categoryRepo.store(category);
        resp.sendRedirect(req.getContextPath() + "/categories");
    }

    private void updateCategory(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Long id = parseId(req.getParameter("id"));

        if (!categoryRepo.existsById(id)) {
            req.setAttribute("error", "Category not found!");
            listCategories(req, resp);
            return;
        }

        ItemCategory category = extractCategoryFromRequest(req);
        category.setRecordId(id);
        categoryRepo.store(category);
        resp.sendRedirect(req.getContextPath() + "/categories");
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = parseId(req.getParameter("id"));
        categoryRepo.eraseById(id);
        resp.sendRedirect(req.getContextPath() + "/categories");
    }

    private void searchCategories(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<ItemCategory> results = categoryRepo.searchByKeyword(keyword);
        req.setAttribute("categoryList", results);
        req.setAttribute("searchTerm", keyword);
        req.getRequestDispatcher("/WEB-INF/views/categories/category-list.jsp")
                .forward(req, resp);
    }

    private ItemCategory extractCategoryFromRequest(HttpServletRequest req) {
        ItemCategory category = new ItemCategory();
        category.setCategoryRef(req.getParameter("categoryRef"));
        category.setCategoryTitle(req.getParameter("categoryTitle"));
        category.setDescription(req.getParameter("description"));
        category.setEnabled("on".equals(req.getParameter("enabled")));

        String priority = req.getParameter("sortPriority");
        if (priority != null && !priority.isEmpty()) {
            category.setSortPriority(Integer.parseInt(priority));
        }
        return category;
    }

    private Long parseId(String idParam) {
        if (idParam == null || idParam.isEmpty()) return null;
        try {
            return Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}