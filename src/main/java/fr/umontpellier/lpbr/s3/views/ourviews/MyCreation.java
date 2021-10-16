package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.views.Creation;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class MyCreation extends Creation {

    public static String fxmlPath = "/fxml/creationJoueurView.fxml";

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
    private ComboBox sexeText;

    @FXML private Label error;


    public MyCreation() {}

    private EventHandler<ActionEvent> creerJoueurAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            String nomSaisi = nomText.getText();
            String prenomSaisi = prenomText.getText();
            String numLicenceSaisi = numLicenceText.getText();
            String nomClubSaisi = nomClubText.getText();
            String eloSaisi = eloText.getText();
            String sexeSaisi = (String)sexeText.getValue();
            LocalDate dateNaissanceseSaisi = dateNaissanceText.getValue();
            Instant instant = dateNaissanceseSaisi == null ? Instant.now() : Instant.from(dateNaissanceseSaisi.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);




            Joueur joueur = new Joueur();
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

//            Session ses = HibernateUtil.getSessionFactory().openSession();
//            ses.beginTransaction();
//            ses.save(joueur);
//            ses.getTransaction().commit();
//            try {
//                View.getView().setScene(MyHome.class);
//            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ajouterJoueur.setOnAction(creerJoueurAction);
        sexeText.getItems().addAll("Homme", "Femme");
    }
}
