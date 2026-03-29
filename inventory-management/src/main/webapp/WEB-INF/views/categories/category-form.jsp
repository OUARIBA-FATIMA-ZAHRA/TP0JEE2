<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StockFlow - ${category.recordId == null ? 'Add' : 'Edit'} Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/navigation.jsp" />

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-dark text-white">
                        <h4 class="mb-0">
                            <i class="fas fa-tag"></i>
                            ${category.recordId == null ? 'Create Category' : 'Update Category'}
                        </h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                <i class="fas fa-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/categories" method="post">
                            <input type="hidden" name="action" value="${category.recordId == null ? 'create' : 'update'}">
                            <c:if test="${category.recordId != null}">
                                <input type="hidden" name="id" value="${category.recordId}">
                            </c:if>

                            <div class="mb-3">
                                <label for="categoryRef" class="form-label">Category Reference *</label>
                                <input type="text" class="form-control" id="categoryRef" name="categoryRef"
                                       value="${category.categoryRef}" required pattern="[A-Z0-9_]{2,30}"
                                       placeholder="e.g., ELEC_001">
                                <small class="text-muted">Unique identifier (uppercase letters, numbers, underscore)</small>
                            </div>

                            <div class="mb-3">
                                <label for="categoryTitle" class="form-label">Category Title *</label>
                                <input type="text" class="form-control" id="categoryTitle" name="categoryTitle"
                                       value="${category.categoryTitle}" required>
                            </div>

                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3">${category.description}</textarea>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="sortPriority" class="form-label">Sort Priority</label>
                                    <input type="number" class="form-control" id="sortPriority" name="sortPriority"
                                           value="${category.sortPriority != null ? category.sortPriority : 0}">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <div class="form-check mt-4">
                                        <input type="checkbox" class="form-check-input" id="enabled" name="enabled"
                                               <c:if test="${category.enabled != false}">checked</c:if>>
                                        <label class="form-check-label" for="enabled">Active Category</label>
                                    </div>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left"></i> Cancel
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Save Category
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/fragments/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>