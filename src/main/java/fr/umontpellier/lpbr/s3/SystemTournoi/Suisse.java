package fr.umontpellier.lpbr.s3.SystemTournoi;

import fr.umontpellier.lpbr.s3.Joueur;
import fr.umontpellier.lpbr.s3.Participe;
import fr.umontpellier.lpbr.s3.Partie;
import fr.umontpellier.lpbr.s3.Tournoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Suisse extends SystemTournoi {
    private Tournoi t;

    public Suisse(Tournoi t) {
        this.t = t;
    }

    private List<Joueur> orderByElo() {
        List<Joueur> joueurs = new ArrayList<>();
        for (Participe p : t.getParticipation()) {
            joueurs.add(p.getJoueur());
        }
        joueurs.sort(Comparator.comparingInt(Joueur::getElo));
        System.out.println(joueurs);
        return joueurs;
    }

    // Prérequis: tournoi avec un nombre pair de joueurs
    public List<Partie> firstRound() {
        List<Partie> parties = new ArrayList<>();
        List<Joueur> sortedElo = orderByElo();
        for(int i = 0; i < sortedElo.size()/2; i++) {
            parties.add(Partie.createPartie(t, sortedElo.get(i), sortedElo.get(sortedElo.size()-1-i), 1, "" + (i + 1)));
        }
        return parties;
    }

    public List<Partie> newRound(int round) {
        return null;
    }
}