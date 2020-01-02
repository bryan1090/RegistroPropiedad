/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.cargaDiferida;

import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class TramiteAccionLazyDataModel extends LazyDataModel<TramiteAccion> {

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    public TramiteAccionLazyDataModel() {
        System.out.println("com.rm.empresarial.controlador.TramiteAccionLazyDataModel.<init>()");
//        this.setRowCount(servicioTramiteAccion.numRegistros());
    }

    @PostConstruct
    public void postConstructor() {
        System.out.println("com.rm.empresarial.controlador.TramiteAccionLazyDataModel.postConstructor()");
        this.setRowCount(servicioTramiteAccion.numTotalRegistros());
    }

    @Override
    public List<TramiteAccion> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<TramiteAccion> lista=new ArrayList<>();
        lista=servicioTramiteAccion.listarPorPaginaConOsinFiltros(first, pageSize, filters);
        if (filters != null && filters.size() > 0) {
            //otherwise it will still show all page links; pages at end will be empty
            this.setRowCount(servicioTramiteAccion.numRegistrosConFiltros(filters));
        }
        else{
           this.setRowCount(servicioTramiteAccion.numTotalRegistros()); 
        }
        if (lista != null) {
            System.out.println("Mostrando " + lista.size() + " registros");
        }
        return lista;

    }

}
