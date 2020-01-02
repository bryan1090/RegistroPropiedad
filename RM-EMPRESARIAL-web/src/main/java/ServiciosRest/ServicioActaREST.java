/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosRest;

import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.servicio.ActaServicio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author JEAN
 */
@Path("acta")
@RequestScoped
public class ServicioActaREST implements Serializable {
    
    @EJB
    private ActaServicio actaServicio;
    
    @POST
    @Path("actHuella")
    @Produces(MediaType.APPLICATION_JSON)
    public String regHuella(@FormParam("acta") String acta, @FormParam("huella") String huella) {
        try {
            if (acta != null && huella != null) {
                Acta a = actaServicio.buscarPor_Acta(new Long(acta));
                if (a != null) {
                    a.setActDescripcion20(huella);
                    actaServicio.edit(a);
                    return "{\"actualizado\":\"si\"}";
                }
            }
        } catch (Exception e) {}
        return "{\"actualizado\":\"no\"}";
    }
    
}
