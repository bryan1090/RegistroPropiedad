/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;

import java.util.Date;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WILSON
 */
@Entity
@Table(name = "Secuencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secuencia.buscarTodo", query = "SELECT s FROM Secuencia s ORDER BY s.secCodigo")
    , @NamedQuery(name = "Secuencia.buscarPorSecId", query = "SELECT s FROM Secuencia s WHERE s.secId = :secId")
    , @NamedQuery(name = "Secuencia.buscarPorSecCodigo", query = "SELECT s FROM Secuencia s WHERE s.secCodigo = :secCodigo")
    , @NamedQuery(name = "Secuencia.buscarPorSecAnio", query = "SELECT s FROM Secuencia s WHERE s.secAnio = :secAnio")
    , @NamedQuery(name = "Secuencia.buscarPorSecActual", query = "SELECT s FROM Secuencia s WHERE s.secActual = :secActual")
    , @NamedQuery(name = "Secuencia.buscarPorSecUser", query = "SELECT s FROM Secuencia s WHERE s.secUser = :secUser")
    , @NamedQuery(name = "Secuencia.buscarPorSecFHR", query = "SELECT s FROM Secuencia s WHERE s.secFHR = :secFHR")
    , @NamedQuery(name = "Secuencia.buscarPorSecCodigoEditar", query = "SELECT s FROM Secuencia s WHERE s.secCodigo = :secCodigo AND s.secId != :secId")})
public class Secuencia implements Serializable {

    private static final long serialVersionUID = -9214106244135354999L;
    public static final String LISTAR_TODOS = "Secuencia.buscarTodo";
    public static final String BUSCAR_POR_CODIGO = "Secuencia.buscarPorSecCodigo";
    public static final String BUSCAR_POR_CODIGO_EDITAR = "Secuencia.buscarPorSecCodigoEditar";

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SecId")
    private Long secId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "SecCodigo")
    private String secCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SecAnio")
    private short secAnio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SecActual")
    private int secActual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SecUser")
    private String secUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SecFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date secFHR;
    //variable que no guarda en la persistencia
//    @Transient
//   private Date SecFHRAsDate;
//
//    public Date getSecFHRAsDate() {
//        return SecFHRAsDate;
//    }
//
//    public void setSecFHRAsDate(Date SecFHRAsDate) {
////         Date calendar=Date.getInstance(); 
////        setSecFHR(calendar.setTime(SecFHRAsDate));
//       this.SecFHRAsDate = SecFHRAsDate;
//    }

    public Secuencia() {
    }

    public Secuencia(Long secId) {
        this.secId = secId;
    }

    public Secuencia(Long secId, String secCodigo, short secAnio, int secActual, String secUser, Date secFHR) {
        this.secId = secId;
        this.secCodigo = secCodigo;
        this.secAnio = secAnio;
        this.secActual = secActual;
        this.secUser = secUser;
        this.secFHR = secFHR;
    }

    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }

    public String getSecCodigo() {
        return secCodigo;
    }

    public void setSecCodigo(String secCodigo) {
        this.secCodigo = secCodigo;
    }

    public short getSecAnio() {
        return secAnio;
    }

    public void setSecAnio(short secAnio) {
        this.secAnio = secAnio;
    }

    public int getSecActual() {
        return secActual;
    }

    public void setSecActual(int secActual) {
        this.secActual = secActual;
    }

    public String getSecUser() {
        return secUser;
    }

    public void setSecUser(String secUser) {
        this.secUser = secUser;
    }

    public Date getSecFHR() {
        return secFHR;
    }

    public void setSecFHR(Date secFHR) {
        this.secFHR = secFHR;
    }

//    public Date getSecFHRAsDate() {
//        return secFHR.getTime();
//    }
//
//    public void setSecFHRFromDate(Date secFHR) {
//       Date calendar=Date.getInstance(); 
//        this.secFHR =calendar.setTime(secFHR);
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secId != null ? secId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secuencia)) {
            return false;
        }
        Secuencia other = (Secuencia) object;
        if ((this.secId == null && other.secId != null) || (this.secId != null && !this.secId.equals(other.secId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.Secuencia[ secId=" + secId + " ]";
    }

}
