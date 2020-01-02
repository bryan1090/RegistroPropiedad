/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * @author WILSON
 */
@Entity
@Table(name = "TipoTramite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTramite.buscarTodo", query = "SELECT t FROM TipoTramite t ORDER BY t.ttrDescripcion")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrId", query = "SELECT t FROM TipoTramite t WHERE t.ttrId = :ttrId")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrDescripcion", query = "SELECT t FROM TipoTramite t WHERE t.ttrDescripcion = :ttrDescripcion")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrUser", query = "SELECT t FROM TipoTramite t WHERE t.ttrUser = :ttrUser")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrFHR", query = "SELECT t FROM TipoTramite t WHERE t.ttrFHR = :ttrFHR")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrValor", query = "SELECT t FROM TipoTramite t WHERE t.ttrValor = :ttrValor")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrValorFijo", query = "SELECT t FROM TipoTramite t WHERE t.ttrValorFijo = :ttrValorFijo")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrUnidadTiempo", query = "SELECT t FROM TipoTramite t WHERE t.ttrUnidadTiempo = :ttrUnidadTiempo")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrCantidadTiempo", query = "SELECT t FROM TipoTramite t WHERE t.ttrCantidadTiempo = :ttrCantidadTiempo")
    , @NamedQuery(name = "TipoTramite.buscarPorTtrDescripcionEditar", query = "SELECT t FROM TipoTramite t WHERE t.ttrDescripcion = :ttrDescripcion AND t.ttrId != :ttrId")})
