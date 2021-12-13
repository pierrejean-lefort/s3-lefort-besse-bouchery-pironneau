package fr.umontpellier.lpbr.s3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Methode {

    public String nom;

    public Methode() {

    }

    public Methode(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    @Override
    public String toString() {
        return nom;
    }

    public static ObservableList<Methode> getMethodeList() {
        Methode elo = new Methode("PerFElo");
        Methode buchholz = new Methode("Bucc");

        ObservableList<Methode> list = FXCollections.observableArrayList(elo, buchholz);
        return list;
    }
}
