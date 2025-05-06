package com.example.master_app.services;

import com.example.master_app.Repository.RapportRepository;
import com.example.master_app.entities.Rapport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RapportService {

    @Autowired
    private RapportRepository rapportRepository;

    public List<Rapport> getAllRapports() {
        return rapportRepository.findAll();
    }

    public Rapport getRapportById(int id) {
        Optional<Rapport> rapport = rapportRepository.findById(id);
        return rapport.orElseThrow(() -> new RuntimeException("Rapport non trouvé"));
    }

    public Rapport createRapport(Rapport s) {
        Rapport rapport = new Rapport();
        return rapportRepository.save(rapport);
    }

    public void deleteRapport(int id) {
        Rapport rapport = getRapportById(id);
        rapportRepository.delete(rapport);
    }

    public File genererPDF(int rapportId) throws Exception {
        Rapport rapport = getRapportById(rapportId);
        String fileName = "rapport_" + rapportId + ".pdf";
        File file = new File(fileName);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Rapport ID: " + rapportId));
        document.add(new Paragraph("Date de création: " + LocalDate.now()));
        document.add(new Paragraph("Contenu du rapport: Exemple de données statistiques."));

        document.close();
        return file;
    }

    public File exporterExcel(int rapportId) throws Exception {
        Rapport rapport = getRapportById(rapportId);
        String fileName = "rapport_" + rapportId + ".xlsx";
        File file = new File(fileName);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rapport");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Rapport ID");
        headerRow.createCell(1).setCellValue("Date de création");
        headerRow.createCell(2).setCellValue("Contenu");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(rapportId);
        dataRow.createCell(1).setCellValue(LocalDate.now().toString());
        dataRow.createCell(2).setCellValue("Exemple de données statistiques.");

        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        }
        workbook.close();
        return file;
    }
}