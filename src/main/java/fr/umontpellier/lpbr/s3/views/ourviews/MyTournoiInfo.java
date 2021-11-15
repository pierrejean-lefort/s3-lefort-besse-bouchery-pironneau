package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.Participe;
import fr.umontpellier.lpbr.s3.Tournoi;
import fr.umontpellier.lpbr.s3.views.TounoiInfo;
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
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MyTournoiInfo extends TounoiInfo {
    public static String fxmlPath = "/fxml/tournoiInfoView.fxml";
    private Tournoi tournoi;
    @FXML private Button retour;
    @FXML private Button deleteJoueur;
    @FXML private Button search;
    @FXML private Button start;
    @FXML private Button delete;
    @FXML private Label idTournoi;
    @FXML private Label nomTournoi;
    @FXML private Label methode;
    @FXML private Label nbRounds;
    @FXML private ListView<Joueur> list;
    private ObservableList joueurs;
    private Joueur selectedJoueur;

    public MyTournoiInfo() {}


    private EventHandler<ActionEvent> retourAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyTournoi.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> searchAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyJoueurSearch.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> startAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            View.getIhm().setLoading();
        }
    };

    private EventHandler<ActionEvent> deleteJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (selectedJoueur == null) return;
            Set<Participe> participes = tournoi.getParticipation();
            Participe pp = null;
            for(Participe p : participes) {
                if (p.getJoueur() == selectedJoueur) {
                    pp = p;
                    HibernateUtil.delete(p);
                }
            }
            if (pp == null) return;

            participes.remove(selectedJoueur);
            View.getIhm().getSelectedTournoi().setParticipation(participes);
            joueurs.removeAll(selectedJoueur);
            list.getItems().remove(selectedJoueur);
            selectedJoueur = null;
        }
    };

    private EventHandler<ActionEvent> deleteAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Session sess = HibernateUtil.openSession();
            for (Participe p : tournoi.getParticipation()) {
                sess.delete(p);
            }
            tournoi.setParticipation(new HashSet<>());
            sess.delete(tournoi);
            HibernateUtil.closeSession(sess);

            try {
                View.getView().setScene(MyTournoi.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private ChangeListener<Joueur> joueurChangeListener = new ChangeListener<Joueur>() {
        @Override
        public void changed(ObservableValue<? extends Joueur> observableValue, Joueur joueur, Joueur t1) {
            deleteJoueur.setDisable(false);
            selectedJoueur = t1;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tournoi = View.getIhm().getSelectedTournoi();
        if (tournoi == null) {
            try {
                View.getView().setScene(MyTournoiInfo.class);
                return;
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        idTournoi.setText("#" + tournoi.getId());
        nomTournoi.setText(tournoi.getNom());
        methode.setText(tournoi.getMethode());
        nbRounds.setText(tournoi.getNbRound() + " rounds");

        retour.setOnAction(retourAction);
        search.setOnAction(searchAction);
        start.setOnAction(startAction);
        deleteJoueur.setOnAction(deleteJoueurAction);
        delete.setOnAction(deleteAction);

        joueurs = FXCollections.observableArrayList();
        for (Participe p : tournoi.getParticipation()) {
            joueurs.add(p.getJoueur());
            list.getItems().add(p.getJoueur());
        }
        list.getSelectionModel().selectedItemProperty().addListener(joueurChangeListener);

    }
}
