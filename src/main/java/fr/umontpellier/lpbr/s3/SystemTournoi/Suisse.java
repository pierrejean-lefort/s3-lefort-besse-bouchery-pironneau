package fr.umontpellier.lpbr.s3.SystemTournoi;

import fr.umontpellier.lpbr.s3.*;
import org.hibernate.Session;

import java.util.*;

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

    private List<Joueur> orderByNbPointetelo(){
        List<Joueur> joueurs= new ArrayList<>();
        Map<Joueur,Double> map = new HashMap<>();
        for (Participe p : t.getParticipation()){
            joueurs.add(p.getJoueur());
            map.put(p.getJoueur(),p.getJoueur().nbPoint(t));
        }
        Collections.sort(joueurs, (j1,j2)->{
            if (map.get(j1)==map.get(j2)){
                return (Integer.compare(j1.getElo(), j2.getElo()));
            }
            else return (int) (map.get(j1)-map.get(j2));
        });
        return joueurs;
    }

    //méthode pour créer une liste de joueur ayant le même score
    private List<Joueur> getJoueursScore(List<Joueur> joueurs,int score){
        List<Joueur> joueursTri = new ArrayList<>();
        for (int i = 0; i < joueurs.size(); i ++){
            if (joueurs.get(i).nbPoint(t)== score){
                joueursTri.add(joueurs.get(i));
            }
        }
        return joueursTri;
    }



    // Prérequis: tournoi avec un nombre pair de joueurs
    public List<Partie> firstRound() {
        List<Partie> parties = new ArrayList<>();
        List<Joueur> sortedElo = orderByElo();
        for(int i = 0; i < sortedElo.size()/2; i++) {
            parties.add(Partie.createPartie(t, sortedElo.get(i), sortedElo.get(sortedElo.size()/2+i), 1, "" + (i + 1)));
        }
        return parties;
    }

    //prérequis : avoir la liste de partie trier
    public List<Joueur> triNoirBlanc(List<Joueur> joueurs, int r){
        List<Joueur> listTrier = new ArrayList<>();
        Session sess = HibernateUtil.openSession();
        for (Joueur j : joueurs){
            List<Partie> parties = sess.createSQLQuery("FROM parties WHERE (joueur_blanc =:j OR joueur_noir =:j)AND tournoi =:t AND numRound =:r")
                    .setParameter("j",j)
                    .setParameter("t",t)
                    .setParameter("r",r-1)
                    .list();
            for (Partie p : parties){
                if (p.getJoueur_blanc().equals(j)){
                    listTrier.add(j);
                }
                else if (p.getJoueur_noir().equals(j)){
                    int i = listTrier.size();
                    listTrier.add(i,j);
                }
            }
        }
        return listTrier;
    }

    public List<Partie> newRound(int round) {
        List<Partie> parties = new ArrayList<>();
        List<Joueur> sortedNbPoint = orderByNbPointetelo();
        for (int i = 0; i<t.gotCurrentRound(); i++){

        }



        return parties;
    }
}