public class TipoTramite implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ttrId")
    private List<TramiteTiempo> tramiteTiempoList;

    @Column(name = "TtrCerificado")
    private Boolean ttrCerificado;
    @Size(max = 10)
    @Column(name = "TrCodigoAgrupacion1")
    private String trCodigoAgrupacion1;
    @Size(max = 10)
    @Column(name = "TrCodigoAgrupacion2")
    private String trCodigoAgrupacion2;
    @Size(max = 10)
    @Column(name = "TrCodigoAgrupacion3")
    private String trCodigoAgrupacion3;
    @Size(max = 10)
    @Column(name = "TrCodigoAgrupacion4")
    private String trCodigoAgrupacion4;
    @Size(max = 10)
    @Column(name = "TrCodigoAgrupacion5")
    private String trCodigoAgrupacion5;
    @Size(max = 10)
    @Column(name = "TtrCodigoMigracion")
    private String ttrCodigoMigracion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ttrId")
    private List<TiempoProceso> tiempoProcesoList;
    
    @Column(name = "TtrPeso")
    private Long ttrPeso;

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoTramite.buscarTodo";
    public static final String BUSCAR_POR_ID = "TipoTramite.buscarPorTtrId";
    public static final String BUSCAR_POR_DESC = "TipoTramite.buscarPorTtrDescripcion";
    public static final String BUSCAR_POR_DESC_EDITAR = "TipoTramite.buscarPorTtrDescripcionEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TtrId")
    private Long ttrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TtrDescripcion")
    private String ttrDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TtrUser")
    private String ttrUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TtrFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ttrFHR;
    @Column(name = "TtrValor")
    private Short ttrValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TtrValorFijo")
    private BigDecimal ttrValorFijo;
    @Size(max = 1)
    @Column(name = "TtrUnidadTiempo")
    private String ttrUnidadTiempo;
    @Column(name = "TtrCantidadTiempo")
    private Short ttrCantidadTiempo;
    @Column(name = "TtrCodigo")
    private Integer ttrCodigo;
    @JoinColumn(name = "TplId", referencedColumnName = "TplId")
    @ManyToOne
    private TipoLibro tplId;


    @Size(max = 1)
    @Column(name = "TtrClase")
    private String ttrClase;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ttrId")
    private Collection<TramiteValor> tramiteValorCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ttrId")
    private Collection<TipoTramiteCompareciente> tipoTramiteComparecienteCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ttrId")
    private List<DocumentoEntregaTramite> documentoEntregaTramiteList;

    public TipoTramite() {
    }

    public TipoTramite(Long ttrId) {
        this.ttrId = ttrId;
    }

    public TipoTramite(Long ttrId, String ttrDescripcion, String ttrUser, Date ttrFHR, short ttrValor, BigDecimal ttrValorFijo, String ttrUnidadTiempo, short ttrCantidadTiempo, boolean ttrCertificado) {
        this.ttrId = ttrId;
        this.ttrDescripcion = ttrDescripcion;
        this.ttrUser = ttrUser;
        this.ttrFHR = ttrFHR;
        this.ttrValor = ttrValor;
        this.ttrValorFijo = ttrValorFijo;
        this.ttrUnidadTiempo = ttrUnidadTiempo;
        this.ttrCantidadTiempo = ttrCantidadTiempo;
        this.ttrCerificado = ttrCertificado;
    }


    public Long getTtrId() {
        return ttrId;
    }

    public void setTtrId(Long ttrId) {
        this.ttrId = ttrId;
    }

    public String getTtrDescripcion() {
        return ttrDescripcion;
    }

    public void setTtrDescripcion(String ttrDescripcion) {
        this.ttrDescripcion = ttrDescripcion;
    }

    public String getTtrUser() {
        return ttrUser;
    }

    public void setTtrUser(String ttrUser) {
        this.ttrUser = ttrUser;
    }

    public Date getTtrFHR() {
        return ttrFHR;
    }

    public void setTtrFHR(Date ttrFHR) {
        this.ttrFHR = ttrFHR;
    }

    public BigDecimal getTtrValorFijo() {
        return ttrValorFijo;
    }

    public void setTtrValorFijo(BigDecimal ttrValorFijo) {
        this.ttrValorFijo = ttrValorFijo;
    }


    public TipoLibro getTplId() {
        return tplId;
    }

    public void setTplId(TipoLibro tplId) {
        this.tplId = tplId;
    }

    public Integer getTtrCodigo() {
        return ttrCodigo;
    }

    public void setTtrCodigo(Integer ttrCodigo) {
        this.ttrCodigo = ttrCodigo;
    }
    
    public String getTtrClase() {
        return ttrClase;
    }

    public void setTtrClase(String ttrClase) {
        this.ttrClase = ttrClase;
    }

    public Long getTtrPeso() {
        return ttrPeso;
    }

    public void setTtrPeso(Long ttrPeso) {
        this.ttrPeso = ttrPeso;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ttrId != null ? ttrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTramite)) {
            return false;
        }
        TipoTramite other = (TipoTramite) object;
        if ((this.ttrId == null && other.ttrId != null) || (this.ttrId != null && !this.ttrId.equals(other.ttrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.TipoTramite[ ttrId=" + ttrId + " ]";
    }

    @XmlTransient
    public Collection<TipoTramiteCompareciente> getTipoTramiteComparecienteCollection() {
        return tipoTramiteComparecienteCollection;
    }

    public void setTipoTramiteComparecienteCollection(Collection<TipoTramiteCompareciente> tipoTramiteComparecienteCollection) {
        this.tipoTramiteComparecienteCollection = tipoTramiteComparecienteCollection;
    }

    public Short getTtrValor() {
        return ttrValor;
    }

    public void setTtrValor(Short ttrValor) {
        this.ttrValor = ttrValor;
    }

    public String getTtrUnidadTiempo() {
        return ttrUnidadTiempo;
    }

    public void setTtrUnidadTiempo(String ttrUnidadTiempo) {
        this.ttrUnidadTiempo = ttrUnidadTiempo;
    }

    public Short getTtrCantidadTiempo() {
        return ttrCantidadTiempo;
    }

    public void setTtrCantidadTiempo(Short ttrCantidadTiempo) {
        this.ttrCantidadTiempo = ttrCantidadTiempo;
    }

    @XmlTransient
    public Collection<TramiteValor> getTramiteValorCollection() {
        return tramiteValorCollection;
    }

    public void setTramiteValorCollection(Collection<TramiteValor> tramiteValorCollection) {
        this.tramiteValorCollection = tramiteValorCollection;
    }

    @XmlTransient
    public List<DocumentoEntregaTramite> getDocumentoEntregaTramiteList() {
        return documentoEntregaTramiteList;
    }

    public void setDocumentoEntregaTramiteList(List<DocumentoEntregaTramite> documentoEntregaTramiteList) {
        this.documentoEntregaTramiteList = documentoEntregaTramiteList;
    }

    public Boolean getTtrCerificado() {
        return ttrCerificado;
    }

    public void setTtrCerificado(Boolean ttrCerificado) {
        this.ttrCerificado = ttrCerificado;
    }

    public String getTrCodigoAgrupacion1() {
        return trCodigoAgrupacion1;
    }

    public void setTrCodigoAgrupacion1(String trCodigoAgrupacion1) {
        this.trCodigoAgrupacion1 = trCodigoAgrupacion1;
    }

    public String getTrCodigoAgrupacion2() {
        return trCodigoAgrupacion2;
    }

    public void setTrCodigoAgrupacion2(String trCodigoAgrupacion2) {
        this.trCodigoAgrupacion2 = trCodigoAgrupacion2;
    }

    public String getTrCodigoAgrupacion3() {
        return trCodigoAgrupacion3;
    }

    public void setTrCodigoAgrupacion3(String trCodigoAgrupacion3) {
        this.trCodigoAgrupacion3 = trCodigoAgrupacion3;
    }

    public String getTrCodigoAgrupacion4() {
        return trCodigoAgrupacion4;
    }

    public void setTrCodigoAgrupacion4(String trCodigoAgrupacion4) {
        this.trCodigoAgrupacion4 = trCodigoAgrupacion4;
    }

    public String getTrCodigoAgrupacion5() {
        return trCodigoAgrupacion5;
    }

    public void setTrCodigoAgrupacion5(String trCodigoAgrupacion5) {
        this.trCodigoAgrupacion5 = trCodigoAgrupacion5;
    }

    public String getTtrCodigoMigracion() {
        return ttrCodigoMigracion;
    }

    public void setTtrCodigoMigracion(String ttrCodigoMigracion) {
        this.ttrCodigoMigracion = ttrCodigoMigracion;
    }

    @XmlTransient
    public List<TiempoProceso> getTiempoProcesoList() {
        return tiempoProcesoList;
    }

    public void setTiempoProcesoList(List<TiempoProceso> tiempoProcesoList) {
        this.tiempoProcesoList = tiempoProcesoList;
    }

    @XmlTransient
    public List<TramiteTiempo> getTramiteTiempoList() {
        return tramiteTiempoList;
    }

    public void setTramiteTiempoList(List<TramiteTiempo> tramiteTiempoList) {
        this.tramiteTiempoList = tramiteTiempoList;
    }

}
