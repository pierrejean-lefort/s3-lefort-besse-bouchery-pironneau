package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.ourviews.MyHome;
import javafx.application.Application;
import javafx.stage.Stage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EchecIHM extends Application {
    private Stage primaryStage;
    private Home homeView;
    SessionFactory sessionFactory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

//        System.out.println(getClass().getResource("fxml/"));

//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure(getClass().getResource("hibernate.cfg.xml"))
//                .build();
//
//        try {
//            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//
//            testDB();
//        } catch (Exception e) {
//            StandardServiceRegistryBuilder.destroy( registry );
//            System.out.println(e.getMessage());
//        }

        primaryStage.setTitle("Echec !");
        initHomeView();
    }

    public void testDB() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        List<Joueur> result = session.createQuery("from joueurs", Joueur.class).list();
//
//        result.forEach(person -> {
//            System.out.println(person.getNumLicence());
//        });
//
//        session.getTransaction().commit();
//        session.close();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void initHomeView() {
        homeView = new MyHome(this);
    }
}
