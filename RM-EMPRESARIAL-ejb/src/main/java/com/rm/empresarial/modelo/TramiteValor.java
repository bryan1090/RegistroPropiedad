/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
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
 * @author Wilson
 */
@Entity
@Table(name = "TramiteValor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteValor.buscarTodo", query = "SELECT t FROM TramiteValor t")
    , @NamedQuery(name = "TramiteValor.buscarPorTrvId", query = "SELECT t FROM TramiteValor t WHERE t.trvId = :trvId")
    , @NamedQuery(name = "TramiteValor.buscarPorTrvValor", query = "SELECT t FROM TramiteValor t WHERE t.trvValor = :trvValor")
    , @NamedQuery(name = "TramiteValor.buscarPorTrvEstado", query = "SELECT t FROM TramiteValor t WHERE t.trvEstado = :trvEstado")
    , @NamedQuery(name = "TramiteValor.buscarPorTrvUser", query = "SELECT t FROM TramiteValor t WHERE t.trvUser = :trvUser")
    , @NamedQuery(name = "TramiteValor.buscarPorTrvFHR", query = "SELECT t FROM TramiteValor t WHERE t.trvFHR = :trvFHR")
    , @NamedQuery(name = "TramiteValor.buscarPorTrvIva", query = "SELECT t FROM TramiteValor t WHERE t.trvIva = :trvIva")
, @NamedQuery(name = "TramiteValor.buscarPorTramite", query = "SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero")
, @NamedQuery(name = "TramiteValor.buscarPorTramiteyTipo", query = "SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero and t.ttrId=:ttrId AND t.trvEstado='A'")
, @NamedQuery(name = "TramiteValor.buscarPorTramiteyTipoyCertificadoNull", query = "SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero and t.ttrId=:ttrId AND t.trvEstado='A' AND t.trvCertificado IS NULL")
, @NamedQuery(name = "TramiteValor.buscarTotalTraValor", query = "SELECT  t.trvValor FROM TramiteValor t WHERE t.traNumero = :traNumero")})
public class TramiteValor implements Serializable {

    @Column(name = "TrvParId")
    private Long trvParId;
    @Size(max = 100)
    @Column(name = "TrvCertificado")
    private String trvCertificado;
    @Getter
    @Setter
    @Size(max = 1)
    @Column(name = "TrvFacturado")
    private String trvFacturado;

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_TRAMITE="TramiteValor.buscarPorTramite";
    public static final String BUSCAR_POR_TRAMITE_TIPO="TramiteValor.buscarPorTramiteyTipo";
    public static final String BUSCAR_POR_TRAMITE_TIPO_CERTIFICADO_NULL="TramiteValor.buscarPorTramiteyTipoyCertificadoNull";
	public static final String BUSCAR_TOTAL_TRA_VALOR="TramiteValor.buscarTotalTraValor";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TrvId", nullable = false)
    private Long trvId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TrvValor")
    private BigDecimal trvValor;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TrvEstado")
    private String trvEstado;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TrvUser")    
    private String trvUser;
    
