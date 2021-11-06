package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.views.Table;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MyTable extends Table {

    public Label table = new Label();

    public Label getTable() {
        return super.getTable();
    }


    public MyTable(Joueur jblanc, Joueur jnoir) {

        HBox hbox = new HBox();

        Pane noir = new Pane();
        Label lbl = new Label(jnoir.getNom());
        lbl.setStyle("-fx-text-fill: white");
        noir.getChildren().add(lbl);


        Pane blanc = new Pane();
        Label lbl2 = new Label(jblanc.getNom());
        blanc.getChildren().add(lbl2);

        blanc.setPrefSize(100, 100);

        noir.setPrefSize(100, 100);

        noir.setStyle("-fx-background-color: black;");
        hbox.getChildren().addAll(noir, blanc);
        this.getChildren().add(hbox);
    }

}
