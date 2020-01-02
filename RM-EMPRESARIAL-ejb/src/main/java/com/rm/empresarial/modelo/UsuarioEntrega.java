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
 * @author DJimenez
 */
@Entity
@Table(name = "UsuarioEntrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioEntrega.findAll", query = "SELECT u FROM UsuarioEntrega u")
    , @NamedQuery(name = "UsuarioEntrega.findByUseId", query = "SELECT u FROM UsuarioEntrega u WHERE u.useId = :useId")
    , @NamedQuery(name = "UsuarioEntrega.findByUseDescripcion", query = "SELECT u FROM UsuarioEntrega u WHERE u.useDescripcion = :useDescripcion")
    , @NamedQuery(name = "UsuarioEntrega.findByUseFHR", query = "SELECT u FROM UsuarioEntrega u WHERE u.useFHR = :useFHR")})
public class UsuarioEntrega implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UseId")
    private Long useId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 200)
    @Column(name = "UseDescripcion")
    private String useDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UseFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date useFHR;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public UsuarioEntrega() {
    }

    public UsuarioEntrega(Long useId) {
        this.useId = useId;
    }

    public UsuarioEntrega(Long useId, String useDescripcion, Date useFHR) {
        this.useId = useId;
        this.useDescripcion = useDescripcion;
        this.useFHR = useFHR;
    }

    public Long getUseId() {
        return useId;
    }

    public void setUseId(Long useId) {
        this.useId = useId;
    }

    public String getUseDescripcion() {
        return useDescripcion;
    }

    public void setUseDescripcion(String useDescripcion) {
        this.useDescripcion = useDescripcion;
    }

    public Date getUseFHR() {
        return useFHR;
    }

    public void setUseFHR(Date useFHR) {
        this.useFHR = useFHR;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
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
        hash += (useId != null ? useId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioEntrega)) {
            return false;
        }
        UsuarioEntrega other = (UsuarioEntrega) object;
        if ((this.useId == null && other.useId != null) || (this.useId != null && !this.useId.equals(other.useId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.UsuarioEntrega[ useId=" + useId + " ]";
    }
    
}
