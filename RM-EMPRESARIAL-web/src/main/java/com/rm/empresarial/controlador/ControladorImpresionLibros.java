/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.FooterPdfEvent;
import com.rm.empresarial.controlador.util.HeaderPdfEvent;
//import com.itextpdf.tool.xml.ElementHandler;
//import com.itextpdf.tool.xml.ElementList;
//import com.itextpdf.tool.xml.Writable;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import com.itextpdf.tool.xml.pipeline.WritableElement;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.Tomo;
import com.rm.empresarial.modelo.Volumen;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.MargenServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.TipoLibroServicio;
import com.rm.empresarial.servicio.TomoServicio;
import com.rm.empresarial.servicio.VolumenServicio;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorImpresionLibros")
@ViewScoped
public class ControladorImpresionLibros implements Serializable {

    @Inject
    @Getter
    @Setter
    private DataManagerSesion dataManagerSesion;

    @Inject
    private PdfTempUtil pdfTempUtil;

    @EJB
    private MarginacionServicio servicioMarginacion;

    @EJB
    private TipoLibroServicio servicioTipoLibro;

    @EJB
    private ActaServicio servicioActa;
    
    @EJB
    private MargenServicio servicioMargen;
    
    @EJB
    private VolumenServicio servicioVolumen;
    
    @EJB
    private TomoServicio servicioTomo;

    @Getter
    @Setter
    private List<Acta> listaActa = new ArrayList<>();

    @Getter
    @Setter
    private List<Marginacion> listaMarginacionTodas = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoLibro> listaTipoLibro;
    
    @Getter
    @Setter
    private List<Tomo> listaTomo;
    
    @Getter
    @Setter
    private List<Volumen> listaVolumen;
    
    @Getter
    @Setter
    private Date fechaInicio;
    
    @Getter
    @Setter
    private Date fechaFin;

    @Getter
    @Setter
    private TipoLibro tipoLibroSeleccionado;
    
    @Getter
    @Setter
    private Margen margen;

    @Getter
    @Setter
    private String dirArchivoComoRecurso;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private Volumen volumenSeleccionado;
    
    @Getter
    @Setter
    private Tomo tomoSeleccionado;

    @Getter
    @Setter
    private String libro;

    public ControladorImpresionLibros() {
    }

