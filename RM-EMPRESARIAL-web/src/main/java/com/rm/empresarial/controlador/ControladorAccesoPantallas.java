/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ListaMenu;
import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.servicio.ListaMenuServicio;
import com.rm.empresarial.servicio.MenuServicio;
import com.rm.empresarial.servicio.RolMenuServicio;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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
@Named(value = "accesoPantallas")
@ViewScoped
public class ControladorAccesoPantallas implements Serializable {


    @EJB
    private ListaMenuServicio listaMenuServicio;

    @EJB
    private MenuServicio menuServicio;

    @Getter
    @Setter
    private List<ListaMenu> ListaMenu = null;

    @Inject
    DataManagerSesion dataManagerSesion;

    public void accederPantallas(String link) throws ServicioExcepcion, IOException {

        Long idRol = dataManagerSesion.getUsuarioLogeado().getRolId().getRolId();

        listaMenuServicio.ObtenerListaMenu(idRol);

        Boolean acceso = menuServicio.BuscarPorRolYLink(idRol, link);

        if (!acceso) {

            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

        }

    }

}
