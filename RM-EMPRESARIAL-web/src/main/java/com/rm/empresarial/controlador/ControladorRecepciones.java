/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.dao.NotariaDao;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteResponsableDao;
import com.rm.empresarial.dao.TramiteUsuarioDao;
import com.rm.empresarial.dao.UsuarioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteResponsable;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorRecepciones")
@ViewScoped
public class ControladorRecepciones implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private Row row;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @EJB
    RepertorioUsuarioDao RepertorioUsuarioDao;

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
    TipoTramiteDao tipoTramiteDao;
    
    @EJB
    UsuarioDao usuarioDao;

    @Inject
    ReporteUtil reporteUtil;

    public ControladorRecepciones() {
        System.out.println("com.rm.empresarial.controlador.ControladorMatriculacion.<init>()");
        RepertorioUsuario = new RepertorioUsuario();
        nuevoIngreso = false;
        estadoBotonNuevaPropiedad = "true";
    }

    public void exportarExcelPropiedad() throws IOException, InvalidFormatException, ServicioExcepcion {
        listaUsuarios = new ArrayList<>();
        usuId = new ArrayList<>();

        setAuxListaTramite(tramiteUsuarioDao.usuarios(fecha));
        for (TramiteUsuario tramiteU : auxListaTramite) {
            usuId.add(tramiteU.getUsuId().getUsuId().intValue());
        }
        Set<Integer> hashSet = new HashSet<Integer>(usuId);
        usuId.clear();
        usuId.addAll(hashSet);

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
            CellStyle title = wb.createCellStyle();
            title.setFont(headerFont);
            CellStyle styleHeader = wb.createCellStyle();
            styleHeader.setFont(headerFont);
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            styleHeader.setBorderBottom(BorderStyle.THIN);
            styleHeader.setBorderTop(BorderStyle.THIN);
            styleHeader.setBorderLeft(BorderStyle.THIN);
            styleHeader.setBorderRight(BorderStyle.THIN);
            CellStyle nombre = wb.createCellStyle();
            nombre.setAlignment(HorizontalAlignment.LEFT);
            Row titulo = sheet.createRow(0);
            titulo.createCell(3).setCellValue("Reporte de Recepciones");
            titulo.getCell(3).setCellStyle(title);
//            header.createCell(1).setCellValue("Fecha");
//            header.createCell(2).setCellValue("Nombre");
//            header.createCell(3).setCellValue("Notaria");
//            header.createCell(4).setCellValue("Telefono");
//            header.createCell(5).setCellValue("E-mail");
//            header.createCell(6).setCellValue("Beneficiario");
//            header.createCell(7).setCellValue("Contratos");
           sheet.addMergedRegion(new CellRangeAddress(0,0,3,5));
//            header.getCell(0).setCellStyle(styleHeader);
//            header.getCell(1).setCellStyle(styleHeader);
//            header.getCell(2).setCellStyle(styleHeader);
//            header.getCell(3).setCellStyle(styleHeader);
//            header.getCell(4).setCellStyle(styleHeader);
//            header.getCell(5).setCellStyle(styleHeader);
//            header.getCell(6).setCellStyle(styleHeader);
//            header.getCell(7).setCellStyle(styleHeader);
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

            int i = 3;//comienza a escribir desde la fila 2
            for (Integer usu : usuId) {
                setUsuario(usuarioDao.buscarUsuarioPorId(usu.toString()));
                setListaTramites(tramiteDao.tramitesUsu(fecha, usu));
                int z = i - 1;
                int v = z - 1;
                
                Row usuario = sheet.createRow(v);
                usuario.createCell(0).setCellValue("Revisor:  "+getUsuario().getPerId().getPerApellidoPaterno()+" "+getUsuario().getPerId().getPerNombre());
                usuario.getCell(0).setCellStyle(title);
                Row header2 = sheet.createRow(z);
                header2.createCell(0).setCellValue("Num.");
                header2.createCell(1).setCellValue("Fecha");
                header2.createCell(2).setCellValue("Nombre");
                header2.createCell(3).setCellValue("Notaria");
                header2.createCell(4).setCellValue("Telefono");
                header2.createCell(5).setCellValue("E-mail");
                header2.createCell(6).setCellValue("Beneficiario");
                header2.createCell(7).setCellValue("Contratos");
                header2.getCell(0).setCellStyle(styleHeader);
                header2.getCell(1).setCellStyle(styleHeader);
                header2.getCell(2).setCellStyle(styleHeader);
                header2.getCell(3).setCellStyle(styleHeader);
                header2.getCell(4).setCellStyle(styleHeader);
                header2.getCell(5).setCellStyle(styleHeader);
                header2.getCell(6).setCellStyle(styleHeader);
                header2.getCell(7).setCellStyle(styleHeader);
                for (Tramite tramite : listaTramites) {
                    row = sheet.createRow(i);
                    Cell cell;

                    cell = row.createCell(0);
                    cell.setCellValue(tramite.getTraNumero());
                    cell.setCellStyle(style);
                    sheet.setColumnWidth(0, 4000);

                    cell = row.createCell(1);
                    cell.setCellValue(tramite.getTraFHR());
                    cell.setCellStyle(cellStyle);
                    sheet.setColumnWidth(1, 4000);

                    cell = row.createCell(2);
                    cell.setCellValue(tramite.getTraPerApellidoPaterno() + " " + tramite.getTraPerApellidoMaterno()
                            + " " + tramite.getTraPerNombre());
                    cell.setCellStyle(descripcion);
                    sheet.setColumnWidth(2, 7000);

                    cell = row.createCell(3);
                    cell.setCellValue("notaria");
                    cell.setCellStyle(style);
                    sheet.setColumnWidth(3, 4000);

                    setPersona(personaDao.encontrarPersonaPorCIRUC(tramite.getTraPerIdentificacion()));
                    if (getPersona() == null) {
                        cell = row.createCell(4);
                        cell.setCellValue("");
                        cell.setCellStyle(style);
                        sheet.setColumnWidth(4, 4000);

                        cell = row.createCell(5);
                        cell.setCellValue("");
                        cell.setCellStyle(style);
                        sheet.setColumnWidth(5, 6000);
                    } else {
                        cell = row.createCell(4);
                        cell.setCellValue(getPersona().getPerCelular());
                        cell.setCellStyle(style);
                        sheet.setColumnWidth(4, 4000);

                        cell = row.createCell(5);
                        cell.setCellValue(getPersona().getPerEmail());
                        cell.setCellStyle(descripcion);
                        sheet.setColumnWidth(5, 6000);

                    }

                    cell = row.createCell(6);
                    cell.setCellValue("responsable");
                    cell.setCellStyle(descripcion);
                    sheet.setColumnWidth(6, 7000);

                    cell = row.createCell(7);
                    cell.setCellValue("Contrato");
                    cell.setCellStyle(style);
                    sheet.setColumnWidth(7, 7000);

                    i++;
                }
                int f = i + 1;
                i = i + 4;
                Row firmas = sheet.createRow(f);
                firmas.createCell(3).setCellValue("ENTREGADO");
                firmas.createCell(6).setCellValue("RECIBIDO");
                firmas.getCell(3).setCellStyle(title);
                 firmas.getCell(6).setCellStyle(title);
               
            }

            int e = 3;
            for (Integer usu : usuId) {
                setListNotaria(notariaDao.tramitesUsu(fecha, usu));
                for (Notaria not : listNotaria) {
                    if (not.getNotNotario().equals("") || not.getCiuId().getCiuNombre().equals("")) {
                        sheet.getRow(e).getCell(3).setCellValue(" ");
                    } else {
                        sheet.getRow(e).getCell(3).setCellValue(not.getNotNotario() + "_" + not.getCiuId().getCiuNombre());
                    }
                    e++;
                }
                e = e + 4;
            }

            int b = 3;
            for (Integer usu : usuId) {
                setListaTramiteResponsable(tramiteResponsableDao.tramitesUsu(fecha, usu));
                for (TramiteResponsable tramRes : listaTramiteResponsable) {
                    setTipoTramite(tipoTramiteDao.buscarID(tramRes.getTtrId().intValue()));
                    setResponsable(personaDao.buscarPersonaPorId(tramRes.getPerId()));
                    if (getTipoTramite().getTtrId().equals("")) {
                        sheet.getRow(b).getCell(7).setCellValue(" ");
                    } else {
                        sheet.getRow(b).getCell(7).setCellValue(getTipoTramite().getTtrDescripcion());
                    }

                    sheet.getRow(b).getCell(6).setCellValue(getResponsable().getPerApellidoPaterno() + " " + getResponsable().getPerApellidoMaterno() + " " + getResponsable().getPerNombre());

                    b++;
                }
                b = b + 4;
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
}
