/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiciosRest;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Java
 */
@ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ServiciosRest.CrossOriginResourceSharingFilter.class);
        resources.add(ServiciosRest.ServicioActaREST.class);
        resources.add(ServiciosRest.ServicioMarginacionREST.class);
        resources.add(ServiciosRest.ServicioTokenPushREST.class);
        resources.add(ServiciosRest.ServicioUsuarioREST.class);
    }
    
}
