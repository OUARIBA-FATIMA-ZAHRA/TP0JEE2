package com.example.inventory_management.controller;

import com.example.inventory_management.entity.Vendor;
import com.example.inventory_management.repository.VendorRepositoryImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vendors")
public class VendorServlet extends HttpServlet {

    private VendorRepositoryImpl vendorRepo;

    @Override
    public void init() {
        vendorRepo = new VendorRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            showCreateForm(req, resp);
        } else if ("modify".equals(action)) {
            showEditForm(req, resp);
        } else if ("remove".equals(action)) {
            deleteVendor(req, resp);
        } else if ("lookup".equals(action)) {
            searchVendors(req, resp);
        } else if ("filterByCity".equals(action)) {
            filterByCity(req, resp);
        } else {
            listVendors(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            createVendor(req, resp);
        } else if ("refresh".equals(action)) {
            updateVendor(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/vendors");
        }
    }

    private void listVendors(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Vendor> vendors = vendorRepo.fetchAll();
        req.setAttribute("vendorList", vendors);
        req.getRequestDispatcher("/WEB-INF/views/vendors/vendor-list.jsp")
                .forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("vendor", new Vendor());
        req.getRequestDispatcher("/WEB-INF/views/vendors/vendor-form.jsp")
                .forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = parseId(req.getParameter("id"));
        vendorRepo.fetchById(id).ifPresent(vendor ->
                req.setAttribute("vendor", vendor));
        req.getRequestDispatcher("/WEB-INF/views/vendors/vendor-form.jsp")
                .forward(req, resp);
    }

    private void createVendor(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Vendor vendor = extractVendorFromRequest(req);

        if (vendor.getVatNumber() != null && !vendor.getVatNumber().isEmpty()) {
            if (vendorRepo.findByVatNumber(vendor.getVatNumber()).isPresent()) {
                req.setAttribute("error", "VAT number already registered!");
                req.setAttribute("vendor", vendor);
                req.getRequestDispatcher("/WEB-INF/views/vendors/vendor-form.jsp")
                        .forward(req, resp);
                return;
            }
        }

        if (vendor.getEmail() != null && !vendor.getEmail().isEmpty()) {
            if (!vendorRepo.isEmailUnique(vendor.getEmail(), null)) {
                req.setAttribute("error", "Email already used by another vendor!");
                req.setAttribute("vendor", vendor);
                req.getRequestDispatcher("/WEB-INF/views/vendors/vendor-form.jsp")
                        .forward(req, resp);
                return;
            }
        }

        vendorRepo.store(vendor);
        resp.sendRedirect(req.getContextPath() + "/vendors");
    }

    private void updateVendor(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Long id = parseId(req.getParameter("vendorId"));

        if (!vendorRepo.existsById(id)) {
            req.setAttribute("error", "Vendor record not found!");
            listVendors(req, resp);
            return;
        }

        Vendor vendor = extractVendorFromRequest(req);
        vendor.setVendorId(id);
        vendorRepo.store(vendor);
        resp.sendRedirect(req.getContextPath() + "/vendors");
    }

    private void deleteVendor(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = parseId(req.getParameter("id"));
        vendorRepo.eraseById(id);
        resp.sendRedirect(req.getContextPath() + "/vendors");
    }

    private void searchVendors(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Vendor> results = vendorRepo.searchVendors(keyword);
        req.setAttribute("vendorList", results);
        req.setAttribute("searchTerm", keyword);
        req.getRequestDispatcher("/WEB-INF/views/vendors/vendor-list.jsp")
                .forward(req, resp);
    }

    private void filterByCity(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String city = req.getParameter("city");
        List<Vendor> results = vendorRepo.findByCity(city);
        req.setAttribute("vendorList", results);
        req.setAttribute("cityFilter", city);
        req.getRequestDispatcher("/WEB-INF/views/vendors/vendor-list.jsp")
                .forward(req, resp);
    }

    private Vendor extractVendorFromRequest(HttpServletRequest req) {
        Vendor vendor = new Vendor();
        vendor.setCompanyName(req.getParameter("companyName"));
        vendor.setVatNumber(req.getParameter("vatNumber"));
        vendor.setEmail(req.getParameter("email"));
        vendor.setPhoneNumber(req.getParameter("phoneNumber"));
        vendor.setStreetAddress(req.getParameter("streetAddress"));
        vendor.setCity(req.getParameter("city"));
        vendor.setPostalCode(req.getParameter("postalCode"));
        vendor.setCountry(req.getParameter("country"));
        vendor.setIsActive("on".equals(req.getParameter("isActive")));
        vendor.setPaymentTerms(req.getParameter("paymentTerms"));

        String creditLimit = req.getParameter("maxCredit");
        if (creditLimit != null && !creditLimit.isEmpty()) {
            vendor.setMaxCredit(Double.parseDouble(creditLimit));
        }

        vendor.setWebsiteUrl(req.getParameter("websiteUrl"));
        vendor.setContactPerson(req.getParameter("contactPerson"));
        return vendor;
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