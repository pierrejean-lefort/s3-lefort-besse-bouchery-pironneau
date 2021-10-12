package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.EchecIHM;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyHome extends Stage implements Initializable {
    @FXML private Pane root;

    @FXML private Button creerJoueur;
    @FXML private Label text;

    private EchecIHM ihm;

    public MyHome(EchecIHM echecIHM) {
        super();
        ihm = echecIHM;

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

            System.out.println("Initiated");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private EventHandler<ActionEvent> creerJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            ihm.initCreationJoueurView();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
