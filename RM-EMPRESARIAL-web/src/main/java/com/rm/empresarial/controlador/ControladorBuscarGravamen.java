/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.GravamenDetalleDao;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.servicio.PropiedadServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DESARROLLO
 */
@Named(value = "controladorBuscarGravamen")
@ViewScoped
public class ControladorBuscarGravamen implements Serializable {

    @Getter
    @Setter
    private String numeroPredio;

    @Getter
    @Setter
    private String numeroCatastro;

    @Getter
    @Setter
    private List<Gravamen> listaGravamen = new ArrayList<>();

    @Getter
    @Setter
    private List<Gravamen> listaGravamenesSeleccionados = new ArrayList<>();

    @Getter
    @Setter
    private List<GravamenDetalle> listaGravDetalleSeleccionados = new ArrayList<>();

    @Getter
    @Setter
    private List<GravamenDetalle> listaGravamenDetCancelar = new ArrayList<>();
    @Getter
    @Setter
    private List<GravamenDetalle> listaGravamenDetalle = new ArrayList<>();

    @EJB
    private PropiedadServicio propiedadServicio;
    @EJB
    private GravamenDao gravamenDao;
    @EJB
    private GravamenDetalleDao gravamenDetalleDao;

    public ControladorBuscarGravamen() {
        listaGravamenDetCancelar.clear();
    }

    
    public void buscarGravamen() {
        listaGravamen.clear();
        listaGravamenDetCancelar.clear();
        List<Gravamen> listGravamenAuxiliar = new ArrayList<>();
        Propiedad propiedad = propiedadServicio.buscarPropiedadPor_predio_Y_catastroEstadoActivo(numeroPredio.trim(), numeroCatastro.trim());
        if (propiedad != null) {
            listGravamenAuxiliar.clear();
            listGravamenAuxiliar = gravamenDao.buscarPorMatricula_EstadoA_EstadoR(propiedad);
        }
        for (Gravamen gravamen : listGravamenAuxiliar) {
            if (!listaGravamen.contains(gravamen)) {
                listaGravamen.add(gravamen);
            }
        }
        List<GravamenDetalle> gravDetalle = new ArrayList<>();
        for (Gravamen gravamen : listaGravamen) {

            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId(gravamen.getGraId());
            if (gravDetalle.isEmpty()) {
                gravamen.setExisteDetalleGrav(false);
            } else {
                gravamen.setExisteDetalleGrav(true);
            }
        }

    }

    public void mostrarGravamenDetalle(Gravamen gravamen) {
        listaGravamenDetalle.clear();
        listaGravamenDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravamen.getGraId(), gravamen.getGraEstado());
         for (GravamenDetalle gravamenDetalle : listaGravamenDetalle) {
            if (listaGravamenDetCancelar.contains(gravamenDetalle)) {
                listaGravDetalleSeleccionados.add(gravamenDetalle);
            }           
        }
    }

    public void agregarListaGravSeleccionado() {

        for (GravamenDetalle gravamenDetalle : listaGravamenDetalle) {

            if (!listaGravDetalleSeleccionados.contains(gravamenDetalle)) {
                listaGravamenDetCancelar.remove(gravamenDetalle);
            }

        }

        for (GravamenDetalle listaGravDetSelec : listaGravDetalleSeleccionados) {

            if (!listaGravamenDetCancelar.contains(listaGravDetSelec)) {
                listaGravamenDetCancelar.add(listaGravDetSelec);
            }
        }
        if (listaGravDetalleSeleccionados.isEmpty()) {
            for (GravamenDetalle gravamenDetalle : listaGravamenDetalle) {
                listaGravamenDetCancelar.remove(gravamenDetalle);
            }

        }

    }

    public void cancelarGravamenes() {
         List<GravamenDetalle> listaGravDetalle = new ArrayList<>();
        //INACTIVAR GRAVAMEN DETALLE SELECCIONADOS.
        for (GravamenDetalle gravDetalle : listaGravamenDetCancelar) {
            gravDetalle.setGvdEstado("I");
            gravamenDetalleDao.edit(gravDetalle);
            Gravamen gravamen = new Gravamen();
            gravamen = gravDetalle.getGraId();
            listaGravDetalle.clear();
            listaGravDetalle = gravamenDetalleDao.listarPorGravamenId_NoEstadoInactivo(gravamen.getGraId());
            if (listaGravDetalle.isEmpty()) {
                gravamen.setGraEstado("I");
                gravamenDao.edit(gravamen);
            }
            
        }

        //INACTIVAR GRAVAMENES SELECCIONADOS
       
        for (Gravamen gravamen : listaGravamenesSeleccionados) {
            gravamen.setGraEstado("I");
            gravamenDao.edit(gravamen);
        }

        //ACTUALIZAR ESTADO DE GRAVAMEN EN CASO QUE SE HAYA INACTIVADO UN GRAVAMEN QUE AUN TIENE GRAVAMEN DETALLE ACTIVO.
        List<GravamenDetalle> listGravDetalle = new ArrayList<>();
        for (Gravamen gravamen : listaGravamenesSeleccionados) {
            listGravDetalle.clear();
            listGravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravamen.getGraId(), "A");
            if (!listGravDetalle.isEmpty()) {
                gravamen.setGraEstado("A");
                gravamenDao.edit(gravamen);
            }
            listGravDetalle.clear();
            listGravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravamen.getGraId(), "R");
            if (!listGravDetalle.isEmpty()) {
                gravamen.setGraEstado("R");
                gravamenDao.edit(gravamen);
            }
        }
        buscarGravamen();

    }

}
