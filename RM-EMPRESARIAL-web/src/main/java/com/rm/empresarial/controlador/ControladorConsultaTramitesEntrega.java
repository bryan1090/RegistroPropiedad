package com.rm.empresarial.controlador;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteEntrega;
import com.rm.empresarial.servicio.TramiteEntregaServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorConsultaTramitesEntrega")
@ViewScoped
public class ControladorConsultaTramitesEntrega implements Serializable {

    @Inject
    PdfTempUtil pdfTempUtil;

    @EJB
    private TramiteServicio servicioTramite;
    @EJB
    private TramiteEntregaServicio servicioTramiteEntrega;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;
    @Getter
    @Setter
    private Margen margen;

    @Getter
    @Setter
    private List<Tramite> listaTramites;
    @Getter
    @Setter
    private List<TramiteEntrega> listaTramitesEntrega;

    @Getter
    @Setter
    private Boolean estadoRenderFiltro = Boolean.FALSE;
    @Getter
    @Setter
    private Date fechaInicio;
    @Getter
    @Setter
    private Date fechaFin;
    @Getter
    @Setter
    private String numTramiteIni;
    @Getter
    @Setter
    private String numTramiteFin;
    @Getter
    @Setter
    private String dirCarpetaTemp;
    @Getter
    @Setter
    private int auxSpan;
    @Getter
    @Setter
    private int auxrang;

    public ControladorConsultaTramitesEntrega() {
        listaTramites = new ArrayList<>();
        listaTramitesEntrega = new ArrayList<>();
        tramiteSeleccionado = new Tramite();
    }

    public void busquedaPorTramites() {
        setEstadoRenderFiltro(Boolean.TRUE);
    }

    public void busquedaPorFechas() {
        setEstadoRenderFiltro(Boolean.FALSE);
        setFechaInicio(null);
        setFechaFin(null);
    }

    public void listarTramitesPorFechas() {
        setListaTramites(servicioTramite.listarPorRangoFecha(fechaInicio, fechaFin));
    }

    public void listarTramitesPorNumero() {
        setListaTramites(servicioTramite.listarPorRangoNumero(Integer.parseInt(numTramiteIni), Integer.parseInt(numTramiteFin)));
    }

    public void listarTramitesEntrega() {
        setListaTramitesEntrega(servicioTramiteEntrega.listarPorNumTramite(tramiteSeleccionado.getTraNumero()));
    }

    public void createPDF() throws FileNotFoundException, DocumentException, ParseException {
        listarTramitesEntrega();
        setAuxSpan(0);
        Document documento = new Document();
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String path = "C:/Users/DJimenez/Downloads/Tramite" + tramiteSeleccionado.getTraNumero() + ".pdf";
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(path));

            documento.open();

            int numFilas = listaTramitesEntrega.size();
            PdfPTable table = new PdfPTable(2);
            for (int row = 0; row < numFilas; row++) {
                if (row <= (numFilas - 2)) {
                    if (listaTramitesEntrega.get(row).getDetId().getTtrId().getTtrDescripcion().equals(listaTramitesEntrega.get(row + 1).getDetId().getTtrId().getTtrDescripcion())) {
                        auxSpan++;
                        if (auxSpan == 1) {
                            setAuxrang(row);
                        }
                    } else {
                        PdfPCell cell = new PdfPCell(new Phrase(listaTramitesEntrega.get(row).getDetId().getTtrId().getTtrDescripcion()));
                        cell.setRowspan(auxSpan + 1);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cell);
                        for (int aux = auxrang; aux <= row; aux++) {
                            table.addCell("" + listaTramitesEntrega.get(aux).getDetId().getDteId().getDteDescripcion());
                        }
                        setAuxSpan(0);
                    }
                } else if (row == (numFilas - 1)) {
                    if (numFilas > 1) {
                        if (listaTramitesEntrega.get(row).getDetId().getTtrId().getTtrDescripcion().equals(listaTramitesEntrega.get(row - 1).getDetId().getTtrId().getTtrDescripcion())) {
                            auxSpan++;
                            PdfPCell cell = new PdfPCell(new Phrase(listaTramitesEntrega.get(row).getDetId().getTtrId().getTtrDescripcion()));
                            cell.setRowspan(auxSpan + 1);
                            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                            table.addCell(cell);
                            for (int aux = auxrang; aux <= row; aux++) {
                                table.addCell("" + listaTramitesEntrega.get(aux).getDetId().getDteId().getDteDescripcion());
                            }
                            setAuxSpan(0);
                        } else {
                            table.addCell("" + listaTramitesEntrega.get(row).getDetId().getTtrId().getTtrDescripcion());
                            table.addCell("" + listaTramitesEntrega.get(row).getDetId().getDteId().getDteDescripcion());
                        }
                    } else {
                        table.addCell("" + listaTramitesEntrega.get(row).getDetId().getTtrId().getTtrDescripcion());
                        table.addCell("" + listaTramitesEntrega.get(row).getDetId().getDteId().getDteDescripcion());
                    }
                }

            }

            Image imagen ;
            imagen = Image.getInstance("F:/RM/Imagenes/Logo_RM_inicio.png");
            imagen.scaleAbsoluteHeight(50f);
            imagen.scaleAbsoluteWidth(50f);
            Paragraph titulo = new Paragraph("                                                      COIN JIH ASESORIA INFORMATICA CIA. LTDA.");
            titulo.setAlignment(Element.ALIGN_MIDDLE);
            documento.add(imagen);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("N° Trámite:" + tramiteSeleccionado.getTraNumero()));
            documento.add(new Paragraph(" "));
            documento.add(table);

            documento.close();

            Path file = Paths.get(path);
            InputStream input = null;

            OutputStream output = ec.getResponseOutputStream();
            File file2 = new File(path);
            Long tamaño = file2.length();
            input = new BufferedInputStream(Files.newInputStream(file));

            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(tamaño.intValue());
            ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + path + "\"");

            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = input.read(bytesBuffer)) > 0) {
                output.write(bytesBuffer, 0, bytesRead);
            }

//            output.write(input);
            fc.responseComplete();
        } catch (DocumentException ex) {

        } catch (java.io.IOException ex) {

        }
    }

}
