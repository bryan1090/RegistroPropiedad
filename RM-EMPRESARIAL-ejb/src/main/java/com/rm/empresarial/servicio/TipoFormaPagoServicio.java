/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.TipoFormaPagoDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoFormaPago;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Wilson Herrera
 */
@LocalBean
@Stateless
public class TipoFormaPagoServicio extends TipoFormaPagoDao implements Serializable {
    
      public List<SelectItem> listaTipoFormapago() throws ServicioExcepcion{
        List<TipoFormaPago> listaTipoFormapago=null;
        List<SelectItem> listaTipoFormapagoItems=new ArrayList<>();
        listaTipoFormapago=listarTodo();
        for(TipoFormaPago tipo:listaTipoFormapago){
            String aux=tipo.getTpfDescripcion();
            String descripcion=String.valueOf(aux);
            SelectItem selectItem=new SelectItem(tipo.getTpfId(),descripcion);
            listaTipoFormapagoItems.add(selectItem);
            
        }
        return listaTipoFormapagoItems;
        
    }
      
      public TipoFormaPago buscarFormaPagoPorId(String tfpid) throws ServicioExcepcion{
         
          return buscarPorId(tfpid);
      }
}
