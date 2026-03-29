<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StockFlow - ${vendor.vendorId == null ? 'Add' : 'Edit'} Vendor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/fragments/navigation.jsp" />

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-9">
                <div class="card shadow">
                    <div class="card-header bg-dark text-white">
                        <h4 class="mb-0">
                            <i class="fas fa-building"></i>
                            ${vendor.vendorId == null ? 'Register Vendor' : 'Update Vendor'}
                        </h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                <i class="fas fa-exclamation-triangle"></i> ${error}
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/vendors" method="post">
                            <input type="hidden" name="action" value="${vendor.vendorId == null ? 'save' : 'refresh'}">
                            <c:if test="${vendor.vendorId != null}">
                                <input type="hidden" name="vendorId" value="${vendor.vendorId}">
                            </c:if>

                            <div class="row">
                                <div class="col-md-8 mb-3">
                                    <label for="companyName" class="form-label">Company Name *</label>
                                    <input type="text" class="form-control" id="companyName" name="companyName"
                                           value="${vendor.companyName}" required>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label for="vatNumber" class="form-label">VAT Number</label>
                                    <input type="text" class="form-control" id="vatNumber" name="vatNumber"
                                           value="${vendor.vatNumber}">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email"
                                           value="${vendor.email}">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="phoneNumber" class="form-label">Phone Number</label>
                                    <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber"
                                           value="${vendor.phoneNumber}">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="streetAddress" class="form-label">Street Address</label>
                                <textarea class="form-control" id="streetAddress" name="streetAddress" rows="2">${vendor.streetAddress}</textarea>
                            </div>

                            <div class="row">
                                <div class="col-md-4 mb-3">
                                    <label for="city" class="form-label">City</label>
                                    <input type="text" class="form-control" id="city" name="city"
                                           value="${vendor.city}">
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label for="postalCode" class="form-label">Postal Code</label>
                                    <input type="text" class="form-control" id="postalCode" name="postalCode"
                                           value="${vendor.postalCode}">
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label for="country" class="form-label">Country</label>
                                    <input type="text" class="form-control" id="country" name="country"
                                           value="${vendor.country}">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="paymentTerms" class="form-label">Payment Terms</label>
                                    <input type="text" class="form-control" id="paymentTerms" name="paymentTerms"
                                           value="${vendor.paymentTerms}" placeholder="e.g., Net 30">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="maxCredit" class="form-label">Credit Limit</label>
                                    <input type="number" step="1000" class="form-control" id="maxCredit" name="maxCredit"
                                           value="${vendor.maxCredit}">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="websiteUrl" class="form-label">Website</label>
                                    <input type="url" class="form-control" id="websiteUrl" name="websiteUrl"
                                           value="${vendor.websiteUrl}" placeholder="https://">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="contactPerson" class="form-label">Contact Person</label>
                                    <input type="text" class="form-control" id="contactPerson" name="contactPerson"
                                           value="${vendor.contactPerson}">
                                </div>
                            </div>

                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="isActive" name="isActive"
                                       <c:if test="${vendor.isActive != false}">checked</c:if>>
                                <label class="form-check-label" for="isActive">Active Vendor</label>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/vendors" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left"></i> Cancel
                                </a>
                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-save"></i> Save Vendor
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