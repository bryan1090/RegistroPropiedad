/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.TipoSuspensionDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteSuspensionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteSuspension;
import com.rm.empresarial.servicio.TipoSuspensionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteSuspensionServicio;
import com.rm.empresarial.servicio.TramiteUsuarioServicio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorBuscarRazonesSuspension")
@ViewScoped
public class ControladorBuscarRazonesSuspension implements Serializable {

	@Inject
	private DataManagerSesion dataManagerSesion;

	@Inject
	ReporteUtil reporteUtil;

	@Inject
	private TramiteUtil tramiteUtil;

	@EJB
	private TipoSuspensionServicio servicioTipoSuspension;
	@EJB
	private TramiteServicio servicioTramite;

	@EJB
	private TramiteSuspensionServicio servicioTramiteSuspension;

	@EJB
	private TramiteUsuarioServicio servicioTramiteUsuario;

	@Getter
	@Setter
	private Long numTramite;
	@Getter
	@Setter
	private Date fechaInicio;
	@Getter
	@Setter
	private Date fechaFin;
	@Getter
	@Setter
	private Boolean rendNumTramite = false;
	@Getter
	@Setter
	private Boolean rendFechaTramite = false;
	@Getter
	@Setter
	private String tipoBusquedaStr;

	@Getter
	@Setter
	private List<TipoSuspension> listaTipoSuspension;

	@Getter
	@Setter
	private List<TipoSuspension> listaTipoSuspensionBDD;

	@Getter
	@Setter
	private List<String> listaIDTipoSuspensionSeleccionados;

	@Getter
	@Setter
	private List<TipoSuspension> listaTipoSuspensionSeleccionados;

	@Getter
	@Setter
	private TipoSuspension nuevoTipoSuspension;

	@Getter
	@Setter
	private Tramite tramiteSeleccionado;
	@Getter
	@Setter
	private List<Tramite> listaTramites;

	//
	@Getter
	@Setter
	private List<TramiteSuspension> listaTramiteSuspension;

	@Getter
	@Setter
	private Boolean mostrarGrid1;
	@Getter
	@Setter
	private Boolean mostrarGrid2;

	@Getter
	@Setter
	private String usuarioLogeado;
	@Getter
	@Setter
	private StreamedContent pdfRazanesSuspension;

	@EJB
	private InstitucionDao institucionDao;
	@EJB
	private TipoSuspensionDao tipoSuspensionDao;
	@EJB
	private TramiteSuspensionDao tramiteSuspensionDao;
	@EJB
	private TramiteDao tramiteDao;

	@Getter
	@Setter
	private InputStream stream;
	@Getter
	@Setter
	private InputStream streamPDFDownload;

	public ControladorBuscarRazonesSuspension() {
		listaTramites = new ArrayList<>();
		listaTipoSuspension = new ArrayList<>();
		listaTramiteSuspension = new ArrayList<>();
		listaTipoSuspensionSeleccionados = new ArrayList<>();

	}

	@PostConstruct
	public void postConstructor() {
		mostrarGrid1 = false;
		usuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuLogin();

	}

	public void tipoBusqueda() {
		switch (tipoBusquedaStr) {
			case "numTramite":
				rendNumTramite = true;
				rendFechaTramite = false;
				break;
			case "fechaTramite":
				rendNumTramite = false;
				rendFechaTramite = true;
				break;
		}
		listaTramiteSuspension.clear();
		listaTramites.clear();

	}

	public void buscarRazonSuspesion() throws ServicioExcepcion {
		listaTramiteSuspension.clear();
		listaTramites.clear();
		switch (tipoBusquedaStr) {
			case "numTramite":
				tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(numTramite);
				listaTramites.add(tramiteSeleccionado);
				//listaTramiteSuspension = tramiteSuspensionDao.buscarTramitesSuspensionPorNumTram(numTramite);
				break;
			case "fechaTramite":
				listaTramites = tramiteDao.listarPorRangoFecha(fechaInicio, fechaFin);
				break;
		}

	}

	public void cargarTramiteSuspension(Tramite tramite) throws ServicioExcepcion, DocumentException, BadElementException, IOException {
		tramiteSeleccionado = tramite;
		listaTramiteSuspension = tramiteSuspensionDao.buscarTramitesSuspensionPorNumTram(tramite.getTraNumero());
		pdfListaSuspension();
	}

	public void descargarPDF() throws ServicioExcepcion {
		System.out.println("com.rm.empresarial.controlador.ControladorRazonesSuspensionPorUsuario.descargarPDF()");
		String fileName = "RazonesSuspension-Tramite " + tramiteSeleccionado.getTraNumero() + ".pdf";
		pdfRazanesSuspension = new DefaultStreamedContent(streamPDFDownload, "application/pdf", fileName);
	}

