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
 * @author DJimenez
 */
@Entity
@Table(name = "TipoDocumentoWeb")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDocumentoWeb.findAll", query = "SELECT t FROM TipoDocumentoWeb t ORDER BY t.tdwNombre")
    , @NamedQuery(name = "TipoDocumentoWeb.findByTdwId", query = "SELECT t FROM TipoDocumentoWeb t WHERE t.tdwId = :tdwId")
    , @NamedQuery(name = "TipoDocumentoWeb.findByTdwNombre", query = "SELECT t FROM TipoDocumentoWeb t WHERE t.tdwNombre = :tdwNombre")
    , @NamedQuery(name = "TipoDocumentoWeb.findByTdwEstado", query = "SELECT t FROM TipoDocumentoWeb t WHERE t.tdwEstado = :tdwEstado")
    , @NamedQuery(name = "TipoDocumentoWeb.findByTdwFHR", query = "SELECT t FROM TipoDocumentoWeb t WHERE t.tdwFHR = :tdwFHR")
    , @NamedQuery(name = "TipoDocumentoWeb.findByTdwUser", query = "SELECT t FROM TipoDocumentoWeb t WHERE t.tdwUser = :tdwUser")
    , @NamedQuery(name = "TipoDocumentoWeb.findByTdwDescripcion", query = "SELECT t FROM TipoDocumentoWeb t WHERE t.tdwDescripcion = :tdwDescripcion")
    , @NamedQuery(name = "TipoDocumentoWeb.findByTdwNombreEditar", query = "SELECT t FROM TipoDocumentoWeb t WHERE t.tdwNombre = :tdwNombre AND t.tdwId != :tdwId")})
public class TipoDocumentoWeb implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoDocumentoWeb.findAll";
    public static final String BUSCAR_POR_NOM = "TipoDocumentoWeb.findByTdwNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "TipoDocumentoWeb.findByTdwNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TdwId")
    private Long tdwId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TdwNombre")
    private String tdwNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TdwEstado")
    private String tdwEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdwFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tdwFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdwUser")
    private String tdwUser;
    @Size(max = 100)
    @Column(name = "TdwDescripcion")
    private String tdwDescripcion;

    public TipoDocumentoWeb() {
    }

    public TipoDocumentoWeb(Long tdwId) {
        this.tdwId = tdwId;
    }

    public TipoDocumentoWeb(Long tdwId, String tdwNombre, String tdwEstado, Date tdwFHR, String tdwUser) {
        this.tdwId = tdwId;
        this.tdwNombre = tdwNombre;
        this.tdwEstado = tdwEstado;
        this.tdwFHR = tdwFHR;
        this.tdwUser = tdwUser;
    }

    public Long getTdwId() {
        return tdwId;
    }

    public void setTdwId(Long tdwId) {
        this.tdwId = tdwId;
    }

    public String getTdwNombre() {
        return tdwNombre;
    }

    public void setTdwNombre(String tdwNombre) {
        this.tdwNombre = tdwNombre;
    }

    public String getTdwEstado() {
        return tdwEstado;
    }

    public void setTdwEstado(String tdwEstado) {
        this.tdwEstado = tdwEstado;
    }

    public Date getTdwFHR() {
        return tdwFHR;
    }

    public void setTdwFHR(Date tdwFHR) {
        this.tdwFHR = tdwFHR;
    }

    public String getTdwUser() {
        return tdwUser;
    }

    public void setTdwUser(String tdwUser) {
        this.tdwUser = tdwUser;
    }

    public String getTdwDescripcion() {
        return tdwDescripcion;
    }

    public void setTdwDescripcion(String tdwDescripcion) {
        this.tdwDescripcion = tdwDescripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdwId != null ? tdwId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDocumentoWeb)) {
            return false;
        }
        TipoDocumentoWeb other = (TipoDocumentoWeb) object;
        if ((this.tdwId == null && other.tdwId != null) || (this.tdwId != null && !this.tdwId.equals(other.tdwId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoDocumentoWeb[ tdwId=" + tdwId + " ]";
    }

}
