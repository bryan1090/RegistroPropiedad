/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JeanCarlos
 */
@Entity
@Table(name = "DocumentoEntrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoEntrega.findAll", query = "SELECT d FROM DocumentoEntrega d ORDER BY d.dteDescripcion")
    , @NamedQuery(name = "DocumentoEntrega.findByDteId", query = "SELECT d FROM DocumentoEntrega d WHERE d.dteId = :dteId")
    , @NamedQuery(name = "DocumentoEntrega.findByDteDescripcion", query = "SELECT d FROM DocumentoEntrega d WHERE d.dteDescripcion = :dteDescripcion")
    , @NamedQuery(name = "DocumentoEntrega.findByDteEstado", query = "SELECT d FROM DocumentoEntrega d WHERE d.dteEstado = :dteEstado")
    , @NamedQuery(name = "DocumentoEntrega.findByDteUser", query = "SELECT d FROM DocumentoEntrega d WHERE d.dteUser = :dteUser")
    , @NamedQuery(name = "DocumentoEntrega.findByDteFHR", query = "SELECT d FROM DocumentoEntrega d WHERE d.dteFHR = :dteFHR")})
public class DocumentoEntrega implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "DocumentoEntrega.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DteId")
    private Long dteId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DteDescripcion")
    private String dteDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "DteEstado")
    private String dteEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DteUser")
    private String dteUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DteFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dteFHR;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dteId")
    private List<DocumentoEntregaTramite> documentoEntregaTramiteList;

    public DocumentoEntrega() {
    }

    public DocumentoEntrega(Long dteId) {
        this.dteId = dteId;
    }

    public DocumentoEntrega(Long dteId, String dteDescripcion, String dteEstado, String dteUser, Date dteFHR) {
        this.dteId = dteId;
        this.dteDescripcion = dteDescripcion;
        this.dteEstado = dteEstado;
        this.dteUser = dteUser;
        this.dteFHR = dteFHR;
    }

    public Long getDteId() {
        return dteId;
    }

    public void setDteId(Long dteId) {
        this.dteId = dteId;
    }

    public String getDteDescripcion() {
        return dteDescripcion;
    }

    public void setDteDescripcion(String dteDescripcion) {
        this.dteDescripcion = dteDescripcion;
    }

    public String getDteEstado() {
        return dteEstado;
    }

    public void setDteEstado(String dteEstado) {
        this.dteEstado = dteEstado;
    }

    public String getDteUser() {
        return dteUser;
    }

    public void setDteUser(String dteUser) {
        this.dteUser = dteUser;
    }

    public Date getDteFHR() {
        return dteFHR;
    }

    public void setDteFHR(Date dteFHR) {
        this.dteFHR = dteFHR;
    }
    
    @XmlTransient
    public List<DocumentoEntregaTramite> getDocumentoEntregaTramiteList() {
        return documentoEntregaTramiteList;
    }

    public void setDocumentoEntregaTramiteList(List<DocumentoEntregaTramite> documentoEntregaTramiteList) {
        this.documentoEntregaTramiteList = documentoEntregaTramiteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dteId != null ? dteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoEntrega)) {
            return false;
        }
        DocumentoEntrega other = (DocumentoEntrega) object;
        if ((this.dteId == null && other.dteId != null) || (this.dteId != null && !this.dteId.equals(other.dteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DocumentoEntrega[ dteId=" + dteId + " ]";
    }
    
}
