package fr.umontpellier.lpbr.s3.Methode;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.Tournoi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public interface Methode {

    String getNom();

    List<Joueur> classer(Tournoi t);

    public static List<Methode> getMethodeList() {
        Methode elo = new Elo();
        Methode buchholz = new Bucholz();

        List<Methode> list = FXCollections.observableArrayList(elo, buchholz);
        return list;
    }
}
