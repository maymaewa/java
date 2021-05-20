package com.oop;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupPDFExporter {
    private Iterable<Group> groups;

    public GroupPDFExporter(Iterable<Group> groups) {
        this.groups = groups;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        cell.setPhrase(new Phrase("Group ID"));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Year", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tickets", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Concerts", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table){
        for (Group group : groups) {
            table.addCell(String.valueOf(group.getId()));
            table.addCell(group.getName());
            table.addCell(String.valueOf(group.getYear()));
            table.addCell(String.valueOf(group.getTickets()));
            table.addCell(String.valueOf(group.getCountOfConcerts()));

        }

    }

    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("List of all groups"));
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();


    }
}
