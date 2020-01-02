/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author Java
 */
@Entity
@Table(name = "TmpAlicuota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpAlicuota.findAll", query = "SELECT t FROM TmpAlicuota t")
    , @NamedQuery(name = "TmpAlicuota.findByTalId", query = "SELECT t FROM TmpAlicuota t WHERE t.talId = :talId")
    , @NamedQuery(name = "TmpAlicuota.findByTalNumero", query = "SELECT t FROM TmpAlicuota t WHERE t.talNumero = :talNumero")
    , @NamedQuery(name = "TmpAlicuota.findByTalMatricula", query = "SELECT t FROM TmpAlicuota t WHERE t.talMatricula = :talMatricula")
    , @NamedQuery(name = "TmpAlicuota.findByTalAlicuota", query = "SELECT t FROM TmpAlicuota t WHERE t.talAlicuota = :talAlicuota")
    , @NamedQuery(name = "TmpAlicuota.findByTalArea", query = "SELECT t FROM TmpAlicuota t WHERE t.talArea = :talArea")
    , @NamedQuery(name = "TmpAlicuota.findByTalPiso", query = "SELECT t FROM TmpAlicuota t WHERE t.talPiso = :talPiso")
    , @NamedQuery(name = "TmpAlicuota.findByTalDescripcion", query = "SELECT t FROM TmpAlicuota t WHERE t.talDescripcion = :talDescripcion")
    , @NamedQuery(name = "TmpAlicuota.findByTalCatastro", query = "SELECT t FROM TmpAlicuota t WHERE t.talCatastro = :talCatastro")
    , @NamedQuery(name = "TmpAlicuota.findByTalPredio", query = "SELECT t FROM TmpAlicuota t WHERE t.talPredio = :talPredio")
    , @NamedQuery(name = "TmpAlicuota.findByTalUser", query = "SELECT t FROM TmpAlicuota t WHERE t.talUser = :talUser")
    , @NamedQuery(name = "TmpAlicuota.findByTalFHR", query = "SELECT t FROM TmpAlicuota t WHERE t.talFHR = :talFHR")
    , @NamedQuery(name = "TmpAlicuota.findByTalEstado", query = "SELECT t FROM TmpAlicuota t WHERE t.talEstado = :talEstado")
    , @NamedQuery(name = "TmpAlicuota.findByTaPrincipal", query = "SELECT t FROM TmpAlicuota t WHERE t.taPrincipal = :taPrincipal")
    , @NamedQuery(name = "TmpAlicuota.findByTraAgrupacion", query = "SELECT t FROM TmpAlicuota t WHERE t.traAgrupacion = :traAgrupacion")
    , @NamedQuery(name = "TmpAlicuota.findByTraConjunto", query = "SELECT t FROM TmpAlicuota t WHERE t.traConjunto = :traConjunto")
    , @NamedQuery(name = "TmpAlicuota.findByTraConjuntoId", query = "SELECT t FROM TmpAlicuota t WHERE t.traConjuntoId = :traConjuntoId")
    , @NamedQuery(name = "TmpAlicuota.findByTalVersion", query = "SELECT t FROM TmpAlicuota t WHERE t.talVersion = :talVersion")
    , @NamedQuery(name = "TmpAlicuota.findByTalPath", query = "SELECT t FROM TmpAlicuota t WHERE t.talPath = :talPath")
    , @NamedQuery(name = "TmpAlicuota.findByTalObservacion", query = "SELECT t FROM TmpAlicuota t WHERE t.talObservacion = :talObservacion")
    , @NamedQuery(name = "TmpAlicuota.findByTalTipoPropiedadCodigo", query = "SELECT t FROM TmpAlicuota t WHERE t.talTipoPropiedadCodigo = :talTipoPropiedadCodigo")
    , @NamedQuery(name = "TmpAlicuota.findByTalTipoMedidaCodigo", query = "SELECT t FROM TmpAlicuota t WHERE t.talTipoMedidaCodigo = :talTipoMedidaCodigo")
    , @NamedQuery(name = "TmpAlicuota.findByTalSuperficie", query = "SELECT t FROM TmpAlicuota t WHERE t.talSuperficie = :talSuperficie")})
public class TmpAlicuota implements Serializable {

