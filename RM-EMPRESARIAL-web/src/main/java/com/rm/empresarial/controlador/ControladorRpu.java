/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RpuDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Cuantia;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.servicio.CuantiaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named("controladorRpu")
@SessionScoped
public class ControladorRpu implements Serializable {
    
    @EJB
    private RpuDao rpuDao ;
     @EJB
    private RepertorioDao rep ;
      
    @Getter
    @Setter
    private List<RepertorioUsuario> lista = new ArrayList<>();
    
    @Getter
    @Setter
    private List<Repertorio> repertorio = new ArrayList<>();
   
      
    

    public ControladorRpu() {
        System.out.println("com.rm.empresarial.controlador.ControladorRpu.<init>()");
        lista = new ArrayList<>();
        repertorio = new ArrayList<>();
    }
    

 
    public void listar(){       
        setLista(rpuDao.listarRepertorioUsuarioEstado());
    }


}