    @PostConstruct
    public void inicializar() {
        try {
            cargarListas();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorImpresionLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarListas() throws ServicioExcepcion {
        listaTipoLibro = servicioTipoLibro.listarTodo();
        listaVolumen = new ArrayList<>();
        listaTomo = new ArrayList<>();
        fechaInicio = Calendar.getInstance().getTime();
        fechaFin = Calendar.getInstance().getTime();
    }
    
    public void obtenerTomos() throws ServicioExcepcion {
//        listaTomo = servicioTomo.listarPorTplId(tipoLibroSeleccionado.getTplId());
        listaTomo = tipoLibroSeleccionado.getTomoList();
    }
    
    public void obtenerVolumenes() {
        listaVolumen = tomoSeleccionado.getVolumenList();
    }

    public void buscarActas() throws ServicioExcepcion {
        listaActa = servicioActa.listarPorTipoLibPorTomoPorVolumenYfechIniFechFin(tipoLibroSeleccionado.getTplId(), new Integer(tomoSeleccionado.getTomTomo()), volumenSeleccionado.getVleVolumen(), fechaInicio, fechaFin);
        llenarListaMarginacionPorActa();
    }

    public List<Marginacion> llenarListaPorActaTiempoReal(Long numActa) {
        return servicioMarginacion.listarPor_NumActa(numActa);

    }

    public void llenarListaMarginacionPorActa() {
        for (Acta actaSelec : listaActa) {
            actaSelec.setListaMarginacion(servicioMarginacion.listarPor_NumActa(actaSelec.getActNumero()));
        }

        if (!listaActa.isEmpty()) {
            JsfUtil.addSuccessMessage("Actas y marginaciones cargadas.");
        }
    }
    
    public String agregarEspaciosTexto(String textoActa)
    {
        String resultado="";
        resultado=textoActa;
        resultado=resultado.replaceAll("<br></br>", "<br>");
        resultado=resultado.replaceAll("</p>", "</p><br>");
        resultado=resultado.replaceAll("<br>", "<br></br>");
//        resultado=resultado.replaceAll("<table width='60%'>", "");
//        resultado=resultado.replaceAll("</table>", "");
//        resultado=resultado.replaceAll("<tr>", "");
//        resultado=resultado.replaceAll("</tr>", "");
//        resultado=resultado.replaceAll("<td>", "");
//        resultado= resultado.replaceAll("</strong></td>", "</strong>");
////        resultado=resultado.replaceAll("<td>", "<p align=\"right\">");
//        resultado=resultado.replaceAll("</td>", "<br></br>");
//        resultado=resultado.replaceAll(": </strong>", "");
        return resultado;
    }

    public void crearPDFconTextoHTMLConMarg() throws IOException, ServicioExcepcion {
        try {
            String nombreArchivo = "libros";//nombre del archivo
            FileOutputStream fos = new FileOutputStream(pdfTempUtil.directorio(nombreArchivo));
            setMargen(servicioMargen.obtenerPorMarTipo("LIB"));
            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());//creo nuevo documento pdf, con tamaño y márgenes
            PdfWriter writer = PdfWriter.getInstance(document, fos);//declaro guardar el documento en el dir creado
            writer.setPageEvent(new HeaderPdfEvent());
//            document.setMarginMirroring(true);
            document.open();//abro el documento para editar

            //*****------------------------------
            //PARRAFO ACTA
//            parrafoIzq.setAlignment(Element.ALIGN_JUSTIFIED);
//            String[] texto = null;
            for (Acta acta : listaActa) {
                if (acta.getActActa() != null) {
                    document.newPage();
                    Paragraph parrafoIzq = new Paragraph();
                    String textoActa=agregarEspaciosTexto(acta.getActActaPdf());
                    parrafoIzq = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafoIzq, textoActa);
                    
//                    XMLWorkerHelper xmlWH;
//                    xmlWH = XMLWorkerHelper.getInstance();
//                    XMLWorkerHelper.getInstance().parseXHtml(new ElementHandler() {
//                        @Override
//                        public void add(final Writable w) {
//                            if (w instanceof WritableElement) {
//                                List<Element> elements = ((WritableElement) w).elements();
//                                for (Element e : elements) {
//                                    System.out.println("-"+e);
//                                }
//                            }
//                        }
//                    }, new StringReader(acta.getActActa()));
//                    ElementList elementList = XMLWorkerHelper.parseToElementList(acta.getActActa(), null);
//                    for (int i = 0; i < elementList.size(); i++) {
//
//                        System.out.println("-" + elementList.get(i));
//                    }
                    //------------------------------*****
                    //*****------------------------------
                    //PARRAFO MARGINACION
                    Font font = new Font(Font.FontFamily.HELVETICA, 10);
                    Paragraph parrafoDer = new Paragraph();
                    parrafoDer.setFont(font);
                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Chunk chunk;
                    for (Marginacion mrg : servicioMarginacion.listarPor_NumActa(acta.getActNumero())) {
                        chunk = new Chunk(sdfDateTime.format(mrg.getMrgFHR()));
                        parrafoDer.add(chunk);
                        parrafoDer.add("\n");

                        chunk = new Chunk(mrg.getMrgUser());
                        parrafoDer.add(chunk);
                        parrafoDer.add("\n");

                        chunk = new Chunk(mrg.getMrgAlt1());
                        parrafoDer.add(chunk);
                        parrafoDer.add("\n");

                        chunk = new Chunk(mrg.getMrgAlt2());
                        parrafoDer.add(chunk);
                        parrafoDer.add("\n");

                        chunk = new Chunk(sdfDate.format(mrg.getMrgFch()));
                        parrafoDer.add(chunk);
                        parrafoDer.add("\n");

                        parrafoDer.add(Chunk.NEWLINE);
                    }

                    //------------------------------*****
                    //TABLA PDF
                    PdfPCell celdaIzq = new PdfPCell(parrafoIzq);
//            celdaIzq.setFixedHeight(80);
                    celdaIzq.setBorder(Rectangle.NO_BORDER);
                    celdaIzq.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    //INTERLINEADO EN CELDA IZQUIERDA
                    celdaIzq.setLeading(new Float(14.5), 0);

                    PdfPCell celdaDer = new PdfPCell(parrafoDer);
                    celdaDer.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//            for (Chunk chunk1 : parrafoDer.getChunks()) {
//                System.out.println("-" + chunk1.getContent());
//            }

//            celdaDer.setFixedHeight(80);
                    celdaDer.setBorder(Rectangle.NO_BORDER);
                    PdfPTable table = new PdfPTable(2);
                    table.setWidthPercentage(100);
//        table.getTotalHeight();

                    table.setWidths(new float[]{70, 30});
                    table.addCell(celdaIzq);
                    table.addCell(celdaDer);

//        writer.setPageEvent(new Watermark()));
                    document.add(table);//añado la tabla al documento
                }
            }
            document.close();//cierro el documento
            writer.close();//cierro la escritura
            setUrl(pdfTempUtil.generarLinksAccesoAlPdf());
//            generarLinksAccesoAlPdf(dirPadre, tempDir, nombreArchivo); //enlaces desde la vista

//        PdfReader reader = new PdfReader(dirFinalArchivo);
//        System.out.println("# paginas: " + reader.getNumberOfPages());
//
//        Document document2 = new Document();
//        String dirFinalArchivo2 = tempDir.getParent().toString() + "/" + tempDir.getFileName().toString() + "/" + "prueba222.pdf";//path final del archivo
//
//        FileOutputStream fos2 = new FileOutputStream(dirFinalArchivo2);//defino la salida
//        PdfWriter writer2 = PdfWriter.getInstance(document2, fos2);
//        PdfImportedPage importedPage;
//
//        for (int page = 1; page <= reader.getNumberOfPages(); page++) {
////            importedPage = copy.getImportedPage(reader, page);
//
////            importedPage = writer.getImportedPage(reader, page);
////        importedPage = writer.getImportedPage(reader, page);
////            document2.add(Image.getInstance(importedPage));
//            fos2.write(reader.getPageContent(page));
//
//        }
//        PdfCopy copy = new PdfCopy(
//                document2, new FileOutputStream(dirFinalArchivo2));
//        document2.open();
//         int n = reader.getNumberOfPages();
//        for (int i = 0; i < n;) {
//            copy.addPage(copy.getImportedPage(reader, ++i));
//        }
//        document2.close();
//
//        reader.close();
//        writer2.close();
//        generarLinksAccesoAlPdf(dirPadre, tempDir, "prueba222.pdf"); //enlaces desde la vista 
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al generar el Pdf");
        }

    }
    
    public void crearPDFconTextoHTMLSinMarg() throws IOException, ServicioExcepcion {
        try {
            String nombreArchivo = "libros";//nombre del archivo
            FileOutputStream fos = new FileOutputStream(pdfTempUtil.directorio(nombreArchivo));
            setMargen(servicioMargen.obtenerPorMarTipo("LIB"));
            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());//creo nuevo documento pdf, con tamaño y márgenes
            PdfWriter writer = PdfWriter.getInstance(document, fos);//declaro guardar el documento en el dir creado
            writer.setPageEvent(new HeaderPdfEvent());
            document.open();//abro el documento para editar
            //*****------------------------------
            //PARRAFO ACTA
            for (Acta acta : listaActa) {
                if (acta.getActActa() != null) {
                    document.newPage();
                    Paragraph parrafo = new Paragraph();
                    parrafo = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafo, acta.getActActaPdf());
                    parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                    document.add(parrafo);
                }
            }
            document.close();//cierro el documento
            writer.close();//cierro la escritura
            setUrl(pdfTempUtil.generarLinksAccesoAlPdf());
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al generar el Pdf");
        }

    }

}
