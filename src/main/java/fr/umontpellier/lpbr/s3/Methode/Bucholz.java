package fr.umontpellier.lpbr.s3.Methode;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.Tournoi;

import java.util.*;

public class Bucholz implements Methode {
    private String nom;

    public Bucholz() {
        this.nom = "Bucc";
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public List<Joueur> classer(Tournoi t) {

        List<Joueur> l = t.gotJoueurs();
        List<Joueur> joueurs= new ArrayList<>();
        Map<Integer,Double> map = new HashMap<>();
        Map<Integer, Double> mapBuch = new HashMap<>();

        for (Joueur j : l){
            joueurs.add(j);
            mapBuch.put(j.getId(),j.nbPointBuch(t));
            map.put(j.getId(),j.nbPoint(t));
        }

        joueurs.sort((j1, j2) -> {
            if (Objects.equals(map.get(j1.getId()), map.get(j2.getId()))) {
                if (Objects.equals(mapBuch.get(j1.getId()), mapBuch.get(j2.getId())))
                    return j1.getNom().compareTo(j2.getNom());
                else
                    return (int) (mapBuch.get(j1.getId()) - mapBuch.get(j2.getId()));
            } else return (int) (map.get(j1.getId()) - map.get(j2.getId()));
        });

        return joueurs;
    }

    @Override
    public String toString() {
        return getNom();
    }
}
