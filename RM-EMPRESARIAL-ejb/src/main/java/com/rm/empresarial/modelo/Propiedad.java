/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
@Table(name = "Propiedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propiedad.findAll", query = "SELECT p FROM Propiedad p")
    ,@NamedQuery(name = "Propiedad.findAllActivo", query = "SELECT p FROM Propiedad p WHERE p.prdEstadoRegistro = :prdEstadoRegistro")
    , @NamedQuery(name = "Propiedad.findByPrdMatricula", query = "SELECT p FROM Propiedad p WHERE p.prdMatricula = :prdMatricula")
    , @NamedQuery(name = "Propiedad.findByPrdCatastro", query = "SELECT p FROM Propiedad p WHERE p.prdCatastro = :prdCatastro")
    , @NamedQuery(name = "Propiedad.findByPrdPredio", query = "SELECT p FROM Propiedad p WHERE p.prdPredio = :prdPredio")
    , @NamedQuery(name = "Propiedad.findByPrdEspacial", query = "SELECT p FROM Propiedad p WHERE p.prdEspacial = :prdEspacial")
    , @NamedQuery(name = "Propiedad.findByPrdLatitud", query = "SELECT p FROM Propiedad p WHERE p.prdLatitud = :prdLatitud")
    , @NamedQuery(name = "Propiedad.findByPrdLongitud", query = "SELECT p FROM Propiedad p WHERE p.prdLongitud = :prdLongitud")
    , @NamedQuery(name = "Propiedad.findByPrdUbicacion", query = "SELECT p FROM Propiedad p WHERE p.prdUbicacion = :prdUbicacion")
    , @NamedQuery(name = "Propiedad.findByPrdSector", query = "SELECT p FROM Propiedad p WHERE p.prdSector = :prdSector")
    , @NamedQuery(name = "Propiedad.findByPrdEtapa", query = "SELECT p FROM Propiedad p WHERE p.prdEtapa = :prdEtapa")
    , @NamedQuery(name = "Propiedad.findByPrdSuperManzana", query = "SELECT p FROM Propiedad p WHERE p.prdSuperManzana = :prdSuperManzana")
    , @NamedQuery(name = "Propiedad.findByPrdBloque", query = "SELECT p FROM Propiedad p WHERE p.prdBloque = :prdBloque")
    , @NamedQuery(name = "Propiedad.findByPrdPiso", query = "SELECT p FROM Propiedad p WHERE p.prdPiso = :prdPiso")
    , @NamedQuery(name = "Propiedad.findByPrdSuperficie", query = "SELECT p FROM Propiedad p WHERE p.prdSuperficie = :prdSuperficie")
    , @NamedQuery(name = "Propiedad.findByPrdNivel", query = "SELECT p FROM Propiedad p WHERE p.prdNivel = :prdNivel")
    , @NamedQuery(name = "Propiedad.findByPrdNumero", query = "SELECT p FROM Propiedad p WHERE p.prdNumero = :prdNumero")
    , @NamedQuery(name = "Propiedad.findByPrdEstadoPropiedad", query = "SELECT p FROM Propiedad p WHERE p.prdEstadoPropiedad = :prdEstadoPropiedad")
    , @NamedQuery(name = "Propiedad.findByPrdEstadoRegistro", query = "SELECT p FROM Propiedad p WHERE p.prdEstadoRegistro = :prdEstadoRegistro")
    , @NamedQuery(name = "Propiedad.findByPrdUserCreador", query = "SELECT p FROM Propiedad p WHERE p.prdUserCreador = :prdUserCreador")
    , @NamedQuery(name = "Propiedad.findByPrdFHR", query = "SELECT p FROM Propiedad p WHERE p.prdFHR = :prdFHR")
    , @NamedQuery(name = "Propiedad.findByPrdUserModificacion", query = "SELECT p FROM Propiedad p WHERE p.prdUserModificacion = :prdUserModificacion")
    , @NamedQuery(name = "Propiedad.findByPrdFHM", query = "SELECT p FROM Propiedad p WHERE p.prdFHM = :prdFHM")
    , @NamedQuery(name = "Propiedad.findByPrdClasePropiedad", query = "SELECT p FROM Propiedad p WHERE p.prdClasePropiedad = :prdClasePropiedad")
    , @NamedQuery(name = "Propiedad.findByPrdTdtParNombreH", query = "SELECT p FROM Propiedad p WHERE p.prdTdtParNombreH = :prdTdtParNombreH")
    , @NamedQuery(name = "Propiedad.findByPrdTdtParIdH", query = "SELECT p FROM Propiedad p WHERE p.prdTdtParIdH = :prdTdtParIdH")
    , @NamedQuery(name = "Propiedad.findByPrdTdtParNombre", query = "SELECT p FROM Propiedad p WHERE p.prdTdtParNombre = :prdTdtParNombre")
    , @NamedQuery(name = "Propiedad.findByPrdTdtParId", query = "SELECT p FROM Propiedad p WHERE p.prdTdtParId = :prdTdtParId")
    , @NamedQuery(name = "Propiedad.findByPrdDescripcion2", query = "SELECT p FROM Propiedad p WHERE p.prdDescripcion2 = :prdDescripcion2")
    , @NamedQuery(name = "Propiedad.findByPrdDescripcion1", query = "SELECT p FROM Propiedad p WHERE p.prdDescripcion1 = :prdDescripcion1")
    , @NamedQuery(name = "Propiedad.findByPrdArea", query = "SELECT p FROM Propiedad p WHERE p.prdArea = :prdArea")
    , @NamedQuery(name = "Propiedad.findByPrdAlicuota", query = "SELECT p FROM Propiedad p WHERE p.prdAlicuota = :prdAlicuota")
    , @NamedQuery(name = "Propiedad.findByPrdConjuntoId", query = "SELECT p FROM Propiedad p WHERE p.prdConjuntoId = :prdConjuntoId")
    , @NamedQuery(name = "Propiedad.findByPrdConjunto", query = "SELECT p FROM Propiedad p WHERE p.prdConjunto = :prdConjunto")
    , @NamedQuery(name = "Propiedad.findByPrdAdjudicacion", query = "SELECT p FROM Propiedad p WHERE p.prdAdjudicacion = :prdAdjudicacion")
    , @NamedQuery(name = "Propiedad.findByPrdPH", query = "SELECT p FROM Propiedad p WHERE p.prdPH = :prdPH")
    , @NamedQuery(name = "Propiedad.findByPrdVendio", query = "SELECT p FROM Propiedad p WHERE p.prdVendio = :prdVendio")
    , @NamedQuery(name = "Propiedad.findByPrdUnidadVivienda", query = "SELECT p FROM Propiedad p WHERE p.prdUnidadVivienda = :prdUnidadVivienda")
    , @NamedQuery(name = "Propiedad.findByPrdAgregado", query = "SELECT p FROM Propiedad p WHERE p.prdAgregado = :prdAgregado")
    , @NamedQuery(name = "Propiedad.findByPrdValidacion", query = "SELECT p FROM Propiedad p WHERE p.prdValidacion = :prdValidacion")
    , @NamedQuery(name = "Propiedad.findByPrdTabla", query = "SELECT p FROM Propiedad p WHERE p.prdTabla = :prdTabla")
    , @NamedQuery(name = "Propiedad.findByPrdIdMigrado", query = "SELECT p FROM Propiedad p WHERE p.prdIdMigrado = :prdIdMigrado")})