    @Column(name = "TrvFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trvFHR;
    
    @Column(name = "TrvIva")
    private BigDecimal trvIva;
    
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;
    
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;
       
    @Basic(optional = true)
    @Size(max = 100)
    @Column(name = "TraNumPredio")   
    private String traNumPredio;
    
    @Basic(optional = true)
    @Column(name = "TrvValorBien")   
    private BigDecimal trvValorBien;
    
    @Basic(optional = true)
    @Column(name = "TrvCantidad")   
    private BigDecimal trvCantidad;
    
    @Basic(optional = true)
    @Column(name = "TrvPorIva")   
    private BigDecimal trvPorIva;
    
   
    
    
    
    @Basic(optional = true)
    @Column(name = "TrvParNombre")   
    private String trvParNombre;
    
    @Basic(optional = true)
    @Size(max = 100)
    @Column(name = "TrvNumCatastro")   
    private String trvNumCatastro;
    
    @Column(name = "TrvAccion")
    private Integer trvAccion;
    
    @Transient
    private String ttrDescripcion;
    @Transient
    @Getter
    @Setter
    private Boolean tienePropHija;
    @Column(name = "TrvPrincipal")
    private Boolean trvPrincipal;  
    
    @Getter
    @Setter
    @Basic(optional = false)    
    @Column(name = "TrvDescuento")
    private BigDecimal trvDescuento;
    @Getter
    @Setter 
    @Basic(optional = false)    
    @Column(name = "TvrPorcentajeDescuento")
    private BigDecimal tvrPorcentajeDescuento;

    public TramiteValor() {
    }

    public TramiteValor(Long trvId) {
        this.trvId = trvId;
    }

    
    public TramiteValor(Long trvId, BigDecimal trvValor, String trvEstado, String trvUser, Date trvFHR, BigDecimal trvIva, TipoTramite ttrId, Tramite traNumero, String traNumPredio, BigDecimal trvValorBien, BigDecimal trvCantidad, BigDecimal trvPorIva, Long trvParId, String trvParNombre, String trvNumCatastro, Integer trvAccion, String ttrDescripcion, Boolean tienePropHija, Boolean trvPrincipal,String trvCertificado) {
        this.trvId = trvId;
        this.trvValor = trvValor;
        this.trvEstado = trvEstado;
        this.trvUser = trvUser;
        this.trvFHR = trvFHR;
        this.trvIva = trvIva;
        this.ttrId = ttrId;
        this.traNumero = traNumero;
        this.traNumPredio = traNumPredio;
        this.trvValorBien = trvValorBien;
        this.trvCantidad = trvCantidad;
        this.trvPorIva = trvPorIva;
        this.trvParId = trvParId;
        this.trvParNombre = trvParNombre;
        this.trvNumCatastro = trvNumCatastro;
        this.trvAccion = trvAccion;
        this.ttrDescripcion = ttrDescripcion;
        this.tienePropHija = tienePropHija;
        this.trvPrincipal = trvPrincipal;
        this.trvCertificado =trvCertificado;
    }

    
    
    
    public Long getTrvId() {
        return trvId;
    }

    public void setTrvId(Long trvId) {
        this.trvId = trvId;
    }

    public BigDecimal getTrvValor() {
        return trvValor;
    }

    public void setTrvValor(BigDecimal trvValor) {
        this.trvValor = trvValor;
    }

    public String getTrvEstado() {
        return trvEstado;
    }

    public void setTrvEstado(String trvEstado) {
        this.trvEstado = trvEstado;
    }

    public String getTrvUser() {
        return trvUser;
    }

    public void setTrvUser(String trvUser) {
        this.trvUser = trvUser;
    }

    public Date getTrvFHR() {
        return trvFHR;
    }

    public void setTrvFHR(Date trvFHR) {
        this.trvFHR = trvFHR;
    }

    public BigDecimal getTrvIva() {
        return trvIva;
    }

    public void setTrvIva(BigDecimal trvIva) {
        this.trvIva = trvIva;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }
        
    public BigDecimal getTrvValorBien() {
        return trvValorBien;
    }

    public void setTrvValorBien(BigDecimal trvValorBien) {
        this.trvValorBien = trvValorBien;
    }
    public String getTraNumPredio() {
        return traNumPredio;
    }

    public void setTraNumPredio(String traNumPredio) {
        this.traNumPredio = traNumPredio;
    }

    public String getTtrDescripcion() {
        return ttrDescripcion;
    }

    public void setTtrDescripcion(String ttrDescripcion) {
        this.ttrDescripcion = ttrDescripcion;
    }
    
    public BigDecimal getTrvCantidad() {
        return trvCantidad;
    }

    public void setTrvCantidad(BigDecimal trvCantidad) {
        this.trvCantidad = trvCantidad;
    }
    
     public BigDecimal geTtrvPorIva() {
        return trvPorIva;
    }

    public void setTtrvPorIva(BigDecimal trvPorIva) {
        this.trvPorIva = trvPorIva;
    }


    public String getTrvParNombre() {
        return trvParNombre;
    }

    public void setTrvParNombre(String trvParNombre) {
        this.trvParNombre = trvParNombre;
    }

    public String getTrvNumCatastro() {
        return trvNumCatastro;
    }

    public void setTrvNumCatastro(String trvNumCatastro) {
        this.trvNumCatastro = trvNumCatastro;
    }
    
     public Integer getTrvAccion() {
        return trvAccion;
    }

    public void setTrvAccion(Integer trvAccion) {
        this.trvAccion = trvAccion;
    }
    
    public Boolean getTrvPrincipal() {
        return trvPrincipal;
    }

    public void setTrvPrincipal(Boolean trvPrincipal) {
        this.trvPrincipal = trvPrincipal;
    }
         
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trvId != null ? trvId.hashCode() : 0);
        return hash;
    }
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteValor)) {
            return false;
        }
        TramiteValor other = (TramiteValor) object;
        if ((this.trvId == null && other.trvId != null) || (this.trvId != null && !this.trvId.equals(other.trvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.TramiteValor[ trvId=" + trvId + " ]";
    }

    public Long getTrvParId() {
        return trvParId;
    }

    public void setTrvParId(Long trvParId) {
        this.trvParId = trvParId;
    }

    public String getTrvCertificado() {
        return trvCertificado;
    }

    public void setTrvCertificado(String trvCertificado) {
        this.trvCertificado = trvCertificado;
    }
    
}
