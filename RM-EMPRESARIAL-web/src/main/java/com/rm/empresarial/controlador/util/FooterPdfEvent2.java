/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

@Dependent
public class FooterPdfEvent2 extends PdfPageEventHelper implements Serializable {

//    private final Font headerFont = new Font(Font.FontFamily.COURIER, 9);
//    private final Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    Font fontMarcaAgua = new Font(Font.FontFamily.HELVETICA, 52, Font.BOLD, new GrayColor(0.75f));

    public FooterPdfEvent2() {
        super();
    }

//    @Override
//    public void onEndPage(PdfWriter writer, Document document) {
//        PdfContentByte cb = writer.getDirectContent();
//        //FOOTER
//        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, 
//                new Phrase(String.format("Pag: %d", writer.getPageNumber()), footerFont), 
//                document.right() - 20, document.bottom(), 0);
//    }
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        System.out.println("Evento fin de pagina pdf");
        ColumnText.showTextAligned(writer.getDirectContentUnder(),
                Element.ALIGN_CENTER, new Phrase("DOCUMENTO NO VÁLIDO", fontMarcaAgua),
                297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
    }
}
