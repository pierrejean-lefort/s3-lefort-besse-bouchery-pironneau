package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.HibernateUtil;
import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.views.Creation;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

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
            System.out.println(sexeSaisi);
            if (nomSaisi.equals("") || prenomSaisi.equals("") || numLicenceSaisi.equals("") || nomClubSaisi.equals("") || eloSaisi.equals("") || dateNaissanceseSaisi == null || sexeSaisi.equals("")) {
                error.setText("Un champs n'est pas valide");
                return;
            }
            Instant instant = Instant.from(dateNaissanceseSaisi.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);




            Joueur joueur = new Joueur();
            joueur.setNumLicence(numLicenceSaisi);
            joueur.setNom(nomSaisi);
            joueur.setPrenom(prenomSaisi);
            joueur.setElo(Integer.parseInt(eloSaisi));
            joueur.setClub(nomClubSaisi);
            joueur.setDateNaissance(date);
            joueur.setSexe(sexeSaisi);
            joueur.setNationalite("fr");
            Session ses = HibernateUtil.getSessionFactory().openSession();
            ses.beginTransaction();
            ses.save(joueur);
            ses.getTransaction().commit();
            try {
                View.getView().setScene("fr.umontpellier.lpbr.s3.views.ourviews.MyHome");
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ajouterJoueur.setOnAction(creerJoueurAction);
        sexeText.getItems().addAll("Homme", "Femme");
    }
}
