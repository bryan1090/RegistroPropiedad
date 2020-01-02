/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.BloqueDao;
import com.rm.empresarial.dao.ConfigDetalleDao;
import com.rm.empresarial.dao.LinderoDao;
import com.rm.empresarial.dao.VersionPHDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Bloque;
import com.rm.empresarial.modelo.Compania;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TmpAlicuota;
import com.rm.empresarial.modelo.VersionPH;
import com.rm.empresarial.servicio.AlicuotaServicio;
import com.rm.empresarial.servicio.CompaniaServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorBuscarVersionPH")
@ViewScoped
public class ControladorBuscarVersionPH implements Serializable {

    @Getter
    @Setter
    private Compania compania;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionada;

    @Getter
    @Setter
    private Propiedad propUV = new Propiedad();

    @Getter
    @Setter
    private Long comId;
    @Getter
    @Setter
    private String bloqueNombre;

    @Getter
    @Setter
    private String tipoBusquedaStr;
    @Getter
    @Setter
    private StreamedContent file;
    @Getter
    @Setter
    private StreamedContent filePlant;
    @Getter
    @Setter
    private Boolean renderedBuscarPorBloque = false;

    @Getter
    @Setter
    private Boolean renderedBuscarPorConjunto = false;

    @Getter
    @Setter
    private List<Compania> listaCompania;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedad = new ArrayList<>();
    ;
    
    @Getter
    @Setter
    private List<Integer> listaSecuencialVersionPH = new ArrayList<>();
    @Getter
    @Setter
    private List<VersionPH> listaVersionPH = new ArrayList<>();

    @Getter
    @Setter
    private List<Alicuota> listaAlicuotaMostrar = new ArrayList<>();

    @Getter
    @Setter
    private List<String> listaParroquiaNombre = new ArrayList<>();

    @Getter
    @Setter
    private List<String> listaBloqueNombre = new ArrayList<>();
    @Getter
    @Setter
    private List<Bloque> listaBloques = new ArrayList<>();

    @EJB
    private CompaniaServicio companiaServicio;

    @EJB
    private PropiedadServicio propiedadServicio;

    @EJB
    private AlicuotaServicio alicuotaServicio;

    @EJB
    private VersionPHDao versionPHDao;

    @EJB
    private BloqueDao bloqueDao;

    @EJB
    private LinderoDao linderoDao;
    
    @EJB
    private ConfigDetalleDao configDetalleDao;

    @PostConstruct
    public void init() {
        try {
            listaCompania = companiaServicio.listarTodo();
            listaBloqueNombre = bloqueDao.listarTodoPorNombreUnico();
            descargarPlantillaPH();
        } catch (ServicioExcepcion ex) {
        }
    }

    private void clear() {
        listaPropiedad.clear();
        listaVersionPH.clear();
        listaSecuencialVersionPH.clear();
        propiedadSeleccionada = new Propiedad();
        propUV = new Propiedad();
        listaAlicuotaMostrar.clear();
    }

    public void cargarCompania() throws ServicioExcepcion {
        setCompania(companiaServicio.buscarPorIdCompania(comId));
    }

    public void buscarPropiedades() throws ServicioExcepcion {
        clear();
        switch (tipoBusquedaStr) {
            case "conjunto":
                setListaPropiedad(propiedadServicio.listarPorConjuntoId(compania.getComId()));
                break;
            case "bloque":
                listaBloques = bloqueDao.listarPorNombreBloque(bloqueNombre);
                for (Bloque listaBloque : listaBloques) {
                    listaPropiedad.add(listaBloque.getPrdMatricula());
                }
        }
    }

