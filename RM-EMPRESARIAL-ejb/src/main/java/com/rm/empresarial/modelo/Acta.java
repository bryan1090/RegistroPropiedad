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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import org.eclipse.persistence.annotations.PrivateOwned;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Acta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acta.findAll", query = "SELECT a FROM Acta a")
    , @NamedQuery(name = "Acta.findByActNumero", query = "SELECT a FROM Acta a WHERE a.actNumero = :actNumero")
    , @NamedQuery(name = "Acta.findByActActa", query = "SELECT a FROM Acta a WHERE a.actActa = :actActa")
    , @NamedQuery(name = "Acta.findByActInscripcion", query = "SELECT a FROM Acta a WHERE a.actInscripcion = :actInscripcion")
    , @NamedQuery(name = "Acta.findByActTomo", query = "SELECT a FROM Acta a WHERE a.actTomo = :actTomo")
    , @NamedQuery(name = "Acta.findByActBis", query = "SELECT a FROM Acta a WHERE a.actBis = :actBis")
    , @NamedQuery(name = "Acta.findByActVolumen", query = "SELECT a FROM Acta a WHERE a.actVolumen = :actVolumen")
    , @NamedQuery(name = "Acta.findByActFoja", query = "SELECT a FROM Acta a WHERE a.actFoja = :actFoja")
    , @NamedQuery(name = "Acta.findByActCatastro", query = "SELECT a FROM Acta a WHERE a.actCatastro = :actCatastro")
    , @NamedQuery(name = "Acta.findByActPredio", query = "SELECT a FROM Acta a WHERE a.actPredio = :actPredio")
    , @NamedQuery(name = "Acta.findByActDescripcion20", query = "SELECT a FROM Acta a WHERE a.actDescripcion20 = :actDescripcion20")
    , @NamedQuery(name = "Acta.findByActDescripcion19", query = "SELECT a FROM Acta a WHERE a.actDescripcion19 = :actDescripcion19")
    , @NamedQuery(name = "Acta.findByActDescripcion18", query = "SELECT a FROM Acta a WHERE a.actDescripcion18 = :actDescripcion18")
    , @NamedQuery(name = "Acta.findByActDescripcion17", query = "SELECT a FROM Acta a WHERE a.actDescripcion17 = :actDescripcion17")
    , @NamedQuery(name = "Acta.findByActDescripcion16", query = "SELECT a FROM Acta a WHERE a.actDescripcion16 = :actDescripcion16")
    , @NamedQuery(name = "Acta.findByActDescripcion15", query = "SELECT a FROM Acta a WHERE a.actDescripcion15 = :actDescripcion15")
    , @NamedQuery(name = "Acta.findByActDescripcion14", query = "SELECT a FROM Acta a WHERE a.actDescripcion14 = :actDescripcion14")
    , @NamedQuery(name = "Acta.findByActDescripcion13", query = "SELECT a FROM Acta a WHERE a.actDescripcion13 = :actDescripcion13")
    , @NamedQuery(name = "Acta.findByActDescripcion11", query = "SELECT a FROM Acta a WHERE a.actDescripcion11 = :actDescripcion11")
    , @NamedQuery(name = "Acta.findByActDescripcion10", query = "SELECT a FROM Acta a WHERE a.actDescripcion10 = :actDescripcion10")
    , @NamedQuery(name = "Acta.findByActDescripcion9", query = "SELECT a FROM Acta a WHERE a.actDescripcion9 = :actDescripcion9")
    , @NamedQuery(name = "Acta.findByActDescripcion8", query = "SELECT a FROM Acta a WHERE a.actDescripcion8 = :actDescripcion8")
    , @NamedQuery(name = "Acta.findByActDescripcion7", query = "SELECT a FROM Acta a WHERE a.actDescripcion7 = :actDescripcion7")
    , @NamedQuery(name = "Acta.findByActDescripcion6", query = "SELECT a FROM Acta a WHERE a.actDescripcion6 = :actDescripcion6")
    , @NamedQuery(name = "Acta.findByActDescripcion5", query = "SELECT a FROM Acta a WHERE a.actDescripcion5 = :actDescripcion5")
    , @NamedQuery(name = "Acta.findByActDescripcion4", query = "SELECT a FROM Acta a WHERE a.actDescripcion4 = :actDescripcion4")
    , @NamedQuery(name = "Acta.findByActDescripcion3", query = "SELECT a FROM Acta a WHERE a.actDescripcion3 = :actDescripcion3")
    , @NamedQuery(name = "Acta.findByActDescripcion2", query = "SELECT a FROM Acta a WHERE a.actDescripcion2 = :actDescripcion2")
    , @NamedQuery(name = "Acta.findByActDescripcion1", query = "SELECT a FROM Acta a WHERE a.actDescripcion1 = :actDescripcion1")
    , @NamedQuery(name = "Acta.findByActNumeroImpresion", query = "SELECT a FROM Acta a WHERE a.actNumeroImpresion = :actNumeroImpresion")
    , @NamedQuery(name = "Acta.findByActAnio", query = "SELECT a FROM Acta a WHERE a.actAnio = :actAnio")
    , @NamedQuery(name = "Acta.findByActUser", query = "SELECT a FROM Acta a WHERE a.actUser = :actUser")
    , @NamedQuery(name = "Acta.findByActFch", query = "SELECT a FROM Acta a WHERE a.actFch = :actFch")
    , @NamedQuery(name = "Acta.findByActFHR", query = "SELECT a FROM Acta a WHERE a.actFHR = :actFHR")
    , @NamedQuery(name = "Acta.findByActEstado", query = "SELECT a FROM Acta a WHERE a.actEstado = :actEstado")
    , @NamedQuery(name = "Acta.findByActActaPdf", query = "SELECT a FROM Acta a WHERE a.actActaPdf = :actActaPdf")
    , @NamedQuery(name = "Acta.findByActParroquia", query = "SELECT a FROM Acta a WHERE a.actParroquia = :actParroquia")
    , @NamedQuery(name = "Acta.findByActDescripcion", query = "SELECT a FROM Acta a WHERE a.actDescripcion = :actDescripcion")
    , @NamedQuery(name = "Acta.findByActTipoContrato", query = "SELECT a FROM Acta a WHERE a.actTipoContrato = :actTipoContrato")
    , @NamedQuery(name = "Acta.findByActFechaIngreso", query = "SELECT a FROM Acta a WHERE a.actFechaIngreso = :actFechaIngreso")
    , @NamedQuery(name = "Acta.findByActFechaDe", query = "SELECT a FROM Acta a WHERE a.actFechaDe = :actFechaDe")
    , @NamedQuery(name = "Acta.findByActFechaSentencia", query = "SELECT a FROM Acta a WHERE a.actFechaSentencia = :actFechaSentencia")
    , @NamedQuery(name = "Acta.findByActTpjId", query = "SELECT a FROM Acta a WHERE a.actTpjId = :actTpjId")
    , @NamedQuery(name = "Acta.findByActNumeroJucio", query = "SELECT a FROM Acta a WHERE a.actNumeroJucio = :actNumeroJucio")
    , @NamedQuery(name = "Acta.findByActJuzgado", query = "SELECT a FROM Acta a WHERE a.actJuzgado = :actJuzgado")
    , @NamedQuery(name = "Acta.findByActNotaria", query = "SELECT a FROM Acta a WHERE a.actNotaria = :actNotaria")
    , @NamedQuery(name = "Acta.findByActNumeroOficio", query = "SELECT a FROM Acta a WHERE a.actNumeroOficio = :actNumeroOficio")
    , @NamedQuery(name = "Acta.findByActObservacion", query = "SELECT a FROM Acta a WHERE a.actObservacion = :actObservacion")
    , @NamedQuery(name = "Acta.findByActPath", query = "SELECT a FROM Acta a WHERE a.actPath = :actPath")
    , @NamedQuery(name = "Acta.findByActNombreArchivo", query = "SELECT a FROM Acta a WHERE a.actNombreArchivo = :actNombreArchivo")
    , @NamedQuery(name = "Acta.findByActMigrado", query = "SELECT a FROM Acta a WHERE a.actMigrado = :actMigrado")
    , @NamedQuery(name = "Acta.findByActNumeroHojas", query = "SELECT a FROM Acta a WHERE a.actNumeroHojas = :actNumeroHojas")
    , @NamedQuery(name = "Acta.findByActTabla", query = "SELECT a FROM Acta a WHERE a.actTabla = :actTabla")
    , @NamedQuery(name = "Acta.findByActIdMigrado", query = "SELECT a FROM Acta a WHERE a.actIdMigrado = :actIdMigrado")
 , @NamedQuery(name = "Acta.findByActFirmaDigital", query = "SELECT a FROM Acta a WHERE a.actFirmaDigital = :actFirmaDigital")})


