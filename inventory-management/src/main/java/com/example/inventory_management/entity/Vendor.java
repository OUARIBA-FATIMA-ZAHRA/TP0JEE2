package com.example.inventory_management.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "supplier_vendors",
        indexes = {
                @Index(name = "idx_vendor_email", columnList = "email"),
                @Index(name = "idx_vendor_company", columnList = "company_name")
        })
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    @Column(name = "company_name", nullable = false, length = 180)
    private String companyName;

    @Column(name = "vat_number", length = 60)
    private String vatNumber;

    @Column(length = 120)
    private String email;

    @Column(length = 25)
    private String phoneNumber;

    @Column(length = 350)
    private String streetAddress;

    @Column(length = 70)
    private String city;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(length = 60)
    private String country;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "payment_terms", length = 80)
    private String paymentTerms;

    @Column(name = "max_credit")
    private Double maxCredit;

    @Column(name = "website_url", length = 200)
    private String websiteUrl;

    @Column(name = "contact_person", length = 120)
    private String contactPerson;

    @Column(name = "registered_date", updatable = false)
    private LocalDateTime registeredDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Vendor() {
        this.registeredDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    public Vendor(String companyName, String email, String city) {
        this();
        this.companyName = companyName;
        this.email = email;
        this.city = city;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (isActive == null) isActive = true;
        if (registeredDate == null) registeredDate = LocalDateTime.now();
        if (lastUpdated == null) lastUpdated = LocalDateTime.now();
        if (vatNumber != null && vatNumber.trim().isEmpty()) {
            vatNumber = null;
        }
    }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getVatNumber() { return vatNumber; }
    public void setVatNumber(String vatNumber) { this.vatNumber = vatNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public String getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(String paymentTerms) { this.paymentTerms = paymentTerms; }
    public Double getMaxCredit() { return maxCredit; }
    public void setMaxCredit(Double maxCredit) { this.maxCredit = maxCredit; }
    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    public LocalDateTime getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(LocalDateTime registeredDate) { this.registeredDate = registeredDate; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public String getFormattedRegistrationDate() {
        if (registeredDate != null) {
            return registeredDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "N/A";
    }
}