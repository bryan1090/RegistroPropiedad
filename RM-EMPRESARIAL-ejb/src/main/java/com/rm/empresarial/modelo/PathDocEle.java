/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JeanCarlos
 */
@Entity
@Table(name = "PathDocEle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PathDocEle.findAll", query = "SELECT p FROM PathDocEle p")
    , @NamedQuery(name = "PathDocEle.findByPdeId", query = "SELECT p FROM PathDocEle p WHERE p.pdeId = :pdeId")
    , @NamedQuery(name = "PathDocEle.findByPdeRepositorio", query = "SELECT p FROM PathDocEle p WHERE p.pdeRepositorio = :pdeRepositorio")
    , @NamedQuery(name = "PathDocEle.findByPdeRepTemporal", query = "SELECT p FROM PathDocEle p WHERE p.pdeRepTemporal = :pdeRepTemporal")
    , @NamedQuery(name = "PathDocEle.findByPdeLogo", query = "SELECT p FROM PathDocEle p WHERE p.pdeLogo = :pdeLogo")
    , @NamedQuery(name = "PathDocEle.findByPdeReportesJrxml", query = "SELECT p FROM PathDocEle p WHERE p.pdeReportesJrxml = :pdeReportesJrxml")
    , @NamedQuery(name = "PathDocEle.findByPdeFirma", query = "SELECT p FROM PathDocEle p WHERE p.pdeFirma = :pdeFirma")
    , @NamedQuery(name = "PathDocEle.findByPdeCertificado", query = "SELECT p FROM PathDocEle p WHERE p.pdeCertificado = :pdeCertificado")
    , @NamedQuery(name = "PathDocEle.findByPdeCerAutorizado", query = "SELECT p FROM PathDocEle p WHERE p.pdeCerAutorizado = :pdeCerAutorizado")
    , @NamedQuery(name = "PathDocEle.findByPdeCerNoAutorizado", query = "SELECT p FROM PathDocEle p WHERE p.pdeCerNoAutorizado = :pdeCerNoAutorizado")
    , @NamedQuery(name = "PathDocEle.findByPdeCerContingencia", query = "SELECT p FROM PathDocEle p WHERE p.pdeCerContingencia = :pdeCerContingencia")
    , @NamedQuery(name = "PathDocEle.findByPdeCerDevuelto", query = "SELECT p FROM PathDocEle p WHERE p.pdeCerDevuelto = :pdeCerDevuelto")
    , @NamedQuery(name = "PathDocEle.findByPdeMailHabilitarAutenticacion", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailHabilitarAutenticacion = :pdeMailHabilitarAutenticacion")
    , @NamedQuery(name = "PathDocEle.findByPdeMailHabilitarSSL", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailHabilitarSSL = :pdeMailHabilitarSSL")
    , @NamedQuery(name = "PathDocEle.findByPdeMailHabilitarDEBUG", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailHabilitarDEBUG = :pdeMailHabilitarDEBUG")
    , @NamedQuery(name = "PathDocEle.findByPdeMailHost", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailHost = :pdeMailHost")
    , @NamedQuery(name = "PathDocEle.findByPdeMailPuerto", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailPuerto = :pdeMailPuerto")
    , @NamedQuery(name = "PathDocEle.findByPdeMailServidorDNS", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailServidorDNS = :pdeMailServidorDNS")
    , @NamedQuery(name = "PathDocEle.findByPdeMailProtocolo", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailProtocolo = :pdeMailProtocolo")
    , @NamedQuery(name = "PathDocEle.findByPdeMailUser", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailUser = :pdeMailUser")
    , @NamedQuery(name = "PathDocEle.findByPdeMailPassword", query = "SELECT p FROM PathDocEle p WHERE p.pdeMailPassword = :pdeMailPassword")
    , @NamedQuery(name = "PathDocEle.findByPdeWsRecepcion", query = "SELECT p FROM PathDocEle p WHERE p.pdeWsRecepcion = :pdeWsRecepcion")
    , @NamedQuery(name = "PathDocEle.findByPdeWsAutorizacion", query = "SELECT p FROM PathDocEle p WHERE p.pdeWsAutorizacion = :pdeWsAutorizacion")
    , @NamedQuery(name = "PathDocEle.findByPdeMetodoRecepcion", query = "SELECT p FROM PathDocEle p WHERE p.pdeMetodoRecepcion = :pdeMetodoRecepcion")
    , @NamedQuery(name = "PathDocEle.findByPdeNameSpaceURIRecepcion", query = "SELECT p FROM PathDocEle p WHERE p.pdeNameSpaceURIRecepcion = :pdeNameSpaceURIRecepcion")
    , @NamedQuery(name = "PathDocEle.findByPdeMetodoAutorizacion", query = "SELECT p FROM PathDocEle p WHERE p.pdeMetodoAutorizacion = :pdeMetodoAutorizacion")
    , @NamedQuery(name = "PathDocEle.findByPdeNameSpaceURIAutorizacion", query = "SELECT p FROM PathDocEle p WHERE p.pdeNameSpaceURIAutorizacion = :pdeNameSpaceURIAutorizacion")
    , @NamedQuery(name = "PathDocEle.findByPdeClaveArchivo", query = "SELECT p FROM PathDocEle p WHERE p.pdeClaveArchivo = :pdeClaveArchivo")})
public class PathDocEle implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "PathDocEle.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PdeId")
    private Long pdeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeRepositorio")
    private String pdeRepositorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeRepTemporal")
    private String pdeRepTemporal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeLogo")
    private String pdeLogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeReportesJrxml")
    private String pdeReportesJrxml;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeFirma")
    private String pdeFirma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeCertificado")
    private String pdeCertificado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeCerAutorizado")
    private String pdeCerAutorizado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeCerNoAutorizado")
    private String pdeCerNoAutorizado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeCerContingencia")
    private String pdeCerContingencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeCerDevuelto")
    private String pdeCerDevuelto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PdeMailHabilitarAutenticacion")
    private boolean pdeMailHabilitarAutenticacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PdeMailHabilitarSSL")
    private boolean pdeMailHabilitarSSL;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PdeMailHabilitarDEBUG")
    private boolean pdeMailHabilitarDEBUG;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeMailHost")
    private String pdeMailHost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PdeMailPuerto")
    private int pdeMailPuerto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeMailServidorDNS")
    private String pdeMailServidorDNS;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PdeMailProtocolo")
    private String pdeMailProtocolo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PdeMailUser")
    private String pdeMailUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PdeMailPassword")
    private String pdeMailPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PdeWsRecepcion")
    private String pdeWsRecepcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeWsAutorizacion")
    private String pdeWsAutorizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PdeMetodoRecepcion")
    private String pdeMetodoRecepcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeNameSpaceURIRecepcion")
    private String pdeNameSpaceURIRecepcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PdeMetodoAutorizacion")
    private String pdeMetodoAutorizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PdeNameSpaceURIAutorizacion")
    private String pdeNameSpaceURIAutorizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PdeClaveArchivo")
    private String pdeClaveArchivo;

    public PathDocEle() {
    }

    public PathDocEle(Long pdeId) {
        this.pdeId = pdeId;
    }

    public PathDocEle(Long pdeId, String pdeRepositorio, String pdeRepTemporal, String pdeLogo, String pdeReportesJrxml, String pdeFirma, String pdeCertificado, String pdeCerAutorizado, String pdeCerNoAutorizado, String pdeCerContingencia, String pdeCerDevuelto, boolean pdeMailHabilitarAutenticacion, boolean pdeMailHabilitarSSL, boolean pdeMailHabilitarDEBUG, String pdeMailHost, int pdeMailPuerto, String pdeMailServidorDNS, String pdeMailProtocolo, String pdeMailUser, String pdeMailPassword, String pdeWsRecepcion, String pdeWsAutorizacion, String pdeMetodoRecepcion, String pdeNameSpaceURIRecepcion, String pdeMetodoAutorizacion, String pdeNameSpaceURIAutorizacion, String pdeClaveArchivo) {
        this.pdeId = pdeId;
        this.pdeRepositorio = pdeRepositorio;
        this.pdeRepTemporal = pdeRepTemporal;
        this.pdeLogo = pdeLogo;
        this.pdeReportesJrxml = pdeReportesJrxml;
        this.pdeFirma = pdeFirma;
        this.pdeCertificado = pdeCertificado;
        this.pdeCerAutorizado = pdeCerAutorizado;
        this.pdeCerNoAutorizado = pdeCerNoAutorizado;
        this.pdeCerContingencia = pdeCerContingencia;
        this.pdeCerDevuelto = pdeCerDevuelto;
        this.pdeMailHabilitarAutenticacion = pdeMailHabilitarAutenticacion;
        this.pdeMailHabilitarSSL = pdeMailHabilitarSSL;
        this.pdeMailHabilitarDEBUG = pdeMailHabilitarDEBUG;
        this.pdeMailHost = pdeMailHost;
        this.pdeMailPuerto = pdeMailPuerto;
        this.pdeMailServidorDNS = pdeMailServidorDNS;
        this.pdeMailProtocolo = pdeMailProtocolo;
        this.pdeMailUser = pdeMailUser;
        this.pdeMailPassword = pdeMailPassword;
        this.pdeWsRecepcion = pdeWsRecepcion;
        this.pdeWsAutorizacion = pdeWsAutorizacion;
        this.pdeMetodoRecepcion = pdeMetodoRecepcion;
        this.pdeNameSpaceURIRecepcion = pdeNameSpaceURIRecepcion;
        this.pdeMetodoAutorizacion = pdeMetodoAutorizacion;
        this.pdeNameSpaceURIAutorizacion = pdeNameSpaceURIAutorizacion;
        this.pdeClaveArchivo = pdeClaveArchivo;
    }

    public Long getPdeId() {
        return pdeId;
    }

    public void setPdeId(Long pdeId) {
        this.pdeId = pdeId;
    }

    public String getPdeRepositorio() {
        return pdeRepositorio;
    }

    public void setPdeRepositorio(String pdeRepositorio) {
        this.pdeRepositorio = pdeRepositorio;
    }

    public String getPdeRepTemporal() {
        return pdeRepTemporal;
    }

    public void setPdeRepTemporal(String pdeRepTemporal) {
        this.pdeRepTemporal = pdeRepTemporal;
    }

    public String getPdeLogo() {
        return pdeLogo;
    }

    public void setPdeLogo(String pdeLogo) {
        this.pdeLogo = pdeLogo;
    }

    public String getPdeReportesJrxml() {
        return pdeReportesJrxml;
    }

    public void setPdeReportesJrxml(String pdeReportesJrxml) {
        this.pdeReportesJrxml = pdeReportesJrxml;
    }

    public String getPdeFirma() {
        return pdeFirma;
    }

    public void setPdeFirma(String pdeFirma) {
        this.pdeFirma = pdeFirma;
    }

    public String getPdeCertificado() {
        return pdeCertificado;
    }

    public void setPdeCertificado(String pdeCertificado) {
        this.pdeCertificado = pdeCertificado;
    }

    public String getPdeCerAutorizado() {
        return pdeCerAutorizado;
    }

    public void setPdeCerAutorizado(String pdeCerAutorizado) {
        this.pdeCerAutorizado = pdeCerAutorizado;
    }

    public String getPdeCerNoAutorizado() {
        return pdeCerNoAutorizado;
    }

    public void setPdeCerNoAutorizado(String pdeCerNoAutorizado) {
        this.pdeCerNoAutorizado = pdeCerNoAutorizado;
    }

    public String getPdeCerContingencia() {
        return pdeCerContingencia;
    }

    public void setPdeCerContingencia(String pdeCerContingencia) {
        this.pdeCerContingencia = pdeCerContingencia;
    }

    public String getPdeCerDevuelto() {
        return pdeCerDevuelto;
    }

    public void setPdeCerDevuelto(String pdeCerDevuelto) {
        this.pdeCerDevuelto = pdeCerDevuelto;
    }

    public boolean getPdeMailHabilitarAutenticacion() {
        return pdeMailHabilitarAutenticacion;
    }

    public void setPdeMailHabilitarAutenticacion(boolean pdeMailHabilitarAutenticacion) {
        this.pdeMailHabilitarAutenticacion = pdeMailHabilitarAutenticacion;
    }

    public boolean getPdeMailHabilitarSSL() {
        return pdeMailHabilitarSSL;
    }

    public void setPdeMailHabilitarSSL(boolean pdeMailHabilitarSSL) {
        this.pdeMailHabilitarSSL = pdeMailHabilitarSSL;
    }

    public boolean getPdeMailHabilitarDEBUG() {
        return pdeMailHabilitarDEBUG;
    }

    public void setPdeMailHabilitarDEBUG(boolean pdeMailHabilitarDEBUG) {
        this.pdeMailHabilitarDEBUG = pdeMailHabilitarDEBUG;
    }

    public String getPdeMailHost() {
        return pdeMailHost;
    }

    public void setPdeMailHost(String pdeMailHost) {
        this.pdeMailHost = pdeMailHost;
    }

    public int getPdeMailPuerto() {
        return pdeMailPuerto;
    }

    public void setPdeMailPuerto(int pdeMailPuerto) {
        this.pdeMailPuerto = pdeMailPuerto;
    }

    public String getPdeMailServidorDNS() {
        return pdeMailServidorDNS;
    }

    public void setPdeMailServidorDNS(String pdeMailServidorDNS) {
        this.pdeMailServidorDNS = pdeMailServidorDNS;
    }

    public String getPdeMailProtocolo() {
        return pdeMailProtocolo;
    }

    public void setPdeMailProtocolo(String pdeMailProtocolo) {
        this.pdeMailProtocolo = pdeMailProtocolo;
    }

    public String getPdeMailUser() {
        return pdeMailUser;
    }

    public void setPdeMailUser(String pdeMailUser) {
        this.pdeMailUser = pdeMailUser;
    }

    public String getPdeMailPassword() {
        return pdeMailPassword;
    }

    public void setPdeMailPassword(String pdeMailPassword) {
        this.pdeMailPassword = pdeMailPassword;
    }

    public String getPdeWsRecepcion() {
        return pdeWsRecepcion;
    }

    public void setPdeWsRecepcion(String pdeWsRecepcion) {
        this.pdeWsRecepcion = pdeWsRecepcion;
    }

    public String getPdeWsAutorizacion() {
        return pdeWsAutorizacion;
    }

    public void setPdeWsAutorizacion(String pdeWsAutorizacion) {
        this.pdeWsAutorizacion = pdeWsAutorizacion;
    }

    public String getPdeMetodoRecepcion() {
        return pdeMetodoRecepcion;
    }

    public void setPdeMetodoRecepcion(String pdeMetodoRecepcion) {
        this.pdeMetodoRecepcion = pdeMetodoRecepcion;
    }

    public String getPdeNameSpaceURIRecepcion() {
        return pdeNameSpaceURIRecepcion;
    }

    public void setPdeNameSpaceURIRecepcion(String pdeNameSpaceURIRecepcion) {
        this.pdeNameSpaceURIRecepcion = pdeNameSpaceURIRecepcion;
    }

    public String getPdeMetodoAutorizacion() {
        return pdeMetodoAutorizacion;
    }

    public void setPdeMetodoAutorizacion(String pdeMetodoAutorizacion) {
        this.pdeMetodoAutorizacion = pdeMetodoAutorizacion;
    }

    public String getPdeNameSpaceURIAutorizacion() {
        return pdeNameSpaceURIAutorizacion;
    }

    public void setPdeNameSpaceURIAutorizacion(String pdeNameSpaceURIAutorizacion) {
        this.pdeNameSpaceURIAutorizacion = pdeNameSpaceURIAutorizacion;
    }

    public String getPdeClaveArchivo() {
        return pdeClaveArchivo;
    }

    public void setPdeClaveArchivo(String pdeClaveArchivo) {
        this.pdeClaveArchivo = pdeClaveArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pdeId != null ? pdeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PathDocEle)) {
            return false;
        }
        PathDocEle other = (PathDocEle) object;
        if ((this.pdeId == null && other.pdeId != null) || (this.pdeId != null && !this.pdeId.equals(other.pdeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PathDocEle[ pdeId=" + pdeId + " ]";
    }
    
}
