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
import java.util.Collection;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @author WILSON
 */
@Entity
@Table(name = "Tramite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tramite.findAll", query = "SELECT t FROM Tramite t")
    , @NamedQuery(name = "Tramite.buscarPorTraNumero", query = "SELECT t FROM Tramite t WHERE t.traNumero = :traNumero")
    , @NamedQuery(name = "Tramite.buscarPorTraDescripcion", query = "SELECT t FROM Tramite t WHERE t.traDescripcion = :traDescripcion")
    , @NamedQuery(name = "Tramite.buscarPorTraUnidadTiempo", query = "SELECT t FROM Tramite t WHERE t.traUnidadTiempo = :traUnidadTiempo")
    , @NamedQuery(name = "Tramite.buscarPorTraTiempo", query = "SELECT t FROM Tramite t WHERE t.traTiempo = :traTiempo")
    , @NamedQuery(name = "Tramite.buscarPorTraTipo", query = "SELECT t FROM Tramite t WHERE t.traTipo = :traTipo")
    , @NamedQuery(name = "Tramite.buscarPorTraUser", query = "SELECT t FROM Tramite t WHERE t.traUser = :traUser")
    , @NamedQuery(name = "Tramite.buscarPorTraFHR", query = "SELECT t FROM Tramite t WHERE t.traFHR = :traFHR")
    , @NamedQuery(name = "Tramite.buscarPorTraClase", query = "SELECT t FROM Tramite t WHERE t.traClase = :traClase")
    , @NamedQuery(name = "Tramite.buscarPorTraNotaria", query = "SELECT t FROM Tramite t WHERE t.traNotaria = :traNotaria")
    , @NamedQuery(name = "Tramite.buscarPorTraParId", query = "SELECT t FROM Tramite t WHERE t.traParId = :traParId")
    , @NamedQuery(name = "Tramite.buscarPorTraParNombre", query = "SELECT t FROM Tramite t WHERE t.traParNombre = :traParNombre")
    , @NamedQuery(name = "Tramite.buscarPorTraJucio", query = "SELECT t FROM Tramite t WHERE t.traJucio = :traJucio")
    , @NamedQuery(name = "Tramite.buscarPorTraNumeroJusgado", query = "SELECT t FROM Tramite t WHERE t.traNumeroJusgado = :traNumeroJusgado")
    , @NamedQuery(name = "Tramite.buscarPorTraRecuValor", query = "SELECT t FROM Tramite t WHERE t.traRecuValor = :traRecuValor")
    , @NamedQuery(name = "Tramite.buscarPorTraCantidadTramite", query = "SELECT t FROM Tramite t WHERE t.traCantidadTramite = :traCantidadTramite")
    , @NamedQuery(name = "Tramite.buscarPorTraEstado", query = "SELECT t FROM Tramite t WHERE t.traEstado = :traEstado")
    , @NamedQuery(name = "Tramite.buscarPorTraEstadoVarios", query = "SELECT t FROM Tramite t WHERE t.traEstadoRegistro = 'RZ' AND t.traEstado IN ('INS','INP','INC','IND','INA')")
    , @NamedQuery(name = "Tramite.buscarPorTraFechaRecepcion", query = "SELECT t FROM Tramite t WHERE t.traFechaRecepcion = :traFechaRecepcion")
    , @NamedQuery(name = "Tramite.buscarPorTraFechaEntrega", query = "SELECT t FROM Tramite t WHERE t.traFechaEntrega = :traFechaEntrega")
    , @NamedQuery(name = "Tramite.buscarPorTraNumeroReImpresion", query = "SELECT t FROM Tramite t WHERE t.traNumeroReImpresion = :traNumeroReImpresion")
    , @NamedQuery(name = "Tramite.buscarPorTraEstadoRegistro", query = "SELECT t FROM Tramite t WHERE t.traEstadoRegistro = :traEstadoRegistro")
    , @NamedQuery(name = "Tramite.buscarPorTraModelo", query = "SELECT t FROM Tramite t WHERE t.traModelo = :traModelo")
    , @NamedQuery(name = "Tramite.buscarPorTraNumeroReIngreso", query = "SELECT t FROM Tramite t WHERE t.traNumeroReIngreso = :traNumeroReIngreso")
    , @NamedQuery(name = "Tramite.buscarPorTraFechaReIngreso", query = "SELECT t FROM Tramite t WHERE t.traFechaReIngreso = :traFechaReIngreso")
    , @NamedQuery(name = "Tramite.buscarPorTraPerIdentificacion", query = "SELECT t FROM Tramite t WHERE t.traPerIdentificacion = :traPerIdentificacion")
    , @NamedQuery(name = "Tramite.buscarPorTraPerNombre", query = "SELECT t FROM Tramite t WHERE t.traPerNombre = :traPerNombre")
    , @NamedQuery(name = "Tramite.buscarPorTraPerApellidoPaterno", query = "SELECT t FROM Tramite t WHERE t.traPerApellidoPaterno = :traPerApellidoPaterno")
    , @NamedQuery(name = "Tramite.buscarPorTraPerApellidoMaterno", query = "SELECT t FROM Tramite t WHERE t.traPerApellidoMaterno = :traPerApellidoMaterno")
    , @NamedQuery(name = "Tramite.buscarPorTraEstadoYporTraEstadoRegistro", query = "SELECT t FROM Tramite t WHERE t.traEstado=:traEstado AND t.traEstadoRegistro=:traEstadoRegistro")
    , @NamedQuery(name = "Tramite.buscarPorTraEstadoYporTraEstadoRegistroJoinCaja", query = "SELECT t,f FROM Tramite t INNER JOIN Factura f on t.traNumero=f.facTraNumero")
    , @NamedQuery(name = "Tramite.findByTraFinal", query = "SELECT t FROM Tramite t WHERE t.traFinal = :traFinal")
    , @NamedQuery(name = "Tramite.findByTraInicial", query = "SELECT t FROM Tramite t WHERE t.traInicial = :traInicial")
})
public class Tramite implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TraTiempo")
    private Integer traTiempo;
    @Column(name = "TraFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar traFHR;
    @Column(name = "TraNotaria")
    private Short traNotaria;
    @Column(name = "TraJucio")
    private Short traJucio;
    @Column(name = "TraNumeroJusgado")
    private Short traNumeroJusgado;
    @Column(name = "TraCantidadTramite")
    private Short traCantidadTramite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraFechaRecepcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar traFechaRecepcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraFechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar traFechaEntrega;
    @Column(name = "TraNumeroReImpresion")
    private Short traNumeroReImpresion;
    @Column(name = "TraFechaReIngreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar traFechaReIngreso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "traNumero")
    private Collection<TramiteValor> tramiteValorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "traNumero")
    private List<TramiteEntrega> tramiteEntregaList;

    public static final String LISTAR_TODOS = "Tramite.findAll";
    public static final String BUSCAR_POR_NUMERO_TRAMITE = "Tramite.buscarPorTraNumero";
    public static final String BUSCAR_POR_USUARIO = "Tramite.buscarPorTraUser";
    public static final String BUSCAR_POR_ESTADO_Y_ESTADO_REGISTRO = "Tramite.buscarPorTraEstadoYporTraEstadoRegistro";
    public static final String BUSCAR_POR_ESTADO_Y_ESTADO_REGISTRO_JOIN_CAJA = "Tramite.buscarPorTraEstadoYporTraEstadoRegistroJoinCaja";
    private static final long serialVersionUID = 6242826787465376056L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraNumero")
    private Long traNumero;
    @Basic(optional = false)
    @NotNull
    @Size(max = 40)
    @Column(name = "TraDescripcion")
    private String traDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraUnidadTiempo")
    private String traUnidadTiempo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TraTipo")
    private String traTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TraUser")
    private String traUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraClase")
    private String traClase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraParId")
    private Long traParId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TraParNombre")
    private String traParNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TraRecuValor")
    private short traRecuValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TraEstado")
    private String traEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "TraEstadoRegistro")
    private String traEstadoRegistro;
    @Basic(optional = false)
    @NotNull
    @Size(max = 1)
    @Column(name = "TraModelo")
    private String traModelo;
    @Column(name = "TraNumeroReIngreso")
    private Short traNumeroReIngreso;
    @Size(max = 20)
    @Column(name = "TraPerIdentificacion")
    private String traPerIdentificacion;
    @Size(max = 100)
    @Column(name = "TraPerNombre")
    private String traPerNombre;
    @Size(max = 60)
    @Column(name = "TraPerApellidoPaterno")
    private String traPerApellidoPaterno;
    @Size(max = 60)
    @Column(name = "TraPerApellidoMaterno")
    private String traPerApellidoMaterno;
    @Column(name = "TraFinal")
    private Integer traFinal;
    @Column(name = "TraInicial")
    private Integer traInicial;
    @Column(name = "TraNumeroContrato")
    private Short traNumeroContrato;
    @Size(max = 2)
    @Column(name = "TraMigrado")
    private String traMigrado;

    @Transient
    private String cajaNombre;
    @Getter
    @Setter
    @Transient
    private Factura factura;

    @Column(name = "TraRtd")
    private String traRtd;

    @JoinTable(name = "CertificadoTramite", joinColumns = {
        @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")}, inverseJoinColumns = {
        @JoinColumn(name = "CerNumero", referencedColumnName = "CerNumero")
        , @JoinColumn(name = "CerSecuencial", referencedColumnName = "CerSecuencial")})
    @ManyToMany
    private List<Certificado> certificadoList;

    public Tramite() {
    }

    public Tramite(Long traNumero) {
        this.traNumero = traNumero;
    }

    public Tramite(Long traNumero, String traDescripcion, String traUnidadTiempo, Integer traTiempo, String traTipo, String traUser, Calendar traFHR, String traClase, short traNotaria, Long traParId, String traParNombre, short traJucio, short traNumeroJusgado, short traRecuValor, short traCantidadTramite, String traEstado, Calendar traFechaRecepcion, Calendar traFechaEntrega, short traNumeroReImpresion, String traEstadoRegistro, String traModelo, Short traNumeroContrato) {
        this.traNumero = traNumero;
        this.traDescripcion = traDescripcion;
        this.traUnidadTiempo = traUnidadTiempo;
        this.traTiempo = traTiempo;
        this.traTipo = traTipo;
        this.traUser = traUser;
        this.traFHR = traFHR;
        this.traClase = traClase;
        this.traNotaria = traNotaria;
        this.traParId = traParId;
        this.traParNombre = traParNombre;
        this.traJucio = traJucio;
        this.traNumeroJusgado = traNumeroJusgado;
        this.traRecuValor = traRecuValor;
        this.traCantidadTramite = traCantidadTramite;
        this.traEstado = traEstado;
        this.traFechaRecepcion = traFechaRecepcion;
        this.traFechaEntrega = traFechaEntrega;
        this.traNumeroReImpresion = traNumeroReImpresion;
        this.traEstadoRegistro = traEstadoRegistro;
        this.traModelo = traModelo;
        this.traNumeroContrato = traNumeroContrato;
    }

    public Long getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Long traNumero) {
        this.traNumero = traNumero;
    }

    public String getTraDescripcion() {
        return traDescripcion;
    }

    public void setTraDescripcion(String traDescripcion) {
        this.traDescripcion = traDescripcion;
    }

    public String getTraUnidadTiempo() {
        return traUnidadTiempo;
    }

    public void setTraUnidadTiempo(String traUnidadTiempo) {
        this.traUnidadTiempo = traUnidadTiempo;
    }

    public String getTraTipo() {
        return traTipo;
    }

    public void setTraTipo(String traTipo) {
        this.traTipo = traTipo;
    }

    public String getTraUser() {
        return traUser;
    }

    public void setTraUser(String traUser) {
        this.traUser = traUser;
    }

    public String getTraClase() {
        return traClase;
    }

    public void setTraClase(String traClase) {
        this.traClase = traClase;
    }

    public Long getTraParId() {
        return traParId;
    }

    public void setTraParId(Long traParId) {
        this.traParId = traParId;
    }

    public String getTraParNombre() {
        return traParNombre;
    }

    public void setTraParNombre(String traParNombre) {
        this.traParNombre = traParNombre;
    }

    public short getTraRecuValor() {
        return traRecuValor;
    }

    public void setTraRecuValor(short traRecuValor) {
        this.traRecuValor = traRecuValor;
    }

    public String getTraEstado() {
        return traEstado;
    }

    public void setTraEstado(String traEstado) {
        this.traEstado = traEstado;
    }

    public String getTraEstadoRegistro() {
        return traEstadoRegistro;
    }

    public void setTraEstadoRegistro(String traEstadoRegistro) {
        this.traEstadoRegistro = traEstadoRegistro;
    }

    public String getTraModelo() {
        return traModelo;
    }

    public void setTraModelo(String traModelo) {
        this.traModelo = traModelo;
    }

    public Short getTraNumeroReIngreso() {
        return traNumeroReIngreso;
    }

    public void setTraNumeroReIngreso(Short traNumeroReIngreso) {
        this.traNumeroReIngreso = traNumeroReIngreso;
    }

    public String getTraPerIdentificacion() {
        return traPerIdentificacion;
    }

    public void setTraPerIdentificacion(String traPerIdentificacion) {
        this.traPerIdentificacion = traPerIdentificacion;
    }

    public String getTraPerNombre() {
        return traPerNombre;
    }

    public void setTraPerNombre(String traPerNombre) {
        this.traPerNombre = traPerNombre;
    }

    public String getTraPerApellidoPaterno() {
        return traPerApellidoPaterno;
    }

    public void setTraPerApellidoPaterno(String traPerApellidoPaterno) {
        this.traPerApellidoPaterno = traPerApellidoPaterno;
    }

    public String getTraPerApellidoMaterno() {
        return traPerApellidoMaterno;
    }

    public void setTraPerApellidoMaterno(String traPerApellidoMaterno) {
        this.traPerApellidoMaterno = traPerApellidoMaterno;
    }

    public String getCajaNombre() {
        return cajaNombre;
    }

    public void setCajaNombre(String cajaNombre) {
        this.cajaNombre = cajaNombre;
    }

    public Short getTraNumeroContrato() {
        return traNumeroContrato;
    }

    public void setTraNumeroContrato(Short traNumeroContrato) {
        this.traNumeroContrato = traNumeroContrato;
    }

    public String getTraMigrado() {
        return traMigrado;
    }

    public void setTraMigrado(String traMigrado) {
        this.traMigrado = traMigrado;
    }

    @XmlTransient
    public List<TramiteEntrega> getTramiteEntregaList() {
        return tramiteEntregaList;
    }

    public void setTramiteEntregaList(List<TramiteEntrega> tramiteEntregaList) {
        this.tramiteEntregaList = tramiteEntregaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (traNumero != null ? traNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tramite)) {
            return false;
        }
        Tramite other = (Tramite) object;
        if ((this.traNumero == null && other.traNumero != null) || (this.traNumero != null && !this.traNumero.equals(other.traNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.Tramite[ traNumero=" + traNumero + " ]";
    }

    public Integer getTraTiempo() {
        return traTiempo;
    }

    public void setTraTiempo(Integer traTiempo) {
        this.traTiempo = traTiempo;
    }

    public Calendar getTraFHR() {
        return traFHR;
    }

    public void setTraFHR(Calendar traFHR) {
        this.traFHR = traFHR;
    }

    public Short getTraNotaria() {
        return traNotaria;
    }

    public void setTraNotaria(Short traNotaria) {
        this.traNotaria = traNotaria;
    }

    public Short getTraJucio() {
        return traJucio;
    }

    public void setTraJucio(Short traJucio) {
        this.traJucio = traJucio;
    }

    public Short getTraNumeroJusgado() {
        return traNumeroJusgado;
    }

    public void setTraNumeroJusgado(Short traNumeroJusgado) {
        this.traNumeroJusgado = traNumeroJusgado;
    }

    public Short getTraCantidadTramite() {
        return traCantidadTramite;
    }

    public void setTraCantidadTramite(Short traCantidadTramite) {
        this.traCantidadTramite = traCantidadTramite;
    }

    public Calendar getTraFechaRecepcion() {
        return traFechaRecepcion;
    }

    public void setTraFechaRecepcion(Calendar traFechaRecepcion) {
        this.traFechaRecepcion = traFechaRecepcion;
    }

    public Calendar getTraFechaEntrega() {
        return traFechaEntrega;
    }

    public void setTraFechaEntrega(Calendar traFechaEntrega) {
        this.traFechaEntrega = traFechaEntrega;
    }

    public Short getTraNumeroReImpresion() {
        return traNumeroReImpresion;
    }

    public void setTraNumeroReImpresion(Short traNumeroReImpresion) {
        this.traNumeroReImpresion = traNumeroReImpresion;
    }

    public Calendar getTraFechaReIngreso() {
        return traFechaReIngreso;
    }

    public Integer getTraFinal() {
        return traFinal;
    }

    public void setTraFinal(Integer traFinal) {
        this.traFinal = traFinal;
    }

    public Integer getTraInicial() {
        return traInicial;
    }

    public void setTraInicial(Integer traInicial) {
        this.traInicial = traInicial;
    }

    public void setTraFechaReIngreso(Calendar traFechaReIngreso) {
        this.traFechaReIngreso = traFechaReIngreso;
    }

    @XmlTransient
    public List<Certificado> getCertificadoList() {
        return certificadoList;
    }

    public void setCertificadoList(List<Certificado> certificadoList) {
        this.certificadoList = certificadoList;
    }

    @XmlTransient
    public Collection<TramiteValor> getTramiteValorCollection() {
        return tramiteValorCollection;
    }

    public void setTramiteValorCollection(Collection<TramiteValor> tramiteValorCollection) {
        this.tramiteValorCollection = tramiteValorCollection;
    }

    public String getTraRtd() {
        return traRtd;
    }

    public void setTraRtd(String traRtd) {
        this.traRtd = traRtd;
    }

}