public class Propiedad implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Propiedad.findAll";
    public static final String LISTAR_TODO_ACTIVO = "Propiedad.findAllActivo";
    public static final String LISTAR_PORID = "Propiedad.findByPrdMatricula";

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PrdMatricula")
    private String prdMatricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PrdCatastro")
    private String prdCatastro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PrdPredio")
    private String prdPredio;
    @Size(max = 40)
    @Column(name = "PrdEspacial")
    private String prdEspacial;
    @Size(max = 40)
    @Column(name = "PrdLatitud")
    private String prdLatitud;
    @Size(max = 40)
    @Column(name = "PrdLongitud")
    private String prdLongitud;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "PrdUbicacion")
    private String prdUbicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "PrdSector")
    private String prdSector;
    @Size(max = 60)
    @Column(name = "PrdEtapa")
    private String prdEtapa;
    @Size(max = 60)
    @Column(name = "PrdSuperManzana")
    private String prdSuperManzana;
    @Size(max = 60)
    @Column(name = "PrdBloque")
    private String prdBloque;
    @Size(max = 60)
    @Column(name = "PrdPiso")
    private String prdPiso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PrdSuperficie")
    private BigDecimal prdSuperficie;
    @Column(name = "PrdNivel")
    private Short prdNivel;
    @Column(name = "PrdNumero")
    private Integer prdNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PrdEstadoPropiedad")
    private String prdEstadoPropiedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PrdEstadoRegistro")
    private String prdEstadoRegistro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PrdUserCreador")
    private String prdUserCreador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prdFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PrdUserModificacion")
    private String prdUserModificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrdFHM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prdFHM;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "PrdClasePropiedad")
    private String prdClasePropiedad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PrdTdtParNombreH")
    private String prdTdtParNombreH;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrdTdtParIdH")
    private Long prdTdtParIdH;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PrdTdtParNombre")
    private String prdTdtParNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrdTdtParId")
    private Long prdTdtParId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "PrdDescripcion2")
    private String prdDescripcion2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "PrdDescripcion1")
    private String prdDescripcion1;
    @OneToMany(mappedBy = "prdPadreMatricula")
    private List<Propiedad> propiedadList;
    @JoinColumn(name = "PrdPadreMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne
    private Propiedad prdPadreMatricula;
    @JoinColumn(name = "TpdId", referencedColumnName = "TpdId")
    @ManyToOne
    private TipoPropiedad tpdId;
    @JoinColumn(name = "TclId", referencedColumnName = "TclId")
    @ManyToOne
    private TipoPropiedadClase tclId;
    @JoinColumn(name = "UmdId", referencedColumnName = "UmdId")
    @ManyToOne
    private UnidMedida umdId;

    @Size(max = 40)
    @Column(name = "PrdArea")
    private String prdArea;
    @Column(name = "PrdAlicuota")
    private BigDecimal prdAlicuota;
    @Column(name = "PrdConjuntoId")
    private Long prdConjuntoId;
    @Size(max = 100)
    @Column(name = "PrdConjunto")
    private String prdConjunto;
    @Column(name = "PrdAdjudicacion")
    private Short prdAdjudicacion;
    @Size(max = 2)
    @Column(name = "PrdPH")
    private String prdPH;
    @Size(max = 2)
    @Column(name = "PrdVendio")
    private String prdVendio;

    @Getter
    @Setter
    @Size(max = 50)
    @Column(name = "PrdUnidadVivienda")
    private String prdUnidadVivienda;
    @Getter
    @Setter
    @Size(max = 1)
    @Column(name = "PrdAgregado")
    private String prdAgregado;
    @Getter
    @Setter
    @Size(max = 1)
    @Column(name = "PrdValidacion")
    private String prdValidacion;

    @Size(max = 100)
    @Column(name = "PrdTabla")
    private String prdTabla;
    @Size(max = 200)
    @Column(name = "PrdIdMigrado")
    private String prdIdMigrado;

    @Transient
    private List<Lindero> listalinderos;

    @Getter
    @Setter
    @Transient
    private Boolean bolVendioSN;

    @Getter
    @Setter
    @Transient
    private String observacionT="Ninguna";

    public Propiedad() {
        listalinderos = new ArrayList<>();
    }

    public Propiedad(String prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    public Propiedad(String prdMatricula, String prdCatastro, String prdPredio, String prdUbicacion, String prdSector, String prdEstadoPropiedad, String prdEstadoRegistro, String prdUserCreador, Date prdFHR, String prdUserModificacion, Date prdFHM, String prdClasePropiedad, String prdTdtParNombreH, Long prdTdtParIdH, String prdTdtParNombre, Long prdTdtParId, String prdDescripcion2, String prdDescripcion1) {
        this.prdMatricula = prdMatricula;
        this.prdCatastro = prdCatastro;
        this.prdPredio = prdPredio;
        this.prdUbicacion = prdUbicacion;
        this.prdSector = prdSector;
        this.prdEstadoPropiedad = prdEstadoPropiedad;
        this.prdEstadoRegistro = prdEstadoRegistro;
        this.prdUserCreador = prdUserCreador;
        this.prdFHR = prdFHR;
        this.prdUserModificacion = prdUserModificacion;
        this.prdFHM = prdFHM;
        this.prdClasePropiedad = prdClasePropiedad;
        this.prdTdtParNombreH = prdTdtParNombreH;
        this.prdTdtParIdH = prdTdtParIdH;
        this.prdTdtParNombre = prdTdtParNombre;
        this.prdTdtParId = prdTdtParId;
        this.prdDescripcion2 = prdDescripcion2;
        this.prdDescripcion1 = prdDescripcion1;
    }

    public String getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(String prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    public String getPrdCatastro() {
        return prdCatastro;
    }

    public void setPrdCatastro(String prdCatastro) {
        this.prdCatastro = prdCatastro;
    }

    public String getPrdPredio() {
        return prdPredio;
    }

    public void setPrdPredio(String prdPredio) {
        this.prdPredio = prdPredio;
    }

    public String getPrdEspacial() {
        return prdEspacial;
    }

    public void setPrdEspacial(String prdEspacial) {
        this.prdEspacial = prdEspacial;
    }

    public String getPrdLatitud() {
        return prdLatitud;
    }

    public void setPrdLatitud(String prdLatitud) {
        this.prdLatitud = prdLatitud;
    }

    public String getPrdLongitud() {
        return prdLongitud;
    }

    public void setPrdLongitud(String prdLongitud) {
        this.prdLongitud = prdLongitud;
    }

    public String getPrdUbicacion() {
        return prdUbicacion;
    }

    public void setPrdUbicacion(String prdUbicacion) {
        this.prdUbicacion = prdUbicacion;
    }

    public String getPrdSector() {
        return prdSector;
    }

    public void setPrdSector(String prdSector) {
        this.prdSector = prdSector;
    }

    public String getPrdEtapa() {
        return prdEtapa;
    }

    public void setPrdEtapa(String prdEtapa) {
        this.prdEtapa = prdEtapa;
    }

    public String getPrdSuperManzana() {
        return prdSuperManzana;
    }

    public void setPrdSuperManzana(String prdSuperManzana) {
        this.prdSuperManzana = prdSuperManzana;
    }

    public String getPrdBloque() {
        return prdBloque;
    }

    public void setPrdBloque(String prdBloque) {
        this.prdBloque = prdBloque;
    }

    public String getPrdPiso() {
        return prdPiso;
    }

    public void setPrdPiso(String prdPiso) {
        this.prdPiso = prdPiso;
    }

    public BigDecimal getPrdSuperficie() {
        return prdSuperficie;
    }

    public void setPrdSuperficie(BigDecimal prdSuperficie) {
        this.prdSuperficie = prdSuperficie;
    }

    public Short getPrdNivel() {
        return prdNivel;
    }

    public void setPrdNivel(Short prdNivel) {
        this.prdNivel = prdNivel;
    }

    public Integer getPrdNumero() {
        return prdNumero;
    }

    public void setPrdNumero(Integer prdNumero) {
        this.prdNumero = prdNumero;
    }

    public String getPrdEstadoPropiedad() {
        return prdEstadoPropiedad;
    }

    public void setPrdEstadoPropiedad(String prdEstadoPropiedad) {
        this.prdEstadoPropiedad = prdEstadoPropiedad;
    }

    public String getPrdEstadoRegistro() {
        return prdEstadoRegistro;
    }

    public void setPrdEstadoRegistro(String prdEstadoRegistro) {
        this.prdEstadoRegistro = prdEstadoRegistro;
    }

    public String getPrdUserCreador() {
        return prdUserCreador;
    }

    public void setPrdUserCreador(String prdUserCreador) {
        this.prdUserCreador = prdUserCreador;
    }

    public Date getPrdFHR() {
        return prdFHR;
    }

    public void setPrdFHR(Date prdFHR) {
        this.prdFHR = prdFHR;
    }

    public String getPrdUserModificacion() {
        return prdUserModificacion;
    }

    public void setPrdUserModificacion(String prdUserModificacion) {
        this.prdUserModificacion = prdUserModificacion;
    }

    public Date getPrdFHM() {
        return prdFHM;
    }

    public void setPrdFHM(Date prdFHM) {
        this.prdFHM = prdFHM;
    }

    public String getPrdClasePropiedad() {
        return prdClasePropiedad;
    }

    public void setPrdClasePropiedad(String prdClasePropiedad) {
        this.prdClasePropiedad = prdClasePropiedad;
    }

    public String getPrdTdtParNombreH() {
        return prdTdtParNombreH;
    }

    public void setPrdTdtParNombreH(String prdTdtParNombreH) {
        this.prdTdtParNombreH = prdTdtParNombreH;
    }

    public Long getPrdTdtParIdH() {
        return prdTdtParIdH;
    }

    public void setPrdTdtParIdH(Long prdTdtParIdH) {
        this.prdTdtParIdH = prdTdtParIdH;
    }

    public String getPrdTdtParNombre() {
        return prdTdtParNombre;
    }

    public void setPrdTdtParNombre(String prdTdtParNombre) {
        this.prdTdtParNombre = prdTdtParNombre;
    }

    public Long getPrdTdtParId() {
        return prdTdtParId;
    }

    public void setPrdTdtParId(Long prdTdtParId) {
        this.prdTdtParId = prdTdtParId;
    }

    public String getPrdDescripcion2() {
        return prdDescripcion2;
    }

    public void setPrdDescripcion2(String prdDescripcion2) {
        this.prdDescripcion2 = prdDescripcion2;
    }

    public String getPrdDescripcion1() {
        return prdDescripcion1;
    }

    public void setPrdDescripcion1(String prdDescripcion1) {
        this.prdDescripcion1 = prdDescripcion1;
    }

    public String getPrdArea() {
        return prdArea;
    }

    public void setPrdArea(String prdArea) {
        this.prdArea = prdArea;
    }

    public BigDecimal getPrdAlicuota() {
        return prdAlicuota;
    }

    public void setPrdAlicuota(BigDecimal prdAlicuota) {
        this.prdAlicuota = prdAlicuota;
    }

    public Long getPrdConjuntoId() {
        return prdConjuntoId;
    }

    public void setPrdConjuntoId(Long prdConjuntoId) {
        this.prdConjuntoId = prdConjuntoId;
    }

    public String getPrdConjunto() {
        return prdConjunto;
    }

    public void setPrdConjunto(String prdConjunto) {
        this.prdConjunto = prdConjunto;
    }

    public Short getPrdAdjudicacion() {
        return prdAdjudicacion;
    }

    public void setPrdAdjudicacion(Short prdAdjudicacion) {
        this.prdAdjudicacion = prdAdjudicacion;
    }

    public String getPrdPH() {
        return prdPH;
    }

    public void setPrdPH(String prdPH) {
        this.prdPH = prdPH;
    }

    public String getPrdVendio() {
        return prdVendio;
    }

    public void setPrdVendio(String prdVendio) {
        this.prdVendio = prdVendio;
    }

    public String getPrdValidacion() {
        return prdValidacion;
    }

    public void setPrdValidacion(String prdValidacion) {
        this.prdValidacion = prdValidacion;
    }

    public String getPrdTabla() {
        return prdTabla;
    }

    public void setPrdTabla(String prdTabla) {
        this.prdTabla = prdTabla;
    }

    public String getPrdIdMigrado() {
        return prdIdMigrado;
    }

    public void setPrdIdMigrado(String prdIdMigrado) {
        this.prdIdMigrado = prdIdMigrado;
    }

    @XmlTransient
    public List<Propiedad> getPropiedadList() {
        return propiedadList;
    }

    public void setPropiedadList(List<Propiedad> propiedadList) {
        this.propiedadList = propiedadList;
    }

    public Propiedad getPrdPadreMatricula() {
        return prdPadreMatricula;
    }

    public void setPrdPadreMatricula(Propiedad prdPadreMatricula) {
        this.prdPadreMatricula = prdPadreMatricula;
    }

    public TipoPropiedad getTpdId() {
        return tpdId;
    }

    public void setTpdId(TipoPropiedad tpdId) {
        this.tpdId = tpdId;
    }

    public TipoPropiedadClase getTclId() {
        return tclId;
    }

    public void setTclId(TipoPropiedadClase tclId) {
        this.tclId = tclId;
    }

    public UnidMedida getUmdId() {
        return umdId;
    }

    public void setUmdId(UnidMedida umdId) {
        this.umdId = umdId;
    }

    public List<Lindero> getListalinderos() {
        return listalinderos;
    }

    public void setListalinderos(List<Lindero> listalinderos) {
        this.listalinderos = listalinderos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prdMatricula != null ? prdMatricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propiedad)) {
            return false;
        }
        Propiedad other = (Propiedad) object;
        if ((this.prdMatricula == null && other.prdMatricula != null) || (this.prdMatricula != null && !this.prdMatricula.equals(other.prdMatricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Propiedad[ prdMatricula=" + prdMatricula + " ]";
    }

}
