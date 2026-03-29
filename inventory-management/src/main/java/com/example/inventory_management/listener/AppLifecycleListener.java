package com.example.inventory_management.listener;

import com.example.inventory_management.config.DatabaseConnector;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLifecycleListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("========================================");
        LOGGER.info("  StockFlow Inventory Manager Starting  ");
        LOGGER.info("========================================");

        try {
            DatabaseConnector.getSessionFactory();
            LOGGER.info("Database connection established successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize database connection", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Shutting down StockFlow Inventory Manager...");
        DatabaseConnector.close();
        LOGGER.info("Application shutdown complete");
    }
}