/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "EquivalenciaTramite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquivalenciaTramite.findAll", query = "SELECT e FROM EquivalenciaTramite e")
    , @NamedQuery(name = "EquivalenciaTramite.findByEvtId", query = "SELECT e FROM EquivalenciaTramite e WHERE e.evtId = :evtId")
    , @NamedQuery(name = "EquivalenciaTramite.findByEvtTtrId1", query = "SELECT e FROM EquivalenciaTramite e WHERE e.evtTtrId1 = :evtTtrId1")
    , @NamedQuery(name = "EquivalenciaTramite.findByEvtTtrId2", query = "SELECT e FROM EquivalenciaTramite e WHERE e.evtTtrId2 = :evtTtrId2")})
public class EquivalenciaTramite implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EvtId")
    private Long evtId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EvtTtrId1")
    private Long evtTtrId1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EvtTtrId2")
    private Long evtTtrId2;

    public EquivalenciaTramite() {
    }

    public EquivalenciaTramite(Long evtId) {
        this.evtId = evtId;
    }

    public EquivalenciaTramite(Long evtId, Long evtTtrId1, Long evtTtrId2) {
        this.evtId = evtId;
        this.evtTtrId1 = evtTtrId1;
        this.evtTtrId2 = evtTtrId2;
    }

    public Long getEvtId() {
        return evtId;
    }

    public void setEvtId(Long evtId) {
        this.evtId = evtId;
    }

    public Long getEvtTtrId1() {
        return evtTtrId1;
    }

    public void setEvtTtrId1(Long evtTtrId1) {
        this.evtTtrId1 = evtTtrId1;
    }

    public Long getEvtTtrId2() {
        return evtTtrId2;
    }

    public void setEvtTtrId2(Long evtTtrId2) {
        this.evtTtrId2 = evtTtrId2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evtId != null ? evtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquivalenciaTramite)) {
            return false;
        }
        EquivalenciaTramite other = (EquivalenciaTramite) object;
        if ((this.evtId == null && other.evtId != null) || (this.evtId != null && !this.evtId.equals(other.evtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.EquivalenciaTramite[ evtId=" + evtId + " ]";
    }
    
}
