package fr.umontpellier.lpbr.s3;
import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    private HibernateUtil() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) new HibernateUtil();

        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory == null) return;
        sessionFactory.close();
    }

}