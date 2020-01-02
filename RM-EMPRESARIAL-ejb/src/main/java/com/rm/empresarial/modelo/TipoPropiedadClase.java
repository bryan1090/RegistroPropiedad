/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "TipoPropiedadClase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPropiedadClase.findAll", query = "SELECT t FROM TipoPropiedadClase t ORDER BY t.tclId")
    , @NamedQuery(name = "TipoPropiedadClase.findByTclId", query = "SELECT t FROM TipoPropiedadClase t WHERE t.tclId = :tclId")
    , @NamedQuery(name = "TipoPropiedadClase.findByTclNombre", query = "SELECT t FROM TipoPropiedadClase t WHERE t.tclNombre = :tclNombre")
    , @NamedQuery(name = "TipoPropiedadClase.findByTclEstado", query = "SELECT t FROM TipoPropiedadClase t WHERE t.tclEstado = :tclEstado")
    , @NamedQuery(name = "TipoPropiedadClase.findByTclUser", query = "SELECT t FROM TipoPropiedadClase t WHERE t.tclUser = :tclUser")
    , @NamedQuery(name = "TipoPropiedadClase.findByTclFHR", query = "SELECT t FROM TipoPropiedadClase t WHERE t.tclFHR = :tclFHR")
    , @NamedQuery(name = "TipoPropiedadClase.findByTclNombreEditar", query = "SELECT t FROM TipoPropiedadClase t WHERE t.tclNombre = :tclNombre AND t.tclId != :tclId")})
public class TipoPropiedadClase implements Serializable {

    @OneToMany(mappedBy = "tclId")
    private List<Propiedad> propiedadList;

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoPropiedadClase.findAll";
    public static final String LISTAR_PORID = "TipoPropiedadClase.findByTclId";
    public static final String BUSCAR_POR_NOM = "TipoPropiedadClase.findByTclNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "TipoPropiedadClase.findByTclNombreEditar";
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TclId")
    private String tclId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TclNombre")
    private String tclNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TclEstado")
    private String tclEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TclUser")
    private String tclUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TclFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tclFHR;

    public TipoPropiedadClase() {
    }

    public TipoPropiedadClase(String tclId) {
        this.tclId = tclId;
    }

    public TipoPropiedadClase(String tclId, String tclNombre, String tclEstado, String tclUser, Date tclFHR) {
        this.tclId = tclId;
        this.tclNombre = tclNombre;
        this.tclEstado = tclEstado;
        this.tclUser = tclUser;
        this.tclFHR = tclFHR;
    }

    public String getTclId() {
        return tclId;
    }

    public void setTclId(String tclId) {
        this.tclId = tclId;
    }

    public String getTclNombre() {
        return tclNombre;
    }

    public void setTclNombre(String tclNombre) {
        this.tclNombre = tclNombre;
    }

    public String getTclEstado() {
        return tclEstado;
    }

    public void setTclEstado(String tclEstado) {
        this.tclEstado = tclEstado;
    }

    public String getTclUser() {
        return tclUser;
    }

    public void setTclUser(String tclUser) {
        this.tclUser = tclUser;
    }

    public Date getTclFHR() {
        return tclFHR;
    }

    public void setTclFHR(Date tclFHR) {
        this.tclFHR = tclFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tclId != null ? tclId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPropiedadClase)) {
            return false;
        }
        TipoPropiedadClase other = (TipoPropiedadClase) object;
        if ((this.tclId == null && other.tclId != null) || (this.tclId != null && !this.tclId.equals(other.tclId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoPropiedadClase[ tclId=" + tclId + " ]";
    }

    @XmlTransient
    public List<Propiedad> getPropiedadList() {
        return propiedadList;
    }

    public void setPropiedadList(List<Propiedad> propiedadList) {
        this.propiedadList = propiedadList;
    }
    
}
