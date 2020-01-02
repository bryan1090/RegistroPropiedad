/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author Prueba
 */
@Entity
@Table(name = "VersionPH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VersionPH.findAll", query = "SELECT v FROM VersionPH v")
    , @NamedQuery(name = "VersionPH.findByVphSecuencial", query = "SELECT v FROM VersionPH v WHERE v.versionPHPK.vphSecuencial = :vphSecuencial")
    , @NamedQuery(name = "VersionPH.findByVphMatricula", query = "SELECT v FROM VersionPH v WHERE v.versionPHPK.vphMatricula = :vphMatricula")
    , @NamedQuery(name = "VersionPH.findByVphVersion", query = "SELECT v FROM VersionPH v WHERE v.versionPHPK.vphVersion = :vphVersion")
    , @NamedQuery(name = "VersionPH.findByVphFHR", query = "SELECT v FROM VersionPH v WHERE v.vphFHR = :vphFHR")
    , @NamedQuery(name = "VersionPH.findByVphpUser", query = "SELECT v FROM VersionPH v WHERE v.vphpUser = :vphpUser")})
public class VersionPH implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VersionPHPK versionPHPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VphFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vphFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "VphpUser")
    private String vphpUser;
    @JoinColumn(name = "TalId", referencedColumnName = "TalId")
    @ManyToOne(optional = false)
    private TmpAlicuota talId;

    public VersionPH() {
    }

    public VersionPH(VersionPHPK versionPHPK) {
        this.versionPHPK = versionPHPK;
    }

    public VersionPH(VersionPHPK versionPHPK, Date vphFHR, String vphpUser) {
        this.versionPHPK = versionPHPK;
        this.vphFHR = vphFHR;
        this.vphpUser = vphpUser;
    }

    public VersionPH(BigInteger vphSecuencial, String vphMatricula, int vphVersion) {
        this.versionPHPK = new VersionPHPK(vphSecuencial, vphMatricula, vphVersion);
    }

    public VersionPHPK getVersionPHPK() {
        return versionPHPK;
    }

    public void setVersionPHPK(VersionPHPK versionPHPK) {
        this.versionPHPK = versionPHPK;
    }

    public Date getVphFHR() {
        return vphFHR;
    }

    public void setVphFHR(Date vphFHR) {
        this.vphFHR = vphFHR;
    }

    public String getVphpUser() {
        return vphpUser;
    }

    public void setVphpUser(String vphpUser) {
        this.vphpUser = vphpUser;
    }

    public TmpAlicuota getTalId() {
        return talId;
    }

    public void setTalId(TmpAlicuota talId) {
        this.talId = talId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (versionPHPK != null ? versionPHPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VersionPH)) {
            return false;
        }
        VersionPH other = (VersionPH) object;
        if ((this.versionPHPK == null && other.versionPHPK != null) || (this.versionPHPK != null && !this.versionPHPK.equals(other.versionPHPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.VersionPH[ versionPHPK=" + versionPHPK + " ]";
    }
    
}
