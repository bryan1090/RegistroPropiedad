/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DJimenez
 */
@Entity
@Table(name = "UsuarioInsidencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioInsidencia.findAll", query = "SELECT u FROM UsuarioInsidencia u")
    , @NamedQuery(name = "UsuarioInsidencia.findByUinId", query = "SELECT u FROM UsuarioInsidencia u WHERE u.uinId = :uinId")})
public class UsuarioInsidencia implements Serializable {

    public static final String LISTAR_TODO = "UsuarioInsidencia.findAll";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UinId")
    private Long uinId;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;
    @OneToMany(mappedBy = "uinId")
    private Collection<Incidencia> incidenciaCollection;

    public UsuarioInsidencia() {
    }

    public UsuarioInsidencia(Long uinId) {
        this.uinId = uinId;
    }

    public Long getUinId() {
        return uinId;
    }

    public void setUinId(Long uinId) {
        this.uinId = uinId;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @XmlTransient
    public Collection<Incidencia> getIncidenciaCollection() {
        return incidenciaCollection;
    }

    public void setIncidenciaCollection(Collection<Incidencia> incidenciaCollection) {
        this.incidenciaCollection = incidenciaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uinId != null ? uinId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioInsidencia)) {
            return false;
        }
        UsuarioInsidencia other = (UsuarioInsidencia) object;
        if ((this.uinId == null && other.uinId != null) || (this.uinId != null && !this.uinId.equals(other.uinId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.UsuarioInsidencia[ uinId=" + uinId + " ]";
    }
    
}
