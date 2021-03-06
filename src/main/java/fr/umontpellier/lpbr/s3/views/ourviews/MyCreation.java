package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.DataValidation;
import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.views.Creation;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class MyCreation extends Creation {

    public static String fxmlPath = "/fxml/creationJoueurView.fxml";

    @FXML
    private Button retour;

    @FXML
    private Button ajouterJoueur;

    @FXML
    private TextField nomText;

    @FXML
    private TextField prenomText;

    @FXML
    private TextField numLicenceText;

    @FXML
    private TextField nomClubText;

    @FXML
    private TextField eloText;

    @FXML
    private DatePicker dateNaissanceText;

    @FXML
    private MenuButton menu;

    @FXML
    private Label categorieAffichage;

    @FXML
    private Label nomErr;

    @FXML
    private Label prenomErr;

    @FXML
    private Label numLicenceErr;

    @FXML
    private Label nomClubErr;

    @FXML
    private Label eloErr;

    @FXML private Label sexErr;

    @FXML
    private Label dateNaissanceErr;

    @FXML
    private ComboBox<String> sexeText;

    @FXML private Label error;



    public MyCreation() {}

    private EventHandler<ActionEvent> retourAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                View.getView().setScene(MyJoueurSearch.class);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private EventHandler<ActionEvent> creerJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {



            String nomSaisi = nomText.getText();
            String prenomSaisi = prenomText.getText();
            String numLicenceSaisi = numLicenceText.getText();
            String nomClubSaisi = nomClubText.getText();
            String eloSaisi = eloText.getText();
            String sexeSaisi = sexeText.getValue();
            LocalDate dateNaissanceSaisi = dateNaissanceText.getValue();
            Instant instant = dateNaissanceSaisi == null ? Instant.now() : Instant.from(dateNaissanceSaisi.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            boolean isError = false;
            if (sexeSaisi == null || sexeSaisi.equals("")) {
                sexErr.setText("A remplir.");
                isError = true;
            } else {
                sexErr.setText("");
            }

            if(!DataValidation.textFieldIsNull(nomText, nomErr, "A remplir") || !DataValidation.dataLength(nomText, nomErr, "Trop long", 32) || !DataValidation.textAlphabet(nomText, nomErr, "Des lettres ?")){
                isError = true;
            }

            if(!DataValidation.textFieldIsNull(prenomText, prenomErr, "A remplir") || !DataValidation.dataLength(prenomText, prenomErr, "Trop long", 32) || !DataValidation.textAlphabet(prenomText, prenomErr, "Des lettres ?")){
                isError = true;
            }

            if(!DataValidation.textFieldIsNull(numLicenceText, numLicenceErr, "A remplir") || !DataValidation.dataLength(numLicenceText, numLicenceErr, "Trop long", 32) || !DataValidation.textNumeric(numLicenceText, numLicenceErr, "Des chiffres ?")){
                isError = true;
            }

            if(!DataValidation.textFieldIsNull(nomClubText, nomClubErr, "A remplir") || !DataValidation.dataLength(nomClubText, nomClubErr, "Trop long", 32) || !DataValidation.textAlphabet(nomClubText, nomClubErr, "Des lettres ?")){
                isError = true;
            }

            if(!DataValidation.textFieldIsNull(eloText, eloErr, "A remplir") || !DataValidation.dataLength(eloText, eloErr, "Trop long", 32) || !DataValidation.textNumeric(eloText, eloErr, "Des chiffres ?")){
                isError = true;
            }

            if(!isError) {
                Joueur joueur = new Joueur();
                joueur.setNumLicence(numLicenceSaisi);
                joueur.setNom(nomSaisi);
                joueur.setPrenom(prenomSaisi);
                joueur.setElo(Objects.equals(eloSaisi, "") ? -1 : Integer.parseInt(eloSaisi));
                joueur.setClub(nomClubSaisi);
                joueur.setDateNaissance(date);
                joueur.setSexe(sexeSaisi);
                joueur.setNationalite("fr");

                Session ses = HibernateUtil.getSessionFactory().openSession();
                ses.beginTransaction();
                ses.save(joueur);
                ses.getTransaction().commit();
                try {
                    View.getView().setScene(MyJoueurSearch.class, true);
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retour.setOnAction(retourAction);
        ajouterJoueur.setOnAction(creerJoueurAction);
        String homme = "Homme";
        String femme = "Femme";
        sexeText.getItems().addAll(homme, femme);
    }
}
