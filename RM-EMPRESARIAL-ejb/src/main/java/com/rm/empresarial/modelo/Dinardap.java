/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DJimenez
 */
@Entity
@Table(name = "dinardap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dinardap.findAll", query = "SELECT d FROM Dinardap d")
    , @NamedQuery(name = "Dinardap.findByDinId", query = "SELECT d FROM Dinardap d WHERE d.dinId = :dinId")
    , @NamedQuery(name = "Dinardap.findByDinZona", query = "SELECT d FROM Dinardap d WHERE d.dinZona = :dinZona")
    , @NamedQuery(name = "Dinardap.findByDinDescripcion", query = "SELECT d FROM Dinardap d WHERE d.dinDescripcion = :dinDescripcion")
    , @NamedQuery(name = "Dinardap.findByDinSuperficie", query = "SELECT d FROM Dinardap d WHERE d.dinSuperficie = :dinSuperficie")
    , @NamedQuery(name = "Dinardap.findByDinCanton", query = "SELECT d FROM Dinardap d WHERE d.dinCanton = :dinCanton")
    , @NamedQuery(name = "Dinardap.findByDinFecha", query = "SELECT d FROM Dinardap d WHERE d.dinFecha = :dinFecha")})
public class Dinardap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "din_id")
    private Integer dinId;
    @Size(max = 10)
    @Column(name = "din_zona")
    private String dinZona;
    @Size(max = 100)
    @Column(name = "din_descripcion")
    private String dinDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "din_superficie")
    private Double dinSuperficie;
    @Size(max = 50)
    @Column(name = "din_canton")
    private String dinCanton;
    @Column(name = "din_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dinFecha;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "din_lorientacion")
    private String dinLorientacion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "din_ldescripcion")
    private String dinLdescripcion;

    public Dinardap() {
    }

    public Dinardap(Integer dinId) {
        this.dinId = dinId;
    }

    public Integer getDinId() {
        return dinId;
    }

    public void setDinId(Integer dinId) {
        this.dinId = dinId;
    }

    public String getDinZona() {
        return dinZona;
    }

    public void setDinZona(String dinZona) {
        this.dinZona = dinZona;
    }

    public String getDinDescripcion() {
        return dinDescripcion;
    }

    public void setDinDescripcion(String dinDescripcion) {
        this.dinDescripcion = dinDescripcion;
    }

    public Double getDinSuperficie() {
        return dinSuperficie;
    }

    public void setDinSuperficie(Double dinSuperficie) {
        this.dinSuperficie = dinSuperficie;
    }

    public String getDinCanton() {
        return dinCanton;
    }

    public void setDinCanton(String dinCanton) {
        this.dinCanton = dinCanton;
    }

    public Date getDinFecha() {
        return dinFecha;
    }

    public void setDinFecha(Date dinFecha) {
        this.dinFecha = dinFecha;
    }

    public String getDinLorientacion() {
        return dinLorientacion;
    }

    public void setDinLorientacion(String dinLorientacion) {
        this.dinLorientacion = dinLorientacion;
    }

    public String getDinLdescripcion() {
        return dinLdescripcion;
    }

    public void setDinLdescripcion(String dinLdescripcion) {
        this.dinLdescripcion = dinLdescripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dinId != null ? dinId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dinardap)) {
            return false;
        }
        Dinardap other = (Dinardap) object;
        if ((this.dinId == null && other.dinId != null) || (this.dinId != null && !this.dinId.equals(other.dinId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Dinardap[ dinId=" + dinId + " ]";
    }
    
}
