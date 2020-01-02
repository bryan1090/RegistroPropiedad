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
@Table(name = "FacturaDetalleImpuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaDetalleImpuesto.findAll", query = "SELECT f FROM FacturaDetalleImpuesto f")
    , @NamedQuery(name = "FacturaDetalleImpuesto.findByFdiId", query = "SELECT f FROM FacturaDetalleImpuesto f WHERE f.fdiId = :fdiId")
    , @NamedQuery(name = "FacturaDetalleImpuesto.findByFdiCodigo", query = "SELECT f FROM FacturaDetalleImpuesto f WHERE f.fdiCodigo = :fdiCodigo")
    , @NamedQuery(name = "FacturaDetalleImpuesto.findByFdiCodigoPorcentaje", query = "SELECT f FROM FacturaDetalleImpuesto f WHERE f.fdiCodigoPorcentaje = :fdiCodigoPorcentaje")
    , @NamedQuery(name = "FacturaDetalleImpuesto.findByFdiTarifa", query = "SELECT f FROM FacturaDetalleImpuesto f WHERE f.fdiTarifa = :fdiTarifa")
    , @NamedQuery(name = "FacturaDetalleImpuesto.findByFdiBaseImponible", query = "SELECT f FROM FacturaDetalleImpuesto f WHERE f.fdiBaseImponible = :fdiBaseImponible")
    , @NamedQuery(name = "FacturaDetalleImpuesto.buscarPorFdiFacturaDetalle", query = "SELECT f FROM FacturaDetalleImpuesto f WHERE f.fdtId = :fdtId")})
public class FacturaDetalleImpuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_DETALLE_FACTURA = "FacturaDetalleImpuesto.buscarPorFdiFacturaDetalle";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FdiId", nullable = false)
    private BigDecimal fdiId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FdiCodigo")
    private String fdiCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FdiCodigoPorcentaje")
    private String fdiCodigoPorcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdiTarifa")
    private BigDecimal fdiTarifa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdiBaseImponible")
    private BigDecimal fdiBaseImponible;
    @JoinColumn(name = "FdtId", referencedColumnName = "FdtId")
    @ManyToOne(optional = false)
    private FacturaDetalle fdtId;

    public FacturaDetalleImpuesto() {
    }

    public FacturaDetalleImpuesto(BigDecimal fdiId) {
        this.fdiId = fdiId;
    }

    public FacturaDetalleImpuesto(BigDecimal fdiId, String fdiCodigo, String fdiCodigoPorcentaje, BigDecimal fdiTarifa, BigDecimal fdiBaseImponible) {
        this.fdiId = fdiId;
        this.fdiCodigo = fdiCodigo;
        this.fdiCodigoPorcentaje = fdiCodigoPorcentaje;
        this.fdiTarifa = fdiTarifa;
        this.fdiBaseImponible = fdiBaseImponible;
    }

    public BigDecimal getFdiId() {
        return fdiId;
    }

    public void setFdiId(BigDecimal fdiId) {
        this.fdiId = fdiId;
    }

    public String getFdiCodigo() {
        return fdiCodigo;
    }

    public void setFdiCodigo(String fdiCodigo) {
        this.fdiCodigo = fdiCodigo;
    }

    public String getFdiCodigoPorcentaje() {
        return fdiCodigoPorcentaje;
    }

    public void setFdiCodigoPorcentaje(String fdiCodigoPorcentaje) {
        this.fdiCodigoPorcentaje = fdiCodigoPorcentaje;
    }

    public BigDecimal getFdiTarifa() {
        return fdiTarifa;
    }

    public void setFdiTarifa(BigDecimal fdiTarifa) {
        this.fdiTarifa = fdiTarifa;
    }

    public BigDecimal getFdiBaseImponible() {
        return fdiBaseImponible;
    }

    public void setFdiBaseImponible(BigDecimal fdiBaseImponible) {
        this.fdiBaseImponible = fdiBaseImponible;
    }

    public FacturaDetalle getFdtId() {
        return fdtId;
    }

    public void setFdtId(FacturaDetalle fdtId) {
        this.fdtId = fdtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fdiId != null ? fdiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaDetalleImpuesto)) {
            return false;
        }
        FacturaDetalleImpuesto other = (FacturaDetalleImpuesto) object;
        if ((this.fdiId == null && other.fdiId != null) || (this.fdiId != null && !this.fdiId.equals(other.fdiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FacturaDetalleImpuesto[ fdiId=" + fdiId + " ]";
    }

}