    public static final String BUSCAR_POR_ID = "TmpAlicuota.findByTalId";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TalId")
    private Long talId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TalNumero")
    private String talNumero;
    @Size(min = 1, max = 100)
    @Column(name = "TalMatricula")
    private String talMatricula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TalAlicuota")
    private BigDecimal talAlicuota;
    @Size(max = 40)
    @Column(name = "TalArea")
    private String talArea;
    @Size(max = 40)
    @Column(name = "TalPiso")
    private String talPiso;
    @Size(max = 200)
    @Column(name = "TalDescripcion")
    private String talDescripcion;
    @Size(max = 20)
    @Column(name = "TalCatastro")
    private String talCatastro;
    @Size(max = 20)
    @Column(name = "TalPredio")
    private String talPredio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TalUser")
    private String talUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TalFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date talFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TalEstado")
    private String talEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TaPrincipal")
    private short taPrincipal;
    @Size(max = 100)
    @Column(name = "TraAgrupacion")
    private String traAgrupacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "TraConjunto")
    private String traConjunto;
    @Column(name = "TraConjuntoId")
    private Long traConjuntoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TalVersion")
    private short talVersion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "TalPath")
    private String talPath;
    @Size(max = 100)
    @Column(name = "TalObservacion")
    private String talObservacion;
    @Size(max = 10)
    @Column(name = "TalTipoPropiedadCodigo")
    private String talTipoPropiedadCodigo;
    @Size(max = 10)
    @Column(name = "TalTipoMedidaCodigo")
    private String talTipoMedidaCodigo;
    @Column(name = "TalSuperficie")
    private BigDecimal talSuperficie;
    @Getter
    @Setter
    @Column(name = "TalAltId")
    private Long talAltId;
    @Getter
    @Setter
    @Column(name = "TalSecuencia")
    private BigInteger talSecuencia;

    public TmpAlicuota() {
    }

    public TmpAlicuota(Long talId) {
        this.talId = talId;
    }

    public TmpAlicuota(Long talId, String talNumero, BigDecimal talAlicuota, String talUser, Date talFHR, String talEstado, short taPrincipal, String traConjunto, short talVersion, String talPath) {
        this.talId = talId;
        this.talNumero = talNumero;
        this.talAlicuota = talAlicuota;
        this.talUser = talUser;
        this.talFHR = talFHR;
        this.talEstado = talEstado;
        this.taPrincipal = taPrincipal;
        this.traConjunto = traConjunto;
        this.talVersion = talVersion;
        this.talPath = talPath;
    }

    public Long getTalId() {
        return talId;
    }

    public void setTalId(Long talId) {
        this.talId = talId;
    }

    public String getTalNumero() {
        return talNumero;
    }

    public void setTalNumero(String talNumero) {
        this.talNumero = talNumero;
    }

    public String getTalMatricula() {
        return talMatricula;
    }

    public void setTalMatricula(String talMatricula) {
        this.talMatricula = talMatricula;
    }

    public BigDecimal getTalAlicuota() {
        return talAlicuota;
    }

    public void setTalAlicuota(BigDecimal talAlicuota) {
        this.talAlicuota = talAlicuota;
    }

    public String getTalArea() {
        return talArea;
    }

    public void setTalArea(String talArea) {
        this.talArea = talArea;
    }

    public String getTalPiso() {
        return talPiso;
    }

    public void setTalPiso(String talPiso) {
        this.talPiso = talPiso;
    }

    public String getTalDescripcion() {
        return talDescripcion;
    }

    public void setTalDescripcion(String talDescripcion) {
        this.talDescripcion = talDescripcion;
    }

    public String getTalCatastro() {
        return talCatastro;
    }

    public void setTalCatastro(String talCatastro) {
        this.talCatastro = talCatastro;
    }

    public String getTalPredio() {
        return talPredio;
    }

    public void setTalPredio(String talPredio) {
        this.talPredio = talPredio;
    }

    public String getTalUser() {
        return talUser;
    }

    public void setTalUser(String talUser) {
        this.talUser = talUser;
    }

    public Date getTalFHR() {
        return talFHR;
    }

    public void setTalFHR(Date talFHR) {
        this.talFHR = talFHR;
    }

    public String getTalEstado() {
        return talEstado;
    }

    public void setTalEstado(String talEstado) {
        this.talEstado = talEstado;
    }

    public short getTaPrincipal() {
        return taPrincipal;
    }

    public void setTaPrincipal(short taPrincipal) {
        this.taPrincipal = taPrincipal;
    }

    public String getTraAgrupacion() {
        return traAgrupacion;
    }

    public void setTraAgrupacion(String traAgrupacion) {
        this.traAgrupacion = traAgrupacion;
    }

    public String getTraConjunto() {
        return traConjunto;
    }

    public void setTraConjunto(String traConjunto) {
        this.traConjunto = traConjunto;
    }

    public Long getTraConjuntoId() {
        return traConjuntoId;
    }

    public void setTraConjuntoId(Long traConjuntoId) {
        this.traConjuntoId = traConjuntoId;
    }

    public short getTalVersion() {
        return talVersion;
    }

    public void setTalVersion(short talVersion) {
        this.talVersion = talVersion;
    }

    public String getTalPath() {
        return talPath;
    }

    public void setTalPath(String talPath) {
        this.talPath = talPath;
    }

    public String getTalObservacion() {
        return talObservacion;
    }

    public void setTalObservacion(String talObservacion) {
        this.talObservacion = talObservacion;
    }

    public String getTalTipoPropiedadCodigo() {
        return talTipoPropiedadCodigo;
    }

    public void setTalTipoPropiedadCodigo(String talTipoPropiedadCodigo) {
        this.talTipoPropiedadCodigo = talTipoPropiedadCodigo;
    }

    public String getTalTipoMedidaCodigo() {
        return talTipoMedidaCodigo;
    }

    public void setTalTipoMedidaCodigo(String talTipoMedidaCodigo) {
        this.talTipoMedidaCodigo = talTipoMedidaCodigo;
    }

    public BigDecimal getTalSuperficie() {
        return talSuperficie;
    }

    public void setTalSuperficie(BigDecimal talSuperficie) {
        this.talSuperficie = talSuperficie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (talId != null ? talId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpAlicuota)) {
            return false;
        }
        TmpAlicuota other = (TmpAlicuota) object;
        if ((this.talId == null && other.talId != null) || (this.talId != null && !this.talId.equals(other.talId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.TmpAlicuota[ talId=" + talId + " ]";
    }
    
}
