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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "TramiteAccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteAccion.findAll", query = "SELECT t FROM TramiteAccion t")
    , @NamedQuery(name = "TramiteAccion.findByTmaId", query = "SELECT t FROM TramiteAccion t WHERE t.tmaId = :tmaId")
    , @NamedQuery(name = "TramiteAccion.findByTmaObservacion", query = "SELECT t FROM TramiteAccion t WHERE t.tmaObservacion = :tmaObservacion")
    , @NamedQuery(name = "TramiteAccion.findByTmaUser", query = "SELECT t FROM TramiteAccion t WHERE t.tmaUser = :tmaUser")
    , @NamedQuery(name = "TramiteAccion.findByTmaFHR", query = "SELECT t FROM TramiteAccion t WHERE t.tmaFHR = :tmaFHR")
    , @NamedQuery(name = "TramiteAccion.findByTmaNumeroDocumento", query = "SELECT t FROM TramiteAccion t WHERE t.tmaNumeroDocumento = :tmaNumeroDocumento")
    , @NamedQuery(name = "TramiteAccion.findByTmaEstadoProceso", query = "SELECT t FROM TramiteAccion t WHERE t.tmaEstadoProceso = :tmaEstadoProceso")
    , @NamedQuery(name = "TramiteAccion.findByTmaEstadoRegistro", query = "SELECT t FROM TramiteAccion t WHERE t.tmaEstadoRegistro = :tmaEstadoRegistro")
    , @NamedQuery(name = "TramiteAccion.findByTmaUserAsg", query = "SELECT t FROM TramiteAccion t WHERE t.tmaUserAsg = :tmaUserAsg")
    , @NamedQuery(name = "TramiteAccion.findByTraNumero", query = "SELECT t FROM TramiteAccion t WHERE t.traNumero = :traNumero")
    , @NamedQuery(name = "TramiteAccion.findByTraNumeroFiltrado", query = "SELECT t FROM TramiteAccion t WHERE t.traNumero = :traNumero AND t.tmaUserAsg IS NOT NULL AND t.tmaEstadoProceso = :tmaEstadoProceso ORDER BY t.tmaFHR DESC")
    , @NamedQuery(name = "TramiteAccion.findByTraNumeroFiltradoNull", query = "SELECT t FROM TramiteAccion t WHERE t.traNumero = :traNumero AND t.tmaUserAsg IS NULL AND t.tmaEstadoProceso = :tmaEstadoProceso ORDER BY t.tmaFHR DESC")
    , @NamedQuery(name = "TramiteAccion.findByTraNumeroListaFiltrado", query = "SELECT t FROM TramiteAccion t WHERE t.traNumero = :traNumero AND t.tmaEstadoProceso = :tmaEstadoProceso ORDER BY t.tmaFHR ASC")})

public class TramiteAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String OBTENER_POR_TRAMITE_FILTRADO = "TramiteAccion.findByTraNumeroFiltrado";
    public static final String OBTENER_POR_TRAMITE_FILTRADO_NULL = "TramiteAccion.findByTraNumeroFiltradoNull";
    public static final String LISTAR_POR_TRAMITE_FILTRADO = "TramiteAccion.findByTraNumeroListaFiltrado";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TmaId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long tmaId;
    @Size(max = 1000)
    @Column(name = "TmaObservacion")
    private String tmaObservacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TmaUser")
    private String tmaUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TmaFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tmaFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TmaNumeroDocumento")
    private String tmaNumeroDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TmaEstadoProceso")
    private String tmaEstadoProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TmaEstadoRegistro")
    private String tmaEstadoRegistro;
    @Size(max = 20)
    @Column(name = "TmaUserAsg")
    private String tmaUserAsg;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;

    public TramiteAccion() {
    }

    public TramiteAccion(Long tmaId) {
        this.tmaId = tmaId;
    }

    public TramiteAccion(Long tmaId, String tmaUser, Date tmaFHR, String tmaNumeroDocumento, String tmaEstadoProceso, String tmaEstadoRegistro) {
        this.tmaId = tmaId;
        this.tmaUser = tmaUser;
        this.tmaFHR = tmaFHR;
        this.tmaNumeroDocumento = tmaNumeroDocumento;
        this.tmaEstadoProceso = tmaEstadoProceso;
        this.tmaEstadoRegistro = tmaEstadoRegistro;
    }

    public Long getTmaId() {
        return tmaId;
    }

    public void setTmaId(Long tmaId) {
        this.tmaId = tmaId;
    }

    public String getTmaObservacion() {
        return tmaObservacion;
    }

    public void setTmaObservacion(String tmaObservacion) {
        this.tmaObservacion = tmaObservacion;
    }

    public String getTmaUser() {
        return tmaUser;
    }

    public void setTmaUser(String tmaUser) {
        this.tmaUser = tmaUser;
    }

    public Date getTmaFHR() {
        return tmaFHR;
    }

    public void setTmaFHR(Date tmaFHR) {
        this.tmaFHR = tmaFHR;
    }

    public String getTmaNumeroDocumento() {
        return tmaNumeroDocumento;
    }

    public void setTmaNumeroDocumento(String tmaNumeroDocumento) {
        this.tmaNumeroDocumento = tmaNumeroDocumento;
    }

    public String getTmaEstadoProceso() {
        return tmaEstadoProceso;
    }

    public void setTmaEstadoProceso(String tmaEstadoProceso) {
        this.tmaEstadoProceso = tmaEstadoProceso;
    }

    public String getTmaEstadoRegistro() {
        return tmaEstadoRegistro;
    }

    public void setTmaEstadoRegistro(String tmaEstadoRegistro) {
        this.tmaEstadoRegistro = tmaEstadoRegistro;
    }

     public String getTmaUserAsg() {
        return tmaUserAsg;
    }

    public void setTmaUserAsg(String tmaUserAsg) {
        this.tmaUserAsg = tmaUserAsg;
    }
    
    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmaId != null ? tmaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteAccion)) {
            return false;
        }
        TramiteAccion other = (TramiteAccion) object;
        if ((this.tmaId == null && other.tmaId != null) || (this.tmaId != null && !this.tmaId.equals(other.tmaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TramiteAccion[ tmaId=" + tmaId + " ]";
    }

}
