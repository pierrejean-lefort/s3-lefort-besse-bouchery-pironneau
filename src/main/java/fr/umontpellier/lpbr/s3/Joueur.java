package fr.umontpellier.lpbr.s3;

import javax.persistence.*;

import javax.validation.constraints.*;

import java.util.Date;

@Entity(name = "joueurs")
public class Joueur {
    private int id;
    @NotNull @Size(min = 1, max = 32) private String numLicence;
    @NotNull @Size(min = 1, max = 32) @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$") private String nom;
    @NotNull @Size(min = 1, max = 32) @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$") private String prenom;
    @NotNull @Min(500) @Max(5000) private int elo;
    @NotNull private Date dateNaissance;
    @NotNull @Size(min = 1, max = 32) @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$") private String club;
    @NotNull @Size(min = 1, max = 32) @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$") private String nationalite;
    @NotNull @Size(min = 1, max = 32) private String sexe;

    private void setId(int id) { this.id = id; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int getId() { return this.id; }

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