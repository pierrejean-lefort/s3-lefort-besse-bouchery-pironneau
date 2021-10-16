package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.View;
import fr.umontpellier.lpbr.s3.views.ourviews.MyHome;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;

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
        View.getView()
                .setIhm(this)
                .setScene(MyHome.class);
    }

    public void testDB() {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        sess.beginTransaction();

        List<Joueur> joueurs = sess.createQuery("from joueurs").list();

        for (Joueur j : joueurs) {
            System.out.println(j);
        }

        List<Tournoi> tournois = sess.createQuery("from tournois").list();

        for (Tournoi t : tournois) {
            System.out.println(t);
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
}
