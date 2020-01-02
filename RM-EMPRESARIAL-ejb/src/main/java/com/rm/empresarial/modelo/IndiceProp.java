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
import javax.persistence.Lob;
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
 * @author DJimenez
 */
@Entity
@Table(name = "indice_prop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IndiceProp.findAll", query = "SELECT i FROM IndiceProp i")
    , @NamedQuery(name = "IndiceProp.findByIndId", query = "SELECT i FROM IndiceProp i WHERE i.indId = :indId")
    , @NamedQuery(name = "IndiceProp.findByIndFecha", query = "SELECT i FROM IndiceProp i WHERE i.indFecha = :indFecha")
    , @NamedQuery(name = "IndiceProp.findByIndParroquia", query = "SELECT i FROM IndiceProp i WHERE i.indParroquia = :indParroquia")
    , @NamedQuery(name = "IndiceProp.findByIndContrato", query = "SELECT i FROM IndiceProp i WHERE i.indContrato = :indContrato")
    , @NamedQuery(name = "IndiceProp.findByIndRepertorio", query = "SELECT i FROM IndiceProp i WHERE i.indRepertorio = :indRepertorio")
    , @NamedQuery(name = "IndiceProp.findByIndLibro", query = "SELECT i FROM IndiceProp i WHERE i.indLibro = :indLibro")
    , @NamedQuery(name = "IndiceProp.findByIndInscripcion", query = "SELECT i FROM IndiceProp i WHERE i.indInscripcion = :indInscripcion")
    , @NamedQuery(name = "IndiceProp.findByIndFojas", query = "SELECT i FROM IndiceProp i WHERE i.indFojas = :indFojas")
    , @NamedQuery(name = "IndiceProp.findByIndVigente", query = "SELECT i FROM IndiceProp i WHERE i.indVigente = :indVigente")
    , @NamedQuery(name = "IndiceProp.findByIndFcancela", query = "SELECT i FROM IndiceProp i WHERE i.indFcancela = :indFcancela")
    , @NamedQuery(name = "IndiceProp.findByIndContratante", query = "SELECT i FROM IndiceProp i WHERE i.indContratante = :indContratante")
    , @NamedQuery(name = "IndiceProp.findByIndCedula", query = "SELECT i FROM IndiceProp i WHERE i.indCedula = :indCedula")
    , @NamedQuery(name = "IndiceProp.findByIndNombre", query = "SELECT i FROM IndiceProp i WHERE i.indNombre = :indNombre")
    , @NamedQuery(name = "IndiceProp.findByIndCodigo", query = "SELECT i FROM IndiceProp i WHERE i.indCodigo = :indCodigo")
    , @NamedQuery(name = "IndiceProp.findByIndFechacel", query = "SELECT i FROM IndiceProp i WHERE i.indFechacel = :indFechacel")
    , @NamedQuery(name = "IndiceProp.findByIndVentotal", query = "SELECT i FROM IndiceProp i WHERE i.indVentotal = :indVentotal")
    , @NamedQuery(name = "IndiceProp.findByIndCrva", query = "SELECT i FROM IndiceProp i WHERE i.indCrva = :indCrva")
    , @NamedQuery(name = "IndiceProp.findByIndNlinea", query = "SELECT i FROM IndiceProp i WHERE i.indNlinea = :indNlinea")
    , @NamedQuery(name = "IndiceProp.findByIndNumero", query = "SELECT i FROM IndiceProp i WHERE i.indNumero = :indNumero")
    , @NamedQuery(name = "IndiceProp.findByIndMigrado", query = "SELECT i FROM IndiceProp i WHERE i.indMigrado = :indMigrado")})
