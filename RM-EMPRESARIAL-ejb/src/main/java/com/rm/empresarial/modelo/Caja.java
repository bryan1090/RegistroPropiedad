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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caja.findAll", query = "SELECT c FROM Caja c")
    , @NamedQuery(name = "Caja.findByCajId", query = "SELECT c FROM Caja c WHERE c.cajId = :cajId")
    , @NamedQuery(name = "Caja.findByCajNombre", query = "SELECT c FROM Caja c WHERE c.cajNombre = :cajNombre")
    , @NamedQuery(name = "Caja.findByCajEstado", query = "SELECT c FROM Caja c WHERE c.cajEstado = :cajEstado")
    , @NamedQuery(name = "Caja.findByCajUser", query = "SELECT c FROM Caja c WHERE c.cajUser = :cajUser")
    , @NamedQuery(name = "Caja.findByCajFHR", query = "SELECT c FROM Caja c WHERE c.cajFHR = :cajFHR")
    , @NamedQuery(name = "Caja.validacionIngreso", query = "SELECT c FROM Caja c WHERE c.cajNombre = :cajNombre AND c.sucId.sucId = :sucId")
    , @NamedQuery(name = "Caja.validacionIngresoEditar", query = "SELECT c FROM Caja c WHERE c.cajNombre = :cajNombre AND c.sucId.sucId = :sucId AND c.cajId != :cajId")
    , @NamedQuery(name = "Caja.findByCajNombreEditar", query = "SELECT c FROM Caja c WHERE c.cajNombre = :cajNombre AND c.cajId != :cajId")})
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_ID = "Caja.findByCajId";
    public static final String LISTAR_TODO = "Caja.findAll";
    public static final String VALIDACION_INGRESO = "Caja.validacionIngreso";
    public static final String BUSCAR_POR_NOM = "Caja.findByCajNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "Caja.findByCajNombreEditar";
    public static final String VALIDACION_INGRESO_EDITAR = "Caja.validacionIngresoEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CajId")
    private Long cajId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CajNombre")
    private String cajNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CajEstado")
    private String cajEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CajUser")
    private String cajUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CajFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cajFHR;
    @JoinColumn(name = "SucId", referencedColumnName = "SucId")
    @ManyToOne(optional = false)
    private Sucursal sucId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cajId")
    private List<Proforma> proformaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cajId")
    private List<UsuarioCaja> usuarioCajaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cajId")
    private List<Factura> facturaList;

    public Caja() {
    }

    public Caja(Long cajId) {
        this.cajId = cajId;
    }

    public Caja(Long cajId, String cajNombre, String cajEstado, String cajUser, Date cajFHR) {
        this.cajId = cajId;
        this.cajNombre = cajNombre;
        this.cajEstado = cajEstado;
        this.cajUser = cajUser;
        this.cajFHR = cajFHR;
    }

    public Long getCajId() {
        return cajId;
    }

    public void setCajId(Long cajId) {
        this.cajId = cajId;
    }

    public String getCajNombre() {
        return cajNombre;
    }

    public void setCajNombre(String cajNombre) {
        this.cajNombre = cajNombre;
    }

    public String getCajEstado() {
        return cajEstado;
    }

    public void setCajEstado(String cajEstado) {
        this.cajEstado = cajEstado;
    }

    public String getCajUser() {
        return cajUser;
    }

    public void setCajUser(String cajUser) {
        this.cajUser = cajUser;
    }

    public Date getCajFHR() {
        return cajFHR;
    }

    public void setCajFHR(Date cajFHR) {
        this.cajFHR = cajFHR;
    }

    public Sucursal getSucId() {
        return sucId;
    }

    public void setSucId(Sucursal sucId) {
        this.sucId = sucId;
    }

    @XmlTransient
    public List<Proforma> getProformaList() {
        return proformaList;
    }

    public void setProformaList(List<Proforma> proformaList) {
        this.proformaList = proformaList;
    }

    @XmlTransient
    public List<UsuarioCaja> getUsuarioCajaList() {
        return usuarioCajaList;
    }

    public void setUsuarioCajaList(List<UsuarioCaja> usuarioCajaList) {
        this.usuarioCajaList = usuarioCajaList;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cajId != null ? cajId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caja)) {
            return false;
        }
        Caja other = (Caja) object;
        if ((this.cajId == null && other.cajId != null) || (this.cajId != null && !this.cajId.equals(other.cajId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Caja[ cajId=" + cajId + " ]";
    }

}
