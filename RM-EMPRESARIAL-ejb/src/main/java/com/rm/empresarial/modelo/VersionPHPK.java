/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Prueba
 */
@Embeddable
public class VersionPHPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "VphSecuencial")
    private BigInteger vphSecuencial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "VphMatricula")
    private String vphMatricula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VphVersion")
    private int vphVersion;

    public VersionPHPK() {
    }

    public VersionPHPK(BigInteger vphSecuencial, String vphMatricula, int vphVersion) {
        this.vphSecuencial = vphSecuencial;
        this.vphMatricula = vphMatricula;
        this.vphVersion = vphVersion;
    }

    public BigInteger getVphSecuencial() {
        return vphSecuencial;
    }

    public void setVphSecuencial(BigInteger vphSecuencial) {
        this.vphSecuencial = vphSecuencial;
    }

    public String getVphMatricula() {
        return vphMatricula;
    }

    public void setVphMatricula(String vphMatricula) {
        this.vphMatricula = vphMatricula;
    }

    public int getVphVersion() {
        return vphVersion;
    }

    public void setVphVersion(int vphVersion) {
        this.vphVersion = vphVersion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vphSecuencial != null ? vphSecuencial.hashCode() : 0);
        hash += (vphMatricula != null ? vphMatricula.hashCode() : 0);
        hash += (int) vphVersion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VersionPHPK)) {
            return false;
        }
        VersionPHPK other = (VersionPHPK) object;
        if ((this.vphSecuencial == null && other.vphSecuencial != null) || (this.vphSecuencial != null && !this.vphSecuencial.equals(other.vphSecuencial))) {
            return false;
        }
        if ((this.vphMatricula == null && other.vphMatricula != null) || (this.vphMatricula != null && !this.vphMatricula.equals(other.vphMatricula))) {
            return false;
        }
        if (this.vphVersion != other.vphVersion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.VersionPHPK[ vphSecuencial=" + vphSecuencial + ", vphMatricula=" + vphMatricula + ", vphVersion=" + vphVersion + " ]";
    }
    
}
