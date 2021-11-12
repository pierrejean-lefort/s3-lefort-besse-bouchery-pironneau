package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Participe;
import fr.umontpellier.lpbr.s3.Partie;
import fr.umontpellier.lpbr.s3.views.Resultat;
import fr.umontpellier.lpbr.s3.views.Table;
import fr.umontpellier.lpbr.s3.views.View;
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

    @FXML
    private Label page;

    private int partieSize;

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
            for (Node n : vbox.getChildren()) {
                for (Node nn : ((HBox)n).getChildren()) {
                    MyTable tbl = (MyTable)nn;
                    if (!tbl.isValid()) {
                        System.out.println("Toutes les tables ne sont pas validées");
                        return;
                    }
                }
            }
            if (View.getIhm().getSelectedTournoi().gotRepartition(View.getIhm().getSelectedTournoi().gotCurrentRound()+1)==null) {
                System.out.println("Pas de répartition pour le round suivant");
                return;
            }
            try {
                View.getView().setScene(MyResultat.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };





    public void initialize(URL url, ResourceBundle resourceBundle) {

        retour.setOnAction(retourAction);

        imprimer.setDisable(true);

        rdsuiv.setOnAction(roundSuiv);

        suiv.setOnAction(pageSuiv);
        prec.setOnAction(pagePrec);

        page.setText(View.getIhm().getCurrentPage()+"");

        int round = View.getIhm().getSelectedTournoi().gotCurrentRound();

        if (round == 0) round = 1;
        System.out.println(round);

        List<Partie> partie = View.getIhm().getSelectedTournoi().gotRepartition(round);

        if (partie == null) return;

        partieSize = partie.size();

        List<HBox> ligne = new ArrayList<>();

        int i = 0;
        int j = -1;

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
