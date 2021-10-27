package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.HibernateUtil;
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
import java.util.List;
import java.util.ResourceBundle;

public class MyTournoi extends Tournois {
    public static String fxmlPath = "/fxml/tournoiView.fxml";
    private ObservableList tournois;
    private Session sess;
    @FXML private ListView<Tournoi> listTournoi;
    @FXML private Button selectTournoi;
    @FXML private Button creerTournoi;

    public MyTournoi() {}

    private EventHandler<ActionEvent> creerTournoiHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if(sess.isOpen())HibernateUtil.closeSession(sess);
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
            if(sess.isOpen())HibernateUtil.closeSession(sess);
            try {
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
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creerTournoi.setOnAction(creerTournoiHandler);
        selectTournoi.setOnAction(selectTournoiHandler);

        sess = HibernateUtil.getSessionFactory().openSession();
        sess.beginTransaction();
        tournois = FXCollections.observableArrayList(sess.createQuery("from tournois").list());

        for(Object t : tournois) {
            listTournoi.getItems().add((Tournoi) t);
        }
        listTournoi.getSelectionModel().selectedItemProperty().addListener(tournoiChangeListener);

    }
}
