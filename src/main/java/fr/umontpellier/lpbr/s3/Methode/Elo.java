package fr.umontpellier.lpbr.s3.Methode;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.Tournoi;

import java.util.*;

public class Elo implements Methode{
    private String nom;

    public Elo() {
        this.nom = "PerFElo";
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
        //map buchholz
        for (Joueur j : l){
            joueurs.add(j);
            map.put(j.getId(),j.nbPoint(t));
        }

        Collections.sort(joueurs, (j1, j2)->{
            if (Objects.equals(map.get(j1.getId()), map.get(j2.getId()))){
                if (j1.getElo() == j2.getElo())
                    return j1.getNom().compareTo(j2.getNom());
                else
                    return j2.getElo() - j1.getElo();
            }
            else return (int) (map.get(j1.getId())-map.get(j2.getId()));
        });

        return joueurs;
    }

    @Override
    public String toString() {
        return getNom();
    }
}
