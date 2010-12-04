package hibernate;

import java.util.Calendar;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.classic.Session;
import org.hibernate.dialect.HSQLDialect;

import com.example.bean.Reservation;
import com.example.bean.Restaurant;
import com.example.bean.User;
import com.example.bean.Ville;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            AnnotationConfiguration cnf = new AnnotationConfiguration();
            cnf.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
            cnf.setProperty(Environment.URL, "jdbc:mysql://localhost:8889/vaadin");
            cnf.setProperty(Environment.USER, "vaadin");
            cnf.setProperty(Environment.PASS, "vaadin");
           // cnf.setProperty(Environment.DIALECT, HSQLDialect.class.getName());
            cnf.setProperty(Environment.SHOW_SQL, "true");
           // cnf.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
            cnf.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS,
                            "thread");
            cnf.addAnnotatedClass(User.class);
            cnf.addAnnotatedClass(Ville.class);
            cnf.addAnnotatedClass(Restaurant.class);
            cnf.addAnnotatedClass(Reservation.class);

            sessionFactory = cnf.buildSessionFactory();


        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
