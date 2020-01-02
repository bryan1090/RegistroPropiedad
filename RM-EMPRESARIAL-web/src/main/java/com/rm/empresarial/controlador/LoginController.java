/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.controlador.util.EncryptPassword;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CodigoVerificacion;
import com.rm.empresarial.modelo.HistoricoContrasenia;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CodigoVerificacionServicio;
import com.rm.empresarial.servicio.HistoricoContraseniaServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "loginController")
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private UsuarioServicio usuarioServicio;
    @Inject
    private DataManagerSesion dataManagerSesion;

    /**
     * Creates a new instance of LoginController
     */
    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String contrasenia;

    @Getter
    @Setter
    private String confiContrasenia;

    @Getter
    @Setter
    private Usuario usuarioLogeado;

    @Getter
    @Setter
    private Usuario usuarioVerificacion;

    @Getter
    @Setter
    private CodigoVerificacion codigoVerificacion;

    @Getter
    @Setter
    private HistoricoContrasenia historicoContrasenia;

    @Getter
    @Setter
    private ParametroPath parametroPath;

    @Getter
    @Setter
    private List<HistoricoContrasenia> listaHistoricoContrasenia;

    @Getter
    @Setter
    private boolean visibleDlg;

    @Getter
    @Setter
    private boolean visibleDlgRecup;

    @EJB
    private CodigoVerificacionServicio codigoVerificacionServicio;

    @EJB
    private HistoricoContraseniaServicio historicoContraseinaServicio;

    @EJB
    private ParametroPathServicio servicioParametroPath;

    @Inject
    private EncryptPassword encryptPassword;

    @Inject
    private ControladorPerfil controladorPerfil;

    @PostConstruct
    public void postControlador() {
        System.out.println("com.rm.empresarial.controlador.LoginController.postControlador()");
        usuarioLogeado = new Usuario();
        historicoContrasenia = new HistoricoContrasenia();
        login = "";
        contrasenia = "";
        visibleDlg = false;
        visibleDlgRecup = false;
    }



    public void limpiar() {
        usuarioLogeado = new Usuario();
        historicoContrasenia = new HistoricoContrasenia();
        login = "";
        contrasenia = "";
        visibleDlg = false;
        visibleDlgRecup = false;
    }

    private void copiarImagenUsuario() {
        parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", java.util.Calendar.getInstance().getTime());
        String[] parts = dataManagerSesion.getUsuarioLogeado().getUsuImagen().split("[.]+");
        Path origenPath = FileSystems.getDefault().getPath(parametroPath.getPrpPath() + "imagenusuario\\" + dataManagerSesion.getUsuarioLogeado().getUsuLogin() + "." + parts[1].trim());
        Path destinoPath = FileSystems.getDefault().getPath(
                FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "\\resources\\apollo-layout\\images\\imagenusuario\\" + dataManagerSesion.getUsuarioLogeado().getUsuLogin() + "." + parts[1].trim());
        try {
            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void logear() throws ServicioExcepcion, IOException {
        System.out.println("com.rm.empresarial.controlador.LoginController.logear()");
        try {
            usuarioLogeado = usuarioServicio.encontrarUsuarioPorNombreContrasenia(login.toUpperCase(), encryptPassword.encrypt(contrasenia));
            switch (usuarioLogeado.getUsuEstado()) {
                case "R":
                    visibleDlg = true;
                    usuarioVerificacion = new Usuario();
                    codigoVerificacion = new CodigoVerificacion();
                    break;
                case "C":
                    visibleDlgRecup = true;
                    usuarioVerificacion = new Usuario();
                    break;
                default:
                    if (usuarioLogeado.getUsuImagen() == null) {
                        usuarioLogeado.setUsuImagen("images/imagenusuario/user_1.png");
                        usuarioServicio.edit(usuarioLogeado);
                        usuarioLogeado = usuarioServicio.buscarUsuarioPorId(usuarioLogeado.getUsuId().toString());
                    } else if (usuarioLogeado.getUsuImagen().equals("")) {
                        usuarioLogeado.setUsuImagen("images/imagenusuario/user_1.png");
                        usuarioServicio.edit(usuarioLogeado);
                        usuarioLogeado = usuarioServicio.buscarUsuarioPorId(usuarioLogeado.getUsuId().toString());
                    }
                    dataManagerSesion.setUsuarioLogeado(usuarioLogeado);
                    if (!dataManagerSesion.getUsuarioLogeado().getUsuImagen().equals("images/imagenusuario/user_1.png")) {
                        copiarImagenUsuario();
                    }
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");
                    controladorPerfil.datosPerfil();
                    break;
            }
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
//            session.setMaxInactiveInterval(5);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            String mensaje = "Usuario y/o Contraseña incorrectos. Intente nuevamente.";

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensaje));

        }

    }

    public void cambiarContrasenia() throws Exception {
        listaHistoricoContrasenia = historicoContraseinaServicio.encontrarPorUsuario(usuarioLogeado.getUsuId());
        boolean compHistoricoCont = false;
        if (!listaHistoricoContrasenia.isEmpty()) {
            for (HistoricoContrasenia hc : listaHistoricoContrasenia) {
                if (usuarioVerificacion.getUsuContrasenia().equals(encryptPassword.decrypt(hc.getHisConPas()))) {
                    compHistoricoCont = false;
                    break;
                } else {
                    compHistoricoCont = true;
                }
            }
        } else {
            compHistoricoCont = true;
        }
        if (compHistoricoCont) {
            if (usuarioVerificacion.getUsuContrasenia().equals(confiContrasenia)) {
                usuarioLogeado.setUsuContrasenia(encryptPassword.encrypt(usuarioVerificacion.getUsuContrasenia()));
                usuarioLogeado.setUsuEstado("A");
                usuarioServicio.edit(usuarioLogeado);
                historicoContrasenia = new HistoricoContrasenia();
                historicoContrasenia.setUsuId(usuarioServicio.encontrarUsuarioPorNombreUsuario(usuarioLogeado.getPerId().getPerIdentificacion()));
                historicoContrasenia.setHisConPas(encryptPassword.encrypt(usuarioVerificacion.getUsuContrasenia()));
                historicoContrasenia.setHisConFHR(Calendar.getInstance().getTime());
                historicoContrasenia.setHisConUser(usuarioLogeado.getUsuLogin());
                historicoContraseinaServicio.guardar(historicoContrasenia);
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha cambiado la contraseña de su cuenta", ""));
                limpiar();
            } else {
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No coinciden las contraseñas, vuelva a intentarlo"));
                limpiar();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "La nueva contraseña ingresada debe ser diferente a las anteriores", ""));
            limpiar();
        }
    }

    public void verificarCuenta() throws Exception {
        listaHistoricoContrasenia = historicoContraseinaServicio.encontrarPorUsuario(usuarioLogeado.getUsuId());
        boolean compHistoricoCont = false;
        if (!listaHistoricoContrasenia.isEmpty()) {
            for (HistoricoContrasenia hc : listaHistoricoContrasenia) {
                if (usuarioVerificacion.getUsuContrasenia().equals(encryptPassword.decrypt(hc.getHisConPas()))) {
                    compHistoricoCont = false;
                    break;
                } else {
                    compHistoricoCont = true;
                }
            }
        } else {
            compHistoricoCont = true;
        }
        if (compHistoricoCont) {
            if (usuarioVerificacion.getUsuContrasenia().equals(confiContrasenia)) {
                CodigoVerificacion codVerif = codigoVerificacionServicio.encontrarPorUsuario(usuarioLogeado.getUsuId());
                if (codVerif != null) {
                    if (codigoVerificacion.getCvnCodigo().equals(encryptPassword.decrypt(codVerif.getCvnCodigo()))) {
                        usuarioLogeado.setUsuContrasenia(encryptPassword.encrypt(usuarioVerificacion.getUsuContrasenia()));
                        usuarioLogeado.setUsuEstado("A");
                        usuarioServicio.edit(usuarioLogeado);
                        historicoContrasenia = new HistoricoContrasenia();
                        historicoContrasenia.setUsuId(usuarioServicio.encontrarUsuarioPorNombreUsuario(usuarioLogeado.getPerId().getPerIdentificacion()));
                        historicoContrasenia.setHisConPas(encryptPassword.encrypt(usuarioVerificacion.getUsuContrasenia()));
                        historicoContrasenia.setHisConFHR(Calendar.getInstance().getTime());
                        historicoContrasenia.setHisConUser(usuarioLogeado.getUsuLogin());
                        historicoContraseinaServicio.guardar(historicoContrasenia);
                        codVerif.setCvnEstado("I");
                        codigoVerificacionServicio.edit(codVerif);
                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha verificado su cuenta", ""));
                        limpiar();
                    } else {
                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "El código ingresado es incorrecto", ""));
                        limpiar();
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocurrió un error durante el proceso", ""));
                    limpiar();
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No coinciden las contraseñas, vuelva a intentarlo"));
                limpiar();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "La nueva contraseña ingresada debe ser diferente a las anteriores", ""));
            limpiar();
        }
    }

}
