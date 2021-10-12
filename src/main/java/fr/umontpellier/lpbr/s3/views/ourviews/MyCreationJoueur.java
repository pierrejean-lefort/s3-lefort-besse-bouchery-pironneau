package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.views.CreationJoueur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyCreationJoueur extends CreationJoueur {

    @FXML private Pane root;

    private EchecIHM ihm;



    public MyCreationJoueur(EchecIHM echecIHM) {


            super();
            ihm = echecIHM;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/creationJoueurView.fxml"));

            fxmlLoader.setController(this);

            try {
                Node node = fxmlLoader.load();
                root = new Pane(node);
                Scene scene = new Scene(root);
                Stage stage = ihm.getPrimaryStage();

                stage.setScene(scene);
                stage.show();


                System.out.println("Initiated");
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

}
