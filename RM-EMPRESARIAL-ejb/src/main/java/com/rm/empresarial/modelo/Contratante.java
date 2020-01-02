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
 * @author Prueba
 */
@Entity
@Table(name = "Contratante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contratante.findAll", query = "SELECT c FROM Contratante c")
    , @NamedQuery(name = "Contratante.findByConId", query = "SELECT c FROM Contratante c WHERE c.conId = :conId")
    , @NamedQuery(name = "Contratante.findByConCodigo", query = "SELECT c FROM Contratante c WHERE c.conCodigo = :conCodigo")
    , @NamedQuery(name = "Contratante.findByConDescripcion", query = "SELECT c FROM Contratante c WHERE c.conDescripcion = :conDescripcion")
    , @NamedQuery(name = "Contratante.findByConUser", query = "SELECT c FROM Contratante c WHERE c.conUser = :conUser")
    , @NamedQuery(name = "Contratante.findByConFHR", query = "SELECT c FROM Contratante c WHERE c.conFHR = :conFHR")
    , @NamedQuery(name = "Contratante.findByConCodigoEditar", query = "SELECT c FROM Contratante c WHERE c.conCodigo = :conCodigo AND c.conId != :conId")
    , @NamedQuery(name = "Contratante.findByConDescripcionEditar", query = "SELECT c FROM Contratante c WHERE c.conDescripcion = :conDescripcion AND c.conId != :conId")})

public class Contratante implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "Contratante.findAll";
    public static final String BUSCAR_POR_COD = "Contratante.findByConCodigo";
    public static final String BUSCAR_POR_DESC = "Contratante.findByConDescripcion";
    public static final String BUSCAR_POR_COD_EDITAR = "Contratante.findByConCodigoEditar";
    public static final String BUSCAR_POR_DESC_EDITAR = "Contratante.findByConDescripcionEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConId")
    private Long conId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ConCodigo")
    private String conCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ConDescripcion")
    private String conDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ConUser")
    private String conUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ConFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date conFHR;

    public Contratante() {
    }

    public Contratante(Long conId) {
        this.conId = conId;
    }

    public Contratante(Long conId, String conCodigo, String conDescripcion, String conUser, Date conFHR) {
        this.conId = conId;
        this.conCodigo = conCodigo;
        this.conDescripcion = conDescripcion;
        this.conUser = conUser;
        this.conFHR = conFHR;
    }

    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }

    public String getConCodigo() {
        return conCodigo;
    }

    public void setConCodigo(String conCodigo) {
        this.conCodigo = conCodigo;
    }

    public String getConDescripcion() {
        return conDescripcion;
    }

    public void setConDescripcion(String conDescripcion) {
        this.conDescripcion = conDescripcion;
    }

    public String getConUser() {
        return conUser;
    }

    public void setConUser(String conUser) {
        this.conUser = conUser;
    }

    public Date getConFHR() {
        return conFHR;
    }

    public void setConFHR(Date conFHR) {
        this.conFHR = conFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conId != null ? conId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contratante)) {
            return false;
        }
        Contratante other = (Contratante) object;
        if ((this.conId == null && other.conId != null) || (this.conId != null && !this.conId.equals(other.conId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Contratante[ conId=" + conId + " ]";
    }

}
