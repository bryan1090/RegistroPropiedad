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
import javax.persistence.CascadeType;
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
@Table(name = "DocumentoEntregaTramite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoEntregaTramite.findAll", query = "SELECT d FROM DocumentoEntregaTramite d ORDER BY d.dteId.dteDescripcion")
    , @NamedQuery(name = "DocumentoEntregaTramite.findByDetId", query = "SELECT d FROM DocumentoEntregaTramite d WHERE d.detId = :detId")
    , @NamedQuery(name = "DocumentoEntregaTramite.findByDetUser", query = "SELECT d FROM DocumentoEntregaTramite d WHERE d.detUser = :detUser")
    , @NamedQuery(name = "DocumentoEntregaTramite.findByDetFHR", query = "SELECT d FROM DocumentoEntregaTramite d WHERE d.detFHR = :detFHR")
    , @NamedQuery(name = "DocumentoEntregaTramite.validacionIngreso", query = "SELECT d FROM DocumentoEntregaTramite d WHERE d.dteId.dteId = :dteId AND d.ttrId.ttrId = :ttrId")
    , @NamedQuery(name = "DocumentoEntregaTramite.validacionIngresoEditar", query = "SELECT d FROM DocumentoEntregaTramite d WHERE d.dteId.dteId = :dteId AND d.ttrId.ttrId = :ttrId AND d.detId != :detId")
    , @NamedQuery(name = "DocumentoEntregaTramite.listarPorTipoTramite", query = "SELECT d FROM DocumentoEntregaTramite d WHERE d.ttrId.ttrId = :ttrId")
    , @NamedQuery(name = "DocumentoEntregaTramite.buscarPorDetIdYttrId", query = "SELECT d FROM DocumentoEntregaTramite d WHERE d.dteId.dteId = :dteId AND d.ttrId.ttrId = :ttrId")})
public class DocumentoEntregaTramite implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_DTEID_Y_TTRID =  "DocumentoEntregaTramite.buscarPorDetIdYttrId";
    public static final String LISTAR_TODO = "DocumentoEntregaTramite.findAll";
    public static final String VALIDACION_INGRESO = "DocumentoEntregaTramite.validacionIngreso";
    public static final String VALIDACION_INGRESO_EDITAR = "DocumentoEntregaTramite.validacionIngresoEditar";
    public static final String LISTAR_POR_TIPO_TRAMITE = "DocumentoEntregaTramite.listarPorTipoTramite";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DetId")
    private Long detId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DetUser")
    private String detUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DetFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date detFHR;
    @JoinColumn(name = "DteId", referencedColumnName = "DteId")
    @ManyToOne(optional = false)
    private DocumentoEntrega dteId;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detId")
    private List<TramiteEntrega> tramiteEntregaList;

    public DocumentoEntregaTramite() {
    }

    public DocumentoEntregaTramite(Long detId) {
        this.detId = detId;
    }

    public DocumentoEntregaTramite(Long detId, String detUser, Date detFHR) {
        this.detId = detId;
        this.detUser = detUser;
        this.detFHR = detFHR;
    }
    
    public DocumentoEntregaTramite(DocumentoEntrega dteId, TipoTramite ttrId) {
        this.dteId = dteId;
        this.ttrId = ttrId;
    }

    public Long getDetId() {
        return detId;
    }

    public void setDetId(Long detId) {
        this.detId = detId;
    }

    public String getDetUser() {
        return detUser;
    }

    public void setDetUser(String detUser) {
        this.detUser = detUser;
    }

    public Date getDetFHR() {
        return detFHR;
    }

    public void setDetFHR(Date detFHR) {
        this.detFHR = detFHR;
    }

    public DocumentoEntrega getDteId() {
        return dteId;
    }

    public void setDteId(DocumentoEntrega dteId) {
        this.dteId = dteId;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }
    
    @XmlTransient
    public List<TramiteEntrega> getTramiteEntregaList() {
        return tramiteEntregaList;
    }

    public void setTramiteEntregaList(List<TramiteEntrega> tramiteEntregaList) {
        this.tramiteEntregaList = tramiteEntregaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detId != null ? detId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoEntregaTramite)) {
            return false;
        }
        DocumentoEntregaTramite other = (DocumentoEntregaTramite) object;
        if ((this.detId == null && other.detId != null) || (this.detId != null && !this.detId.equals(other.detId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DocumentoEntregaTramite[ detId=" + detId + " ]";
    }
    
}
