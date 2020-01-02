/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DJimenez
 */
@Entity
@Table(name = "cercontrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cercontrato.findAll", query = "SELECT c FROM Cercontrato c")
    , @NamedQuery(name = "Cercontrato.findByCerId", query = "SELECT c FROM Cercontrato c WHERE c.cerId = :cerId")
    , @NamedQuery(name = "Cercontrato.findByCerProforma", query = "SELECT c FROM Cercontrato c WHERE c.cerProforma = :cerProforma")
    , @NamedQuery(name = "Cercontrato.findByCerFechacel", query = "SELECT c FROM Cercontrato c WHERE c.cerFechacel = :cerFechacel")
    , @NamedQuery(name = "Cercontrato.findByCerVentotal", query = "SELECT c FROM Cercontrato c WHERE c.cerVentotal = :cerVentotal")
    , @NamedQuery(name = "Cercontrato.findByCerCrva", query = "SELECT c FROM Cercontrato c WHERE c.cerCrva = :cerCrva")
    , @NamedQuery(name = "Cercontrato.findByCerCodigocat", query = "SELECT c FROM Cercontrato c WHERE c.cerCodigocat = :cerCodigocat")
    , @NamedQuery(name = "Cercontrato.findByCerNumero", query = "SELECT c FROM Cercontrato c WHERE c.cerNumero = :cerNumero")
    , @NamedQuery(name = "Cercontrato.findByCerSri", query = "SELECT c FROM Cercontrato c WHERE c.cerSri = :cerSri")
    , @NamedQuery(name = "Cercontrato.findByCerOtorgante", query = "SELECT c FROM Cercontrato c WHERE c.cerOtorgante = :cerOtorgante")
    , @NamedQuery(name = "Cercontrato.findByCerCi", query = "SELECT c FROM Cercontrato c WHERE c.cerCi = :cerCi")
    , @NamedQuery(name = "Cercontrato.findByCerFechains", query = "SELECT c FROM Cercontrato c WHERE c.cerFechains = :cerFechains")
    , @NamedQuery(name = "Cercontrato.findByCerImprime", query = "SELECT c FROM Cercontrato c WHERE c.cerImprime = :cerImprime")
    , @NamedQuery(name = "Cercontrato.findByCerTipobien", query = "SELECT c FROM Cercontrato c WHERE c.cerTipobien = :cerTipobien")
    , @NamedQuery(name = "Cercontrato.findByCerTipoph", query = "SELECT c FROM Cercontrato c WHERE c.cerTipoph = :cerTipoph")
    , @NamedQuery(name = "Cercontrato.findByCerAutorizado", query = "SELECT c FROM Cercontrato c WHERE c.cerAutorizado = :cerAutorizado")
    , @NamedQuery(name = "Cercontrato.findByCerRevisor", query = "SELECT c FROM Cercontrato c WHERE c.cerRevisor = :cerRevisor")
    , @NamedQuery(name = "Cercontrato.findByCerFecha", query = "SELECT c FROM Cercontrato c WHERE c.cerFecha = :cerFecha")
    , @NamedQuery(name = "Cercontrato.findByCerAsesor", query = "SELECT c FROM Cercontrato c WHERE c.cerAsesor = :cerAsesor")
    , @NamedQuery(name = "Cercontrato.findByCerRevisorins", query = "SELECT c FROM Cercontrato c WHERE c.cerRevisorins = :cerRevisorins")
    , @NamedQuery(name = "Cercontrato.findByCerCopiacert", query = "SELECT c FROM Cercontrato c WHERE c.cerCopiacert = :cerCopiacert")
    , @NamedQuery(name = "Cercontrato.findByCerPrelacion", query = "SELECT c FROM Cercontrato c WHERE c.cerPrelacion = :cerPrelacion")
    , @NamedQuery(name = "Cercontrato.findByCerTipodcto", query = "SELECT c FROM Cercontrato c WHERE c.cerTipodcto = :cerTipodcto")
    , @NamedQuery(name = "Cercontrato.findByCerModo", query = "SELECT c FROM Cercontrato c WHERE c.cerModo = :cerModo")})
