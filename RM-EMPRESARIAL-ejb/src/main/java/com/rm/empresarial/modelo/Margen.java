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
 * @author JeanCarlos
 */
@Entity
@Table(name = "Margen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Margen.findAll", query = "SELECT m FROM Margen m ORDER BY m.marTipo")
    , @NamedQuery(name = "Margen.findByMarId", query = "SELECT m FROM Margen m WHERE m.marId = :marId")
    , @NamedQuery(name = "Margen.findByMarTipo", query = "SELECT m FROM Margen m WHERE m.marTipo = :marTipo")
    , @NamedQuery(name = "Margen.findByMarSuperior", query = "SELECT m FROM Margen m WHERE m.marSuperior = :marSuperior")
    , @NamedQuery(name = "Margen.findByMarInferior", query = "SELECT m FROM Margen m WHERE m.marInferior = :marInferior")
    , @NamedQuery(name = "Margen.findByMarIzquierdo", query = "SELECT m FROM Margen m WHERE m.marIzquierdo = :marIzquierdo")
    , @NamedQuery(name = "Margen.findByMarDerecho", query = "SELECT m FROM Margen m WHERE m.marDerecho = :marDerecho")
    , @NamedQuery(name = "Margen.findByMarEstado", query = "SELECT m FROM Margen m WHERE m.marEstado = :marEstado")
    , @NamedQuery(name = "Margen.findByMarUser", query = "SELECT m FROM Margen m WHERE m.marUser = :marUser")
    , @NamedQuery(name = "Margen.findByMarFHR", query = "SELECT m FROM Margen m WHERE m.marFHR = :marFHR")})
public class Margen implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Margen.findAll";
    public static final String BUSCAR_POR_MAR_TIPO = "Margen.findByMarTipo";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MarId")
    private Long marId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "MarTipo")
    private String marTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MarSuperior")
    private float marSuperior;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MarInferior")
    private float marInferior;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MarIzquierdo")
    private float marIzquierdo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MarDerecho")
    private float marDerecho;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "MarEstado")
    private String marEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MarUser")
    private String marUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MarFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date marFHR;

    public Margen() {
    }

    public Margen(Long marId) {
        this.marId = marId;
    }

    public Margen(Long marId, String marTipo, float marSuperior, float marInferior, float marIzquierdo, float marDerecho, String marEstado, String marUser, Date marFHR) {
        this.marId = marId;
        this.marTipo = marTipo;
        this.marSuperior = marSuperior;
        this.marInferior = marInferior;
        this.marIzquierdo = marIzquierdo;
        this.marDerecho = marDerecho;
        this.marEstado = marEstado;
        this.marUser = marUser;
        this.marFHR = marFHR;
    }

    public Long getMarId() {
        return marId;
    }

    public void setMarId(Long marId) {
        this.marId = marId;
    }

    public String getMarTipo() {
        return marTipo;
    }

    public void setMarTipo(String marTipo) {
        this.marTipo = marTipo;
    }

    public float getMarSuperior() {
        return marSuperior;
    }

    public void setMarSuperior(float marSuperior) {
        this.marSuperior = marSuperior;
    }

    public float getMarInferior() {
        return marInferior;
    }

    public void setMarInferior(float marInferior) {
        this.marInferior = marInferior;
    }

    public float getMarIzquierdo() {
        return marIzquierdo;
    }

    public void setMarIzquierdo(float marIzquierdo) {
        this.marIzquierdo = marIzquierdo;
    }

    public float getMarDerecho() {
        return marDerecho;
    }

    public void setMarDerecho(float marDerecho) {
        this.marDerecho = marDerecho;
    }

    public String getMarEstado() {
        return marEstado;
    }

    public void setMarEstado(String marEstado) {
        this.marEstado = marEstado;
    }

    public String getMarUser() {
        return marUser;
    }

    public void setMarUser(String marUser) {
        this.marUser = marUser;
    }

    public Date getMarFHR() {
        return marFHR;
    }

    public void setMarFHR(Date marFHR) {
        this.marFHR = marFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (marId != null ? marId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Margen)) {
            return false;
        }
        Margen other = (Margen) object;
        if ((this.marId == null && other.marId != null) || (this.marId != null && !this.marId.equals(other.marId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Margen[ marId=" + marId + " ]";
    }
    
}
