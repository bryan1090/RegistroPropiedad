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
import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Marco
 */
@Named(value = "controladorMenuDinamico")
@ViewScoped
public class ControladorMenuDinamico implements Serializable {

    @Getter
    @Setter
    private MenuModel model = new DefaultMenuModel();

    @Getter
    @Setter
    private MenuModel Principal = new DefaultMenuModel();

    private String idMenu;

    @EJB
    private RolMenuServicio rolMenuServicio;
    @EJB
    private MenuServicio menuServicio;
    @EJB
    private ListaMenuServicio listaMenuServicio;

    @Getter
    @Setter
    private List<ListaMenu> generarListaMenu = new ArrayList<>();

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @PostConstruct
    public void ControladorMenuDinamico() {
        try {
            generarListaMenu.clear();
            generarListaMenu = (listaMenuServicio.ObtenerListaMenu(dataManagerSesion.getUsuarioLogeado().getRolId().getRolId()));

            DefaultMenuItem principal = new DefaultMenuItem("PANTALLA PRINCIPAL");
            principal.setUrl("/dashboard.xhtml");
            principal.setIcon("fa fa-dashboard");
            model.addElement(principal);

            DefaultSubMenu orientacion = new DefaultSubMenu("ORIENTACIÓN");
            orientacion.setIcon("fa fa-bars");
            DefaultMenuItem orientacionItemHorizantal = new DefaultMenuItem("Horizontal");
            DefaultMenuItem orientacionItemVertical = new DefaultMenuItem("Vertical");
            DefaultMenuItem orientacionItemEstatico = new DefaultMenuItem("Estatico");
            DefaultMenuItem orientacionItemSimple = new DefaultMenuItem("Simple");

            orientacionItemHorizantal.setCommand("#{guestPreferences.setLayoutMode('horizontal')}");
            orientacionItemVertical.setCommand("#{guestPreferences.setLayoutMode('overlay')}");
            orientacionItemEstatico.setCommand("#{guestPreferences.setLayoutMode('static')}");
            orientacionItemSimple.setCommand("#{guestPreferences.setLayoutMode('slim')}");

            orientacionItemHorizantal.setAjax(false);
            orientacionItemVertical.setAjax(false);
            orientacionItemEstatico.setAjax(false);
            orientacionItemSimple.setAjax(false);

            orientacionItemHorizantal.setIcon("fa fa-arrows-h");
            orientacionItemVertical.setIcon("fa fa-arrows-v");
            orientacionItemEstatico.setIcon("fa fa-bars");
            orientacionItemSimple.setIcon("fa fa-window-restore");

            orientacion.addElement(orientacionItemHorizantal);
            orientacion.addElement(orientacionItemVertical);
            orientacion.addElement(orientacionItemEstatico);
            orientacion.addElement(orientacionItemSimple);
            model.addElement(orientacion);

            for (ListaMenu listaMenu : generarListaMenu) {                

                if (listaMenu.getNivel() == 0) {
                    DefaultSubMenu subMenu = new DefaultSubMenu(listaMenu.getMenNombre());
                    subMenu.setIcon(listaMenu.getMenIcono());

                    for (ListaMenu m : generarListaMenu) {
                       

                        if(Objects.equals(m.getMenPadre(), listaMenu.getMenId())) {
                            DefaultMenuItem item = new DefaultMenuItem(m.getMenNombre());

                            item.setUrl(m.getMenLink());
                            item.setIcon(m.getMenIcono());
                            subMenu.addElement(item);

                        }

                    }                    
                    model.addElement(subMenu);
                } else {
                    /*
                    if (listaMenu.getMenPadre() == null) {
                        DefaultMenuItem item = new DefaultMenuItem(listaMenu.getMenNombre());
                        model.addElement(item);

                    }*/
                }
            }
        } catch (ServicioExcepcion e) {
        }
    }
}
