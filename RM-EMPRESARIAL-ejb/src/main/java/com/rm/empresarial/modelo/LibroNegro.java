/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "LibroNegro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LibroNegro.findAll", query = "SELECT l FROM LibroNegro l")
    , @NamedQuery(name = "LibroNegro.findByLbnId", query = "SELECT l FROM LibroNegro l WHERE l.lbnId = :lbnId")
    , @NamedQuery(name = "LibroNegro.findByLbnPerIdentificacion", query = "SELECT l FROM LibroNegro l WHERE l.lbnPerIdentificacion = :lbnPerIdentificacion")
    , @NamedQuery(name = "LibroNegro.findByLbnPerNombre", query = "SELECT l FROM LibroNegro l WHERE l.lbnPerNombre = :lbnPerNombre")
    , @NamedQuery(name = "LibroNegro.findByLbnPerApellidoPaterno", query = "SELECT l FROM LibroNegro l WHERE l.lbnPerApellidoPaterno = :lbnPerApellidoPaterno")
    , @NamedQuery(name = "LibroNegro.findByLbnPerApellidoMaterno", query = "SELECT l FROM LibroNegro l WHERE l.lbnPerApellidoMaterno = :lbnPerApellidoMaterno")
    , @NamedQuery(name = "LibroNegro.findByLbnEstado", query = "SELECT l FROM LibroNegro l WHERE l.lbnEstado = :lbnEstado")
    , @NamedQuery(name = "LibroNegro.findByLbnUser", query = "SELECT l FROM LibroNegro l WHERE l.lbnUser = :lbnUser")
    , @NamedQuery(name = "LibroNegro.findByLbnFHR", query = "SELECT l FROM LibroNegro l WHERE l.lbnFHR = :lbnFHR")
    , @NamedQuery(name = "LibroNegro.findByLbnFechaActualizacion", query = "SELECT l FROM LibroNegro l WHERE l.lbnFechaActualizacion = :lbnFechaActualizacion")
    , @NamedQuery(name = "LibroNegro.findByLbnUserActualizador", query = "SELECT l FROM LibroNegro l WHERE l.lbnUserActualizador = :lbnUserActualizador")
    , @NamedQuery(name = "LibroNegro.findByLbnObservacion", query = "SELECT l FROM LibroNegro l WHERE l.lbnObservacion = :lbnObservacion")
    , @NamedQuery(name = "LibroNegro.findByLbnPredio", query = "SELECT l FROM LibroNegro l WHERE l.lbnPredio = :lbnPredio")
    , @NamedQuery(name = "LibroNegro.findByLbnCatastro", query = "SELECT l FROM LibroNegro l WHERE l.lbnCatastro = :lbnCatastro")})
