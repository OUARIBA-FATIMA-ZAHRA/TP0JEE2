<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StockFlow - Category Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/navigation.jsp" />

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <div class="d-flex justify-content-between align-items-center">
                    <h1><i class="fas fa-tags"></i> Product Categories</h1>
                    <a href="${pageContext.request.contextPath}/categories?action=new" class="btn btn-primary">
                        <i class="fas fa-plus"></i> New Category
                    </a>
                </div>
                <hr>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <form action="${pageContext.request.contextPath}/categories" method="get" class="d-flex">
                    <input type="hidden" name="action" value="search">
                    <input type="text" class="form-control me-2" name="keyword"
                           placeholder="Search categories..." value="${searchTerm}">
                    <button type="submit" class="btn btn-outline-primary">
                        <i class="fas fa-search"></i> Search
                    </button>
                </form>
            </div>
            <div class="col-md-6 text-end">
                <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">
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
                                <th>Reference</th>
                                <th>Title</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Priority</th>
                                <th>Created</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="cat" items="${categoryList}">
                                <tr>
                                    <td>${cat.recordId}</td>
                                    <td><code>${cat.categoryRef}</code></td>
                                    <td><strong>${cat.categoryTitle}</strong></td>
                                    <td>${cat.description}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${cat.enabled}">
                                                <span class="badge bg-success">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${cat.sortPriority}</td>
                                    <td>${cat.formattedCreationDate}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/categories?action=edit&id=${cat.recordId}"
                                           class="btn btn-sm btn-warning">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/categories?action=delete&id=${cat.recordId}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Delete this category?')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <c:if test="${empty categoryList}">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle"></i> No categories found.
                        <a href="${pageContext.request.contextPath}/categories?action=new">Create one</a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/fragments/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>