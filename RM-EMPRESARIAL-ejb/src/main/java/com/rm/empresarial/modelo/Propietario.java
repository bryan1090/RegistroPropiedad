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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Propietario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietario.findAll", query = "SELECT p FROM Propietario p")
    , @NamedQuery(name = "Propietario.findByPprId", query = "SELECT p FROM Propietario p WHERE p.pprId = :pprId")
    , @NamedQuery(name = "Propietario.findByPprPerIdentificacion", query = "SELECT p FROM Propietario p WHERE p.pprPerIdentificacion = :pprPerIdentificacion")
    , @NamedQuery(name = "Propietario.findByPprPerNombre", query = "SELECT p FROM Propietario p WHERE p.pprPerNombre = :pprPerNombre")
    , @NamedQuery(name = "Propietario.findByPprPerApellidoPaterno", query = "SELECT p FROM Propietario p WHERE p.pprPerApellidoPaterno = :pprPerApellidoPaterno")
    , @NamedQuery(name = "Propietario.findByPpPerApellidoMaterno", query = "SELECT p FROM Propietario p WHERE p.ppPerApellidoMaterno = :ppPerApellidoMaterno")
    , @NamedQuery(name = "Propietario.findByPprEstado", query = "SELECT p FROM Propietario p WHERE p.pprEstado = :pprEstado")
    , @NamedQuery(name = "Propietario.findByPprFHR", query = "SELECT p FROM Propietario p WHERE p.pprFHR = :pprFHR")
    , @NamedQuery(name = "Propietario.findByPprUser", query = "SELECT p FROM Propietario p WHERE p.pprUser = :pprUser")
    , @NamedQuery(name = "Propietario.findByPrdRepertorio", query = "SELECT p FROM Propietario p WHERE p.prdRepertorio = :prdRepertorio")})
public class Propietario implements Serializable {

    public static final String LISTAR_TODO = "Propietario.findAll";
    public static final String BUSCAR_POR_ID_PERSONA = "Propietario.findByPprPerIdentificacion";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PprId")
    private Long pprId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PprPerIdentificacion")
    private String pprPerIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PprPerNombre")
    private String pprPerNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "PprPerApellidoPaterno")
    private String pprPerApellidoPaterno;
    @Size(max = 60)
    @Column(name = "PpPerApellidoMaterno")
    private String ppPerApellidoMaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PprEstado")
    private String pprEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PprFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pprFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PprUser")
    private String pprUser;
    @Column(name = "PrdRepertorio")
    private Integer prdRepertorio;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @Transient
    @Getter
    @Setter
    private int cantidadPropiedadDetalle = 0;
    @Getter
    @Setter
    @Transient
    private int existe = 0;
    @Getter
    @Setter
    @Size(max = 1)
    @Column(name = "PrdFideicomiso")
    private String prdFideicomiso;

    public Propietario() {
    }

    public Propietario(Long pprId) {
        this.pprId = pprId;
    }

    public Propietario(Long pprId, String pprPerIdentificacion, String pprPerNombre, String pprPerApellidoPaterno, String pprEstado, Date pprFHR, String pprUser) {
        this.pprId = pprId;
        this.pprPerIdentificacion = pprPerIdentificacion;
        this.pprPerNombre = pprPerNombre;
        this.pprPerApellidoPaterno = pprPerApellidoPaterno;
        this.pprEstado = pprEstado;
        this.pprFHR = pprFHR;
        this.pprUser = pprUser;
    }

    public Long getPprId() {
        return pprId;
    }

    public void setPprId(Long pprId) {
        this.pprId = pprId;
    }

    public String getPprPerIdentificacion() {
        return pprPerIdentificacion;
    }

    public void setPprPerIdentificacion(String pprPerIdentificacion) {
        this.pprPerIdentificacion = pprPerIdentificacion;
    }

    public String getPprPerNombre() {
        return pprPerNombre;
    }

    public void setPprPerNombre(String pprPerNombre) {
        this.pprPerNombre = pprPerNombre;
    }

    public String getPprPerApellidoPaterno() {
        return pprPerApellidoPaterno;
    }

    public void setPprPerApellidoPaterno(String pprPerApellidoPaterno) {
        this.pprPerApellidoPaterno = pprPerApellidoPaterno;
    }

    public String getPpPerApellidoMaterno() {
        return ppPerApellidoMaterno;
    }

    public void setPpPerApellidoMaterno(String ppPerApellidoMaterno) {
        this.ppPerApellidoMaterno = ppPerApellidoMaterno;
    }

    public String getPprEstado() {
        return pprEstado;
    }

    public void setPprEstado(String pprEstado) {
        this.pprEstado = pprEstado;
    }

    public Date getPprFHR() {
        return pprFHR;
    }

    public void setPprFHR(Date pprFHR) {
        this.pprFHR = pprFHR;
    }

    public String getPprUser() {
        return pprUser;
    }

    public void setPprUser(String pprUser) {
        this.pprUser = pprUser;
    }

    public Integer getPrdRepertorio() {
        return prdRepertorio;
    }

    public void setPrdRepertorio(Integer prdRepertorio) {
        this.prdRepertorio = prdRepertorio;
    }

    public Persona getPerId() {
        return perId;
    }

    public void setPerId(Persona perId) {
        this.perId = perId;
    }

    public Propiedad getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(Propiedad prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pprId != null ? pprId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietario)) {
            return false;
        }
        Propietario other = (Propietario) object;
        if ((this.pprId == null && other.pprId != null) || (this.pprId != null && !this.pprId.equals(other.pprId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Propietario[ pprId=" + pprId + " ]";
    }

}
