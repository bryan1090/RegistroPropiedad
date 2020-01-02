/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.puente.ParroquiaPuente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Wilson
 */
@Stateless
@LocalBean
public class ParroquiaServicio extends ParroquiaPuente implements Serializable {

    private static final long serialVersionUID = 4434832784253135287L;

    public List<SelectItem> listaParroquia() throws ServicioExcepcion {
        List<Parroquia> listaParroquia = null;
        List<SelectItem> listaParroquiaItems = new ArrayList<>();
        listaParroquia = listarTodo();
        for (Parroquia parroquia : listaParroquia) {
            String aux = parroquia.getParNombre();
            String nombreParroquia = String.valueOf(aux);
            SelectItem selectItem = new SelectItem(parroquia.getParId(), nombreParroquia);
            listaParroquiaItems.add(selectItem);
        }
        return listaParroquiaItems;

    }
}
