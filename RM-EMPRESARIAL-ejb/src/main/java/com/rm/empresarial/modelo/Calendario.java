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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Calendario",uniqueConstraints = @UniqueConstraint(columnNames = {"CldAnio","CldMes","CldDia"}))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendario.findAll", query = "SELECT c FROM Calendario c")
    , @NamedQuery(name = "Calendario.findByCldId", query = "SELECT c FROM Calendario c WHERE c.cldId = :cldId")
    , @NamedQuery(name = "Calendario.findByCldAnio", query = "SELECT c FROM Calendario c WHERE c.cldAnio = :cldAnio")
    , @NamedQuery(name = "Calendario.findByCldMes", query = "SELECT c FROM Calendario c WHERE c.cldMes = :cldMes")
    , @NamedQuery(name = "Calendario.findByCldDia", query = "SELECT c FROM Calendario c WHERE c.cldDia = :cldDia")
    , @NamedQuery(name = "Calendario.findByCldFecha", query = "SELECT c FROM Calendario c WHERE c.cldFecha = :cldFecha")
    , @NamedQuery(name = "Calendario.findByCldFestivo", query = "SELECT c FROM Calendario c WHERE c.cldFestivo = :cldFestivo")})
public class Calendario implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO="Calendario.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CldId")
    private Long cldId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CldAnio")
    private short cldAnio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CldMes")
    private short cldMes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CldDia")
    private short cldDia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CldFecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cldFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CldFestivo")
    private String cldFestivo;

    public Calendario() {
    }

    public Calendario(Long cldId) {
        this.cldId = cldId;
    }

    public Calendario(Long cldId, short cldAnio, short cldMes, short cldDia, Date cldFecha, String cldFestivo) {
        this.cldId = cldId;
        this.cldAnio = cldAnio;
        this.cldMes = cldMes;
        this.cldDia = cldDia;
        this.cldFecha = cldFecha;
        this.cldFestivo = cldFestivo;
    }

    public Long getCldId() {
        return cldId;
    }

    public void setCldId(Long cldId) {
        this.cldId = cldId;
    }

    public short getCldAnio() {
        return cldAnio;
    }

    public void setCldAnio(short cldAnio) {
        this.cldAnio = cldAnio;
    }

    public short getCldMes() {
        return cldMes;
    }

    public void setCldMes(short cldMes) {
        this.cldMes = cldMes;
    }

    public short getCldDia() {
        return cldDia;
    }

    public void setCldDia(short cldDia) {
        this.cldDia = cldDia;
    }

    public Date getCldFecha() {
        return cldFecha;
    }

    public void setCldFecha(Date cldFecha) {
        this.cldFecha = cldFecha;
    }

    public String getCldFestivo() {
        return cldFestivo;
    }

    public void setCldFestivo(String cldFestivo) {
        this.cldFestivo = cldFestivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cldId != null ? cldId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendario)) {
            return false;
        }
        Calendario other = (Calendario) object;
        if ((this.cldId == null && other.cldId != null) || (this.cldId != null && !this.cldId.equals(other.cldId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.Calendario[ cldId=" + cldId + " ]";
    }
    
}
