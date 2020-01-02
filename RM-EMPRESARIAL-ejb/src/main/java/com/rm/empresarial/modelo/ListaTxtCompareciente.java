/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigInteger;
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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Marco
 */
@Entity
@Table(name = "ListaTxtCompareciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaTxtCompareciente.findAll", query = "SELECT l FROM ListaTxtCompareciente l")
    , @NamedQuery(name = "ListaTxtCompareciente.findByPersona", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.persona = :persona")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtPerIdentificacion", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtPerIdentificacion = :tdtPerIdentificacion")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtId = :tdtId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTraNumero", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.traNumero = :traNumero")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtTtrId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtTtrId = :tdtTtrId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtTtrDescripcion", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtTtrDescripcion = :tdtTtrDescripcion")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTtcId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.ttcId = :ttcId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtTpcId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtTpcId = :tdtTpcId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtTpcDescripcion", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtTpcDescripcion = :tdtTpcDescripcion")
    , @NamedQuery(name = "ListaTxtCompareciente.findByPerId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.perId = :perId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtPerTipoContribuyente", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtPerTipoContribuyente = :tdtPerTipoContribuyente")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtPerTipoIdentificacion", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtPerTipoIdentificacion = :tdtPerTipoIdentificacion")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtConyuguePerId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtConyuguePerId = :tdtConyuguePerId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtConyuguePerTipoIden", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtConyuguePerTipoIden = :tdtConyuguePerTipoIden")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtConyuguePerNombre", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtConyuguePerNombre = :tdtConyuguePerNombre")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtTplId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtTplId = :tdtTplId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtTplDescripcion", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtTplDescripcion = :tdtTplDescripcion")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtEstado", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtEstado = :tdtEstado")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtUser", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtUser = :tdtUser")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtFHR", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtFHR = :tdtFHR")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtNumeroRepertorio", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtNumeroRepertorio = :tdtNumeroRepertorio")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtFechaRegistro", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtFechaRegistro = :tdtFechaRegistro")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtTpcCodigo", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtTpcCodigo = :tdtTpcCodigo")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtParNombre", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtParNombre = :tdtParNombre")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtParId", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtParId = :tdtParId")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtCatastro", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtCatastro = :tdtCatastro")
    , @NamedQuery(name = "ListaTxtCompareciente.findByTdtPredio", query = "SELECT l FROM ListaTxtCompareciente l WHERE l.tdtPredio = :tdtPredio")})
