/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
import javax.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Certificado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Certificado.findAll", query = "SELECT c FROM Certificado c")
    , @NamedQuery(name = "Certificado.findByCerNumero", query = "SELECT c FROM Certificado c WHERE c.certificadoPK.cerNumero LIKE :cerNumero")
    , @NamedQuery(name = "Certificado.findByCerSecuencial", query = "SELECT c FROM Certificado c WHERE c.certificadoPK.cerSecuencial = :cerSecuencial")
    , @NamedQuery(name = "Certificado.findByCerFechaIngreso", query = "SELECT c FROM Certificado c WHERE c.cerFechaIngreso = :cerFechaIngreso")
    , @NamedQuery(name = "Certificado.findByCerPredio", query = "SELECT c FROM Certificado c WHERE c.cerPredio = :cerPredio")
    , @NamedQuery(name = "Certificado.findByCerCatastro", query = "SELECT c FROM Certificado c WHERE c.cerCatastro = :cerCatastro")
    , @NamedQuery(name = "Certificado.findByCerCuantia", query = "SELECT c FROM Certificado c WHERE c.cerCuantia = :cerCuantia")
    , @NamedQuery(name = "Certificado.findByCerAdquisicion", query = "SELECT c FROM Certificado c WHERE c.cerAdquisicion = :cerAdquisicion")
    , @NamedQuery(name = "Certificado.findByCerCertificado", query = "SELECT c FROM Certificado c WHERE c.cerCertificado = :cerCertificado")
    , @NamedQuery(name = "Certificado.findByCerPropiedad", query = "SELECT c FROM Certificado c WHERE c.cerPropiedad = :cerPropiedad")
    , @NamedQuery(name = "Certificado.findByCerPropietario", query = "SELECT c FROM Certificado c WHERE c.cerPropietario = :cerPropietario")
    , @NamedQuery(name = "Certificado.findByCerEstado", query = "SELECT c FROM Certificado c WHERE c.cerEstado = :cerEstado")
    , @NamedQuery(name = "Certificado.findByCerUsu", query = "SELECT c FROM Certificado c WHERE c.cerUsu = :cerUsu")
    , @NamedQuery(name = "Certificado.findByCerFHR", query = "SELECT c FROM Certificado c WHERE c.cerFHR = :cerFHR")
    , @NamedQuery(name = "Certificado.findByCerGravamen", query = "SELECT c FROM Certificado c WHERE c.cerGravamen = :cerGravamen")
    , @NamedQuery(name = "Certificado.findByCerObservacion", query = "SELECT c FROM Certificado c WHERE c.cerObservacion = :cerObservacion")})
