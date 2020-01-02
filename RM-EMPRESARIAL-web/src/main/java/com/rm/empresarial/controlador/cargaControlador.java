/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.bb.UsuarioControllerBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.dao.CargaLaboralDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.TramiteUsuarioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.primefaces.component.focus.Focus.PropertyKeys.context;

@Named("cargaControlador")
@ViewScoped
public class cargaControlador implements Serializable {

    @Inject
    @Getter
    @Setter
    private UsuarioControllerBb usuarioControladorBb;

    @Getter
    @Setter
    private Date fechaIni;

    @Getter
    @Setter
    private Date fechaFin;

    @Getter
    @Setter
    private String rolId;

    @Getter
    @Setter
    private String pathImagen;

    @Getter
    @Setter
    private List<CargaLaboral> listaCargaLaboral;

    @Getter
    @Setter
    private List<TramiteUsuario> listaTramiteUsuario;

    @Getter
    @Setter
    private List<RepertorioUsuario> listaRepertorioUsuario;

    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificadoCarga;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Usuario usuarioSeleccionado;

    @Getter
    @Setter
    private String persona;

    @Getter
    @Setter
    private TramiteUsuario tamiteUsuario;

    @Getter
    @Setter
    private CargaLaboral cargaLaboral;

    @Inject
    ReporteUtil reporteUtil;

    @EJB
    private InstitucionDao servicioInstitucion;

    @EJB
    private CargaLaboralDao cargaDao;

    @EJB
    private TramiteUsuarioDao tramiteUsuarioDao;

    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;

    @EJB
    private CertificadoCargaServicio certificadoCargaServicio;

    @PostConstruct
    public void init() {
//        setFechaIni(Calendar.getInstance().getTime());
//        setFechaFin(Calendar.getInstance().getTime());
        cargaLaboral = new CargaLaboral();
        listaCargaLaboral = new ArrayList<>();
        listaTramiteUsuario = new ArrayList<>();
        listaRepertorioUsuario = new ArrayList<>();
        listaCertificadoCarga = new ArrayList<>();
        listarCarga();
    }

    public void listarCarga() {
        setListaCargaLaboral(cargaDao.listaCargaLaboral());
        setRolId("todos");
        if (!listaCargaLaboral.isEmpty()) {
            for (CargaLaboral cl : listaCargaLaboral) {
                cl.setCarTipoCompleto(cargaDao.buscarEstadoPorCarTipo(cl.getCarTipo()));
            }
        }
    }

    public void generarReporte() throws JRException, IOException, NamingException, SQLException, ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.Impresion.generarReporte()");
        Institucion institucion = servicioInstitucion.obtenerInstitucion();
        setPathImagen(institucion.getInsLogo());
        File archivo = new File(getPathImagen());
        if (!archivo.exists()) {
            setPathImagen("");
            System.out.println("OJO: ¡¡No existe el archivo de configuración!!");
        }

