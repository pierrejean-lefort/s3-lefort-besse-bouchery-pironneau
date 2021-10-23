package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.DataValidation;
import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Tournoi;
import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Session;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;


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
            //récupération des variables
            String nomTournoiSaisi = nomTournoi.getText();
            String methodeSaisi = (String)methode.getValue();


            //création du tournoi
            Tournoi nvTournoi = new Tournoi();
            nvTournoi.setNom(nomTournoiSaisi);
            nvTournoi.setMethode(methodeSaisi);
            nvTournoi.setNbRound(compt);

            //message d'erreur



            //enregistrement dans la bd
            Session ses = HibernateUtil.getSessionFactory().openSession();
            ses.beginTransaction();
            ses.save(nvTournoi);
            ses.getTransaction().commit();
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
        System.out.println("Initialized");
        methode.getItems().addAll("performance élo","","");





    }
}
