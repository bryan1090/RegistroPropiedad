/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;
import com.rm.empresarial.controlador.util.EnviarEmailController;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.ConfigDetalleDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.TipoSuspensionDao;
import com.rm.empresarial.dao.TramiteSuspensionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteSuspension;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.TipoSuspensionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteSuspensionServicio;
import com.rm.empresarial.servicio.TramiteUsuarioServicio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
 * @author Bryan_Mora
 */
@Named(value = "controladorRazonesSuspensionPorUsuario")
@ViewScoped
public class ControladorRazonesSuspensionPorUsuario implements Serializable {

	@Inject
	private DataManagerSesion dataManagerSesion;

	@Inject
	private TramiteUtil tramiteUtil;

	@Inject
	ReporteUtil reporteUtil;

	@EJB
	private TipoSuspensionServicio servicioTipoSuspension;
	@EJB
	private TramiteServicio servicioTramite;

	@EJB
	private TramiteSuspensionServicio servicioTramiteSuspension;

	@EJB
	private TramiteUsuarioServicio servicioTramiteUsuario;

	@EJB
	private PersonaServicio servicioPersona;

	@Inject
	private EnviarEmailController utilCorreo;

	@Getter
	@Setter
	private Long numProforma;

	@Getter
	@Setter
	private String observacion;

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

	//segundo many checkbox (SEGUNDO CASO)
	@Getter
	@Setter
	private List<TramiteSuspension> listaTramiteSuspension;

	@Getter
	@Setter
	private List<TramiteSuspension> listaTramiteSuspensionesAntes;

	@Getter
	@Setter
	private List<String> listaIDTramiteSuspensionSeleccionados;
	@Getter
	@Setter
	private Boolean mostrarGrid1;
	@Getter
	@Setter
	private Boolean mostrarGrid2;

	@Getter
	@Setter
	private TramiteSuspension nuevoTramiteSuspension;

	@Getter
	@Setter
	private String usuarioLogeado;
	@Getter
	@Setter
	private Boolean disabledBtnImprimir = true;
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
	private ConfigDetalleDao configDetalleDao;
	@Getter
	@Setter
	private InputStream stream;
	@Getter
	@Setter
	private InputStream streamPDFDownload;

	public ControladorRazonesSuspensionPorUsuario() {
		System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.<init>()");
		listaTipoSuspension = new ArrayList<>();
		listaTipoSuspensionSeleccionados = new ArrayList<>();
		listaIDTramiteSuspensionSeleccionados = new ArrayList<>();
	}

	@PostConstruct
	public void postConstructor() {
		mostrarGrid1 = false;
		mostrarGrid2 = false;

		usuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
		System.out.println("#items seleccionados en lista id tramite suspension: " + listaIDTramiteSuspensionSeleccionados.size());
//        try {
////            listaTipoSuspensionBDD = servicioTipoSuspension.listarTodo();
//
//        } catch (ServicioExcepcion ex) {
//            Logger.getLogger(RazonesSuspensionController.class.getName()).log(Level.SEVERE, null, ex);
//        }
	}

	public void agregarNuevo() {
		System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.agregarNuevo()");

		String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();

		if (!observacion.isEmpty()) {
			if (mostrarGrid2) {
				Long idNuevo = servicioTipoSuspension.asignarID();
				nuevoTramiteSuspension = new TramiteSuspension(idNuevo, nombreUsuario, Calendar.getInstance().getTime(), observacion.toUpperCase());
				listaTramiteSuspension.add(nuevoTramiteSuspension);
				listaIDTramiteSuspensionSeleccionados.add(idNuevo.toString());

			} else {
				Long idNuevo = servicioTipoSuspension.asignarID();
				nuevoTipoSuspension = new TipoSuspension(idNuevo, observacion.toUpperCase(), "A", nombreUsuario, Calendar.getInstance().getTime());
				listaTipoSuspension.add(nuevoTipoSuspension); //agregando nuevo objecto a la lista select items
				listaTipoSuspensionSeleccionados.add(nuevoTipoSuspension);//nuevo tipo suspension agregado lista de objetos
				listaIDTipoSuspensionSeleccionados.add(idNuevo.toString());//nuevo tipo suspencion como seleccionado
			}

		}
		observacion = "";
	}

