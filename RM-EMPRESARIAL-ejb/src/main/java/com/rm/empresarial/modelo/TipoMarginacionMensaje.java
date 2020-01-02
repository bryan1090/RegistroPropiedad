/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author JeanCarlos
 */
@Entity
@Table(name = "TipoMarginacionMensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMarginacionMensaje.findAll", query = "SELECT t FROM TipoMarginacionMensaje t ORDER BY t.tmmMensaje")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmId", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmId = :tmmId")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmMensaje", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmMensaje = :tmmMensaje")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmVariable", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmVariable = :tmmVariable")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmEstado", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmEstado = :tmmEstado")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmUser", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmUser = :tmmUser")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmFHR", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmFHR = :tmmFHR")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmOrden", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmOrden = :tmmOrden")
    , @NamedQuery(name = "TipoMarginacionMensaje.findByTmmVariableEditar", query = "SELECT t FROM TipoMarginacionMensaje t WHERE t.tmmVariable = :tmmVariable AND t.tmmId != :tmmId")})
public class TipoMarginacionMensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoMarginacionMensaje.findAll";
    public static final String BUSCAR_POR_VAR = "TipoMarginacionMensaje.findByTmmVariable";
    public static final String BUSCAR_POR_VAR_EDITAR = "TipoMarginacionMensaje.findByTmmVariableEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TmmId")
    private Long tmmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "TmmMensaje")
    private String tmmMensaje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TmmVariable")
    private String tmmVariable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TmmEstado")
    private String tmmEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TmmUser")
    private String tmmUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TmmFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tmmFHR;
    @Column(name = "TmmOrden")
    private Short tmmOrden;
    @JoinColumn(name = "TmcId", referencedColumnName = "TmcId")
    @ManyToOne(optional = false)
    private TipoMarginacion tmcId;

    public TipoMarginacionMensaje() {
    }

    public TipoMarginacionMensaje(Long tmmId) {
        this.tmmId = tmmId;
    }

    public TipoMarginacionMensaje(Long tmmId, String tmmMensaje, String tmmVariable, String tmmEstado, String tmmUser, Date tmmFHR) {
        this.tmmId = tmmId;
        this.tmmMensaje = tmmMensaje;
        this.tmmVariable = tmmVariable;
        this.tmmEstado = tmmEstado;
        this.tmmUser = tmmUser;
        this.tmmFHR = tmmFHR;
    }

    public Long getTmmId() {
        return tmmId;
    }

    public void setTmmId(Long tmmId) {
        this.tmmId = tmmId;
    }

    public String getTmmMensaje() {
        return tmmMensaje;
    }

    public void setTmmMensaje(String tmmMensaje) {
        this.tmmMensaje = tmmMensaje;
    }

    public String getTmmVariable() {
        return tmmVariable;
    }

    public void setTmmVariable(String tmmVariable) {
        this.tmmVariable = tmmVariable;
    }

    public String getTmmEstado() {
        return tmmEstado;
    }

    public void setTmmEstado(String tmmEstado) {
        this.tmmEstado = tmmEstado;
    }

    public String getTmmUser() {
        return tmmUser;
    }

    public void setTmmUser(String tmmUser) {
        this.tmmUser = tmmUser;
    }

    public Date getTmmFHR() {
        return tmmFHR;
    }

    public void setTmmFHR(Date tmmFHR) {
        this.tmmFHR = tmmFHR;
    }

    public Short getTmmOrden() {
        return tmmOrden;
    }

    public void setTmmOrden(Short tmmOrden) {
        this.tmmOrden = tmmOrden;
    }

    public TipoMarginacion getTmcId() {
        return tmcId;
    }

    public void setTmcId(TipoMarginacion tmcId) {
        this.tmcId = tmcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmmId != null ? tmmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMarginacionMensaje)) {
            return false;
        }
        TipoMarginacionMensaje other = (TipoMarginacionMensaje) object;
        if ((this.tmmId == null && other.tmmId != null) || (this.tmmId != null && !this.tmmId.equals(other.tmmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoMarginacionMensaje[ tmmId=" + tmmId + " ]";
    }
    
}
