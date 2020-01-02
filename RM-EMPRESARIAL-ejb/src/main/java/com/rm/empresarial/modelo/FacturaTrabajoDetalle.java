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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "FacturaTrabajoDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaTrabajoDetalle.findAll", query = "SELECT f FROM FacturaTrabajoDetalle f")
    , @NamedQuery(name = "FacturaTrabajoDetalle.findByFtdId", query = "SELECT f FROM FacturaTrabajoDetalle f WHERE f.ftdId = :ftdId")
    , @NamedQuery(name = "FacturaTrabajoDetalle.findByFtdDescripcion", query = "SELECT f FROM FacturaTrabajoDetalle f WHERE f.ftdDescripcion = :ftdDescripcion")
    , @NamedQuery(name = "FacturaTrabajoDetalle.findByFtdPath", query = "SELECT f FROM FacturaTrabajoDetalle f WHERE f.ftdPath = :ftdPath")})
public class FacturaTrabajoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FtdId")
    private BigDecimal ftdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "FtdDescripcion")
    private String ftdDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "FtdPath")
    private String ftdPath;
    @JoinColumn(name = "FtrId", referencedColumnName = "FtrId")
    @ManyToOne(optional = false)
    private FacturaTrabajo ftrId;

    public FacturaTrabajoDetalle() {
    }

    public FacturaTrabajoDetalle(BigDecimal ftdId) {
        this.ftdId = ftdId;
    }

    public FacturaTrabajoDetalle(BigDecimal ftdId, String ftdDescripcion, String ftdPath) {
        this.ftdId = ftdId;
        this.ftdDescripcion = ftdDescripcion;
        this.ftdPath = ftdPath;
    }

    public BigDecimal getFtdId() {
        return ftdId;
    }

    public void setFtdId(BigDecimal ftdId) {
        this.ftdId = ftdId;
    }

    public String getFtdDescripcion() {
        return ftdDescripcion;
    }

    public void setFtdDescripcion(String ftdDescripcion) {
        this.ftdDescripcion = ftdDescripcion;
    }

    public String getFtdPath() {
        return ftdPath;
    }

    public void setFtdPath(String ftdPath) {
        this.ftdPath = ftdPath;
    }

    public FacturaTrabajo getFtrId() {
        return ftrId;
    }

    public void setFtrId(FacturaTrabajo ftrId) {
        this.ftrId = ftrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ftdId != null ? ftdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaTrabajoDetalle)) {
            return false;
        }
        FacturaTrabajoDetalle other = (FacturaTrabajoDetalle) object;
        if ((this.ftdId == null && other.ftdId != null) || (this.ftdId != null && !this.ftdId.equals(other.ftdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FacturaTrabajoDetalle[ ftdId=" + ftdId + " ]";
    }
    
}
