/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfAppearance;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.MargenServicio;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import lombok.Getter;
import lombok.Setter;

@Dependent
public class UtilitarioPdf implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @Inject
    private EncryptPassword encryptPassword;

    @EJB
    private MargenServicio servicioMargen;

    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    @Getter
    @Setter
    private Margen margen;

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
    private String dirArchivoRTFComoRecurso;

    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    String dirFinalArchivo;
    @Getter
    @Setter
    private InputStream fileRTF = null;

    //ruta certificado y firma
    private String path_Firma;
    private String path_Certificado;
    private String nombreFirma;
    private String passFirma;

    @Getter
    @Setter
    String dirArchivoFirmado;

    private static final float ALTO_FIRMA = 40;
    private static final float ANCHO_FIRMA = 140;

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

    public void firmarPdf(String urlDocAfirmar, String nombreDocFirmado) {
        String dirCompletaArchivo = "";
        try {
            String dirDocumentosFirmados = servicioConfigDetalle.buscarPorConfig("FIRMADIGITAL").getConfigDetalleTexto();

            dirCompletaArchivo = dirDocumentosFirmados + nombreDocFirmado + "_firmado" + ".pdf";
            //ruta certificado y firma
//            this.nombreFirma = "firma_test";
//        this.passFirma = encryptPassword.decrypt(dataManagerSesion.getUsuarioLogeado().getUsuFirmaContrasenia());
//            this.passFirma = "Coinjih123";
            this.passFirma = encryptPassword.decrypt(dataManagerSesion.getUsuarioLogeado().getUsuFirmaContrasenia());

            String extension = ".p12";
            this.path_Firma = dataManagerSesion.getUsuarioLogeado().getUsuPathFirma();
//            this.path_Firma = "F:\\RM\\FacturacionElectronica\\repo_local_elec\\recursos\\firma\\firma_test.p12";

            KeyStore ks = null;
            PrivateKey pk = null;
//        KeyStore ks = KeyStore.getInstance("pkcs12", "BC");

            ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(path_Firma), passFirma.toCharArray());
            String alias = (String) ks.aliases().nextElement();

            pk = (PrivateKey) ks.getKey(alias, passFirma.toCharArray());
            Certificate[] chain = ks.getCertificateChain(alias);

            X509Certificate certificate = null;
            certificate = (X509Certificate) ks.getCertificate(alias);
            String dn = certificate.getSubjectX500Principal().getName();
            LdapName ldapDN = new LdapName(dn);
            String nombresFirma = "";
            for (Rdn rdn : ldapDN.getRdns()) {
                if (rdn.getType().equals("CN")) {
                    nombresFirma = rdn.getValue().toString();
                }
//                System.out.println(rdn.getType() + " -> " + rdn.getValue());
            }
//            X509Certificate certificate = null;
//            certificate = (X509Certificate) ks.getCertificate(alias);
//---campo firma
//            PdfFormField field
//                    = PdfFormField.createSignature(writer);
//            field.setWidget(new Rectangle(72, 732, 144, 780),
//                    PdfAnnotation.HIGHLIGHT_INVERT);
//            field.setFieldName("mySig");
//            field.setFlags(PdfAnnotation.FLAGS_PRINT);
//            field.setPage();
//            field.setMKBorderColor(Color.BLACK);
//            field.setMKBackgroundColor(Color.WHITE);
//            PdfAppearance tp = PdfAppearance.createAppearance(writer, 72, 48);
//            tp.rectangle(0.5f, 0.5f, 71.5f, 47.5f);
//            tp.stroke();
//            field.setAppearance(
//                    PdfAnnotation.APPEARANCE_NORMAL, tp);
            //-------//
            PdfReader reader = new PdfReader(urlDocAfirmar);
            System.out.println("dirCompletaArchivo: " + dirCompletaArchivo);
            PdfStamper stamper = PdfStamper.createSignature(reader, new FileOutputStream(dirCompletaArchivo), '\0');

            int numUltimaPagina = reader.getNumberOfPages();
            System.out.println("numUltimaPagina: " + numUltimaPagina);
            Rectangle rectanguloPagina = reader.getPageSize(reader.getPageN(numUltimaPagina));
            System.out.println("rectanguloPagina: " + rectanguloPagina.toString());
            float altoPagina = rectanguloPagina.getHeight();
            float anchoPagina = rectanguloPagina.getWidth();

//            PdfContentByte canvas = stamper.getOverContent(numUltimaPagina);
//            Document document = new PdfDocument();
//            ColumnText.showTextAligned(canvas,
//                    Element.ALIGN_LEFT, new Phrase("############"), 36, altoPagina - 100, 0);
//            PdfReader reader1 = stamper.getReader();
//            PdfWriter writer = stamper.getWriter();
//            for (int page = 1; page <= pdfReader.NumberOfPages; page++) {
//                PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
//                TextMarginFinder finder = new TextMarginFinder();
//                FilteredRenderListener filtered = new FilteredRenderListener(finder, new SpaceFilter());
//                parser.ProcessContent(page, new TextRenderInfoSplitter(filtered));
//                System.Console.Write("Page {0}, Bottom y {1}\n", page, finder.GetLly());
//
//            }
            PdfSignatureAppearance appearance
                    = stamper.getSignatureAppearance();
            float llx = 60;
            float lly = 5;
            Rectangle cuadroFirma = crearRectanguloFirma(altoPagina, llx, lly);
            appearance.setVisibleSignature(cuadroFirma, numUltimaPagina, "firma");
            appearance.setAcro6Layers(true);

//            appearance.setReason("Razón de la Firma");
//            appearance.setLocation("Ubicación");
            appearance.setImage(Image.getInstance(dirDocumentosFirmados + "rm_inicio.png"));
            appearance.setImageScale(-1);
            appearance.setCrypto(
                    pk, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
            appearance.setLayer2Text("Firmado por: " + nombresFirma + "\n"
                    + "Fecha: " + FechasUtil.convertirFechaA_ddMMAAAA_hhmmss(Calendar.getInstance().getTime()));

//            if (certified) {
//                appearance.setCertificationLevel(
//                        PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
//            }
//            if (graphic) {
//                appearance.setAcro6Layers(true);
//                appearance.setSignatureGraphic(Image.getInstance(RESOURCE));
//                appearance.setRender(PdfSignatureAppearance.SignatureRenderGraphicAndDescription);
//            }
            stamper.close();
            dirArchivoFirmado = dirCompletaArchivo;

        } catch (KeyStoreException ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UtilitarioPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Rectangle crearRectanguloFirma(float alturaPagina, float llx, float lly) {

        float urx = llx + ANCHO_FIRMA;//160 es el ancho de la firma
        float ury = lly + ALTO_FIRMA; //70 es el alto de la firma
        Rectangle cuadroFirma = new Rectangle(llx, lly, urx, ury);
        return cuadroFirma;
    }

    public void abrirPDFenServidorComoNuevoTab(String configDescripcion, String nombreArchivo) {

//        String url = "";
        String contentType = "application/pdf";
        String extension = "pdf";
        String path = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        try {
            //OBTENGO DIRECCION 
            ConfigDetalle cfgDet = servicioConfigDetalle.buscarPorConfig(configDescripcion);
            String directorioPrincipal = cfgDet.getConfigDetalleTexto();
//            subDirectorio=repertorioDigitalSeleccionado.getRtdPath();
//            url = "file:///" + dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            path = directorioPrincipal + nombreArchivo + "." + extension;

            Path file = Paths.get(path);
            InputStream input = null;
//            BufferedOutputStream bos = null;
            OutputStream output = ec.getResponseOutputStream();
            File file2 = new File(path);
            Long tamaño = file2.length();
            input = new BufferedInputStream(Files.newInputStream(file));

//FORMATO RESPUESTA
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + nombreArchivo + "." + extension + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

//LEER EXISTENTE Y MODIFICAR
//            Document document = new Document();
//            PdfReader reader = new PdfReader(path);
//            document.open();
//            System.out.println("# paginas: " + document.getPageNumber());
//            document.close();
            //ESCRIBIR NUEVO
//                        Document document = new Document();
//            FileOutputStream fos = new FileOutputStream(path);
//            PdfWriter writer = PdfWriter.getInstance(document, fos);
//            document.open();
//            document.add(new Paragraph("Hola mundo"));
//            document.close();
//            writer.close();
//ESCRIBIR A LA SALIDA OUTPUT
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = input.read(bytesBuffer)) > 0) {
                output.write(bytesBuffer, 0, bytesRead);
            }

//            output.write(input);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.

            //REPERTORIO DIGITAL
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
        }
    }

    public void abrirPDFenServidorComoRecurso(String subDirectorio, String nombreArchivo) {
        //este metodo carga la direccion del archivo en el disco duro, genera un inputstream del archivo
        //copia su contenido a un archivo temporal creado en la carpeta web en la que está corriendo el programa
        //y finalmente llena la direccion de este archivo temporal para ser accedido como un recurso
        String dirPrincipal = "";
//        String url = "";

        String contentType = "application/pdf";
        String extension = "pdf";
        String path = "";
        FacesContext fc = FacesContext.getCurrentInstance();
//
        ExternalContext ec = fc.getExternalContext();

        try {
            //OBTENER DIRECCION 
//            ParametroPath parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            ConfigDetalle cfgDet = servicioConfigDetalle.buscarPorConfig("MIGRACION");
            dirPrincipal = cfgDet.getConfigDetalleTexto();
//            nombreArchivo = formularioDigitalSeleccionado.getFmdNombreArchivo();
//            subDirectorio = formularioDigitalSeleccionado.getFmdPath();
            url = "file:///" + dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            path = dirPrincipal + subDirectorio + nombreArchivo + "." + extension;

            Path file1 = Paths.get(path);
            Path file2;
            String dirRealAplicacion = ec.getRealPath("/");
            String dirContexto = ec.getRequestContextPath();
            //Crear archivo y copiar
            try (InputStream input = new BufferedInputStream(Files.newInputStream(file1))) {
                Path direccion = Paths.get(dirRealAplicacion + "temp/");
                file2 = Files.createTempFile(direccion, "documento", ".pdf");
                Files.copy(input, file2, StandardCopyOption.REPLACE_EXISTING);
                url = "/" + "temp" + "/" + file2.getFileName().toString();
            } catch (Exception e) {
//                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace(System.out);
            }

            dirArchivoComoRecurso = url;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
        }
    }

    public void descargarRTFDesdeDirectorio(String subDirectorio, String nombreArchivo) {
       
        String dirPrincipal = "";
        String contentType = "application/rtf";
        String extension = "rtf";
        String path = "";        

        try {
            //OBTENER DIRECCION 
//            ParametroPath parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            ConfigDetalle cfgDet = servicioConfigDetalle.buscarPorConfig("MIGRACION");
            dirPrincipal = cfgDet.getConfigDetalleTexto();           
            path = dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            Path file1 = Paths.get(path);           
           
            //Crear buscar archivo y crear
            try (InputStream input = new BufferedInputStream(Files.newInputStream(file1))) {
                fileRTF = new BufferedInputStream(Files.newInputStream(file1));
            } catch (Exception e) {
//                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace(System.out);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
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
