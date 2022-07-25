package org.valeryvash.util;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;

import java.util.TimeZone;

public class HibernateSessionProvider {
    private static StandardServiceRegistry registry = null;
    private static SessionFactory sessionFactory = null;

    static {
        registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Event.class)
                .addAnnotatedClass(File.class)
                .getMetadataBuilder()
                .build()
                .getSessionFactoryBuilder()
                .build();
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
