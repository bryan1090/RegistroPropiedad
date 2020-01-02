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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "FacturaTrabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaTrabajo.findAll", query = "SELECT f FROM FacturaTrabajo f")
    , @NamedQuery(name = "FacturaTrabajo.findByFtrId", query = "SELECT f FROM FacturaTrabajo f WHERE f.ftrId = :ftrId")
    , @NamedQuery(name = "FacturaTrabajo.findByFtrEstado", query = "SELECT f FROM FacturaTrabajo f WHERE f.ftrEstado = :ftrEstado")
    , @NamedQuery(name = "FacturaTrabajo.findByFtrUser", query = "SELECT f FROM FacturaTrabajo f WHERE f.ftrUser = :ftrUser")
    , @NamedQuery(name = "FacturaTrabajo.findByFtrFRH", query = "SELECT f FROM FacturaTrabajo f WHERE f.ftrFRH = :ftrFRH")})
public class FacturaTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FtrId")
    private BigDecimal ftrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "FtrEstado")
    private String ftrEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FtrUser")
    private String ftrUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FtrFRH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ftrFRH;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ftrId")
    private List<FacturaTrabajoDetalle> facturaTrabajoDetalleList;
    @JoinColumn(name = "FacId", referencedColumnName = "FacId")
    @ManyToOne(optional = false)
    private Factura facId;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public FacturaTrabajo() {
    }

    public FacturaTrabajo(BigDecimal ftrId) {
        this.ftrId = ftrId;
    }

    public FacturaTrabajo(BigDecimal ftrId, String ftrEstado, String ftrUser, Date ftrFRH) {
        this.ftrId = ftrId;
        this.ftrEstado = ftrEstado;
        this.ftrUser = ftrUser;
        this.ftrFRH = ftrFRH;
    }

    public BigDecimal getFtrId() {
        return ftrId;
    }

    public void setFtrId(BigDecimal ftrId) {
        this.ftrId = ftrId;
    }

    public String getFtrEstado() {
        return ftrEstado;
    }

    public void setFtrEstado(String ftrEstado) {
        this.ftrEstado = ftrEstado;
    }

    public String getFtrUser() {
        return ftrUser;
    }

    public void setFtrUser(String ftrUser) {
        this.ftrUser = ftrUser;
    }

    public Date getFtrFRH() {
        return ftrFRH;
    }

    public void setFtrFRH(Date ftrFRH) {
        this.ftrFRH = ftrFRH;
    }

    @XmlTransient
    public List<FacturaTrabajoDetalle> getFacturaTrabajoDetalleList() {
        return facturaTrabajoDetalleList;
    }

    public void setFacturaTrabajoDetalleList(List<FacturaTrabajoDetalle> facturaTrabajoDetalleList) {
        this.facturaTrabajoDetalleList = facturaTrabajoDetalleList;
    }

    public Factura getFacId() {
        return facId;
    }

    public void setFacId(Factura facId) {
        this.facId = facId;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ftrId != null ? ftrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaTrabajo)) {
            return false;
        }
        FacturaTrabajo other = (FacturaTrabajo) object;
        if ((this.ftrId == null && other.ftrId != null) || (this.ftrId != null && !this.ftrId.equals(other.ftrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FacturaTrabajo[ ftrId=" + ftrId + " ]";
    }
    
}
