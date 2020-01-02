/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.TipoSuspension;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class TipoSuspensionControllerBb implements Serializable {

    @Getter
    @Setter
    private TipoSuspension tipoSuspension;

    public void nuevoTipoSuspension() {
        setTipoSuspension(new TipoSuspension());
    }
}
