package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppListener implements ServletContextListener {
    private EmailNotificationThread emailThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emailThread = new EmailNotificationThread();
        emailThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emailThread != null && emailThread.isAlive()) {
            emailThread.interrupt();
        }
    }
}