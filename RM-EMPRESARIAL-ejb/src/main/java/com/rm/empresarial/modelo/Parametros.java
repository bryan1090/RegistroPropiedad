/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Wilson
 */
@Entity
@Table(name = "Parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.buscarTodo", query = "SELECT p FROM Parametros p ORDER BY p.prmParametro")
    , @NamedQuery(name = "Parametros.buscarPorPrmId", query = "SELECT p FROM Parametros p WHERE p.prmId = :prmId")
    , @NamedQuery(name = "Parametros.buscarPorPrmParametro", query = "SELECT p FROM Parametros p WHERE p.prmParametro = :prmParametro")
    , @NamedQuery(name = "Parametros.buscarPorPrmDescripcion", query = "SELECT p FROM Parametros p WHERE p.prmDescripcion = :prmDescripcion")
    , @NamedQuery(name = "Parametros.buscarPorPrmValor1", query = "SELECT p FROM Parametros p WHERE p.prmValor1 = :prmValor1")
    , @NamedQuery(name = "Parametros.buscarPorPrmValor2", query = "SELECT p FROM Parametros p WHERE p.prmValor2 = :prmValor2")
    , @NamedQuery(name = "Parametros.buscarPorPrmValor3", query = "SELECT p FROM Parametros p WHERE p.prmValor3 = :prmValor3")
    , @NamedQuery(name = "Parametros.buscarPorPrmUnidadTiempo", query = "SELECT p FROM Parametros p WHERE p.prmUnidadTiempo = :prmUnidadTiempo")
    , @NamedQuery(name = "Parametros.buscarPorPrmCantidadTiempo", query = "SELECT p FROM Parametros p WHERE p.prmCantidadTiempo = :prmCantidadTiempo")})
public class Parametros implements Serializable {

    private static final long serialVersionUID = -8338521988818383123L;

    public static final String LISTAR_TODO = "Parametros.buscarTodo";
    public static final String BUSCAR_POR_PARAMETRO = "Parametros.buscarPorPrmParametro";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrmId")
    private BigDecimal prmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PrmParametro")
    private String prmParametro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PrmDescripcion")
    private String prmDescripcion;
    @Size(max = 40)
    @Column(name = "PrmValor1")
    private String prmValor1;
    @Column(name = "PrmValor2")
    private BigDecimal prmValor2;
    @Column(name = "PrmValor3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prmValor3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrmUnidadTiempo")
    private String prmUnidadTiempo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrmCantidadTiempo")
    private short prmCantidadTiempo;

    public Parametros() {
    }

    public Parametros(BigDecimal prmId) {
        this.prmId = prmId;
    }

    public Parametros(BigDecimal prmId, String prmParametro, String prmDescripcion, String prmUnidadTiempo, short prmCantidadTiempo) {
        this.prmId = prmId;
        this.prmParametro = prmParametro;
        this.prmDescripcion = prmDescripcion;
        this.prmUnidadTiempo = prmUnidadTiempo;
        this.prmCantidadTiempo = prmCantidadTiempo;
    }

    public BigDecimal getPrmId() {
        return prmId;
    }

    public void setPrmId(BigDecimal prmId) {
        this.prmId = prmId;
    }

    public String getPrmParametro() {
        return prmParametro;
    }

    public void setPrmParametro(String prmParametro) {
        this.prmParametro = prmParametro;
    }

    public String getPrmDescripcion() {
        return prmDescripcion;
    }

    public void setPrmDescripcion(String prmDescripcion) {
        this.prmDescripcion = prmDescripcion;
    }

    public String getPrmValor1() {
        return prmValor1;
    }

    public void setPrmValor1(String prmValor1) {
        this.prmValor1 = prmValor1;
    }

    public BigDecimal getPrmValor2() {
        return prmValor2;
    }

    public void setPrmValor2(BigDecimal prmValor2) {
        this.prmValor2 = prmValor2;
    }

    public Date getPrmValor3() {
        return prmValor3;
    }

    public void setPrmValor3(Date prmValor3) {
        this.prmValor3 = prmValor3;
    }

    public String getPrmUnidadTiempo() {
        return prmUnidadTiempo;
    }

    public void setPrmUnidadTiempo(String prmUnidadTiempo) {
        this.prmUnidadTiempo = prmUnidadTiempo;
    }

    public short getPrmCantidadTiempo() {
        return prmCantidadTiempo;
    }

    public void setPrmCantidadTiempo(short prmCantidadTiempo) {
        this.prmCantidadTiempo = prmCantidadTiempo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prmId != null ? prmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.prmId == null && other.prmId != null) || (this.prmId != null && !this.prmId.equals(other.prmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.Parametros[ prmId=" + prmId + " ]";
    }
    
}
