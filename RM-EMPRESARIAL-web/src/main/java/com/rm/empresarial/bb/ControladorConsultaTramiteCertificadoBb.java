/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Tramite;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Dany
 */
@Named(value = "controladorConsultaTramiteCertificadoBb")
@Dependent
public class ControladorConsultaTramiteCertificadoBb implements Serializable {

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private CertificadoCarga
            certificadoSeleccionado;

    public ControladorConsultaTramiteCertificadoBb() {
        if (tramiteSeleccionado != null) {
            certificadoSeleccionado = new CertificadoCarga();
        } else if (certificadoSeleccionado != null) {
            tramiteSeleccionado = new Tramite();
        } else {
            tramiteSeleccionado = new Tramite();
            certificadoSeleccionado = new CertificadoCarga();
        }
    }

}
