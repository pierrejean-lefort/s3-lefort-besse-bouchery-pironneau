package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.ourviews.MyHome;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class EchecIHM extends Application {
    private Stage primaryStage;
    private Home homeView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Echec !");
        initHomeView();

        testDB();
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
}
