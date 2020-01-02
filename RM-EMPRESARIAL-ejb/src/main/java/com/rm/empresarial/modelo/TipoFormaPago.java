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
@Table(name = "TipoFormaPago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoFormaPago.findAll", query = "SELECT t FROM TipoFormaPago t")
    , @NamedQuery(name = "TipoFormaPago.findByTpfId", query = "SELECT t FROM TipoFormaPago t WHERE t.tpfId = :tpfId")
    , @NamedQuery(name = "TipoFormaPago.findByTpfDescripcion", query = "SELECT t FROM TipoFormaPago t WHERE t.tpfDescripcion = :tpfDescripcion")
    , @NamedQuery(name = "TipoFormaPago.findByTpfCodigo", query = "SELECT t FROM TipoFormaPago t WHERE t.tpfCodigo = :tpfCodigo")
    , @NamedQuery(name = "TipoFormaPago.findByTpfUser", query = "SELECT t FROM TipoFormaPago t WHERE t.tpfUser = :tpfUser")
    , @NamedQuery(name = "TipoFormaPago.findByTpfFHR", query = "SELECT t FROM TipoFormaPago t WHERE t.tpfFHR = :tpfFHR")})
public class TipoFormaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoFormaPago.findAll";
    public static final String VALIDAR_DESC = "TipoFormaPago.findByTpfDescripcion";
    public static final String VALIDAR_DESC_CREAR = "TipoFormaPago.findByTpfDescripcion";
    public static final String VALIDAR_DESC_EDITAR = "TipoFormaPago.findByTpfDescripcionEditar";
    public static final String OBTENER_POR_ID = "TipoFormaPago.findByTpfId";
    public static final String BUSCAR_POR_ID = "TipoFormaPago.findByTpfId";

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TpfId")
    private Long tpfId;
    @Size(max = 100)
    @Column(name = "TpfDescripcion")
    private String tpfDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TpfCodigo")
    private String tpfCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpfUser")
    private String tpfUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpfFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpfFHR;

    public TipoFormaPago() {
    }

    public TipoFormaPago(Long tpfId) {
        this.tpfId = tpfId;
    }

    public TipoFormaPago(Long tpfId, String tpfCodigo, String tpfUser, Date tpfFHR) {
        this.tpfId = tpfId;
        this.tpfCodigo = tpfCodigo;
        this.tpfUser = tpfUser;
        this.tpfFHR = tpfFHR;
    }

    public Long getTpfId() {
        return tpfId;
    }

    public void setTpfId(Long tpfId) {
        this.tpfId = tpfId;
    }

    public String getTpfDescripcion() {
        return tpfDescripcion;
    }

    public void setTpfDescripcion(String tpfDescripcion) {
        this.tpfDescripcion = tpfDescripcion;
    }

    public String getTpfCodigo() {
        return tpfCodigo;
    }

    public void setTpfCodigo(String tpfCodigo) {
        this.tpfCodigo = tpfCodigo;
    }

    public String getTpfUser() {
        return tpfUser;
    }

    public void setTpfUser(String tpfUser) {
        this.tpfUser = tpfUser;
    }

    public Date getTpfFHR() {
        return tpfFHR;
    }

    public void setTpfFHR(Date tpfFHR) {
        this.tpfFHR = tpfFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpfId != null ? tpfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoFormaPago)) {
            return false;
        }
        TipoFormaPago other = (TipoFormaPago) object;
        if ((this.tpfId == null && other.tpfId != null) || (this.tpfId != null && !this.tpfId.equals(other.tpfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoFormaPago[ tpfId=" + tpfId + " ]";
    }

}
