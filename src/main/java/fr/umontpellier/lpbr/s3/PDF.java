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

    public static void roundCreatePDF(ArrayList<Partie> partieListe){
        Document document = new Document();
        int nbPartie = partieListe.size();
        try {
            PdfPTable table = new PdfPTable(8); // création du nombre de colone
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

             // noramalement ça gére la largeur des colones

            int compteur = 1;

            for (Partie p : partieListe ) {
                PdfPCell cell= new PdfPCell(new Paragraph(p.getId()));
                table.addCell(cell);
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
