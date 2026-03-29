package com.example.inventory_management.repository;

import com.example.inventory_management.config.DatabaseConnector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T, ID> implements CrudRepository<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepository.class);
    protected final Class<T> entityType;
    protected final SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public AbstractRepository() {
        this.entityType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.sessionFactory = DatabaseConnector.getSessionFactory();
    }

    @Override
    public T store(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            LOGGER.debug("Entity stored successfully: {}", entity);
            return entity;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Failed to store entity", e);
            throw new RuntimeException("Database operation failed", e);
        }
    }

    @Override
    public Optional<T> fetchById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            T entity = session.find(entityType, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            LOGGER.error("Failed to fetch entity with id: {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<T> fetchAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(entityType);
            query.from(entityType);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            LOGGER.error("Failed to fetch all entities", e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean eraseById(ID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            T entity = session.find(entityType, id);
            if (entity != null) {
                session.delete(entity);
                transaction.commit();
                LOGGER.debug("Entity with id {} deleted successfully", id);
                return true;
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error("Failed to delete entity with id: {}", id, e);
            return false;
        }
    }

    @Override
    public boolean existsById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(entityType, id) != null;
        } catch (Exception e) {
            LOGGER.error("Failed to check existence for id: {}", id, e);
            return false;
        }
    }

    @Override
    public long count() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            query.select(builder.count(query.from(entityType)));
            return session.createQuery(query).getSingleResult();
        } catch (Exception e) {
            LOGGER.error("Failed to count entities", e);
            return 0;
        }
    }
}