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
 * @author USUARIO
 */
@Entity
@Table(name = "RepositorioRC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepositorioRC.findAll", query = "SELECT r FROM RepositorioRC r")
    , @NamedQuery(name = "RepositorioRC.findByRrcId", query = "SELECT r FROM RepositorioRC r WHERE r.rrcId = :rrcId")
    , @NamedQuery(name = "RepositorioRC.findByRrcTipoContribuyente", query = "SELECT r FROM RepositorioRC r WHERE r.rrcTipoContribuyente = :rrcTipoContribuyente")
    , @NamedQuery(name = "RepositorioRC.findByRrcTipoIdentificacion", query = "SELECT r FROM RepositorioRC r WHERE r.rrcTipoIdentificacion = :rrcTipoIdentificacion")
    , @NamedQuery(name = "RepositorioRC.buscarPorIdentificacion", query = "SELECT r FROM RepositorioRC r WHERE r.rrcIdentificacion = :rrcIdentificacion")
    , @NamedQuery(name = "RepositorioRC.findByRrcNombre", query = "SELECT r FROM RepositorioRC r WHERE r.rrcNombre = :rrcNombre")
    , @NamedQuery(name = "RepositorioRC.findByRrcApellidoPaterno", query = "SELECT r FROM RepositorioRC r WHERE r.rrcApellidoPaterno = :rrcApellidoPaterno")
    , @NamedQuery(name = "RepositorioRC.findByRrcApellidoMaterno", query = "SELECT r FROM RepositorioRC r WHERE r.rrcApellidoMaterno = :rrcApellidoMaterno")
    , @NamedQuery(name = "RepositorioRC.findByRrcFechaNacimiento", query = "SELECT r FROM RepositorioRC r WHERE r.rrcFechaNacimiento = :rrcFechaNacimiento")
    , @NamedQuery(name = "RepositorioRC.findByRrcFechaDefuncion", query = "SELECT r FROM RepositorioRC r WHERE r.rrcFechaDefuncion = :rrcFechaDefuncion")
    , @NamedQuery(name = "RepositorioRC.findByRrcTelefono", query = "SELECT r FROM RepositorioRC r WHERE r.rrcTelefono = :rrcTelefono")
    , @NamedQuery(name = "RepositorioRC.findByRrcCelular", query = "SELECT r FROM RepositorioRC r WHERE r.rrcCelular = :rrcCelular")
    , @NamedQuery(name = "RepositorioRC.findByRrcEmail", query = "SELECT r FROM RepositorioRC r WHERE r.rrcEmail = :rrcEmail")
    , @NamedQuery(name = "RepositorioRC.findByRrcDireccion", query = "SELECT r FROM RepositorioRC r WHERE r.rrcDireccion = :rrcDireccion")
    , @NamedQuery(name = "RepositorioRC.findByRrcEstadoCivil", query = "SELECT r FROM RepositorioRC r WHERE r.rrcEstadoCivil = :rrcEstadoCivil")
    , @NamedQuery(name = "RepositorioRC.findByRrcCalidad", query = "SELECT r FROM RepositorioRC r WHERE r.rrcCalidad = :rrcCalidad")
    , @NamedQuery(name = "RepositorioRC.findByRrcSexo", query = "SELECT r FROM RepositorioRC r WHERE r.rrcSexo = :rrcSexo")})
