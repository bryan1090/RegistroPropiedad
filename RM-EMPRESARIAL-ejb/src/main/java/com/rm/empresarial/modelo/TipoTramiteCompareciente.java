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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author WILSON
 */
@Entity
@Table(name = "TipoTramiteCompareciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTramiteCompareciente.buscarTodo", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttcEstado = 'A' ORDER BY t.tpcId.tpcDescripcion")
    , @NamedQuery(name = "TipoTramiteCompareciente.buscarPorTtcId", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttcId = :ttcId AND t.ttcEstado = 'A'")
    , @NamedQuery(name = "TipoTramiteCompareciente.buscarPorTtcOrden", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttcOrden = :ttcOrden AND t.ttcEstado = 'A'")
    , @NamedQuery(name = "TipoTramiteCompareciente.buscarPorTtcFHR", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttcFHR = :ttcFHR AND t.ttcEstado = 'A'")
    , @NamedQuery(name = "TipoTramiteCompareciente.buscarPorTtcUser", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttcUser = :ttcUser AND t.ttcEstado = 'A'")
    , @NamedQuery(name = "TipoTramiteCompareciente.buscarPorTtcPropietario", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttcPropietario = :ttcPropietario AND t.ttcEstado = 'A'")
    , @NamedQuery(name = "TipoTramiteCompareciente.buscarPorTipoTramite", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttrId.ttrId = :ttrId AND t.ttcEstado = 'A'")
    , @NamedQuery(name = "TipoTramiteCompareciente.listarTipoComparecientePorTipoTramite", query = "SELECT t.tpcId FROM TipoTramiteCompareciente t WHERE t.ttrId.ttrId = :ttrId AND t.ttcEstado = 'A'")
    , @NamedQuery(name = "TipoTramiteCompareciente.buscarPorTramiteIdPorTipoCompareciente", query = "SELECT t FROM TipoTramiteCompareciente t WHERE t.ttrId.ttrId = :ttrId AND t.tpcId.tpcId = :tpcId AND t.ttcEstado = 'A'")
})

public class TipoTramiteCompareciente implements Serializable {

    public static final String LISTAR_TODO = "TipoTramiteCompareciente.buscarTodo";
    public static final String BUSCAR_POR_ID = "TipoTramiteCompareciente.buscarPorTtcId";
    public static final String BUSCAR_POR_TIPO_TRAMTE = "TipoTramiteCompareciente.buscarPorTipoTramite";
    public static final String LISTAR_TIPO_COMPARECIENTE_POR_TIPO_TRAMITE = "TipoTramiteCompareciente.listarTipoComparecientePorTipoTramite";
    public static final String BUSCAR_POR_TRAMITE_ID_POR_TIPO_COMPARECIENTE = "TipoTramiteCompareciente.buscarPorTramiteIdPorTipoCompareciente";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TtcId")
    private BigDecimal ttcId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TtcOrden")
    private short ttcOrden;
    @Column(name = "TtcFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ttcFHR;
    @Size(max = 20)
    @Column(name = "TtcUser")
    private String ttcUser;
    @Size(max = 1)
    @Column(name = "TtcPropietario")
    private String ttcPropietario;
    @JoinColumn(name = "TpcId", referencedColumnName = "TpcId")
    @ManyToOne(optional = false)
    private TipoCompareciente tpcId;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;
    @Getter
    @Setter
    @Size(max = 1)
    @Column(name = "TtcEstado")
    private String ttcEstado;

    public TipoTramiteCompareciente() {
    }

    public TipoTramiteCompareciente(BigDecimal ttcId) {
        this.ttcId = ttcId;
    }

    public TipoTramiteCompareciente(BigDecimal ttcId, short ttcOrden) {
        this.ttcId = ttcId;
        this.ttcOrden = ttcOrden;
    }

    public BigDecimal getTtcId() {
        return ttcId;
    }

    public void setTtcId(BigDecimal ttcId) {
        this.ttcId = ttcId;
    }

    public short getTtcOrden() {
        return ttcOrden;
    }

    public void setTtcOrden(short ttcOrden) {
        this.ttcOrden = ttcOrden;
    }

    public Date getTtcFHR() {
        return ttcFHR;
    }

    public void setTtcFHR(Date ttcFHR) {
        this.ttcFHR = ttcFHR;
    }

    public String getTtcUser() {
        return ttcUser;
    }

    public void setTtcUser(String ttcUser) {
        this.ttcUser = ttcUser;
    }

    public String getTtcPropietario() {
        return ttcPropietario;
    }

    public void setTtcPropietario(String ttcPropietario) {
        this.ttcPropietario = ttcPropietario;
    }

    public TipoCompareciente getTpcId() {
        return tpcId;
    }

    public void setTpcId(TipoCompareciente tpcId) {
        this.tpcId = tpcId;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ttcId != null ? ttcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTramiteCompareciente)) {
            return false;
        }
        TipoTramiteCompareciente other = (TipoTramiteCompareciente) object;
        if ((this.ttcId == null && other.ttcId != null) || (this.ttcId != null && !this.ttcId.equals(other.ttcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.TipoTramiteCompareciente[ ttcId=" + ttcId + " ]";
    }

}
