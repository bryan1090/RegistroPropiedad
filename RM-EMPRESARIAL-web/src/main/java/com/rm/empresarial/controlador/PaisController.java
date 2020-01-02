package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.PaisControllerBb;
import com.rm.empresarial.modelo.Pais;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.PaisServicio;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.UploadedFile;

@Named("paisController")
@ViewScoped
public class PaisController implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private PaisServicio servicioPais;
    private List<Pais> items;
    private Pais selected;

    @Getter
    @Setter
    @Inject
    private PaisControllerBb paisControllerBb;

    private UploadedFile file;

    public PaisController() {
    }

    @PostConstruct
    public void postConstructor() {
        getPaisControllerBb().nuevoPais();
    }

    //METODOS PROPIOS
    public void subirArchivo(FileUploadEvent event) throws IOException, InvalidFormatException {
        try {

            Boolean error = false;
            List<String> errores = new ArrayList<>();
            if (event.getFile() != null) {
                InputStream is = event.getFile().getInputstream();

                Workbook workbook = WorkbookFactory.create(is);
                Sheet sheet = workbook.getSheetAt(0);

                System.out.println("nombre de hoja: " + sheet.getSheetName());

                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {//iterador filas

                    Row row = rowIterator.next();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    if (row.getRowNum() > 1) {
                        Pais paisNuevo = new Pais();
                        List<String> datosObjeto = new ArrayList<>();
                        error = false;

                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            CellStyle style = workbook.createCellStyle();

                            switch (cell.getCellTypeEnum()) {
                                case NUMERIC:
                                    error = true;
                                    errores.add(cell.getAddress().toString());
                                    style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                                    cell.setCellStyle(style);
                                    break;
                                case STRING:

                                    datosObjeto.add(cell.getStringCellValue().trim());
                                    break;
                                case BOOLEAN:
                                    break;
                                case FORMULA:
                                    break;
                                case BLANK:
                                    System.out.println("Celda Vacía: " + cell.getAddress());
                                    error = true;
                                    errores.add(cell.getAddress().toString());
                                    style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                                    cell.setCellStyle(style);
                                    break;
                                case _NONE:
                                    System.out.println("Celda Vacía: " + cell.getAddress());
                                    errores.add(cell.getAddress().toString());
                                    error = true;
                                    style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                                    cell.setCellStyle(style);
                                    break;
                            }
                        }

                        //SI NO HAY ERRORES Y EL NUMERO DE ATRIBUTOS DEL OBJETO ES CORRECTO
                        if (!error) {
//                            getPaisControllerBb().setPais(servicioPais.asignarID());
                            //paisNuevo = new Pais(getFacade().asignarID(), datosObjeto.get(0), datosObjeto.get(2),
                            //       null, "", datosObjeto.get(1), datosObjeto.get(3));

                            paisNuevo = new Pais(getPaisControllerBb().getPais().getPaiId(), datosObjeto.get(0), datosObjeto.get(2),
                                    null, "", datosObjeto.get(1), datosObjeto.get(3));

                            //getFacade().create(paisNuevo);
                            servicioPais.guardar(paisNuevo);
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(
                                            "¡Carga completada satisfactoriamente!", "asdssasd"));

                        } else {

                            for (String errore : errores) {
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "Error 0005: Ocurrio un error en la carga de archivo (" + errore.toString(), ""));
                            }

//                            FacesContext fc = FacesContext.getCurrentInstance();
//                            ExternalContext ec = fc.getExternalContext();
//
//                            String nombreArchivo = "ListaPaises_" + Calendar.getInstance().getTime().toString() + ".xlsx";
//                            ec.responseReset(); // limpia las cabeceras de la respuesta
//                            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
//                            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador
//
//                            OutputStream output = ec.getResponseOutputStream();
//                            workbook.write(output);//Se escribe el documento
//
//                            fc.responseComplete();//cierra la respuesta
                        }
                    }

                }//FIN DE ITERADOR DE FILAS
//            }
            } else {
                System.out.println("el archivo es nulo");

            }
            //ACTUALIZO LA LISTA DE PAISES
            items = getFacade().listarTodo();
        } catch (Exception e) {
        }

    }

    public void exportarExcel() throws IOException, InvalidFormatException {
        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            InputStream is = ec.getResourceAsStream("/Templates/listaPais.xlsx");

            Workbook wb = WorkbookFactory.create(is);

            Sheet sheet = wb.getSheetAt(0);
            System.out.println("hoja 1: " + wb.getSheetName(0));
//LLENANDO EL EXCEL CON LA LISTA DE OBJETOS 

            int i = 2;//comienza a escribir desde la fila 2
            for (Pais pais : items) {
                Row row = sheet.createRow(i);
                Cell cell;
                cell = row.createCell(0);
                cell.setCellValue(pais.getPaiNombre());
                cell = row.createCell(1);
                cell.setCellValue(pais.getPaiCodigo());
                cell = row.createCell(2);
                cell.setCellValue(pais.getPaiEstado());
                cell = row.createCell(3);
                cell.setCellValue(pais.getPaiInicial());
                i++;
            }

            String nombreArchivo = "ListaPaises_" + Calendar.getInstance().getTime().toString() + ".xlsx";
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

    //METODOS CREADOS POR ASISTENTE
    public Pais prepareCreate() {
        selected = new Pais();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        //esta linea asigna el id de acuerdo a una consulta del max valor de id
        //en la tabla respectiva
//        selected.setPaiId(servicioPais.asignarID().getPaiId());
//System.out.println("**"+servicioPais.asignarID());
//        System.out.println("**"+servicioPais.asignarID2());
//        selected.setPaiId(getPaisControllerBb().getPais().getPaiId());
        if (servicioPais.validarNombreCrear(selected.getPaiNombre())) {
            selected.setPaiFHR(Calendar.getInstance().getTime());
            selected.setPaiUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PaisCreated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioPais.validarNombreEditar(selected.getPaiId(), selected.getPaiNombre())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PaisUpdated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PaisDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    //GETTERS Y SETTERS
    public Pais getSelected() {
        return selected;
    }

    public void setSelected(Pais selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PaisServicio getFacade() {
        return servicioPais;
    }

    public List<Pais> getItems() throws ServicioExcepcion {
        items = getFacade().listarTodo();
        return items;
    }

    public Pais getPais(java.lang.Long id) {
        return getFacade().find(new Pais(), id);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
//
//    public List<Pais> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//

    public List<Pais> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Pais.class)
    public static class PaisControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PaisController controller = (PaisController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "paisController");
            return controller.getPais(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = new java.lang.Long(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pais) {
                Pais o = (Pais) object;
                return getStringKey(o.getPaiId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pais.class.getName()});
                return null;
            }
        }

    }

}
