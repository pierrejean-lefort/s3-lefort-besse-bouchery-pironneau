package fr.umontpellier.lpbr.s3.SystemTournoi;

import fr.umontpellier.lpbr.s3.*;
import org.hibernate.Session;

import java.util.*;

public class SuisseComplet extends SystemTournoi{
    private Tournoi t;
    private Session sess;

    public SuisseComplet(Tournoi t) {
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

    private List<Joueur> orderByNbPointeteloJ(List<Joueur> l){
        List<Joueur> joueurs = new ArrayList<>();
        Map<Integer,Double> map = new HashMap<>();
        for (Joueur j : l){
            joueurs.add(j);
            map.put(j.getId(),j.nbPoint(t));
        }
        Collections.sort(joueurs, (j1, j2)->{
            if (Objects.equals(map.get(j1.getId()), map.get(j2.getId()))){ //map magie noire pour trier la liste
                return j2.getElo() - j1.getElo();
            }
            else return (int) (map.get(j1.getId())-map.get(j2.getId()));
        });
        return joueurs;
    }

    private List<Participe> orderByNbPointeteloP(List<Participe> l){
        List<Participe> joueurs = new ArrayList<>();
        Map<Integer,Double> map = new HashMap<>();
        for (Participe j : l){
            joueurs.add(j);
            map.put(j.getId(),j.getJoueur().nbPoint(t));
        }
        Collections.sort(joueurs, (j1, j2)->{
            if (Objects.equals(map.get(j1.getId()), map.get(j2.getId()))){ //map magie noire pour trier la liste
                return j2.getJoueur().getElo() - j1.getJoueur().getElo();
            }
            else return (int) (map.get(j1.getId())-map.get(j2.getId()));
        });
        return joueurs;
    }

    // get les joueurs contre qui il n'a jamais jou??
    public List<Joueur> getNewJoueursJ(Joueur j, List<Joueur> l) {
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
    }

    public List<Participe> getNewJoueursP(Participe p1, List<Participe> l) {
        Set<Partie> list = t.getParties();
        list.removeIf((p) -> p.getJoueur_noir() != p1.getJoueur() && p.getJoueur_blanc() != p1.getJoueur());

        List<Joueur> adversaires = new ArrayList<>();
        for (Partie p : list) {
            if (p.getJoueur_blanc().equals(p1.getJoueur())) {
                adversaires.add(p.getJoueur_noir());
            } else {
                adversaires.add(p.getJoueur_blanc());
            }
        }

        l.removeIf(adversaires::contains);

        return l;
    }

    //renvoie si deux joueurs ont d??j?? jou?? ensemble
    public boolean alreadyPlayedWith(Participe p1, Participe p2) {
        List<Participe> l = new ArrayList<>();
        l.add(p2);
        List<Participe> adv = getNewJoueursP(p1, l);
        if (adv.contains(p2)) return false;
        else {
            return true;
        }
    }

    public List<Participe> getSameScore(Participe j, List<Participe> l) {
        double score = j.getJoueur().nbPoint(t);

        List<Participe> sameScore = orderByNbPointeteloP(l);
        double finalScore = score;
        sameScore.removeIf(j2 -> j2.getJoueur().nbPoint(t) != finalScore);

        return sameScore;
    }

    public List<Joueur> getSameScore(Joueur j, List<Joueur> l) {
        double score = j.nbPoint(t);

        List<Joueur> sameScore = orderByNbPointeteloJ(l);
        double finalScore = score;
        sameScore.removeIf(j2 -> j2.nbPoint(t) != finalScore);

        return sameScore;
    }

    // avoir les joueurs de couleur diff??rent au ronde
    public List<Joueur> getDiffColor(List<Joueur> l, int round, boolean wasBlanc) {
        List<Joueur> r = new ArrayList<>();
        for (Joueur j1 : l) {
            boolean wasJ1Blanc = j1.wasBlanc(t, round);

            if (wasBlanc == wasJ1Blanc) continue;
            r.add(j1);
        }

        return r;
    }

    public Joueur getAdversaire(Joueur j, List<Joueur> possible, int round, boolean wasBlanc) { //l?? on commence ?? avoir mal ?? la t??te
        List<Joueur> possible2 = new ArrayList<>(possible); //liste avec tous les joueurs non appari??s

        List<Joueur> jamaisJoue = getNewJoueursJ(j, possible2); //liste des joueurs contre qui il a jamais jou??

        List<Joueur> deCouleurDiff = getDiffColor(jamaisJoue, round - 1, wasBlanc); //liste des joueurs de couleur oppos??e au round n-1

        List<Joueur> mmPts = getSameScore(j, deCouleurDiff);
        if (mmPts == null) {
            mmPts = getSameScore(j, jamaisJoue);
        }

        if (mmPts == null) {
            return null;
        }

        return mmPts.get(mmPts.size()/2);
    }

    // Pr??requis: tournoi avec un nombre pair de joueurs
    public List<Partie> firstRound() { //todo: mettre ?? jour les attributs des Participe (joueurs)
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
        t.setParties(new HashSet<>(parties));
        HibernateUtil.closeSession(sess);

        return parties;
    }

    public boolean appariementPossible(List<Participe> s1, List<Participe> s2) {
        //on essaie d'apparier le premier du groupe S1 avec le premier de S2 etc...
        //check if they already played together
        for (int i = 0; i < s1.size(); i++) {
            if (alreadyPlayedWith(s1.get(i), s2.get(i))) return false;
        }
        return true;
    }

    /**
     * penser ?? incr??menter numPartie
     * fonction primordiale avec appariementPossible(), elle g??re les couleurs des joueurs
     *
     * @return null si un des joueurs a un probl??me PAS POUR L'INSTANT
     **/
    public List<Partie> appariementCouleur(int numPartieDebut, int round, List<Participe> s1, List<Participe> s2) {
        //on cr??e les parties
        List<Partie> parties = new ArrayList<>();
        for(int i = 0; i < s1.size(); i++){
            //si le premier joueur ??tait blanc et pas le second (parfait) //todo: impl??menter les test sur la couleurPref
            if(s1.get(i).getJoueur().wasBlanc(t, round) && !s2.get(i).getJoueur().wasBlanc(t, round)){
                Partie p = Partie.createPartie(t,s2.get(i).getJoueur(),s1.get(i).getJoueur(),round, ""+numPartieDebut);
                parties.add(p);
                numPartieDebut++;
            }//l'inverse //todo: impl??menter les test sur la couleurPref
            else if(!s1.get(i).getJoueur().wasBlanc(t, round) && s2.get(i).getJoueur().wasBlanc(t, round)){
                Partie p = Partie.createPartie(t,s1.get(i).getJoueur(),s2.get(i).getJoueur(),round, ""+numPartieDebut);
                parties.add(p);
                numPartieDebut++;
            }//si les deux ??taient blancs //todo: impl??menter les test sur la couleurPref
            else if(s1.get(i).getJoueur().wasBlanc(t, round) && s2.get(i).getJoueur().wasBlanc(t, round)){
                //As a temporary workaround, we arbitrarily make the S2 player play the same color again
                Partie p = Partie.createPartie(t,s2.get(i).getJoueur(),s1.get(i).getJoueur(),round, ""+numPartieDebut);
                parties.add(p);
                numPartieDebut++;
            }//si les deux ??taient noirs //todo: impl??menter les test sur la couleurPref
            else{
                //As a temporary workaround, we arbitrarily make the S2 player play the same color again
                Partie p = Partie.createPartie(t,s1.get(i).getJoueur(),s2.get(i).getJoueur(),round, ""+numPartieDebut);
                parties.add(p);
                numPartieDebut++;
            }
        }
        return parties;
    }

  /*  public int calculX(List<Participe> joueurs){
        int b = 0; //nombre de joueurs ayant une couleur pr??f??rencielle blanche
        int n = 0; //nombre de joueurs ayant une couleur pr??f??rentielle noire
        int q = (int) Math.ceil(joueurs.size()/2.0); //moiti?? du nombre de joueurs du niveau de points ,arrondie au nombre entier sup??rieur

        for(Participe p : joueurs){
            if(p.getCouleurPref()>0) b++;
            else if(p.getCouleurPref()<0) n++;
        }
        if(n>b) return n-q;
        else return b-q;
    }*/

    /** la fonction ne met pas ?? jour l'attribut flottant des joueurs de niveaux diff??rents
     *
     * @param participants liste ?? partir de laquelle faire le groupe de niveau
     * @param option 0 normal, 1 tousDescendants , 2 fusion des deux derniers groupes
     * @return liste de joueurs du m??me groupe de niveau
     */
    public List<Participe> getNivPts(List<Participe> participants, int option){
        Participe p1 = participants.get(0);
        List<Participe> nivPts = getSameScore(p1, participants);

        if(option==0) {
            if (nivPts.size()<2){
                //p1.setFlotteur(p1.getFlotteur()-1); todo: un jour peut ??tre
                nivPts = getSameScore(participants.get(1),participants);
                nivPts.add(p1);
                nivPts = orderByNbPointeteloP(nivPts);
            }
        }
        return nivPts;
    }

    /** newroundAux()
     * tentative de fonction r??cursive cr??ant l'appariement de la ronde suivante
     * les List pass??es ?? la fonction vont ??tre modifi??es (fournir des copies)
     * @param parties liste dans laquelle l'algorithme va enregistrer les appariements
     * @param participants joueurs restant ?? apparier
     * @param round numRound en cours d'appariement
     * @param numPartie indice de la premiere partie ?? apparier
     * @return les parties ?? jouer de la round suivantes
     */
    public boolean newRoundAux(List<Partie> parties, List<Participe> participants, int round, int numPartie, int totalSize){
        Set<Partie> existe = new HashSet<>(t.getParties());
        //todo: faire de cet algo un truc qui fonctionne
        //indice de la partie
        //on trie les joueurs
        participants = orderByNbPointeteloP(participants);
        //cas de base chelou
        if(participants.isEmpty()) return true;

        //cas de base (dernier groupe de points)
        t.setParties(existe);
        if(participants.get(0).getJoueur().nbPoint(t)==0){
            if (participants.size()<2) return false;
            List<Participe> nivPts = new ArrayList<>(participants);

            //cas appariement homog??ne
            if(nivPts.size()%2==0){ //pr??requis nombre de joueurs pairs, on devrait pouvoir g??n??rer au moins la 2nd ronde avec ??a :')
                //initialisation des sous groupes S1 et S2
                List<Participe> S1 = new ArrayList<>();
                List<Participe> S2 = new ArrayList<>();
                for(int i = 0; i < nivPts.size()/2; i++){
                    S1.add(nivPts.get(i));
                    S2.add(nivPts.get(i+nivPts.size()/2-1));
                }

                //calcul de x suivant la r??gle A8 (nombre d'appariement sans couleurs)
                //int x=calculX(nivPts);

                //calcul de p suivant la r??gle A6 (nombre de joueur de S1)
                int p=S1.size();

                //on pr??pare les permutations de S2
                List<List<Participe>> permuS2 = calculPermu(S2.size(), S2);

                int k = 0; //indice permutation en cours de test
                //on essaie d'apparier le premier du groupe S1 avec le premier de S2 etc...
                boolean testingPermutations = true;
                while(testingPermutations) {
                    if (!appariementPossible(S1, S2)) { //on test si l'appariement est possible, si non, on permute S2
                        S2 = permuS2.get(k);
                        if (k < permuS2.size() - 1) {        //si il reste des permutations ?? essayer on continue
                            k++;
                            continue;
                        }
                        return false;                  //si aucune permutation dans S2 n'a donn?? de solution, on return false (C13)
                        // l'algo essayera une autre combinaison (recursivit??)
                        //todo: enlever le return false au moment de l'ajout des echanges
                    }
                    testingPermutations = false; //si on a un appariement on quitte la boucle
                }
                EchecIHM.taskSetProgress(((double) p / totalSize) * 100);
                //todo: tester les ??changes entre S1 et S2
                //si on arrive l?? c'est que on a reussi ?? faire des appariements, on ajoute les parties et on return true
                List<Partie> resultatAppariement = appariementCouleur(numPartie,round,S1, S2); //je fais comme si c'??tait bon
                parties.addAll(resultatAppariement);
                return true;
            }
        }

        //cas r??cursif todo: le cas r??cursif...
        else{
            //objectif: obtenir le premier nivPts (groupe de m??me score, qu'il soit homog??ne ou h??t??rog??ne)
            List<Participe> nivPts = getNivPts(participants,0);

            //objectif: retirer ce groupe des joueurs ?? apparier
            for (Participe p:
                 nivPts) {
                participants.remove(p);
            }

            //cas d'un appariement simple homog??ne
            if(nivPts.size()%2==0){//pr??requis nombre de joueurs pairs, on devrait pouvoir g??n??rer au moins la 2nd ronde avec ??a :')
                //initialisation des sous groupes S1 et S2
                List<Participe> S1 = new ArrayList<>();
                List<Participe> S2 = new ArrayList<>();
                for(int i = 0; i < nivPts.size()/2; i++){
                    S1.add(nivPts.get(i));
                    S2.add(nivPts.get(i+nivPts.size()/2-1));
                }

                //calcul de x suivant la r??gle A8 (nombre d'appariement sans couleurs)
                //int x=calculX(nivPts);

                //calcul de p suivant la r??gle A6 (nombre de joueur de S1)
                int p=S1.size();

                //on pr??pare les permutations de S2
                List<List<Participe>> permuS2 = calculPermu(S2.size(), S2);

                //indice permutation en cours de test
                int k = 0;

                //on essaie d'apparier le premier du groupe S1 avec le premier de S2 etc...
                boolean testingPermutations = true;

                while(testingPermutations) {
                    if (!appariementPossible(S1, S2)) { //on test si l'appariement est possible, si non, on permute S2
                        if (k < permuS2.size()) {
                            S2 = permuS2.get(k);//si il reste des permutations ?? essayer on continue
                            k++;
                            continue;
                        }
                        return false;                  //si aucune permutation dans S2 n'a donn?? de solution, on return false (C13)
                        // l'algo essayera une autre combinaison (recursivit??)
                        //todo: enlever le return false au moment de l'ajout des echanges
                    }
                    //List ?? valider de parties
                    List<Partie> partiesTemp = new ArrayList<>();
                    List<Participe> participantsTemp = new ArrayList<>(participants);
                    int faking = numPartie+ S1.size();

                    //APPEL A LA FONCTION RECURSIVE ULTRA LOURDE
                    if(newRoundAux(partiesTemp,participantsTemp,round,faking, totalSize)){
                        parties.addAll(partiesTemp);
                        break;
                    }
                    //si l'appel r??cursif a retourn?? false (??chec d'appariement) alors on continue de: modifier S2 , ??changer des joueurs , faire flotter des joueurs etc.
                    if (k < permuS2.size()) {
                        S2 = permuS2.get(k);//si il reste des permutations ?? essayer on continue
                        k++;
                        continue;
                    }
                    return false;                  //si aucune permutation dans S2 n'a donn?? de solution, on return false (C13) todo:faire flotter le/les joueurs emp??chant l'appariement
                }
                EchecIHM.taskSetProgress(((double) p / totalSize) * 100);
                //todo: tester les ??changes entre S1 et S2
                //si on arrive l?? c'est que on a reussi ?? faire des appariements, on ajoute les parties et on return true
                List<Partie> resultatAppariement = appariementCouleur(numPartie,round,S1, S2); //je fais comme si c'??tait bon
                parties.addAll(resultatAppariement);
                return true;
            }
        }
        System.out.println("Pas impl??ment?? nombreJoueursImpairs");
        return false;
    }

    //calcul toutes les permutations k=participants.size()
    public List<List<Participe>> calculPermu(int k, List<Participe> participants) {
        List<List<Participe>> pa = new ArrayList<>();
        if (k == 1) {
            pa.add(participants);
            return pa;
        } else {
            calculPermu(k-1, participants);
            for (int i=0; i < k-2; i++) {
                if (k%2 == 0) {
                    Participe temp = participants.get(i);
                    participants.set(i, participants.get(k-1));
                    participants.set(k-1, temp);
                    pa.add(participants);
                } else {
                    Participe temp = participants.get(i);
                    participants.set(0, participants.get(k-1));
                    participants.set(k-1, temp);
                    pa.add(participants);
                }
                calculPermu(k-1, participants);
            }
        }
        return pa;
    }

    public List<List<List<Participe>>> echanges(List<Participe> s1, List<Participe> s2) {
        //initialisation de la liste qui contiendra tous les ??changes possibles
        List<List<List<Participe>>> listS1S2 = new ArrayList<>();
        for(int i = 0; i < s1.size(); i++) {
            for (int j = 0; j < s2.size(); j++) {
                //parcours de s1 et s2
                //la diff??rence entre les num??ros ??chang??s doit ??tre la plus petite possible
                Participe temp = s1.get(i);
                if (j <= i+2) {
                    s1.set(i, s2.get(j));
                    s2.set(j, temp);
                    List listEchange1 = new ArrayList<Participe>();
                    listEchange1.add(s1);
                    listEchange1.add(s2);
                    listS1S2.add(listEchange1);
                }
                //TODO ajouter la derniere liste de liste de liste...
            }
        }
        return listS1S2;
    }



    /** newround()
     * fonction cr??ant l'appariement de la ronde suivante
     *
     * @return les parties ?? jouer de la round suivantes
     */

    public List<Partie> newRound(int nonMerci) throws Exception { //round ignor??
        if(t.getNbRound()==t.gotCurrentRound()) {
            throw new Exception("tournoi termin??");
        }else if (t.gotCurrentRound()==0) {
            throw new Exception("round precedent non termin??");
        } else {
            int round = t.gotCurrentRound();

            List<Partie> parties = new ArrayList<>(); //instancie la liste des parties ?? apparier

            Set<Participe> participes = t.getParticipation();
            //on prends tout les joueurs participants
            List<Participe> participe = new ArrayList<>(participes);

            //corps de la m??thode
            long start = System.currentTimeMillis();
            System.out.println("d??but appariement");
            if(newRoundAux(parties, participe, round, 1, participe.size())){
                System.out.println("appariement termin??");
            }else{
                System.out.println("appariement ??chou??");
            }
            System.out.println("temps d'execution (ms) : "+(System.currentTimeMillis()-start));
            HibernateUtil.closeSession(sess);

            return parties;
        }
    }
}
