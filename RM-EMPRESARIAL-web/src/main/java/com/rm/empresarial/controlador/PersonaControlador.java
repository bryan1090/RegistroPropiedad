package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.PaisControllerBb;
import com.rm.empresarial.modelo.Pais;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.dao.ActaDao;
import com.rm.empresarial.dao.ConfigDetalleDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.NotariaDao;
import com.rm.empresarial.dao.ParroquiaDao;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.PropietarioDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TipoComparecienteDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.Ciudad;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.PaisServicio;
import doel.sri.bridge.ConfiguracionRepositorio;
import doel.sri.modelo.BPago;
import doel.sri.servicios.ServicioArchivo;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

import org.primefaces.model.UploadedFile;

@Named("personaControlador")
@ViewScoped
public class PersonaControlador implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private PersonaDao personaDao;

    @EJB
    private TipoComparecienteDao tipoComparecienteDao;

    @EJB
    private ActaDao actaDao;

    @EJB
    private PropietarioDao propietarioDao;
    @EJB
    private TramiteDetalleDao tramiteDetalleDao;
    @Getter
    @Setter
    private String nota;
    @Getter
    @Setter
    private String rtd;
    @Getter
    @Setter
    private String repertorio;
    @Getter
    @Setter
    private String propietarioObj;
    @Getter
    @Setter
    private List<Persona> persona;
    @Getter
    @Setter
    private List<String> propietarioS = new ArrayList<>();
    @Getter
    @Setter
    private Date fechaActa;
    @Getter
    @Setter
    private String espacio;
    @Getter
    @Setter
    private String rep;

    @Getter
    @Setter
    private TramiteDetalle objTramiteDetale;

    @Inject
    ReporteUtil reporteUtil;
    @Getter
    @Setter
    private List<Tramite> tramite;

    @Getter
    @Setter
    private List<TipoCompareciente> tipoCompareciente;

    @Getter
    @Setter
    private List<TramiteDetalle> tramiteDetalle;

    @Getter
    @Setter
    private List<Propiedad> propiedad;

    @Getter
    @Setter
    private List<ConfigDetalle> año;

    @Getter
    @Setter
    private List<TipoTramite> tipoTra;

    @Getter
    @Setter
    private String datos;

    @Getter
    @Setter
    private String mes;

    @Getter
    @Setter
    private Row row;

    @Getter
    @Setter
    private Propietario objPropietario;

    @Getter
    @Setter
    private Institucion institucion;

    @Getter
    @Setter
    private Acta objActa;

    @Getter
    @Setter
    private Tramite objTramite;

    @Getter
    @Setter
    private Repertorio repertorios;

    @Getter
    @Setter
    private Notaria objNotaria;

    @Getter
    @Setter
    private Parroquia parroquia;

    @Getter
    @Setter
    private Ciudad ciudad;

    @Getter
    @Setter
    private Canton canton;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private List<TramiteValor> tramiteValor;

    @Getter
    @Setter
    private List<Propietario> propietario;

    @Getter
    @Setter
    private List<Acta> acta;

    @Getter
    @Setter
    private List<Acta> i;
    @Getter
    @Setter
    private List<Notaria> notaria;

    @Getter
    @Setter
    private String fechaIngreso;

    @EJB
    private PropiedadDao propiedadDao;
    @EJB
    private ParroquiaDao parroquiaDao;

    @EJB
    private InstitucionDao institucionDao;

    @EJB
    private TipoTramiteDao tipoTramiteDao;

    @EJB
    private TramiteValorDao tramiteValorDao;

    @EJB
    private NotariaDao notariaDao;

    @EJB
    private ConfigDetalleDao configDetalleDao;

    @Getter
    @Setter
    private String fecha2;

    @Getter
    @Setter
    private Date fecha;

    @Getter
    @Setter
    private String anio;

    @Getter
    @Setter
    @Inject
    private PaisControllerBb paisControllerBb;

    private UploadedFile file;

    @PostConstruct

    public void post() {
        try {
            AñoMes();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(PersonaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PersonaControlador() {
        propietarioS = new ArrayList<>(); 
    }

    public void fecha() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setFecha2(getMes() + "/01/" + getAnio());
        Date fech = sdf.parse(getFecha2());
        System.out.println(fech + "  inicio del mes");
        System.out.println(ponerDiasFechaFinMes(fech) + "  fin de mes");

    }

    public void exportarExcel() throws IOException, InvalidFormatException, ServicioExcepcion, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setFecha2(getMes() + "/01/" + getAnio());
        Date fech = sdf.parse(getFecha2());
        System.out.println(fech + "  inicio del mes");
        System.out.println(ponerDiasFechaFinMes(fech) + "  fin de mes");

        setActa(actaDao.listarActaPorFechaIngreso(fech, ponerDiasFechaFinMes(fech)));
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File archivo = new File("reportePersonas.xlsx");
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Personas");
            sheet.setColumnWidth(0, 13);
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
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//            Row header = sheet.createRow(0);
//            header.createCell(0).setCellValue("Tipo Identificacion");
//            header.createCell(1).setCellValue("Identificacion");
//            header.createCell(2).setCellValue("Nombre");
//           sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));

//            header.getCell(0).setCellStyle(styleHeader);
//            header.getCell(1).setCellStyle(styleHeader);
//            header.getCell(2).setCellStyle(styleHeader);
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle descripcion = wb.createCellStyle();
            descripcion.setWrapText(true);
            descripcion.setVerticalAlignment(VerticalAlignment.TOP);

            int i = 0;//comienza a escribir desde la fila 2
            sheet.autoSizeColumn(i++);
            for (Acta acta : acta) {
                row = sheet.createRow(i);
                Cell cell;
                cell = row.createCell(0);
                cell.setCellValue(acta.getRepNumero().getRepNumero());
                cell.setCellStyle(style);
                sheet.setColumnWidth(0, 4000);

                cell = row.createCell(1);
                cell.setCellValue(acta.getActFechaIngreso());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(1, 4000);

                setTipoTramite(tipoTramiteDao.buscarID(acta.getRepNumero().getRepTtrId()));

                cell = row.createCell(2);
                cell.setCellValue(getTipoTramite().getTrCodigoAgrupacion5());
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 3000);

                cell = row.createCell(3);
                cell.setCellValue(acta.getPrdMatricula().getPrdDescripcion2());
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(3, 10000);

                cell = row.createCell(4);
                cell.setCellValue(acta.getPrdMatricula().getPrdCatastro());
                cell.setCellStyle(style);
                sheet.setColumnWidth(4, 3000);

                cell = row.createCell(5);
                cell.setCellValue("");
                cell.setCellStyle(style);
                sheet.setColumnWidth(5, 4000);

                cell = row.createCell(6);
                cell.setCellValue(acta.getPrdMatricula().getTpdId().getTpdCodigo());
                cell.setCellStyle(style);
                sheet.setColumnWidth(6, 4000);

                cell = row.createCell(7);
                cell.setCellValue(acta.getPrdMatricula().getPrdDescripcion2());
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(7, 10000);

                setParroquia(parroquiaDao.buscarPorId(acta.getPrdMatricula().getPrdTdtParId()));

                cell = row.createCell(8);
                cell.setCellValue(getParroquia().getCiuId().getCanId().getCanCodigo());
                cell.setCellStyle(style);
                sheet.setColumnWidth(8, 4000);

                setInstitucion(institucionDao.obtenerInstitucion());
                cell = row.createCell(9);
                cell.setCellValue(getInstitucion().getInsCodigoRegistro());
                cell.setCellStyle(style);
                sheet.setColumnWidth(8, 4000);

                cell = row.createCell(10);
                cell.setCellValue(ponerDiasFechaFinMes(fech));
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(10, 5000);

                i++;
            }
            setTramiteValor(tramiteValorDao.listarPorFecha(fech, ponerDiasFechaFinMes(fech)));
            int e = 1;
            for (TramiteValor tramiteValor : tramiteValor) {
                if (tramiteValor.getTrvValorBien() == null) {

                    sheet.getRow(e).getCell(5).setCellValue("0.00");
                } else {
                    sheet.getRow(e).getCell(5).setCellValue(tramiteValor.getTrvValorBien().doubleValue());
                }
                e++;
            }

            String nombreArchivo = "ListaActa_" + Calendar.getInstance().getTime().toString() + ".xlsx";
            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador

            OutputStream output = ec.getResponseOutputStream();
            wb.write(output);//Se escribe el documento
            fc.responseComplete();//cierra la respuesta
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error 0004: Ocurrio un error con la carga y/o modificacion del template ListaPais", ""));

        }

    }

    private static Date ponerDiasFechaFinMes(Date fecha) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }

    public void exportarExcelPropiedad() throws IOException, InvalidFormatException, ServicioExcepcion {

        setPersona(personaDao.listarPersonas());

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File archivo = new File("Persona.xlsx");
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Persona");
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
//            Row header = sheet.createRow(0);
//            header.createCell(0).setCellValue("Matricula");
//            header.createCell(1).setCellValue("Fecha");
//            header.createCell(2).setCellValue("Descripcion 1");
//            header.createCell(3).setCellValue("Descripcion 2");
//           sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
//            header.getCell(0).setCellStyle(styleHeader);
//            header.getCell(1).setCellStyle(styleHeader);
//            header.getCell(2).setCellStyle(styleHeader);
//            header.getCell(3).setCellStyle(styleHeader);
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle descripcion = wb.createCellStyle();
            descripcion.setWrapText(true);
            descripcion.setVerticalAlignment(VerticalAlignment.TOP);

            int i = 0;//comienza a escribir desde la fila 2
            sheet.autoSizeColumn(i++);
            for (Persona persona : persona) {
                row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(0);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(0, 4000);

                cell = row.createCell(1);
                cell.setCellValue(persona.getPerTipoIdentificacion());
                cell.setCellStyle(style);
                sheet.setColumnWidth(1, 4000);

                cell = row.createCell(2);
                cell.setCellValue(persona.getPerIdentificacion());
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 4000);

                cell = row.createCell(3);
                cell.setCellValue(persona.getPerApellidoPaterno());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(3, 4000);

                cell = row.createCell(4);
                cell.setCellValue(persona.getPerApellidoMaterno());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(4, 4000);

                cell = row.createCell(5);
                cell.setCellValue(persona.getPerNombre());
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(5, 6000);

                cell = row.createCell(6);
                cell.setCellValue(persona.getPerNacionalidad());
                cell.setCellStyle(style);
                sheet.setColumnWidth(6, 4000);

                cell = row.createCell(7);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(7, 4000);

                cell = row.createCell(8);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(8, 4000);

                i++;
            }

            setTramiteDetalle(tramiteDetalleDao.listarTramite());
            int e = 1;
            for (TramiteDetalle tramiteDetalle : tramiteDetalle) {
                if (tramiteDetalle.getTdtNumeroRepertorio() == 0) {

                    sheet.getRow(e).getCell(0).setCellValue(" ");
                } else {
                    sheet.getRow(e).getCell(0).setCellValue(tramiteDetalle.getTdtNumeroRepertorio());
                }
                e++;
            }
            setTipoCompareciente(tipoComparecienteDao.listarTipoCompareciente());
            int b = 1;
            for (TipoCompareciente tipoCompareciente : tipoCompareciente) {
                if (tipoCompareciente.getTpcCodigoEntregaRecibe() == null) {

                    sheet.getRow(b).getCell(7).setCellValue(" ");
                    sheet.getRow(b).getCell(8).setCellValue(" ");
                } else {
//                    String charToString = Character.toString(codigo1);
                    sheet.getRow(b).getCell(7).setCellValue(tipoCompareciente.getTpcCodigoEntregaRecibe().substring(0, 2));
                    sheet.getRow(b).getCell(8).setCellValue(tipoCompareciente.getTpcCodigoEntregaRecibe().substring(3, 5));
                }
                b++;
            }

            String nombreArchivo = "Propiedad_" + Calendar.getInstance().getTime().toString() + ".xlsx";
            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador

            OutputStream output = ec.getResponseOutputStream();
            wb.write(output);//Se escribe el documento
            fc.responseComplete();//cierra la respuesta
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error 0004: Ocurrio un error con la carga y/o modificacion del template ListaPais", ""));

        }

    }

    public void exportarExcelActa() throws IOException, InvalidFormatException, ServicioExcepcion, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        setFecha2(getMes() + "/01/" + getAnio());
        Date fech = sdf.parse(getFecha2());
        System.out.println(fech + "  inicio del mes");
        System.out.println(ponerDiasFechaFinMes(fech) + "  fin de mes");

        setActa(actaDao.listarActaReporte(fech, ponerDiasFechaFinMes(fech)));
        setPropietario(propietarioDao.listarReporte(fech, ponerDiasFechaFinMes(fech)));
        setNotaria(notariaDao.listarReporte(fech, ponerDiasFechaFinMes(fech)));

        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File archivo = new File("reporteActa.xlsx");
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Acta");
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

