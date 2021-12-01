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
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Table round :"+partieListe.get(0).getNumRonde()+" du tournois"+t.getNom()+".pdf"));
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

                PdfPCell nomPrenomEloBlanc = new PdfPCell(new Paragraph(p.getJoueur_blanc().toString()));
                nomPrenomEloBlanc.setBackgroundColor(myColor);
                table.addCell(nomPrenomEloBlanc);


                PdfPCell resultat = new PdfPCell(new Paragraph(p.resultatToString()));
                resultat.setBackgroundColor(myColor);
                table.addCell(resultat);

                PdfPCell nomPrenomEloNoir = new PdfPCell(new Paragraph(p.getJoueur_noir().toString()));
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




}
