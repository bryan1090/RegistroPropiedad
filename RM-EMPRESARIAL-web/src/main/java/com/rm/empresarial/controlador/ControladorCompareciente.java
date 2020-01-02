/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson
 */
@Dependent
public class ControladorCompareciente implements Serializable {

    private static final long serialVersionUID = -644181482730470141L;

    @Getter
    @Setter
    private String nombres;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private String tramite;

    @Getter
    @Setter
    private String compareciente;

    @Getter
    @Setter
    private Integer numeral;

    @Getter
    @Setter
    private Long ttrId;

    @Getter
    @Setter
    private Integer perIdConyugue;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private TipoTramiteCompareciente tramiteCompareciente;

    @Getter
    @Setter
    private Persona personaConyugue;

    @Getter
    @Setter
    private Long parId;

    @Getter
    @Setter
    private String parNombre;

    @Getter
    @Setter
    private BigDecimal ttcId;

    @Getter
    @Setter
    private String tdtCatastro;

    @Getter
    @Setter
    private String tdtPredio;

    public ControladorCompareciente() {
    }

    public void inicializar() {

    }

    public ControladorCompareciente(String nombres, String identificacion, String tramite, String compareciente, Integer numeral, Long ttrId, Persona personaConyugue, Persona persona, TipoTramiteCompareciente tramiteCompareciente, Long parId, String parNombre, BigDecimal ttcId, String tdtCatastro, String tdtPredio) {
        this.nombres = nombres;
        this.identificacion = identificacion;
        this.tramite = tramite;
        this.compareciente = compareciente;
        this.numeral = numeral;
        this.ttrId = ttrId;
        this.personaConyugue = personaConyugue;
        this.persona = persona;
        this.tramiteCompareciente = tramiteCompareciente;
        this.parId = parId;
        this.parNombre = parNombre;
        this.ttcId = ttcId;
        this.tdtCatastro = tdtCatastro;
        this.tdtPredio = tdtPredio;

    }

}
