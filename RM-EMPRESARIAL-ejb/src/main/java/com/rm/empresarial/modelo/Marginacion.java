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
@Table(name = "Marginacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Marginacion.findAll", query = "SELECT m FROM Marginacion m")
    , @NamedQuery(name = "Marginacion.findByMrgId", query = "SELECT m FROM Marginacion m WHERE m.mrgId = :mrgId")
    , @NamedQuery(name = "Marginacion.findByMrgFHR", query = "SELECT m FROM Marginacion m WHERE m.mrgFHR = :mrgFHR")
    , @NamedQuery(name = "Marginacion.findByMrgUser", query = "SELECT m FROM Marginacion m WHERE m.mrgUser = :mrgUser")
    , @NamedQuery(name = "Marginacion.findByMrgAlt1", query = "SELECT m FROM Marginacion m WHERE m.mrgAlt1 = :mrgAlt1")
    , @NamedQuery(name = "Marginacion.findByMrgAlt2", query = "SELECT m FROM Marginacion m WHERE m.mrgAlt2 = :mrgAlt2")
    , @NamedQuery(name = "Marginacion.findByMrgAlt3", query = "SELECT m FROM Marginacion m WHERE m.mrgAlt3 = :mrgAlt3")
    , @NamedQuery(name = "Marginacion.findByMrgAlt4", query = "SELECT m FROM Marginacion m WHERE m.mrgAlt4 = :mrgAlt4")
    , @NamedQuery(name = "Marginacion.findByMrgFch", query = "SELECT m FROM Marginacion m WHERE m.mrgFch = :mrgFch")
    , @NamedQuery(name = "Marginacion.buscarPorMrgAlt2YactNum", query = "SELECT m FROM Marginacion m WHERE m.mrgFch = :mrgFch AND m.actNumero.actNumero = :actNumero")})

public class Marginacion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_POR_MRGALT2 = "Marginacion.findByMrgAlt2";
    public static final String LISTAR_POR_MRGALT2_ACTNUM = "Marginacion.buscarPorMrgAlt2YactNum";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MrgId")
    private Long mrgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MrgFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mrgFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MrgUser")
    private String mrgUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "MrgAlt1")
    private String mrgAlt1; //texto de la marginacion
    @Basic(optional = false)
    @Size(max = 100)
    @Column(name = "MrgAlt2")
    private String mrgAlt2; //numero de repertorio
    @Basic(optional = false)
    @Size(max = 100)
    @Column(name = "MrgAlt3")
    private String mrgAlt3;
    @Basic(optional = false)
    @Size(min = 1, max = 2000)
    @Column(name = "MrgAlt4")
    private String mrgAlt4;
     @Column(name = "MrgFch")
    @Temporal(TemporalType.DATE)
    private Date mrgFch;
    @JoinColumn(name = "ActNumero", referencedColumnName = "ActNumero")
    @ManyToOne(optional = false)
    private Acta actNumero;
    @JoinColumn(name = "TmcId", referencedColumnName = "TmcId")
    @ManyToOne
    private TipoMarginacion tmcId;

    public Marginacion() {
    }

    public Marginacion(Long mrgId) {
        this.mrgId = mrgId;
    }

    public Marginacion(Long mrgId, Date mrgFHR, String mrgUser, String mrgAlt1, String mrgAlt2, String mrgAlt3, String mrgAlt4) {
        this.mrgId = mrgId;
        this.mrgFHR = mrgFHR;
        this.mrgUser = mrgUser;
        this.mrgAlt1 = mrgAlt1;
        this.mrgAlt2 = mrgAlt2;
        this.mrgAlt3 = mrgAlt3;
        this.mrgAlt4 = mrgAlt4;
    }

    public Long getMrgId() {
        return mrgId;
    }

    public void setMrgId(Long mrgId) {
        this.mrgId = mrgId;
    }

    public Date getMrgFHR() {
        return mrgFHR;
    }

    public void setMrgFHR(Date mrgFHR) {
        this.mrgFHR = mrgFHR;
    }

    public String getMrgUser() {
        return mrgUser;
    }

    public void setMrgUser(String mrgUser) {
        this.mrgUser = mrgUser;
    }

    public String getMrgAlt1() {
        return mrgAlt1;
    }

    public void setMrgAlt1(String mrgAlt1) {
        this.mrgAlt1 = mrgAlt1;
    }

    public String getMrgAlt2() {
        return mrgAlt2;
    }

    public void setMrgAlt2(String mrgAlt2) {
        this.mrgAlt2 = mrgAlt2;
    }

    public String getMrgAlt3() {
        return mrgAlt3;
    }

    public void setMrgAlt3(String mrgAlt3) {
        this.mrgAlt3 = mrgAlt3;
    }

    public String getMrgAlt4() {
        return mrgAlt4;
    }

    public void setMrgAlt4(String mrgAlt4) {
        this.mrgAlt4 = mrgAlt4;
    }

    public Date getMrgFch() {
        return mrgFch;
    }

    public void setMrgFch(Date mrgFch) {
        this.mrgFch = mrgFch;
    }
    
    public Acta getActNumero() {
        return actNumero;
    }

    public void setActNumero(Acta actNumero) {
        this.actNumero = actNumero;
    }

    public TipoMarginacion getTmcId() {
        return tmcId;
    }

    public void setTmcId(TipoMarginacion tmcId) {
        this.tmcId = tmcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mrgId != null ? mrgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marginacion)) {
            return false;
        }
        Marginacion other = (Marginacion) object;
        if ((this.mrgId == null && other.mrgId != null) || (this.mrgId != null && !this.mrgId.equals(other.mrgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Marginacion[ mrgId=" + mrgId + " ]";
    }
    
}
