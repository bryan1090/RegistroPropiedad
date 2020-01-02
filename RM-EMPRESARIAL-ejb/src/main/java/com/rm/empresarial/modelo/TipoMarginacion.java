/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "TipoMarginacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMarginacion.findAll", query = "SELECT t FROM TipoMarginacion t ORDER BY t.tmcNombre")
    , @NamedQuery(name = "TipoMarginacion.findByTmcId", query = "SELECT t FROM TipoMarginacion t WHERE t.tmcId = :tmcId")
    , @NamedQuery(name = "TipoMarginacion.findByTmcNombre", query = "SELECT t FROM TipoMarginacion t WHERE t.tmcNombre = :tmcNombre")
    , @NamedQuery(name = "TipoMarginacion.findByTmcEstado", query = "SELECT t FROM TipoMarginacion t WHERE t.tmcEstado = :tmcEstado")
    , @NamedQuery(name = "TipoMarginacion.findByTmcUser", query = "SELECT t FROM TipoMarginacion t WHERE t.tmcUser = :tmcUser")
    , @NamedQuery(name = "TipoMarginacion.findByTmcFHR", query = "SELECT t FROM TipoMarginacion t WHERE t.tmcFHR = :tmcFHR")
    , @NamedQuery(name = "TipoMarginacion.findByTmcNombreEditar", query = "SELECT t FROM TipoMarginacion t WHERE t.tmcNombre = :tmcNombre AND t.tmcId != :tmcId")
    , @NamedQuery(name = "TipoMarginacion.findByTmcTextoFijo", query = "SELECT t FROM TipoMarginacion t WHERE t.tmcTextoFijo = :tmcTextoFijo")

})

public class TipoMarginacion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "TipoMarginacion.findAll";
    public static final String BUSCAR_POR_NOM = "TipoMarginacion.findByTmcNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "TipoMarginacion.findByTmcNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TmcId")
    private Long tmcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TmcNombre")
    private String tmcNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TmcEstado")
    private String tmcEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TmcUser")
    private String tmcUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TmcFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tmcFHR;
    @OneToMany(mappedBy = "tmcId")
    private Collection<Marginacion> marginacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tmcId")
    private Collection<TipoMarginacionMensaje> tipoMarginacionMensajeCollection;
    @Size(max = 200)
    @Column(name = "TmcTextoFijo")
    private String tmcTextoFijo;
@Size(max = 10)
    @Column(name = "TmcCodigo")
    private String tmcCodigo;
    public TipoMarginacion() {
    }

    public TipoMarginacion(Long tmcId) {
        this.tmcId = tmcId;
    }

    public TipoMarginacion(Long tmcId, String tmcNombre, String tmcEstado, String tmcUser, Date tmcFHR) {
        this.tmcId = tmcId;
        this.tmcNombre = tmcNombre;
        this.tmcEstado = tmcEstado;
        this.tmcUser = tmcUser;
        this.tmcFHR = tmcFHR;
    }

    public Long getTmcId() {
        return tmcId;
    }

    public void setTmcId(Long tmcId) {
        this.tmcId = tmcId;
    }

    public String getTmcNombre() {
        return tmcNombre;
    }

    public void setTmcNombre(String tmcNombre) {
        this.tmcNombre = tmcNombre;
    }

    public String getTmcEstado() {
        return tmcEstado;
    }

    public void setTmcEstado(String tmcEstado) {
        this.tmcEstado = tmcEstado;
    }

    public String getTmcUser() {
        return tmcUser;
    }

    public void setTmcUser(String tmcUser) {
        this.tmcUser = tmcUser;
    }

    public Date getTmcFHR() {
        return tmcFHR;
    }

    public void setTmcFHR(Date tmcFHR) {
        this.tmcFHR = tmcFHR;
    }

    public String getTmcTextoFijo() {
        return tmcTextoFijo;
    }

    public void setTmcTextoFijo(String tmcTextoFijo) {
        this.tmcTextoFijo = tmcTextoFijo;
    }
    
    public String getTmcCodigo() {
        return tmcCodigo;
    }

    public void setTmcCodigo(String tmcCodigo) {
        this.tmcCodigo = tmcCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmcId != null ? tmcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMarginacion)) {
            return false;
        }
        TipoMarginacion other = (TipoMarginacion) object;
        if ((this.tmcId == null && other.tmcId != null) || (this.tmcId != null && !this.tmcId.equals(other.tmcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.TipoMarginacion[ tmcId=" + tmcId + " ]";
    }

}
