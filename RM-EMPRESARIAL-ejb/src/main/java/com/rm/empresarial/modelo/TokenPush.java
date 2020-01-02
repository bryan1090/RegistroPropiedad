/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JeanCarlos
 */
@Entity
@Table(name = "TokenPush")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TokenPush.findAll", query = "SELECT t FROM TokenPush t")
    , @NamedQuery(name = "TokenPush.findByTkpId", query = "SELECT t FROM TokenPush t WHERE t.tkpId = :tkpId")
    , @NamedQuery(name = "TokenPush.findByTkpLogin", query = "SELECT t FROM TokenPush t WHERE t.tkpLogin = :tkpLogin")
    , @NamedQuery(name = "TokenPush.findByTkpEstado", query = "SELECT t FROM TokenPush t WHERE t.tkpEstado = :tkpEstado")})
public class TokenPush implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TkpId")
    private Long tkpId;
    @Basic(optional = false)
    @Size(max = 2000)
    @Column(name = "TkpLogin")
    private String tkpLogin;
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TkpEstado")
    private String tkpEstado;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public TokenPush() {
    }

    public TokenPush(Long tkpId) {
        this.tkpId = tkpId;
    }

    public TokenPush(Long tkpId, String tkpLogin, String tkpEstado) {
        this.tkpId = tkpId;
        this.tkpLogin = tkpLogin;
        this.tkpEstado = tkpEstado;
    }

    public Long getTkpId() {
        return tkpId;
    }

    public void setTkpId(Long tkpId) {
        this.tkpId = tkpId;
    }

    public String getTkpLogin() {
        return tkpLogin;
    }

    public void setTkpLogin(String tkpLogin) {
        this.tkpLogin = tkpLogin;
    }

    public String getTkpEstado() {
        return tkpEstado;
    }

    public void setTkpEstado(String tkpEstado) {
        this.tkpEstado = tkpEstado;
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
        hash += (tkpId != null ? tkpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TokenPush)) {
            return false;
        }
        TokenPush other = (TokenPush) object;
        if ((this.tkpId == null && other.tkpId != null) || (this.tkpId != null && !this.tkpId.equals(other.tkpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TokenPush[ tkpId=" + tkpId + " ]";
    }
    
}