	public void pdfListaSuspension() throws DocumentException, BadElementException, IOException {
		try {
			Institucion institucion = institucionDao.obtenerInstitucion();
			String pathImagen = institucion.getInsLogo();
			String nomInstitucion = institucion.getInsNombre();
			String usuarioLog = dataManagerSesion.getUsuarioLogeado().getUsuLogin();

			Date fechaRep = com.ibm.icu.util.Calendar.getInstance().getTime();
//            Map<String, Object> parametros = new HashMap<>();
//            parametros.put("NumTramite", tramiteSeleccionado.getTraNumero().intValue());
//            parametros.put("pathImagen", pathImagen);
//            parametros.put("institucion", nomInstitucion);
//            parametros.put("fechaReporte", fechaRep);
//            parametros.put("usuario", usuarioLog);
//            try {
//                reporteUtil.imprimirReporteEnPDF("RazonesSuspension", "RazonesSuspensiòn-Tràmite" + tramiteSeleccionado.getTraNumero(), parametros);
//            } catch (JRException | IOException | NamingException | SQLException ex) {
//
//                Logger.getLogger(RecepcionDocumentacionControlador.class
//                        .getName()).log(Level.SEVERE, null, ex);
//                addErrorMessage("ERROR AL GENERAR PDF");
//
//            }
			Document pdf = new Document(PageSize.LETTER);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer;
			writer = PdfWriter.getInstance(pdf, baos);
			if (!pdf.isOpen()) {
				pdf.open();
			}

			String name = pathImagen;
			//String name = "\resources\\imagenes\\Logo_Municipio_Rumiñahui.png";

			Image logo = Image.getInstance(name);
			logo.setAlignment(Element.ALIGN_CENTER);
			logo.scaleToFit(100, 100);
			pdf.add(logo);

			//Añadir contenido a PDF
			Paragraph institucionPDF = new Paragraph(institucion.getInsNombre());
			Paragraph titulo = new Paragraph("RAZONES DE SUSPENSIÓN - Nro. Trámite: " + tramiteSeleccionado.getTraNumero());
			institucionPDF.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			pdf.add(institucionPDF);
			pdf.add(titulo);
			pdf.add(new Paragraph("\n"));
			Paragraph numTramite = new Paragraph("Nro. Trámite: " + tramiteSeleccionado.getTraNumero());
			pdf.add(numTramite);
			Paragraph separador = new Paragraph("________________________________________________________________________________");
			pdf.add(separador);
			pdf.add(new Paragraph("\n"));
			int count = 1;
			TipoSuspension tipoSuspension;
			List<TramiteSuspension> listTramSusp = new ArrayList<>();

			if (listaIDTipoSuspensionSeleccionados != null) {
				listTramSusp.clear();
				listTramSusp = tramiteSuspensionDao.buscarTramitesSuspensionPorNumTramYporEstadoS(tramiteSeleccionado.getTraNumero());
				for (TramiteSuspension tramiteSuspension : listTramSusp) {
					int numero = count++;
					pdf.add(new Paragraph(numero + ". " + tramiteSuspension.getTmsNombre()));
				}

			}
			count = 1;
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{5, 65, 15, 15});
			table.setHeaderRows(4);
			Font fuente = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
			if (listaTramiteSuspension != null) {
				PdfPCell cell1;
				cell1 = new PdfPCell(new Phrase(""));
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				PdfPCell cell2;
				cell2 = new PdfPCell(new Phrase(""));
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				PdfPCell cell3;
				cell3 = new PdfPCell(new Phrase("CUMPLE", fuente));
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				PdfPCell cell4;
				cell4 = new PdfPCell(new Phrase("NO CUMPLE", fuente));
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				for (TramiteSuspension tramSusp : listaTramiteSuspension) {
					PdfPCell cell = new PdfPCell();
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell = new PdfPCell(new Phrase(String.valueOf(count))));
					table.addCell(cell = new PdfPCell(new Phrase(tramSusp.getTmsNombre())));
					if ("N".equals(tramSusp.getTmsEstado())) {
						table.addCell(cell = new PdfPCell(new Phrase("CUMPLE")));
						table.addCell("");
					} else if ("S".equals(tramSusp.getTmsEstado())) {
						table.addCell(cell = new PdfPCell(new Phrase("")));
						table.addCell("NO CUMPLE");
					}
					int numero = count++;
				}
				pdf.add(table);
				pdf.add(new Paragraph("\n"));
				pdf.add(new Paragraph("\n"));
				pdf.add(new Paragraph("Responsable:    " + dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
				pdf.add(new Paragraph("\n"));
				String info = "NOTA: POR FAVOR CUANDO VUELVA A INGRESAR EL TRÀMITE ADJUNTE TODA LA DOCUMENTACIÒN, INCLUIDA ESTA HOJA DE OBSERVACIONES.";
				String info1 = "REVISE QUE NINGÚN DOCUMENTO SE ENCUENTRE CADUCADO.";

				pdf.add(new Paragraph(info + info1, fuente));
			}
			pdf.close();
			String fileName = "RazonesSuspension-Tramite " + tramiteSeleccionado.getTraNumero() + ".pdf";
			stream = new ByteArrayInputStream(baos.toByteArray());
			streamPDFDownload = new ByteArrayInputStream(baos.toByteArray());

		} catch (ServicioExcepcion e) {
			System.out.println(e);
			JsfUtil.addErrorMessage(e, "Error al generar el archivo PDF");
		}

	}

}
