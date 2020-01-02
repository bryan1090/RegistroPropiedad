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
@Named(value = "controladorReporteVentas")
@ViewScoped
public class ControladorReporteVentas implements Serializable {

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
    private List<TramiteUsuario> listaUsuarios;

    @Getter
    @Setter
    private List<TramiteUsuario> auxListaTramite;

    @Getter
    @Setter
    private List<Notaria> listNotaria;

    @Getter
    @Setter
    private List<Integer> usuId;

    @Getter
    @Setter
    private List<Factura> listaFactura;

    @Getter
    @Setter
    private List<FacturaDetalle> listaFacturaDetalle;

    @Getter
    @Setter
    private Usuario usuario;

    @Getter
    @Setter
    private List<TramiteResponsable> listaTramiteResponsable;

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

    public ControladorReporteVentas() {

        System.out.println("com.rm.empresarial.controlador.ControladorMatriculacion.<init>()");
        RepertorioUsuario = new RepertorioUsuario();
        nuevoIngreso = false;
        estadoBotonNuevaPropiedad = "true";
    }

    public void exportarExcel() throws IOException, InvalidFormatException, ServicioExcepcion, ParseException {

        setListaFactura(facturaDao.reporteFactura(fechaIni, fechaFin));
        setListaFacturaDetalle(facturaDetalleDao.reporteFactura(fechaIni, fechaFin));
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File archivo = new File("Factura.xlsx");
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Factura");
            System.out.println("hoja 1: " + wb.getSheetName(0));
            //LLENANDO EL EXCEL CON LA LISTA DE OBJETOS
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.getBold();
            Font letra = wb.createFont();
            letra.setBold(true);
            letra.setFontHeightInPoints((short) 12);
            letra.getBold();
            letra.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
            CellStyle title = wb.createCellStyle();
            title.setFont(headerFont);
            CellStyle styleHeader = wb.createCellStyle();
            styleHeader.setFont(headerFont);
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            styleHeader.setBorderBottom(BorderStyle.THIN);
            styleHeader.setBorderTop(BorderStyle.THIN);
            styleHeader.setBorderLeft(BorderStyle.THIN);
            styleHeader.setBorderRight(BorderStyle.THIN);
            CellStyle eader = wb.createCellStyle();
            eader.setFont(letra);
            eader.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
            eader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            eader.setAlignment(HorizontalAlignment.CENTER);
            eader.setBorderBottom(BorderStyle.THIN);
            eader.setBorderTop(BorderStyle.THIN);
            eader.setBorderLeft(BorderStyle.THIN);
            eader.setBorderRight(BorderStyle.THIN);
            CellStyle nombre = wb.createCellStyle();
            nombre.setAlignment(HorizontalAlignment.LEFT);
            Row header = sheet.createRow(1);
            header.createCell(0).setCellValue("Factura");
            header.createCell(1).setCellValue("Tipo Identificacion");
            header.createCell(2).setCellValue("Identificacion");
            header.createCell(3).setCellValue("Nombre");
            header.createCell(4).setCellValue("Descripcion");
            header.createCell(5).setCellValue("Cantidad");
            header.createCell(6).setCellValue("Valor");
            header.createCell(7).setCellValue("Descuento");
            header.createCell(8).setCellValue("Total");
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));
            header.getCell(0).setCellStyle(eader);
            header.getCell(1).setCellStyle(eader);
            header.getCell(2).setCellStyle(eader);
            header.getCell(3).setCellStyle(eader);
            header.getCell(4).setCellStyle(eader);
            header.getCell(5).setCellStyle(eader);
            header.getCell(6).setCellStyle(eader);
            header.getCell(7).setCellStyle(eader);
            header.getCell(8).setCellStyle(eader);
            CellStyle style = wb.createCellStyle();
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle descripcion = wb.createCellStyle();
            descripcion.setBorderBottom(BorderStyle.THIN);
            descripcion.setBorderTop(BorderStyle.THIN);
            descripcion.setBorderLeft(BorderStyle.THIN);
            descripcion.setBorderRight(BorderStyle.THIN);
            descripcion.setWrapText(true);
            descripcion.setVerticalAlignment(VerticalAlignment.TOP);

            int i = 2;//comienza a escribir desde la fila 2
            for (Factura factura : listaFactura) {
                row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(0);
                cell.setCellValue(factura.getFacNumero());
                cell.setCellStyle(style);
                sheet.setColumnWidth(0, 5000);

                cell = row.createCell(1);
                cell.setCellValue(factura.getFacPerTipoIdentificacion());
                cell.setCellStyle(style);
                sheet.setColumnWidth(1, 5000);

                cell = row.createCell(2);
                cell.setCellValue(factura.getFacPerIdentificacion());
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 5000);

                cell = row.createCell(3);
                cell.setCellValue(factura.getFacPerApellidoPaterno() + " " + factura.getFacPerApellidoMaterno() + " " + factura.getFacPerNombre());
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(3, 7000);

                cell = row.createCell(4);
                cell.setCellValue("Descripcion");
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(4, 5000);

                cell = row.createCell(5);
                cell.setCellValue("Cantidad");
                cell.setCellStyle(style);
                sheet.setColumnWidth(5, 3000);

                cell = row.createCell(6);
                cell.setCellValue("Valor");
                cell.setCellStyle(style);
                sheet.setColumnWidth(6, 3000);

                cell = row.createCell(7);
                cell.setCellValue("Descuento");
                cell.setCellStyle(style);
                sheet.setColumnWidth(7, 3000);

                cell = row.createCell(8);
                cell.setCellValue("Total");
                cell.setCellStyle(style);
                sheet.setColumnWidth(8, 3000);
                i++;
            }
            int e = 2;
            for (FacturaDetalle fDetalle : listaFacturaDetalle) {

                if (fDetalle != null) {
                    sheet.getRow(e).getCell(4).setCellValue(fDetalle.getFdtTtrDescripcion());
                    sheet.getRow(e).getCell(5).setCellValue(fDetalle.getFdtCantidad().doubleValue());
                    sheet.getRow(e).getCell(6).setCellValue(fDetalle.getFdtValor().doubleValue());
                    sheet.getRow(e).getCell(7).setCellValue(fDetalle.getFdtDescuento().doubleValue());
                    sheet.getRow(e).getCell(8).setCellValue(fDetalle.getFdtTotal().doubleValue());
                }
                e++;
            }
            String nombreArchivo = "Recepcion_" + Calendar.getInstance().getTime().toString() + ".xlsx";
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

    public void descargarExcelAclaratoria() throws ServicioExcepcion, ParseException, ParseException {
        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setListaFactura(facturaDao.reporteFactura(fechaIni, fechaFin));
        setListaFacturaDetalle(facturaDetalleDao.reporteFactura(fechaIni, fechaFin));
        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            Path path = FileSystems.getDefault().getPath(
                FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "\\resources\\plantillaExcel\\reporteVentas.xlsx");
         
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
            for (Factura factura : listaFactura) {
                row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(1);
                cell.setCellValue("Tramite");
                cell.setCellStyle(style);
                sheet.setColumnWidth(1, 5000);

                cell = row.createCell(2);
                cell.setCellValue(sdf.format(factura.getFacFecha()));
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(2, 5000);

                cell = row.createCell(3);
                cell.setCellValue(factura.getFacNumero());
                cell.setCellStyle(style);
                sheet.setColumnWidth(3, 5000);

                cell = row.createCell(4);
                cell.setCellValue(factura.getFacPerTipoIdentificacion());
                cell.setCellStyle(style);
                sheet.setColumnWidth(4, 5000);

                cell = row.createCell(5);
                cell.setCellValue(factura.getFacPerIdentificacion());
                cell.setCellStyle(style);
                sheet.setColumnWidth(5, 5000);

                cell = row.createCell(6);
                cell.setCellValue(factura.getFacPerApellidoPaterno() + " " + factura.getFacPerApellidoMaterno() + " " + factura.getFacPerNombre());
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(6, 7000);

                cell = row.createCell(7);
                cell.setCellValue("Descripcion");
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(7, 5000);

                cell = row.createCell(8);
                cell.setCellValue("Tipo Tramite");
                cell.setCellStyle(style);
                sheet.setColumnWidth(8, 5000);

                cell = row.createCell(9);
                cell.setCellValue("Cantidad");
                cell.setCellStyle(style);
                sheet.setColumnWidth(9, 3000);

                cell = row.createCell(10);
                cell.setCellValue("SubTotal");
                cell.setCellStyle(style);
                sheet.setColumnWidth(10, 3000);

                cell = row.createCell(11);
                cell.setCellValue("Descuento");
                cell.setCellStyle(style);
                sheet.setColumnWidth(11, 3000);

                cell = row.createCell(12);
                cell.setCellValue("IVA");
                cell.setCellStyle(style);
                sheet.setColumnWidth(12, 3000);

                cell = row.createCell(13);
                cell.setCellValue("Total");
                cell.setCellStyle(style);
                sheet.setColumnWidth(13, 3000);

                cell = row.createCell(14);
                cell.setCellValue(factura.getFacUser());
                cell.setCellStyle(style);
                sheet.setColumnWidth(14, 4000);
                i++;
            }
            int e = 1;
            for (FacturaDetalle fDetalle : listaFacturaDetalle) {
                if (fDetalle != null) {
                    double subtotal = 0;
                    double descuento = 0;
                    double iva = 0;
                    double total = 0;
                    sheet.getRow(e).getCell(1).setCellValue(fDetalle.getFdtTraNumero());
                    sheet.getRow(e).getCell(7).setCellValue(fDetalle.getFdtTtrDescripcion());
                    setTipotramite(tipoTramiteDao.buscarPorDescripcion(fDetalle.getFdtTtrDescripcion()));
                    if(getTipotramite()==null){
                        sheet.getRow(e).getCell(8).setCellValue("CERTIFICADO"); 
                    }else{
                        sheet.getRow(e).getCell(8).setCellValue("INSCRIPCION"); 
                    }
                    sheet.getRow(e).getCell(9).setCellValue(fDetalle.getFdtCantidad().doubleValue());
                    subtotal = fDetalle.getFdtValor().doubleValue() * fDetalle.getFdtCantidad().doubleValue();
                    sheet.getRow(e).getCell(10).setCellValue(df.format(subtotal));
                    descuento = fDetalle.getFdtDescuento().doubleValue();
                    sheet.getRow(e).getCell(11).setCellValue(df.format(descuento));

                    setFacturaImpuesto(facturaImpustoDao.buscarPorComprobante(fDetalle));
                    if (getFacturaImpuesto() != null) {
                        iva = getFacturaImpuesto().getFdiBaseImponible().doubleValue() * getFacturaImpuesto().getFdiTarifa().doubleValue() / 100;
                        sheet.getRow(e).getCell(12).setCellValue(df.format(iva));
                    }
                    total = subtotal - descuento + iva;
                    sheet.getRow(e).getCell(13).setCellValue(df.format(total));
                }
                e++;
            }
              Row header = sheet.getRow(1);
            header.createCell(0).setCellValue("Desde:  " + sdf.format(fechaIni)+"  Hasta: " + sdf.format(fechaFin));
            header.getCell(0).setCellStyle(eader);

//             ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            workbook.write(bos);
//            bos.close();
//            workbook.close();
//            byte[] bytes = bos.toByteArray();
//            InputStream myInputStream = new ByteArrayInputStream(bytes);
//            fileExcelAclar = new DefaultStreamedContent(myInputStream, "application/vnd.ms-excel", "Excel_Ventas.xlsx");
            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "Ventas.xlsx" + "\""); //Se descarga desde el navegador

            OutputStream output = ec.getResponseOutputStream();
            workbook.write(output);//Se escribe el documento
            fc.responseComplete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void descargaHabilitada() {
        setDescarga(false);
    }

//    public void descargar() {
//        try {
//            descargarExcelAclaratoria();
//            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/plantillaExcel/reporteVenta.xlsx");
//
//            file = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "PH_PLANTILLA.xlsx");
//
////                    InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/plantillaExcel/p.jpg");
////        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_boromir.jpg");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
}
