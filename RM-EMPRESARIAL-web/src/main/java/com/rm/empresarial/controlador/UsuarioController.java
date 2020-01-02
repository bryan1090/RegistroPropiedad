/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.UsuarioControllerBb;
import com.rm.empresarial.controlador.util.ControladorHuellaDigitalUtil;
import com.rm.empresarial.controlador.util.EncryptPassword;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.Zona;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.RolServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import com.rm.empresarial.servicio.ZonaServicio;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import sun.tools.jar.resources.jar;

/**
 *
 * @author Bryan_Mora
 */
@Named("usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {

    @Inject
    @Getter
    @Setter
    private ControladorHuellaDigitalUtil controladorHuellaDigitalUtil;

    private List<Usuario> items;

    @Getter
    @Setter
    private List<Rol> listaRol;

    @Getter
    @Setter
    private List<Zona> listaZona;

    @Getter
    @Setter
    private String identificacionUsuario = "";

    @Getter
    @Setter
    private String idZona;

    @Getter
    @Setter
    private String idRol;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    @Inject
    private UsuarioControllerBb usuarioControllerBb;

    @Getter
    @Setter
    private Usuario usuarioSeleccionado;

    @Getter
    @Setter
    private ParametroPath parametroPath;

    private List<String> extensiones;

    @EJB
    private UsuarioServicio usuarioServicio;

    @EJB
    private ParametroPathServicio servicioParametroPath;

    @EJB
    private PersonaServicio personaServicio;
    @Inject
    private EncryptPassword encryptPassword;

    @Inject
    DataManagerSesion dataManagerSesion;

    public UsuarioController() {
        items = new ArrayList<>();
    }

    @PostConstruct
    public void postConstructor() {

        items = usuarioServicio.listarTodo();
//            listaRol = rolServicio.listarTodo();
//            listaZona = zonaServicio.listarTodo();
    }

    public void buscarPersona() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.UsuarioController.buscarPersona()");
        usuarioControllerBb.getUsuario().setPerId(personaServicio.encontrarPersonaPorIdentificacion(identificacionUsuario));
    }

    private UsuarioServicio getFacade() {
        return usuarioServicio;
    }

    public Usuario prepareCreate() {
        identificacionUsuario = "";
        usuarioControllerBb.nuevoUsuario();

        return usuarioControllerBb.getUsuario();
    }

    public void prepareEdit() {
        identificacionUsuario = usuarioControllerBb.getUsuario().getPerId().getPerIdentificacion();
        try {
            usuarioControllerBb.getUsuario().setUsuContrasenia(encryptPassword.decrypt(usuarioControllerBb.getUsuario().getUsuContrasenia()));
            usuarioControllerBb.getUsuario().setUsuFirmaContrasenia(encryptPassword.decrypt(usuarioControllerBb.getUsuario().getUsuFirmaContrasenia()));

        } catch (Exception e) {
            addErrorMessage("Error de desencriptador");
        }
    }

    public void clear() {
        usuarioControllerBb.setUsuario(null);
    }

    public void create() throws ServicioExcepcion, Exception {
        if (usuarioServicio.validarLoginCrear(usuarioControllerBb.getUsuario().getUsuLogin())) {
            if (usuarioServicio.validarEmailCrear(usuarioControllerBb.getUsuario().getUsuEmail())) {
                if (usuarioServicio.validarPerIdCrear(usuarioControllerBb.getUsuario().getPerId())) {
                    usuarioControllerBb.getUsuario().setUsuContrasenia(encryptPassword.encrypt(usuarioControllerBb.getUsuario().getUsuContrasenia()));
                    usuarioControllerBb.getUsuario().setUsuFirmaContrasenia(encryptPassword.encrypt(usuarioControllerBb.getUsuario().getUsuFirmaContrasenia()));
                    usuarioControllerBb.getUsuario().setUsuFHR(Calendar.getInstance().getTime());
                    usuarioControllerBb.getUsuario().setUsuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    usuarioControllerBb.getUsuario().setUsuFirHuella(controladorHuellaDigitalUtil.getTextFIR());
                    try {
                        usuarioControllerBb.getUsuario().setPerId(personaServicio.encontrarPersonaPorIdentificacion(identificacionUsuario));
                    } catch (Exception e) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: No existe esa Persona", ""));
                    }
                    persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
                    items = usuarioServicio.listarTodo();
                } else {
                    addErrorMessage("La Persona ingresada ya está vinculada a otro usuario.");
                }
            } else {
                addErrorMessage("El Email ingresado ya está vinculado a otro usuario.");
            }
        } else {
            addErrorMessage("El Login ingresado ya existe.");
        }
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
        clear();
    }

    public void update() throws Exception {
        if (usuarioServicio.validarLoginEditar(usuarioControllerBb.getUsuario().getUsuId(), usuarioControllerBb.getUsuario().getUsuLogin())) {
            if (usuarioServicio.validarEmailEditar(usuarioControllerBb.getUsuario().getUsuId(), usuarioControllerBb.getUsuario().getUsuEmail())) {
                if (usuarioServicio.validarPerIdEditar(usuarioControllerBb.getUsuario().getUsuId(), usuarioControllerBb.getUsuario().getPerId())) {
                    usuarioControllerBb.getUsuario().setUsuContrasenia(encryptPassword.encrypt(usuarioControllerBb.getUsuario().getUsuContrasenia()));
                    usuarioControllerBb.getUsuario().setUsuFirmaContrasenia(encryptPassword.encrypt(usuarioControllerBb.getUsuario().getUsuFirmaContrasenia()));
                    persist(PersistAction.UPDATE,
                            ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated")
                    );
                } else {
                    addErrorMessage("La Persona ingresada ya está vinculada a otro usuario.");
                }
            } else {
                addErrorMessage("El Email ingresado ya está vinculado a otro usuario.");
            }
        } else {
            addErrorMessage("El Login ingresado ya existe.");
        }
        clear();
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (usuarioControllerBb.getUsuario() != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    System.out.println("com.rm.empresarial.controlador.UsuarioController.persist()");
                    usuarioServicio.edit(usuarioControllerBb.getUsuario());
                } else {
                    usuarioServicio.remove(usuarioControllerBb.getUsuario());
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

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            usuarioControllerBb.setUsuario(null); // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public Usuario getUsuario(Long id) {
        return getFacade().find(new Usuario(), id);
    }

//    public List<Usuario> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return  usuarioServicio.listarTodo();
////        return getFacade().findAll();
//    }
//
    public List<Usuario> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = Long.valueOf(value);
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getUsuId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }

    public List<Usuario> getItems() throws ServicioExcepcion {
        items = usuarioServicio.listarTodo();
        return items;
    }

    public void setItems(List<Usuario> listaUsuarios) {
        this.items = listaUsuarios;
    }

    private void buscarImagenUsuarioYeliminar() throws IOException {
        parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
        for (String ext : extensiones) {
            Path origenPath = FileSystems.getDefault().getPath(parametroPath.getPrpPath() + "imagenusuario\\" + usuarioSeleccionado.getUsuLogin() + "." + ext);
            Files.deleteIfExists(origenPath);
            Path destinoPath = FileSystems.getDefault().getPath(
                    FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "\\resources\\apollo-layout\\images\\imagenusuario\\" + usuarioSeleccionado.getUsuLogin() + "." + ext);
            Files.deleteIfExists(destinoPath);
        }
    }

    public void subirImagen(FileUploadEvent event) throws IOException {
        InputStream is = event.getFile().getInputstream();
        String[] parts = event.getFile().getContentType().split("[: /]+");
        String extension = parts[1];
        extensiones = new ArrayList<>();
        extensiones.add(0, "png");
        extensiones.add(1, "jpeg");
        extensiones.add(2, "gif");
        if (extension.equalsIgnoreCase(extensiones.get(0)) || extension.equalsIgnoreCase(extensiones.get(1)) || extension.equalsIgnoreCase(extensiones.get(2))) {
            buscarImagenUsuarioYeliminar();
            Path file;
            String nombreArchivo = usuarioSeleccionado.getUsuLogin();
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            String dirPrincipal = parametroPath.getPrpPath();
            String subDirectorio = "imagenusuario\\";
            Path direccion;
            try (InputStream input = is) {
                direccion = Paths.get(dirPrincipal + subDirectorio + nombreArchivo + "." + extension);
                Files.deleteIfExists(direccion);
                file = Files.createFile(direccion);
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                usuarioSeleccionado.setUsuImagen("images/imagenusuario/" + nombreArchivo + "." + extension);
                usuarioServicio.edit(usuarioSeleccionado);
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace();
            }
        }
    }

    public void capturarHuella() {
        try {
            Process p = Runtime.getRuntime().exec("directorio/ejecutable");
        } catch (Exception e) {
            
        }
    }

    //    public String getIdentificacionUsuario() {
//        return identificacionUsuario;
//    }
//
//    public void setIdentificacionUsuario(String identificacionUsuario) {
//        this.identificacionUsuario = identificacionUsuario;
//    }
//
//   
//
//    public List<Rol> getListaRol() {
//        return listaRol;
//    }
//
//    public void setListaRol(List<Rol> listaRol) {
//        this.listaRol = listaRol;
//    }
//
//    public List<Zona> getListaZona() {
//        return listaZona;
//    }
//
//    public void setListaZona(List<Zona> listaZona) {
//        this.listaZona = listaZona;
//    }
//    
}
