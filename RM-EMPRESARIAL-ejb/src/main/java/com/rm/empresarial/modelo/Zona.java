/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Zona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zona.listarTodo", query = "SELECT z FROM Zona z ORDER BY z.zonNombre")
    , @NamedQuery(name = "Zona.findByZonId", query = "SELECT z FROM Zona z WHERE z.zonId = :zonId")
    , @NamedQuery(name = "Zona.findByZonNombre", query = "SELECT z FROM Zona z WHERE z.zonNombre = :zonNombre")
    , @NamedQuery(name = "Zona.findByZonDireccion", query = "SELECT z FROM Zona z WHERE z.zonDireccion = :zonDireccion")
    , @NamedQuery(name = "Zona.findByZonEstado", query = "SELECT z FROM Zona z WHERE z.zonEstado = :zonEstado")
    , @NamedQuery(name = "Zona.findByZonFHR", query = "SELECT z FROM Zona z WHERE z.zonFHR = :zonFHR")
    , @NamedQuery(name = "Zona.findByZonUser", query = "SELECT z FROM Zona z WHERE z.zonUser = :zonUser")})
public class Zona implements Serializable {

    private static final long serialVersionUID = 8109691240893998194L;

    public static final String LISTAR_TODO = "Zona.listarTodo";
    public static final String ENCONTRAR_ZONA_POR_ID = "Zona.findByZonId";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ZonId")
    private Long zonId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ZonNombre")
    private String zonNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ZonDireccion")
    private String zonDireccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ZonEstado")
    private Character zonEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ZonFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zonFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ZonUser")
    private String zonUser;
    @JoinColumn(name = "InsId", referencedColumnName = "InsId")
    @ManyToOne(optional = false)
    private Institucion insId;
    @JoinColumn(name = "ParId", referencedColumnName = "ParId")
    @ManyToOne(optional = false)
    private Parroquia parId;

    public Zona() {
    }

    public Zona(Long zonId) {
        this.zonId = zonId;
    }

    public Zona(Long zonId, String zonNombre, String zonDireccion, Character zonEstado, Date zonFHR, String zonUser) {
        this.zonId = zonId;
        this.zonNombre = zonNombre;
        this.zonDireccion = zonDireccion;
        this.zonEstado = zonEstado;
        this.zonFHR = zonFHR;
        this.zonUser = zonUser;
    }

    public Long getZonId() {
        return zonId;
    }

    public void setZonId(Long zonId) {
        this.zonId = zonId;
    }

    public String getZonNombre() {
        return zonNombre;
    }

    public void setZonNombre(String zonNombre) {
        this.zonNombre = zonNombre;
    }

    public String getZonDireccion() {
        return zonDireccion;
    }

    public void setZonDireccion(String zonDireccion) {
        this.zonDireccion = zonDireccion;
    }

    public Character getZonEstado() {
        return zonEstado;
    }

    public void setZonEstado(Character zonEstado) {
        this.zonEstado = zonEstado;
    }

    public Date getZonFHR() {
        return zonFHR;
    }

    public void setZonFHR(Date zonFHR) {
        this.zonFHR = zonFHR;
    }

    public String getZonUser() {
        return zonUser;
    }

    public void setZonUser(String zonUser) {
        this.zonUser = zonUser;
    }

    public Institucion getInsId() {
        return insId;
    }

    public void setInsId(Institucion insId) {
        this.insId = insId;
    }

    public Parroquia getParId() {
        return parId;
    }

    public void setParId(Parroquia parId) {
        this.parId = parId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zonId != null ? zonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zona)) {
            return false;
        }
        Zona other = (Zona) object;
        if ((this.zonId == null && other.zonId != null) || (this.zonId != null && !this.zonId.equals(other.zonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Zona[ zonId=" + zonId + " ]";
    }

}
