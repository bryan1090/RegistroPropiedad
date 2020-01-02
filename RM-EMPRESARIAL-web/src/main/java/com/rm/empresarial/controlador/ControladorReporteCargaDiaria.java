/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.dao.FacturaDao;
import com.rm.empresarial.dao.FacturaDetalleDao;
import com.rm.empresarial.dao.FacturaDetalleImpuestoDao;
import com.rm.empresarial.dao.NotariaDao;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteResponsableDao;
import com.rm.empresarial.dao.TramiteUsuarioDao;
import com.rm.empresarial.dao.UsuarioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Bloque;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TmpAlicuota;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteResponsable;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorReporteCargaDiaria")
@ViewScoped
public class ControladorReporteCargaDiaria implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private Row row;

    @Getter
    @Setter
    private StreamedContent file;

    @Getter
    @Setter
    private TipoTramite tipotramite;

    @Getter
    @Setter
    private StreamedContent fileExcelAclar;

    @Getter
    @Setter
    private FacturaDetalleImpuesto facturaImpuesto;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @EJB
    RepertorioUsuarioDao RepertorioUsuarioDao;

    @EJB
    TipoTramiteDao tipoTramiteDao;

    @EJB
    FacturaDetalleImpuestoDao facturaImpustoDao;

    @Getter
    @Setter
    private List<Tramite> listaTramites;

    @Getter
    @Setter
    private String repertorio;

    @Getter
    @Setter
    private String numTramite;

    @Getter
    @Setter
    private RepertorioUsuario RepertorioUsuario;

    @Getter
    @Setter
    private Repertorio repertorioNum;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private Persona responsable;

    @Getter
    @Setter
    private Boolean nuevoIngreso;

    @Getter
    @Setter
    private Boolean descarga = false;

    @Getter
    @Setter
    private Boolean generar = true;

    @Getter
    @Setter
    private Persona personaSeleccionada;
    @Getter
    @Setter
    private String nombrePersona;
    @Getter
    @Setter
    private Long numeroTramite;

    @Getter
    @Setter
    private Long idParroquia;
    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleActualizado;

    @Getter
    @Setter
    private String parNombre;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private String estadoBotonNuevaPropiedad;

    @Getter
    @Setter
    private Date fecha;

    @Getter
    @Setter
    private Date fechaIni;

    @Getter
    @Setter
    private Date fechaFin;

    @Getter
    @Setter
    private List<TramiteUsuario> listaTramiteUsuario;

    @Getter
    @Setter
    private List<TramiteResponsable> listaTramiteReponsable;

    @Getter
    @Setter
    private List<TipoTramite> listaTipoTramite;

    @Getter
    @Setter
    private List<TramiteUsuario> listaReporteCargaDiaria;

    @EJB
    TramiteDao tramiteDao;

    @EJB
    PersonaDao personaDao;

    @EJB
    NotariaDao notariaDao;

    @EJB
    TramiteUsuarioDao tramiteUsuarioDao;

    @EJB
    TramiteResponsableDao tramiteResponsableDao;

    @EJB
    UsuarioDao usuarioDao;

    @EJB
    FacturaDao facturaDao;

    @EJB
    FacturaDetalleDao facturaDetalleDao;

    @Inject
    ReporteUtil reporteUtil;

    public ControladorReporteCargaDiaria() {

        System.out.println("com.rm.empresarial.controlador.ControladorMatriculacion.<init>()");
        RepertorioUsuario = new RepertorioUsuario();
        nuevoIngreso = false;
        estadoBotonNuevaPropiedad = "true";
    }

    public void excelCargaDiaria() throws ServicioExcepcion, ParseException, ParseException {
        listaReporteCargaDiaria = new ArrayList<>();
        setListaTramiteUsuario(tramiteUsuarioDao.tramiteUsuarioCarga(fechaIni, fechaFin));
        setListaTipoTramite(tipoTramiteDao.tramiteUsuarioCarga(fechaIni, fechaFin));

        for (int i = 0; i < getListaTipoTramite().size(); i++) {
            String contrato = getListaTipoTramite().get(i).getTtrDescripcion();
            getListaTramiteUsuario().get(i).setTusEstadoDetalle(contrato);
        }
        boolean estado = false;
        for (TramiteUsuario tramitus : listaTramiteUsuario) {
            if (listaReporteCargaDiaria.size() > 0) {
                for (int i = 0; i < listaReporteCargaDiaria.size(); i++) {
                    if (tramitus.getTraNumero().getTraNumero().equals(listaReporteCargaDiaria.get(i).getTraNumero().getTraNumero()) && tramitus.getTusEstadoDetalle().equals(listaReporteCargaDiaria.get(i).getTusEstadoDetalle())) {
                        estado = false;
                    } else {
                        estado = true;
                    }
                }
                if (estado) {
                    listaReporteCargaDiaria.add(tramitus);
                }
            } else {
                listaReporteCargaDiaria.add(tramitus);
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            Path path = FileSystems.getDefault().getPath(
                    FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "\\resources\\plantillaExcel\\cargaDiaria.xlsx");

            String excelFilePath = path.toString();
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            for (int fila = 2; fila <= sheet.getLastRowNum() + 1; fila++) {
                if (fila >= 2 && fila < lastRowNum) {
                    sheet.shiftRows(fila + 1, lastRowNum, -1);
                }
                if (fila == lastRowNum) {
                    Row removingRow = sheet.getRow(fila);
                    if (removingRow != null) {
                        sheet.removeRow(removingRow);

                    }
                }
            }

            CellStyle descripcion = workbook.createCellStyle();
            descripcion.setBorderBottom(BorderStyle.THIN);
            descripcion.setBorderTop(BorderStyle.THIN);
            descripcion.setBorderLeft(BorderStyle.THIN);
            descripcion.setBorderRight(BorderStyle.THIN);
            descripcion.setWrapText(true);
            descripcion.setVerticalAlignment(VerticalAlignment.TOP);

            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Font letra = workbook.createFont();
            letra.setBold(true);
            letra.setFontHeightInPoints((short) 11);
            letra.getBold();
            letra.setColor(HSSFColor.HSSFColorPredefined.BLUE_GREY.getIndex());

            CellStyle eader = workbook.createCellStyle();
            eader.setFont(letra);

            int i = 1;
            for (TramiteUsuario tramiteU : listaReporteCargaDiaria) {
                row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(1);
                cell.setCellValue(tramiteU.getTraNumero().getTraNumero());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(tramiteU.getUsuId().getPerId().getPerApellidoPaterno() + tramiteU.getUsuId().getPerId().getPerApellidoMaterno() + tramiteU.getUsuId().getPerId().getPerNombre());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(tramiteU.getTusEstadoDetalle());
                cell.setCellStyle(style);

                i++;
            }
            if (sheet.getRow(1) == null) {
                Row header = sheet.createRow(1);
                header.createCell(0).setCellValue("Desde:  " + sdf.format(fechaIni) + "  Hasta: " + sdf.format(fechaFin));
                header.getCell(0).setCellStyle(eader);
            } else {
                Row header = sheet.getRow(1);
                header.createCell(0).setCellValue("Desde:  " + sdf.format(fechaIni) + "  Hasta: " + sdf.format(fechaFin));
                header.getCell(0).setCellStyle(eader);
            }
            if (sheet.getRow(2) == null) {
                Row usuario = sheet.createRow(2);
                usuario.createCell(0).setCellValue("Generado por " + dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                usuario.getCell(0).setCellStyle(eader);
            } else {
                Row usuario = sheet.getRow(2);
                usuario.createCell(0).setCellValue("Generado por " + dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                usuario.getCell(0).setCellStyle(eader);
            }

            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "CargaDiaria.xlsx" + "\""); //Se descarga desde el navegador

            OutputStream output = ec.getResponseOutputStream();
            workbook.write(output);//Se escribe el documento
            fc.responseComplete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
