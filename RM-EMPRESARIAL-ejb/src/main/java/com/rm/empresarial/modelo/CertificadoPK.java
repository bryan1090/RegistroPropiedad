/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Bryan_Mora
 */
@Embeddable
public class CertificadoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CerNumero")
    private String cerNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CerSecuencial")
    private short cerSecuencial;

    public CertificadoPK() {
    }

    public CertificadoPK(String cerNumero, short cerSecuencial) {
        this.cerNumero = cerNumero;
        this.cerSecuencial = cerSecuencial;
    }

    public String getCerNumero() {
        return cerNumero;
    }

    public void setCerNumero(String cerNumero) {
        this.cerNumero = cerNumero;
    }

    public short getCerSecuencial() {
        return cerSecuencial;
    }

    public void setCerSecuencial(short cerSecuencial) {
        this.cerSecuencial = cerSecuencial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cerNumero != null ? cerNumero.hashCode() : 0);
        hash += (int) cerSecuencial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoPK)) {
            return false;
        }
        CertificadoPK other = (CertificadoPK) object;
        if ((this.cerNumero == null && other.cerNumero != null) || (this.cerNumero != null && !this.cerNumero.equals(other.cerNumero))) {
            return false;
        }
        if (this.cerSecuencial != other.cerSecuencial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CertificadoPK[ cerNumero=" + cerNumero + ", cerSecuencial=" + cerSecuencial + " ]";
    }
    
}
