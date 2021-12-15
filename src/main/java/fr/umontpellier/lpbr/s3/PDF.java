package fr.umontpellier.lpbr.s3;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Cell;


public class PDF {

    public static void resultatRoundCreatePDF(ArrayList<Partie> partieListe, Tournoi t){

        Document document = new Document();

        try {

            // création du pdf et choix du nom (penser a ne pas utiliser de caractère spéciaux)
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Table round  "+partieListe.get(0).getNumRonde()+" du tournois "+t.getNom()+".pdf"));


            document.open();

            document.add(new Paragraph("Table round"+partieListe.get(0).getNumRonde()+" du tournois "+t.getNom()+".pdf"));

            PdfPTable table = new PdfPTable(6); // création du nombre de colone
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //largueur colone

            float[] columnWidths = {1f, 1f, 3f, 1f, 3f,1f};
            table.setWidths(columnWidths);

            //définition d'une couleurr

            BaseColor myColor = BaseColor.CYAN;



             // ajoue des noms des colums pour l'impression
            PdfPCell cell1= new PdfPCell(new Paragraph("table"));
            cell1.setBackgroundColor(myColor);
            table.addCell(cell1);

            PdfPCell cell2= new PdfPCell(new Paragraph("pts"));
            cell2.setBackgroundColor(myColor);
            table.addCell(cell2);

            PdfPCell cell3= new PdfPCell(new Paragraph("blanc"));
            cell3.setBackgroundColor(myColor);
            table.addCell(cell3);

            PdfPCell cell5= new PdfPCell(new Paragraph("res"));
            cell5.setBackgroundColor(myColor);
            table.addCell(cell5);

            PdfPCell cell6= new PdfPCell(new Paragraph("Noir"));
            cell6.setBackgroundColor(myColor);
            table.addCell(cell6);

            PdfPCell cell8= new PdfPCell(new Paragraph("pts"));
            cell8.setBackgroundColor(myColor);
            table.addCell(cell8);




            for (Partie p : partieListe ) {
                int intGetTab = Integer.parseInt(p.getTable());

                if (intGetTab%2 == 0){
                    // couleur fond case
                    myColor = WebColors.getRGBColor("#a6ada9");
                }
                else {
                    myColor = WebColors.getRGBColor("#ffffff");
                }
                PdfPCell nbTable= new PdfPCell(new Paragraph(p.getTable()));// début de la nouvelle ligne
                nbTable.setBackgroundColor(myColor); // définition de la couleur
                table.addCell(nbTable); //ajoue de la cellule au tableau

                PdfPCell ptsBlanc = new PdfPCell(new Paragraph((int)p.getJoueur_blanc().nbPoint(t,p.getNumRonde())));
                ptsBlanc.setBackgroundColor(myColor);
                table.addCell(ptsBlanc);

                PdfPCell nomPrenomEloBlanc = new PdfPCell(new Paragraph(p.getJoueur_blanc().toStringPDF()));
                nomPrenomEloBlanc.setBackgroundColor(myColor);
                table.addCell(nomPrenomEloBlanc);


                PdfPCell resultat = new PdfPCell(new Paragraph(p.resultatToString()));
                resultat.setBackgroundColor(myColor);
                table.addCell(resultat);

                PdfPCell nomPrenomEloNoir = new PdfPCell(new Paragraph(p.getJoueur_noir().toStringPDF()));
                nomPrenomEloNoir.setBackgroundColor(myColor);
                table.addCell(nomPrenomEloNoir);


                PdfPCell ptsNoir = new PdfPCell(new Paragraph((int)p.getJoueur_noir().nbPoint(t,p.getNumRonde())));
                ptsNoir.setBackgroundColor(myColor);
                table.addCell(ptsNoir);
            }
            document.add(table);
            document.close();
            writer.close();
            System.out.println("le doc a était créer ");

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



    public static void appareillementPDF(ArrayList<Partie> partieListe, Tournoi t){
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Appareillement round  "+partieListe.get(0).getNumRonde()+" du tournois "+t.getNom()+".pdf"));

            document.open();

            document.add(new Paragraph("Les matchs du round "+partieListe.get(0).getNumRonde()+" sont : "));

            PdfPTable table = new PdfPTable(3);

            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //largueur colone

            float[] columnWidths = {0.25f, 2f, 2f};
            table.setWidths(columnWidths);

            //définition d'une couleurr

            BaseColor myColor = BaseColor.CYAN;



            // ajoue des noms des colums pour l'impression
            PdfPCell cell1= new PdfPCell(new Paragraph("table"));
            cell1.setBackgroundColor(myColor);
            table.addCell(cell1);

            PdfPCell cell2= new PdfPCell(new Paragraph("joueur blanc"));
            cell2.setBackgroundColor(myColor);
            table.addCell(cell2);

            PdfPCell cell3= new PdfPCell(new Paragraph("joueur noir"));
            cell3.setBackgroundColor(myColor);
            table.addCell(cell3);



            for (Partie p : partieListe ) {
                int intGetTab = Integer.parseInt(p.getTable());

                if (intGetTab%2 == 0){
                    // couleur fond case
                    myColor = WebColors.getRGBColor("#a6ada9");
                }
                else {
                    myColor = WebColors.getRGBColor("#ffffff");
                }
                PdfPCell nbTable= new PdfPCell(new Paragraph(p.getTable()));// début de la nouvelle ligne
                nbTable.setBackgroundColor(myColor); // définition de la couleur
                table.addCell(nbTable); //ajoue de la cellule au tableau

                PdfPCell nomPrenomEloBlanc = new PdfPCell(new Paragraph(p.getJoueur_blanc().toStringPDF()));
                nomPrenomEloBlanc.setBackgroundColor(myColor);
                table.addCell(nomPrenomEloBlanc);


                PdfPCell nomPrenomEloNoir = new PdfPCell(new Paragraph(p.getJoueur_noir().toStringPDF()));
                nomPrenomEloNoir.setBackgroundColor(myColor);
                table.addCell(nomPrenomEloNoir);

            }


            document.add(table);
            document.close();
            writer.close();
            System.out.println("le doc a était créer ");


        }
        catch (Exception e){
            e.printStackTrace();
        }




    }

    //création pdf grille américaine

    public static void grilleAmericainePDF(ArrayList<Partie> partieListe, Tournoi t, ArrayList<Joueur> joueurs){
        Document document = new Document();

        try{
            int numRound = partieListe.get(0).getNumRonde();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Grille Américaine round "+numRound+" du tournois "+t.getNom()+".pdf"));


            PdfPTable table = new PdfPTable(7+numRound);

            BaseColor myColor = BaseColor.CYAN;

            PdfPCell cell1= new PdfPCell(new Paragraph("place"));
            cell1.setBackgroundColor(myColor);
            table.addCell(cell1);

            PdfPCell cell2= new PdfPCell(new Paragraph("Nom"));
            cell2.setBackgroundColor(myColor);
            table.addCell(cell2);

            PdfPCell cell3= new PdfPCell(new Paragraph("Elo"));
            cell3.setBackgroundColor(myColor);
            table.addCell(cell3);


            // création du nom des colones de round
            for (int i = 1; i<= numRound; i++){
                PdfPCell cell4= new PdfPCell(new Paragraph("round"+i));
                cell4.setBackgroundColor(myColor);
                table.addCell(cell4);
            }

            PdfPCell cell5= new PdfPCell(new Paragraph("Pts"));
            cell5.setBackgroundColor(myColor);
            table.addCell(cell5);

            PdfPCell cell6= new PdfPCell(new Paragraph("Bu"));
            cell6.setBackgroundColor(myColor);
            table.addCell(cell6);



            PdfPCell cell7= new PdfPCell(new Paragraph("Cu"));
            cell7.setBackgroundColor(myColor);
            table.addCell(cell7);

            PdfPCell cell8= new PdfPCell(new Paragraph("Perf"));
            cell8.setBackgroundColor(myColor);
            table.addCell(cell8);

            //todo faire un for each sur le classement des joueurs pour récupérer leurs informations et les insérer dans les cellule

            int classement = 1;

            for (Joueur j : joueurs){

                if (classement%2 == 0){
                    // couleur fond case
                    myColor = WebColors.getRGBColor("#a6ada9");
                }
                else {
                    myColor = WebColors.getRGBColor("#ffffff");
                }

                PdfPCell position= new PdfPCell(new Paragraph(classement));// début de la nouvelle ligne
                position.setBackgroundColor(myColor); // définition de la couleur
                table.addCell(position); //ajoue de la cellule au tableau

                PdfPCell nom= new PdfPCell(new Paragraph(j.getNom()+" " +j.getPrenom()));
                nom.setBackgroundColor(myColor);
                table.addCell(nom);

                PdfPCell elo= new PdfPCell(new Paragraph(j.getElo()));
                elo.setBackgroundColor(myColor);
                table.addCell(elo);

                //todo création de la méthodde pour calculer le bucolse
                PdfPCell bucolse= new PdfPCell(new Paragraph(classement));
                bucolse.setBackgroundColor(myColor);
                table.addCell(bucolse);

                //todo création de la méthodde pour calculer le cu
                PdfPCell cu= new PdfPCell(new Paragraph(classement));
                cu.setBackgroundColor(myColor);
                table.addCell(cu);

                //todo création de la méthodde pour calculer le perfElo
                PdfPCell perfElo= new PdfPCell(new Paragraph(classement));
                perfElo.setBackgroundColor(myColor);
                table.addCell(perfElo);

                classement ++;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }








}
