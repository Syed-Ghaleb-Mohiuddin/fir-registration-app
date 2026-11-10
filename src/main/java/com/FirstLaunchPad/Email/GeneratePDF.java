package com.FirstLaunchPad.Email;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

final class GeneratePDF {
    private GeneratePDF() {
    }

    static void write(FirReport report, Path output) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                writeText(content, PDType1Font.HELVETICA_BOLD, 18, 50, 790, "FIR Registration Form");
                writeText(content, PDType1Font.HELVETICA, 9, 50, 770,
                    "Generated from the console registration form");

                float y = 735;
                for (int i = 0; i < FirReport.LABELS.length; i++) {
                    writeText(content, PDType1Font.HELVETICA_BOLD, 9, 50, y, clean(FirReport.LABELS[i]));
                    writeText(content, PDType1Font.HELVETICA, 10, 215, y, clean(report.valueAt(i)));
                    y -= 29;
                }

                writeText(content, PDType1Font.HELVETICA, 10, 50, 270,
                    "Signature of applicant: ______________________________");
            }

            document.save(output.toFile());
        }
    }

    private static void writeText(PDPageContentStream content, PDType1Font font,
                                  float size, float x, float y, String text) throws IOException {
        content.beginText();
        content.setFont(font, size);
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
    }

    private static String clean(String value) {
        String compact = value.replace('\r', ' ').replace('\n', ' ').trim();
        if (compact.length() > 60) {
            return compact.substring(0, 57) + "...";
        }
        return compact;
    }
}
