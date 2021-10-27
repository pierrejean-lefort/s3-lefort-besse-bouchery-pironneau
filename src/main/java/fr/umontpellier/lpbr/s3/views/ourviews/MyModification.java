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
import org.hibernate.query.Query;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class MyModification extends Creation {

    public static String fxmlPath = "/fxml/modificationJoueurView.fxml";

    @FXML
    private Button retour;

    @FXML
    private Button modifierJoueur;

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

    @FXML
    private Label dateNaissanceErr;

    @FXML
    private ComboBox sexeText;

    @FXML private Label error;


    public MyModification() {}

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

    private EventHandler<ActionEvent> modifierJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            String nomSaisi = nomText.getText();
            String prenomSaisi = prenomText.getText();
            String numLicenceSaisi = numLicenceText.getText();
            String nomClubSaisi = nomClubText.getText();
            String eloSaisi = eloText.getText();
            String sexeSaisi = (String)sexeText.getValue();
            LocalDate dateNaissanceSaisi = dateNaissanceText.getValue();
            Instant instant = dateNaissanceSaisi == null ? Instant.now() : Instant.from(dateNaissanceSaisi.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);


            //@TODO trouver un moyen pour que le code ci-dessous ne soit pas aussi dégueulasse.

            if(DataValidation.textFieldIsNull(nomText, nomErr, "A remplir")){
                if (DataValidation.dataLength(nomText, nomErr, "Trop long", 32)){
                    DataValidation.textAlphabet(nomText, nomErr, "Des lettres ?");
                }
            }
            if(DataValidation.textFieldIsNull(prenomText, prenomErr, "A remplir")){
                if(DataValidation.dataLength(prenomText, prenomErr, "Trop long", 32)){
                    DataValidation.textAlphabet(prenomText, prenomErr, "Des lettres ?");
                }
            }
            if(DataValidation.textFieldIsNull(numLicenceText, numLicenceErr, "A remplir")){
                if(DataValidation.dataLength(numLicenceText, numLicenceErr, "Trop long", 32)){
                    DataValidation.textNumeric(numLicenceText, numLicenceErr, "Des chiffres ?");
                }
            }
            if(DataValidation.textFieldIsNull(nomClubText, nomClubErr, "A remplir")){
                if(DataValidation.dataLength(nomClubText, nomClubErr, "Trop long", 32)){
                    DataValidation.textAlphabet(nomClubText, nomClubErr, "Des lettres ?");
                }
            }
            if(DataValidation.textFieldIsNull(eloText, eloErr, "A remplir")){
                if(DataValidation.dataLength(eloText, eloErr, "Trop long", 32)){
                    DataValidation.textNumeric(eloText, eloErr, "Des chiffres ?");
                }
            }

            if(
                !DataValidation.textFieldIsNull(nomText, nomErr, "A remplir")||
                !DataValidation.dataLength(nomText, nomErr, "Trop long", 32)||
                !DataValidation.textAlphabet(nomText, nomErr, "Des lettres ?")||

                !DataValidation.textFieldIsNull(prenomText, prenomErr, "A remplir")||
                !DataValidation.dataLength(prenomText, prenomErr, "Trop long", 32)||
                !DataValidation.textAlphabet(prenomText, prenomErr, "Des lettres ?")||

                !DataValidation.textFieldIsNull(numLicenceText, numLicenceErr, "A remplir")||
                !DataValidation.dataLength(numLicenceText, numLicenceErr, "Trop long", 32)||
                !DataValidation.textNumeric(numLicenceText, numLicenceErr, "Des Chiffres ?")||

                !DataValidation.textFieldIsNull(nomClubText, nomClubErr, "A remplir")||
                !DataValidation.dataLength(nomClubText, nomClubErr, "Trop long", 32)||
                !DataValidation.textAlphabet(nomClubText, nomClubErr, "Des lettres ?")||

                !DataValidation.textFieldIsNull(eloText, eloErr, "A remplir")||
                !DataValidation.dataLength(eloText, eloErr, "Trop long", 32)||
                !DataValidation.textNumeric(eloText, eloErr, "Des chiffres ?")){
                System.out.println("je suis bloké");
            }else {

                Joueur joueur = View.getIhm().getSelectedJoueur();
                joueur.setNumLicence(numLicenceSaisi);
                joueur.setNom(nomSaisi);
                joueur.setPrenom(prenomSaisi);
                joueur.setElo(Objects.equals(eloSaisi, "") ? -1 : Integer.parseInt(eloSaisi));
                joueur.setClub(nomClubSaisi);
                joueur.setDateNaissance(date);
                joueur.setSexe(sexeSaisi);
                joueur.setNationalite("fr");

                //            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                //            Validator validator = factory.getValidator();
                //            Validator v = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
                //
                //            Set<ConstraintViolation<Joueur>> violations = v.validate(joueur);
                //            for (ConstraintViolation<Joueur> violation : violations) {
                //                System.out.println(violation.getMessage());
                //            }

                Session sess = HibernateUtil.openSession();
                Query query = sess.createQuery(" UPDATE joueurs SET club = :club, dateNaissance = :dateNaissance,elo= :elo ,nationalite= :nationalite ,nom= :nom ,numLicence= :numLicence ,prenom= :prenom ,sexe= :sexe " + " WHERE id= :id");
                query.setParameter("id", joueur.getId());
                query.setParameter("club", joueur.getClub());
                query.setParameter("dateNaissance", joueur.getDateNaissance());
                query.setParameter("elo", joueur.getElo());
                query.setParameter("nationalite", joueur.getNationalite());
                query.setParameter("nom", joueur.getNom());
                query.setParameter("numLicence", joueur.getNumLicence());
                query.setParameter("prenom", joueur.getPrenom());
                query.setParameter("sexe", joueur.getSexe());
                query.executeUpdate();
                HibernateUtil.closeSession(sess);
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
        Joueur joueur = View.getIhm().getSelectedJoueur();
        retour.setOnAction(retourAction);
        modifierJoueur.setOnAction(modifierJoueurAction);
        sexeText.getItems().addAll("Homme", "Femme");
        nomText.setText(joueur.getNom());
        prenomText.setText(joueur.getPrenom());
        numLicenceText.setText(joueur.getNumLicence());
        nomClubText.setText(joueur.getClub());
        eloText.setText(Integer.toString(joueur.getElo()));
        LocalDate localDate = Instant.ofEpochMilli(joueur.getDateNaissance().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        dateNaissanceText.setValue(localDate);
        sexeText.setValue(joueur.getSexe());
    }
}
