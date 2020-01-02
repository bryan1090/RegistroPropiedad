/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.bb.UsuarioControllerBb;
import com.rm.empresarial.controlador.ControladorHistoricoContrasenia;
import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.HistoricoContrasenia;
import com.rm.empresarial.modelo.RolMenu;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.HistoricoContraseniaServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Marco
 */
@Named(value = "cambiarContraseña")
@ViewScoped
public class ControladorCambiarContraseña implements Serializable {

    @EJB
    private HistoricoContraseniaServicio historicoContraseniaServicio;

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private String passwordActual;

    @Getter
    @Setter
    private String nuevaPassword;

    @Getter
    @Setter
    private String repetirPassword;

    @Inject
    private UsuarioControllerBb usuarioControllerBb;

    @EJB
    private UsuarioServicio usuarioServicio;

    @Inject
    private EncryptPassword encryptPassword;

    @Getter
    @Setter
    private HistoricoContrasenia historicoContrasenia = new HistoricoContrasenia();

    @Getter
    @Setter
    private List<HistoricoContrasenia> listaHistoricoContrasenia;

    @Getter
    @Setter
    private Usuario usuario;

    public void cambiarContraseña() throws ServicioExcepcion, Exception {
        usuario = usuarioServicio.buscarUsuarioPorId(String.valueOf(dataManagerSesion.getUsuarioLogeado().getUsuId()));
        passwordActual = dataManagerSesion.getUsuarioLogeado().getUsuContrasenia();
        listaHistoricoContrasenia = historicoContraseniaServicio.encontrarPorUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId());
        boolean compHistoricoCont = false;
        if (!listaHistoricoContrasenia.isEmpty()) {
            for (HistoricoContrasenia hc : listaHistoricoContrasenia) {
                if (nuevaPassword.equals(encryptPassword.decrypt(hc.getHisConPas()))) {
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
            if (nuevaPassword.equals(repetirPassword)) {
                usuario.setUsuContrasenia(encryptPassword.encrypt(nuevaPassword));
                usuarioServicio.edit(usuario);

                Date fecha = Calendar.getInstance().getTime();
                getHistoricoContrasenia().setHisConFHR(fecha);
                getHistoricoContrasenia().setHisConPas(encryptPassword.encrypt(nuevaPassword));
                getHistoricoContrasenia().setHisConUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getHistoricoContrasenia().setUsuId(usuario);

                historicoContraseniaServicio.guardar(getHistoricoContrasenia());

                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null, new FacesMessage("Successful", "Contraseña cambiada correctamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Las contraseñas no coinciden", null));

            }
        } else {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "La nueva contraseña ingresada debe ser diferente a las anteriores", ""));
        }

    }

    public void cambiarContraseñaPorMail(String password, Usuario userLogin) throws ServicioExcepcion, Exception {
        usuario = userLogin;
        usuario.setUsuContrasenia(encryptPassword.encrypt(password));
        usuario.setUsuEstado("C");
        usuarioServicio.edit(usuario);

    }

}