public class ListaTxtCompareciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 222)
    @Column(name = "Persona")
    private String persona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdtPerIdentificacion")
    private String tdtPerIdentificacion;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtId")
    private BigInteger tdtId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraNumero")
    private int traNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtTtrId")
    private BigInteger tdtTtrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TdtTtrDescripcion")
    private String tdtTtrDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TtcId")
    private BigInteger ttcId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtTpcId")
    private BigInteger tdtTpcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TdtTpcDescripcion")
    private String tdtTpcDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PerId")
    private BigInteger perId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TdtPerTipoContribuyente")
    private String tdtPerTipoContribuyente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TdtPerTipoIdentificacion")
    private String tdtPerTipoIdentificacion;
    @Column(name = "TdtConyuguePerId")
    private BigInteger tdtConyuguePerId;
    @Size(max = 10)
    @Column(name = "TdtConyuguePerTipoIden")
    private String tdtConyuguePerTipoIden;
    @Size(max = 300)
    @Column(name = "TdtConyuguePerNombre")
    private String tdtConyuguePerNombre;
    @Column(name = "TdtTplId")
    private BigInteger tdtTplId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TdtTplDescripcion")
    private String tdtTplDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TdtEstado")
    private String tdtEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdtUser")
    private String tdtUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tdtFHR;
    @Column(name = "TdtNumeroRepertorio")
    private Integer tdtNumeroRepertorio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtFechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tdtFechaRegistro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TdtTpcCodigo")
    private String tdtTpcCodigo;
    @Size(max = 40)
    @Column(name = "TdtParNombre")
    private String tdtParNombre;
    @Column(name = "TdtParId")
    private BigInteger tdtParId;
    @Size(max = 100)
    @Column(name = "TdtCatastro")
    private String tdtCatastro;
    @Size(max = 100)
    @Column(name = "TdtPredio")
    private String tdtPredio;
    @Getter
    @Setter
    @Basic(optional = false)    
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PerTipoIdentificacion")
    private String perTipoIdentificacion;
    @Getter
    @Setter
    @Size(max = 3)
    @Column(name = "PerEstadoCivil")
    private String perEstadoCivil;

    public ListaTxtCompareciente() {
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getTdtPerIdentificacion() {
        return tdtPerIdentificacion;
    }

    public void setTdtPerIdentificacion(String tdtPerIdentificacion) {
        this.tdtPerIdentificacion = tdtPerIdentificacion;
    }

    public BigInteger getTdtId() {
        return tdtId;
    }

    public void setTdtId(BigInteger tdtId) {
        this.tdtId = tdtId;
    }

    public int getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(int traNumero) {
        this.traNumero = traNumero;
    }

    public BigInteger getTdtTtrId() {
        return tdtTtrId;
    }

    public void setTdtTtrId(BigInteger tdtTtrId) {
        this.tdtTtrId = tdtTtrId;
    }

    public String getTdtTtrDescripcion() {
        return tdtTtrDescripcion;
    }

    public void setTdtTtrDescripcion(String tdtTtrDescripcion) {
        this.tdtTtrDescripcion = tdtTtrDescripcion;
    }

    public BigInteger getTtcId() {
        return ttcId;
    }

    public void setTtcId(BigInteger ttcId) {
        this.ttcId = ttcId;
    }

    public BigInteger getTdtTpcId() {
        return tdtTpcId;
    }

    public void setTdtTpcId(BigInteger tdtTpcId) {
        this.tdtTpcId = tdtTpcId;
    }

    public String getTdtTpcDescripcion() {
        return tdtTpcDescripcion;
    }

    public void setTdtTpcDescripcion(String tdtTpcDescripcion) {
        this.tdtTpcDescripcion = tdtTpcDescripcion;
    }

    public BigInteger getPerId() {
        return perId;
    }

    public void setPerId(BigInteger perId) {
        this.perId = perId;
    }

    public String getTdtPerTipoContribuyente() {
        return tdtPerTipoContribuyente;
    }

    public void setTdtPerTipoContribuyente(String tdtPerTipoContribuyente) {
        this.tdtPerTipoContribuyente = tdtPerTipoContribuyente;
    }

    public String getTdtPerTipoIdentificacion() {
        return tdtPerTipoIdentificacion;
    }

    public void setTdtPerTipoIdentificacion(String tdtPerTipoIdentificacion) {
        this.tdtPerTipoIdentificacion = tdtPerTipoIdentificacion;
    }

    public BigInteger getTdtConyuguePerId() {
        return tdtConyuguePerId;
    }

    public void setTdtConyuguePerId(BigInteger tdtConyuguePerId) {
        this.tdtConyuguePerId = tdtConyuguePerId;
    }

    public String getTdtConyuguePerTipoIden() {
        return tdtConyuguePerTipoIden;
    }

    public void setTdtConyuguePerTipoIden(String tdtConyuguePerTipoIden) {
        this.tdtConyuguePerTipoIden = tdtConyuguePerTipoIden;
    }

    public String getTdtConyuguePerNombre() {
        return tdtConyuguePerNombre;
    }

    public void setTdtConyuguePerNombre(String tdtConyuguePerNombre) {
        this.tdtConyuguePerNombre = tdtConyuguePerNombre;
    }

    public BigInteger getTdtTplId() {
        return tdtTplId;
    }

    public void setTdtTplId(BigInteger tdtTplId) {
        this.tdtTplId = tdtTplId;
    }

    public String getTdtTplDescripcion() {
        return tdtTplDescripcion;
    }

    public void setTdtTplDescripcion(String tdtTplDescripcion) {
        this.tdtTplDescripcion = tdtTplDescripcion;
    }

    public String getTdtEstado() {
        return tdtEstado;
    }

    public void setTdtEstado(String tdtEstado) {
        this.tdtEstado = tdtEstado;
    }

    public String getTdtUser() {
        return tdtUser;
    }

    public void setTdtUser(String tdtUser) {
        this.tdtUser = tdtUser;
    }

    public Date getTdtFHR() {
        return tdtFHR;
    }

    public void setTdtFHR(Date tdtFHR) {
        this.tdtFHR = tdtFHR;
    }

    public Integer getTdtNumeroRepertorio() {
        return tdtNumeroRepertorio;
    }

    public void setTdtNumeroRepertorio(Integer tdtNumeroRepertorio) {
        this.tdtNumeroRepertorio = tdtNumeroRepertorio;
    }

    public Date getTdtFechaRegistro() {
        return tdtFechaRegistro;
    }

    public void setTdtFechaRegistro(Date tdtFechaRegistro) {
        this.tdtFechaRegistro = tdtFechaRegistro;
    }

    public String getTdtTpcCodigo() {
        return tdtTpcCodigo;
    }

    public void setTdtTpcCodigo(String tdtTpcCodigo) {
        this.tdtTpcCodigo = tdtTpcCodigo;
    }

    public String getTdtParNombre() {
        return tdtParNombre;
    }

    public void setTdtParNombre(String tdtParNombre) {
        this.tdtParNombre = tdtParNombre;
    }

    public BigInteger getTdtParId() {
        return tdtParId;
    }

    public void setTdtParId(BigInteger tdtParId) {
        this.tdtParId = tdtParId;
    }

    public String getTdtCatastro() {
        return tdtCatastro;
    }

    public void setTdtCatastro(String tdtCatastro) {
        this.tdtCatastro = tdtCatastro;
    }

    public String getTdtPredio() {
        return tdtPredio;
    }

    public void setTdtPredio(String tdtPredio) {
        this.tdtPredio = tdtPredio;
    }
    
}
