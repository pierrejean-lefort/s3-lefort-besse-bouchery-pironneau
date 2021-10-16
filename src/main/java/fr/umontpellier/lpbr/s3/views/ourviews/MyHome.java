package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.View;
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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyHome extends Home {
    public static String fxmlPath = "/fxml/homeView.fxml";
    @FXML private Button creerJoueur;
    @FXML private Label text;

    public MyHome() {
//        creerJoueur.setOnAction(creerJoueurAction); // definir les action dans le initalize
    }

    private EventHandler<ActionEvent> creerJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene("fr.umontpellier.lpbr.s3.views.ourviews.MyCreation"); // Comment changer de scene
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creerJoueur.setOnAction(creerJoueurAction);
        System.out.println("Initialized");
    }
}
