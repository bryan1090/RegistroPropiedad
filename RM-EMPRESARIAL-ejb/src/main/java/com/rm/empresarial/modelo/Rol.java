/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.resource.spi.AuthenticationMechanism;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Rol")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.listarTodo", query = "SELECT r FROM Rol r ORDER BY r.rolNombre")
    , @NamedQuery(name = "Rol.findByRolId", query = "SELECT r FROM Rol r WHERE r.rolId = :rolId")
    , @NamedQuery(name = "Rol.findByRolNombre", query = "SELECT r FROM Rol r WHERE r.rolNombre = :rolNombre")
    , @NamedQuery(name = "Rol.findByRolEstado", query = "SELECT r FROM Rol r WHERE r.rolEstado = :rolEstado")
    , @NamedQuery(name = "Rol.findByRolUser", query = "SELECT r FROM Rol r WHERE r.rolUser = :rolUser")
    , @NamedQuery(name = "Rol.findByRolFHR", query = "SELECT r FROM Rol r WHERE r.rolFHR = :rolFHR")
    , @NamedQuery(name = "Rol.findByRolNombreEditar", query = "SELECT r FROM Rol r WHERE r.rolNombre = :rolNombre AND r.rolId != :rolId")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Rol.listarTodo";
    public static final String ENCONTRAR_ROL_POR_ID="Rol.findByRolId";
    public static final String BUSCAR_POR_NOM = "Rol.findByRolNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "Rol.findByRolNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "RolId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "RolNombre")
    private String rolNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "RolEstado")
    private String rolEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "RolUser")
    private String rolUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RolFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolFHR;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolId")
    private List<Usuario> usuarioList;

    public Rol() {
    this.rolFHR=Calendar.getInstance().getTime();
        this.rolUser="ugenerico";
    }

    public Rol(Long rolId) {
        this.rolId = rolId;
    }

    public Rol(Long rolId, String rolNombre, String rolEstado, String rolUser, Date rolFHR) {
        this.rolId = rolId;
        this.rolNombre = rolNombre;
        this.rolEstado = rolEstado;
//        this.rolUser = rolUser;
//        this.rolFHR = rolFHR;
         this.rolFHR=Calendar.getInstance().getTime();
        this.rolUser="ugenerico";
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public String getRolEstado() {
        return rolEstado;
    }

    public void setRolEstado(String rolEstado) {
        this.rolEstado = rolEstado;
    }

    public String getRolUser() {
        return rolUser;
    }

    public void setRolUser(String rolUser) {
        this.rolUser = rolUser;
    }

    public Date getRolFHR() {
        return rolFHR;
    }

    public void setRolFHR(Date rolFHR) {
        this.rolFHR = rolFHR;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolId != null ? rolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.rolId == null && other.rolId != null) || (this.rolId != null && !this.rolId.equals(other.rolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenproject3.Rol[ rolId=" + rolId + " ]";
    }

}
