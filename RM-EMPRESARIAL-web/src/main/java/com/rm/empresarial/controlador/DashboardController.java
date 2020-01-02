/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "dashboardController")
@ViewScoped
public class DashboardController implements Serializable {

    /**
     * Creates a new instance of DashboardController
     */
    @Inject
    private LoginController loginController;
    
    
    public DashboardController() {
        
    }
    
    public String getNombreUsuario()
    {
        System.out.println("usuario logeado: "+loginController.getUsuarioLogeado().getUsuLogin());
        return "asd";
    }
}
