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

    /**
     * +1 si joué noir -1 si joué blanc
     */
    @JoinColumn(name="couleurPref")
    private int couleurPref = 0; //est positif pour préférentiel blanc //compliqué ==> http://www.echecs.asso.fr/LivreArbitre/310.pdf paragraphe A7
    @JoinColumn(name="colorInRow")
    private int colorInRow = 0; // nombre de fois qu'un joueur joue la même couleur
    @JoinColumn(name="flotteur")
    private int flotteur = 0; // -1 si flotteur descendant //compliqué ==> http://www.echecs.asso.fr/LivreArbitre/310.pdf paragraphe A4

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

    public int getFlotteur(){
        return flotteur;
    }

    public int getCouleurPref() {
        return couleurPref;
    }

    public void setFlotteur(int flotteur){
        this.flotteur = flotteur;
    }

    public void setCouleurPref(int couleurPref) {
        this.couleurPref = couleurPref;
    }

    @Override
    public String toString() {
        return "Participe{" +
                "id=" + id +
                ", joueur=" + joueur.getNom() +
                ", tournoi=" + tournoi.getNom() +
                ", elo_joueur=" + elo_joueur +
                ", couleurPref=" + couleurPref +
                ", flotteur=" + flotteur +
                '}';
    }
}