	public void buscarTramite() throws ServicioExcepcion {
		System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.buscarTramite()");
		try {
			if (servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporEstadoS(getNumProforma()).size() > 0) {
//                if (servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporEstadoS(numProforma).size() > 0) {
				listaTramiteSuspension = servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporUsuario(numProforma,
						dataManagerSesion.getUsuarioLogeado().getUsuId());
				listaTramiteSuspensionesAntes = servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporUsuario(numProforma,
						dataManagerSesion.getUsuarioLogeado().getUsuId());
				if (listaTramiteSuspension.size() > 0) {
					for (TramiteSuspension tramSusp : listaTramiteSuspension) {
						System.out.println("id: " + tramSusp.getTmsId() + " estado: " + tramSusp.getTmsEstado());
						if (tramSusp.getTmsEstado().trim().equals("S")) {
							if (listaIDTramiteSuspensionSeleccionados == null) {
								listaIDTramiteSuspensionSeleccionados = new ArrayList<>();
							}
							listaIDTramiteSuspensionSeleccionados.add(tramSusp.getTmsId().toString());
						}
					}
					tramiteSeleccionado = servicioTramite.buscarTramitePorNumero(getNumProforma());
					System.out.println("tramite seleccionado: " + tramiteSeleccionado.getTraNumero());
					mostrarGrid2 = true;
					mostrarGrid1 = false;
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//                            "Se encontraron " + listaTramiteSuspension.size() + " trámites suspensión", ""));
				} else {
					JsfUtil.addErrorMessage("Tramite suspendido por otro funcionario");
				}

//                } else {
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//                            "Todos los trámites suspensión están inactivos", ""));
//                }
			} else if (servicioTramite.buscarTramitePor_numero_estado_Susp(getNumProforma()) != null) {
				tramiteSeleccionado = servicioTramite.buscarTramitePor_numero_estado_Susp(getNumProforma());
				if (servicioTramiteUsuario.buscarTramitePorUsuarioYNumTramiteActivos(tramiteSeleccionado, dataManagerSesion.getUsuarioLogeado()) != null) {
					listaTipoSuspension = servicioTipoSuspension.listarPorTipoRevision();
					mostrarGrid1 = true;
					mostrarGrid2 = false;
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Trámite asignado a otro funcionario", ""));
					//JsfUtil.addErrorMessage("Tramite asignado a otro funcionario");
				}

//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Numero de Trámite encontrado ", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Trámite no existe", ""));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Tramite no existe");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Trámite no existe", ""));
		}

	}

	public void salir() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

	}

