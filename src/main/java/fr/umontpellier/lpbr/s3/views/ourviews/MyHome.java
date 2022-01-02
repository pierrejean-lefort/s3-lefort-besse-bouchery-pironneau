package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Methode.Methode;
import fr.umontpellier.lpbr.s3.Tournoi;
import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MyHome extends Home {
    public static String fxmlPath = "/fxml/homeView.fxml";
    @FXML private Button retour;
    @FXML private Button nbRoundPlus;
    @FXML private Button nbRoundMoins;
    @FXML private Button commencer;
    @FXML private Label compteur;
    @FXML private ComboBox methode;
    @FXML private TextField nomTournoi;
    private int compt;
    @FXML private Label methode1;
    @FXML private Label methode2;

    public MyHome() {

    }

    private EventHandler<ActionEvent>  nbRoundMoinsAtion = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (compt > 1){
                compt --;
                compteur.setText(""+compt+"");
            }
        }
    };



    private EventHandler<ActionEvent>  nbRoundPlusAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            compt ++;
            compteur.setText(""+compt+"");
        }
    };

    private EventHandler<ActionEvent> commencerAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            //TODO messages d'erreur.
            //            if(compt<=0||
            //                    nomTournoi.getText().isEmpty()||
            //                    !methode.getValue().toString().equals("performance élo")){
            //                System.out.println("ERROR: Work-In-Progress\nLa gestion des erreurs n'a pas été implémentée à cette partie pour le moment.\n\nVeillez a respecter la typographie des arguments passés à la vue CréationTournoi\n");
            //            }else {

            //récupération des variables
            String nomTournoiSaisi = nomTournoi.getText();
            String methodeSaisi =  methode.getValue().toString();
            methode1.setText(methodeSaisi);


            //création du tournoi
            Tournoi nvTournoi = new Tournoi();
            nvTournoi.setNom(nomTournoiSaisi);
            nvTournoi.setMethode(methodeSaisi);
            nvTournoi.setNbRound(compt);

            //enregistrement dans la bd
            HibernateUtil.save(nvTournoi);

            try {
                View.getIhm().setSelectedTournoi(nvTournoi);
                View.getView().setScene(MyTournoiInfo.class, true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    };

    private EventHandler<ActionEvent>  retourAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyTournoi.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nbRoundPlus.setOnAction(nbRoundPlusAction);
        nbRoundMoins.setOnAction(nbRoundMoinsAtion);
        commencer.setOnAction(commencerAction);
        retour.setOnAction(retourAction);
        ObservableList<Methode> methodes = FXCollections.observableArrayList(Methode.getMethodeList());
        methode.setItems(methodes);
    }
}
