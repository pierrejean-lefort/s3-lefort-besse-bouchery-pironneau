package fr.umontpellier.lpbr.s3;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "joueurs")
public class Joueur {
    private int id;
    private String numLicence;
    private String nom;
    private String prenom;
    private int elo;
    private Date dateNaissance;
    private String club;
    private String nationalite;
    private String sexe;
    private Set<Participe> participations = new HashSet<>();

    private void setId(int id) { this.id = id; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() { return this.id; }

    public void setNumLicence(String numLicence) {
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

    @Column(name = "nationalite")
    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    @Column(name = "sexe")
    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Joueur() {

    }

    @Column(name = "numLicence")
    public String getNumLicence() {
        return numLicence;
    }

    @OneToMany(mappedBy="joueur")
    public Set<Participe> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<Participe> participations) {
        this.participations = participations;
    }

    @Override
    public String toString() {
        return "#" + id + " - N°" + numLicence + " - " + nom + " " + prenom + " - Elo: " + elo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return id == joueur.id && elo == joueur.elo && numLicence.equals(joueur.numLicence) && nom.equals(joueur.nom) && prenom.equals(joueur.prenom) && dateNaissance.equals(joueur.dateNaissance) && club.equals(joueur.club) && nationalite.equals(joueur.nationalite) && sexe.equals(joueur.sexe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numLicence, nom, prenom, elo, dateNaissance, club, nationalite, sexe);
    }
}