//           sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle descripcion = wb.createCellStyle();
            descripcion.setWrapText(true);
            descripcion.setVerticalAlignment(VerticalAlignment.TOP);

            int i = 0;
            sheet.autoSizeColumn(i++);
            for (Acta acta : acta) {

                row = sheet.createRow(i);
                Cell cell;
                cell = row.createCell(0);
                cell.setCellValue(acta.getRepNumero().getTraNumero().getTraRtd());
                cell.setCellStyle(style);
                sheet.setColumnWidth(0, 5000);

                cell = row.createCell(1);
                cell.setCellValue(acta.getActFechaIngreso());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(1, 3000);

                cell = row.createCell(2);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 5000);

                cell = row.createCell(3);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(3, 3000);

                cell = row.createCell(4);
                cell.setCellValue(acta.getActFch());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(4, 3000);
                
                 cell = row.createCell(6);
                cell.setCellValue("XXXX");
                cell.setCellStyle(style);
                sheet.setColumnWidth(6, 3000);

                cell = row.createCell(5);
                cell.setCellValue(acta.getRepNumero().getRepNumero());
                cell.setCellStyle(style);
                sheet.setColumnWidth(5, 3000);

                i++;
            }

            int b = 1;
            for (Propietario propietario : propietario) {
                if (propietario.getPprPerIdentificacion() == null) {

                    sheet.getRow(b).getCell(2).setCellValue(" ");
                } else {
                    sheet.getRow(b).getCell(2).setCellValue(propietario.getPprPerIdentificacion());
                }
                b++;
            }

            int c = 1;
            for (Notaria notaria : notaria) {
                if (notaria.getNotNotario() == null) {

                    sheet.getRow(c).getCell(3).setCellValue(" ");
                } else {
                    sheet.getRow(c).getCell(3).setCellValue(notaria.getNotNotario());
                }
                c++;
            }

            String nombreArchivo = "Acta_" + Calendar.getInstance().getTime().toString() + ".xlsx";
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

    public void exportarTXT() throws IOException, InvalidFormatException, ServicioExcepcion, ParseException, Exception {
        propietarioS = new ArrayList<>(); 
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setFecha2(getMes() + "/01/" + getAnio());
        Date fech = sdf.parse(getFecha2());
        System.out.println(fech + "  inicio del mes");
        System.out.println(ponerDiasFechaFinMes(fech) + "  fin de mes");
        if (fech != null) {
            setActa(actaDao.listarActaReporte(fech, ponerDiasFechaFinMes(fech)));
            setPropietario(propietarioDao.listarReporte(fech, ponerDiasFechaFinMes(fech)));

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            String nombreArchivo = "reporteActa.txt";

            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("text/comma-separated-values; charset=utf8"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador

            byte[] datos;

            DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String historial = hourdateFormat.format(fech);

            int i = 0;
             
            for(Propietario propietario:propietario){
                getPropietarioS().add(propietario.getPprPerIdentificacion());
                
            }
            
                for (Acta acta : acta) {
                     setObjNotaria(notariaDao.listarNOtariaPorId(acta.getRepNumero().getTraNumero().getTraNotaria()));
                    if (acta.getActFechaIngreso() != null) {
                        setFechaIngreso(hourdateFormat.format(acta.getActFechaIngreso()));
                    } else {
                        setFechaIngreso("          ");
                    }
                    
                    ///////////////////////////////////////Notaria///////////////////////////////////
                    if (getObjNotaria() == null || getObjNotaria().getNotNotario().isEmpty()) {
                        setNota("             ");
                    }else{
                        
                    if(getObjNotaria().getNotNotario().length()== 1){
                        setNota(getObjNotaria().getNotNotario()+"            ");
                    }
                    if(getObjNotaria().getNotNotario().length()== 2){
                        setNota(getObjNotaria().getNotNotario()+"           ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 3){
                        setNota(getObjNotaria().getNotNotario()+"          ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 4){
                        setNota(getObjNotaria().getNotNotario()+"         ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 5){
                        setNota(getObjNotaria().getNotNotario()+"        ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 6){
                        setNota(getObjNotaria().getNotNotario()+"       ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 7){
                        setNota(getObjNotaria().getNotNotario()+"      ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 8){
                        setNota(getObjNotaria().getNotNotario()+"     ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 9){
                        setNota(getObjNotaria().getNotNotario()+"    ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 10){
                        setNota(getObjNotaria().getNotNotario()+"   ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 11){
                        setNota(getObjNotaria().getNotNotario()+"  ");
                    }
                     if(getObjNotaria().getNotNotario().length()== 12){
                        setNota(getObjNotaria().getNotNotario()+" ");
                    }
                     if(getObjNotaria().getNotNotario().length() > 12){
                        setNota(getObjNotaria().getNotNotario()+"");
                    }
                     
                    }
                    
                    //////////////////////////////TRD//////////////////////////////////////////
                    if (acta.getRepNumero().getTraNumero().getTraRtd().isEmpty()) {
                        setRtd("            ");
                    } else {
                        if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 1) {
                            setEspacio("           ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                        if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 2) {
                            setEspacio("          ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                        if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 3) {
                            setEspacio("         ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd().concat("         "));
                        } 
                        if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 4) {
                            setEspacio("        ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        } 
                        if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 5) {
                            setEspacio("       ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                        if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 6) {
                            setEspacio("      ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                        if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 7) {
                            setEspacio("     ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                         if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 8) {
                            setEspacio("    ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                          if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 9) {
                            setEspacio("   ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                          if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 10) {
                            setEspacio("  ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                          if (acta.getRepNumero().getTraNumero().getTraRtd().length() == 11) {
                            setEspacio(" ");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                           if (acta.getRepNumero().getTraNumero().getTraRtd().length() > 11) {
                            setEspacio("");
                            setRtd(acta.getRepNumero().getTraNumero().getTraRtd() + getEspacio());
                        }
                    }
                    ///////////////////////////////IDENTIFICACION//////////////////////////
                    
                    if(getPropietarioS().get(i).isEmpty()){
                        setPropietarioObj("              ");
                    }
                     if(getPropietarioS().get(i).length() == 10){
                        setPropietarioObj(getPropietarioS().get(i)+"    ");
                    }
                     if(getPropietarioS().get(i).length() == 13){
                        setPropietarioObj(getPropietarioS().get(i)+" ");
                    }
                     if(getPropietarioS().get(i).length() > 13){
                        setPropietarioObj(getPropietarioS().get(i)+"");
                    }
                     //////////////////////////Repertorio/////////////////////////////
                    setRep(acta.getRepNumero().getRepNumero().toString());
                     if(getRep().length()==1){
                        setRepertorio(getRep()+"          ");
                    }
                     if(getRep().length()==2){
                       setRepertorio(getRep()+"         ");
                    }
                      if(getRep().length()==3){
                        setRepertorio(getRep()+"        ");
                    }
                      if(getRep().length()==4){
                        setRepertorio(getRep()+"       ");
                    }
                      if(getRep().length()==5){
                        setRepertorio(getRep()+"      ");
                    }
                       if(getRep().length()==6){
                        setRepertorio(getRep()+"     ");
                    }
                        if(getRep().length()==7){
                        setRepertorio(getRep()+"    ");
                    }
                        if(getRep().length()==8){
                        setRepertorio(getRep()+"   ");
                    }
                         if(getRep().length()==9){
                        setRepertorio(getRep()+"  ");
                    }
                           if(getRep().length()==10){
                        setRepertorio(getRep()+" ");
                    }
                          if(getRep().length() > 10){
                        setRepertorio(getRep()+"");
                    }
                    setDatos(getRtd()+ historial + " " + getPropietarioObj()+ getNota() + getFechaIngreso()+ " " + getRepertorio() +"XXXX"+ "\r\n");

                    OutputStream output = ec.getResponseOutputStream();
                    output.write(getDatos().getBytes());
                    i++;
            }
            fc.responseComplete();//cierra la respuesta}
        } else {
            addErrorMessage("Error no se encontro una fecha ");
        }
    }

    public void generarPdfPropiedad() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setFecha2(getMes() + "/01/" + getAnio());
        Date fech = sdf.parse(getFecha2());
        System.out.println(fech + "  inicio del mes");
        System.out.println(ponerDiasFechaFinMes(fech) + "  fin de mes");
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("FechaIni", fech);
            parametros.put("FechaFin", ponerDiasFechaFinMes(fech));

            reporteUtil.imprimirReporteEnPDF("propiedad", "Reg.Propiedad", parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Pdf Generado"));
        } catch (Exception e) {
            System.err.println(e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al generar pdf");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void AñoMes() throws ServicioExcepcion {
        setAño(configDetalleDao.listarPorConfigDesc("ANIO"));

    }
}
