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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Entity
@Table(name = "Incidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Incidencia.findAll", query = "SELECT i FROM Incidencia i")
    , @NamedQuery(name = "Incidencia.findByIncId", query = "SELECT i FROM Incidencia i WHERE i.incId = :incId")
    , @NamedQuery(name = "Incidencia.findByIncDescripcion", query = "SELECT i FROM Incidencia i WHERE i.incDescripcion = :incDescripcion")
    , @NamedQuery(name = "Incidencia.findByIncEstado", query = "SELECT i FROM Incidencia i WHERE i.incEstado = :incEstado")
    , @NamedQuery(name = "Incidencia.findByIncUser", query = "SELECT i FROM Incidencia i WHERE i.incUser = :incUser")
    , @NamedQuery(name = "Incidencia.findByIncFHR", query = "SELECT i FROM Incidencia i WHERE i.incFHR = :incFHR")
    , @NamedQuery(name = "Incidencia.findByUsuId", query = "SELECT i FROM Incidencia i WHERE i.uinId.usuId = :usuId AND (i.incEstado='A' OR i.incEstado='P') ")})
public class Incidencia implements Serializable {

    public static final String LISTAR_TODO = "Incidencia.findAll";
    public static final String LISTAR_POR_USU_LOGUEADO = "Incidencia.findByUsuId";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IncId")
    private Long incId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "IncDescripcion")
    private String incDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "IncEstado")
    private String incEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "IncUser")
    private String incUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IncFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date incFHR;
    @Getter
    @Setter
    @Column(name = "IncRepNumero")
    private Long incRepNumero;
    @Getter
    @Setter
    @Column(name = "IncRepFecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date incRepFecha;
    @JoinColumn(name = "TidId", referencedColumnName = "TidId")
    @ManyToOne(optional = false)
    private TipoIncidencia tidId;
    @JoinColumn(name = "UinId", referencedColumnName = "UinId")
    @ManyToOne
    private UsuarioInsidencia uinId;

    public Incidencia() {
    }

    public Incidencia(Long incId) {
        this.incId = incId;
    }

    public Incidencia(Long incId, String incDescripcion, String incEstado, String incUser, Date incFHR) {
        this.incId = incId;
        this.incDescripcion = incDescripcion;
        this.incEstado = incEstado;
        this.incUser = incUser;
        this.incFHR = incFHR;
    }

    public Long getIncId() {
        return incId;
    }

    public void setIncId(Long incId) {
        this.incId = incId;
    }

    public String getIncDescripcion() {
        return incDescripcion;
    }

    public void setIncDescripcion(String incDescripcion) {
        this.incDescripcion = incDescripcion;
    }

    public String getIncEstado() {
        return incEstado;
    }

    public void setIncEstado(String incEstado) {
        this.incEstado = incEstado;
    }

    public String getIncUser() {
        return incUser;
    }

    public void setIncUser(String incUser) {
        this.incUser = incUser;
    }

    public Date getIncFHR() {
        return incFHR;
    }

    public void setIncFHR(Date incFHR) {
        this.incFHR = incFHR;
    }

    public TipoIncidencia getTidId() {
        return tidId;
    }

    public void setTidId(TipoIncidencia tidId) {
        this.tidId = tidId;
    }

    public UsuarioInsidencia getUinId() {
        return uinId;
    }

    public void setUinId(UsuarioInsidencia uinId) {
        this.uinId = uinId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incId != null ? incId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incidencia)) {
            return false;
        }
        Incidencia other = (Incidencia) object;
        if ((this.incId == null && other.incId != null) || (this.incId != null && !this.incId.equals(other.incId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Incidencia[ incId=" + incId + " ]";
    }

}
