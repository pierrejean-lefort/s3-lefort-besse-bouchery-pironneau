package fr.umontpellier.lpbr.s3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Methode {

    public String nom;
    public String code;

    public Methode() {

    }

    public Methode(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return nom;
    }

    public static ObservableList<Methode> getMethodeList() {
        Methode elo = new Methode("PerFElo", "Elo");
        Methode buchholz = new Methode("Bucc", "Buch");

        ObservableList<Methode> list = FXCollections.observableArrayList(elo, buchholz);
        return list;
    }
}
