package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.Participe;
import fr.umontpellier.lpbr.s3.Partie;
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
import java.util.ArrayList;
import java.util.List;
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

        suiv.setDisable(true);

        int nbParticipant = View.getIhm().getSelectedTournoi().getParticipation().size();
        List<Partie> partie = View.getIhm().getSelectedTournoi().gotRepartition(View.getIhm().getSelectedTournoi().gotCurrentRound());
        float nbLigne = ((float) nbParticipant)/6;

        Participe[] joueurs = View.getIhm().getSelectedTournoi().getParticipation().toArray(new Participe[nbParticipant]);

        List<HBox> ligne = new ArrayList<>();

        int i = 0;
        int j = -1;

        for(Partie p : partie) {

            if (i%3 == 0) {
                j++;
                HBox hbox = new HBox();
                ligne.add(j, hbox);
            }
            Table m1 = new MyTable(p.getJoueur_blanc(), p.getJoueur_noir());
            m1.getTable().setText("Table" + i);
            m1.getTable().setTranslateY(10);
            m1.getChildren().add(m1.getTable());
            ligne.get(j).getChildren().add(m1);
            i++;
        }

        for(HBox hb : ligne) {

            vbox.getChildren().add(hb);
        }

        retour.setOnAction(retourAction);

        imprimer.setDisable(true);
        rdsuiv.setDisable(true);
        suiv.setDisable(true);
        prec.setDisable(true);


    }
}
