/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "TramiteUsuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteUsuario.buscarTodos", query = "SELECT t FROM TramiteUsuario t ")
    , @NamedQuery(name = "TramiteUsuario.buscarPorTusId", query = "SELECT t FROM TramiteUsuario t WHERE t.tusId = :tusId")
    , @NamedQuery(name = "TramiteUsuario.buscarPorTusEstado", query = "SELECT t FROM TramiteUsuario t WHERE t.traNumero.traEstado IN( 'PRO' ,'RVT') ")
    , @NamedQuery(name = "TramiteUsuario.buscarModelo", query = "SELECT t FROM TramiteUsuario t")
    , @NamedQuery(name = "TramiteUsuario.buscarPorTusUser", query = "SELECT t FROM TramiteUsuario t WHERE t.tusUser = :tusUser")
    , @NamedQuery(name = "TramiteUsuario.buscarPorTusFHR", query = "SELECT t FROM TramiteUsuario t WHERE t.tusFHR = :tusFHR")
    , @NamedQuery(name = "TramiteUsuario.buscarPorUsuario", query = " SELECT t FROM TramiteUsuario t WHERE t.usuId = :usuId and t.traNumero.traEstadoRegistro = 'RA' and t.traNumero.traEstado IN( 'PRO' ,'RVT')")
    , @NamedQuery(name = "TramiteUsuario.buscarPorUsuarioModelo", query = " FROM TramiteUsuario t WHERE t.usuId = :usuId and t.traNumero.traModelo='S'")
    , @NamedQuery(name = "TramiteUsuario.buscarPorModelo", query = " FROM TramiteUsuario t WHERE t.traNumero.traModelo='S'")
    , @NamedQuery(name = "TramiteUsuario.buscarPorTramite", query = " FROM TramiteUsuario t WHERE t.traNumero=:traNumero AND t.tusEstado ='A'")
    , @NamedQuery(name = "TramiteUsuario.buscarPorTramiteYUsuarioActivos", query = "SELECT t FROM TramiteUsuario t WHERE t.tusEstado ='A' AND t.traNumero =:traNumero AND t.usuId =:usuId ")
})
public class TramiteUsuario implements Serializable {

    private static final long serialVersionUID = 8068860849789856355L;
    public static final String BUSCAR_POR_USUARIO = "TramiteUsuario.buscarPorUsuario";
    public static final String BUSCAR_POR_USUARIO_MODELO = "TramiteUsuario.buscarPorUsuarioModelo";
    public static final String BUSCAR_TODOS = "TramiteUsuario.buscarPorTusEstado";
    public static final String BUSCAR_TODOS_MODELOS = "TramiteUsuario.buscarModelo";
    public static final String BUSCAR_POR_MODELO_TODOS = "TramiteUsuario.buscarPorModelo";
    public static final String BUSCAR_POR_TRAMITE = "TramiteUsuario.buscarPorTramite";
    public static final String BUSCAR_POR_TRAMITE_USUARIO_ACTIVOS = "TramiteUsuario.buscarPorTramiteYUsuarioActivos";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TusId", nullable = false)
    private BigDecimal tusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TusEstado")
    private String tusEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TusUser")
    private String tusUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TusFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar tusFHR;
    @Size(max = 200)
    @Column(name = "TusEstadoDetalle")
    private String tusEstadoDetalle;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public TramiteUsuario() {
    }

    public TramiteUsuario(BigDecimal tusId) {
        this.tusId = tusId;
    }

    public TramiteUsuario(BigDecimal tusId, String tusEstado, String tusUser, Calendar tusFHR) {
        this.tusId = tusId;
        this.tusEstado = tusEstado;
        this.tusUser = tusUser;
        this.tusFHR = tusFHR;
    }

    public BigDecimal getTusId() {
        return tusId;
    }

    public void setTusId(BigDecimal tusId) {
        this.tusId = tusId;
    }

    public String getTusEstado() {
        return tusEstado;
    }

    public void setTusEstado(String tusEstado) {
        this.tusEstado = tusEstado;
    }

    public String getTusUser() {
        return tusUser;
    }

    public void setTusUser(String tusUser) {
        this.tusUser = tusUser;
    }

    public Calendar getTusFHR() {
        return tusFHR;
    }

    public void setTusFHR(Calendar tusFHR) {
        this.tusFHR = tusFHR;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    public String getTusEstadoDetalle() {
        return tusEstadoDetalle;
    }

    public void setTusEstadoDetalle(String tusEstadoDetalle) {
        this.tusEstadoDetalle = tusEstadoDetalle;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tusId != null ? tusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteUsuario)) {
            return false;
        }
        TramiteUsuario other = (TramiteUsuario) object;
        if ((this.tusId == null && other.tusId != null) || (this.tusId != null && !this.tusId.equals(other.tusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TramiteUsuario[ tusId=" + tusId + " ]";
    }

}