public class Certificado implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_CER_NUM = "Certificado.findByCerNumero";
    public static final String BUSCAR_POR_CER_FHR = "Certificado.findByCerFHR";
    @EmbeddedId
    protected CertificadoPK certificadoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CerFechaIngreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cerFechaIngreso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CerPredio")
    private String cerPredio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CerCatastro")
    private String cerCatastro;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "CerCuantia")
    private BigDecimal cerCuantia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CerAdquisicion")
    private String cerAdquisicion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CerCertificado")
    private String cerCertificado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CerPropiedad")
    private String cerPropiedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CerPropietario")
    private String cerPropietario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CerEstado")
    private String cerEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CerUsu")
    private String cerUsu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CerFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cerFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CerGravamen")
    private String cerGravamen;
    @Size(max = 2147483647)
    @Column(name = "CerObservacion")
    private String cerObservacion;
    @Column(name = "CerFechaImpresion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cerFechaImpresion;
    @JoinTable(name = "CertificadoGravamen", joinColumns = {
        @JoinColumn(name = "CerNumero", referencedColumnName = "CerNumero")
        , @JoinColumn(name = "CerSecuencial", referencedColumnName = "CerSecuencial")}, inverseJoinColumns = {
        @JoinColumn(name = "GraId", referencedColumnName = "GraId")})
    @ManyToMany
    private List<Gravamen> gravamenList;
    @JoinTable(name = "CertificadoPropietario", joinColumns = {
        @JoinColumn(name = "CerNumero", referencedColumnName = "CerNumero")
        , @JoinColumn(name = "CerSecuencial", referencedColumnName = "CerSecuencial")}, inverseJoinColumns = {
        @JoinColumn(name = "PprId", referencedColumnName = "PprId")})
    @ManyToMany
    private List<Propietario> propietarioList;
    
    @JoinTable(name = "CertificadoPropiedad", joinColumns = {
        @JoinColumn(name = "CerNumero", referencedColumnName = "CerNumero")
        , @JoinColumn(name = "CerSecuencial", referencedColumnName = "CerSecuencial")}, inverseJoinColumns = {
        @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")})
    @ManyToMany
    private List<Propiedad> propiedadList;
    @JoinColumn(name = "FacId", referencedColumnName = "FacId")
    @ManyToOne(optional = false)
    private Factura facId;
    @JoinColumn(name = "TroId", referencedColumnName = "TroId")
    @ManyToOne(optional = false)
    private TipoCertificado troId;
    
    @Getter
    @Setter
    @Column(name = "CerPosecionEfectiva")
    private Boolean cerPosesionEfectiva;
    
    
    @Getter
    @Setter
    @Transient
    private Boolean bolActivo;
    
    @ManyToMany(mappedBy = "certificadoList")
    private List<Tramite> tramiteList;

    public Certificado() {
    }

    public Certificado(CertificadoPK certificadoPK) {
        this.certificadoPK = certificadoPK;
    }

    public Certificado(CertificadoPK certificadoPK, Date cerFechaIngreso, String cerPredio, String cerCatastro, BigDecimal cerCuantia, String cerAdquisicion, String cerCertificado, String cerPropiedad, String cerPropietario, String cerEstado, String cerUsu, Date cerFHR, String cerGravamen) {
        this.certificadoPK = certificadoPK;
        this.cerFechaIngreso = cerFechaIngreso;
        this.cerPredio = cerPredio;
        this.cerCatastro = cerCatastro;
        this.cerCuantia = cerCuantia;
        this.cerAdquisicion = cerAdquisicion;
        this.cerCertificado = cerCertificado;
        this.cerPropiedad = cerPropiedad;
        this.cerPropietario = cerPropietario;
        this.cerEstado = cerEstado;
        this.cerUsu = cerUsu;
        this.cerFHR = cerFHR;
        this.cerGravamen = cerGravamen;
    }

    public Certificado(String cerNumero, short cerSecuencial) {
        this.certificadoPK = new CertificadoPK(cerNumero, cerSecuencial);
    }

    public CertificadoPK getCertificadoPK() {
        return certificadoPK;
    }

    public void setCertificadoPK(CertificadoPK certificadoPK) {
        this.certificadoPK = certificadoPK;
    }

    public Date getCerFechaIngreso() {
        return cerFechaIngreso;
    }

    public void setCerFechaIngreso(Date cerFechaIngreso) {
        this.cerFechaIngreso = cerFechaIngreso;
    }

    public String getCerPredio() {
        return cerPredio;
    }

    public void setCerPredio(String cerPredio) {
        this.cerPredio = cerPredio;
    }

    public String getCerCatastro() {
        return cerCatastro;
    }

    public void setCerCatastro(String cerCatastro) {
        this.cerCatastro = cerCatastro;
    }

    public BigDecimal getCerCuantia() {
        return cerCuantia;
    }

    public void setCerCuantia(BigDecimal cerCuantia) {
        this.cerCuantia = cerCuantia;
    }

    public String getCerAdquisicion() {
        return cerAdquisicion;
    }

    public void setCerAdquisicion(String cerAdquisicion) {
        this.cerAdquisicion = cerAdquisicion;
    }

    public String getCerCertificado() {
        return cerCertificado;
    }

    public void setCerCertificado(String cerCertificado) {
        this.cerCertificado = cerCertificado;
    }

    public String getCerPropiedad() {
        return cerPropiedad;
    }

    public void setCerPropiedad(String cerPropiedad) {
        this.cerPropiedad = cerPropiedad;
    }

    public String getCerPropietario() {
        return cerPropietario;
    }

    public void setCerPropietario(String cerPropietario) {
        this.cerPropietario = cerPropietario;
    }

    public String getCerEstado() {
        return cerEstado;
    }

    public void setCerEstado(String cerEstado) {
        this.cerEstado = cerEstado;
    }

    public String getCerUsu() {
        return cerUsu;
    }

    public void setCerUsu(String cerUsu) {
        this.cerUsu = cerUsu;
    }

    public Date getCerFHR() {
        return cerFHR;
    }

    public void setCerFHR(Date cerFHR) {
        this.cerFHR = cerFHR;
    }

    public String getCerGravamen() {
        return cerGravamen;
    }

    public void setCerGravamen(String cerGravamen) {
        this.cerGravamen = cerGravamen;
    }

    public String getCerObservacion() {
        return cerObservacion;
    }

    public void setCerObservacion(String cerObservacion) {
        this.cerObservacion = cerObservacion;
    }

    @XmlTransient
    public List<Gravamen> getGravamenList() {
        return gravamenList;
    }

    public void setGravamenList(List<Gravamen> gravamenList) {
        this.gravamenList = gravamenList;
    }

    @XmlTransient
    public List<Propietario> getPropietarioList() {
        return propietarioList;
    }

    public void setPropietarioList(List<Propietario> propietarioList) {
        this.propietarioList = propietarioList;
    }

    @XmlTransient
    public List<Propiedad> getPropiedadList() {
        return propiedadList;
    }

    public void setPropiedadList(List<Propiedad> propiedadList) {
        this.propiedadList = propiedadList;
    }
    
    public Date getCerFechaImpresion() {
        return cerFechaImpresion;
    }

    public void setCerFechaImpresion(Date cerFechaImpresion) {
        this.cerFechaImpresion = cerFechaImpresion;
    }

    public Factura getFacId() {
        return facId;
    }

    public void setFacId(Factura facId) {
        this.facId = facId;
    }

    public TipoCertificado getTroId() {
        return troId;
    }

    public void setTroId(TipoCertificado troId) {
        this.troId = troId;
    }
    
     @XmlTransient
    public List<Tramite> getTramiteList() {
        return tramiteList;
    }

    public void setTramiteList(List<Tramite> tramiteList) {
        this.tramiteList = tramiteList;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (certificadoPK != null ? certificadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Certificado)) {
            return false;
        }
        Certificado other = (Certificado) object;
        if ((this.certificadoPK == null && other.certificadoPK != null) || (this.certificadoPK != null && !this.certificadoPK.equals(other.certificadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Certificado[ certificadoPK=" + certificadoPK + " ]";
    }

}
