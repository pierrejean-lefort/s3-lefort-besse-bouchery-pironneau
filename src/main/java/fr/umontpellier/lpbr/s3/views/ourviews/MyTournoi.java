package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Participe;
import fr.umontpellier.lpbr.s3.Tournoi;
import fr.umontpellier.lpbr.s3.views.Tournois;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class MyTournoi extends Tournois {
    public static String fxmlPath = "/fxml/tournoiView.fxml";
    private ObservableList<Tournoi> tournois;
    @FXML private ListView<Tournoi> listTournoi;
    @FXML private Button selectTournoi;
    @FXML private Button creerTournoi;
    @FXML private Button supprimerTournoi;

    public MyTournoi() {}

    private EventHandler<ActionEvent> deleteAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Tournoi tournoi = View.getIhm().getSelectedTournoi();
            View.getIhm().setSelectedTournoi(null);
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

    private EventHandler<ActionEvent> creerTournoiHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyHome.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> selectTournoiHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                int round = View.getIhm().getSelectedTournoi().gotCurrentRound();
                View.getIhm().setCurrentPage(0);
                if (round != 0) {
                    View.getIhm().setLoading();
                    return;
                }
                View.getView().setScene(MyTournoiInfo.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private ChangeListener<Tournoi> tournoiChangeListener = new ChangeListener<Tournoi>() {
        @Override
        public void changed(ObservableValue<? extends Tournoi> observableValue, Tournoi tournoi, Tournoi t1) {
            View.getIhm().setSelectedTournoi(t1);
            System.out.println("Tournoi selectioné: " + t1.getNom());
            selectTournoi.setDisable(false);
            supprimerTournoi.setDisable(false);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creerTournoi.setOnAction(creerTournoiHandler);
        selectTournoi.setOnAction(selectTournoiHandler);
        supprimerTournoi.setOnAction(deleteAction);
        supprimerTournoi.setDisable(true);

        Session sess = HibernateUtil.openSession();
        tournois = FXCollections.observableArrayList(sess.createQuery("from tournois").list());

        for(Tournoi t : tournois) {
            listTournoi.getItems().add(t);
        }
        listTournoi.getSelectionModel().selectedItemProperty().addListener(tournoiChangeListener);
        HibernateUtil.closeSession(sess);
    }
}
