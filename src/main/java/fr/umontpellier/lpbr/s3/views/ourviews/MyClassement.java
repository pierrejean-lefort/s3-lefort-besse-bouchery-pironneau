package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.views.Classement;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MyClassement extends Classement{
    public static String fxmlPath = "/fxml/classementView.fxml";

    @FXML private Button retour;
    @FXML private ListView<Joueur> list;
    @FXML private TextField search;
    @FXML private Label error;
    @FXML private Label nomTournoi;
    @FXML private Label methode;

    private List<Joueur> allJoueurs;
    private ObservableList<Joueur> joueurs;

    public MyClassement() {}

    private EventHandler<ActionEvent> retourAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyTournoi.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retour.setOnAction(retourAction);
        search.setDisable(true);
        methode.setText(View.getIhm().getSelectedTournoi().getMethode());
        nomTournoi.setText(View.getIhm().getSelectedTournoi().getNom());
        allJoueurs = View.getIhm().getSelectedTournoi().gotClassement();
        for (Joueur j : allJoueurs){
            list.getItems().add(j);
        }
    }
}