    public void buscarVersiones() throws ServicioExcepcion {
        try {
            listaSecuencialVersionPH.clear();
            listaVersionPH.clear();
            List<VersionPH> listVersion = new ArrayList<>();
            listaSecuencialVersionPH = versionPHDao.listarSecuencialVersionesPorMatricula(propiedadSeleccionada.getPrdMatricula());
            for (Integer versionSecuencial : listaSecuencialVersionPH) {
                listVersion.clear();
                listVersion = versionPHDao.listarPorNumMatriculaPorSecuencial(propiedadSeleccionada.getPrdMatricula(), versionSecuencial);
                listaVersionPH.addAll(listVersion);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void listarDetalleAlicuotas(Propiedad propiedad) throws ServicioExcepcion {
        propUV = propiedad;
        listaAlicuotaMostrar.clear();
        listaAlicuotaMostrar = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
    }

    public void tipoBusqueda() {
        switch (tipoBusquedaStr) {
            case "bloque":
                renderedBuscarPorBloque = true;
                renderedBuscarPorConjunto = false;
                break;
            case "conjunto":
                renderedBuscarPorBloque = false;
                renderedBuscarPorConjunto = true;
                break;
        }
        clear();
    }

    public void descargarVersionExcel(VersionPH versPH) throws IOException, ServicioExcepcion {
        try {
//DESCARGAR EXCEL DESDE DIRECTORIO
//            if (!versPH.getTalId().getTalPath().trim().isEmpty()) {
//                String path;
//                path = versPH.getTalId().getTalPath();
//                Path file1 = Paths.get(path);
//                InputStream stream = new BufferedInputStream(Files.newInputStream(file1));
//                file = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "Excel_PH.xlsx");
//            } 
            //CREAR ARCHIVO EXCEL
//            else {
            List<TmpAlicuota> listTmpAlic = new ArrayList<>();
            List<VersionPH> listVersPH = new ArrayList<>();
            listVersPH.clear();
            listTmpAlic.clear();
            listVersPH = versionPHDao.listarPorNumMatriculaPorVersion(versPH.getVersionPHPK().getVphMatricula(), versPH.getVersionPHPK().getVphVersion());
            for (VersionPH versionPH : listVersPH) {
                listTmpAlic.add(versionPH.getTalId());
            }
            crearExcelHistorialTmpAlicuota(listTmpAlic);

//            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void crearExcelHistorialTmpAlicuota(List<TmpAlicuota> listTmpAlic) throws InvalidFormatException {

        try {
            /* crear excel nuevo*/
//            String[] columns = {"Es Principal ?", "Codigo Tipo", "Subtipo",
//                "Bloque", "Nombre Unidad", "Piso", "Secuencial", "Tipo Propiedad",
//                "Tipo Propiead solo Referencial", "Alicuota", "Superficie", "Und", "Catastro",
//                "Predio", "LINDERO1", "LINDERO2", "LINDERO3", "LINDERO4", "LINDERO5",
//                "LINDERO6", "LINDERO7", "LINDERO8", "Observación"};        
//
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("HISTORIAL");

//            Row headerRow = sheet.createRow(1);
//            for (int i = 0; i < columns.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(columns[i]);
//            }

            /* buscar el excel (plantilla) y crear nuevo */
            ConfigDetalle configDet = configDetalleDao.buscarPorConfig("DESCARGAR PLANTILLA");
            String excelFilePath = configDet.getConfigDetalleTexto();
            //String excelFilePath = "F:\\RM\\Proyectos Netbeans\\MP_RME\\RM-EMPRESARIAL-web\\src\\main\\webapp\\resources\\plantillaExcel\\PH_PLANTILLA.xlsx";
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

//eliminar filas del excel buscado
            int lastRowNum = sheet.getLastRowNum();
            int rowNum = 2;
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
            Bloque bloq;
            List<Lindero> listLindExc = new ArrayList<>();
            int numRowLindero = 13;
            Propiedad propExc = new Propiedad();
            for (TmpAlicuota tmpAlic : listTmpAlic) {
                Row row = sheet.createRow(rowNum++);
                //Bloque
                bloq = new Bloque();
                bloq = bloqueDao.encontrarBloquePorNumMatriculaPorNombre(tmpAlic.getTalMatricula(), tmpAlic.getTalArea());
                if (bloq != null) {
                    row.createCell(1).setCellValue(bloq.getBloCodigo());
                    row.createCell(2).setCellValue(bloq.getBloSubTipo());
                } else {
                    row.createCell(1).setCellValue("");
                    row.createCell(2).setCellValue("");
                }
                //Predio, Catastro, Linderos
                if (tmpAlic.getTalPredio() != null && tmpAlic.getTalCatastro() != null) {
                    row.createCell(12).setCellValue(tmpAlic.getTalPredio());
                    row.createCell(13).setCellValue(tmpAlic.getTalCatastro());
                    listLindExc.clear();
                    propExc = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tmpAlic.getTalPredio(), tmpAlic.getTalCatastro());
                    if (propExc != null) {
                        row.createCell(4).setCellValue(propExc.getPrdUnidadVivienda());
                        listLindExc = linderoDao.listarPorNumMatricula(propExc.getPrdMatricula());
                    }
                    numRowLindero = 13;
                    for (Lindero lindero : listLindExc) {
                        numRowLindero++;
                        row.createCell(numRowLindero).setCellValue(lindero.getLdrNombre());
                    }

                } else {
                    Alicuota alic = new Alicuota();
                    alic = alicuotaServicio.buscarPorId(tmpAlic.getTalAltId());
                    if (alic != null) {
                        row.createCell(12).setCellValue(alic.getPrdMatricula().getPrdPredio());
                        row.createCell(13).setCellValue(alic.getPrdMatricula().getPrdCatastro());
                        listLindExc.clear();
                        propExc = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(alic.getPrdMatricula().getPrdPredio(), alic.getPrdMatricula().getPrdCatastro());
                        if (propExc != null) {
                            row.createCell(4).setCellValue(propExc.getPrdUnidadVivienda());
                            listLindExc = linderoDao.listarPorNumMatricula(propExc.getPrdMatricula());
                        }
                        numRowLindero = 13;
                        for (Lindero lindero : listLindExc) {
                            numRowLindero++;
                            row.createCell(numRowLindero).setCellValue(lindero.getLdrNombre());
                        }
                    } else {
                        row.createCell(12).setCellValue("");
                        row.createCell(13).setCellValue("");
                    }
                }
//**********************************************//
                row.createCell(0).setCellValue(tmpAlic.getTaPrincipal());
                row.createCell(3).setCellValue(tmpAlic.getTalArea());
                row.createCell(5).setCellValue(tmpAlic.getTalPiso());
                row.createCell(6).setCellValue(tmpAlic.getTalNumero());
                row.createCell(7).setCellValue(tmpAlic.getTalTipoPropiedadCodigo());
                row.createCell(8).setCellValue(tmpAlic.getTalDescripcion());
                row.createCell(9).setCellValue(tmpAlic.getTalAlicuota().toString());
                row.createCell(10).setCellValue(tmpAlic.getTalSuperficie().toString());
                row.createCell(11).setCellValue(tmpAlic.getTalTipoMedidaCodigo());
                row.createCell(22).setCellValue(tmpAlic.getTalObservacion());

            }

            // Resize all columns to fit the content size
//            for (int i = 0; i < columns.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
            workbook.close();
            byte[] bytes = bos.toByteArray();
            InputStream myInputStream = new ByteArrayInputStream(bytes);
            file = new DefaultStreamedContent(myInputStream, "application/vnd.ms-excel", "Excel_PH.xlsx");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void descargarPlantillaPH() {
        try {

            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/plantillaExcel/PH_PLANTILLA.xlsx");

            filePlant = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "PH_PLANTILLA.xlsx");

//                    InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/plantillaExcel/p.jpg");
//        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_boromir.jpg");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public StreamedContent getFile() {
        return file;
    }

}
