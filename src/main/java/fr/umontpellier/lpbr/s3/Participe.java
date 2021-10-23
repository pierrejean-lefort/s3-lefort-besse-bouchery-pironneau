package fr.umontpellier.lpbr.s3;

import javax.persistence.*;

@Entity(name = "participe")
public class Participe {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name="joueur_id")
    private Joueur joueur;

    @ManyToOne
    @JoinColumn(name="tournoi_id")
    private Tournoi tournoi;

    private int elo_joueur;

    public Participe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="elo_joueur")
    public int getElo_joueur() {
        return elo_joueur;
    }

    public void setElo_joueur(int elo_joueur) {
        this.elo_joueur = elo_joueur;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Tournoi getTournoi() {
        return tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    @Override
    public String toString() {
        return "Participe{" +
                "id=" + id +
                ", joueur=" + joueur.getNom() +
                ", tournoi=" + tournoi.getNom() +
                ", elo_joueur=" + elo_joueur +
                '}';
    }
}
