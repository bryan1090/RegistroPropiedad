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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "TipoSuspension")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoSuspension.findAll", query = "SELECT t FROM TipoSuspension t ORDER BY t.tpsNombre")
    , @NamedQuery(name = "TipoSuspension.findByTpsId", query = "SELECT t FROM TipoSuspension t WHERE t.tpsId = :tpsId")
    , @NamedQuery(name = "TipoSuspension.findByTpsNombre", query = "SELECT t FROM TipoSuspension t WHERE t.tpsNombre = :tpsNombre")
    , @NamedQuery(name = "TipoSuspension.findByTpsEstado", query = "SELECT t FROM TipoSuspension t WHERE t.tpsEstado = :tpsEstado")
    , @NamedQuery(name = "TipoSuspension.findByTpsUser", query = "SELECT t FROM TipoSuspension t WHERE t.tpsUser = :tpsUser")
    , @NamedQuery(name = "TipoSuspension.findByTpsFHR", query = "SELECT t FROM TipoSuspension t WHERE t.tpsFHR = :tpsFHR")
    , @NamedQuery(name = "TipoSuspension.findByTpsNombreEditar", query = "SELECT t FROM TipoSuspension t WHERE t.tpsNombre = :tpsNombre AND t.tpsId != :tpsId")})
public class TipoSuspension implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoSuspension.findAll";
    public static final String OBTENER_POR_ID = "TipoSuspension.findByTpsId";
    public static final String BUSCAR_POR_NOM = "TipoSuspension.findByTpsNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "TipoSuspension.findByTpsNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TpsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tpsId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "TpsNombre")
    private String tpsNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TpsEstado")
    private String tpsEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpsUser")
    private String tpsUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpsFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpsFHR;
    
     @Column(name = "TpsRevision")
    private Boolean tpsRevision;
    @Column(name = "TpsInscripcion")
    private Boolean tpsInscripcion;

    public TipoSuspension() {
    }

    public TipoSuspension(Long tpsId) {
        this.tpsId = tpsId;
    }

    public TipoSuspension(Long tpsId, String tpsNombre, String tpsEstado, String tpsUser, Date tpsFHR) {
        this.tpsId = tpsId;
        this.tpsNombre = tpsNombre;
        this.tpsEstado = tpsEstado;
        this.tpsUser = tpsUser;
        this.tpsFHR = tpsFHR;
    }

    public Long getTpsId() {
        return tpsId;
    }

    public void setTpsId(Long tpsId) {
        this.tpsId = tpsId;
    }

    public String getTpsNombre() {
        return tpsNombre;
    }

    public void setTpsNombre(String tpsNombre) {
        this.tpsNombre = tpsNombre;
    }

    public String getTpsEstado() {
        return tpsEstado;
    }

    public void setTpsEstado(String tpsEstado) {
        this.tpsEstado = tpsEstado;
    }

    public String getTpsUser() {
        return tpsUser;
    }

    public void setTpsUser(String tpsUser) {
        this.tpsUser = tpsUser;
    }

    public Date getTpsFHR() {
        return tpsFHR;
    }

    public void setTpsFHR(Date tpsFHR) {
        this.tpsFHR = tpsFHR;
    }
    
    public Boolean getTpsRevision() {
        return tpsRevision;
    }

    public void setTpsRevision(Boolean tpsRevision) {
        this.tpsRevision = tpsRevision;
    }

    public Boolean getTpsInscripcion() {
        return tpsInscripcion;
    }

    public void setTpsInscripcion(Boolean tpsInscripcion) {
        this.tpsInscripcion = tpsInscripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpsId != null ? tpsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoSuspension)) {
            return false;
        }
        TipoSuspension other = (TipoSuspension) object;
        if ((this.tpsId == null && other.tpsId != null) || (this.tpsId != null && !this.tpsId.equals(other.tpsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TipoSuspension[ tpsId=" + tpsId + " ]";
    }

}
