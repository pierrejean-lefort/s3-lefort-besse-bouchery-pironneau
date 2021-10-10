package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.ourviews.MyHome;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EchecIHM extends Application {
    private Stage primaryStage;
    private Home homeView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Echec !");
        initHomeView();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void initHomeView() {
        homeView = new MyHome(this);
    }
}
