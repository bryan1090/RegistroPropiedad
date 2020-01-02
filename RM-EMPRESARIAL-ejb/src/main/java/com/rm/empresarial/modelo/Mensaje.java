/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "Mensaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m")
    , @NamedQuery(name = "Mensaje.findByMsjId", query = "SELECT m FROM Mensaje m WHERE m.msjId = :msjId")
    , @NamedQuery(name = "Mensaje.findByMsjNumero", query = "SELECT m FROM Mensaje m WHERE m.msjNumero = :msjNumero")
    , @NamedQuery(name = "Mensaje.findByMsjDescripcion", query = "SELECT m FROM Mensaje m WHERE m.msjDescripcion = :msjDescripcion")
    , @NamedQuery(name = "Mensaje.findByMsjTipo", query = "SELECT m FROM Mensaje m WHERE m.msjTipo = :msjTipo")
    , @NamedQuery(name = "Mensaje.findByMsjProblema", query = "SELECT m FROM Mensaje m WHERE m.msjProblema = :msjProblema")
    , @NamedQuery(name = "Mensaje.findByMsjSolucion", query = "SELECT m FROM Mensaje m WHERE m.msjSolucion = :msjSolucion")
    , @NamedQuery(name = "Mensaje.findByMsjColor", query = "SELECT m FROM Mensaje m WHERE m.msjColor = :msjColor")
    , @NamedQuery(name = "Mensaje.findByMsjNegrilla", query = "SELECT m FROM Mensaje m WHERE m.msjNegrilla = :msjNegrilla")
    , @NamedQuery(name = "Mensaje.findByMsjTamanio", query = "SELECT m FROM Mensaje m WHERE m.msjTamanio = :msjTamanio")
    , @NamedQuery(name = "Mensaje.findByMsjNumeroEditar", query = "SELECT m FROM Mensaje m WHERE m.msjNumero = :msjNumero AND m.msjId != :msjId")
    , @NamedQuery(name = "Mensaje.findByMsjDescripcionEditar", query = "SELECT m FROM Mensaje m WHERE m.msjDescripcion = :msjDescripcion AND m.msjId != :msjId")})

public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "Mensaje.findAll";
    public static final String BUSCAR_POR_NUM = "Mensaje.findByMsjNumero";
    public static final String BUSCAR_POR_DESC = "Mensaje.findByMsjDescripcion";
    public static final String BUSCAR_POR_NUM_EDITAR = "Mensaje.findByMsjNumeroEditar";
    public static final String BUSCAR_POR_DESC_EDITAR = "Mensaje.findByMsjDescripcionEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MsjId")
    private Long msjId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MsjNumero")
    private short msjNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "MsjDescripcion")
    private String msjDescripcion;
    @Size(max = 40)
    @Column(name = "MsjTipo")
    private String msjTipo;
    @Size(max = 200)
    @Column(name = "MsjProblema")
    private String msjProblema;
    @Size(max = 200)
    @Column(name = "MsjSolucion")
    private String msjSolucion;
    @Size(max = 40)
    @Column(name = "MsjColor")
    private String msjColor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "MsjNegrilla")
    private String msjNegrilla;
    @Column(name = "MsjTamanio")
    private Short msjTamanio;

    public Mensaje() {
    }

    public Mensaje(Long msjId) {
        this.msjId = msjId;
    }

    public Mensaje(Long msjId, short msjNumero, String msjDescripcion, String msjNegrilla) {
        this.msjId = msjId;
        this.msjNumero = msjNumero;
        this.msjDescripcion = msjDescripcion;
        this.msjNegrilla = msjNegrilla;
    }

    public Long getMsjId() {
        return msjId;
    }

    public void setMsjId(Long msjId) {
        this.msjId = msjId;
    }

    public short getMsjNumero() {
        return msjNumero;
    }

    public void setMsjNumero(short msjNumero) {
        this.msjNumero = msjNumero;
    }

    public String getMsjDescripcion() {
        return msjDescripcion;
    }

    public void setMsjDescripcion(String msjDescripcion) {
        this.msjDescripcion = msjDescripcion;
    }

    public String getMsjTipo() {
        return msjTipo;
    }

    public void setMsjTipo(String msjTipo) {
        this.msjTipo = msjTipo;
    }

    public String getMsjProblema() {
        return msjProblema;
    }

    public void setMsjProblema(String msjProblema) {
        this.msjProblema = msjProblema;
    }

    public String getMsjSolucion() {
        return msjSolucion;
    }

    public void setMsjSolucion(String msjSolucion) {
        this.msjSolucion = msjSolucion;
    }

    public String getMsjColor() {
        return msjColor;
    }

    public void setMsjColor(String msjColor) {
        this.msjColor = msjColor;
    }

    public String getMsjNegrilla() {
        return msjNegrilla;
    }

    public void setMsjNegrilla(String msjNegrilla) {
        this.msjNegrilla = msjNegrilla;
    }

    public Short getMsjTamanio() {
        return msjTamanio;
    }

    public void setMsjTamanio(Short msjTamanio) {
        this.msjTamanio = msjTamanio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msjId != null ? msjId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        if ((this.msjId == null && other.msjId != null) || (this.msjId != null && !this.msjId.equals(other.msjId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Mensaje[ msjId=" + msjId + " ]";
    }

}
