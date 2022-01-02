package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.Methode.Methode;
import fr.umontpellier.lpbr.s3.SystemTournoi.Suisse;
import fr.umontpellier.lpbr.s3.views.View;
import fr.umontpellier.lpbr.s3.views.ourviews.*;
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
import java.util.Set;
import java.util.concurrent.Callable;

public class EchecIHM extends Application {
    private Stage primaryStage;
    private Tournoi selectedTournoi;
    private Joueur selectedJoueur; // joueur pour créer un tournoi, ou celui de gauche dans les appariments
    private Joueur selectedJoueurAppar; // Joueur à droite
    private int toAssign; // Lequel du droite ou du gauche à assigner
    private int currentPage;
    private DoubleProperty progress;
    private List<Joueur> aAppairer;
    private List<Joueur> appaire;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        this.currentPage = 0;
        this.progress = new SimpleDoubleProperty(0.0);
        this.toAssign = -1;

        this.aAppairer = new ArrayList<>();
        this.appaire = new ArrayList<>();

        primaryStage.setTitle("Echecs !");
        if (true) {
            View.getView()
                    .setIhm(this)
                    .setScene(MyTournoi.class);
        } else {
            testDB();
//            Session sess = HibernateUtil.openSession();
//
//            Tournoi t = (Tournoi) sess.createQuery("FROM tournois WHERE id=3").getSingleResult();
//
//            System.out.println(t.gotCurrentRound());
//
//            sess.close();
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

    public Joueur getSelectedJoueurAppar() {
        return selectedJoueurAppar;
    }

    public void setSelectedJoueurAppar(Joueur selectedJoueurAppar) { this.selectedJoueurAppar = selectedJoueurAppar; }

    public int getToAssign() {
        return toAssign;
    }

    public void setToAssign(int toAssign) {
        this.toAssign = toAssign;
    }

    public List<Joueur> getaAppairer() {
        return aAppairer;
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
//            View.getView().setScene(MyLoading.class);
//            setProgress(-1);
//            setCurrentPage(0);
            aAppairer.clear();
            appaire.clear();
            setSelectedJoueurAppar(null);
            setSelectedJoueur(null);
            setSelectedJoueur(null);

            int round = getSelectedTournoi().gotCurrentRound();
            if (round == 0) round = 1;

            Tournoi t = View.getIhm().getSelectedTournoi();
            if (round >= t.getNbRound()){
                View.getView().setScene(MyClassement.class, true);
                return;
            }

            Session sess = HibernateUtil.openSession();
            for (Participe p : t.getParticipation()) {
                Joueur j = p.getJoueur();

                List<Partie> parties = sess.createQuery("FROM parties WHERE tournoi=:t AND numRonde=:r AND ( joueur_noir=:j OR joueur_blanc=:j )")
                        .setParameter("t", t)
                        .setParameter("r", round)
                        .setParameter("j", j)
                        .list();

                if (parties.isEmpty()) {
                    aAppairer.add(j);
                    if (getSelectedJoueurAppar() == null) {
                        setSelectedJoueurAppar(j);
                    } else if (getSelectedJoueur() == null) {
                        setSelectedJoueur(j);
                    }
                } else {
                    appaire.add(j);
                }
            }

            HibernateUtil.closeSession(sess);
            if (aAppairer.size() == 0) {
                View.getView().setScene(MyResultat.class, true);
                return;
            }
            View.getView().setScene(MyAppar.class, true);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Joueur suivantAppariement() {
        if (aAppairer.size() == 0) return null;
        return aAppairer.get(0);
    }

    public Joueur precedentAppariment() {
        if (appaire.size() -1 < 0) return null;
        Joueur j = appaire.get(appaire.size()-1);
        Session sess = HibernateUtil.openSession();
        if (j != null) {
            int round = getSelectedTournoi().gotCurrentRound();
            if (round == 0) round = 1;
            List<Partie> parties = sess.createQuery("FROM parties WHERE numRonde=:round AND ( joueur_blanc=:jb OR joueur_noir=:jn )")
                    .setParameter("round", round)
                    .setParameter("jb", j)
                    .setParameter("jn", j)
                    .list();

            if (!parties.isEmpty()) {
                Partie p = parties.get(0);

                appaire.remove(p.getJoueur_noir());
                appaire.remove(p.getJoueur_blanc());

                aAppairer.add(p.getJoueur_blanc());
                aAppairer.add(p.getJoueur_noir());

                p.setJoueur_noir(null);
                p.setJoueur_blanc(null);
                sess.delete(p);
            }
        }
        HibernateUtil.closeSession(sess);
        return j;
    }

    public boolean appaire(Joueur jb, Joueur jn, int round) {
        Session sess = HibernateUtil.openSession();

        List<Partie> partiesJb = sess.createQuery("FROM parties WHERE tournoi=:t AND numRonde=:round AND ( joueur_blanc=:jb OR joueur_noir=:jn )")
                .setParameter("t", getSelectedTournoi())
                .setParameter("round", round)
                .setParameter("jb", jb)
                .setParameter("jn", jb)
                .list();

        List<Partie> partiesJn = sess.createQuery("FROM parties WHERE tournoi=:t AND numRonde=:round AND ( joueur_blanc=:jb OR joueur_noir=:jn )")
                .setParameter("t", getSelectedTournoi())
                .setParameter("round", round)
                .setParameter("jb", jn)
                .setParameter("jn", jn)
                .list();

        HibernateUtil.closeSession(sess);


        appaire.add(jb);
        appaire.add(jn);
        aAppairer.remove(jb);
        aAppairer.remove(jn);

        if (!partiesJb.isEmpty() || !partiesJn.isEmpty()) {
            System.out.println("Partie already exists for one of this player");
            System.out.println(partiesJb);
            System.out.println(partiesJn);
            return false;
        }

        Partie.createPartie(getSelectedTournoi(), jb, jn, round, ""+appaire.size());

        return true;
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