        if (getFechaIni() != null && getFechaFin() != null && getRolId().equals("todos")) {
            try {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("FechaIni", getFechaIni());
                parametros.put("FechaFin", getFechaFin());
                parametros.put("pathImagen", getPathImagen());
                reporteUtil.imprimirReporteEnPDF("cargaLab", "CargaLaboral", parametros);
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al descargar");
            }
        } else {
            if (getFechaIni() == null || getFechaFin() == null || getRolId().equals("todos")) {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("pathImagen", getPathImagen());
                reporteUtil.imprimirReporteEnPDF("cargaLaboral", "CargaLaboral", parametros);
            } else {
                try {
                    Map<String, Object> parametros = new HashMap<>();
                    parametros.put("FechaIni", getFechaIni());
                    parametros.put("FechaFin", getFechaFin());
                    parametros.put("RolId", getRolId());
                    parametros.put("pathImagen", getPathImagen());
                    reporteUtil.imprimirReporteEnPDF("cargaParam", "CargaLaboral", parametros);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al descargar");
                }

            }
        }

    }

    public void listarPorFecha() throws ServicioExcepcion {
        listaCargaLaboral = new ArrayList<>();
        if (getFechaIni() == null || getFechaFin() == null) {
            JsfUtil.addErrorMessage("Ingrese fechas");
        } else {
            if (getFechaIni().equals(getFechaFin())) {
                setListaCargaLaboral(cargaDao.listarPorFechaYrol(fechaIni, fechaFin, rolId));
            } else {
                setListaCargaLaboral(cargaDao.listarFechaYrol(fechaIni, fechaFin, rolId));
            }
            if (!listaCargaLaboral.isEmpty()) {
                for (CargaLaboral cl : listaCargaLaboral) {
                    cl.setCarTipoCompleto(cargaDao.buscarEstadoPorCarTipo(cl.getCarTipo()));
                }
            }

        }

    }

    public void generarExcel() throws ServicioExcepcion {
        for (CargaLaboral cl : listaCargaLaboral) {
            List<TramiteUsuario> listaTramiteUsuarioPorUsuId = tramiteUsuarioDao.listarPorUsuId(cl.getUsuId().getUsuId());
            List<RepertorioUsuario> listaRepertorioUsuarioPorUsuId = repertorioUsuarioServicio.listarPorUsuId(cl.getUsuId().getUsuId());
            List<CertificadoCarga> listaCertificadoCargaPorUsuId = certificadoCargaServicio.listarPorUsuId(cl.getUsuId().getUsuId());
            for (TramiteUsuario tramiteUsuario : listaTramiteUsuarioPorUsuId) {
                listaTramiteUsuario.add(tramiteUsuario);
            }
            for (RepertorioUsuario repertorioUsuario : listaRepertorioUsuarioPorUsuId) {
                listaRepertorioUsuario.add(repertorioUsuario);
            }
            for (CertificadoCarga certificadoCarga : listaCertificadoCargaPorUsuId) {
                listaCertificadoCarga.add(certificadoCarga);
            }
        }
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Carga Laboral");
            System.out.println("hoja 1: " + wb.getSheetName(0));
            //LLENANDO EL EXCEL CON LA LISTA DE OBJETOS
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.getBold();
            CellStyle styleHeader = wb.createCellStyle();
            styleHeader.setFont(headerFont);
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            CellStyle nombre = wb.createCellStyle();
            nombre.setAlignment(HorizontalAlignment.LEFT);
            Row titulo = sheet.createRow(0);
            DateFormat formatoFecha = new SimpleDateFormat("dd_MM_yyyy");
            titulo.createCell(1).setCellValue("Carga Laboral");
            titulo.getCell(1).setCellStyle(styleHeader);
            Row header = sheet.createRow(1);
            header.createCell(0).setCellValue("Fecha");
            header.createCell(1).setCellValue("Persona");
            header.createCell(2).setCellValue("Usuario");
            header.createCell(3).setCellValue("Rol");
            header.createCell(4).setCellValue("Documento");
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 6));
            header.getCell(0).setCellStyle(styleHeader);
            header.getCell(1).setCellStyle(styleHeader);
            header.getCell(2).setCellStyle(styleHeader);
            header.getCell(3).setCellStyle(styleHeader);
            header.getCell(4).setCellStyle(styleHeader);
