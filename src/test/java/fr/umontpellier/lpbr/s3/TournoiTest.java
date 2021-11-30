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

import static org.junit.Assert.assertEquals;

public class TournoiTest {

    private static Tournoi testTournoi;
    private static Session sess;

    @BeforeEach
    public void init() {
        sess = HibernateUtil.openSession();
        sess.createSQLQuery("FLUSH TABLES");

        testTournoi = new Tournoi();
        testTournoi.setNom("Test");
        testTournoi.setMethode(Methode.getMethodeList().get(0).getCode());
        testTournoi.setNbRound(9);


        for (int i = 0; i < 20; i++) {
            Joueur j = new Joueur();
            j.setNom("Test" + i);
            j.setPrenom("Test");
            j.setClub("Club" + i);
            j.setElo(1000+i*3);
            j.setNationalite("fr");
            j.setDateNaissance(Date.from(Instant.now()));
            j.setNumLicence("1655416574" + i);
            j.setSexe("H");
            sess.save(j);

            System.out.println("ta grosse darrone");
            System.out.println(j.getId());

            Participe p = new Participe();
            p.setTournoi(testTournoi);
            p.setJoueur(j);
            p.setElo_joueur(j.getElo());


        }

        System.out.println("Test initialized");
    }

    @Test
    public void gotCurrentRoundTest() {
        System.out.println("Test running");
        int r = testTournoi.gotCurrentRound();
        assertEquals(1, r);
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
        sess.flush();
        HibernateUtil.closeSession(sess);
    }

}
