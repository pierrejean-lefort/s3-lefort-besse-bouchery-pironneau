package fr.umontpellier.lpbr.s3;

import org.hibernate.Session;

import javax.persistence.*;

@Entity(name = "parties")
public class Partie {
    private int id;
    private String table;
    private int numRonde;

    /**
     * resultat :
     * 1 if white wins
     * 2 if black wins
     * 3 if equality
     */
    private int resultat;

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

    @Column(name="resultat", nullable = true)
    public int getResultat() {
        return resultat;
    }

    public void setResultat(int resultat) {
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
        return "|#" + id + " | " + joueur_blanc.getId() + " | " + joueur_noir.getId() + " | " + resultat + "\n";
    }

    /**
     * Créer et sauvegarder une nouvelle partie
     * @param t Tournoi auquel associé la partie
     * @param jb Joueur blanc qui joue pour cette partie
     * @param jn Joueur noir qui joue pour cette partie
     * @param num Numéro du rounde associé
     * @param tbl Tabel sur laquelle joue les joueurs
     * @return la partie créée
     */
    public static Partie createPartie(Tournoi t, Joueur jb, Joueur jn, int num, String tbl) {
        Partie p = new Partie();
        p.setTournoi(t);
        p.setJoueur_blanc(jb);
        p.setJoueur_noir(jn);
        p.setNumRonde(num);
        p.setTable(tbl);

        Session sess = HibernateUtil.openSession();
        if (t.getStatus() == 0) {
            t.setStatus(1);
            sess.save(HibernateUtil.prepareToSave(t));
        }
        sess.save(p);
        HibernateUtil.closeSession(sess);

        return p;
    }
}
