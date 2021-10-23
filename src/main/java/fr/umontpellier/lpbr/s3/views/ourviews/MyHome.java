package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.views.Home;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyHome extends Home {
    @FXML private Pane root;

    @FXML private Button nbRoundPlus;
    @FXML private Button nbRoundMoins;
    @FXML private Button creerJoueur;
    @FXML private Label text;
    @FXML private Label compteur;
    private int compt;

    private EchecIHM ihm;

    public MyHome(EchecIHM echecIHM) {
        super();
        ihm = echecIHM;
        compt = 0;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/homeView.fxml"));

        fxmlLoader.setController(this);

        try {
            Node node = fxmlLoader.load();
            root = new Pane(node);
            Scene scene = new Scene(root);
            Stage stage = ihm.getPrimaryStage();

            stage.setScene(scene);
            stage.show();

            creerJoueur.setOnAction(creerJoueurAction);
            nbRoundPlus.setOnAction(nbRoundPlusAction);
            nbRoundMoins.setOnAction(nbRoundMoinsAtion);

            System.out.println("Initiated");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private EventHandler<ActionEvent> creerJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.out.println("Hello");
            text.setText("Ton père");
        }
    };

    private EventHandler<ActionEvent>  nbRoundPlusAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            compt ++;
            compteur.setText(""+compt+"");
        }
    };

    private EventHandler<ActionEvent>  nbRoundMoinsAtion = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (compt != 0){
                compt --;
                compteur.setText(""+compt+"");
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
