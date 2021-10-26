package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.Participe;
import fr.umontpellier.lpbr.s3.views.Resultat;
import fr.umontpellier.lpbr.s3.views.Table;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static fr.umontpellier.lpbr.s3.views.View.getIhm;

public class MyResultat extends Resultat {

    public static String fxmlPath = "/fxml/resultatsView.fxml";

    public MyResultat() {}

    @FXML
    private Button retour;

    @FXML
    private VBox vbox;

    @FXML
    private Button imprimer;

    @FXML
    private Button rdsuiv;

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

    public void initialize(URL url, ResourceBundle resourceBundle) {

        int nbParticipant = View.getIhm().getSelectedTournoi().getParitipation().size();

        float nbLigne = ((float) nbParticipant)/6;

        Participe[] joueurs = View.getIhm().getSelectedTournoi().getParitipation().toArray(new Participe[nbParticipant]);

        for (int i = 0; i < Math.max(Math.min(nbLigne, 3), 1); i++) {
            HBox hbox = new HBox();
            hbox.setStyle("-fx-border-style: solid inside;");
            for (int j = 0; j < 3; j++) {
                Table m1 = new MyTable(joueurs[0].getJoueur(), joueurs[1].getJoueur());
                hbox.getChildren().add(m1);
            }
            vbox.getChildren().add(hbox);
        }

        retour.setOnAction(retourAction);


    }
}
