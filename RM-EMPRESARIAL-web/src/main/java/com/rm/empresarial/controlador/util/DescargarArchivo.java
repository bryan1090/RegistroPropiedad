/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.bb.ControladorConsultaComprobantesBb;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Wilson Herrera
 */
@Dependent
@ManagedBean
public class DescargarArchivo {
    
    @Inject
    @Getter
    @Setter
    private ControladorConsultaComprobantesBb consultaComprobantesBb;

     private StreamedContent file;
     
     @PostConstruct
     public void inicilizar(){
         getConsultaComprobantesBb().init();
     }

    public DescargarArchivo() {
        String path="C:/repo_local_elec/temporales/"+"001-999-180000028"+".xml";
     
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(path);
        file = new DefaultStreamedContent(stream, "image /xml", "Descargando Archivo");
      }

    public StreamedContent getFile() {
        return file;
    }
}
