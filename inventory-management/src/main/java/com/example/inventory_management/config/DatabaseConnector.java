package com.example.inventory_management.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DatabaseConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnector.class);
    private static volatile SessionFactory sessionFactory;
    private static final Object LOCK = new Object();

    private DatabaseConnector() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (LOCK) {
                if (sessionFactory == null) {
                    try {
                        LOGGER.info("Initializing Hibernate SessionFactory...");
                        sessionFactory = new Configuration()
                                .configure("hibernate.cfg.xml")
                                .buildSessionFactory();
                        LOGGER.info("SessionFactory created successfully");
                    } catch (Exception e) {
                        LOGGER.error("Failed to initialize SessionFactory", e);
                        throw new RuntimeException("Database connection failed", e);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            LOGGER.info("SessionFactory closed successfully");
        }
    }
}