	public void insertarTramitesSuspension() throws DocumentException, BadElementException, IOException {
		System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.insertarTramitesSuspension()");
		String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();

		Boolean exito = false;

		if (tramiteSeleccionado != null) {

			if (listaTipoSuspensionSeleccionados.size() + listaIDTipoSuspensionSeleccionados.size() > 0) {
				for (TipoSuspension tipSus : listaTipoSuspension) {
					if (listaIDTipoSuspensionSeleccionados.contains(tipSus.getTpsId().toString())) {
						TramiteSuspension nuevoTraSus = new TramiteSuspension(1L, nombreUsuario, Calendar.getInstance().getTime(), tipSus.getTpsNombre());

						tramiteSeleccionado.setTraEstadoRegistro("RZ");
						nuevoTraSus.setTraNumero(tramiteSeleccionado);
						nuevoTraSus.setTmsTpsId(tipSus.getTpsId());
						nuevoTraSus.setTmsEstado("S");
						nuevoTraSus.setTrmTipo("RVT");
						System.out.println("tram num " + tramiteSeleccionado.getTraNumero());

						try {
							servicioTramiteSuspension.create(nuevoTraSus);
							servicioTramite.edit(tramiteSeleccionado);

							exito = true;
							tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
									tramiteSeleccionado.getTraNumero().toString(),
									"Tramite " + tramiteSeleccionado.getTraNumero() + " suspendido por: "
									+ tipSus.getTpsNombre(),
									tramiteSeleccionado.getTraEstado(),
									tramiteSeleccionado.getTraEstadoRegistro(), null);
						} catch (Exception e) {
							exito = false;
						}

					}
				}
				if (exito) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Trámite(s) suspensión ingresado(s)!", ""));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:¡No se pudo ingresar el tramite(s) de suspension!", ""));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe seleccionar los tipos de suspensión", ""));

			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe ingresar un número de trámite", ""));
		}
		//numProforma = null;
		//listaIDTipoSuspensionSeleccionados = new ArrayList<>();
		//listaTipoSuspensionSeleccionados = new ArrayList<>();
		//tramiteSeleccionado = null;
		mostrarGrid1 = true;
		disabledBtnImprimir = false;
		pdfListaSuspension();
		enviarCorreoConPdfAdjunto();
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

			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setWidths(new int[]{5, 65, 15, 15});
			table.setHeaderRows(4);
			Font fuente = new Font(FontFamily.HELVETICA, 10, Font.BOLD);

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
				for (TramiteSuspension tramiteSuspension : listTramSusp) {
					PdfPCell cell = new PdfPCell();
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell = new PdfPCell(new Phrase(String.valueOf(count))));
					table.addCell(cell = new PdfPCell(new Phrase(tramiteSuspension.getTmsNombre())));
					if ("N".equals(tramiteSuspension.getTmsEstado())) {
						table.addCell(cell = new PdfPCell(new Phrase("CUMPLE")));
						table.addCell("");
					} else if ("S".equals(tramiteSuspension.getTmsEstado())) {
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
				Font fuente1 = new Font(FontFamily.HELVETICA, 10, Font.UNDERLINE);
				String info = "NOTA: POR FAVOR CUANDO VUELVA A INGRESAR EL TRÀMITE ADJUNTE TODA LA DOCUMENTACIÒN, INCLUIDA ESTA HOJA DE OBSERVACIONES.";
				String info1 = "REVISE QUE NINGÚN DOCUMENTO SE ENCUENTRE CADUCADO.";

				pdf.add(new Paragraph(info + info1, fuente));
			}
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
				Font fuente1 = new Font(FontFamily.HELVETICA, 10, Font.UNDERLINE);
				String info = "NOTA: POR FAVOR CUANDO VUELVA A INGRESAR EL TRÀMITE ADJUNTE TODA LA DOCUMENTACIÒN, INCLUIDA ESTA HOJA DE OBSERVACIONES.";
				String info1 = "REVISE QUE NINGÚN DOCUMENTO SE ENCUENTRE CADUCADO.";

				pdf.add(new Paragraph(info + info1, fuente));
			}
			pdf.close();
			stream = new ByteArrayInputStream(baos.toByteArray());
			streamPDFDownload = new ByteArrayInputStream(baos.toByteArray());

		} catch (ServicioExcepcion e) {
			System.out.println(e);
			JsfUtil.addErrorMessage(e, "Error al generar el archivo PDF");
		}
	}

	public void enviarCorreoConPdfAdjunto() {

		try {
			String idPersona = tramiteSeleccionado.getTraPerIdentificacion();
			Persona personaSel = servicioPersona.encontrarPersonaPorIdentificacion(idPersona);
			if (personaSel != null) {
				String asunto = "Suspensión del trámite: " + tramiteSeleccionado.getTraNumero();
				String mensaje = "Estimado " + personaSel.getPerApellidoMaterno()
						+ " " + personaSel.getPerApellidoPaterno()
						+ " " + personaSel.getPerNombre() + ", su trámite con numero " + tramiteSeleccionado.getTraNumero()
						+ " ha sido suspendido por las razones adjuntas en el documento.";
				String correo = "" + personaSel.getPerEmail();
				System.out.println("Enviando correo a: " + correo);
				if (correo != null && !correo.isEmpty() && !correo.equals("")) {
					utilCorreo.enviaEmailConAdjunto(correo, asunto, mensaje, "pdf", stream, "RazonesDeSuspension.pdf", " ");
				} else {
					ConfigDetalle confDet = new ConfigDetalle();
					try {
						confDet = configDetalleDao.buscarPorConfig("CORREO POR DEFECTO");
						utilCorreo.enviaEmailConAdjunto(confDet.getConfigDetalleTexto(), asunto, mensaje, "pdf", stream, "RazonesDeSuspension.pdf", " ");
					} catch (Exception e) {
						System.out.println(e);
					}

				}
			} else {
				JsfUtil.addWarningMessage("Persona no encontrada. No se envió correo electrónico");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
//            String correo = getPersona().getPerEmail().trim();
//            String asunto = "Ingreso Tramite " + getTramite().getTraNumero();
//            String mensaje = "Se ingresó el tramite, desde: " + getTramite().getTraInicial()
//                    + " hasta: " + getTramite().getTraFinal();

	}

	public void descargarPDF() throws ServicioExcepcion, JRException {
        Institucion institucion = institucionDao.obtenerInstitucion();
        String pathImagen = institucion.getInsLogo();
        String nomInstitucion = institucion.getInsNombre();
        String usuarioLog = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
        String tipo = "RVT";

        Date fechaRep = com.ibm.icu.util.Calendar.getInstance().getTime();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("NumTramite", tramiteSeleccionado.getTraNumero().intValue());
        parametros.put("pathImagen", pathImagen);
        parametros.put("institucion", nomInstitucion);
        parametros.put("fechaReporte", fechaRep);
        parametros.put("usuario", usuarioLog);
        parametros.put("Estado", tipo);
        try {
            reporteUtil.imprimirReporteEnPDF("RazonesSuspension", "RazonesSuspensiòn-Tràmite" + tramiteSeleccionado.getTraNumero(), parametros);
        } catch (JRException | IOException | NamingException | SQLException ex) {

            Logger.getLogger(RecepcionDocumentacionControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
            addErrorMessage("ERROR AL GENERAR PDF");

        }
	}

	public void insertarTramitesSuspensionCaso2() throws ServicioExcepcion, DocumentException, BadElementException, IOException {
//        String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
		Boolean exito = false;

		if (tramiteSeleccionado != null) {
//            if (listaTipoSuspensionSeleccionados.size() + listaIDTramiteSuspensionSeleccionados.size() > 0) {
			if (listaIDTramiteSuspensionSeleccionados.isEmpty()) {
				tramiteSeleccionado.setTraEstadoRegistro("RI");
			} else {
				tramiteSeleccionado.setTraEstadoRegistro("RZ");

			}

			for (TramiteSuspension tramSusp : listaTramiteSuspension) {
				if (listaIDTramiteSuspensionSeleccionados.contains(tramSusp.getTmsId().toString())) {
					tramSusp.setTmsEstado("S");

//                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, 
//                            tramiteSeleccionado.getTraNumero().toString(), 
//                            "Tramite "+tramiteSeleccionado.getTraNumero()+ "suspendido por: "
//                                    +tramSusp.getTmsNombre()
//                                    , tramiteSeleccionado.getTraEstado(), 
//                                    tramiteSeleccionado.getTraEstadoRegistro());  
				} else {
					tramSusp.setTmsEstado("N");
//                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, 
//                            tramiteSeleccionado.getTraNumero().toString(), 
//                            "Tramite "+tramiteSeleccionado.getTraNumero()+ "levantada suspensión por: "
//                                    +tramSusp.getTmsNombre()
//                                    , tramiteSeleccionado.getTraEstado(), 
//                                    tramiteSeleccionado.getTraEstadoRegistro());
				}

				//*--
				try {
					tramSusp.setTrmTipo("RVT");
					tramSusp.setTraNumero(tramiteSeleccionado);
					servicioTramiteSuspension.edit(tramSusp);
					servicioTramite.edit(tramiteSeleccionado);
					exito = true;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (exito) {

				//*-- Se guarda un registro de si se levanto o se añadio una suspension. Y tambien nuevas suspensiones
				for (TramiteSuspension tramiteSuspensionDespues : listaTramiteSuspension) {
					for (TramiteSuspension tramSuspAntes : listaTramiteSuspensionesAntes) {
						if (tramSuspAntes.getTmsId().equals(tramiteSuspensionDespues.getTmsId())
								&& !tramSuspAntes.getTmsEstado().equals(tramiteSuspensionDespues.getTmsEstado())) {
							if (tramSuspAntes.getTmsEstado().equals("S")) {
								tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
										tramiteSeleccionado.getTraNumero().toString(),
										"Tramite " + tramiteSeleccionado.getTraNumero() + "suspendido por: "
										+ tramSuspAntes.getTmsNombre(),
										tramiteSeleccionado.getTraEstado(),
										tramiteSeleccionado.getTraEstadoRegistro(), null);
							} else {
								tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
										tramiteSeleccionado.getTraNumero().toString(),
										"Tramite " + tramiteSeleccionado.getTraNumero() + "levantada suspensión por: "
										+ tramSuspAntes.getTmsNombre(),
										tramiteSeleccionado.getTraEstado(),
										tramiteSeleccionado.getTraEstadoRegistro(), null);
							}
						}
					}
					// codigo para guardar nuevos
					if (!listaTramiteSuspensionesAntes.contains(tramiteSuspensionDespues)) {
						if (tramiteSuspensionDespues.getTmsEstado().equals("S")) {
							tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
									tramiteSeleccionado.getTraNumero().toString(),
									"Tramite " + tramiteSeleccionado.getTraNumero() + "suspendido por: "
									+ tramiteSuspensionDespues.getTmsNombre(),
									tramiteSeleccionado.getTraEstado(),
									tramiteSeleccionado.getTraEstadoRegistro(), null);
						} else {
							tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
									tramiteSeleccionado.getTraNumero().toString(),
									"Tramite " + tramiteSeleccionado.getTraNumero() + "levantada suspensión por: "
									+ tramiteSuspensionDespues.getTmsNombre(),
									tramiteSeleccionado.getTraEstado(),
									tramiteSeleccionado.getTraEstadoRegistro(), null);
						}
					}
				}

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Trámite(s) suspensión ingresado(s)!", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:¡No se pudo actualizar los Trámites Suspension!", ""));
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe ingresar un número de trámite", ""));
		}
		//numProforma = null;
//        listaIDTramiteSuspensionSeleccionados = new ArrayList<>();
//        listaTramiteSuspension = new ArrayList<>();
//        tramiteSeleccionado = null;
		mostrarGrid2 = true;
		disabledBtnImprimir = false;

		pdfListaSuspension();
		enviarCorreoConPdfAdjunto();

	}

	public void recargarPagina() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/suspension/RazonesSuspensionPorUsuario.xhtml");
	}

	//CUSTOM CONVERTER
//    public TipoSuspension getTipoSuspension(Long id) throws ServicioExcepcion {
//        return servicioTipoSuspension.buscarTipoSuspensionPorId(id);
//    }
//    public List<TipoSuspension> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
//    public List<TipoSuspension> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
//    @FacesConverter(value="converter", forClass = TipoSuspension.class)
//    public static class TipoSuspensionControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            RazonesSuspensionController controller = (RazonesSuspensionController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "razonesSuspensionController");
//            Object objeto;
//            try {
//                objeto=controller.getTipoSuspension(getKey(value));
//                return objeto;
//            } catch (ServicioExcepcion ex) {
//                Logger.getLogger(RazonesSuspensionController.class.getName()).log(Level.SEVERE, null, ex);
//                return null;
//
//            }
//        }
//
//        Long getKey(String value) {
//            Long key;
//            key = new Long(value);
//            return key;
//        }
//
//        String getStringKey(Long value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof TipoSuspension) {
//                TipoSuspension o = (TipoSuspension) object;
//                return getStringKey(o.getTpsId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoSuspension.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