//           sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setWrapText(true);
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle descripcion = wb.createCellStyle();
            descripcion.setWrapText(true);
            descripcion.setVerticalAlignment(VerticalAlignment.TOP);

            int i = 1;//comienza a escribir desde la fila 2
            sheet.autoSizeColumn(i++);
            for (TramiteUsuario tramiteUsuario : listaTramiteUsuario) {
                Row row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(0);
                cell.setCellValue(tramiteUsuario.getTusFHR());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(0, 5000);

                cell = row.createCell(1);
                cell.setCellValue(tramiteUsuario.getUsuId().getPerId().getPerApellidoPaterno().trim() + " "
                        + tramiteUsuario.getUsuId().getPerId().getPerApellidoMaterno().trim() + " "
                        + tramiteUsuario.getUsuId().getPerId().getPerNombre().trim());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(1, 10000);

                cell = row.createCell(2);
                cell.setCellValue(tramiteUsuario.getUsuId().getUsuLogin());
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 4000);

                cell = row.createCell(3);
                cell.setCellValue(tramiteUsuario.getUsuId().getRolId().getRolNombre());
                cell.setCellStyle(style);
                sheet.setColumnWidth(3, 6000);

                cell = row.createCell(4);
                cell.setCellValue(tramiteUsuario.getTraNumero().getTraNumero());
                cell.setCellStyle(style);
                sheet.setColumnWidth(4, 9000);
                i++;
            }
            listaTramiteUsuario = new ArrayList<>();
            for (RepertorioUsuario repertorioUsuario : listaRepertorioUsuario) {
                Row row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(0);
                cell.setCellValue(repertorioUsuario.getRpuFHR());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(0, 5000);

                cell = row.createCell(1);
                cell.setCellValue(repertorioUsuario.getUsuId().getPerId().getPerApellidoPaterno().trim() + " "
                        + repertorioUsuario.getUsuId().getPerId().getPerApellidoMaterno().trim() + " "
                        + repertorioUsuario.getUsuId().getPerId().getPerNombre().trim());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(1, 10000);

                cell = row.createCell(2);
                cell.setCellValue(repertorioUsuario.getUsuId().getUsuLogin());
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 4000);

                cell = row.createCell(3);
                cell.setCellValue(repertorioUsuario.getUsuId().getRolId().getRolNombre());
                cell.setCellStyle(style);
                sheet.setColumnWidth(3, 6000);

                cell = row.createCell(4);
                cell.setCellValue(repertorioUsuario.getRepNumero().getRepNumero());
                cell.setCellStyle(style);
                sheet.setColumnWidth(4, 9000);
                i++;
            }
            listaRepertorioUsuario = new ArrayList<>();
            for (CertificadoCarga certificadoCarga : listaCertificadoCarga) {
                Row row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(0);
                cell.setCellValue(certificadoCarga.getCdcFHR());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(0, 5000);

                cell = row.createCell(1);
                cell.setCellValue(certificadoCarga.getUsuId().getPerId().getPerApellidoPaterno().trim() + " "
                        + certificadoCarga.getUsuId().getPerId().getPerApellidoMaterno().trim() + " "
                        + certificadoCarga.getUsuId().getPerId().getPerNombre().trim());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(1, 10000);

                cell = row.createCell(2);
                cell.setCellValue(certificadoCarga.getUsuId().getUsuLogin());
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 4000);

                cell = row.createCell(3);
                cell.setCellValue(certificadoCarga.getUsuId().getRolId().getRolNombre());
                cell.setCellStyle(style);
                sheet.setColumnWidth(3, 6000);

                cell = row.createCell(4);
                cell.setCellValue(certificadoCarga.getCdcDocumento());
                cell.setCellStyle(style);
                sheet.setColumnWidth(4, 9000);
                i++;
            }
            listaCertificadoCarga = new ArrayList<>();
            String nombreArchivo = "CargaLaboral_" + formatoFecha.format(Calendar.getInstance().getTime()) + ".xlsx";
            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador

            OutputStream output = ec.getResponseOutputStream();
            wb.write(output);//Se escribe el documento
            fc.responseComplete();//cierra la respuesta

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar la informacion ", ""));

        }
    }

//     public void ver() {
//        try {
//            switch (getCargaLaboral().getCarTipo()) {
//                case "RVT":
//                     setLista2(tramiteUsuarioDao.listarRVT(getTamiteUsuario().getUsuId().getUsuId()));
//                    break;
//                case "IMP":
//
//                    System.out.println("com.rm.empresarial.controlador.cargaControlador.crearPersona()");
//                    break;
//            }
//
//        } catch (ServicioExcepcion ex) {
//            Logger.getLogger(cargaControlador.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
}
