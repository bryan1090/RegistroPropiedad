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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Entity
@Table(name = "TipoIncidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIncidencia.findAll", query = "SELECT t FROM TipoIncidencia t ORDER BY t.tidDescripcion")
    , @NamedQuery(name = "TipoIncidencia.findByTidId", query = "SELECT t FROM TipoIncidencia t WHERE t.tidId = :tidId")
    , @NamedQuery(name = "TipoIncidencia.findByTidDescripcion", query = "SELECT t FROM TipoIncidencia t WHERE t.tidDescripcion = :tidDescripcion")
    , @NamedQuery(name = "TipoIncidencia.findByTidEstado", query = "SELECT t FROM TipoIncidencia t WHERE t.tidEstado = :tidEstado")
    , @NamedQuery(name = "TipoIncidencia.findByTidUser", query = "SELECT t FROM TipoIncidencia t WHERE t.tidUser = :tidUser")
    , @NamedQuery(name = "TipoIncidencia.findByTidFHR", query = "SELECT t FROM TipoIncidencia t WHERE t.tidFHR = :tidFHR")})
public class TipoIncidencia implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoIncidencia.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TidId")
    private Long tidId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TidDescripcion")
    private String tidDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TidEstado")
    private String tidEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TidUser")
    private String tidUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TidFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tidFHR;
    @Getter
    @Setter
    @Size(max = 10)
    @Column(name = "TipCodigoPadre")
    private String tipCodigoPadre;
    @Getter
    @Setter
    @Size(max = 10)
    @Column(name = "TipCodigoHijo")
    private String tipCodigoHijo;

    public TipoIncidencia() {
    }

    public TipoIncidencia(Long tidId) {
        this.tidId = tidId;
    }

    public TipoIncidencia(Long tidId, String tidDescripcion, String tidEstado, String tidUser, Date tidFHR) {
        this.tidId = tidId;
        this.tidDescripcion = tidDescripcion;
        this.tidEstado = tidEstado;
        this.tidUser = tidUser;
        this.tidFHR = tidFHR;
    }

    public Long getTidId() {
        return tidId;
    }

    public void setTidId(Long tidId) {
        this.tidId = tidId;
    }

    public String getTidDescripcion() {
        return tidDescripcion;
    }

    public void setTidDescripcion(String tidDescripcion) {
        this.tidDescripcion = tidDescripcion;
    }

    public String getTidEstado() {
        return tidEstado;
    }

    public void setTidEstado(String tidEstado) {
        this.tidEstado = tidEstado;
    }

    public String getTidUser() {
        return tidUser;
    }

    public void setTidUser(String tidUser) {
        this.tidUser = tidUser;
    }

    public Date getTidFHR() {
        return tidFHR;
    }

    public void setTidFHR(Date tidFHR) {
        this.tidFHR = tidFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tidId != null ? tidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoIncidencia)) {
            return false;
        }
        TipoIncidencia other = (TipoIncidencia) object;
        if ((this.tidId == null && other.tidId != null) || (this.tidId != null && !this.tidId.equals(other.tidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoIncidencia[ tidId=" + tidId + " ]";
    }
    
}
