/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.Serializable;
import javax.enterprise.context.Dependent;

/**
 *
 * @author JEAN
 */
@Dependent
public class HeaderPdfEvent extends PdfPageEventHelper implements Serializable {

    private final Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);

    public HeaderPdfEvent() {
        super();
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        //HEADER
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                new Phrase(String.format("%d", writer.getPageNumber()), footerFont),
                document.right() - 20, document.top(), 0);
    }
}
