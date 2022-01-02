package fr.umontpellier.lpbr.s3.Methode;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.Tournoi;

import java.util.List;

public class Elo implements Methode{
    private String nom;

    public Elo() {
        this.nom = "Elo";
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public List<Joueur> classer(Tournoi t) {
        return null;
    }
}