public class LibroNegro implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_IDENTIFICACION = "LibroNegro.findByLbnPerIdentificacion";

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "LbnId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private BigDecimal lbnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LbnPerIdentificacion")
    private String lbnPerIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LbnPerNombre")
    private String lbnPerNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "LbnPerApellidoPaterno")
    private String lbnPerApellidoPaterno;
    @Size(max = 60)
    @Column(name = "LbnPerApellidoMaterno")
    private String lbnPerApellidoMaterno;
    @Size(max = 3)
    @Column(name = "LbnEstado")
    private String lbnEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LbnUser")
    private String lbnUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LbnFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lbnFHR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LbnFechaActualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lbnFechaActualizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LbnUserActualizador")
    private String lbnUserActualizador;
    @Size(max = 150)
    @Column(name = "LbnObservacion")
    private String lbnObservacion;
    @Size(max = 40)
    @Column(name = "LbnPredio")
    private String lbnPredio;
    @Size(max = 40)
    @Column(name = "LbnCatastro")
    private String lbnCatastro;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;

    public LibroNegro() {
    }

    public LibroNegro(BigDecimal lbnId) {
        this.lbnId = lbnId;
    }

    public LibroNegro(BigDecimal lbnId, String lbnPerIdentificacion, String lbnPerNombre, String lbnPerApellidoPaterno, String lbnUser, Calendar lbnFHR, Calendar lbnFechaActualizacion, String lbnUserActualizador) {
        this.lbnId = lbnId;
        this.lbnPerIdentificacion = lbnPerIdentificacion;
        this.lbnPerNombre = lbnPerNombre;
        this.lbnPerApellidoPaterno = lbnPerApellidoPaterno;
        this.lbnUser = lbnUser;
        this.lbnFHR = lbnFHR;
        this.lbnFechaActualizacion = lbnFechaActualizacion;
        this.lbnUserActualizador = lbnUserActualizador;
    }

    public BigDecimal getLbnId() {
        return lbnId;
    }

    public void setLbnId(BigDecimal lbnId) {
        this.lbnId = lbnId;
    }

    public String getLbnPerIdentificacion() {
        return lbnPerIdentificacion;
    }

    public void setLbnPerIdentificacion(String lbnPerIdentificacion) {
        this.lbnPerIdentificacion = lbnPerIdentificacion;
    }

    public String getLbnPerNombre() {
        return lbnPerNombre;
    }

    public void setLbnPerNombre(String lbnPerNombre) {
        this.lbnPerNombre = lbnPerNombre;
    }

    public String getLbnPerApellidoPaterno() {
        return lbnPerApellidoPaterno;
    }

    public void setLbnPerApellidoPaterno(String lbnPerApellidoPaterno) {
        this.lbnPerApellidoPaterno = lbnPerApellidoPaterno;
    }

    public String getLbnPerApellidoMaterno() {
        return lbnPerApellidoMaterno;
    }

    public void setLbnPerApellidoMaterno(String lbnPerApellidoMaterno) {
        this.lbnPerApellidoMaterno = lbnPerApellidoMaterno;
    }

    public String getLbnEstado() {
        return lbnEstado;
    }

    public void setLbnEstado(String lbnEstado) {
        this.lbnEstado = lbnEstado;
    }

    public String getLbnUser() {
        return lbnUser;
    }

    public void setLbnUser(String lbnUser) {
        this.lbnUser = lbnUser;
    }

    public Calendar getLbnFHR() {
        return lbnFHR;
    }

    public void setLbnFHR(Calendar lbnFHR) {
        this.lbnFHR = lbnFHR;
    }

    public Calendar getLbnFechaActualizacion() {
        return lbnFechaActualizacion;
    }

    public void setLbnFechaActualizacion(Calendar lbnFechaActualizacion) {
        this.lbnFechaActualizacion = lbnFechaActualizacion;
    }

    public String getLbnUserActualizador() {
        return lbnUserActualizador;
    }

    public void setLbnUserActualizador(String lbnUserActualizador) {
        this.lbnUserActualizador = lbnUserActualizador;
    }

    public String getLbnObservacion() {
        return lbnObservacion;
    }

    public void setLbnObservacion(String lbnObservacion) {
        this.lbnObservacion = lbnObservacion;
    }

    public String getLbnPredio() {
        return lbnPredio;
    }

    public void setLbnPredio(String lbnPredio) {
        this.lbnPredio = lbnPredio;
    }

    public String getLbnCatastro() {
        return lbnCatastro;
    }

    public void setLbnCatastro(String lbnCatastro) {
        this.lbnCatastro = lbnCatastro;
    }

    public Persona getPerId() {
        return perId;
    }

    public void setPerId(Persona perId) {
        this.perId = perId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lbnId != null ? lbnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LibroNegro)) {
            return false;
        }
        LibroNegro other = (LibroNegro) object;
        if ((this.lbnId == null && other.lbnId != null) || (this.lbnId != null && !this.lbnId.equals(other.lbnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.LibroNegro[ lbnId=" + lbnId + " ]";
    }

}
