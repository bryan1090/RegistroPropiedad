/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosRest;

import ModeloRest.TokenDeRegistroFCM;
import ModeloRest.UsuarioREST;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TokenPush;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.TokenPushServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author JeanCarlos
 */
@Path("tokenpush")
public class ServicioTokenPushREST implements Serializable {
    
    @EJB
    private TokenPushServicio tokenPushServicio;
    
    @EJB
    private UsuarioServicio usuarioServicio;

    //Recive datos en formato json y lo transforma al modelo
    @POST
    @Path("regToken")
    @Consumes(MediaType.APPLICATION_JSON)
    public void registrarToken(TokenDeRegistroFCM tokenDeRegistroFCM) throws ServicioExcepcion {
        TokenPush tokenPush = tokenPushServicio.obtenerPorUsuId(tokenDeRegistroFCM.getUsuario().getUsuId());
        if (tokenPush == null) {
            Usuario usuario = usuarioServicio.encontrarUsuarioPorNombreContrasenia(tokenDeRegistroFCM.getUsuario().getUsuLogin(), tokenDeRegistroFCM.getUsuario().getUsuContrasenia());
            if (usuario != null) {
                tokenPush = new TokenPush();
                tokenPush.setUsuId(usuario);
                tokenPush.setTkpLogin(tokenDeRegistroFCM.getTokenFCM());
                tokenPush.setTkpEstado("A");
                //Persiste en la base de datos
                //  create(tokenPush);
                tokenPushServicio.guardarSalida(tokenPush);
            }
        } else {
            if (!tokenPush.getTkpLogin().equals(tokenDeRegistroFCM.getTokenFCM())) {
                tokenPush.setTkpLogin(tokenDeRegistroFCM.getTokenFCM());
                tokenPush.setTkpEstado("A");
                // edit(tokenPush);
                tokenPushServicio.edit(tokenPush);
            }
        }
        
    }
    
    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public void logout(UsuarioREST usuarioREST) {
        TokenPush tokenPush = tokenPushServicio.obtenerPorUsuId(usuarioREST.getUsuId());
        if (tokenPush != null) {
            tokenPush.setTkpEstado("I");
            tokenPushServicio.edit(tokenPush);
        }
    }
    
}
