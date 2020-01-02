/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.DocumentoEntregaTramite;
import com.rm.empresarial.modelo.TipoTramite;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Dependent
public class ControladorModeloDocumentosEntregados implements Serializable{

    @Getter
    @Setter
    private TipoTramite tipoTramite;
    
    @Getter
    @Setter
    private DocumentoEntregaTramite documento;
    
    public ControladorModeloDocumentosEntregados() {
    }
    
    public ControladorModeloDocumentosEntregados(TipoTramite tipoTramite, DocumentoEntregaTramite documento){
        this.tipoTramite = tipoTramite;
        this.documento = documento;
    }
    
}
