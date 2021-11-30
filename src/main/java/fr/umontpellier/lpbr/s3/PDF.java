package fr.umontpellier.lpbr.s3;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Cell;


public class PDF {

    public static void resultatRoundCreatePDF(ArrayList<Partie> partieListe, Tournoi t){

        Document document = new Document();


        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("resultat round"+partieListe.get(0).getNumRonde()+" du tournois"+t.getNom()));
            document.open();
            PdfPTable table = new PdfPTable(6); // création du nombre de colone
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

             // ajoue des noms des colums pour l'impression
            PdfPCell cell1= new PdfPCell(new Paragraph("table"));
            table.addCell(cell1);

            PdfPCell cell2= new PdfPCell(new Paragraph("pts"));
            table.addCell(cell2);

            PdfPCell cell3= new PdfPCell(new Paragraph("blanc"));
            table.addCell(cell3);



            PdfPCell cell5= new PdfPCell(new Paragraph("res"));
            table.addCell(cell5);

            PdfPCell cell6= new PdfPCell(new Paragraph("Noir"));
            table.addCell(cell6);



            PdfPCell cell8= new PdfPCell(new Paragraph("pts"));
            table.addCell(cell8);




            for (Partie p : partieListe ) {
                PdfPCell nbTable= new PdfPCell(new Paragraph(p.getTable()));// début de la nouvelle ligne
                table.addCell(nbTable); //ajoue de la cellule au tableau

                PdfPCell ptsBlanc = new PdfPCell(new Paragraph((int)p.getJoueur_blanc().nbPoint(t,p.getNumRonde())));
                table.addCell(ptsBlanc);

                PdfPCell nomPrenomEloBlanc = new PdfPCell(new Paragraph(p.getJoueur_blanc().toString()));
                table.addCell(nomPrenomEloBlanc);


                PdfPCell resultat = new PdfPCell(new Paragraph(p.resultatToString()));
                table.addCell(resultat);

                PdfPCell nomPrenomEloNoir = new PdfPCell(new Paragraph(p.getJoueur_noir().toString()));
                table.addCell(nomPrenomEloNoir);


                PdfPCell ptsNoir = new PdfPCell(new Paragraph((int)p.getJoueur_noir().nbPoint(t,p.getNumRonde())));
                table.addCell(ptsNoir);





            }
            document.add(table);
            document.close();
            writer.close();





        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
