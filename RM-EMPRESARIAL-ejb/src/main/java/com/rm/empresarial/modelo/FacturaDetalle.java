/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "FacturaDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaDetalle.findAll", query = "SELECT f FROM FacturaDetalle f")
    , @NamedQuery(name = "FacturaDetalle.findByFdtId", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtId = :fdtId")
    , @NamedQuery(name = "FacturaDetalle.findByFdtTraNumero", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtTraNumero = :fdtTraNumero")
    , @NamedQuery(name = "FacturaDetalle.findByFdtCantidad", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtCantidad = :fdtCantidad")
    , @NamedQuery(name = "FacturaDetalle.findByFdtValor", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtValor = :fdtValor")
    , @NamedQuery(name = "FacturaDetalle.findByFdtDescuento", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtDescuento = :fdtDescuento")
    , @NamedQuery(name = "FacturaDetalle.findByFdtTotal", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtTotal = :fdtTotal")
    , @NamedQuery(name = "FacturaDetalle.findByFdtTtrId", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtTtrId = :fdtTtrId")
    , @NamedQuery(name = "FacturaDetalle.findByFdtTtrDescripcion", query = "SELECT f FROM FacturaDetalle f WHERE f.fdtTtrDescripcion = :fdtTtrDescripcion")
    , @NamedQuery(name = "FacturaDetalle.findByFacId", query = "SELECT f FROM FacturaDetalle f WHERE f.facId.facId = :facId")})
public class FacturaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ENCONTRAR_POR_NUMERO_TRAMITE = "FacturaDetalle.findByFdtTraNumero";
    public static final String LISTAR_POR_FACID = "FacturaDetalle.findByFacId";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FdtId", nullable = false)
    private BigDecimal fdtId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdtTraNumero")
    private int fdtTraNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdtCantidad")
    private BigDecimal fdtCantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdtValor")
    private BigDecimal fdtValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdtDescuento")
    private BigDecimal fdtDescuento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdtTotal")
    private BigDecimal fdtTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FdtTtrId")
    private BigInteger fdtTtrId;
    @Basic(optional = false)
    @Size(max = 200)
    @Column(name = "FdtTtrDescripcion")
    private String fdtTtrDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdtId")
    private List<FacturaDetalleImpuesto> facturaDetalleImpuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fdtId")
    private List<FacturaDetalleAdicional> facturaDetalleAdicionalList;
    @JoinColumn(name = "FacId", referencedColumnName = "FacId")
    @ManyToOne(optional = false)
    private Factura facId;

    @Getter
    @Setter
    @Transient
    private BigDecimal trvIva;

    @Getter
    @Setter
    @Transient
    private FacturaDetalleImpuesto listaImpuesto;
    
    @Getter
    @Setter
    @Transient
    private String fdiCodigo;
   
    @Getter
    @Setter
    @Transient
    private String fdiCodigoPorcentaje;
    
    @Getter
    @Setter
    @Transient
    private BigDecimal fdiTarifa;
    
    @Getter
    @Setter
    @Transient
    private BigDecimal fdiBaseImponible;
     
    @Getter
    @Setter
    @Column(name = "FdtPredio")
    private String numeroPredio;
    
    @Getter
    @Setter
    @Column(name = "FdtCatastro")
    private String numeroCatastro;
    
    

    
    public FacturaDetalle() {
    }

    public FacturaDetalle(BigDecimal fdtId) {
        this.fdtId = fdtId;
    }

    public FacturaDetalle(BigDecimal fdtId, int fdtTraNumero, BigDecimal fdtCantidad, BigDecimal fdtValor, BigDecimal fdtDescuento, BigDecimal fdtTotal, BigInteger fdtTtrId, String fdtTtrDescripcion, BigDecimal trvIva,String fdiCodigo,String fdiCodigoPorcentaje,BigDecimal fdiTarifa,BigDecimal fdiBaseImponible,String numeroPredio,String numeroCatastro) {
        this.fdtId = fdtId;
        this.fdtTraNumero = fdtTraNumero;
        this.fdtCantidad = fdtCantidad;
        this.fdtValor = fdtValor;
        this.fdtDescuento = fdtDescuento;
        this.fdtTotal = fdtTotal;
        this.fdtTtrId = fdtTtrId;
        this.fdtTtrDescripcion = fdtTtrDescripcion;
        this.trvIva = trvIva;
        this.fdiCodigo=fdiCodigo;
        this.fdiCodigoPorcentaje=fdiCodigoPorcentaje;
        this.fdiBaseImponible=fdiBaseImponible;
        this.fdiTarifa=fdiTarifa;
        this.numeroPredio=numeroPredio;
        this.numeroCatastro=numeroCatastro;
    }

    public BigDecimal getFdtId() {
        return fdtId;
    }

    public void setFdtId(BigDecimal fdtId) {
        this.fdtId = fdtId;
    }

    public int getFdtTraNumero() {
        return fdtTraNumero;
    }

    public void setFdtTraNumero(int fdtTraNumero) {
        this.fdtTraNumero = fdtTraNumero;
    }

    public BigDecimal getFdtCantidad() {
        return fdtCantidad;
    }

    public void setFdtCantidad(BigDecimal fdtCantidad) {
        this.fdtCantidad = fdtCantidad;
    }

    public BigDecimal getFdtValor() {
        return fdtValor;
    }

    public void setFdtValor(BigDecimal fdtValor) {
        this.fdtValor = fdtValor;
    }

    public BigDecimal getFdtDescuento() {
        return fdtDescuento;
    }

    public void setFdtDescuento(BigDecimal fdtDescuento) {
        this.fdtDescuento = fdtDescuento;
    }

    public BigDecimal getFdtTotal() {
        return fdtTotal;
    }

    public void setFdtTotal(BigDecimal fdtTotal) {
        this.fdtTotal = fdtTotal;
    }

    public BigInteger getFdtTtrId() {
        return fdtTtrId;
    }

    public void setFdtTtrId(BigInteger fdtTtrId) {
        this.fdtTtrId = fdtTtrId;
    }

    public String getFdtTtrDescripcion() {
        return fdtTtrDescripcion;
    }

    public void setFdtTtrDescripcion(String fdtTtrDescripcion) {
        this.fdtTtrDescripcion = fdtTtrDescripcion;
    }

    @XmlTransient
    public List<FacturaDetalleImpuesto> getFacturaDetalleImpuestoList() {
        return facturaDetalleImpuestoList;
    }

    public void setFacturaDetalleImpuestoList(List<FacturaDetalleImpuesto> facturaDetalleImpuestoList) {
        this.facturaDetalleImpuestoList = facturaDetalleImpuestoList;
    }

    @XmlTransient
    public List<FacturaDetalleAdicional> getFacturaDetalleAdicionalList() {
        return facturaDetalleAdicionalList;
    }

    public void setFacturaDetalleAdicionalList(List<FacturaDetalleAdicional> facturaDetalleAdicionalList) {
        this.facturaDetalleAdicionalList = facturaDetalleAdicionalList;
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
        hash += (fdtId != null ? fdtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaDetalle)) {
            return false;
        }
        FacturaDetalle other = (FacturaDetalle) object;
        if ((this.fdtId == null && other.fdtId != null) || (this.fdtId != null && !this.fdtId.equals(other.fdtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FacturaDetalle[ fdtId=" + fdtId + " ]";
    }

}
