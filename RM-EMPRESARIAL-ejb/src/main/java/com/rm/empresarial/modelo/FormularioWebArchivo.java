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
@Table(name = "FormularioWebArchivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormularioWebArchivo.findAll", query = "SELECT f FROM FormularioWebArchivo f")
    , @NamedQuery(name = "FormularioWebArchivo.findByFwaId", query = "SELECT f FROM FormularioWebArchivo f WHERE f.fwaId = :fwaId")
    , @NamedQuery(name = "FormularioWebArchivo.findByFwaNombreArchivo", query = "SELECT f FROM FormularioWebArchivo f WHERE f.fwaNombreArchivo = :fwaNombreArchivo")
    , @NamedQuery(name = "FormularioWebArchivo.findByFwaPath", query = "SELECT f FROM FormularioWebArchivo f WHERE f.fwaPath = :fwaPath")
    , @NamedQuery(name = "FormularioWebArchivo.findByFwaEstado", query = "SELECT f FROM FormularioWebArchivo f WHERE f.fwaEstado = :fwaEstado")
    , @NamedQuery(name = "FormularioWebArchivo.findByFwaFHR", query = "SELECT f FROM FormularioWebArchivo f WHERE f.fwaFHR = :fwaFHR")
    , @NamedQuery(name = "FormularioWebArchivo.findByFwaUser", query = "SELECT f FROM FormularioWebArchivo f WHERE f.fwaUser = :fwaUser")})
public class FormularioWebArchivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FwaId")
    private Integer fwaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FwaNombreArchivo")
    private String fwaNombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "FwaPath")
    private String fwaPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "FwaEstado")
    private String fwaEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FwaFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fwaFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FwaUser")
    private String fwaUser;
    @JoinColumn(name = "FlwId", referencedColumnName = "FlwId")
    @ManyToOne(optional = false)
    private FormularioWeb flwId;

    public FormularioWebArchivo() {
    }

    public FormularioWebArchivo(Integer fwaId) {
        this.fwaId = fwaId;
    }

    public FormularioWebArchivo(Integer fwaId, String fwaNombreArchivo, String fwaPath, String fwaEstado, Date fwaFHR, String fwaUser) {
        this.fwaId = fwaId;
        this.fwaNombreArchivo = fwaNombreArchivo;
        this.fwaPath = fwaPath;
        this.fwaEstado = fwaEstado;
        this.fwaFHR = fwaFHR;
        this.fwaUser = fwaUser;
    }

    public Integer getFwaId() {
        return fwaId;
    }

    public void setFwaId(Integer fwaId) {
        this.fwaId = fwaId;
    }

    public String getFwaNombreArchivo() {
        return fwaNombreArchivo;
    }

    public void setFwaNombreArchivo(String fwaNombreArchivo) {
        this.fwaNombreArchivo = fwaNombreArchivo;
    }

    public String getFwaPath() {
        return fwaPath;
    }

    public void setFwaPath(String fwaPath) {
        this.fwaPath = fwaPath;
    }

    public String getFwaEstado() {
        return fwaEstado;
    }

    public void setFwaEstado(String fwaEstado) {
        this.fwaEstado = fwaEstado;
    }

    public Date getFwaFHR() {
        return fwaFHR;
    }

    public void setFwaFHR(Date fwaFHR) {
        this.fwaFHR = fwaFHR;
    }

    public String getFwaUser() {
        return fwaUser;
    }

    public void setFwaUser(String fwaUser) {
        this.fwaUser = fwaUser;
    }

    public FormularioWeb getFlwId() {
        return flwId;
    }

    public void setFlwId(FormularioWeb flwId) {
        this.flwId = flwId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fwaId != null ? fwaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioWebArchivo)) {
            return false;
        }
        FormularioWebArchivo other = (FormularioWebArchivo) object;
        if ((this.fwaId == null && other.fwaId != null) || (this.fwaId != null && !this.fwaId.equals(other.fwaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.FormularioWebArchivo[ fwaId=" + fwaId + " ]";
    }
    
}
