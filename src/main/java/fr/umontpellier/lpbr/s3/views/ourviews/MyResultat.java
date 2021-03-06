package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.*;
import fr.umontpellier.lpbr.s3.views.Resultat;
import fr.umontpellier.lpbr.s3.views.Table;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @FXML
    private Label page;

    @FXML
    private Label roundLbl;

    @FXML
    private Label error;

    private int partieSize;
    private int round;

    private EventHandler<ActionEvent> imprimerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            ArrayList<Partie> partie = new ArrayList<>(View.getIhm().getSelectedTournoi().gotRepartition(round));

            if (partie == null) {
                try {
                    View.getView().setScene(MyTournoi.class);
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return;
            };

            partie.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getTable())));

            PDF.resultatRoundCreatePDF(partie, View.getIhm().getSelectedTournoi());
            imprimer.setDisable(true);
        }
    };

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

    private EventHandler<ActionEvent> pageSuiv = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            int curPage = View.getIhm().getCurrentPage();
            if ((curPage)*9+9 > partieSize) return;
            View.getIhm().setCurrentPage(curPage+1);
            try {
                View.getView().setScene(MyResultat.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> pagePrec = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            int curPage = View.getIhm().getCurrentPage();
            if ((curPage-1) < 0) return;
            View.getIhm().setCurrentPage(curPage-1);
            try {
                View.getView().setScene(MyResultat.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> roundSuiv = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {

            if (!View.getIhm().getSelectedTournoi().isRoundValid(round)) {
                String err = "Toutes les tables ne sont pas valid??es";
                System.out.println(err);
                error.setText(err);
                return;
            }
            View.getIhm().setLoading();
        }
    };

    private EventHandler<ActionEvent> classementView = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyClassement.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    public void initialize(URL url, ResourceBundle resourceBundle) {

        retour.setOnAction(retourAction);

        imprimer.setOnAction(imprimerAction);
        imprimer.setDisable(false);

        rdsuiv.setOnAction(roundSuiv);

        suiv.setOnAction(pageSuiv);
        prec.setOnAction(pagePrec);

        page.setText((View.getIhm().getCurrentPage()+1)+"");

        round = View.getIhm().getSelectedTournoi().gotCurrentRound();
        // si le tournoi est termin?? on ne doit pas pouvoir ajouter de ronde suppl??mentaire, ??videmment la partie logicielle que pj a cod?? pour g??rer l'affichage des vues (lors de la selection d'un tournoi dans MyTournoi) fais n'importe quoi sans v??rifications et on se bloque dans des loop infinies de cr??ation de rondes
        // alors on essaie de fix :)
        if(View.getIhm().getSelectedTournoi().getNbRound()<=round) {
            rdsuiv.setText("Classement");
            rdsuiv.setOnAction(classementView);
            round = View.getIhm().getSelectedTournoi().getNbRound(); // si un round a ??t?? g??n??r?? en trop, on revient quand m??me sur le roundMax
        }
        System.out.println(round);
        if (round == 0) round = 1;

        roundLbl.setText("Round #"+round);
        System.out.println("Round #"+round);

        List<Partie> partie = View.getIhm().getSelectedTournoi().gotRepartition(round);

        if (partie == null) {
            try {
                View.getView().setScene(MyTournoi.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return;
        };

        partieSize = partie.size();

        List<HBox> ligne = new ArrayList<>();

        int i = 0;
        int j = -1;

        partie.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getTable())));
        partie = partie.subList(View.getIhm().getCurrentPage()*9, Math.min(View.getIhm().getCurrentPage()*9+9, partieSize));

        for(Partie p : partie) {

            if (i%3 == 0) {
                j++;
                HBox hbox = new HBox();
                ligne.add(j, hbox);
            }
            Table m1 = new MyTable(p);
            m1.getTable().setText("Table" + p.getTable());
            m1.getTable().setTranslateY(10);
            m1.getChildren().add(m1.getTable());
            ligne.get(j).getChildren().add(m1);
            i++;
        }

        for(HBox hb : ligne) {
            vbox.getChildren().add(hb);
        }
    }
}
