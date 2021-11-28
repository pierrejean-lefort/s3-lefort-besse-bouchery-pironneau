package fr.umontpellier.lpbr.s3;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class TournoiTest {

    private Tournoi testTournoi;


    @BeforeEach
    public void init() {

        System.out.println("Test initializing");

        testTournoi = Mockito.mock(Tournoi.class);
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


}
