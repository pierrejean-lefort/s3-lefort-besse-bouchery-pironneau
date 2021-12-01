package fr.umontpellier.lpbr.s3.SystemTournoi;

import fr.umontpellier.lpbr.s3.*;
import org.hibernate.Session;

import java.util.*;

public class Suisse extends SystemTournoi {
    private Tournoi t;
    private Session sess;

    public Suisse(Tournoi t) {
        this.t = t;
        this.sess = HibernateUtil.openSession();
    }

    private List<Joueur> orderByElo() {
        List<Joueur> joueurs = new ArrayList<>();
        for (Participe p : t.getParticipation()) {
            joueurs.add(p.getJoueur());
        }
        joueurs.sort((j1 , j2) -> {
            if (j1.getElo() == j2.getElo())
                return j1.getNom().compareTo(j2.getNom());
            else
                return j2.getElo() - j1.getElo();
        });
        return joueurs;
    }

    private List<Joueur> orderByNbPointetelo(List<Joueur> l){
        List<Joueur> joueurs= new ArrayList<>();
        Map<Integer,Double> map = new HashMap<>();
        for (Joueur j : l){
            joueurs.add(j);
            map.put(j.getId(),j.nbPoint(t));
        }
        Collections.sort(joueurs, (j1,j2)->{
            if (Objects.equals(map.get(j1.getId()), map.get(j2.getId()))){
                return j2.getElo() - j1.getElo();
            }
            else return (int) (map.get(j1.getId())-map.get(j2.getId()));
        });
        return joueurs;
    }

    // get les joueurs contre qui il n'a jamais joué
    public List<Joueur> getNewJoueurs(Joueur j, List<Joueur> l) {
        Set<Partie> list = t.getParties();
        list.removeIf((p) -> p.getJoueur_noir() != j && p.getJoueur_blanc() != j);

        List<Joueur> adversaires = new ArrayList<>();
        for (Partie p : list) {
            if (p.getJoueur_blanc().equals(j)) {
                adversaires.add(p.getJoueur_noir());
            } else {
                adversaires.add(p.getJoueur_blanc());
            }
        }

        l.removeIf(adversaires::contains);

        return l;
    };

    public List<Joueur> getSameScore(Joueur j, List<Joueur> l) {
        double score = j.nbPoint(t);

        List<Joueur> sameScore = orderByNbPointetelo(l);
        double finalScore = score;
        sameScore.removeIf(j2 -> j2.nbPoint(t) != finalScore);
        while(sameScore.size() == 0) {
            System.out.println(score);
            if (score - 0.5 < 0) {
                return null;
            }
            score = score - 0.5;
            sameScore = orderByNbPointetelo(l);
            double finalScore1 = score;
            sameScore.removeIf(j2 -> j2.nbPoint(t) != finalScore1);
        }

        return sameScore;
    }

    // avoir les joueurs de couleur différent au ronde
    public List<Joueur> getDiffColor(List<Joueur> l, int round, boolean wasBlanc) {
        List<Joueur> r = new ArrayList<>();
        for (Joueur j1 : l) {
            boolean wasJ1Blanc = j1.wasBlanc(t, round);

            if (wasBlanc == wasJ1Blanc) continue;
            r.add(j1);
        }

        return r;
    }

    public Joueur getAdversaire(Joueur j, List<Joueur> possible, int round, boolean wasBlanc) { //là on commence à avoir mal à la tête
        List<Joueur> possible2 = new ArrayList<>(possible); //liste avec tous les joueurs non appariés

        List<Joueur> jamaisJoue = getNewJoueurs(j, possible2); //liste des joueurs contre qui il a jamais joué

        List<Joueur> deCouleurDiff = getDiffColor(jamaisJoue, round - 1, wasBlanc); //liste des joueurs de couleur opposée au round n-1

        List<Joueur> mmPts = getSameScore(j, deCouleurDiff);
        if (mmPts == null) {
            mmPts = getSameScore(j, jamaisJoue);
        }

        if (mmPts == null) {
            return null;
        }

        return mmPts.get(mmPts.size()/2);
    }

    // Prérequis: tournoi avec un nombre pair de joueurs
    public List<Partie> firstRound() {
        List<Partie> parties = new ArrayList<>();
        List<Joueur> sortedElo = orderByElo();
        boolean j = true;
        for(int i = 0; i < sortedElo.size()/2; i++) {
            Joueur jb = sortedElo.get(i);
            Joueur jn = sortedElo.get(sortedElo.size()/2+i);
            EchecIHM.taskSetProgress((i/(double) ((sortedElo.size()/2)-1))*100);
            parties.add(Partie.createPartie(t, j ? jb : jn, j ? jn : jb, 1, "" + (i + 1)));
            j = !j;
        }

        HibernateUtil.closeSession(sess);

        return parties;
    }

    public List<Partie> newRound(int round) {
        List<Partie> parties = new ArrayList<>();

        List<Joueur> aApparai = new ArrayList<>();
        Set<Participe> participes = t.getParticipation();
        for (Participe p : participes) {
            aApparai.add(p.getJoueur());
        }

        int i = 1;
        for (Participe p : participes) {
            Joueur j = p.getJoueur();
            if (!aApparai.contains(j)) // sert à rien ???
                continue;

            aApparai.remove(j); // on enlève le joueur que l'on traite de la liste des joueurs avec qui il peut etre apparié


            boolean wasBlanc = j.wasBlanc(t, round-1); // on regarde si il a été blanc au round précédent

            Joueur adversaire = getAdversaire(j, aApparai, round, wasBlanc); //on appelle une fonction giga cursed pour avoir son adversaire
            if (adversaire == null) { //si la fonction cursed a planté, c'est l'alarme, c'est la galère
                System.out.println("Pas d'adversaire trouvé pour" + j.getId() + " !");
                aApparai.add(j); //le joueur que l'on voulait apparier est relâché dans la fosse
                continue; //on continue si ça plante ???
            }
            System.out.println(adversaire);  //si ça a planté rip, sinon affiche adv j

            aApparai.remove(adversaire); // l'adversaire n'est plus à apparier

            double size = participes.size(); //formules magiques pour faire une barre de progression
            double index = i*2;
            EchecIHM.taskSetProgress((index/size)*100);
            parties.add(Partie.createPartie(t, wasBlanc ? adversaire : j, wasBlanc ? j : adversaire, round, "" + i));
            i++;
        }

        HibernateUtil.closeSession(sess);

        return parties;
    }
}