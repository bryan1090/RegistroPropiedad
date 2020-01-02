/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfAppearance;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.servicio.MargenServicio;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Dependent
public class PdfTempUtil implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private Margen margen;

    @EJB
    private MargenServicio servicioMargen;

    @Getter
    @Setter
    private String nombreArchivo;

    @Getter
    @Setter
    private String dirPadre;

    @Getter
    @Setter
    private Path tempDir;

    @Getter
    @Setter
    private String dirArchivoComoRecurso;

    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    String dirFinalArchivo;

//    public void borrar() throws IOException, DocumentException {
//        PdfReader reader = new PdfReader(dirFinalArchivo);
//
//        PdfStamper stamper = PdfStamper.createSignature(reader, new FileOutputStream(dirFinalArchivo + "_terminado.pdf"), '\0');
//        PdfSignatureAppearance appearance
//                = stamper.getSignatureAppearance();
//
//        appearance.setVisibleSignature(new Rectangle(72, 732, 144, 780), 2, "firma");
//        appearance.setReason("Razón de la Firma");
//        appearance.setLocation("Ubicación");
//        appearance.setCrypto(
//                pk, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
//    }

    public void crearPDFconTextoHTML(String nombreArchivo, String texto, String tipoMargen) throws ServicioExcepcion {
        try {
            String dirCarpetaTemp = directorio();//Creo el directorio temporal sin los archivos
//            System.out.println("dirCarpetaTemp: " + dirCarpetaTemp);
            dirFinalArchivo = dirCarpetaTemp + nombreArchivo;//Directorio completo del archivo
            System.out.println("dirFinalArchivo: " + dirFinalArchivo);

            FileOutputStream fos = new FileOutputStream(dirFinalArchivo);
            setMargen(servicioMargen.obtenerPorMarTipo(tipoMargen));
            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.setPageEvent(new FooterPdfEvent2());
//            byte[] USER = "Hello".getBytes();
//            byte[] OWNER = "World".getBytes();
//
//            writer.setEncryption(USER, OWNER,
//                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
//            writer.createXmpMetadata();

//            writer.setPageEvent(new FooterPdfEvent());
            document.setMarginMirroring(true);
            document.open();

            Paragraph parrafo = new Paragraph();
            parrafo = convertirHtmlConFormatoAParrafoPdfOld(parrafo, texto);
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(parrafo);

            document.close();//cierro el documento

//            writer.setEncryption(null, (PdfWriter.), 0);
            writer.close();//cierro la escritura

            generarLinksAccesoAlPdf();

            setNombreArchivo(nombreArchivo);//Agregar el nombre archivo final a la dependencia
//            OutputStream fos = new FileOutputStream(dirCarpetaTemp + nombreArchivo);//Crear archivo final
//            PdfTempUtil.doMerge(listPdfs, fos);//Combinar pdfs que se agregaron en la lista

            generarLinksAccesoAlPdf();//Generar la url que se mostrará en la lista
        } catch (DocumentException | IOException ex) {
            ex.getMessage();
        }
    }
    
    
     public void crearPDFconTextoHTML_Repetir(String nombreArchivo, String texto, String tipoMargen, int numCopias) throws ServicioExcepcion {
        try {
            String dirCarpetaTemp = directorio();//Creo el directorio temporal sin los archivos
//            System.out.println("dirCarpetaTemp: " + dirCarpetaTemp);
            dirFinalArchivo = dirCarpetaTemp + nombreArchivo;//Directorio completo del archivo
            System.out.println("dirFinalArchivo: " + dirFinalArchivo);

            FileOutputStream fos = new FileOutputStream(dirFinalArchivo);
            setMargen(servicioMargen.obtenerPorMarTipo(tipoMargen));
            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
            PdfWriter writer = PdfWriter.getInstance(document, fos);
                        writer.setPageEvent(new FooterPdfEvent2());

//            byte[] USER = "Hello".getBytes();
//            byte[] OWNER = "World".getBytes();
//
//            writer.setEncryption(USER, OWNER,
//                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
//            writer.createXmpMetadata();

//            writer.setPageEvent(new FooterPdfEvent());
            document.setMarginMirroring(true);
            document.open();

            
            Paragraph parrafo = new Paragraph();
            parrafo = convertirHtmlConFormatoAParrafoPdfOld(parrafo, texto);
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(parrafo);
            
            for(int i = 1; i<= numCopias - 1; i++){
            document.newPage();
            document.add(parrafo);
            }

            document.close();//cierro el documento

//            writer.setEncryption(null, (PdfWriter.), 0);
            writer.close();//cierro la escritura

            generarLinksAccesoAlPdf();

            setNombreArchivo(nombreArchivo);//Agregar el nombre archivo final a la dependencia
//            OutputStream fos = new FileOutputStream(dirCarpetaTemp + nombreArchivo);//Crear archivo final
//            PdfTempUtil.doMerge(listPdfs, fos);//Combinar pdfs que se agregaron en la lista

            generarLinksAccesoAlPdf();//Generar la url que se mostrará en la lista
        } catch (DocumentException | IOException ex) {
            ex.getMessage();
        }
    }
    

    public void crearPDFconTextoHTMLConFirma(String nombreArchivo, String texto, String tipoMargen) throws ServicioExcepcion {
        try {
            String dirCarpetaTemp = directorio();//Creo el directorio temporal sin los archivos
//            System.out.println("dirCarpetaTemp: " + dirCarpetaTemp);
            dirFinalArchivo = dirCarpetaTemp + nombreArchivo;//Directorio completo del archivo
            System.out.println("dirFinalArchivo: " + dirFinalArchivo);

            FileOutputStream fos = new FileOutputStream(dirFinalArchivo);
            setMargen(servicioMargen.obtenerPorMarTipo(tipoMargen));
            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
            PdfWriter writer = PdfWriter.getInstance(document, fos);
//            byte[] USER = "Hello".getBytes();
//            byte[] OWNER = "World".getBytes();
//
//            writer.setEncryption(USER, OWNER,
//                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
//            writer.createXmpMetadata();

//            writer.setPageEvent(new FooterPdfEvent());
            document.setMarginMirroring(true);
            document.open();

            Paragraph parrafo = new Paragraph();
            parrafo = convertirHtmlConFormatoAParrafoPdfOld(parrafo, texto);
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(parrafo);

            PdfFormField field
                    = PdfFormField.createSignature(writer);
            field.setWidget(new Rectangle(72, 732, 144, 780),
                    PdfAnnotation.HIGHLIGHT_INVERT);
            field.setFieldName("mySig");
            field.setFlags(PdfAnnotation.FLAGS_PRINT);
            field.setPage();
            field.setMKBorderColor(BaseColor.BLACK);
            field.setMKBackgroundColor(BaseColor.WHITE);
            PdfAppearance tp = PdfAppearance.createAppearance(writer, 72, 48);
            tp.rectangle(0.5f, 0.5f, 71.5f, 47.5f);
            tp.stroke();
            field.setAppearance(
                    PdfAnnotation.APPEARANCE_NORMAL, tp);
            writer.addAnnotation(field);

            document.close();//cierro el documento

//            writer.setEncryption(null, (PdfWriter.), 0);
            writer.close();//cierro la escritura

            generarLinksAccesoAlPdf();

            setNombreArchivo(nombreArchivo);//Agregar el nombre archivo final a la dependencia
//            OutputStream fos = new FileOutputStream(dirCarpetaTemp + nombreArchivo);//Crear archivo final
//            PdfTempUtil.doMerge(listPdfs, fos);//Combinar pdfs que se agregaron en la lista

            generarLinksAccesoAlPdf();//Generar la url que se mostrará en la lista
        } catch (DocumentException | IOException ex) {
            ex.getMessage();
        }
    }

