/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Calendar;
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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "ParametroPath")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroPath.findAll", query = "SELECT p FROM ParametroPath p ORDER BY p.prpTipo")
    , @NamedQuery(name = "ParametroPath.findByPrpId", query = "SELECT p FROM ParametroPath p WHERE p.prpId = :prpId")
    , @NamedQuery(name = "ParametroPath.findByPrpTipo", query = "SELECT p FROM ParametroPath p WHERE p.prpTipo = :prpTipo")
    , @NamedQuery(name = "ParametroPath.findByPrpPath", query = "SELECT p FROM ParametroPath p WHERE p.prpPath = :prpPath")
    , @NamedQuery(name = "ParametroPath.findByPrpFechaInicio", query = "SELECT p FROM ParametroPath p WHERE p.prpFechaInicio = :prpFechaInicio")
    , @NamedQuery(name = "ParametroPath.findByPrpFechaFin", query = "SELECT p FROM ParametroPath p WHERE p.prpFechaFin = :prpFechaFin")
    , @NamedQuery(name = "ParametroPath.findByPrpEstado", query = "SELECT p FROM ParametroPath p WHERE p.prpEstado = :prpEstado")
    , @NamedQuery(name = "ParametroPath.findByPrpUser", query = "SELECT p FROM ParametroPath p WHERE p.prpUser = :prpUser")
    , @NamedQuery(name = "ParametroPath.findByPrpFHR", query = "SELECT p FROM ParametroPath p WHERE p.prpFHR = :prpFHR")
    , @NamedQuery(name = "ParametroPath.encontrarPorTipoPorFecha", query = "SELECT p FROM ParametroPath p WHERE p.prpTipo = :prpTipo AND :fecha BETWEEN p.prpFechaInicio AND p.prpFechaFin")
})
public class ParametroPath implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "ParametroPath.findAll";
    public static final String OBTENER_POR_TIPO_Y_POR_FECHA = "ParametroPath.encontrarPorTipoPorFecha";

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrpId")
    private Long prpId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PrpTipo")
    private String prpTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "PrpPath")
    private String prpPath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrpFechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prpFechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrpFechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prpFechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PrpEstado")
    private String prpEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PrpUser")
    private String prpUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrpFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prpFHR;

    public ParametroPath() {

    }

    public ParametroPath(Long prpId) {
        this.prpId = prpId;
    }

    public ParametroPath(Long prpId, String prpTipo, String prpPath, Date prpFechaInicio, Date prpFechaFin, String prpEstado, String prpUser, Date prpFHR) {

        this.prpId = prpId;
        this.prpTipo = prpTipo;
        this.prpPath = prpPath;
        this.prpFechaInicio = prpFechaInicio;
        this.prpFechaFin = prpFechaFin;
        this.prpEstado = prpEstado;
        this.prpUser = prpUser;
        this.prpFHR = prpFHR;
    }

    public Long getPrpId() {
        return prpId;
    }

    public void setPrpId(Long prpId) {
        this.prpId = prpId;
    }

    public String getPrpTipo() {
        return prpTipo;
    }

    public void setPrpTipo(String prpTipo) {
        this.prpTipo = prpTipo;
    }

    public String getPrpPath() {
        return prpPath;
    }

    public void setPrpPath(String prpPath) {
        this.prpPath = prpPath;
    }

    public Date getPrpFechaInicio() {
        return prpFechaInicio;
    }

    public void setPrpFechaInicio(Date prpFechaInicio) {
        this.prpFechaInicio = prpFechaInicio;
    }

    public Date getPrpFechaFin() {
        return prpFechaFin;
    }

    public void setPrpFechaFin(Date prpFechaFin) {
        this.prpFechaFin = prpFechaFin;
    }

    public String getPrpEstado() {
        return prpEstado;
    }

    public void setPrpEstado(String prpEstado) {
        this.prpEstado = prpEstado;
    }

    public String getPrpUser() {
        return prpUser;
    }

    public void setPrpUser(String prpUser) {
        this.prpUser = prpUser;
    }

    public Date getPrpFHR() {
        return prpFHR;
    }

    public void setPrpFHR(Date prpFHR) {
        this.prpFHR = prpFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prpId != null ? prpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroPath)) {
            return false;
        }
        ParametroPath other = (ParametroPath) object;
        if ((this.prpId == null && other.prpId != null) || (this.prpId != null && !this.prpId.equals(other.prpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ParametroPath[ prpId=" + prpId + " ]";
    }

}
