package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.views.ourviews.MyCreationJoueur;
import fr.umontpellier.lpbr.s3.views.ourviews.MyHome;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class EchecIHM extends Application {
    private Stage primaryStage;
    private MyHome homeView;
    private MyCreationJoueur creationJoeueurView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Echec !");
        initHomeView();
    }

    public void testDB() {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        sess.beginTransaction();

        List<Joueur> joueurs = sess.createQuery("from joueurs").list();

        for (Joueur j : joueurs) {
            System.out.println(j);
        }

        sess.getTransaction().commit();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Stopping app");
        HibernateUtil.shutdown();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void initHomeView() {
        homeView = new MyHome(this);
    }

    public void initCreationJoueurView() { creationJoeueurView = new MyCreationJoueur(this);}
}
