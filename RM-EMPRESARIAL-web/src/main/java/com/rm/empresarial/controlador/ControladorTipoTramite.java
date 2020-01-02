/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Prueba
 */
@Named("controladorTipoTramite")
@ViewScoped
public class ControladorTipoTramite implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @Getter
    @Setter
    private List<String> listaComportamiento = new ArrayList<String>();
    @Getter
    @Setter
    List<Integer> listaPeso = new ArrayList<Integer>();
    @Getter
    @Setter
    private String comportamiento;
    @EJB
    private TipoTramiteServicio servicioTipoTramite;
    private List<TipoTramite> items = null;
    private TipoTramite selected;

    public ControladorTipoTramite() {
    }

    @PostConstruct
    public void inicializar() {
        cargarListaComportamiento();
        cargarListaPeso();
    }

    public TipoTramite getSelected() {
        return selected;
    }

    public void setSelected(TipoTramite selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoTramiteServicio getFacade() {
        return servicioTipoTramite;
    }

    public TipoTramite prepareCreate() {
        selected = new TipoTramite();
        selected.setTtrValorFijo(BigDecimal.ZERO);        
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void setearStringComportamiento() {
        switch (selected.getTtrCodigo()) {
            case 1:
                comportamiento = "PROHIBICION";
                break;
            case 2:
                comportamiento = "DEMANDA";
                break;
            case 3:
                comportamiento = "SENTENCIAS";
                break;
            case 4:
                comportamiento = "COPIA TEXTUAL";
                break;
            case 5:
                comportamiento = "COMPRAVENTA";
                break;
            case 6:
                comportamiento = "CANCELACION";
                break;
            case 7:
                comportamiento = "DECLARATORIA PH";
                break;
            case 8:
                comportamiento = "HIPOTECA";
                break;
            case 9:
                comportamiento = "REVOCATORIA PH";
                break;
            case 10:
                comportamiento = "MODIFICATORIA PH";
                break;
            case 11:
                comportamiento = "ADJUDICACION";
                break;
            case 12:
                comportamiento = "FIDEICOMISO";
                break;
            case 13:
                comportamiento = "CANCELACION DE FIDEICOMISO";
                break;
            default:
                comportamiento = "";
                break;

        }
    }

    public void cargarListaComportamiento() {
        listaComportamiento.clear();
        listaComportamiento.add("PROHIBICION");
        listaComportamiento.add("DEMANDA");
        listaComportamiento.add("SENTENCIAS");
        listaComportamiento.add("COPIA TEXTUAL");
        listaComportamiento.add("COMPRAVENTA");
        listaComportamiento.add("CANCELACION");
        listaComportamiento.add("DECLARATORIA PH");
        listaComportamiento.add("HIPOTECA");
        listaComportamiento.add("REVOCATORIA PH");
        listaComportamiento.add("MODIFICATORIA PH");
        listaComportamiento.add("ADJUDICACION");
        listaComportamiento.add("FIDEICOMISO");
        listaComportamiento.add("CANCELACION DE FIDEICOMISO");
    }

    public void cargarListaPeso() {
        listaPeso.clear();
        listaPeso.add(1);
        listaPeso.add(2);
        listaPeso.add(3);
        listaPeso.add(4);
        listaPeso.add(5);
    }

    public void setearCodigo() {
        switch (comportamiento) {
            case "PROHIBICION":
                selected.setTtrCodigo(1);
                break;
            case "DEMANDA":
                selected.setTtrCodigo(2);
                break;
            case "SENTENCIAS":
                selected.setTtrCodigo(3);
                break;
            case "COPIA TEXTUAL":
                selected.setTtrCodigo(4);
                break;
            case "COMPRAVENTA":
                selected.setTtrCodigo(5);
                break;
            case "CANCELACION":
                selected.setTtrCodigo(6);
                break;
            case "DECLARATORIA PH":
                selected.setTtrCodigo(7);
                break;
            case "HIPOTECA":
                selected.setTtrCodigo(8);
                break;
            case "REVOCATORIA PH":
                selected.setTtrCodigo(9);
                break;
            case "MODIFICATORIA PH":
                selected.setTtrCodigo(10);
                break;
            case "ADJUDICACION":
                selected.setTtrCodigo(11);
                break;
            case "FIDEICOMISO":
                selected.setTtrCodigo(12);
                break;
            case "CANCELACION DE FIDEICOMISO":
                selected.setTtrCodigo(13);
                break;
            default:
                selected.setTtrCodigo(13);
                break;
        }

    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoTramite.validarDescripCrear(selected.getTtrDescripcion())) {
            if (comportamiento != null && !comportamiento.equals("")) {
                setearCodigo();
                selected.setTtrUnidadTiempo("H");
                selected.setTtrCantidadTiempo(Short.valueOf("0"));
                selected.setTtrFHR(Calendar.getInstance().getTime());
                selected.setTtrUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteCreated"));
            } else {
                addErrorMessage("Seleecione el tipo de Comportamiento.");
            }
        } else {
            addErrorMessage("La Descripción ingresada ya existe.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoTramite.validarDescripEditar(selected.getTtrId(), selected.getTtrDescripcion())) {
            if (comportamiento != null && !comportamiento.equals("")) {
                setearCodigo();
                selected.setTtrUnidadTiempo("H");
                selected.setTtrCantidadTiempo(Short.valueOf("0"));
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteUpdated"));                
            } else {
                addErrorMessage("Seleecione el tipo de Comportamiento.");
            }
        } else {
            addErrorMessage("La Descripción ingresada ya existe.");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoTramite> getItems() throws ServicioExcepcion {
        items = getFacade().listarTodo();
        return items;
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

    public TipoTramite getTipoTramite(Long id) {
        return getFacade().find(new TipoTramite(), id);
    }

//    public List<TipoTramite> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<TipoTramite> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoTramite.class)
    public static class TipoTramiteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoTramite controller = (ControladorTipoTramite) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTipoTramite");
            return controller.getTipoTramite(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = new Long(value);
            return key;
        }

        String getStringKey(Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoTramite) {
                TipoTramite o = (TipoTramite) object;
                return getStringKey(o.getTtrId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoTramite.class.getName()});
                return null;
            }
        }

    }

}
