package com.rm.empresarial.controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.dao.RazonNuevaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.modelo.Razon;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.MargenServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorBusquedaCertificadoRazon")
@ViewScoped
public class ControladorBusquedaCertificadoRazon implements Serializable {

    @Inject
    private PdfTempUtil pdfTempUtil;

    @EJB
    private RazonNuevaDao servicioRazon;
    @EJB
    private CertificadoServicio servicioCertificado;
    @EJB
    private UsuarioServicio servicioUsuario;
    @EJB
    private MargenServicio servicioMargen;
    @EJB
    private RepertorioServicio repertorioServicio;

    @Getter
    @Setter
    private String usuario;
    @Getter
    @Setter
    private Date fecha;
    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private Margen margen;
    
    @Getter
    @Setter
    private List<Tramite> listaTramites;
    
    @Getter
    @Setter
    private List<Repertorio> listaRepertorio;
    
   
    @Getter
    @Setter
    private List<Razon> listaRazones;
    @Getter
    @Setter
    private List<Certificado> listaCertificados;
    @Getter
    @Setter
    private List<Usuario> listaUsuarios;

    @PostConstruct
    public void postControladorBusquedaCertificadoRazon() {
        setListaUsuarios(servicioUsuario.listarTodoSinWeb());
    }

    public ControladorBusquedaCertificadoRazon() {
        listaRazones = new ArrayList<>();
        listaCertificados = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
        listaTramites = new ArrayList<>();
        listaRepertorio = new ArrayList<>();
    }

    public void buscarRazones_Y_Certificados() {
        try {
            listaCertificados.clear();
            listaRazones.clear();
            listaTramites.clear();
            listaRepertorio.clear();
            setListaRazones(servicioRazon.obtenerPorFechaRepertorio_Usuario_Tramite(usuario, fecha));
            setListaCertificados(servicioCertificado.listarPorFecha_Y_Usuario(usuario, fecha));
            for (Razon razon : listaRazones) {
                if(!listaTramites.contains(razon.getTraNumero())){
                    listaTramites.add(razon.getTraNumero());
                }
            }
            for (Tramite tram : listaTramites) {
                Repertorio repert = repertorioServicio.encontrarPorTramite(tram.getTraNumero());
                if(!listaRepertorio.contains(repert)){
                    listaRepertorio.add(repert);
                }
            }
        } catch (ServicioExcepcion e) {
            System.out.print(e);
        }
    }

    public void imprimirRazones() {
        try {
            
            String nombreArchivo = "Razones";//nombre del archivo
            FileOutputStream fos = new FileOutputStream(pdfTempUtil.directorio(nombreArchivo));
            setMargen(servicioMargen.obtenerPorMarTipo("RAZ"));
            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());//creo nuevo documento pdf, con tamaño y márgenes
            PdfWriter writer = PdfWriter.getInstance(document, fos);//declaro guardar el documento en el dir creado
            document.open();//abro el documento para editar
            //*****------------------------------
            //PARRAFO ACTA
            for (Razon razon : listaRazones) {
                Repertorio repert = new Repertorio();
                repert = repertorioServicio.encontrarRecientePorTramite(razon.getTraNumero().getTraNumero());
                if (razon.getRazHtml() != null) {
                    document.newPage();
                    Paragraph parrafo = new Paragraph();
                    parrafo = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafo, razon.getRazHtml());
                    parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                    document.add(parrafo);
                    for(int i = 1; i <= repert.getRepNumeroImpresion()-2; i++){
                        document.newPage();
                        document.add(parrafo);
                    }
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

    public void imprimirCertificados() {
        try {
            String nombreArchivo = "Certificados";//nombre del archivo
            FileOutputStream fos = new FileOutputStream(pdfTempUtil.directorio(nombreArchivo));
            setMargen(servicioMargen.obtenerPorMarTipo("CER"));
            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());//creo nuevo documento pdf, con tamaño y márgenes
            PdfWriter writer = PdfWriter.getInstance(document, fos);//declaro guardar el documento en el dir creado
            document.open();//abro el documento para editar
            //*****------------------------------
            //PARRAFO ACTA
            for (Certificado certificado : listaCertificados) {
                if (certificado.getCerCertificado() != null) {
                    document.newPage();
                    Paragraph parrafo = new Paragraph();
                    parrafo = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafo, certificado.getCerCertificado());
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
