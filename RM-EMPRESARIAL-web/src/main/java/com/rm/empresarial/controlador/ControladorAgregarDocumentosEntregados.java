/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.DocumentoEntregaTramite;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TramiteDocumentoEntrega;
import com.rm.empresarial.modelo.TramiteResponsable;
import com.rm.empresarial.servicio.DocumentoEntregaTramiteServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorAgregarDocumentosEntregados")
@Dependent
public class ControladorAgregarDocumentosEntregados implements Serializable {

    @Getter
    @Setter
    private List<DocumentoEntregaTramite> listaDocumentoEntregaTramite = new ArrayList<DocumentoEntregaTramite>();

    @Getter
    @Setter
    private List<DocumentoEntregaTramite> listaDocumentoEntregaTramiteSeleccion = new ArrayList<DocumentoEntregaTramite>();

    @Getter
    @Setter
    private List<DocumentoEntregaTramite> listaDocumentoEntregaTramiteFiltrado;

    @Getter
    @Setter
    private List<TramiteResponsable> listaTramiteResponsable = new ArrayList<>();

    @Getter
    @Setter
    private List<String> listaPersonaResponsable = new ArrayList<>();

    @Getter
    @Setter
    List<TramiteDocumentoEntrega> listaTramiteDocumentoEntrega = new ArrayList<>();

    @Getter
    @Setter
    private DocumentoEntregaTramite documentoEntregaTramiteSeleccionado;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private TramiteResponsable tramiteResponsable;

    @Getter
    @Setter
    private TramiteResponsable tramiteResponsableSeleccionado;

    @Getter
    @Setter
    private Persona personaResponsable;

    @Getter
    @Setter
    private String estadoResponsable = "true";

    @Getter
    @Setter
    private int docSeleccionado;

    @Getter
    @Setter
    private boolean verificacion = Boolean.FALSE;

    @Getter
    @Setter
    private String aux = "false";

    @Getter
    @Setter
    private String personaResponsableSeleccionada;

    @Getter
    @Setter
    private HashMap<Persona, List<DocumentoEntregaTramite>> datos;

    @Getter
    @Setter
    private List<List<DocumentoEntregaTramite>> listaInfoTramites = new ArrayList<>();
    @Getter
    @Setter
    private List<List<DocumentoEntregaTramite>> listaInfoTramitesFiltrado = new ArrayList<>();
    @Getter
    @Setter
    private List<List<DocumentoEntregaTramite>> listaInfoTramitesSeleccion = new ArrayList<>();

    @Getter
    @Setter
    private List<Persona> listaPersonasResponsables = new ArrayList<>();

    @Getter
    @Setter
    private List<List<String>> listaPersonasResponsablesPorDocumento = new ArrayList<>();

    @Getter
    @Setter
    private int num;

    @Getter
    @Setter
    private List<List<TramiteResponsable>> listaTramitesResponsable = new ArrayList<>();

    @Getter
    @Setter
    private String btnColor = "green-btn";

//    @Getter
//    @Setter
//    private DocumentoEntregaTramite documentoEntregaTramite;
    @EJB
    private DocumentoEntregaTramiteServicio servicioDocumentoEntregaTramite;
    @EJB
    private PersonaServicio servicioPersona;

    public void onSelectTipoTramite() throws ServicioExcepcion {
        if (tipoTramite != null) {
//            if (listaDocumentoEntregaTramiteFiltrado == null) {
            setListaDocumentoEntregaTramiteFiltrado(servicioDocumentoEntregaTramite.listarPorTipoTramite(tipoTramite.getTtrId()));
            for (DocumentoEntregaTramite documentoEntregaTramite : listaDocumentoEntregaTramite) {
                if(listaDocumentoEntregaTramiteFiltrado.contains(documentoEntregaTramite)){
                    listaDocumentoEntregaTramiteFiltrado.remove(documentoEntregaTramite);
                }
            }
            
//            } else {
//                setListaDocumentoEntregaTramiteFiltrado(servicioDocumentoEntregaTramite.listarPorTipoTramite(tipoTramite.getTtrId()));
//                for (int i = 0; i < listaDocumentoEntregaTramite.size(); i++) {
//                    for (int j = 0; j < listaDocumentoEntregaTramiteFiltrado.size(); j++) {
//                        if (listaDocumentoEntregaTramite.get(i).equals(listaDocumentoEntregaTramiteFiltrado.get(j))) {
//                            listaDocumentoEntregaTramiteFiltrado.remove(listaDocumentoEntregaTramite.get(i));
//                        }
//                    }
//                }
//            }
        }
    }

    public void prepararAgregarDocumento() throws ServicioExcepcion {
        listaDocumentoEntregaTramiteFiltrado.clear();
        onSelectTipoTramite();
//        if (listaDocumentoEntregaTramite.isEmpty()) {
//            onSelectTipoTramite();
//        }
    }

