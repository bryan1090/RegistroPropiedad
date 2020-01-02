/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosRest;

import ModeloRest.UsuarioREST;
import ModeloRest.encryptedPassword;
import com.rm.empresarial.firebase.MiFirebaseAdmin;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author JeanCarlos
 */
@Path("usuario")
@RequestScoped
public class ServicioUsuarioREST implements Serializable {

    private final encryptedPassword ep;

    @Inject
    private MiFirebaseAdmin miFirebaseAdmin;

    @EJB
    private UsuarioServicio usuarioServicio;

    public ServicioUsuarioREST() {
        ep = new encryptedPassword();
    }

    @GET
    @Path("logueo")
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioREST logueo(@QueryParam("u") String Usuario, @QueryParam("c") String Contrasenia) throws Exception {
        UsuarioREST logueoRespuesta = new UsuarioREST();
        if (Usuario != null && Contrasenia != null) {
            Usuario u = usuarioServicio.encontrarUsuarioPorNombreContrasenia(Usuario, ep.encrypt(Contrasenia));
            if (u != null) {
                //Generacion de Token de Autenticacion
                String customToken = miFirebaseAdmin.generarToken(u.getUsuLogin().trim());
                //Valores a enviar en Json
                logueoRespuesta.setUsuId(u.getUsuId());
                logueoRespuesta.setUsuLogin(u.getUsuLogin().trim());
                logueoRespuesta.setUsuContrasenia(u.getUsuContrasenia());
                logueoRespuesta.setCustomToken(customToken);
                logueoRespuesta.getPersona().setPerIdentificacion(u.getPerId().getPerIdentificacion().trim());
                logueoRespuesta.getPersona().setPerNombre(u.getPerId().getPerNombre().trim());
                logueoRespuesta.getPersona().setPerApellidoPaterno(u.getPerId().getPerApellidoPaterno().trim());
                logueoRespuesta.getPersona().setPerApellidoMaterno(u.getPerId().getPerApellidoMaterno().trim());
            } else {
                logueoRespuesta = null;
            }
        }
        return logueoRespuesta;
    }

    @GET
    @Path("relogueo")
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioREST relogueo(@QueryParam("u") String Usuario, @QueryParam("c") String Contrasenia) throws Exception {
        UsuarioREST relogueoRespuesta = new UsuarioREST();
        if (Usuario != null && Contrasenia != null) {
            Usuario u = usuarioServicio.encontrarUsuarioPorNombreContrasenia(Usuario, Contrasenia);
            if (u != null) {
                relogueoRespuesta.setUsuId(u.getUsuId());
                relogueoRespuesta.setUsuLogin(u.getUsuLogin().trim());
                relogueoRespuesta.setUsuContrasenia(u.getUsuContrasenia());
                relogueoRespuesta.getPersona().setPerIdentificacion(u.getPerId().getPerIdentificacion().trim());
                relogueoRespuesta.getPersona().setPerNombre(u.getPerId().getPerNombre().trim());
                relogueoRespuesta.getPersona().setPerApellidoPaterno(u.getPerId().getPerApellidoPaterno().trim());
                relogueoRespuesta.getPersona().setPerApellidoMaterno(u.getPerId().getPerApellidoMaterno().trim());
            } else {
                relogueoRespuesta = null;
            }
        }
        return relogueoRespuesta;
    }

    @POST
    @Path("regHuella")
    @Produces(MediaType.APPLICATION_JSON)
    public String regHuella(@FormParam("usuario") String usuario, @FormParam("huella") String huella) {
        try {
            if (usuario != null && huella != null) {
                Usuario u = usuarioServicio.encontrarUsuarioPorNombreUsuario(usuario);
                if (u != null) {
                    u.setUsuFirHuella(huella);
                    usuarioServicio.edit(u);
                    return "{\"actualizado\":\"si\"}";
                }
            }
        } catch (Exception e) {}
        return "{\"actualizado\":\"no\"}";
    }

}
