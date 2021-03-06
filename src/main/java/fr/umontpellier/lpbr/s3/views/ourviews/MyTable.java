package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.Partie;
import fr.umontpellier.lpbr.s3.views.Table;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.hibernate.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class MyTable extends Table {

    private boolean isNChecked = false;

    private boolean isBChecked = false;

    public Label table = new Label();

    public Label getTable() {
        return super.getTable();
    }

    public boolean formatResultat(boolean isBlanc, Partie p) {
        return (p.getResultat()==1 || p.getResultat()==3) && isBlanc ? true : (p.getResultat()==2 || p.getResultat() == 3) && !isBlanc;
    }

    public MyTable(Partie p) {

        Label equa = new Label();
        equa.setLayoutX(75);
        equa.setLayoutY(50);
        equa.setStyle("-fx-text-fill: white");

        Label lity = new Label();
        lity.setLayoutX(0);
        lity.setLayoutY(50);
        lity.setStyle("-fx-text-fill: black");

        HBox hbox = new HBox();

        Label checkB = new Label("");
        checkB.setPadding(new Insets(25));
        checkB.setFont(new Font(20));

        Pane noir = new Pane();
        Label lbl = new Label(p.getJoueur_noir().getNom() + " " + p.getJoueur_noir().getPrenom());
        lbl.setStyle("-fx-text-fill: white");
        Label checkN = new Label("");
        checkN.setAlignment(Pos.BASELINE_CENTER);
        checkN.setPadding(new Insets(25));
        checkN.setFont(new Font(20));
        checkN.setStyle("-fx-text-fill: white");
        isNChecked = formatResultat(false, p);
        checkN.setText(isNChecked ? "X" : "");
        noir.getChildren().addAll(lbl, checkN, equa);
        noir.setOnMouseClicked(event -> {
            isNChecked = !isNChecked;
            checkN.setText(isNChecked ? "X" : "");
            if (isBChecked && isNChecked) {
                equa.setText("equa");
                lity.setText("lity");
            } else {
                equa.setText("");
                lity.setText("");
            }
            Session ses = HibernateUtil.openSession();
            p.setResultat(getResultat());
            ses.update(p);
            HibernateUtil.closeSession(ses);
        });


        Pane blanc = new Pane();
        Label lbl2 = new Label(p.getJoueur_blanc().getNom() + " " + p.getJoueur_blanc().getPrenom());
        isBChecked = formatResultat(true, p);
        checkB.setText(isBChecked ? "X" : "");
        blanc.getChildren().addAll(lbl2, checkB, lity);
        blanc.setOnMouseClicked(event -> {
            isBChecked = !isBChecked;
            checkB.setText(isBChecked ? "X" : "");
            if (isBChecked && isNChecked) {
                equa.setText("equa");
                lity.setText("lity");
            } else {
                equa.setText("");
                lity.setText("");
            }
            Session ses = HibernateUtil.openSession();
            p.setResultat(getResultat());
            ses.update(p);
            HibernateUtil.closeSession(ses);
        });

        blanc.setPrefSize(100, 75);
        noir.setPrefSize(100, 75);

        noir.setStyle("-fx-background-color: black;");
        hbox.getChildren().addAll(noir, blanc);
        this.getChildren().add(hbox);


    }

    public boolean isValid() {
        return isNChecked || isBChecked;
    }

    public int getResultat() {
        return isNChecked && isBChecked ? 3 : (isNChecked ? 2 : 1);
    }

}
