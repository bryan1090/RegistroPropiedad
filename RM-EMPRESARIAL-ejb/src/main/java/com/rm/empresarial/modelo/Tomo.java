/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JeanCarlos
 */
@Entity
@Table(name = "Tomo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tomo.findAll", query = "SELECT t FROM Tomo t ORDER BY t.tplId.tplDescripcion")
    , @NamedQuery(name = "Tomo.findByTomId", query = "SELECT t FROM Tomo t WHERE t.tomId = :tomId")
    , @NamedQuery(name = "Tomo.findByTomAnio", query = "SELECT t FROM Tomo t WHERE t.tomAnio = :tomAnio")
    , @NamedQuery(name = "Tomo.findByTomUser", query = "SELECT t FROM Tomo t WHERE t.tomUser = :tomUser")
    , @NamedQuery(name = "Tomo.findByTomFHR", query = "SELECT t FROM Tomo t WHERE t.tomFHR = :tomFHR")})
public class Tomo implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Tomo.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TomId")
    private Long tomId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TomAnio")
    private Long tomAnio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TomUser")
    private String tomUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TomFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tomFHR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TomTomo")
    private short tomTomo;
    @JoinColumn(name = "TplId", referencedColumnName = "TplId")
    @ManyToOne(optional = false)
    private TipoLibro tplId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tomId")
    private List<Volumen> volumenList;

    public Tomo() {
    }

    public Tomo(Long tomId) {
        this.tomId = tomId;
    }

    public Tomo(Long tomId, Long tomAnio, String tomUser, Date tomFHR) {
        this.tomId = tomId;
        this.tomAnio = tomAnio;
        this.tomUser = tomUser;
        this.tomFHR = tomFHR;
    }

    public Long getTomId() {
        return tomId;
    }

    public void setTomId(Long tomId) {
        this.tomId = tomId;
    }

    public Long getTomAnio() {
        return tomAnio;
    }

    public void setTomAnio(Long tomAnio) {
        this.tomAnio = tomAnio;
    }

    public String getTomUser() {
        return tomUser;
    }

    public void setTomUser(String tomUser) {
        this.tomUser = tomUser;
    }

    public Date getTomFHR() {
        return tomFHR;
    }

    public void setTomFHR(Date tomFHR) {
        this.tomFHR = tomFHR;
    }
    
    public short getTomTomo() {
        return tomTomo;
    }

    public void setTomTomo(short tomTomo) {
        this.tomTomo = tomTomo;
    }

    public TipoLibro getTplId() {
        return tplId;
    }

    public void setTplId(TipoLibro tplId) {
        this.tplId = tplId;
    }
    
    @XmlTransient
    public List<Volumen> getVolumenList() {
        return volumenList;
    }

    public void setVolumenList(List<Volumen> volumenList) {
        this.volumenList = volumenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tomId != null ? tomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tomo)) {
            return false;
        }
        Tomo other = (Tomo) object;
        if ((this.tomId == null && other.tomId != null) || (this.tomId != null && !this.tomId.equals(other.tomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tomo[ tomId=" + tomId + " ]";
    }
    
}
