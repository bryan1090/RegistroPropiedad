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
 * @author Wilson
 */
@Entity
@Table(name = "Notaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notaria.BuscarTodos", query = "SELECT n FROM Notaria n ORDER BY n.notNotario")
    , @NamedQuery(name = "Notaria.BuscarPorId", query = "SELECT n FROM Notaria n WHERE n.notId = :notId")
    , @NamedQuery(name = "Notaria.BuscarPorNumero", query = "SELECT n FROM Notaria n WHERE n.notNumero = :notNumero")
    , @NamedQuery(name = "Notaria.BuscarPorNumeroPorCiudad", query = "SELECT n FROM Notaria n WHERE n.notNumero = :notNumero AND n.ciuId = :ciuId")
    , @NamedQuery(name = "Notaria.BuscarPorNotario", query = "SELECT n FROM Notaria n WHERE n.notNotario = :notNotario")
    , @NamedQuery(name = "Notaria.BuscarPorDireccion", query = "SELECT n FROM Notaria n WHERE n.notDireccion = :notDireccion")
    , @NamedQuery(name = "Notaria.BuscarPorUser", query = "SELECT n FROM Notaria n WHERE n.notUser = :notUser")
    , @NamedQuery(name = "Notaria.BuscarPorFHR", query = "SELECT n FROM Notaria n WHERE n.notFHR = :notFHR")
    , @NamedQuery(name = "Notaria.BuscarPorCiuId", query = "SELECT n FROM Notaria n WHERE n.ciuId = :ciuId")
    , @NamedQuery(name = "Notaria.BuscarPorNumeroEditar", query = "SELECT n FROM Notaria n WHERE n.notNumero = :notNumero AND n.notId != :notId")})
public class Notaria implements Serializable {

    private static final long serialVersionUID = -2246240289385548236L;

    public static final String LISTAR_TODO = "Notaria.BuscarTodos";
    public static final String BUSCAR_POR_NUM = "Notaria.BuscarPorNumero";
    public static final String BUSCAR_POR_NUM_POR_ID_CIUDAD = "Notaria.BuscarPorNumeroPorCiudad";
    public static final String BUSCAR_POR_NUM_EDITAR = "Notaria.BuscarPorNumeroEditar";
    public static final String LISTAR_POR_CIUDAD = "Notaria.BuscarPorCiuId";

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotId")
    private Long notId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "NotNumero")
    private short notNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NotNotario")
    private String notNotario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NotDireccion")
    private String notDireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NotUser")
    private String notUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NotFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notFHR;
    @JoinColumn(name = "CiuId", referencedColumnName = "CiuId")
    @ManyToOne(optional = false)
    private Ciudad ciuId;

    public Notaria() {
    }

    public Notaria(Long notId) {
        this.notId = notId;
    }

    public Notaria(Long notId, short notNumero, String notNotario, String notDireccion, String notUser, Date notFHR) {
        this.notId = notId;
        this.notNumero = notNumero;
        this.notNotario = notNotario;
        this.notDireccion = notDireccion;
        this.notUser = notUser;
        this.notFHR = notFHR;
    }

    public Long getNotId() {
        return notId;
    }

    public void setNotId(Long notId) {
        this.notId = notId;
    }

    public short getNotNumero() {
        return notNumero;
    }

    public void setNotNumero(short notNumero) {
        this.notNumero = notNumero;
    }

    public String getNotNotario() {
        return notNotario;
    }

    public void setNotNotario(String notNotario) {
        this.notNotario = notNotario;
    }

    public String getNotDireccion() {
        return notDireccion;
    }

    public void setNotDireccion(String notDireccion) {
        this.notDireccion = notDireccion;
    }

    public String getNotUser() {
        return notUser;
    }

    public void setNotUser(String notUser) {
        this.notUser = notUser;
    }

    public Date getNotFHR() {
        return notFHR;
    }

    public void setNotFHR(Date notFHR) {
        this.notFHR = notFHR;
    }

    public Ciudad getCiuId() {
        return ciuId;
    }

    public void setCiuId(Ciudad ciuId) {
        this.ciuId = ciuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notId != null ? notId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notaria)) {
            return false;
        }
        Notaria other = (Notaria) object;
        if ((this.notId == null && other.notId != null) || (this.notId != null && !this.notId.equals(other.notId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.Notaria[ notId=" + notId + " ]";
    }

}
