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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "GravamenDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GravamenDetalle.findAll", query = "SELECT g FROM GravamenDetalle g")
    , @NamedQuery(name = "GravamenDetalle.findByGvdId", query = "SELECT g FROM GravamenDetalle g WHERE g.gvdId = :gvdId")
    , @NamedQuery(name = "GravamenDetalle.findByGvdEstado", query = "SELECT g FROM GravamenDetalle g WHERE g.gvdEstado = :gvdEstado")
    , @NamedQuery(name = "GravamenDetalle.findByGvdUser", query = "SELECT g FROM GravamenDetalle g WHERE g.gvdUser = :gvdUser")
    , @NamedQuery(name = "GravamenDetalle.findByGvdFHR", query = "SELECT g FROM GravamenDetalle g WHERE g.gvdFHR = :gvdFHR")
    , @NamedQuery(name = "GravamenDetalle.findByGvdPorcentaje", query = "SELECT g FROM GravamenDetalle g WHERE g.gvdPorcentaje = :gvdPorcentaje")})
public class GravamenDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GvdId")
    private Long gvdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "GvdEstado")
    private String gvdEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "GvdUser")
    private String gvdUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GvdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gvdFHR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GvdPorcentaje")
    private BigDecimal gvdPorcentaje;
    @JoinColumn(name = "GraId", referencedColumnName = "GraId")
    @ManyToOne(optional = false)
    private Gravamen graId;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;
    @Transient
    @Getter
    @Setter
    private int numeroMatricula;

    public GravamenDetalle() {
    }

    public GravamenDetalle(Long gvdId) {
        this.gvdId = gvdId;
    }

    public GravamenDetalle(Long gvdId, String gvdEstado, String gvdUser, Date gvdFHR, BigDecimal gvdPorcentaje) {
        this.gvdId = gvdId;
        this.gvdEstado = gvdEstado;
        this.gvdUser = gvdUser;
        this.gvdFHR = gvdFHR;
        this.gvdPorcentaje = gvdPorcentaje;
    }

    public Long getGvdId() {
        return gvdId;
    }

    public void setGvdId(Long gvdId) {
        this.gvdId = gvdId;
    }

    public String getGvdEstado() {
        return gvdEstado;
    }

    public void setGvdEstado(String gvdEstado) {
        this.gvdEstado = gvdEstado;
    }

    public String getGvdUser() {
        return gvdUser;
    }

    public void setGvdUser(String gvdUser) {
        this.gvdUser = gvdUser;
    }

    public Date getGvdFHR() {
        return gvdFHR;
    }

    public void setGvdFHR(Date gvdFHR) {
        this.gvdFHR = gvdFHR;
    }

    public BigDecimal getGvdPorcentaje() {
        return gvdPorcentaje;
    }

    public void setGvdPorcentaje(BigDecimal gvdPorcentaje) {
        this.gvdPorcentaje = gvdPorcentaje;
    }

    public Gravamen getGraId() {
        return graId;
    }

    public void setGraId(Gravamen graId) {
        this.graId = graId;
    }

    public Persona getPerId() {
        return perId;
    }

    public void setPerId(Persona perId) {
        this.perId = perId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gvdId != null ? gvdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GravamenDetalle)) {
            return false;
        }
        GravamenDetalle other = (GravamenDetalle) object;
        if ((this.gvdId == null && other.gvdId != null) || (this.gvdId != null && !this.gvdId.equals(other.gvdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.GravamenDetalle[ gvdId=" + gvdId + " ]";
    }
    
}
