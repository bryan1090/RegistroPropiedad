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
@Table(name = "RepertorioUsuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepertorioUsuario.findAll", query = "SELECT r FROM RepertorioUsuario r")
    , @NamedQuery(name = "RepertorioUsuario.findByRpuId", query = "SELECT r FROM RepertorioUsuario r WHERE r.rpuId = :rpuId")
    , @NamedQuery(name = "RepertorioUsuario.findByRpuEstado", query = "SELECT r FROM RepertorioUsuario r WHERE r.rpuEstado = :rpuEstado")
    , @NamedQuery(name = "RepertorioUsuario.findByRpuUser", query = "SELECT r FROM RepertorioUsuario r WHERE r.rpuUser = :rpuUser")
    , @NamedQuery(name = "RepertorioUsuario.findByRpuFHR", query = "SELECT r FROM RepertorioUsuario r WHERE r.rpuFHR = :rpuFHR")
    , @NamedQuery(name = "RepertorioUsuario.findByRpuTipo", query = "SELECT r FROM RepertorioUsuario r WHERE r.rpuTipo = :rpuTipo")
    , @NamedQuery(name = "RepertorioUsuario.listarPorTipoPorEstado", query = "SELECT r FROM RepertorioUsuario r WHERE r.rpuTipo = :rpuTipo AND r.rpuEstado = :rpuEstado")
})
public class RepertorioUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "RepertorioUsuario.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RpuId")
    private Long rpuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "RpuEstado")
    private String rpuEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RpuUser")
    private String rpuUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RpuFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rpuFHR;
    @Size(max = 10)
    @Column(name = "RpuTipo")
    private String rpuTipo;
    @Size(max = 10)
    @Column(name = "RpuEstadoProceso")
    private String rpuEstadoProceso;
    @JoinColumn(name = "RepNumero", referencedColumnName = "RepNumero")
    @ManyToOne(optional = false)
    private Repertorio repNumero;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public RepertorioUsuario() {
    }

    public RepertorioUsuario(Long rpuId) {
        this.rpuId = rpuId;
    }

    public RepertorioUsuario(Long rpuId, String rpuEstado, String rpuUser, Date rpuFHR) {
        this.rpuId = rpuId;
        this.rpuEstado = rpuEstado;
        this.rpuUser = rpuUser;
        this.rpuFHR = rpuFHR;
    }

    public Long getRpuId() {
        return rpuId;
    }

    public void setRpuId(Long rpuId) {
        this.rpuId = rpuId;
    }

    public String getRpuEstado() {
        return rpuEstado;
    }

    public void setRpuEstado(String rpuEstado) {
        this.rpuEstado = rpuEstado;
    }

    public String getRpuUser() {
        return rpuUser;
    }

    public void setRpuUser(String rpuUser) {
        this.rpuUser = rpuUser;
    }

    public Date getRpuFHR() {
        return rpuFHR;
    }

    public void setRpuFHR(Date rpuFHR) {
        this.rpuFHR = rpuFHR;
    }

    public String getRpuTipo() {
        return rpuTipo;
    }

    public void setRpuTipo(String rpuTipo) {
        this.rpuTipo = rpuTipo;
    }

    public Repertorio getRepNumero() {
        return repNumero;
    }

    public void setRepNumero(Repertorio repNumero) {
        this.repNumero = repNumero;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }
    public String getRpuEstadoProceso() {
        return rpuEstadoProceso;
    }

    public void setRpuEstadoProceso(String rpuEstadoProceso) {
        this.rpuEstadoProceso = rpuEstadoProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rpuId != null ? rpuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepertorioUsuario)) {
            return false;
        }
        RepertorioUsuario other = (RepertorioUsuario) object;
        if ((this.rpuId == null && other.rpuId != null) || (this.rpuId != null && !this.rpuId.equals(other.rpuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.RepertorioUsuario[ rpuId=" + rpuId + " ]";
    }

}
