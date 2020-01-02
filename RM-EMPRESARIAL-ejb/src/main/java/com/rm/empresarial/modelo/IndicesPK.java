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
public class IndicesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "IND_APELLIDOP")
    private String indApellidop;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "IND_APELLIDOM")
    private String indApellidom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 96)
    @Column(name = "IND_NOMBRES")
    private String indNombres;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IND_CODIGO")
    private long indCodigo;

    public IndicesPK() {
    }

    public IndicesPK(String indApellidop, String indApellidom, String indNombres, long indCodigo) {
        this.indApellidop = indApellidop;
        this.indApellidom = indApellidom;
        this.indNombres = indNombres;
        this.indCodigo = indCodigo;
    }

    public String getIndApellidop() {
        return indApellidop;
    }

    public void setIndApellidop(String indApellidop) {
        this.indApellidop = indApellidop;
    }

    public String getIndApellidom() {
        return indApellidom;
    }

    public void setIndApellidom(String indApellidom) {
        this.indApellidom = indApellidom;
    }

    public String getIndNombres() {
        return indNombres;
    }

    public void setIndNombres(String indNombres) {
        this.indNombres = indNombres;
    }

    public long getIndCodigo() {
        return indCodigo;
    }

    public void setIndCodigo(long indCodigo) {
        this.indCodigo = indCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indApellidop != null ? indApellidop.hashCode() : 0);
        hash += (indApellidom != null ? indApellidom.hashCode() : 0);
        hash += (indNombres != null ? indNombres.hashCode() : 0);
        hash += (int) indCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndicesPK)) {
            return false;
        }
        IndicesPK other = (IndicesPK) object;
        if ((this.indApellidop == null && other.indApellidop != null) || (this.indApellidop != null && !this.indApellidop.equals(other.indApellidop))) {
            return false;
        }
        if ((this.indApellidom == null && other.indApellidom != null) || (this.indApellidom != null && !this.indApellidom.equals(other.indApellidom))) {
            return false;
        }
        if ((this.indNombres == null && other.indNombres != null) || (this.indNombres != null && !this.indNombres.equals(other.indNombres))) {
            return false;
        }
        if (this.indCodigo != other.indCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.util.IndicesPK[ indApellidop=" + indApellidop + ", indApellidom=" + indApellidom + ", indNombres=" + indNombres + ", indCodigo=" + indCodigo + " ]";
    }
    
}
