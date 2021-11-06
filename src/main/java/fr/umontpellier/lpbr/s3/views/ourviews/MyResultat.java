package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.Participe;
import fr.umontpellier.lpbr.s3.views.Resultat;
import fr.umontpellier.lpbr.s3.views.Table;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    private Button suiv;

    @FXML
    private Button prec;

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

        int nbParticipant = View.getIhm().getSelectedTournoi().getParticipation().size();

        float nbLigne = ((float) nbParticipant)/6;

        Participe[] joueurs = View.getIhm().getSelectedTournoi().getParticipation().toArray(new Participe[nbParticipant]);

        int compteur = 0;

        for (int i = 0; i < Math.max(Math.min(nbLigne, 3), 1); i++) {
            HBox hbox = new HBox();
            hbox.setStyle("-fx-border-style: solid inside;");
            for (int j = 0; j < 3; j++) {
                compteur++;
                Table m1 = new MyTable(joueurs[0].getJoueur(), joueurs[1].getJoueur());
                m1.getTable().setText("Table" + compteur);
                m1.getTable().setTranslateY(10);
                m1.getChildren().add(m1.getTable());

                hbox.getChildren().add(m1);
            }
            vbox.getChildren().add(hbox);
        }

        retour.setOnAction(retourAction);

        imprimer.setDisable(true);
        rdsuiv.setDisable(true);
        suiv.setDisable(true);
        prec.setDisable(true);


    }
}
