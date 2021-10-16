package fr.umontpellier.lpbr.s3;

import javax.persistence.*;

import java.util.Date;

@Entity(name = "joueurs")
public class Joueur {
    private int id;
    private int numLicence;
    private String nom;
    private String prenom;
    private int elo;
    private Date dateNaissance;
    private String club;

    private void setId(int id) { this.id = id; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int getId() { return this.id; }

    public void setNumLicence(int numLicence) {
        this.numLicence = numLicence;
    }

    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name = "prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Column(name = "elo")
    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    @Column(name = "club")
    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dateNaissance")
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    public Joueur(int numLicence, String nom, String prenom, int elo, Date dateNaissance, String club) {
        this.numLicence = numLicence;
        this.nom = nom;
        this.prenom = prenom;
        this.elo = elo;
        this.dateNaissance = dateNaissance;
        this.club = club;
    }

    public Joueur() {

    }

    @Column(name = "numLicence")
    public int getNumLicence() {
        return numLicence;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "numLicence='" + numLicence + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", elo=" + elo +
                ", dateNaissance=" + dateNaissance +
                ", nomClub='" + club + '\'' +
                '}';
    }
}