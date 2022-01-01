package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.views.Appar;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyAppar extends Appar {

    public static String fxmlPath = "/fxml/appar.fxml";

    private int rounde;

    @FXML
    private Button retour;

    @FXML
    private Button precedent;

    @FXML
    private Button suivant;

    @FXML
    private Button search;

    @FXML
    private Button search1;

    @FXML
    private Text round;

    @FXML
    private Text error;

    @FXML
    private Text nom;

    @FXML
    private Text prenom;

    @FXML
    private Text sexe;

    @FXML
    private Text numLicense;

    @FXML
    private Text elo;

    @FXML
    private Text points;

    @FXML
    private Text nom1;

    @FXML
    private Text prenom1;

    @FXML
    private Text sexe1;

    @FXML
    private Text numLicense1;

    @FXML
    private Text elo1;

    @FXML
    private Text points1;

    @FXML
    private CheckBox blanc;

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


    private EventHandler<ActionEvent> searchAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getIhm().setToAssign(1);
                View.setLastScene(MyAppar.class);
                View.getView().setScene(MyJoueurSearch.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> searchAction1 = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getIhm().setToAssign(0);
                View.setLastScene(MyAppar.class);
                View.getView().setScene(MyJoueurSearch.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> precedentAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                Joueur prec = View.getIhm().precedentAppariment();
                if (prec == null) {
                    return;
                }
                View.getIhm().setSelectedJoueurAppar(prec);
                View.getView().setScene(MyAppar.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> suivantAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                Joueur j1 = View.getIhm().getSelectedJoueurAppar();
                Joueur j2 = View.getIhm().getSelectedJoueur();
                if (j2 == null) {
                    error.setText("Vous devez sélectionner un joueur");
                    return;
                }

                View.getIhm().appaire(blanc.isSelected() ? j2 : j1, blanc.isSelected() ? j1 : j2, rounde);

                Joueur next = View.getIhm().suivantAppariement();
                if (next == null) {
                    View.getIhm().setLoading();
                    return;
                }
                View.getIhm().setSelectedJoueur(null);
                View.getIhm().setSelectedJoueurAppar(next);
                View.getView().setScene(MyAppar.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    public void setJoueurInfo1(Joueur j) {
        nom1.setText(j != null ? j.getNom() : "");
        prenom1.setText(j != null ? j.getPrenom() : "");
        sexe1.setText(j != null ? j.getSexe() : "");
        numLicense1.setText(j != null ? j.getNumLicence() : "");
        elo1.setText(""+(j != null ? j.getElo() : ""));
        points1.setText(""+(j != null ? j.nbPoint(View.getIhm().getSelectedTournoi()) : ""));
    }

    public void setJoueurInfo(Joueur j) {
        nom.setText(j != null ? j.getNom() : "");
        prenom.setText(j != null ? j.getPrenom() : "");
        sexe.setText(j != null ? j.getSexe() : "");
        numLicense.setText(j != null ? j.getNumLicence() : "");
        elo.setText(""+(j != null ? j.getElo() : ""));
        points.setText(""+(j != null ? j.nbPoint(View.getIhm().getSelectedTournoi()) : ""));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retour.setOnAction(retourAction);
        precedent.setOnAction(precedentAction);
        suivant.setOnAction(suivantAction);
        search.setOnAction(searchAction);
        search1.setOnAction(searchAction1);
        error.setText("");
        rounde = View.getIhm().getSelectedTournoi().gotCurrentRound();
        if (rounde == 0) rounde = 1;
        round.setText(""+rounde);

        Joueur j = View.getIhm().getSelectedJoueurAppar();
        if (j == null) {
            error.setText("Error, joueur à appairer non-existant !");
            return;
        }

        setJoueurInfo(j);
        setJoueurInfo1(View.getIhm().getSelectedJoueur());
    }
}
