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
@Table(name = "UsuarioCaja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioCaja.findAll", query = "SELECT u FROM UsuarioCaja u ORDER BY u.usuId.usuLogin")
    , @NamedQuery(name = "UsuarioCaja.findByUscId", query = "SELECT u FROM UsuarioCaja u WHERE u.uscId = :uscId")
    , @NamedQuery(name = "UsuarioCaja.findByUscUser", query = "SELECT u FROM UsuarioCaja u WHERE u.usuId = :usuId")
    , @NamedQuery(name = "UsuarioCaja.findByUscFHR", query = "SELECT u FROM UsuarioCaja u WHERE u.uscFHR = :uscFHR")
    , @NamedQuery(name = "UsuarioCaja.validacionIngreso", query = "SELECT u FROM UsuarioCaja u WHERE u.usuId.usuId = :usuId AND u.cajId.cajId = :cajId")
    , @NamedQuery(name = "UsuarioCaja.validacionIngresoEditar", query = "SELECT u FROM UsuarioCaja u WHERE u.usuId.usuId = :usuId AND u.cajId.cajId = :cajId AND u.uscId != :uscId")})
public class UsuarioCaja implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String CAJA_POR_USARIO = "UsuarioCaja.findByUscUser";
    public static final String LISTAR_TODO = "UsuarioCaja.findAll";
    public static final String VALIDACION_INGRESO = "UsuarioCaja.validacionIngreso";
    public static final String VALIDACION_INGRESO_EDITAR = "UsuarioCaja.validacionIngresoEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UscId")
    private Long uscId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "UscUser")
    private String uscUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UscFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uscFHR;
    @JoinColumn(name = "CajId", referencedColumnName = "CajId")
    @ManyToOne(optional = false)
    private Caja cajId;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public UsuarioCaja() {
    }

    public UsuarioCaja(Long uscId) {
        this.uscId = uscId;
    }

    public UsuarioCaja(Long uscId, String uscUser, Date uscFHR) {
        this.uscId = uscId;
        this.uscUser = uscUser;
        this.uscFHR = uscFHR;
    }

    public Long getUscId() {
        return uscId;
    }

    public void setUscId(Long uscId) {
        this.uscId = uscId;
    }

    public String getUscUser() {
        return uscUser;
    }

    public void setUscUser(String uscUser) {
        this.uscUser = uscUser;
    }

    public Date getUscFHR() {
        return uscFHR;
    }

    public void setUscFHR(Date uscFHR) {
        this.uscFHR = uscFHR;
    }

    public Caja getCajId() {
        return cajId;
    }

    public void setCajId(Caja cajId) {
        this.cajId = cajId;
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
        hash += (uscId != null ? uscId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioCaja)) {
            return false;
        }
        UsuarioCaja other = (UsuarioCaja) object;
        if ((this.uscId == null && other.uscId != null) || (this.uscId != null && !this.uscId.equals(other.uscId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.UsuarioCaja[ uscId=" + uscId + " ]";
    }
    
}
