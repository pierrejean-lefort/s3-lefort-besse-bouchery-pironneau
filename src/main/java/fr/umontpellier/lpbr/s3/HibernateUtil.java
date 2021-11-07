package fr.umontpellier.lpbr.s3;
import org.apache.commons.lang3.SerializationUtils;
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

    public static Session openSession() {
        Session sess = getSessionFactory().openSession();
        sess.beginTransaction();
        return sess;
    }

    public static void closeSession(Session sess) {
        sess.getTransaction().commit();
        sess.close();
    }

    public static void save(Object o) {
        Session sess = openSession();
        sess.save(o);
        closeSession(sess);
    }

    public static void delete(Object o) {
        Session sess = openSession();
        sess.delete(o);
        closeSession(sess);
    }

    public static void shutdown() {
        if (sessionFactory == null) return;
        sessionFactory.close();
    }

    public static Object prepareToSave(Object o) {
        if (o.getClass() == Tournoi.class) {
            Tournoi t = ((Tournoi) o).clone();
            t.setParticipation(null);
            t.setParties(null);
            return t;
        }

        return o;
    }

}