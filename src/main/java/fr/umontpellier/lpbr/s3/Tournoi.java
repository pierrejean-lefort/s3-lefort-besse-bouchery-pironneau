package fr.umontpellier.lpbr.s3;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "tournois")
public class Tournoi {
    private int id;
    private int nbRound;
    private String nom;
    private String methode;
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

    @OneToMany(mappedBy="tournoi")
    public Set<Participe> getParticipation() {
        return participation;
    }

    public void setParticipation(Set<Participe> participation) {
        this.participation = participation;
    }

    @OneToMany(mappedBy="tournoi")
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

//    public int getCurrentRound() {
//
//    }
//
//    public List<Joueur> getRepartition()
}
