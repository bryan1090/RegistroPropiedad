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
 * @author Prueba
 */
@Entity
@Table(name = "TipoJuicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoJuicio.findAll", query = "SELECT t FROM TipoJuicio t")
    , @NamedQuery(name = "TipoJuicio.findByTpjId", query = "SELECT t FROM TipoJuicio t WHERE t.tpjId = :tpjId")
    , @NamedQuery(name = "TipoJuicio.findByTpjDescripcion", query = "SELECT t FROM TipoJuicio t WHERE t.tpjDescripcion = :tpjDescripcion")
    , @NamedQuery(name = "TipoJuicio.findByTpjUser", query = "SELECT t FROM TipoJuicio t WHERE t.tpjUser = :tpjUser")
    , @NamedQuery(name = "TipoJuicio.findByTpjEstado", query = "SELECT t FROM TipoJuicio t WHERE t.tpjEstado = :tpjEstado")
    , @NamedQuery(name = "TipoJuicio.findByTpjFHR", query = "SELECT t FROM TipoJuicio t WHERE t.tpjFHR = :tpjFHR")})
public class TipoJuicio implements Serializable {

    public static final String LISTAR_TODO = "TipoJuicio.findAll";
    public static final String BUSCAR_POR_ID = "TipoJuicio.findByTpjId";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpjId")
    private Long tpjId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TpjDescripcion")
    private String tpjDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpjUser")
    private String tpjUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TpjEstado")
    private String tpjEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpjFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpjFHR;

    public TipoJuicio() {
    }

    public TipoJuicio(Long tpjId) {
        this.tpjId = tpjId;
    }

    public TipoJuicio(Long tpjId, String tpjDescripcion, String tpjUser, String tpjEstado, Date tpjFHR) {
        this.tpjId = tpjId;
        this.tpjDescripcion = tpjDescripcion;
        this.tpjUser = tpjUser;
        this.tpjEstado = tpjEstado;
        this.tpjFHR = tpjFHR;
    }

    public Long getTpjId() {
        return tpjId;
    }

    public void setTpjId(Long tpjId) {
        this.tpjId = tpjId;
    }

    public String getTpjDescripcion() {
        return tpjDescripcion;
    }

    public void setTpjDescripcion(String tpjDescripcion) {
        this.tpjDescripcion = tpjDescripcion;
    }

    public String getTpjUser() {
        return tpjUser;
    }

    public void setTpjUser(String tpjUser) {
        this.tpjUser = tpjUser;
    }

    public String getTpjEstado() {
        return tpjEstado;
    }

    public void setTpjEstado(String tpjEstado) {
        this.tpjEstado = tpjEstado;
    }

    public Date getTpjFHR() {
        return tpjFHR;
    }

    public void setTpjFHR(Date tpjFHR) {
        this.tpjFHR = tpjFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpjId != null ? tpjId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoJuicio)) {
            return false;
        }
        TipoJuicio other = (TipoJuicio) object;
        if ((this.tpjId == null && other.tpjId != null) || (this.tpjId != null && !this.tpjId.equals(other.tpjId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.TipoJuicio[ tpjId=" + tpjId + " ]";
    }
    
}
