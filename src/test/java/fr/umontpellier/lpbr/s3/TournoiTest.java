package fr.umontpellier.lpbr.s3;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

public class TournoiTest {
    private Tournoi testTournoi;
    private Session sess;

    @Before
    public void init() {
        sess = HibernateUtil.openSession();

        System.out.println("Test initializing");

        testTournoi = new Tournoi();
        testTournoi.setNom("Test");
        testTournoi.setMethode(Methode.getMethodeList().get(0).getCode());
        testTournoi.setNbRound(9);
        sess.save(testTournoi);

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

            Participe p = new Participe();
            p.setTournoi(testTournoi);
            p.setJoueur(j);
            p.setElo_joueur(j.getElo());

            sess.save(p);
        }

        System.out.println("Test initialized");
    }

    @Test
    public void gotCurrentRoundTest() {
        System.out.println("Test running");
        int r = testTournoi.gotCurrentRound();
        assertEquals(r, 0);
    }

    @After
    public void shutdown() {
        System.out.println("Test shutdown");

        sess.close();
        HibernateUtil.shutdown();
    }
}
