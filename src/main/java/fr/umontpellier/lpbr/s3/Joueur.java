package fr.umontpellier.lpbr.s3;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

@Entity
@Table( name = "joueurs" )
public class Joueur {
    private String numLicence;
    private String nom;
    private String prenom;
    private int elo;
    private Date dateNaissance;
    private String nomClub;

    public void setNumLicence(String numLicence) {
        this.numLicence = numLicence;
    }

    @Column(name = "Nom")
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

    @Column(name = "nomClub")
    public String getNomClub() {
        return nomClub;
    }

    public void setNomClub(String nomClub) {
        this.nomClub = nomClub;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dateNaissance")
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    public Joueur(String numLicence, String nom, String prenom, int elo, Date dateNaissance, String nomClub) {
        this.numLicence = numLicence;
        this.nom = nom;
        this.prenom = prenom;
        this.elo = elo;
        this.dateNaissance = dateNaissance;
        this.nomClub = nomClub;
    }

    public Joueur() {

    }

    @Id
    public String getNumLicence() {
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
                ", nomClub='" + nomClub + '\'' +
                '}';
    }
}