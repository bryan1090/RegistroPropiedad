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
@Table(name = "EMensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EMensaje.findAll", query = "SELECT e FROM EMensaje e order by 1 desc")
         , @NamedQuery(name = "EMensaje.buscarIdMax", query = "SELECT MAX(e.eMsIdSec) FROM EMensaje e")
    , @NamedQuery(name = "EMensaje.findByEMsIdSec", query = "SELECT e  FROM EMensaje e WHERE e.eMsIdSec = :eMsIdSec")
    , @NamedQuery(name = "EMensaje.findByEMsgDocumento", query = "SELECT e FROM EMensaje e WHERE e.eMsgDocumento = :eMsgDocumento")
    , @NamedQuery(name = "EMensaje.findByEMsgSerie", query = "SELECT e FROM EMensaje e WHERE e.eMsgSerie = :eMsgSerie")
    , @NamedQuery(name = "EMensaje.findByEMsgNumero", query = "SELECT e FROM EMensaje e WHERE e.eMsgNumero = :eMsgNumero")
    , @NamedQuery(name = "EMensaje.findByEMsgMensaje", query = "SELECT e FROM EMensaje e WHERE e.eMsgMensaje = :eMsgMensaje")
    , @NamedQuery(name = "EMensaje.findByEMsgTipo", query = "SELECT e FROM EMensaje e WHERE e.eMsgTipo = :eMsgTipo")
    , @NamedQuery(name = "EMensaje.findByEMsgEstado", query = "SELECT e FROM EMensaje e WHERE e.eMsgEstado = :eMsgEstado")
    , @NamedQuery(name = "EMensaje.findByEMsgDetalle", query = "SELECT e FROM EMensaje e WHERE e.eMsgDetalle = :eMsgDetalle")
    , @NamedQuery(name = "EMensaje.findByEMsgPrograma", query = "SELECT e FROM EMensaje e WHERE e.eMsgPrograma = :eMsgPrograma")
    , @NamedQuery(name = "EMensaje.findByEMsgClave", query = "SELECT e FROM EMensaje e WHERE e.eMsgClave = :eMsgClave")
    , @NamedQuery(name = "EMensaje.findByEMsgAutorizacion", query = "SELECT e FROM EMensaje e WHERE e.eMsgAutorizacion = :eMsgAutorizacion")
    , @NamedQuery(name = "EMensaje.findByEMsgUser", query = "SELECT e FROM EMensaje e WHERE e.eMsgUser = :eMsgUser")
    , @NamedQuery(name = "EMensaje.findByEMsgFHR", query = "SELECT e FROM EMensaje e WHERE e.eMsgFHR = :eMsgFHR")
    , @NamedQuery(name = "EMensaje.findByEMsgErrorSri", query = "SELECT e FROM EMensaje e WHERE e.eMsgErrorSri = :eMsgErrorSri")
    , @NamedQuery(name = "EMensaje.findByEMsgCodigoSri", query = "SELECT e FROM EMensaje e WHERE e.eMsgCodigoSri = :eMsgCodigoSri")
    , @NamedQuery(name = "EMensaje.findBySerieNumero", query = "SELECT e FROM EMensaje e WHERE e.eMsgSerie = :eMsgSerie AND e.eMsgNumero = :eMsgNumero")})