public class Acta implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Acta.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ActNumero")
    private Long actNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ActActa")
    private String actActa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActInscripcion")
    private BigInteger actInscripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActTomo")
    private int actTomo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ActBis")
    private String actBis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActVolumen")
    private long actVolumen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActFoja")
    private long actFoja;
    @Size(max = 100)
    @Column(name = "ActCatastro")
    private String actCatastro;
    @Size(max = 100)
    @Column(name = "ActPredio")
    private String actPredio;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion20")
    private String actDescripcion20;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion19")
    private String actDescripcion19;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion18")
    private String actDescripcion18;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion17")
    private String actDescripcion17;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion16")
    private String actDescripcion16;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion15")
    private String actDescripcion15;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion14")
    private String actDescripcion14;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion13")
    private String actDescripcion13;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion11")
    private String actDescripcion11;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion10")
    private String actDescripcion10;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion9")
    private String actDescripcion9;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion8")
    private String actDescripcion8;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion7")
    private String actDescripcion7;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion6")
    private String actDescripcion6;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion5")
    private String actDescripcion5;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion4")
    private String actDescripcion4;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion3")
    private String actDescripcion3;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion2")
    private String actDescripcion2;
    @Size(max = 2147483647)
    @Column(name = "ActDescripcion1")
    private String actDescripcion1;
    @Column(name = "ActNumeroImpresion")
    private Short actNumeroImpresion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActAnio")
    private int actAnio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ActUser")
    private String actUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActFch")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actFch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actFHR;
    @Size(max = 3)
    @Column(name = "ActEstado")
    private String actEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ActActaPdf")
    private String actActaPdf;
    @Size(max = 40)
    @Column(name = "ActParroquia")
    private String actParroquia;
    @Size(max = 100)
    @Column(name = "ActDescripcion")
    private String actDescripcion;
    @Size(max = 100)
    @Column(name = "ActTipoContrato")
    private String actTipoContrato;
    @Column(name = "ActFechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date actFechaIngreso;
    @Column(name = "ActFechaDe")
    @Temporal(TemporalType.DATE)
    private Date actFechaDe;
    @Column(name = "ActFechaSentencia")
    @Temporal(TemporalType.DATE)
    private Date actFechaSentencia;
    @Column(name = "ActTpjId")
    private Long actTpjId;
    @Column(name = "ActNumeroJucio")
    private Integer actNumeroJucio;
    @Column(name = "ActJuzgado")
    private Integer actJuzgado;
    @Column(name = "ActNotaria")
    private Integer actNotaria;
    @Column(name = "ActNumeroOficio")
    private Integer actNumeroOficio;
    @Size(max = 100)
    @Column(name = "ActObservacion")
    private String actObservacion;
    @Getter
    @Setter
    @Size(max = 2000)
    @Column(name = "ActPath")
    private String actPath;
    @Getter
    @Setter
    @Size(max = 512)
    @Column(name = "ActPathPdf")
    private String actPathPdf;
    @Getter
    @Setter
    @Size(max = 2000)
    @Column(name = "ActNombreArchivo")
    private String actNombreArchivo;
    
    @Getter
    @Setter
    @Size(max = 512)
    @Column(name = "ActNombreEscritura")
    private String actNombreEscritura;
    @Getter
    @Setter
    @Size(max = 2)
    @Column(name = "ActMigrado")
    private String actMigrado;
    @Getter
    @Setter
    @Column(name = "ActNumeroHojas")
    private Integer actNumeroHojas;
    @Size(max = 50)
    @Column(name = "ActTabla")
    private String actTabla;
    @Size(max = 50)
    @Column(name = "ActIdMigrado")
    private String actIdMigrado;
    @Size(max = 2147483647)
    @Column(name = "ActFirmaDigital")
    private String actFirmaDigital;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actNumero")
//    private List<ActaRazon> actaRazonList;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @JoinColumn(name = "RepNumero", referencedColumnName = "RepNumero")
    @ManyToOne(optional = false)
    private Repertorio repNumero;
    @JoinColumn(name = "TpaId", referencedColumnName = "TpaId")
    @ManyToOne(optional = false)
    private TipoActa tpaId;
    @JoinColumn(name = "TplId", referencedColumnName = "TplId")
    @ManyToOne
    private TipoLibro tplId;

    //no esta funcionando esta lista en la vista
    @Transient
    private List<Marginacion> listaMarginacion = new ArrayList();

    public List<Marginacion> getListaMarginacion() {
        return listaMarginacion;
    }

    public void setListaMarginacion(List<Marginacion> listaMarginacion) {
        this.listaMarginacion = listaMarginacion;
    }

    public Acta() {
    }

    public Acta(Long actNumero) {
        this.actNumero = actNumero;
    }

    public Acta(Long actNumero, BigInteger actInscripcion, int actTomo, String actBis, long actVolumen, long actFoja, int actAnio, String actUser, Date actFch, Date actFHR, String actActaPdf) {
        this.actNumero = actNumero;
        this.actInscripcion = actInscripcion;
        this.actTomo = actTomo;
        this.actBis = actBis;
        this.actVolumen = actVolumen;
        this.actFoja = actFoja;
        this.actAnio = actAnio;
        this.actUser = actUser;
        this.actFch = actFch;
        this.actFHR = actFHR;
        this.actActaPdf = actActaPdf;
    }

    public Long getActNumero() {
        return actNumero;
    }

    public void setActNumero(Long actNumero) {
        this.actNumero = actNumero;
    }

    public String getActActa() {
        return actActa;
    }

    public void setActActa(String actActa) {
        this.actActa = actActa;
    }

    public BigInteger getActInscripcion() {
        return actInscripcion;
    }

    public void setActInscripcion(BigInteger actInscripcion) {
        this.actInscripcion = actInscripcion;
    }

    public int getActTomo() {
        return actTomo;
    }

    public void setActTomo(int actTomo) {
        this.actTomo = actTomo;
    }

    public String getActBis() {
        return actBis;
    }

    public void setActBis(String actBis) {
        this.actBis = actBis;
    }

    public long getActVolumen() {
        return actVolumen;
    }

    public void setActVolumen(long actVolumen) {
        this.actVolumen = actVolumen;
    }

    public long getActFoja() {
        return actFoja;
    }

    public void setActFoja(long actFoja) {
        this.actFoja = actFoja;
    }

    public String getActCatastro() {
        return actCatastro;
    }

    public void setActCatastro(String actCatastro) {
        this.actCatastro = actCatastro;
    }

    public String getActPredio() {
        return actPredio;
    }

    public void setActPredio(String actPredio) {
        this.actPredio = actPredio;
    }

    public String getActDescripcion20() {
        return actDescripcion20;
    }

    public void setActDescripcion20(String actDescripcion20) {
        this.actDescripcion20 = actDescripcion20;
    }

    public String getActDescripcion19() {
        return actDescripcion19;
    }

    public void setActDescripcion19(String actDescripcion19) {
        this.actDescripcion19 = actDescripcion19;
    }

    public String getActDescripcion18() {
        return actDescripcion18;
    }

    public void setActDescripcion18(String actDescripcion18) {
        this.actDescripcion18 = actDescripcion18;
    }

    public String getActDescripcion17() {
        return actDescripcion17;
    }

    public void setActDescripcion17(String actDescripcion17) {
        this.actDescripcion17 = actDescripcion17;
    }

    public String getActDescripcion16() {
        return actDescripcion16;
    }

    public void setActDescripcion16(String actDescripcion16) {
        this.actDescripcion16 = actDescripcion16;
    }

    public String getActDescripcion15() {
        return actDescripcion15;
    }

    public void setActDescripcion15(String actDescripcion15) {
        this.actDescripcion15 = actDescripcion15;
    }

    public String getActDescripcion14() {
        return actDescripcion14;
    }

    public void setActDescripcion14(String actDescripcion14) {
        this.actDescripcion14 = actDescripcion14;
    }

    public String getActDescripcion13() {
        return actDescripcion13;
    }

    public void setActDescripcion13(String actDescripcion13) {
        this.actDescripcion13 = actDescripcion13;
    }

    public String getActDescripcion11() {
        return actDescripcion11;
    }

    public void setActDescripcion11(String actDescripcion11) {
        this.actDescripcion11 = actDescripcion11;
    }

    public String getActDescripcion10() {
        return actDescripcion10;
    }

    public void setActDescripcion10(String actDescripcion10) {
        this.actDescripcion10 = actDescripcion10;
    }

    public String getActDescripcion9() {
        return actDescripcion9;
    }

    public void setActDescripcion9(String actDescripcion9) {
        this.actDescripcion9 = actDescripcion9;
    }

    public String getActDescripcion8() {
        return actDescripcion8;
    }

    public void setActDescripcion8(String actDescripcion8) {
        this.actDescripcion8 = actDescripcion8;
    }

    public String getActDescripcion7() {
        return actDescripcion7;
    }

    public void setActDescripcion7(String actDescripcion7) {
        this.actDescripcion7 = actDescripcion7;
    }

    public String getActDescripcion6() {
        return actDescripcion6;
    }

    public void setActDescripcion6(String actDescripcion6) {
        this.actDescripcion6 = actDescripcion6;
    }

    public String getActDescripcion5() {
        return actDescripcion5;
    }

    public void setActDescripcion5(String actDescripcion5) {
        this.actDescripcion5 = actDescripcion5;
    }

    public String getActDescripcion4() {
        return actDescripcion4;
    }

    public void setActDescripcion4(String actDescripcion4) {
        this.actDescripcion4 = actDescripcion4;
    }

    public String getActDescripcion3() {
        return actDescripcion3;
    }

    public void setActDescripcion3(String actDescripcion3) {
        this.actDescripcion3 = actDescripcion3;
    }

    public String getActDescripcion2() {
        return actDescripcion2;
    }

    public void setActDescripcion2(String actDescripcion2) {
        this.actDescripcion2 = actDescripcion2;
    }

    public String getActDescripcion1() {
        return actDescripcion1;
    }

    public void setActDescripcion1(String actDescripcion1) {
        this.actDescripcion1 = actDescripcion1;
    }

    public Short getActNumeroImpresion() {
        return actNumeroImpresion;
    }

    public void setActNumeroImpresion(Short actNumeroImpresion) {
        this.actNumeroImpresion = actNumeroImpresion;
    }

    public int getActAnio() {
        return actAnio;
    }

    public void setActAnio(int actAnio) {
        this.actAnio = actAnio;
    }

    public String getActUser() {
        return actUser;
    }

    public void setActUser(String actUser) {
        this.actUser = actUser;
    }

    public Date getActFch() {
        return actFch;
    }

    public void setActFch(Date actFch) {
        this.actFch = actFch;
    }

    public Date getActFHR() {
        return actFHR;
    }

    public void setActFHR(Date actFHR) {
        this.actFHR = actFHR;
    }

    public String getActEstado() {
        return actEstado;
    }

    public void setActEstado(String actEstado) {
        this.actEstado = actEstado;
    }

    public String getActActaPdf() {
        return actActaPdf;
    }

    public void setActActaPdf(String actActaPdf) {
        this.actActaPdf = actActaPdf;
    }
    
     public String getActNombreArchivo() {
        return actNombreArchivo;
    }

    public void setActNombreArchivo(String actNombreArchivo) {
        this.actNombreArchivo = actNombreArchivo;
    }

    public String getActMigrado() {
        return actMigrado;
    }

    public void setActMigrado(String actMigrado) {
        this.actMigrado = actMigrado;
    }

    public Integer getActNumeroHojas() {
        return actNumeroHojas;
    }

    public void setActNumeroHojas(Integer actNumeroHojas) {
        this.actNumeroHojas = actNumeroHojas;
    }

    public String getActTabla() {
        return actTabla;
    }

    public void setActTabla(String actTabla) {
        this.actTabla = actTabla;
    }

    public String getActIdMigrado() {
        return actIdMigrado;
    }

    public void setActIdMigrado(String actIdMigrado) {
        this.actIdMigrado = actIdMigrado;
    }

   

    public TipoLibro getTplId() {
        return tplId;
    }

    public void setTplId(TipoLibro tplId) {
        this.tplId = tplId;
    }

    public Propiedad getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(Propiedad prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    public Repertorio getRepNumero() {
        return repNumero;
    }

    public void setRepNumero(Repertorio repNumero) {
        this.repNumero = repNumero;
    }

    public TipoActa getTpaId() {
        return tpaId;
    }

    public void setTpaId(TipoActa tpaId) {
        this.tpaId = tpaId;
    }

    public String getActParroquia() {
        return actParroquia;
    }

    public void setActParroquia(String actParroquia) {
        this.actParroquia = actParroquia;
    }

    public String getActDescripcion() {
        return actDescripcion;
    }

    public void setActDescripcion(String actDescripcion) {
        this.actDescripcion = actDescripcion;
    }

    public String getActTipoContrato() {
        return actTipoContrato;
    }

    public void setActTipoContrato(String actTipoContrato) {
        this.actTipoContrato = actTipoContrato;
    }

    public Date getActFechaIngreso() {
        return actFechaIngreso;
    }

    public void setActFechaIngreso(Date actFechaIngreso) {
        this.actFechaIngreso = actFechaIngreso;
    }

    public Date getActFechaDe() {
        return actFechaDe;
    }

    public void setActFechaDe(Date actFechaDe) {
        this.actFechaDe = actFechaDe;
    }

    public Date getActFechaSentencia() {
        return actFechaSentencia;
    }

    public void setActFechaSentencia(Date actFechaSentencia) {
        this.actFechaSentencia = actFechaSentencia;
    }

    public Long getActTpjId() {
        return actTpjId;
    }

    public void setActTpjId(Long actTpjId) {
        this.actTpjId = actTpjId;
    }

    public Integer getActNumeroJucio() {
        return actNumeroJucio;
    }

    public void setActNumeroJucio(Integer actNumeroJucio) {
        this.actNumeroJucio = actNumeroJucio;
    }

    public Integer getActJuzgado() {
        return actJuzgado;
    }

    public void setActJuzgado(Integer actJuzgado) {
        this.actJuzgado = actJuzgado;
    }

    public Integer getActNotaria() {
        return actNotaria;
    }

    public void setActNotaria(Integer actNotaria) {
        this.actNotaria = actNotaria;
    }

    public Integer getActNumeroOficio() {
        return actNumeroOficio;
    }

    public void setActNumeroOficio(Integer actNumeroOficio) {
        this.actNumeroOficio = actNumeroOficio;
    }

    public String getActObservacion() {
        return actObservacion;
    }

    public void setActObservacion(String actObservacion) {
        this.actObservacion = actObservacion;
    }

    public String getActFirmaDigital() {
        return actFirmaDigital;
    }

    public void setActFirmaDigital(String actFirmaDigital) {
        this.actFirmaDigital = actFirmaDigital;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actNumero != null ? actNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acta)) {
            return false;
        }
        Acta other = (Acta) object;
        if ((this.actNumero == null && other.actNumero != null) || (this.actNumero != null && !this.actNumero.equals(other.actNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Acta[ actNumero=" + actNumero + " ]";
    }

//    @XmlTransient
//    public List<ActaRazon> getActaRazonList() {
//        return actaRazonList;
//    }
//
//    public void setActaRazonList(List<ActaRazon> actaRazonList) {
//        this.actaRazonList = actaRazonList;
//    }
}
