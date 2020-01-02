/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Prueba
 */
@Entity
@Table(name = "TipoCertificado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCertificado.findAll", query = "SELECT t FROM TipoCertificado t")
    , @NamedQuery(name = "TipoCertificado.findByTroId", query = "SELECT t FROM TipoCertificado t WHERE t.troId = :troId")
    , @NamedQuery(name = "TipoCertificado.findByTroNombre", query = "SELECT t FROM TipoCertificado t WHERE t.troNombre = :troNombre")
    , @NamedQuery(name = "TipoCertificado.findByTroValor", query = "SELECT t FROM TipoCertificado t WHERE t.troValor = :troValor")
    , @NamedQuery(name = "TipoCertificado.findByTroUnidadTiempo", query = "SELECT t FROM TipoCertificado t WHERE t.troUnidadTiempo = :troUnidadTiempo")
    , @NamedQuery(name = "TipoCertificado.findByTroCantidadTiempo", query = "SELECT t FROM TipoCertificado t WHERE t.troCantidadTiempo = :troCantidadTiempo")
    , @NamedQuery(name = "TipoCertificado.findByTroIva", query = "SELECT t FROM TipoCertificado t WHERE t.troIva = :troIva")
    , @NamedQuery(name = "TipoCertificado.findByTroUser", query = "SELECT t FROM TipoCertificado t WHERE t.troUser = :troUser")
    , @NamedQuery(name = "TipoCertificado.findByTroFhr", query = "SELECT t FROM TipoCertificado t WHERE t.troFhr = :troFhr")
    , @NamedQuery(name = "TipoCertificado.findByTroTipo", query = "SELECT t FROM TipoCertificado t WHERE t.troTipo = :troTipo")
})
public class TipoCertificado implements Serializable {

    public static final String LISTAR_TODOS = "TipoCertificado.findAll";

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TroId")
    private Long troId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "TroNombre")
    private String troNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TroValor")
    private BigDecimal troValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TroUnidadTiempo")
    private String troUnidadTiempo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TroCantidadTiempo")
    private short troCantidadTiempo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TroIva")
    private String troIva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TroUser")
    private String troUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TroFhr")
    @Temporal(TemporalType.TIMESTAMP)
    private Date troFhr;

    @Size(max = 1)
    @Column(name = "TroTipo")
    private String troTipo;
    @Size(max = 2147483647)
    @Column(name = "TroDescripcion1")
    private String troDescripcion1;
    @Size(max = 2147483647)
    @Column(name = "TroDescripcion2")
    private String troDescripcion2;

    public TipoCertificado() {
    }

    public TipoCertificado(Long troId) {
        this.troId = troId;
    }

    public TipoCertificado(Long troId, String troNombre, BigDecimal troValor, String troUnidadTiempo, short troCantidadTiempo, String troIva, String troUser, Date troFhr) {
        this.troId = troId;
        this.troNombre = troNombre;
        this.troValor = troValor;
        this.troUnidadTiempo = troUnidadTiempo;
        this.troCantidadTiempo = troCantidadTiempo;
        this.troIva = troIva;
        this.troUser = troUser;
        this.troFhr = troFhr;
    }

    public Long getTroId() {
        return troId;
    }

    public void setTroId(Long troId) {
        this.troId = troId;
    }

    public String getTroNombre() {
        return troNombre;
    }

    public void setTroNombre(String troNombre) {
        this.troNombre = troNombre;
    }

    public BigDecimal getTroValor() {
        return troValor;
    }

    public void setTroValor(BigDecimal troValor) {
        this.troValor = troValor;
    }

    public String getTroUnidadTiempo() {
        return troUnidadTiempo;
    }

    public void setTroUnidadTiempo(String troUnidadTiempo) {
        this.troUnidadTiempo = troUnidadTiempo;
    }

    public short getTroCantidadTiempo() {
        return troCantidadTiempo;
    }

    public void setTroCantidadTiempo(short troCantidadTiempo) {
        this.troCantidadTiempo = troCantidadTiempo;
    }

    public String getTroIva() {
        return troIva;
    }

    public void setTroIva(String troIva) {
        this.troIva = troIva;
    }

    public String getTroUser() {
        return troUser;
    }

    public void setTroUser(String troUser) {
        this.troUser = troUser;
    }

    public Date getTroFhr() {
        return troFhr;
    }

    public void setTroFhr(Date troFhr) {
        this.troFhr = troFhr;
    }

    public String getTroTipo() {
        return troTipo;
    }

    public void setTroTipo(String troTipo) {
        this.troTipo = troTipo;
    }

    public String getTroDescripcion1() {
        return troDescripcion1;
    }

    public void setTroDescripcion1(String troDescripcion1) {
        this.troDescripcion1 = troDescripcion1;
    }

    public String getTroDescripcion2() {
        return troDescripcion2;
    }

    public void setTroDescripcion2(String troDescripcion2) {
        this.troDescripcion2 = troDescripcion2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (troId != null ? troId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCertificado)) {
            return false;
        }
        TipoCertificado other = (TipoCertificado) object;
        if ((this.troId == null && other.troId != null) || (this.troId != null && !this.troId.equals(other.troId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoCertifcado.TipoCertificado[ troId=" + troId + " ]";
    }

}
