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

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "TipoDescuento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDescuento.findAll", query = "SELECT t FROM TipoDescuento t")
    , @NamedQuery(name = "TipoDescuento.findByTpdId", query = "SELECT t FROM TipoDescuento t WHERE t.tpdId = :tpdId")
    , @NamedQuery(name = "TipoDescuento.findByTpdDescripcion", query = "SELECT t FROM TipoDescuento t WHERE t.tpdDescripcion = :tpdDescripcion")
    , @NamedQuery(name = "TipoDescuento.findByTpdPorcentaje", query = "SELECT t FROM TipoDescuento t WHERE t.tpdPorcentaje = :tpdPorcentaje")
    , @NamedQuery(name = "TipoDescuento.findByTpdIdentificacion", query = "SELECT t FROM TipoDescuento t WHERE t.tpdIdentificacion = :tpdIdentificacion")
    , @NamedQuery(name = "TipoDescuento.findByTpdIdPersona", query = "SELECT t FROM TipoDescuento t WHERE t.tpdIdPersona = :tpdIdPersona")
    , @NamedQuery(name = "TipoDescuento.findByTpdFHR", query = "SELECT t FROM TipoDescuento t WHERE t.tpdFHR = :tpdFHR")
    , @NamedQuery(name = "TipoDescuento.findByTpdUser", query = "SELECT t FROM TipoDescuento t WHERE t.tpdUser = :tpdUser")
    , @NamedQuery(name = "TipoDescuento.findByTpdEstado", query = "SELECT t FROM TipoDescuento t WHERE t.tpdEstado = :tpdEstado")
    , @NamedQuery(name = "TipoDescuento.findByTpdPublico", query = "SELECT t FROM TipoDescuento t WHERE t.tpdPublico = :tpdPublico")})
public class TipoDescuento implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "TipoDescuento.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TpdId")
    private Long tpdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "TpdDescripcion")
    private String tpdDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpdPorcentaje")
    private BigDecimal tpdPorcentaje;
    @Size(max = 20)
    @Column(name = "TpdIdentificacion")
    private String tpdIdentificacion;
    @Column(name = "TpdIdPersona")
    private Long tpdIdPersona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpdFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpdUser")
    private String tpdUser;
    @Size(max = 3)
    @Column(name = "TpdEstado")
    private String tpdEstado;
    @Size(max = 1)
    @Column(name = "TpdPublico")
    private String tpdPublico;

    public TipoDescuento() {
    }

    public TipoDescuento(Long tpdId) {
        this.tpdId = tpdId;
    }

    public TipoDescuento(Long tpdId, String tpdDescripcion, BigDecimal tpdPorcentaje, Date tpdFHR, String tpdUser) {
        this.tpdId = tpdId;
        this.tpdDescripcion = tpdDescripcion;
        this.tpdPorcentaje = tpdPorcentaje;
        this.tpdFHR = tpdFHR;
        this.tpdUser = tpdUser;
    }

    public Long getTpdId() {
        return tpdId;
    }

    public void setTpdId(Long tpdId) {
        this.tpdId = tpdId;
    }

    public String getTpdDescripcion() {
        return tpdDescripcion;
    }

    public void setTpdDescripcion(String tpdDescripcion) {
        this.tpdDescripcion = tpdDescripcion;
    }

    public BigDecimal getTpdPorcentaje() {
        return tpdPorcentaje;
    }

    public void setTpdPorcentaje(BigDecimal tpdPorcentaje) {
        this.tpdPorcentaje = tpdPorcentaje;
    }

    public String getTpdIdentificacion() {
        return tpdIdentificacion;
    }

    public void setTpdIdentificacion(String tpdIdentificacion) {
        this.tpdIdentificacion = tpdIdentificacion;
    }

    public Long getTpdIdPersona() {
        return tpdIdPersona;
    }

    public void setTpdIdPersona(Long tpdIdPersona) {
        this.tpdIdPersona = tpdIdPersona;
    }

    public Date getTpdFHR() {
        return tpdFHR;
    }

    public void setTpdFHR(Date tpdFHR) {
        this.tpdFHR = tpdFHR;
    }

    public String getTpdUser() {
        return tpdUser;
    }

    public void setTpdUser(String tpdUser) {
        this.tpdUser = tpdUser;
    }

    public String getTpdEstado() {
        return tpdEstado;
    }

    public void setTpdEstado(String tpdEstado) {
        this.tpdEstado = tpdEstado;
    }

    public String getTpdPublico() {
        return tpdPublico;
    }

    public void setTpdPublico(String tpdPublico) {
        this.tpdPublico = tpdPublico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpdId != null ? tpdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDescuento)) {
            return false;
        }
        TipoDescuento other = (TipoDescuento) object;
        if ((this.tpdId == null && other.tpdId != null) || (this.tpdId != null && !this.tpdId.equals(other.tpdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prueba.TipoDescuento[ tpdId=" + tpdId + " ]";
    }
    
}
