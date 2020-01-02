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
 * @author Prueba
 */
@Entity
@Table(name = "FormularioDigital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormularioDigital.findAll", query = "SELECT f FROM FormularioDigital f")
    , @NamedQuery(name = "FormularioDigital.findByFmdId", query = "SELECT f FROM FormularioDigital f WHERE f.fmdId = :fmdId")
    , @NamedQuery(name = "FormularioDigital.findByFmdNombreArchivo", query = "SELECT f FROM FormularioDigital f WHERE f.fmdNombreArchivo = :fmdNombreArchivo")
    , @NamedQuery(name = "FormularioDigital.findByFmdPath", query = "SELECT f FROM FormularioDigital f WHERE f.fmdPath = :fmdPath")
    , @NamedQuery(name = "FormularioDigital.findByFmdExtension", query = "SELECT f FROM FormularioDigital f WHERE f.fmdExtension = :fmdExtension")
    , @NamedQuery(name = "FormularioDigital.findByFmdEstado", query = "SELECT f FROM FormularioDigital f WHERE f.fmdEstado = :fmdEstado")
    , @NamedQuery(name = "FormularioDigital.findByFmdUser", query = "SELECT f FROM FormularioDigital f WHERE f.fmdUser = :fmdUser")
    , @NamedQuery(name = "FormularioDigital.findByFmdFHR", query = "SELECT f FROM FormularioDigital f WHERE f.fmdFHR = :fmdFHR")
    , @NamedQuery(name = "FormularioDigital.buscarPorFechaInicioFechaFin", query = "SELECT f FROM FormularioDigital f WHERE f.fmdFHR >= :fechaInicio AND f.fmdFHR <= :fechaFin ")
    , @NamedQuery(name = "FormularioDigital.buscarPorNumeroFactura", query = "SELECT f FROM FormularioDigital f WHERE f.facId.facNumero = :numeroFactura ")
})
public class FormularioDigital implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO="FormularioDigital.findAll";
    public static final String BUSCAR_POR_FECHA_INICIO_FECHA_FIN = "FormularioDigital.buscarPorFechaInicioFechaFin";
    public static final String BUSCAR_POR_NUMERO_FACTURA = "FormularioDigital.buscarPorNumeroFactura";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FmdId")
    private Long fmdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FmdNombreArchivo")
    private String fmdNombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "FmdPath")
    private String fmdPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FmdExtension")
    private String fmdExtension;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "FmdEstado")
    private String fmdEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FmdUser")
    private String fmdUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FmdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fmdFHR;
    @JoinColumn(name = "FacId", referencedColumnName = "FacId")
    @ManyToOne(optional = false)
    private Factura facId;

    public FormularioDigital() {
    }

    public FormularioDigital(Long fmdId) {
        this.fmdId = fmdId;
    }

    public FormularioDigital(Long fmdId, String fmdNombreArchivo, String fmdPath, String fmdExtension, String fmdEstado, String fmdUser, Date fmdFHR) {
        this.fmdId = fmdId;
        this.fmdNombreArchivo = fmdNombreArchivo;
        this.fmdPath = fmdPath;
        this.fmdExtension = fmdExtension;
        this.fmdEstado = fmdEstado;
        this.fmdUser = fmdUser;
        this.fmdFHR = fmdFHR;
    }

    public Long getFmdId() {
        return fmdId;
    }

    public void setFmdId(Long fmdId) {
        this.fmdId = fmdId;
    }

    public String getFmdNombreArchivo() {
        return fmdNombreArchivo;
    }

    public void setFmdNombreArchivo(String fmdNombreArchivo) {
        this.fmdNombreArchivo = fmdNombreArchivo;
    }

    public String getFmdPath() {
        return fmdPath;
    }

    public void setFmdPath(String fmdPath) {
        this.fmdPath = fmdPath;
    }

    public String getFmdExtension() {
        return fmdExtension;
    }

    public void setFmdExtension(String fmdExtension) {
        this.fmdExtension = fmdExtension;
    }

    public String getFmdEstado() {
        return fmdEstado;
    }

    public void setFmdEstado(String fmdEstado) {
        this.fmdEstado = fmdEstado;
    }

    public String getFmdUser() {
        return fmdUser;
    }

    public void setFmdUser(String fmdUser) {
        this.fmdUser = fmdUser;
    }

    public Date getFmdFHR() {
        return fmdFHR;
    }

    public void setFmdFHR(Date fmdFHR) {
        this.fmdFHR = fmdFHR;
    }

    public Factura getFacId() {
        return facId;
    }

    public void setFacId(Factura facId) {
        this.facId = facId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fmdId != null ? fmdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioDigital)) {
            return false;
        }
        FormularioDigital other = (FormularioDigital) object;
        if ((this.fmdId == null && other.fmdId != null) || (this.fmdId != null && !this.fmdId.equals(other.fmdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoCertifcado.FormularioDigital[ fmdId=" + fmdId + " ]";
    }
    
}
