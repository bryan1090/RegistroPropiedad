/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Sesion;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
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
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Marco
 */
@Named("controladorPerfil")
@SessionScoped
public class ControladorPerfil implements Serializable {

    @Getter
    @Setter
    private String usuarioLogin;

    @Getter
    @Setter
    private String apellidoPaterno;
    @Getter
    @Setter
    private String apellidoMaterno;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String direccion;
    @Getter
    @Setter
    private String telefono;
    @Getter
    @Setter
    private String celular;
    @Getter
    @Setter
    private String email;
    
    private List<String> extensiones;

    private Usuario usuario = new Usuario();

    @Getter
    @Setter
    private ParametroPath parametroPath;

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private UsuarioServicio usuarioServicio;

    @EJB
    private ParametroPathServicio servicioParametroPath;

    public void datosPerfil() throws ServicioExcepcion {

        String login = dataManagerSesion.getUsuarioLogeado().getUsuLogin();

        String mail = dataManagerSesion.getUsuarioLogeado().getUsuEmail();

        usuario = usuarioServicio.encontrarUsuarioPorNombreEmail(login, mail);

        usuarioLogin = dataManagerSesion.getUsuarioLogeado().getUsuLogin().trim();
        apellidoPaterno = usuario.getPerId().getPerApellidoPaterno().trim();
        apellidoMaterno = usuario.getPerId().getPerApellidoMaterno().trim();
        nombre = usuario.getPerId().getPerNombre().trim();
        direccion = usuario.getPerId().getPerDireccion().trim();
        telefono = usuario.getPerId().getPerTelefono().trim();
        celular = usuario.getPerId().getPerCelular().trim();
        email = usuario.getPerId().getPerEmail().trim();

    }

    private void buscarImagenUsuarioYeliminar() throws IOException {
        parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", java.util.Calendar.getInstance().getTime());
        for (String ext : extensiones) {
            Path origenPath = FileSystems.getDefault().getPath(parametroPath.getPrpPath() + "imagenusuario\\" + dataManagerSesion.getUsuarioLogeado().getUsuLogin() + "." + ext);
            Files.deleteIfExists(origenPath);
            Path destinoPath = FileSystems.getDefault().getPath(
                FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "\\resources\\apollo-layout\\images\\imagenusuario\\" + dataManagerSesion.getUsuarioLogeado().getUsuLogin() + "." + ext);
            Files.deleteIfExists(destinoPath);
        }
    }

    public void subirImagen(FileUploadEvent event) throws IOException {
        InputStream is = event.getFile().getInputstream();
        String[] parts = event.getFile().getContentType().split("[: /]+");
        System.out.println(" extension:" + parts[1]);
        String extension = parts[1];
        extensiones = new ArrayList<>();
        extensiones.add(0, "png");
        extensiones.add(1, "jpeg");
        extensiones.add(2, "gif");
        if (extension.equalsIgnoreCase(extensiones.get(0)) || extension.equalsIgnoreCase(extensiones.get(1)) || extension.equalsIgnoreCase(extensiones.get(2))) {
            buscarImagenUsuarioYeliminar();
            Usuario u = dataManagerSesion.getUsuarioLogeado();
            Path file;
            String nombreArchivo = usuarioLogin;
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            String dirPrincipal = parametroPath.getPrpPath();
            String subDirectorio = "imagenusuario\\";
            Path direccionPath;
            try (InputStream input = is) {
                direccionPath = Paths.get(dirPrincipal + subDirectorio + nombreArchivo + "." + extension);
                Files.deleteIfExists(direccionPath);
                file = Files.createFile(direccionPath);
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                u.setUsuImagen("images/imagenusuario/" + nombreArchivo + "." + extension);
                usuarioServicio.edit(u);
                dataManagerSesion.logout();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al subir archivo");
            }
        }

    }

}
