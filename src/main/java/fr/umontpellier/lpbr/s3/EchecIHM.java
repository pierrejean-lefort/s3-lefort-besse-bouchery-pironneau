package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.SystemTournoi.Suisse;
import fr.umontpellier.lpbr.s3.views.View;
import fr.umontpellier.lpbr.s3.views.ourviews.MyLoading;
import fr.umontpellier.lpbr.s3.views.ourviews.MyResultat;
import fr.umontpellier.lpbr.s3.views.ourviews.MyTournoi;
import fr.umontpellier.lpbr.s3.views.ourviews.MyTournoiInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EchecIHM extends Application {
    private Stage primaryStage;
    private Tournoi selectedTournoi;
    private Joueur selectedJoueur;
    private int currentPage;
    private DoubleProperty progress;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        this.currentPage = 0;
        this.progress = new SimpleDoubleProperty(0.0);

        primaryStage.setTitle("Echec !");
        if (true) {
            View.getView()
                    .setIhm(this)
                    .setScene(MyTournoi.class);
        } else {
//            testDB();
            Session sess = HibernateUtil.openSession();

            Tournoi t = (Tournoi) sess.createQuery("FROM tournois WHERE id=3").getSingleResult();

            System.out.println(t.gotCurrentRound());

            sess.close();
        }
    }

    public Tournoi getSelectedTournoi() {
        return selectedTournoi;
    }

    public void setSelectedTournoi(Tournoi selectedTournoi) {
        this.selectedTournoi = selectedTournoi;
    }

    public Joueur getSelectedJoueur() {
        return selectedJoueur;
    }

    public void setSelectedJoueur(Joueur selectedJoueur) {
        this.selectedJoueur = selectedJoueur;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public static void taskSetProgress(double progress) {
        if (View.getIhm() == null) return;

        Platform.runLater(() -> {
            System.out.println("new progress l" + progress);
            View.getIhm().setProgress(progress);
        });
    }

    public DoubleProperty getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress.set(progress/100);
    }

    public void setLoading() {
        try {
            View.getView().setScene(MyLoading.class);
            setProgress(-1);
            setCurrentPage(0);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void testDB() {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        sess.beginTransaction();
        List<Joueur> joueurs = sess.createQuery("from joueurs").list();
        List<Tournoi> tournois = sess.createQuery("from tournois").list();
        List<Partie> parties = sess.createQuery("from parties").list();

        System.out.println("\n Joueurs:");
        for (Joueur j : joueurs) {
            System.out.println(j);
        }

        System.out.println("\n Tournois:");
        for (Tournoi t : tournois) {
            int r = t.gotCurrentRound();
            System.out.println(t + " - round " + r);

            if (r == 2 && t.getParticipation().size() == 22) {
                List<Partie> p2 = new ArrayList<>(parties);
                p2.removeIf(p -> p.getNumRonde() != 1);
                System.out.println("Dernier round: " + p2);

                System.out.println("Prochain round: " + t.gotRepartition(r));
            }
        }

        System.out.println("\n Parties:");
        for (Partie p : parties) {
            System.out.println(p);
        }

        HibernateUtil.closeSession(sess);
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
