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
@Table(name = "HistoricoContrasenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoricoContrasenia.findAll", query = "SELECT h FROM HistoricoContrasenia h")
    , @NamedQuery(name = "HistoricoContrasenia.findByHisConId", query = "SELECT h FROM HistoricoContrasenia h WHERE h.hisConId = :hisConId")
    , @NamedQuery(name = "HistoricoContrasenia.findByHisConPas", query = "SELECT h FROM HistoricoContrasenia h WHERE h.hisConPas = :hisConPas")
    , @NamedQuery(name = "HistoricoContrasenia.findByHisConFHR", query = "SELECT h FROM HistoricoContrasenia h WHERE h.hisConFHR = :hisConFHR")
    , @NamedQuery(name = "HistoricoContrasenia.findByHisConUser", query = "SELECT h FROM HistoricoContrasenia h WHERE h.hisConUser = :hisConUser")})
public class HistoricoContrasenia implements Serializable {
    
     public static final String LISTAR_TODO = "HistoricoContrasenia.findAll";

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HisConId")
    private Long hisConId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "HisConPas")
    private String hisConPas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HisConFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hisConFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "HisConUser")
    private String hisConUser;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public HistoricoContrasenia() {
    }

    public HistoricoContrasenia(Long hisConId) {
        this.hisConId = hisConId;
    }

    public HistoricoContrasenia(Long hisConId, String hisConPas, Date hisConFHR, String hisConUser) {
        this.hisConId = hisConId;
        this.hisConPas = hisConPas;
        this.hisConFHR = hisConFHR;
        this.hisConUser = hisConUser;
    }

    public Long getHisConId() {
        return hisConId;
    }

    public void setHisConId(Long hisConId) {
        this.hisConId = hisConId;
    }

    public String getHisConPas() {
        return hisConPas;
    }

    public void setHisConPas(String hisConPas) {
        this.hisConPas = hisConPas;
    }

    public Date getHisConFHR() {
        return hisConFHR;
    }

    public void setHisConFHR(Date hisConFHR) {
        this.hisConFHR = hisConFHR;
    }

    public String getHisConUser() {
        return hisConUser;
    }

    public void setHisConUser(String hisConUser) {
        this.hisConUser = hisConUser;
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
        hash += (hisConId != null ? hisConId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoContrasenia)) {
            return false;
        }
        HistoricoContrasenia other = (HistoricoContrasenia) object;
        if ((this.hisConId == null && other.hisConId != null) || (this.hisConId != null && !this.hisConId.equals(other.hisConId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.HistoricoContrasenia[ hisConId=" + hisConId + " ]";
    }
    
}
