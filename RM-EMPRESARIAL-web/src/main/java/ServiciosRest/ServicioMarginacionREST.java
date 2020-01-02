/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosRest;

import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.servicio.MarginacionServicio;
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
@Path("marginacion")
@RequestScoped
public class ServicioMarginacionREST implements Serializable {
    
    @EJB
    private MarginacionServicio marginacionServicio;
    
    @POST
    @Path("margHuella")
    @Produces(MediaType.APPLICATION_JSON)
    public String regHuella(@FormParam("acta") String acta, @FormParam("huella") String huella) {
        try {
            if (acta != null && huella != null) {
                Marginacion m = marginacionServicio.obtenerPorActNumero(new Long(acta));
                if (m != null) {
                    m.setMrgAlt4(huella);
                    marginacionServicio.edit(m);
                    return "{\"actualizado\":\"si\"}";
                }
            }
        } catch (Exception e) {}
        return "{\"actualizado\":\"no\"}";
    }
    
}