public class Cercontrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cer_id")
    private Integer cerId;
    @Column(name = "cer_proforma")
    private Integer cerProforma;
    @Column(name = "cer_fechacel")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cerFechacel;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_observa")
    private String cerObserva;
    @Column(name = "cer_ventotal")
    private Boolean cerVentotal;
    @Column(name = "cer_crva")
    private Integer cerCrva;
    @Size(max = 30)
    @Column(name = "cer_codigocat")
    private String cerCodigocat;
    @Column(name = "cer_numero")
    private Integer cerNumero;
    @Column(name = "cer_sri")
    private Boolean cerSri;
    @Size(max = 50)
    @Column(name = "cer_otorgante")
    private String cerOtorgante;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_peticionario")
    private String cerPeticionario;
    @Size(max = 50)
    @Column(name = "cer_ci")
    private String cerCi;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_adquisicion")
    private String cerAdquisicion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_historial")
    private String cerHistorial;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_propietario")
    private String cerPropietario;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_descripcion")
    private String cerDescripcion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_gravamenes")
    private String cerGravamenes;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_gravamenes2")
    private String cerGravamenes2;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_da")
    private String cerDa;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_dv")
    private String cerDv;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_testamento")
    private String cerTestamento;
    @Column(name = "cer_fechains")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cerFechains;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_imputil")
    private String cerImputil;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_detalle")
    private String cerDetalle;
    @Column(name = "cer_imprime")
    private Boolean cerImprime;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_antecede")
    private String cerAntecede;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_ujudicial")
    private String cerUjudicial;
    @Size(max = 15)
    @Column(name = "cer_tipobien")
    private String cerTipobien;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_juez")
    private String cerJuez;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_autoprov")
    private String cerAutoprov;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_tipojuicio")
    private String cerTipojuicio;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_numjuicio")
    private String cerNumjuicio;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_accion")
    private String cerAccion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_dispjudicial")
    private String cerDispjudicial;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_propiet")
    private String cerPropiet;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_gravamen")
    private String cerGravamen;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_embargo")
    private String cerEmbargo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_demanda")
    private String cerDemanda;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_bienes")
    private String cerBienes;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_detalleph")
    private String cerDetalleph;
    @Size(max = 15)
    @Column(name = "cer_tipoph")
    private String cerTipoph;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_gravamen2")
    private String cerGravamen2;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_loriins")
    private String cerLoriins;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_referencia")
    private String cerReferencia;
    @Size(max = 50)
    @Column(name = "cer_autorizado")
    private String cerAutorizado;
    @Size(max = 50)
    @Column(name = "cer_revisor")
    private String cerRevisor;
    @Size(max = 100)
    @Column(name = "cer_fecha")
    private String cerFecha;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_otros")
    private String cerOtros;
    @Size(max = 50)
    @Column(name = "cer_asesor")
    private String cerAsesor;
    @Size(max = 50)
    @Column(name = "cer_revisorins")
    private String cerRevisorins;
    @Size(max = 50)
    @Column(name = "cer_copiacert")
    private String cerCopiacert;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cer_objeto")
    private String cerObjeto;
    @Size(max = 1)
    @Column(name = "cer_prelacion")
    private String cerPrelacion;
    @Size(max = 50)
    @Column(name = "cer_tipodcto")
    private String cerTipodcto;
    @Size(max = 50)
    @Column(name = "cer_modo")
    private String cerModo;

    public Cercontrato() {
    }

    public Cercontrato(Integer cerId) {
        this.cerId = cerId;
    }

    public Integer getCerId() {
        return cerId;
    }

    public void setCerId(Integer cerId) {
        this.cerId = cerId;
    }

    public Integer getCerProforma() {
        return cerProforma;
    }

    public void setCerProforma(Integer cerProforma) {
        this.cerProforma = cerProforma;
    }

    public Date getCerFechacel() {
        return cerFechacel;
    }

    public void setCerFechacel(Date cerFechacel) {
        this.cerFechacel = cerFechacel;
    }

    public String getCerObserva() {
        return cerObserva;
    }

    public void setCerObserva(String cerObserva) {
        this.cerObserva = cerObserva;
    }

    public Boolean getCerVentotal() {
        return cerVentotal;
    }

    public void setCerVentotal(Boolean cerVentotal) {
        this.cerVentotal = cerVentotal;
    }

    public Integer getCerCrva() {
        return cerCrva;
    }

    public void setCerCrva(Integer cerCrva) {
        this.cerCrva = cerCrva;
    }

    public String getCerCodigocat() {
        return cerCodigocat;
    }

    public void setCerCodigocat(String cerCodigocat) {
        this.cerCodigocat = cerCodigocat;
    }

    public Integer getCerNumero() {
        return cerNumero;
    }

    public void setCerNumero(Integer cerNumero) {
        this.cerNumero = cerNumero;
    }

    public Boolean getCerSri() {
        return cerSri;
    }

    public void setCerSri(Boolean cerSri) {
        this.cerSri = cerSri;
    }

    public String getCerOtorgante() {
        return cerOtorgante;
    }

    public void setCerOtorgante(String cerOtorgante) {
        this.cerOtorgante = cerOtorgante;
    }

    public String getCerPeticionario() {
        return cerPeticionario;
    }

    public void setCerPeticionario(String cerPeticionario) {
        this.cerPeticionario = cerPeticionario;
    }

    public String getCerCi() {
        return cerCi;
    }

    public void setCerCi(String cerCi) {
        this.cerCi = cerCi;
    }

    public String getCerAdquisicion() {
        return cerAdquisicion;
    }

    public void setCerAdquisicion(String cerAdquisicion) {
        this.cerAdquisicion = cerAdquisicion;
    }

    public String getCerHistorial() {
        return cerHistorial;
    }

    public void setCerHistorial(String cerHistorial) {
        this.cerHistorial = cerHistorial;
    }

    public String getCerPropietario() {
        return cerPropietario;
    }

    public void setCerPropietario(String cerPropietario) {
        this.cerPropietario = cerPropietario;
    }

    public String getCerDescripcion() {
        return cerDescripcion;
    }

    public void setCerDescripcion(String cerDescripcion) {
        this.cerDescripcion = cerDescripcion;
    }

    public String getCerGravamenes() {
        return cerGravamenes;
    }

    public void setCerGravamenes(String cerGravamenes) {
        this.cerGravamenes = cerGravamenes;
    }

    public String getCerGravamenes2() {
        return cerGravamenes2;
    }

    public void setCerGravamenes2(String cerGravamenes2) {
        this.cerGravamenes2 = cerGravamenes2;
    }

    public String getCerDa() {
        return cerDa;
    }

    public void setCerDa(String cerDa) {
        this.cerDa = cerDa;
    }

    public String getCerDv() {
        return cerDv;
    }

    public void setCerDv(String cerDv) {
        this.cerDv = cerDv;
    }

    public String getCerTestamento() {
        return cerTestamento;
    }

    public void setCerTestamento(String cerTestamento) {
        this.cerTestamento = cerTestamento;
    }

    public Date getCerFechains() {
        return cerFechains;
    }

    public void setCerFechains(Date cerFechains) {
        this.cerFechains = cerFechains;
    }

    public String getCerImputil() {
        return cerImputil;
    }

    public void setCerImputil(String cerImputil) {
        this.cerImputil = cerImputil;
    }

    public String getCerDetalle() {
        return cerDetalle;
    }

    public void setCerDetalle(String cerDetalle) {
        this.cerDetalle = cerDetalle;
    }

    public Boolean getCerImprime() {
        return cerImprime;
    }

    public void setCerImprime(Boolean cerImprime) {
        this.cerImprime = cerImprime;
    }

    public String getCerAntecede() {
        return cerAntecede;
    }

    public void setCerAntecede(String cerAntecede) {
        this.cerAntecede = cerAntecede;
    }

    public String getCerUjudicial() {
        return cerUjudicial;
    }

    public void setCerUjudicial(String cerUjudicial) {
        this.cerUjudicial = cerUjudicial;
    }

    public String getCerTipobien() {
        return cerTipobien;
    }

    public void setCerTipobien(String cerTipobien) {
        this.cerTipobien = cerTipobien;
    }

    public String getCerJuez() {
        return cerJuez;
    }

    public void setCerJuez(String cerJuez) {
        this.cerJuez = cerJuez;
    }

    public String getCerAutoprov() {
        return cerAutoprov;
    }

    public void setCerAutoprov(String cerAutoprov) {
        this.cerAutoprov = cerAutoprov;
    }

    public String getCerTipojuicio() {
        return cerTipojuicio;
    }

    public void setCerTipojuicio(String cerTipojuicio) {
        this.cerTipojuicio = cerTipojuicio;
    }

    public String getCerNumjuicio() {
        return cerNumjuicio;
    }

    public void setCerNumjuicio(String cerNumjuicio) {
        this.cerNumjuicio = cerNumjuicio;
    }

    public String getCerAccion() {
        return cerAccion;
    }

    public void setCerAccion(String cerAccion) {
        this.cerAccion = cerAccion;
    }

    public String getCerDispjudicial() {
        return cerDispjudicial;
    }

    public void setCerDispjudicial(String cerDispjudicial) {
        this.cerDispjudicial = cerDispjudicial;
    }

    public String getCerPropiet() {
        return cerPropiet;
    }

    public void setCerPropiet(String cerPropiet) {
        this.cerPropiet = cerPropiet;
    }

    public String getCerGravamen() {
        return cerGravamen;
    }

    public void setCerGravamen(String cerGravamen) {
        this.cerGravamen = cerGravamen;
    }

    public String getCerEmbargo() {
        return cerEmbargo;
    }

    public void setCerEmbargo(String cerEmbargo) {
        this.cerEmbargo = cerEmbargo;
    }

    public String getCerDemanda() {
        return cerDemanda;
    }

    public void setCerDemanda(String cerDemanda) {
        this.cerDemanda = cerDemanda;
    }

    public String getCerBienes() {
        return cerBienes;
    }

    public void setCerBienes(String cerBienes) {
        this.cerBienes = cerBienes;
    }

    public String getCerDetalleph() {
        return cerDetalleph;
    }

    public void setCerDetalleph(String cerDetalleph) {
        this.cerDetalleph = cerDetalleph;
    }

    public String getCerTipoph() {
        return cerTipoph;
    }

    public void setCerTipoph(String cerTipoph) {
        this.cerTipoph = cerTipoph;
    }

    public String getCerGravamen2() {
        return cerGravamen2;
    }

    public void setCerGravamen2(String cerGravamen2) {
        this.cerGravamen2 = cerGravamen2;
    }

    public String getCerLoriins() {
        return cerLoriins;
    }

    public void setCerLoriins(String cerLoriins) {
        this.cerLoriins = cerLoriins;
    }

    public String getCerReferencia() {
        return cerReferencia;
    }

    public void setCerReferencia(String cerReferencia) {
        this.cerReferencia = cerReferencia;
    }

    public String getCerAutorizado() {
        return cerAutorizado;
    }

    public void setCerAutorizado(String cerAutorizado) {
        this.cerAutorizado = cerAutorizado;
    }

    public String getCerRevisor() {
        return cerRevisor;
    }

    public void setCerRevisor(String cerRevisor) {
        this.cerRevisor = cerRevisor;
    }

    public String getCerFecha() {
        return cerFecha;
    }

    public void setCerFecha(String cerFecha) {
        this.cerFecha = cerFecha;
    }

    public String getCerOtros() {
        return cerOtros;
    }

    public void setCerOtros(String cerOtros) {
        this.cerOtros = cerOtros;
    }

    public String getCerAsesor() {
        return cerAsesor;
    }

    public void setCerAsesor(String cerAsesor) {
        this.cerAsesor = cerAsesor;
    }

    public String getCerRevisorins() {
        return cerRevisorins;
    }

    public void setCerRevisorins(String cerRevisorins) {
        this.cerRevisorins = cerRevisorins;
    }

    public String getCerCopiacert() {
        return cerCopiacert;
    }

    public void setCerCopiacert(String cerCopiacert) {
        this.cerCopiacert = cerCopiacert;
    }

    public String getCerObjeto() {
        return cerObjeto;
    }

    public void setCerObjeto(String cerObjeto) {
        this.cerObjeto = cerObjeto;
    }

    public String getCerPrelacion() {
        return cerPrelacion;
    }

    public void setCerPrelacion(String cerPrelacion) {
        this.cerPrelacion = cerPrelacion;
    }

    public String getCerTipodcto() {
        return cerTipodcto;
    }

    public void setCerTipodcto(String cerTipodcto) {
        this.cerTipodcto = cerTipodcto;
    }

    public String getCerModo() {
        return cerModo;
    }

    public void setCerModo(String cerModo) {
        this.cerModo = cerModo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cerId != null ? cerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cercontrato)) {
            return false;
        }
        Cercontrato other = (Cercontrato) object;
        if ((this.cerId == null && other.cerId != null) || (this.cerId != null && !this.cerId.equals(other.cerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cercontrato[ cerId=" + cerId + " ]";
    }
    
}