//    public void agregarImagenEspacioEnBlanco() {
//        try {
//            FacesContext fc = FacesContext.getCurrentInstance();
//            ExternalContext ec = fc.getExternalContext();
//            InputStream is = ec.getResourceAsStream("/imagenes/espacioEnBlanco.png");
////LEER EXISTENTE Y MODIFICAR
//            Document document = new Document();
//            PdfReader reader = new PdfReader(url);
//            document.open();
//            System.out.println("# paginas: " + document.getPageNumber());
//            document.close();
////            ESCRIBIR NUEVO
////            Document document = new Document();
////            FileOutputStream fos = new FileOutputStream(path);
////            PdfWriter writer = PdfWriter.getInstance(document, fos);
////            document.open();
////            document.add(new Paragraph("Hola mundo"));
////            document.close();
////            writer.close();
//        } catch (IOException ex) {
//            Logger.getLogger(PdfTempUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public String directorio() {
        try {
            setDirPadre("temp");
            tempDir = crearCarpetaTemporalEnDirWebDeDeploy(dirPadre, dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            String dirFinalCarpeta = tempDir.getParent().toString() + "/" + tempDir.getFileName().toString() + "/";//path carpeta
            return dirFinalCarpeta;
        } catch (IOException e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al generar el Pdf");
            return null;
        }
    }

    public String directorio(String nomArchivo) throws IOException, ServicioExcepcion {
        try {
            setNombreArchivo(nomArchivo + ".pdf");//nombre del archivo
            setDirPadre("temp");
            tempDir = crearCarpetaTemporalEnDirWebDeDeploy(dirPadre, dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            String dirFinal = tempDir.getParent().toString() + "/" + tempDir.getFileName().toString() + "/" + nombreArchivo;//path final del archivo
            return dirFinal;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al generar el Pdf");
            return null;
        }
    }

    private Path crearCarpetaTemporalEnDirWebDeDeploy(String dirPadre, String nombreCarpetaTemp) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

//        System.out.println("1" + ec.getRequestContextPath());// /RM-EMPRESARIAL-web
//        System.out.println("1" + ec.getRequestServletPath()); // /paginas/PROCESOS/Marginacion/Marginacion.xhtml
//        System.out.println("1" + ec.getRealPath("/")); //F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\
        String dirWebRealDeploy = ec.getRealPath("/");//**Se debe crear la carpeta y el archivo dentro del directorio real**

        Path tempDir = Files.createTempDirectory(Paths.get(dirWebRealDeploy + dirPadre + "/"),
                nombreCarpetaTemp + "_");//creo un directorio temporal en los directorios del deploy de la aplicacion.

        tempDir.toFile().deleteOnExit();//al cerrar la JVM, es decir al detener el glassfish se elimina la carpeta temporal

        // tempDir.getParent()=> F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\temp 
        // tempDir.getFileName().toString() => BMORA_2023083052387587915
        // nombreArchivo => prueba.pdf
        // dirFinalArchivo => F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\temp/BMORA_6348744837621346013/prueba.pdf
        return tempDir;
    }

    public Paragraph convertirHtmlConFormatoAParrafoPdfOld(Paragraph parrafo, String textoHtml) throws IOException {
//CODIGO QUE LEE EL TEXTO EN FORMATO XHTML Y LO COPIA A UN PARRAFO DEL ARCHIVO PDF CON EL FORMAT CORRESPONDIENTE
        StringReader strReader = new StringReader(textoHtml);
        List<Element> p = new ArrayList<>();
        p = HTMLWorker.parseToList(strReader, null);

//        parrafoIzq.setAlignment(Element.ALIGN_JUSTIFIED);
        for (int k = 0; k < p.size(); ++k) {
//            System.out.println("-> " + p.get(k));
            parrafo.add(p.get(k));
        }
        return parrafo;
    }

    public String generarLinksAccesoAlPdf() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        String dirContexto = ec.getRequestContextPath();
        dirArchivoComoRecurso = dirContexto + "/" + dirPadre + "/" + tempDir.getFileName().toString() + "/" + nombreArchivo;
        url = "/" + "temp" + "/" + tempDir.getFileName().toString() + "/" + nombreArchivo;

        return url;
    }

    public static void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //importar la página desde la fuente pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //añadir la página al destino pdf
                cb.addTemplate(page, 0, 0);
            }
        }

        outputStream.flush();
        document.close();
        outputStream.close();
    }

}