    public void agregarDocumento() {
        int aux = 0;
        if (!listaDocumentoEntregaTramiteSeleccion.isEmpty()) {
            for (int i = 0; i < listaDocumentoEntregaTramiteSeleccion.size(); i++) {
                DocumentoEntregaTramite det = listaDocumentoEntregaTramiteSeleccion.get(i);
                for (DocumentoEntregaTramite documentoEntregaTramite : listaDocumentoEntregaTramite) {
                    if (documentoEntregaTramite.getDteId().getDteDescripcion().equals(det.getDteId().getDteDescripcion())) {
                        aux++;
                    }
                }
                if (aux == 0) {
                    listaDocumentoEntregaTramite.add(det);
                    listaPersonaResponsable.add(personaResponsable.getPerApellidoPaterno() + " " + personaResponsable.getPerApellidoMaterno() + " " + personaResponsable.getPerNombre());                    
                }
                listaDocumentoEntregaTramiteFiltrado.remove(det);

            }
            tramiteResponsable = new TramiteResponsable();
            tramiteResponsable.setPerId(personaResponsable.getPerId());
            tramiteResponsable.setTtrId(tipoTramite.getTtrId());
            listaTramiteResponsable.add(tramiteResponsable);
            listaDocumentoEntregaTramiteSeleccion = new ArrayList<>();
            

//            if (!listaDocumentoEntregaTramite.isEmpty()) {
//                int sizeListaDet = listaDocumentoEntregaTramite.size();
//                int sizeListaDetSelec = listaDocumentoEntregaTramiteSeleccion.size();
//                for (int i = 0; i < sizeListaDet; i++) {
//                    for (int j = 0; j < sizeListaDetSelec; j++) {
//                        if (listaDocumentoEntregaTramiteSeleccion.get(j).getDteId().getDteId().equals(listaDocumentoEntregaTramite.get(i).getDteId().getDteId())
//                                && listaDocumentoEntregaTramiteSeleccion.get(j).getTtrId().getTtrId().equals(listaDocumentoEntregaTramite.get(i).getTtrId().getTtrId())) {
//                        } else {
//                            try {
//                                listaDocumentoEntregaTramite.add(servicioDocumentoEntregaTramite.buscarPorDteIdYttrId(listaDocumentoEntregaTramiteSeleccion.get(j).getDteId().getDteId(), listaDocumentoEntregaTramiteSeleccion.get(j).getTtrId().getTtrId()));
//                            } catch (ServicioExcepcion ex) {
//                            }
//                        }
//                    }
//                }
//            } else {
//                
//            }
        }

    }

    public void comprobarNumTramites(int numTramites) {
        if (listaTramiteResponsable.size() == numTramites) {
            setVerificacion(Boolean.TRUE);
        }
    }

    public void borrarFila() {
        setAux("false");
        listaDocumentoEntregaTramite.remove(documentoEntregaTramiteSeleccionado);
        //listaDocumentoEntregaTramiteFiltrado.add(documentoEntregaTramiteSeleccionado);
        listaPersonaResponsable.remove(docSeleccionado);
        if (listaDocumentoEntregaTramite.size() >= 1) {
            for (DocumentoEntregaTramite det : listaDocumentoEntregaTramite) {
                if (det.getTtrId().getTtrDescripcion().equals(documentoEntregaTramiteSeleccionado.getTtrId().getTtrDescripcion())) {
                    setAux("true");
                }
            }
        } else {
            setAux("false");
        }

        System.out.print("" + listaTramiteResponsable.size());
    }

    public DocumentoEntregaTramite getDocumentoEntregaTramite(java.lang.Long id) {
        return servicioDocumentoEntregaTramite.find(new DocumentoEntregaTramite(), id);
    }

    @FacesConverter("convertidorCheck")
    public static class DocumentoEntregaTramiteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorAgregarDocumentosEntregados controller = (ControladorAgregarDocumentosEntregados) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorAgregarDocumentosEntregados");
            return controller.getDocumentoEntregaTramite(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
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
            if (object instanceof DocumentoEntregaTramite) {
                DocumentoEntregaTramite o = (DocumentoEntregaTramite) object;
                return getStringKey(o.getDetId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DocumentoEntregaTramite.class.getName()});
                return null;
            }
        }

    }

    public void guardarTramiteEnMemoria() {
        if (num < listaInfoTramites.size()) {
            listaInfoTramites.set(num, listaDocumentoEntregaTramite);
            listaInfoTramitesFiltrado.set(num, listaDocumentoEntregaTramiteFiltrado);
            listaInfoTramitesSeleccion.set(num, listaDocumentoEntregaTramiteSeleccion);
            listaPersonasResponsables.set(num, personaResponsable);
            listaPersonasResponsablesPorDocumento.set(num, listaPersonaResponsable);
//            listaTramitesResponsable.set(num, listaTramiteResponsable);
        } else {
            listaInfoTramites.add(listaDocumentoEntregaTramite);
            listaInfoTramitesFiltrado.add(listaDocumentoEntregaTramiteFiltrado);
            listaInfoTramitesSeleccion.add(listaDocumentoEntregaTramiteSeleccion);
            listaPersonasResponsables.add(personaResponsable);
            listaPersonasResponsablesPorDocumento.add(listaPersonaResponsable);
            listaTramitesResponsable.add(listaTramiteResponsable);
        }
        listaTramitesResponsable.set(num, listaTramiteResponsable);
        setListaTramiteResponsable(new ArrayList());
        setBtnColor("red-btn");
    }

    public void setVariables() {

    }

}
