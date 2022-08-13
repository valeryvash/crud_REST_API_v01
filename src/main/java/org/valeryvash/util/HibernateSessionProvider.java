package org.valeryvash.util;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;

import java.util.Properties;
import java.util.TimeZone;

public class HibernateSessionProvider {
    private static SessionFactory sessionFactory = null;

    static {
        final String CONNECTION_URL = System.getenv("CLEARDB_DATABASE_URL");

        Properties properties = new Properties();
        properties.put("hibernate.connection.url", CONNECTION_URL);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.jdbc.time_zone", "UTC");
        properties.put("show_sql", "true");
        properties.put("generate_statistics", "true");
        properties.put("use_sql_comments", "true");
        properties.put("hbm2ddl.auto", "validate");

        Configuration conf =
                new Configuration()
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Event.class)
                        .addAnnotatedClass(File.class)
                        .setProperties(properties);

        sessionFactory =
                conf.buildSessionFactory();

    }

    public static Session provideSession() {
        /*
         *  Time zones
         *  https://www.baeldung.com/hibernate-date-time#time-zone-setup
         *  Duplicates in hibernate.cfg.xml
         */
        Session sessionToBeReturned = sessionFactory
                .withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC"))
                .openSession();
        if (sessionToBeReturned != null) {
            return sessionToBeReturned;
        } else {
            throw new SessionException("SessionProvider class doesn't provide a session");
        }
    }
}
