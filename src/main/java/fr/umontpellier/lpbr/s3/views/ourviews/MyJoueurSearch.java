package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.*;
import fr.umontpellier.lpbr.s3.views.JoueurSearch;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class MyJoueurSearch extends JoueurSearch {
    public static String fxmlPath = "/fxml/joueurSearch.fxml";
    @FXML private Button retour;
    @FXML private Button newJoueur;
    @FXML private Button updateJoueur;
    @FXML private ListView<Joueur> list;
    @FXML private Button selectionner;
    @FXML private TextField search;
    @FXML private Label error;

    private List<Joueur> allJoueurs;
    private ObservableList<Joueur> joueurs;

    public MyJoueurSearch() {}

    private EventHandler<ActionEvent> retourAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(View.getLastScene());
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> selectionnerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.out.println(View.getLastScene());
            System.out.println("gné");
            if (View.getLastScene() == MyTournoiInfo.class) {
                Tournoi t = View.getIhm().getSelectedTournoi();
                Joueur j = View.getIhm().getSelectedJoueur();
                for (Participe p : t.getParticipation()) {
                    System.out.println("J: " + j);
                    System.out.println("P: " + p.getJoueur());
                    System.out.println("-----");
                    if (p.getJoueur().equals(j)) {
                        error.setText("Ce joueur est déjà dans le tournoi !");
                        return;
                    }
                }

                DataValidation.joueurExiste(j, t, error,"Ce joueur est déjà dans le tournoi !");
                Participe p = new Participe();
                p.setJoueur(j);
                p.setTournoi(t);
                p.setElo_joueur(j.getElo());

                HibernateUtil.save(p);// add in databse

                Set<Participe> pa = t.getParticipation(); // add in class because no refresh
                pa.add(p);
                t.setParticipation(pa);

                System.out.println("Joueur ajouté !");
            } else if (View.getLastScene() == MyAppar.class) {
                Tournoi t = View.getIhm().getSelectedTournoi();
                Joueur j = View.getIhm().getSelectedJoueur();
                boolean found = false;

                for(Participe p : t.getParticipation()) {
                    if (p.getJoueur().equals(j)) {
                        found = true;
                    }
                }

                if (!found) {
                    error.setText("Le joueur n'est pas dans le tournoi");
                    return;
                }
            }
            try {
                View.getView().setScene(View.getLastScene(), true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> newJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyCreation.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> updateJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (View.getIhm().getSelectedJoueur() == null) return;
            try {
                View.getView().setScene(MyModification.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private ChangeListener<String> searchListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
            if (Objects.equals(s, t1)) {
                return;
            }

            joueurs.removeAll();
            list.getItems().clear();

            t1 = t1.toLowerCase(Locale.ROOT);

            for(Joueur j : allJoueurs) {
                if (t1.equals("") || j.getNom().toLowerCase(Locale.ROOT).contains(t1) || j.getNumLicence().toLowerCase(Locale.ROOT).contains(t1) || j.getPrenom().toLowerCase(Locale.ROOT).contains(t1)) {
                    joueurs.add(j);
                    list.getItems().add(j);
                }
            }
        }
    };

    private ChangeListener<Joueur> joueurChangeListener = new ChangeListener<Joueur>() {
        @Override
        public void changed(ObservableValue<? extends Joueur> observableValue, Joueur joueur, Joueur t1) {
            updateJoueur.setDisable(false);
            View.getIhm().setSelectedJoueur(t1);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retour.setOnAction(retourAction);
        selectionner.setOnAction(selectionnerAction);
        newJoueur.setOnAction(newJoueurAction);
        updateJoueur.setOnAction(updateJoueurAction);
        search.textProperty().addListener(searchListener);

        Session sess = HibernateUtil.openSession();
        allJoueurs = View.getLastScene() != MyAppar.class ? sess.createQuery("from joueurs").list() : new ArrayList<>();
        if (View.getLastScene() == MyAppar.class) {
            Joueur toAppar = View.getIhm().getSelectedJoueurAppar();
            for(Joueur j : View.getIhm().getaAppairer()) {
                if (!j.equals(toAppar)) {
                    allJoueurs.add(j);
                }
            }
        }

        HibernateUtil.closeSession(sess);

        joueurs = FXCollections.observableArrayList();

        for(Joueur j : allJoueurs) {
            joueurs.add(j);
            list.getItems().add(j);
        }

        list.getSelectionModel().selectedItemProperty().addListener(joueurChangeListener);
    }
}
