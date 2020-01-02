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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "TramiteDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteDetalle.findAll", query = "SELECT t FROM TramiteDetalle t")
    , @NamedQuery(name = "TramiteDetalle.findByTdtId", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtId = :tdtId")
    , @NamedQuery(name = "TramiteDetalle.findByTdtTtrId", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtTtrId = :tdtTtrId")
    , @NamedQuery(name = "TramiteDetalle.findByTdtTtrDescripcion", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtTtrDescripcion = :tdtTtrDescripcion")
    , @NamedQuery(name = "TramiteDetalle.findByTdtTpcId", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtTpcId = :tdtTpcId")
    , @NamedQuery(name = "TramiteDetalle.findByTdtTpcDescripcion", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtTpcDescripcion = :tdtTpcDescripcion")
    , @NamedQuery(name = "TramiteDetalle.findByTdtPerTipoContribuyente", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtPerTipoContribuyente = :tdtPerTipoContribuyente")
    , @NamedQuery(name = "TramiteDetalle.findByTdtPerTipoIdentificacion", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtPerTipoIdentificacion = :tdtPerTipoIdentificacion")
    , @NamedQuery(name = "TramiteDetalle.findByTdtPerIdentificacion", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtPerIdentificacion = :tdtPerIdentificacion")
    , @NamedQuery(name = "TramiteDetalle.findByTdtPerNombre", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtPerNombre = :tdtPerNombre")
    , @NamedQuery(name = "TramiteDetalle.findByTdtPerApellidoPaterno", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtPerApellidoPaterno = :tdtPerApellidoPaterno")
    , @NamedQuery(name = "TramiteDetalle.findByTdtPerApellidoMaterno", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtPerApellidoMaterno = :tdtPerApellidoMaterno")
    , @NamedQuery(name = "TramiteDetalle.findByTdtConyuguePerId", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtConyuguePerId = :tdtConyuguePerId")
    , @NamedQuery(name = "TramiteDetalle.findByTdtConyuguePerTipoIden", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtConyuguePerTipoIden = :tdtConyuguePerTipoIden")
    , @NamedQuery(name = "TramiteDetalle.findByTdtConyuguePerNombre", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtConyuguePerNombre = :tdtConyuguePerNombre")
    , @NamedQuery(name = "TramiteDetalle.findByTdtTplId", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtTplId = :tdtTplId")
    , @NamedQuery(name = "TramiteDetalle.findByTdtTplDescripcion", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtTplDescripcion = :tdtTplDescripcion")
    , @NamedQuery(name = "TramiteDetalle.findByTdtEstado", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtEstado = :tdtEstado")
    , @NamedQuery(name = "TramiteDetalle.findByTdtUser", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtUser = :tdtUser")
    , @NamedQuery(name = "TramiteDetalle.findByTdtFHR", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtFHR = :tdtFHR")
    , @NamedQuery(name = "TramiteDetalle.findByTdtNumeroRepertorio", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtNumeroRepertorio = :tdtNumeroRepertorio")
    , @NamedQuery(name = "TramiteDetalle.findByTdtFechaRegistro", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtFechaRegistro = :tdtFechaRegistro")
    , @NamedQuery(name = "TramiteDetalle.findByTdtTpcCodigo", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtTpcCodigo = :tdtTpcCodigo")
    , @NamedQuery(name = "TramiteDetalle.findByTraNumero", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero")
    , @NamedQuery(name = "TramiteDetalle.encontrarPorNumTramiteYporTipoTramite", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtTtrId = :tdtTtrId")
    , @NamedQuery(name = "TramiteDetalle.listarPorNumTramiteYporTipoTramite", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtTtrId = :tipoTr")
    , @NamedQuery(name = "TramiteDetalle.encontrarPorTraNumeroOrderByNumRepertorio", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero ORDER BY t.tdtOrden")
    , @NamedQuery(name = "TramiteDetalle.encontrarPorNumTramYporPersonaYportramComparecienteYporEstado", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero = :traNumero AND t.perId=:perId AND t.ttcId=:ttcId AND t.tdtEstado LIKE 'A'")
    , @NamedQuery(name = "TramiteDetalle.findByTdtParNombre", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtParNombre = :tdtParNombre")
    , @NamedQuery(name = "TramiteDetalle.encontrarPorTraNumeroPorEstadoOrderByNumRepertorio", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtEstado LIKE 'A' ORDER BY t.tdtOrden")
    , @NamedQuery(name = "TramiteDetalle.findByTdtParId", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtParId = :tdtParId")
    , @NamedQuery(name = "TramiteDetalle.findByTdtCatastro", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtCatastro = :tdtCatastro")
    , @NamedQuery(name = "TramiteDetalle.findByTdtPredio", query = "SELECT t FROM TramiteDetalle t WHERE t.tdtPredio = :tdtPredio")
    , @NamedQuery(name = "TramiteDetalle.encontrarPorNumTramiteYDescripcion", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtTtrDescripcion = :traDescripcion ")
    , @NamedQuery(name = "TramiteDetalle.listarPorNumTramiteYporTipoTramitePorEstado", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtTtrId = :tipoTr AND t.tdtEstado = 'A'")
    , @NamedQuery(name = "TramiteDetalle.listarPorNumTramitePorTipoTramitePorEstadoPorRepertorio", query = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtTtrId = :tipoTr AND t.tdtEstado = 'A' AND t.tdtNumeroRepertorio = :numRepertorio")
    , @NamedQuery(name = "TramiteDetalle.obtenerPorPerId", query = "SELECT t FROM TramiteDetalle t WHERE t.perId = :perId ")})

public class TramiteDetalle implements Serializable {

    public static final String BUSCAR_POR_NUMERO_TRAMITE = "TramiteDetalle.findByTraNumero";
    public static final String BUSCAR_POR_NUMERO_TRAMITE_ORDER_BY_NUM_REPERTORIO = "TramiteDetalle.encontrarPorTraNumeroOrderByNumRepertorio";
    public static final String BUSCAR_POR_NUMERO_TRAMITE_Y_ESTADO_ORDER_BY_NUM_REPERTORIO = "TramiteDetalle.encontrarPorTraNumeroPorEstadoOrderByNumRepertorio";

    public static final String BUSCAR_POR_NUM_TRAMITE_Y_TIPO_TRAMITE = "TramiteDetalle.encontrarPorNumTramiteYporTipoTramite";
    public static final String LISTAR_POR_NUM_TRAMITE_Y_TIPO_TRAMITE = "TramiteDetalle.listarPorNumTramiteYporTipoTramite";
    public static final String LISTAR_POR_NUM_TRAMITE_Y_TIPO_TRAMITE_POR_ESTADO = "TramiteDetalle.listarPorNumTramiteYporTipoTramitePorEstado";
    public static final String LISTAR_POR_NUM_TRAMITE_TIPO_TRAMITE_ESTADO_REPERTORIO = "TramiteDetalle.listarPorNumTramitePorTipoTramitePorEstadoPorRepertorio";
    public static final String BUSCAR_POR_TRAMITE = "SELECT DISTINCT  *  from TramiteDetalle tde "
            + " INNER JOIN Tramite tra on tra.traNumero=tde.traNumero where tra.traNumero.traNumero =?1";

    public static final String BUSCAR_POR_NUM_TRAMITE_POR_PERSONA_POR_TIPO_TRAM_COMPARECIENTE_POR_ESTADO
            = "TramiteDetalle.encontrarPorNumTramYporPersonaYportramCompareciente";

    public static final String BUSCAR_POR_NUM_TRAMITE_Y_DESCRIPCION = "TramiteDetalle.encontrarPorNumTramiteYDescripcion";
    public static final String LISTAR_POR_NUM_REPERTORIO = "TramiteDetalle.findByTdtNumeroRepertorio";

    public static final String BUSCAR_POR_ID_TRAMITE_DETALLE = "TramiteDetalle.findByTdtId";

    private static final long serialVersionUID = 7399129631676085378L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TdtId", nullable = false)
    private Long tdtId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtTtrId")
    private Long tdtTtrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "TdtTtrDescripcion")
    private String tdtTtrDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtTpcId")
    private Long tdtTpcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "TdtTpcDescripcion")
    private String tdtTpcDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TdtPerTipoContribuyente")
    private String tdtPerTipoContribuyente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TdtPerTipoIdentificacion")
    private String tdtPerTipoIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdtPerIdentificacion")
    private String tdtPerIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TdtPerNombre")
    private String tdtPerNombre;
    @Basic(optional = false)
    
    @Size(max = 60)
    @Column(name = "TdtPerApellidoPaterno")
    private String tdtPerApellidoPaterno;
    @Basic(optional = false)
    
    @Size(max = 60)
    @Column(name = "TdtPerApellidoMaterno")
    private String tdtPerApellidoMaterno;
    @Column(name = "TdtConyuguePerId")
    private Long tdtConyuguePerId;
    @Size(max = 10)
    @Column(name = "TdtConyuguePerTipoIden")
    private String tdtConyuguePerTipoIden;
    @Size(max = 300)
    @Column(name = "TdtConyuguePerNombre")
    private String tdtConyuguePerNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtTplId")
    private Short tdtTplId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "TdtTplDescripcion")
    private String tdtTplDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TdtEstado")
    private String tdtEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdtUser")
    private String tdtUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar tdtFHR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtNumeroRepertorio")
    private int tdtNumeroRepertorio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdtFechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar tdtFechaRegistro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TdtTpcCodigo")
    private String tdtTpcCodigo;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;
    @JoinColumn(name = "TtcId", referencedColumnName = "TtcId")
    @ManyToOne(optional = false)
    private TipoTramiteCompareciente ttcId;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;
    @Size(max = 40)
    @Column(name = "TdtParNombre")
    private String tdtParNombre;
    @Column(name = "TdtParId")
    private Long tdtParId;
    @Size(max = 100)
    @Column(name = "TdtCatastro")
    private String tdtCatastro;
    @Size(max = 100)
    @Column(name = "TdtPredio")
    private String tdtPredio;
    @Column(name = "TdtOrden")
    private Integer tdtOrden;

    public TramiteDetalle() {
    }

    public TramiteDetalle(long tdtId) {
        this.tdtId = tdtId;
    }

    public TramiteDetalle(Long tdtId, Long tdtTtrId, String tdtTtrDescripcion, Long tdtTpcId, String tdtTpcDescripcion, String tdtPerTipoContribuyente, String tdtPerTipoIdentificacion, String tdtPerIdentificacion, String tdtPerNombre, String tdtPerApellidoPaterno, String tdtPerApellidoMaterno, Short tdtTplId, String tdtTplDescripcion, String tdtEstado, String tdtUser, Calendar tdtFHR, int tdtNumeroRepertorio, Calendar tdtFechaRegistro, String tdtTpcCodigo, String tdtParNombre, Long tdtParId) {
        this.tdtId = tdtId;
        this.tdtTtrId = tdtTtrId;
        this.tdtTtrDescripcion = tdtTtrDescripcion;
        this.tdtTpcId = tdtTpcId;
        this.tdtTpcDescripcion = tdtTpcDescripcion;
        this.tdtPerTipoContribuyente = tdtPerTipoContribuyente;
        this.tdtPerTipoIdentificacion = tdtPerTipoIdentificacion;
        this.tdtPerIdentificacion = tdtPerIdentificacion;
        this.tdtPerNombre = tdtPerNombre;
        this.tdtPerApellidoPaterno = tdtPerApellidoPaterno;
        this.tdtPerApellidoMaterno = tdtPerApellidoMaterno;
        this.tdtTplId = tdtTplId;
        this.tdtTplDescripcion = tdtTplDescripcion;
        this.tdtEstado = tdtEstado;
        this.tdtUser = tdtUser;
        this.tdtFHR = tdtFHR;
        this.tdtNumeroRepertorio = tdtNumeroRepertorio;
        this.tdtFechaRegistro = tdtFechaRegistro;
        this.tdtTpcCodigo = tdtTpcCodigo;
        this.tdtParNombre = tdtParNombre;
        this.tdtParId = tdtParId;
    }

    public Long getTdtId() {
        return tdtId;
    }

    public void setTdtId(Long tdtId) {
        this.tdtId = tdtId;
    }

    public Long getTdtTtrId() {
        return tdtTtrId;
    }

    public void setTdtTtrId(Long tdtTtrId) {
        this.tdtTtrId = tdtTtrId;
    }

    public String getTdtTtrDescripcion() {
        return tdtTtrDescripcion;
    }

    public void setTdtTtrDescripcion(String tdtTtrDescripcion) {
        this.tdtTtrDescripcion = tdtTtrDescripcion;
    }

    public Long getTdtTpcId() {
        return tdtTpcId;
    }

    public void setTdtTpcId(Long tdtTpcId) {
        this.tdtTpcId = tdtTpcId;
    }

    public String getTdtTpcDescripcion() {
        return tdtTpcDescripcion;
    }

    public void setTdtTpcDescripcion(String tdtTpcDescripcion) {
        this.tdtTpcDescripcion = tdtTpcDescripcion;
    }

    public String getTdtPerTipoContribuyente() {
        return tdtPerTipoContribuyente;
    }

    public void setTdtPerTipoContribuyente(String tdtPerTipoContribuyente) {
        this.tdtPerTipoContribuyente = tdtPerTipoContribuyente;
    }

    public String getTdtPerTipoIdentificacion() {
        return tdtPerTipoIdentificacion;
    }

    public void setTdtPerTipoIdentificacion(String tdtPerTipoIdentificacion) {
        this.tdtPerTipoIdentificacion = tdtPerTipoIdentificacion;
    }

    public String getTdtPerIdentificacion() {
        return tdtPerIdentificacion;
    }

    public void setTdtPerIdentificacion(String tdtPerIdentificacion) {
        this.tdtPerIdentificacion = tdtPerIdentificacion;
    }

    public String getTdtPerNombre() {
        return tdtPerNombre;
    }

    public void setTdtPerNombre(String tdtPerNombre) {
        this.tdtPerNombre = tdtPerNombre;
    }

    public String getTdtPerApellidoPaterno() {
        return tdtPerApellidoPaterno;
    }

    public void setTdtPerApellidoPaterno(String tdtPerApellidoPaterno) {
        this.tdtPerApellidoPaterno = tdtPerApellidoPaterno;
    }

    public String getTdtPerApellidoMaterno() {
        return tdtPerApellidoMaterno;
    }

    public void setTdtPerApellidoMaterno(String tdtPerApellidoMaterno) {
        this.tdtPerApellidoMaterno = tdtPerApellidoMaterno;
    }

    public Long getTdtConyuguePerId() {
        return tdtConyuguePerId;
    }

    public void setTdtConyuguePerId(Long tdtConyuguePerId) {
        this.tdtConyuguePerId = tdtConyuguePerId;
    }

    public String getTdtConyuguePerTipoIden() {
        return tdtConyuguePerTipoIden;
    }

    public void setTdtConyuguePerTipoIden(String tdtConyuguePerTipoIden) {
        this.tdtConyuguePerTipoIden = tdtConyuguePerTipoIden;
    }

    public String getTdtConyuguePerNombre() {
        return tdtConyuguePerNombre;
    }

    public void setTdtConyuguePerNombre(String tdtConyuguePerNombre) {
        this.tdtConyuguePerNombre = tdtConyuguePerNombre;
    }

    public Short getTdtTplId() {
        return tdtTplId;
    }

    public void setTdtTplId(Short tdtTplId) {
        this.tdtTplId = tdtTplId;
    }

    public String getTdtTplDescripcion() {
        return tdtTplDescripcion;
    }

    public void setTdtTplDescripcion(String tdtTplDescripcion) {
        this.tdtTplDescripcion = tdtTplDescripcion;
    }

    public String getTdtEstado() {
        return tdtEstado;
    }

    public void setTdtEstado(String tdtEstado) {
        this.tdtEstado = tdtEstado;
    }

    public String getTdtUser() {
        return tdtUser;
    }

    public void setTdtUser(String tdtUser) {
        this.tdtUser = tdtUser;
    }

    public Calendar getTdtFHR() {
        return tdtFHR;
    }

    public void setTdtFHR(Calendar tdtFHR) {
        this.tdtFHR = tdtFHR;
    }

    public int getTdtNumeroRepertorio() {
        return tdtNumeroRepertorio;
    }

    public void setTdtNumeroRepertorio(int tdtNumeroRepertorio) {
        this.tdtNumeroRepertorio = tdtNumeroRepertorio;
    }

    public Calendar getTdtFechaRegistro() {
        return tdtFechaRegistro;
    }

    public void setTdtFechaRegistro(Calendar tdtFechaRegistro) {
        this.tdtFechaRegistro = tdtFechaRegistro;
    }

    public String getTdtTpcCodigo() {
        return tdtTpcCodigo;
    }

    public void setTdtTpcCodigo(String tdtTpcCodigo) {
        this.tdtTpcCodigo = tdtTpcCodigo;
    }

    public Persona getPerId() {
        return perId;
    }

    public void setPerId(Persona perId) {
        this.perId = perId;
    }

    public TipoTramiteCompareciente getTtcId() {
        return ttcId;
    }

    public void setTtcId(TipoTramiteCompareciente ttcId) {
        this.ttcId = ttcId;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    public String getTdtParNombre() {
        return tdtParNombre;
    }

    public void setTdtParNombre(String tdtParNombre) {
        this.tdtParNombre = tdtParNombre;
    }

    public Long getTdtParId() {
        return tdtParId;
    }

    public void setTdtParId(Long tdtParId) {
        this.tdtParId = tdtParId;
    }

    public String getTdtCatastro() {
        return tdtCatastro;
    }

    public void setTdtCatastro(String tdtCatastro) {
        this.tdtCatastro = tdtCatastro;
    }

    public String getTdtPredio() {
        return tdtPredio;
    }

    public void setTdtPredio(String tdtPredio) {
        this.tdtPredio = tdtPredio;
    }

    public Integer getTdtOrden() {
        return tdtOrden;
    }

    public void setTdtOrden(Integer tdtOrden) {
        this.tdtOrden = tdtOrden;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdtId != null ? tdtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteDetalle)) {
            return false;
        }
        TramiteDetalle other = (TramiteDetalle) object;
        if ((this.tdtId == null && other.tdtId != null) || (this.tdtId != null && !this.tdtId.equals(other.tdtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TramiteDetalle[ tdtId=" + tdtId + " ]";
    }

}
