/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.BloqueDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Bloque;
import com.rm.empresarial.modelo.Compania;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.servicio.AlicuotaServicio;
import com.rm.empresarial.servicio.CompaniaServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorBuscarMatriculas")
@ViewScoped
public class ControladorBuscarMatriculas implements Serializable{
    
    @Getter
    @Setter
    private Compania compania;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionada;
    
    @Getter
    @Setter
    private Propiedad propUV = new Propiedad();
    
    @Getter
    @Setter
    private Long comId;
    
    @Getter
    @Setter
    private String bloqueNombre;
    
    @Getter
    @Setter
    private String tipoBusquedaStr;
    
    @Getter
    @Setter
    private Boolean renderedBuscarPorBloque = false;
    
    @Getter
    @Setter
    private Boolean renderedBuscarPorConjunto = false;
    

    @Getter
    @Setter
    private List<Compania> listaCompania;
    
    @Getter
    @Setter
    private List<String> listaParroquiaNombre = new ArrayList<>();
    
    @Getter
    @Setter
    private List<String> listaBloqueNombre = new ArrayList<>();
    @Getter
    @Setter
    private List<Bloque> listaBloques = new ArrayList<>();
    
    @Getter
    @Setter
    private List<Propiedad> listaPropiedad;   
    
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesHijas;
    
    @Getter
    @Setter
    private List<Alicuota> listaAlicuotaMostrar = new ArrayList<>();
    
    @EJB
    private CompaniaServicio companiaServicio;
    
    @EJB
    private PropiedadServicio propiedadServicio;
    
    @EJB
    private AlicuotaServicio alicuotaServicio;
    
    @EJB
    private BloqueDao bloqueDao;
    
    @PostConstruct
    public void init() {
        try {
            listaCompania = companiaServicio.listarTodo();
            listaBloqueNombre = bloqueDao.listarTodoPorNombreUnico();
            
        } catch (ServicioExcepcion ex) {}
    }
    
    private void clear(){
        listaPropiedad = new ArrayList<>();
        listaPropiedadesHijas = new ArrayList<>();
        propiedadSeleccionada = new Propiedad();
        propUV = new Propiedad();
        listaAlicuotaMostrar = new ArrayList<>();
    }
    
    public void cargarCompania() throws ServicioExcepcion {
        setCompania(companiaServicio.buscarPorIdCompania(comId));
    }
    
    public void buscarPropiedades() throws ServicioExcepcion {
        clear();
        switch (tipoBusquedaStr){
            case "conjunto":
                setListaPropiedad(propiedadServicio.listarPorConjuntoId(compania.getComId()));       
                break;
            case "bloque":
                listaBloques = bloqueDao.listarPorNombreBloque(bloqueNombre);
                for (Bloque listaBloque : listaBloques) {
                    listaPropiedad.add(listaBloque.getPrdMatricula());
                }
        }
        
        
    }
    
    public void buscarPropiedadesHijas() throws ServicioExcepcion{
        setListaPropiedadesHijas(propiedadServicio.listarPorMatriculaPadre(propiedadSeleccionada.getPrdMatricula()));
    }
    
    public void listarDetalleAlicuotas(Propiedad propiedad) throws ServicioExcepcion {
        propUV = propiedad;
        listaAlicuotaMostrar.clear();
        listaAlicuotaMostrar = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
    }
    public void tipoBusqueda(){
        switch (tipoBusquedaStr){
            case "bloque":
                renderedBuscarPorBloque = true;
                renderedBuscarPorConjunto = false;
                break;
            case "conjunto":
                renderedBuscarPorBloque = false;
                renderedBuscarPorConjunto = true;
                break;
        }
        clear();
    }    
            
}