public class RepositorioRC implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_IDENTIFICACION = "RepositorioRC.buscarPorIdentificacion";
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RrcId")
    private BigDecimal rrcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "RrcTipoContribuyente")
    private String rrcTipoContribuyente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "RrcTipoIdentificacion")
    private String rrcTipoIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RrcIdentificacion")
    private String rrcIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RrcNombre")
    private String rrcNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "RrcApellidoPaterno")
    private String rrcApellidoPaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "RrcApellidoMaterno")
    private String rrcApellidoMaterno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RrcFechaNacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rrcFechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RrcFechaDefuncion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rrcFechaDefuncion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RrcTelefono")
    private String rrcTelefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RrcCelular")
    private String rrcCelular;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RrcEmail")
    private String rrcEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "RrcDireccion")
    private String rrcDireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "RrcEstadoCivil")
    private String rrcEstadoCivil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RrcCalidad")
    private String rrcCalidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RrcSexo")
    private String rrcSexo;

    public RepositorioRC() {
    }

    public RepositorioRC(BigDecimal rrcId) {
        this.rrcId = rrcId;
    }

    public RepositorioRC(BigDecimal rrcId, String rrcTipoContribuyente, String rrcTipoIdentificacion, String rrcIdentificacion, String rrcNombre, String rrcApellidoPaterno, String rrcApellidoMaterno, Date rrcFechaNacimiento, Date rrcFechaDefuncion, String rrcTelefono, String rrcCelular, String rrcEmail, String rrcDireccion, String rrcEstadoCivil, String rrcCalidad, String rrcSexo) {
        this.rrcId = rrcId;
        this.rrcTipoContribuyente = rrcTipoContribuyente;
        this.rrcTipoIdentificacion = rrcTipoIdentificacion;
        this.rrcIdentificacion = rrcIdentificacion;
        this.rrcNombre = rrcNombre;
        this.rrcApellidoPaterno = rrcApellidoPaterno;
        this.rrcApellidoMaterno = rrcApellidoMaterno;
        this.rrcFechaNacimiento = rrcFechaNacimiento;
        this.rrcFechaDefuncion = rrcFechaDefuncion;
        this.rrcTelefono = rrcTelefono;
        this.rrcCelular = rrcCelular;
        this.rrcEmail = rrcEmail;
        this.rrcDireccion = rrcDireccion;
        this.rrcEstadoCivil = rrcEstadoCivil;
        this.rrcCalidad = rrcCalidad;
        this.rrcSexo = rrcSexo;
    }

    public BigDecimal getRrcId() {
        return rrcId;
    }

    public void setRrcId(BigDecimal rrcId) {
        this.rrcId = rrcId;
    }

    public String getRrcTipoContribuyente() {
        return rrcTipoContribuyente;
    }

    public void setRrcTipoContribuyente(String rrcTipoContribuyente) {
        this.rrcTipoContribuyente = rrcTipoContribuyente;
    }

    public String getRrcTipoIdentificacion() {
        return rrcTipoIdentificacion;
    }

    public void setRrcTipoIdentificacion(String rrcTipoIdentificacion) {
        this.rrcTipoIdentificacion = rrcTipoIdentificacion;
    }

    public String getRrcIdentificacion() {
        return rrcIdentificacion;
    }

    public void setRrcIdentificacion(String rrcIdentificacion) {
        this.rrcIdentificacion = rrcIdentificacion;
    }

    public String getRrcNombre() {
        return rrcNombre;
    }

    public void setRrcNombre(String rrcNombre) {
        this.rrcNombre = rrcNombre;
    }

    public String getRrcApellidoPaterno() {
        return rrcApellidoPaterno;
    }

    public void setRrcApellidoPaterno(String rrcApellidoPaterno) {
        this.rrcApellidoPaterno = rrcApellidoPaterno;
    }

    public String getRrcApellidoMaterno() {
        return rrcApellidoMaterno;
    }

    public void setRrcApellidoMaterno(String rrcApellidoMaterno) {
        this.rrcApellidoMaterno = rrcApellidoMaterno;
    }

    public Date getRrcFechaNacimiento() {
        return rrcFechaNacimiento;
    }

    public void setRrcFechaNacimiento(Date rrcFechaNacimiento) {
        this.rrcFechaNacimiento = rrcFechaNacimiento;
    }

    public Date getRrcFechaDefuncion() {
        return rrcFechaDefuncion;
    }

    public void setRrcFechaDefuncion(Date rrcFechaDefuncion) {
        this.rrcFechaDefuncion = rrcFechaDefuncion;
    }

    public String getRrcTelefono() {
        return rrcTelefono;
    }

    public void setRrcTelefono(String rrcTelefono) {
        this.rrcTelefono = rrcTelefono;
    }

    public String getRrcCelular() {
        return rrcCelular;
    }

    public void setRrcCelular(String rrcCelular) {
        this.rrcCelular = rrcCelular;
    }

    public String getRrcEmail() {
        return rrcEmail;
    }

    public void setRrcEmail(String rrcEmail) {
        this.rrcEmail = rrcEmail;
    }

    public String getRrcDireccion() {
        return rrcDireccion;
    }

    public void setRrcDireccion(String rrcDireccion) {
        this.rrcDireccion = rrcDireccion;
    }

    public String getRrcEstadoCivil() {
        return rrcEstadoCivil;
    }

    public void setRrcEstadoCivil(String rrcEstadoCivil) {
        this.rrcEstadoCivil = rrcEstadoCivil;
    }

    public String getRrcCalidad() {
        return rrcCalidad;
    }

    public void setRrcCalidad(String rrcCalidad) {
        this.rrcCalidad = rrcCalidad;
    }

    public String getRrcSexo() {
        return rrcSexo;
    }

    public void setRrcSexo(String rrcSexo) {
        this.rrcSexo = rrcSexo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rrcId != null ? rrcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepositorioRC)) {
            return false;
        }
        RepositorioRC other = (RepositorioRC) object;
        if ((this.rrcId == null && other.rrcId != null) || (this.rrcId != null && !this.rrcId.equals(other.rrcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.RepositorioRC[ rrcId=" + rrcId + " ]";
    }
    
}
