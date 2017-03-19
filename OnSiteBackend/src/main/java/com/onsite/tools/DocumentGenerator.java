package com.onsite.tools;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onsite.model.Comment;
import com.onsite.model.Issue;
import com.onsite.model.Project;
import com.onsite.repository.IssueRepository;
import com.onsite.repository.ProjectRepository;

/**
 * Created by Isuru on 12/11/2016.
 */

public class DocumentGenerator {
    String reportedDate;
    String issueName;
    String state;
    String sevearity;
    String reporter;
    String assignee;
    String commentsOnIssue;
    String commenterName;
    String comment;
    List<Comment> comments;


    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public String generateIssueReport(Integer projectId, List<Issue> issues, Project p ) {
        try {


            Document document = new Document();
            String filename = p.getProjectName()+"_report.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            for (Issue i : issues) {
                reportedDate = DateFormat.getDateInstance().format(i.getReportedDate());
                issueName = i.getIssueTitle();
                state = i.getStatus();
                sevearity = i.getSeverity();
                reporter = i.getReporter().getFullName();
                assignee = i.getAssignee() == null ? "unassigned" : i.getAssignee().getFullName();
                comments = i.getComments();


                //addImage(document);
                addMetaData(document);

                addTitlePage(document);
                if(!i.getImageUrls().isEmpty()) {
                    String imageUrl = i.getImageUrls().get(0);
                    imageUrl = imageUrl.substring(imageUrl.lastIndexOf("/"),imageUrl.length());

                    Image image = Image.getInstance("IssueImages/"+imageUrl);
                    document.add(image);
                    image.scalePercent(0.15f);
                }
            }
            //addContent(document);

            document.close();
            return filename;
        } catch (Exception e) {
            e.printStackTrace();return null;
        }

    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Issue Report Generated on" + DateFormat.getDateInstance().format(new Date()));
        document.addSubject("Using on Site Document Generator");
        document.addKeywords("Issue, PDF, Report");
        document.addAuthor("Isuru");
        document.addCreator("Onsite_issue_tracking_system");
    }


    private void addTitlePage(Document document) throws DocumentException {

        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Report Issues", catFont));

        addEmptyLine(preface, 1);

        //preface.add(new Paragraph("Issue Name:", catFont));
        Chunk chunk = new Chunk("Issue Name:" + " " + issueName + "  ");
        preface.add(chunk);

        addEmptyLine(preface, 1);

        Chunk chunk1 = new Chunk("Reported Date : " + " " + reportedDate + " ");
        preface.add(chunk1);

        addEmptyLine(preface, 1);

        Chunk chunk2 = new Chunk("State : " + " " + state + " ");
        preface.add(chunk2);

        addEmptyLine(preface, 1);

        Chunk chunk3 = new Chunk("Sevearity : " + " " + sevearity + " ");
        preface.add(chunk3);

        addEmptyLine(preface, 1);

        Chunk chunk4 = new Chunk("Reporter : " + " " + reporter + " ");
        preface.add(chunk4);

        addEmptyLine(preface, 1);

        Chunk chunk5 = new Chunk("Assignee : " + " " + assignee + " ");
        preface.add(chunk5);

        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Comments On Issue "));
        addEmptyLine(preface, 1);

        for (Comment c : comments) {
            Chunk chunki = new Chunk(c.getCommentor().getFullName());
            preface.add(chunki);

            addEmptyLine(preface, 1);

            Chunk chunkj = new Chunk(c.getCommentBody());
            preface.add(chunkj);
        }


        addEmptyLine(preface, 1);

        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));


        // Will create: Report generated by: _name, _date

        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph(
                "This document descibe issues which occur when constructing ",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list

        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }



    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }


    }
}
