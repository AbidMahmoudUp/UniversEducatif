
package com.example.javafxprojectusertask.Entities;


import com.example.javafxprojectusertask.Services.BibliothequeDAO;
import com.example.javafxprojectusertask.Services.LivreDAO;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelExporter {

    public static void exporterBibliotheque(List<Bibliotheque> bibliotheques, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Bibliotheques");

            // En-têtes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(3).setCellValue("ID");
            headerRow.createCell(4).setCellValue("Nom");
            headerRow.createCell(5).setCellValue("Mail");
            headerRow.createCell(6).setCellValue("Nombre de Livres");

            // Ajout du logo
            InputStream logoStream = ExcelExporter.class.getResourceAsStream("/images/GreyLogo.png");

            byte[] logoBytes = IOUtils.toByteArray(logoStream);
            int logoId = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_PNG);
            logoStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(1);  // Colonne où le logo doit commencer
            anchor.setRow1(2);  // Ligne où le logo doit commencer
            anchor.setCol2(3);  // Colonne où le logo doit se terminer
            anchor.setRow2(4);  // Ligne où le logo doit se terminer
            drawing.createPicture(anchor, logoId);

            // Données
            int rowNum = 1;
            for (Bibliotheque bibliotheque : bibliotheques) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(3).setCellValue(bibliotheque.getIdBib());
                row.createCell(4).setCellValue(bibliotheque.getNom());
                row.createCell(5).setCellValue(bibliotheque.getMail());
                row.createCell(6).setCellValue(bibliotheque.getNbrLivre());
            }

            // Sauvegarde dans le fichier
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Exportation terminée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exporterLivres(List<Livre> livres, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Livres");

            // En-têtes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Type");
            headerRow.createCell(2).setCellValue("Titre");
            headerRow.createCell(3).setCellValue("Auteur");
            headerRow.createCell(4).setCellValue("Langue");
            headerRow.createCell(5).setCellValue("ID Bibliothèque");

            // Données
            int rowNum = 1;
            for (Livre livre : livres) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(livre.getIdLivre());
                row.createCell(1).setCellValue(livre.getType());
                row.createCell(2).setCellValue(livre.getTitre());
                row.createCell(3).setCellValue(livre.getAuteur());
                row.createCell(4).setCellValue(livre.getLangue());
                row.createCell(5).setCellValue(livre.getIdBib().getIdBib());
            }

            // Sauvegarde dans le fichier
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Exportation terminée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BibliothequeDAO bibliothequeDAO = new BibliothequeDAO();
        LivreDAO livreDAO = new LivreDAO();

        List<Bibliotheque> bibliotheques = bibliothequeDAO.getAllBibliotheques();
        ArrayList<Livre> livres = livreDAO.getAllLivres();

        ExcelExporter.exporterBibliotheque(bibliotheques, "C:/Users/MSI/OneDrive/Bureau/excel/FICHIER.xlsx");
        ExcelExporter.exporterLivres(livres, "C:/Users/MSI/OneDrive/Bureau/excel/FICHIERS.xlsx");
    }

}
