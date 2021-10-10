package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.views.Home;
import fr.umontpellier.lpbr.s3.views.ourviews.MyHome;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EchecIHM extends Application {
    private Stage primaryStage;
    private Home homeView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        try {
            System.out.println("Connecting database...");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/s3", "root", "root");
            Statement stmt = con.createStatement();
            System.out.println();
            System.out.println("Connection established successfully.");

            // Create and execute an SQL statement that returns user name.
//            String SQL = "SELECT SUSER_SNAME()";
//            try (ResultSet rs = stmt.executeQuery(SQL)) {
//
//                // Iterate through the data in the result set and display it.
//                while (rs.next()) {
//                    System.out.println("user name: " + rs.getString(1));
//                }
//            }
        }
        catch (Exception e) {
        e.printStackTrace();
        }

        primaryStage.setTitle("Echec !");
        initHomeView();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void initHomeView() {
        homeView = new MyHome(this);
    }
}
