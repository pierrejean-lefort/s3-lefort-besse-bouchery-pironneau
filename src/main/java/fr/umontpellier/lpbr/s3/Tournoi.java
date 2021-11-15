package fr.umontpellier.lpbr.s3;


import fr.umontpellier.lpbr.s3.SystemTournoi.Suisse;
import fr.umontpellier.lpbr.s3.SystemTournoi.SystemTournoi;
import fr.umontpellier.lpbr.s3.views.View;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.*;

@Entity(name = "tournois")
public class Tournoi {
    private int id;
    private int nbRound;
    private String nom;
    private String methode;
    private int status;
    private Set<Participe> participation = new HashSet<>();
    private Set<Partie> parties = new HashSet<>();

    public Tournoi() {
    }

    public Tournoi(int id, int nbRound, String nom, String methode) {
        this.id = id;
        this.nbRound = nbRound;
        this.nom = nom;
        this.methode = methode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="nbRound")
    public int getNbRound() {
        return nbRound;
    }

    public void setNbRound(int nbRound) {
        this.nbRound = nbRound;
    }

    @Column(name="nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name="methode")
    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    @OneToMany(mappedBy="tournoi", fetch = FetchType.EAGER)
    public Set<Participe> getParticipation() {
        return participation;
    }

    public void setParticipation(Set<Participe> participation) {
        this.participation = participation;
    }

    @OneToMany(mappedBy="tournoi", fetch = FetchType.EAGER)
    public Set<Partie> getParties() {
        return parties;
    }

    public void setParties(Set<Partie> parties) {
        this.parties = parties;
    }

    @Override
    public String toString() {
        return "#" + id + " - " + nom + " - " + getParticipation().size() + " joueurs";
    }

    @Column(name="status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int gotCurrentRound() { // get the last finished round, return 0 if no round has been finished
//        Session sess = HibernateUtil.openSession();
//        Object p = sess.createQuery("SELECT MAX(numRonde) FROM parties WHERE tournoi = :id")
//        Object p = sess.createQuery("SELECT MAX(t3.numRonde) FROM ( " +
//                            "SELECT p1.tournoi_id, p1.numRonde" +
//                            "FROM parties AS p1 LEFT JOIN ( " +
//                                "SELECT p2.tournoi_id, p2.numRonde FROM parties AS p2 WHERE p2.resultat = 0" +
//                            ") As t2 USING (tournoi_id)" +
//                            "WHERE t2.tournoi_id IS NULL" +
//                        ") As t3 WHERE tournoi = :id")
//            .setParameter("id", this)
//                .getSingleResult();
//        HibernateUtil.closeSession(sess);
        int p = 0;

        Set<Partie> parties = getParties();
        List<Partie> parties1 = new ArrayList<>(parties);
        parties1.sort(Comparator.comparingInt(Partie::getNumRonde));
        System.out.println(parties1);
        for (Partie p1 : parties1) {
            if (p1.getNumRonde() > p) p = p1.getNumRonde();
            if (p1.getResultat() == 0) {
                return p;
            }
        }

        return p + 1;

//        SELECT MAX(t3.numRonde) FROM ( SELECT p1.tournoi_id, p1.numRonde FROM parties AS p1 LEFT JOIN ( SELECT p2.tournoi_id, p2.numRonde FROM parties AS p2 WHERE p2.resultat = 0 ) As t2 USING (tournoi_id) WHERE t2.tournoi_id IS NULL ) As t3
    }

    public List<Partie> gotRound(int round) {
        Session sess = HibernateUtil.openSession();
        List<Partie> p = sess.createQuery("FROM parties WHERE numRonde = :round AND tournoi = :id")
                .setParameter("id", this)
                .setParameter("round", round)
                .list();
        if (p.size() != 0) {
            EchecIHM.taskSetProgress(100);
        }

        HibernateUtil.closeSession(sess);

        return p.size() != 0 ? p : null;
    }

    public List<Partie> gotRepartition(int round) {
        List<Partie> p = gotRound(round);
        if (p != null) return p;

        if (getParticipation().size() == 0 || getParticipation().size() % 2 != 0) return null;


        SystemTournoi sys = new Suisse(this);
        return round == 1 ? sys.firstRound() : sys.newRound(round);
    }

    public Tournoi clone() {
        Tournoi t = new Tournoi();
        t.setId(id);
        t.setStatus(status);
        t.setNom(nom);
        t.setMethode(methode);
        t.setNbRound(nbRound);
        return t;
    }
}