public class EMensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "EMensaje.findAll";
    public static final String BUSCAR_POR_NUMERO = "EMensaje.findByEMsgAutorizacion";
    public static final String BUSCAR_POR_SERIE_Y_NUMERO = "EMensaje.findBySerieNumero";
    public static final String OBTENER_ID="EMensaje.buscarIdMax";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMsIdSec")
    private Long eMsIdSec;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EMsgDocumento")
    private String eMsgDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "EMsgSerie")
    private String eMsgSerie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "EMsgNumero")
    private String eMsgNumero;
    @Size(max = 100)
    @Column(name = "EMsgMensaje")
    private String eMsgMensaje;
    @Size(max = 40)
    @Column(name = "EMsgTipo")
    private String eMsgTipo;
    @Size(max = 20)
    @Column(name = "EMsgEstado")
    private String eMsgEstado;
    @Size(max = 600)
    @Column(name = "EMsgDetalle")
    private String eMsgDetalle;
    @Size(max = 40)
    @Column(name = "EMsgPrograma")
    private String eMsgPrograma;
    @Size(max = 60)
    @Column(name = "EMsgClave")
    private String eMsgClave;
    @Size(max = 60)
    @Column(name = "EMsgAutorizacion")
    private String eMsgAutorizacion;
    @Size(max = 20)
    @Column(name = "EMsgUser")
    private String eMsgUser;
    @Column(name = "EMsgFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eMsgFHR;
    @Size(max = 1)
    @Column(name = "EMsgErrorSri")
    private String eMsgErrorSri;
    @Size(max = 20)
    @Column(name = "EMsgCodigoSri")
    private String eMsgCodigoSri;

    public EMensaje() {
    }

    public EMensaje(Long eMsIdSec) {
        this.eMsIdSec = eMsIdSec;
    }

    public EMensaje(Long eMsIdSec, String eMsgDocumento, String eMsgSerie, String eMsgNumero) {
        this.eMsIdSec = eMsIdSec;
        this.eMsgDocumento = eMsgDocumento;
        this.eMsgSerie = eMsgSerie;
        this.eMsgNumero = eMsgNumero;
    }

    public Long getEMsIdSec() {
        return eMsIdSec;
    }

    public void setEMsIdSec(Long eMsIdSec) {
        this.eMsIdSec = eMsIdSec;
    }

    public String getEMsgDocumento() {
        return eMsgDocumento;
    }

    public void setEMsgDocumento(String eMsgDocumento) {
        this.eMsgDocumento = eMsgDocumento;
    }

    public String getEMsgSerie() {
        return eMsgSerie;
    }

    public void setEMsgSerie(String eMsgSerie) {
        this.eMsgSerie = eMsgSerie;
    }

    public String getEMsgNumero() {
        return eMsgNumero;
    }

    public void setEMsgNumero(String eMsgNumero) {
        this.eMsgNumero = eMsgNumero;
    }

    public String getEMsgMensaje() {
        return eMsgMensaje;
    }

    public void setEMsgMensaje(String eMsgMensaje) {
        this.eMsgMensaje = eMsgMensaje;
    }

    public String getEMsgTipo() {
        return eMsgTipo;
    }

    public void setEMsgTipo(String eMsgTipo) {
        this.eMsgTipo = eMsgTipo;
    }

    public String getEMsgEstado() {
        return eMsgEstado;
    }

    public void setEMsgEstado(String eMsgEstado) {
        this.eMsgEstado = eMsgEstado;
    }

    public String getEMsgDetalle() {
        return eMsgDetalle;
    }

    public void setEMsgDetalle(String eMsgDetalle) {
        this.eMsgDetalle = eMsgDetalle;
    }

    public String getEMsgPrograma() {
        return eMsgPrograma;
    }

    public void setEMsgPrograma(String eMsgPrograma) {
        this.eMsgPrograma = eMsgPrograma;
    }

    public String getEMsgClave() {
        return eMsgClave;
    }

    public void setEMsgClave(String eMsgClave) {
        this.eMsgClave = eMsgClave;
    }

    public String getEMsgAutorizacion() {
        return eMsgAutorizacion;
    }

    public void setEMsgAutorizacion(String eMsgAutorizacion) {
        this.eMsgAutorizacion = eMsgAutorizacion;
    }

    public String getEMsgUser() {
        return eMsgUser;
    }

    public void setEMsgUser(String eMsgUser) {
        this.eMsgUser = eMsgUser;
    }

    public Date getEMsgFHR() {
        return eMsgFHR;
    }

    public void setEMsgFHR(Date eMsgFHR) {
        this.eMsgFHR = eMsgFHR;
    }

    public String getEMsgErrorSri() {
        return eMsgErrorSri;
    }

    public void setEMsgErrorSri(String eMsgErrorSri) {
        this.eMsgErrorSri = eMsgErrorSri;
    }

    public String getEMsgCodigoSri() {
        return eMsgCodigoSri;
    }

    public void setEMsgCodigoSri(String eMsgCodigoSri) {
        this.eMsgCodigoSri = eMsgCodigoSri;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eMsIdSec != null ? eMsIdSec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EMensaje)) {
            return false;
        }
        EMensaje other = (EMensaje) object;
        if ((this.eMsIdSec == null && other.eMsIdSec != null) || (this.eMsIdSec != null && !this.eMsIdSec.equals(other.eMsIdSec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EMensaje[ eMsIdSec=" + eMsIdSec + " ]";
    }

}
