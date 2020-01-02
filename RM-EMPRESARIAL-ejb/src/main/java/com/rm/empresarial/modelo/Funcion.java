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
@Table(name = "Funcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcion.findAll", query = "SELECT f FROM Funcion f ORDER BY f.rolId.rolNombre")
    , @NamedQuery(name = "Funcion.findByFunId", query = "SELECT f FROM Funcion f WHERE f.funId = :funId")
    , @NamedQuery(name = "Funcion.findByFunPrograma", query = "SELECT f FROM Funcion f WHERE f.funPrograma = :funPrograma")
    , @NamedQuery(name = "Funcion.findByFunUser", query = "SELECT f FROM Funcion f WHERE f.funUser = :funUser")
    , @NamedQuery(name = "Funcion.findByFunFHR", query = "SELECT f FROM Funcion f WHERE f.funFHR = :funFHR")
    , @NamedQuery(name = "Funcion.validarIngreso", query = "SELECT f FROM Funcion f WHERE f.rolId.rolId = :rolId AND f.funPrograma = :funPrograma")
    , @NamedQuery(name = "Funcion.validarIngresoEditar", query = "SELECT f FROM Funcion f WHERE f.rolId.rolId = :rolId AND f.funPrograma = :funPrograma AND f.funId != :funId")})
public class Funcion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ENCONTRAR_POR_PROGRAMA = "Funcion.findByFunPrograma";
    public static final String LISTAR_TODO = "Funcion.findAll";
    public static final String VALIDAR_INGRESO = "Funcion.validarIngreso";
    public static final String VALIDAR_INGRESO_EDITAR = "Funcion.validarIngresoEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FunId")
    private Long funId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FunPrograma")
    private String funPrograma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FunUser")
    private String funUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FunFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funFHR;
    @JoinColumn(name = "RolId", referencedColumnName = "RolId")
    @ManyToOne(optional = false)
    private Rol rolId;

    public Funcion() {
    }

    public Funcion(Long funId) {
        this.funId = funId;
    }

    public Funcion(Long funId, String funPrograma, String funUser, Date funFHR) {
        this.funId = funId;
        this.funPrograma = funPrograma;
        this.funUser = funUser;
        this.funFHR = funFHR;
    }

    public Long getFunId() {
        return funId;
    }

    public void setFunId(Long funId) {
        this.funId = funId;
    }

    public String getFunPrograma() {
        return funPrograma;
    }

    public void setFunPrograma(String funPrograma) {
        this.funPrograma = funPrograma;
    }

    public String getFunUser() {
        return funUser;
    }

    public void setFunUser(String funUser) {
        this.funUser = funUser;
    }

    public Date getFunFHR() {
        return funFHR;
    }

    public void setFunFHR(Date funFHR) {
        this.funFHR = funFHR;
    }

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funId != null ? funId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcion)) {
            return false;
        }
        Funcion other = (Funcion) object;
        if ((this.funId == null && other.funId != null) || (this.funId != null && !this.funId.equals(other.funId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Funcion[ funId=" + funId + " ]";
    }

}
