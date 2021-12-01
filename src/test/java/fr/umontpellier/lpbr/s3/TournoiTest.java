package fr.umontpellier.lpbr.s3;

import org.hibernate.*;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TournoiTest {

    private static Tournoi testTournoi;
    private static Session sess;

    @BeforeEach
    public void init() {
        sess = HibernateUtil.openSession();
        sess.createSQLQuery("FLUSH TABLES");

        testTournoi = new Tournoi();
        testTournoi.setId(1);
        testTournoi.setNom("Test1");
        testTournoi.setMethode(Methode.getMethodeList().get(0).getCode());
        testTournoi.setNbRound(9);
        sess.save(testTournoi);

        for (int i = 0; i < 8; i++) {
            Joueur j = new Joueur();
            j.setNom("Test" + (i+1));
            j.setPrenom("Test");
            j.setClub("Club" + i);
            j.setElo(2000-i*3);
            j.setNationalite("fr");
            j.setDateNaissance(Date.from(Instant.now()));
            j.setNumLicence("1655416574" + i);
            j.setSexe("H");
            sess.save(j);

            Participe p = new Participe();
            p.setTournoi(testTournoi);
            p.setJoueur(j);
            p.setElo_joueur(j.getElo());
            sess.save(p);

            HibernateUtil.closeSession(sess);
            sess = HibernateUtil.openSession();
        }

        HibernateUtil.closeSession(sess);
    }

    private Joueur searchByName(String name) {
        System.out.println("searching " + name);
        for (Participe p : testTournoi.getParticipation()) {
            if (p.getJoueur().getNom().equals(name)) {
                return p.getJoueur();
            }
        }
        return null;
    }

    @Test
    public void gotCurrentRoundTestFirstRound() {
        int r = testTournoi.gotCurrentRound();
        assertEquals(0, r);
    }

    @Test
    public void gotCurrentRoundTestSecondRound() {
        testTournoi.gotRepartition(1);

        int r = testTournoi.gotCurrentRound();
        assertEquals(1, r);
    }

    @Test
    public void gotCurrentRoundTestSecondRoundWithResults() {
        List<Partie> parties = testTournoi.gotRepartition(1);
        sess = HibernateUtil.openSession();
        for (Partie p : parties) {
            p.setResultat(1);
            sess.update(p);
        }
        HibernateUtil.closeSession(sess);
        for (Partie p : testTournoi.getParties()) {
            System.out.println(p.getId() + " " + p.getResultat());
        }

        int r = testTournoi.gotCurrentRound();
        assertEquals(2, r);
    }

    @Test
    public void gotCurrentRoundTestThirdRound() {
        List<Partie> parties = testTournoi.gotRepartition(1);
        sess = HibernateUtil.openSession();
        for (Partie p : parties) {
            p.setResultat(1);
            sess.update(p);
        }
        HibernateUtil.closeSession(sess);
        for (Partie p : testTournoi.getParties()) {
            System.out.println(p.getId() + " " + p.getResultat());
        }
        testTournoi.gotRepartition(2);

        int r = testTournoi.gotCurrentRound();
        assertEquals(2, r);
    }

    @Test
    public void getCurrentRoundRepartition() {
        List<Partie> r = testTournoi.gotRepartition(1);

        assertEquals(r.get(0).getJoueur_blanc().getId(), searchByName("Test1").getId());
        assertEquals(r.get(0).getJoueur_noir().getId(), searchByName("Test5").getId());
        assertEquals(r.get(1).getJoueur_blanc().getId(), searchByName("Test6").getId());
        assertEquals(r.get(1).getJoueur_noir().getId(), searchByName("Test2").getId());
        assertEquals(r.get(2).getJoueur_blanc().getId(), searchByName("Test3").getId());
        assertEquals(r.get(2).getJoueur_noir().getId(), searchByName("Test7").getId());
        assertEquals(r.get(3).getJoueur_blanc().getId(), searchByName("Test8").getId());
        assertEquals(r.get(3).getJoueur_noir().getId(), searchByName("Test4").getId());
    }

    @Test
    public void getCurrentRoundRepartition2() {
        List<Partie> r = testTournoi.gotRepartition(1);
        sess = HibernateUtil.openSession();
        boolean j = true;
        for(Partie p : r) {
            p.setResultat(j ? 1 : 2);
            sess.update(p);
        }
        HibernateUtil.closeSession(sess);
        r = testTournoi.gotRepartition(2);

        System.out.println(searchByName("Test4"));
        System.out.println(r);

        assertEquals(r.get(0).getJoueur_blanc().getId(), searchByName("Test4").getId());
        assertEquals(r.get(0).getJoueur_noir().getId(), searchByName("Test1").getId());

        assertEquals(r.get(1).getJoueur_blanc().getId(), searchByName("Test2").getId());
        assertEquals(r.get(1).getJoueur_noir().getId(), searchByName("Test3").getId());

        assertEquals(r.get(2).getJoueur_blanc().getId(), searchByName("Test5").getId());
        assertEquals(r.get(2).getJoueur_noir().getId(), searchByName("Test8").getId());

        assertEquals(r.get(3).getJoueur_blanc().getId(), searchByName("Test7").getId());
        assertEquals(r.get(3).getJoueur_noir().getId(), searchByName("Test6").getId());
    }




    @AfterEach
    public void deleteAll() {
        sess = HibernateUtil.openSession();
        for (Participe p : testTournoi.getParticipation()) {
            Joueur j = p.getJoueur();
            j.setParticipations(null);
            sess.delete(j);

            p.setTournoi(null);
            p.setJoueur(null);
            sess.delete(p);
        }

        for (Partie p : testTournoi.getParties()) {
            p.setJoueur_blanc(null);
            p.setJoueur_noir(null);
            sess.delete(p);
        }

        testTournoi.setParticipation(null);
        testTournoi.setParties(null);
        sess.delete(testTournoi);
        //HibernateUtil.closeSession(sess);
        sess.close();
    }

}
