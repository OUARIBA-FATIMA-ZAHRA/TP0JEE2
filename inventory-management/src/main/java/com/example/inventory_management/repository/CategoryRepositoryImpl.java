package com.example.inventory_management.repository;

import com.example.inventory_management.entity.ItemCategory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl extends AbstractRepository<ItemCategory, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    public Optional<ItemCategory> findByReference(String reference) {
        try (Session session = sessionFactory.openSession()) {
            Query<ItemCategory> query = session.createQuery(
                    "FROM ItemCategory c WHERE c.categoryRef = :ref", ItemCategory.class);
            query.setParameter("ref", reference);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            LOGGER.error("Failed to find category by reference: {}", reference, e);
            return Optional.empty();
        }
    }

    public List<ItemCategory> fetchActiveOnly() {
        try (Session session = sessionFactory.openSession()) {
            Query<ItemCategory> query = session.createQuery(
                    "FROM ItemCategory c WHERE c.enabled = true ORDER BY c.sortPriority",
                    ItemCategory.class);
            return query.list();
        } catch (Exception e) {
            LOGGER.error("Failed to fetch active categories", e);
            return Collections.emptyList();
        }
    }

    public List<ItemCategory> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return fetchAll();
        }
        try (Session session = sessionFactory.openSession()) {
            Query<ItemCategory> query = session.createQuery(
                    "FROM ItemCategory c WHERE LOWER(c.categoryTitle) LIKE LOWER(:kw) " +
                            "OR LOWER(c.categoryRef) LIKE LOWER(:kw)", ItemCategory.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            LOGGER.error("Failed to search categories with keyword: {}", keyword, e);
            return Collections.emptyList();
        }
    }
}