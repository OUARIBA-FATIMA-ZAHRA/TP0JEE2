<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StockFlow - Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/navigation.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h1 class="display-5 mb-4">
                    <i class="fas fa-chart-line"></i> Dashboard
                </h1>
                <hr>
            </div>
        </div>

        <div class="row mt-4">
            <div class="col-md-6 col-lg-3 mb-4">
                <div class="card bg-primary text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="card-title">Total Categories</h6>
                                <h2 class="display-6">${totalCategories}</h2>
                            </div>
                            <i class="fas fa-tags fa-3x opacity-50"></i>
                        </div>
                        <div class="mt-3">
                            <small>Active: ${activeCategories}</small>
                        </div>
                    </div>
                    <div class="card-footer bg-transparent border-0">
                        <a href="${pageContext.request.contextPath}/categories" class="text-white text-decoration-none">
                            Manage Categories <i class="fas fa-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-3 mb-4">
                <div class="card bg-success text-white shadow">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h6 class="card-title">Total Vendors</h6>
                                <h2 class="display-6">${totalVendors}</h2>
                            </div>
                            <i class="fas fa-truck fa-3x opacity-50"></i>
                        </div>
                        <div class="mt-3">
                            <small>Active: ${activeVendors}</small>
                        </div>
                    </div>
                    <div class="card-footer bg-transparent border-0">
                        <a href="${pageContext.request.contextPath}/vendors" class="text-white text-decoration-none">
                            Manage Vendors <i class="fas fa-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-4">
            <div class="col-12">
                <div class="card shadow">
                    <div class="card-header bg-dark text-white">
                        <h5 class="mb-0"><i class="fas fa-info-circle"></i> Quick Actions</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/categories?action=new" class="btn btn-outline-primary w-100 mb-2">
                                    <i class="fas fa-plus"></i> Add New Category
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/vendors?action=add" class="btn btn-outline-success w-100 mb-2">
                                    <i class="fas fa-plus"></i> Add New Vendor
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/fragments/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>