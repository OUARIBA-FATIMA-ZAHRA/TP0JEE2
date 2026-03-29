package com.example.inventory_management.repository;

import com.example.inventory_management.entity.Vendor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VendorRepositoryImpl extends AbstractRepository<Vendor, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorRepositoryImpl.class);

    public Optional<Vendor> findByVatNumber(String vatNumber) {
        if (vatNumber == null || vatNumber.trim().isEmpty()) {
            return Optional.empty();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Vendor> query = session.createQuery(
                    "FROM Vendor v WHERE v.vatNumber = :vat", Vendor.class);
            query.setParameter("vat", vatNumber);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            LOGGER.error("Failed to find vendor by VAT: {}", vatNumber, e);
            return Optional.empty();
        }
    }

    public List<Vendor> fetchActiveVendors() {
        try (Session session = sessionFactory.openSession()) {
            Query<Vendor> query = session.createQuery(
                    "FROM Vendor v WHERE v.isActive = true ORDER BY v.companyName",
                    Vendor.class);
            return query.list();
        } catch (Exception e) {
            LOGGER.error("Failed to fetch active vendors", e);
            return Collections.emptyList();
        }
    }

    public List<Vendor> searchVendors(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return fetchAll();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Vendor> query = session.createQuery(
                    "FROM Vendor v WHERE LOWER(v.companyName) LIKE LOWER(:kw) " +
                            "OR LOWER(v.city) LIKE LOWER(:kw) " +
                            "OR LOWER(v.email) LIKE LOWER(:kw)", Vendor.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            LOGGER.error("Failed to search vendors with keyword: {}", keyword, e);
            return Collections.emptyList();
        }
    }

    public List<Vendor> findByCity(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Vendor> query = session.createQuery(
                    "FROM Vendor v WHERE LOWER(v.city) = LOWER(:city)", Vendor.class);
            query.setParameter("city", cityName);
            return query.list();
        } catch (Exception e) {
            LOGGER.error("Failed to find vendors by city: {}", cityName, e);
            return Collections.emptyList();
        }
    }

    public boolean isEmailUnique(String email, Long excludeId) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(v) FROM Vendor v WHERE v.email = :email AND v.vendorId <> :id",
                    Long.class);
            query.setParameter("email", email);
            query.setParameter("id", excludeId != null ? excludeId : -1L);
            Long count = query.uniqueResult();
            return count == null || count == 0;
        } catch (Exception e) {
            LOGGER.error("Failed to check email uniqueness", e);
            return false;
        }
    }
}