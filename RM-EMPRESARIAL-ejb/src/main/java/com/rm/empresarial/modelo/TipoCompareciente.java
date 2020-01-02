/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "TipoCompareciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCompareciente.findAll", query = "SELECT t FROM TipoCompareciente t ORDER BY t.tpcDescripcion")
    , @NamedQuery(name = "TipoCompareciente.findByTpcId", query = "SELECT t FROM TipoCompareciente t WHERE t.tpcId = :tpcId")
    , @NamedQuery(name = "TipoCompareciente.findByTpcCodigo", query = "SELECT t FROM TipoCompareciente t WHERE t.tpcCodigo = :tpcCodigo")
    , @NamedQuery(name = "TipoCompareciente.findByTpcDescripcion", query = "SELECT t FROM TipoCompareciente t WHERE t.tpcDescripcion = :tpcDescripcion")
    , @NamedQuery(name = "TipoCompareciente.findByTpcUser", query = "SELECT t FROM TipoCompareciente t WHERE t.tpcUser = :tpcUser")
    , @NamedQuery(name = "TipoCompareciente.findByTpcFHR", query = "SELECT t FROM TipoCompareciente t WHERE t.tpcFHR = :tpcFHR")
    , @NamedQuery(name = "TipoCompareciente.findByTpcDescripcionEditar", query = "SELECT t FROM TipoCompareciente t WHERE t.tpcDescripcion = :tpcDescripcion AND t.tpcId != :tpcId")})

public class TipoCompareciente implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoCompareciente.findAll";
    public static final String LISTAR_PORID = "TipoCompareciente.findByTpcId";
    public static final String BUSCAR_POR_DESC = "TipoCompareciente.findByTpcDescripcion";
    public static final String BUSCAR_POR_DESC_EDITAR = "TipoCompareciente.findByTpcDescripcionEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TpcId")
    private Long tpcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TpcCodigo")
    private String tpcCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TpcDescripcion")
    private String tpcDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpcUser")
    private String tpcUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpcFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpcFHR;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tpcId")
    private List<TipoTramiteCompareciente> tipoTramiteComparecienteList;
    @Column(name = "TpcCodigoEntregaRecibe")
    private String tpcCodigoEntregaRecibe;

    public TipoCompareciente() {
    }

    public TipoCompareciente(Long tpcId) {
        this.tpcId = tpcId;
    }

    public TipoCompareciente(Long tpcId, String tpcCodigo, String tpcDescripcion, String tpcUser, Date tpcFHR) {
        this.tpcId = tpcId;
        this.tpcCodigo = tpcCodigo;
        this.tpcDescripcion = tpcDescripcion;
        this.tpcUser = tpcUser;
        this.tpcFHR = tpcFHR;
    }

    public Long getTpcId() {
        return tpcId;
    }

    public void setTpcId(Long tpcId) {
        this.tpcId = tpcId;
    }

    public String getTpcCodigoEntregaRecibe() {
        return tpcCodigoEntregaRecibe;
    }

    public void setTpcCodigoEntregaRecibe(String tpcCodigoEntregaRecibe) {
        this.tpcCodigoEntregaRecibe = tpcCodigoEntregaRecibe;
    }
    

    public String getTpcCodigo() {
        return tpcCodigo;
    }

    public void setTpcCodigo(String tpcCodigo) {
        this.tpcCodigo = tpcCodigo;
    }

    public String getTpcDescripcion() {
        return tpcDescripcion;
    }

    public void setTpcDescripcion(String tpcDescripcion) {
        this.tpcDescripcion = tpcDescripcion;
    }

    public String getTpcUser() {
        return tpcUser;
    }

    public void setTpcUser(String tpcUser) {
        this.tpcUser = tpcUser;
    }

    public Date getTpcFHR() {
        return tpcFHR;
    }

    public void setTpcFHR(Date tpcFHR) {
        this.tpcFHR = tpcFHR;
    }

    @XmlTransient
    public List<TipoTramiteCompareciente> getTipoTramiteComparecienteList() {
        return tipoTramiteComparecienteList;
    }

    public void setTipoTramiteComparecienteList(List<TipoTramiteCompareciente> tipoTramiteComparecienteList) {
        this.tipoTramiteComparecienteList = tipoTramiteComparecienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpcId != null ? tpcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCompareciente)) {
            return false;
        }
        TipoCompareciente other = (TipoCompareciente) object;
        if ((this.tpcId == null && other.tpcId != null) || (this.tpcId != null && !this.tpcId.equals(other.tpcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TipoCompareciente[ tpcId=" + tpcId + " ]";
    }

}
