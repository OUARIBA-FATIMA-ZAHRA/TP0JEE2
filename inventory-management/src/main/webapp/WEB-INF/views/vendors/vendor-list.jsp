<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StockFlow - Vendor Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/navigation.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <div class="d-flex justify-content-between align-items-center">
                    <h1><i class="fas fa-truck"></i> Vendor Directory</h1>
                    <a href="${pageContext.request.contextPath}/vendors?action=add" class="btn btn-success">
                        <i class="fas fa-plus"></i> New Vendor
                    </a>
                </div>
                <hr>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-4">
                <form action="${pageContext.request.contextPath}/vendors" method="get" class="d-flex">
                    <input type="hidden" name="action" value="lookup">
                    <input type="text" class="form-control me-2" name="keyword"
                           placeholder="Search vendors..." value="${searchTerm}">
                    <button type="submit" class="btn btn-outline-success">
                        <i class="fas fa-search"></i>
                    </button>
                </form>
            </div>
            <div class="col-md-3">
                <form action="${pageContext.request.contextPath}/vendors" method="get" class="d-flex">
                    <input type="hidden" name="action" value="filterByCity">
                    <input type="text" class="form-control me-2" name="city"
                           placeholder="Filter by city..." value="${cityFilter}">
                    <button type="submit" class="btn btn-outline-info">
                        <i class="fas fa-city"></i>
                    </button>
                </form>
            </div>
            <div class="col-md-5 text-end">
                <a href="${pageContext.request.contextPath}/vendors" class="btn btn-secondary">
                    <i class="fas fa-list"></i> Show All
                </a>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="card shadow">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Company</th>
                                <th>VAT Number</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>City</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="v" items="${vendorList}">
                                <tr>
                                    <td>${v.vendorId}</td>
                                    <td><strong>${v.companyName}</strong></td>
                                    <td><code>${v.vatNumber}</code></td>
                                    <td>${v.email}</td>
                                    <td>${v.phoneNumber}</td>
                                    <td>${v.city}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${v.isActive}">
                                                <span class="badge bg-success">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/vendors?action=modify&id=${v.vendorId}"
                                           class="btn btn-sm btn-warning">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/vendors?action=remove&id=${v.vendorId}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Delete this vendor?')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <c:if test="${empty vendorList}">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle"></i> No vendors found.
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/fragments/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>