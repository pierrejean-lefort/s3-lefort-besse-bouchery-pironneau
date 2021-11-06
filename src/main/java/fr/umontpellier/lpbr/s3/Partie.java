package fr.umontpellier.lpbr.s3;

import javax.persistence.*;

@Entity(name = "parties")
public class Partie {
    private int id;
    private String table;
    private int numRonde;
    private String resultat;

    private Tournoi tournoi;
    private Joueur joueur_blanc;
    private Joueur joueur_noir;


    public Partie() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="table_name")
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Column(name="numRonde")
    public int getNumRonde() {
        return numRonde;
    }

    public void setNumRonde(int numRonde) {
        this.numRonde = numRonde;
    }

    @Column(name="resultat")
    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    @ManyToOne
    @JoinColumn(name="tournoi_id")
    public Tournoi getTournoi() {
        return tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    @ManyToOne
    @JoinColumn(name="joueur_blanc")
    public Joueur getJoueur_blanc() {
        return joueur_blanc;
    }

    public void setJoueur_blanc(Joueur joueur_blanc) {
        this.joueur_blanc = joueur_blanc;
    }

    @ManyToOne
    @JoinColumn(name="joueur_noir")
    public Joueur getJoueur_noir() {
        return joueur_noir;
    }

    public void setJoueur_noir(Joueur joueur_noir) {
        this.joueur_noir = joueur_noir;
    }

    @Override
    public String toString() {
        return "Partie{" +
                "id=" + id +
                ", table='" + table + '\'' +
                ", numRonde=" + numRonde +
                ", resultat='" + resultat + '\'' +
                ", tournoi=" + tournoi +
                ", joueur_blanc=" + joueur_blanc +
                ", joueur_noir=" + joueur_noir +
                '}';
    }
}
