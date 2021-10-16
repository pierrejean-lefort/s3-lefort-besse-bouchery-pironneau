package fr.umontpellier.lpbr.s3;

import javax.persistence.*;

@Entity(name = "tournois")
public class Tournoi {
    private int id;
    private int nbRound;
    private String nom;
    private String methode;

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

    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", nbRound=" + nbRound +
                ", nom='" + nom + '\'' +
                ", methode='" + methode + '\'' +
                '}';
    }
}