public class IndiceProp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ind_id")
    private Integer indId;
    @Column(name = "ind_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date indFecha;
    @Size(max = 255)
    @Column(name = "ind_parroquia")
    private String indParroquia;
    @Size(max = 255)
    @Column(name = "ind_contrato")
    private String indContrato;
    @Column(name = "ind_repertorio")
    private Integer indRepertorio;
    @Column(name = "ind_libro")
    private Short indLibro;
    @Column(name = "ind_inscripcion")
    private Integer indInscripcion;
    @Column(name = "ind_fojas")
    private Integer indFojas;
    @Column(name = "ind_vigente")
    private Boolean indVigente;
    @Column(name = "ind_fcancela")
    @Temporal(TemporalType.TIMESTAMP)
    private Date indFcancela;
    @Size(max = 255)
    @Column(name = "ind_contratante")
    private String indContratante;
    @Size(max = 255)
    @Column(name = "ind_cedula")
    private String indCedula;
    @Size(max = 255)
    @Column(name = "ind_nombre")
    private String indNombre;
    @Size(max = 255)
    @Column(name = "ind_codigo")
    private String indCodigo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "ind_observa")
    private String indObserva;
    @Column(name = "ind_fechacel")
    @Temporal(TemporalType.TIMESTAMP)
    private Date indFechacel;
    @Column(name = "ind_ventotal")
    private Boolean indVentotal;
    @Column(name = "ind_crva")
    private Integer indCrva;
    @Column(name = "ind_nlinea")
    private Integer indNlinea;
    @Column(name = "ind_numero")
    private Integer indNumero;
    @Size(max = 1)
    @Column(name = "ind_migrado")
    private String indMigrado;

    public IndiceProp() {
    }

    public IndiceProp(Integer indId) {
        this.indId = indId;
    }

    public Integer getIndId() {
        return indId;
    }

    public void setIndId(Integer indId) {
        this.indId = indId;
    }

    public Date getIndFecha() {
        return indFecha;
    }

    public void setIndFecha(Date indFecha) {
        this.indFecha = indFecha;
    }

    public String getIndParroquia() {
        return indParroquia;
    }

    public void setIndParroquia(String indParroquia) {
        this.indParroquia = indParroquia;
    }

    public String getIndContrato() {
        return indContrato;
    }

    public void setIndContrato(String indContrato) {
        this.indContrato = indContrato;
    }

    public Integer getIndRepertorio() {
        return indRepertorio;
    }

    public void setIndRepertorio(Integer indRepertorio) {
        this.indRepertorio = indRepertorio;
    }

    public Short getIndLibro() {
        return indLibro;
    }

    public void setIndLibro(Short indLibro) {
        this.indLibro = indLibro;
    }

    public Integer getIndInscripcion() {
        return indInscripcion;
    }

    public void setIndInscripcion(Integer indInscripcion) {
        this.indInscripcion = indInscripcion;
    }

    public Integer getIndFojas() {
        return indFojas;
    }

    public void setIndFojas(Integer indFojas) {
        this.indFojas = indFojas;
    }

    public Boolean getIndVigente() {
        return indVigente;
    }

    public void setIndVigente(Boolean indVigente) {
        this.indVigente = indVigente;
    }

    public Date getIndFcancela() {
        return indFcancela;
    }

    public void setIndFcancela(Date indFcancela) {
        this.indFcancela = indFcancela;
    }

    public String getIndContratante() {
        return indContratante;
    }

    public void setIndContratante(String indContratante) {
        this.indContratante = indContratante;
    }

    public String getIndCedula() {
        return indCedula;
    }

    public void setIndCedula(String indCedula) {
        this.indCedula = indCedula;
    }

    public String getIndNombre() {
        return indNombre;
    }

    public void setIndNombre(String indNombre) {
        this.indNombre = indNombre;
    }

    public String getIndCodigo() {
        return indCodigo;
    }

    public void setIndCodigo(String indCodigo) {
        this.indCodigo = indCodigo;
    }

    public String getIndObserva() {
        return indObserva;
    }

    public void setIndObserva(String indObserva) {
        this.indObserva = indObserva;
    }

    public Date getIndFechacel() {
        return indFechacel;
    }

    public void setIndFechacel(Date indFechacel) {
        this.indFechacel = indFechacel;
    }

    public Boolean getIndVentotal() {
        return indVentotal;
    }

    public void setIndVentotal(Boolean indVentotal) {
        this.indVentotal = indVentotal;
    }

    public Integer getIndCrva() {
        return indCrva;
    }

    public void setIndCrva(Integer indCrva) {
        this.indCrva = indCrva;
    }

    public Integer getIndNlinea() {
        return indNlinea;
    }

    public void setIndNlinea(Integer indNlinea) {
        this.indNlinea = indNlinea;
    }

    public Integer getIndNumero() {
        return indNumero;
    }

    public void setIndNumero(Integer indNumero) {
        this.indNumero = indNumero;
    }

    public String getIndMigrado() {
        return indMigrado;
    }

    public void setIndMigrado(String indMigrado) {
        this.indMigrado = indMigrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indId != null ? indId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndiceProp)) {
            return false;
        }
        IndiceProp other = (IndiceProp) object;
        if ((this.indId == null && other.indId != null) || (this.indId != null && !this.indId.equals(other.indId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.IndiceProp[ indId=" + indId + " ]";
    }
    
}
