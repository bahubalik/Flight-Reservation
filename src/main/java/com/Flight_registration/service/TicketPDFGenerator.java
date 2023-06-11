package com.Flight_registration.service;
import com.Flight_registration.entities.Flight;
import com.Flight_registration.entities.Passenger;
import com.Flight_registration.entities.Reservation;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

@Service
public class TicketPDFGenerator {

    public File generateTicketPDF(Flight flight, Passenger passenger, Reservation reservation) {
        try {
            File ticketFile = File.createTempFile("ticket", ".pdf");

            // Create a new PDF document
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(ticketFile));
            document.open();

            // Add heading to the document
            Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph heading = new Paragraph("Flight Ticket", headingFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            heading.setSpacingAfter(20);
            document.add(heading);

            // Create a table for the ticket details
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            // Add headings to the table
            Font tableHeadingFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            PdfPCell headingCell1 = new PdfPCell(new Paragraph("Flight Details", tableHeadingFont));
            PdfPCell headingCell2 = new PdfPCell(new Paragraph("Passenger Details", tableHeadingFont));
            table.addCell(headingCell1);
            table.addCell(headingCell2);

            // Add flight details to the table
            table.addCell("Flight Number:");
            table.addCell(flight.getFlightNumber());
            table.addCell("Operating Airlines:");
            table.addCell(flight.getOperatingAirlines());
            table.addCell("Departure City:");
            table.addCell(flight.getDepartureCity());
            table.addCell("Arrival City:");
            table.addCell(flight.getArrivalCity());
            // Add more flight details as needed

            // Add passenger details to the table
            table.addCell("Passenger Name:");
            table.addCell(passenger.getFirstName() + " " + passenger.getLastName());
            table.addCell("Email:");
            table.addCell(passenger.getEmail());
            table.addCell("Phone:");
            table.addCell(passenger.getPhone());
         
            // Add more passenger details as needed

            // Add the table to the document
            document.add(table);

            document.close();

            return ticketFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

