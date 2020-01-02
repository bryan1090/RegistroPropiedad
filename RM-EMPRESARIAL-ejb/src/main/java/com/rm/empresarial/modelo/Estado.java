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
 * @author Richard
 */
@Entity
@Table(name = "Estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e ORDER BY e.estCodigo")
    , @NamedQuery(name = "Estado.findByEstId", query = "SELECT e FROM Estado e WHERE e.estId = :estId")
    , @NamedQuery(name = "Estado.findByEstCodigo", query = "SELECT e FROM Estado e WHERE e.estCodigo = :estCodigo")
    , @NamedQuery(name = "Estado.findByEstDescripcion", query = "SELECT e FROM Estado e WHERE e.estDescripcion = :estDescripcion")
    , @NamedQuery(name = "Estado.findByEstUser", query = "SELECT e FROM Estado e WHERE e.estUser = :estUser")
    , @NamedQuery(name = "Estado.findByEstFHR", query = "SELECT e FROM Estado e WHERE e.estFHR = :estFHR")
    , @NamedQuery(name = "Estado.findByEstCodigoEditar", query = "SELECT e FROM Estado e WHERE e.estCodigo = :estCodigo AND e.estId != :estId")
    , @NamedQuery(name = "Estado.findByEstDescripcionEditar", query = "SELECT e FROM Estado e WHERE e.estDescripcion = :estDescripcion AND e.estId != :estId")})
public class Estado implements Serializable {

    @Size(max = 2)
    @Column(name = "EstOrdena")
    private String estOrdena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estId")
    private List<TiempoProceso> tiempoProcesoList;

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "Estado.findAll";
    public static final String LISTAR_PORID = "Estado.findByEstId";
    public static final String BUSCAR_POR_COD = "Estado.findByEstCodigo";
    public static final String BUSCAR_POR_DESC = "Estado.findByEstDescripcion";
    public static final String BUSCAR_POR_COD_EDITAR = "Estado.findByEstCodigoEditar";
    public static final String BUSCAR_POR_DESC_EDITAR = "Estado.findByEstDescripcionEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EstId")
    private Long estId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "EstCodigo")
    private String estCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EstDescripcion")
    private String estDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EstUser")
    private String estUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EstFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estFHR;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estId")
    private List<EstadoAtributo> estadoAtributoList;

    public Estado() {
    }

    public Estado(Long estId) {
        this.estId = estId;
    }

    public Estado(Long estId, String estCodigo, String estDescripcion, String estUser, Date estFHR) {
        this.estId = estId;
        this.estCodigo = estCodigo;
        this.estDescripcion = estDescripcion;
        this.estUser = estUser;
        this.estFHR = estFHR;
    }

    public Long getEstId() {
        return estId;
    }

    public void setEstId(Long estId) {
        this.estId = estId;
    }

    public String getEstCodigo() {
        return estCodigo;
    }

    public void setEstCodigo(String estCodigo) {
        this.estCodigo = estCodigo;
    }

    public String getEstDescripcion() {
        return estDescripcion;
    }

    public void setEstDescripcion(String estDescripcion) {
        this.estDescripcion = estDescripcion;
    }

    public String getEstUser() {
        return estUser;
    }

    public void setEstUser(String estUser) {
        this.estUser = estUser;
    }

    public Date getEstFHR() {
        return estFHR;
    }

    public void setEstFHR(Date estFHR) {
        this.estFHR = estFHR;
    }

    @XmlTransient
    public List<EstadoAtributo> getEstadoAtributoList() {
        return estadoAtributoList;
    }

    public void setEstadoAtributoList(List<EstadoAtributo> estadoAtributoList) {
        this.estadoAtributoList = estadoAtributoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estId != null ? estId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.estId == null && other.estId != null) || (this.estId != null && !this.estId.equals(other.estId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.Estado[ estId=" + estId + " ]";
    }

    public String getEstOrdena() {
        return estOrdena;
    }

    public void setEstOrdena(String estOrdena) {
        this.estOrdena = estOrdena;
    }

    @XmlTransient
    public List<TiempoProceso> getTiempoProcesoList() {
        return tiempoProcesoList;
    }

    public void setTiempoProcesoList(List<TiempoProceso> tiempoProcesoList) {
        this.tiempoProcesoList = tiempoProcesoList;
    }

}
