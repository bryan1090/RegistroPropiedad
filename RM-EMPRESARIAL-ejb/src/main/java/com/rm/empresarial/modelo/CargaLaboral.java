/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "CargaLaboral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargaLaboral.buscarTodos", query = "SELECT c FROM CargaLaboral c")
    , @NamedQuery(name = "CargaLaboral.buscarPorCarId", query = "SELECT c FROM CargaLaboral c WHERE c.carId = :carId")
    , @NamedQuery(name = "CargaLaboral.buscarPorCarAsignado", query = "SELECT c FROM CargaLaboral c WHERE c.carAsignado = :carAsignado")
    , @NamedQuery(name = "CargaLaboral.buscarPorCarUser", query = "SELECT c FROM CargaLaboral c WHERE c.carUser = :carUser")
    , @NamedQuery(name = "CargaLaboral.buscarPorCarFHR", query = "SELECT c FROM CargaLaboral c WHERE c.carFHR = :carFHR")
    , @NamedQuery(name = "CargaLaboral.buscarPorCarTipo", query = "SELECT c FROM CargaLaboral c WHERE c.carTipo = :carTipo")
    , @NamedQuery(name = "CargaLaboral.listarUsuariosCargaLaboralPorCarTipo", query = "SELECT c.usuId FROM CargaLaboral c WHERE c.carTipo = :carTipo")
})
public class CargaLaboral implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_CARGA_LABORAL = "SELECT TOP 1 *  from CargaLaboral c"
            + " INNER JOIN Usuario u on u.UsuId=c.UsuId "
            + " ORDER BY c.CarAsignado ASC";
    public static final String BUSCAR_POR_CARGA_LABORAL_TIPO = "SELECT TOP 1 *  from CargaLaboral c"
            + " INNER JOIN Usuario u on u.UsuId=c.UsuId "
            + " WHERE c.carTipo= ?"
            + " ORDER BY c.CarAsignado ASC ";
    public static final String LISTAR_USUARIOS_DE_CARGA_LABORAL = "CargaLaboral.listarUsuariosCargaLaboralPorCarTipo";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarId")
    private Long carId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CarAsignado")
    private short carAsignado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CarUser")
    private String carUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CarFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar carFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CarTipo")
    private String carTipo;
    @Column(name = "CarCantidad")
    private Long carCantidad;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;
    @Transient
    @Getter
    @Setter
    private String carTipoCompleto;

    public CargaLaboral() {
    }

    public CargaLaboral(Long carId) {
        this.carId = carId;
    }

    public CargaLaboral(Long carId, short carAsignado, String carUser, Calendar carFHR, String carTipo) {
        this.carId = carId;
        this.carAsignado = carAsignado;
        this.carUser = carUser;
        this.carFHR = carFHR;
        this.carTipo = carTipo;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public short getCarAsignado() {
        return carAsignado;
    }

    public void setCarAsignado(short carAsignado) {
        this.carAsignado = carAsignado;
    }

    public String getCarUser() {
        return carUser;
    }

    public void setCarUser(String carUser) {
        this.carUser = carUser;
    }

    public Calendar getCarFHR() {
        return carFHR;
    }

    public void setCarFHR(Calendar carFHR) {
        this.carFHR = carFHR;
    }

    public String getCarTipo() {
        return carTipo;
    }

    public void setCarTipo(String carTipo) {
        this.carTipo = carTipo;
    }
    
    public Long getCarCantidad() {
        return carCantidad;
    }

    public void setCarCantidad(Long carCantidad) {
        this.carCantidad = carCantidad;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carId != null ? carId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargaLaboral)) {
            return false;
        }
        CargaLaboral other = (CargaLaboral) object;
        if ((this.carId == null && other.carId != null) || (this.carId != null && !this.carId.equals(other.carId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CargaLaboral[ carId=" + carId + " ]";
    }

}
