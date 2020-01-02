/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DJimenez
 */
@Entity
@Table(name = "marginacionR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarginacionR.findAll", query = "SELECT m FROM MarginacionR m")
    , @NamedQuery(name = "MarginacionR.findByMarId", query = "SELECT m FROM MarginacionR m WHERE m.marId = :marId")
    , @NamedQuery(name = "MarginacionR.findByMarLibro", query = "SELECT m FROM MarginacionR m WHERE m.marLibro = :marLibro")
    , @NamedQuery(name = "MarginacionR.findByMarAnio", query = "SELECT m FROM MarginacionR m WHERE m.marAnio = :marAnio")
    , @NamedQuery(name = "MarginacionR.findByMarInscripcion", query = "SELECT m FROM MarginacionR m WHERE m.marInscripcion = :marInscripcion")})
public class MarginacionR implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mar_id")
    private Integer marId;
    @Column(name = "mar_libro")
    private Short marLibro;
    @Column(name = "mar_anio")
    private Short marAnio;
    @Column(name = "mar_inscripcion")
    private Integer marInscripcion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "mar_marginacion")
    private String marMarginacion;

    public MarginacionR() {
    }

    public MarginacionR(Integer marId) {
        this.marId = marId;
    }

    public Integer getMarId() {
        return marId;
    }

    public void setMarId(Integer marId) {
        this.marId = marId;
    }

    public Short getMarLibro() {
        return marLibro;
    }

    public void setMarLibro(Short marLibro) {
        this.marLibro = marLibro;
    }

    public Short getMarAnio() {
        return marAnio;
    }

    public void setMarAnio(Short marAnio) {
        this.marAnio = marAnio;
    }

    public Integer getMarInscripcion() {
        return marInscripcion;
    }

    public void setMarInscripcion(Integer marInscripcion) {
        this.marInscripcion = marInscripcion;
    }

    public String getMarMarginacion() {
        return marMarginacion;
    }

    public void setMarMarginacion(String marMarginacion) {
        this.marMarginacion = marMarginacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marId != null ? marId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarginacionR)) {
            return false;
        }
        MarginacionR other = (MarginacionR) object;
        if ((this.marId == null && other.marId != null) || (this.marId != null && !this.marId.equals(other.marId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MarginacionR[ marId=" + marId + " ]";
    }

}
