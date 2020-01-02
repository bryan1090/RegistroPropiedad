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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Cuantia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuantia.buscarTodos", query = "SELECT c FROM Cuantia c ORDER BY c.ttrId.ttrDescripcion")
    , @NamedQuery(name = "Cuantia.buscarPorCuaId", query = "SELECT c FROM Cuantia c WHERE c.cuaId = :cuaId")
    , @NamedQuery(name = "Cuantia.buscarPorCuaValorInicial", query = "SELECT c FROM Cuantia c WHERE c.cuaValorInicial = :cuaValorInicial")
    , @NamedQuery(name = "Cuantia.buscarPorCuaValorFinal", query = "SELECT c FROM Cuantia c WHERE c.cuaValorFinal = :cuaValorFinal")
    , @NamedQuery(name = "Cuantia.buscarPorCuaValorAplica", query = "SELECT c FROM Cuantia c WHERE c.cuaValorAplica = :cuaValorAplica")
    , @NamedQuery(name = "Cuantia.buscarPorCuaUser", query = "SELECT c FROM Cuantia c WHERE c.cuaUser = :cuaUser")
    , @NamedQuery(name = "Cuantia.buscarPorCuaFHR", query = "SELECT c FROM Cuantia c WHERE c.cuaFHR = :cuaFHR")
    , @NamedQuery(name = "Cuantia.findByCuaTipo", query = "SELECT c FROM Cuantia c WHERE c.cuaTipo = :cuaTipo")
    , @NamedQuery(name = "Cuantia.findByCuaPorcentaje", query = "SELECT c FROM Cuantia c WHERE c.cuaPorcentaje = :cuaPorcentaje")
    , @NamedQuery(name = "Cuantia.buscarPorCuaTTRID", query = "SELECT c FROM Cuantia c WHERE c.ttrId = :ttrId ")})
public class Cuantia implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "Cuantia.buscarTodos";
    public static final String BUSCAR_POR_TRA_ID = "Cuantia.buscarPorCuaTTRID";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CuaId")
    private Long cuaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CuaValorInicial")
    private BigDecimal cuaValorInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CuaValorFinal")
    private BigDecimal cuaValorFinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CuaValorAplica")
    private BigDecimal cuaValorAplica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CuaUser")
    private String cuaUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CuaFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cuaFHR;
    @Size(max = 3)
    @Column(name = "CuaTipo")
    private String cuaTipo;
    @Column(name = "CuaPorcentaje")
    private BigDecimal cuaPorcentaje;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;

    public Cuantia() {
    }

    public Cuantia(Long cuaId) {
        this.cuaId = cuaId;
    }

    public Cuantia(Long cuaId, BigDecimal cuaValorInicial, BigDecimal cuaValorFinal, BigDecimal cuaValorAplica, String cuaUser, Date cuaFHR) {
        this.cuaId = cuaId;
        this.cuaValorInicial = cuaValorInicial;
        this.cuaValorFinal = cuaValorFinal;
        this.cuaValorAplica = cuaValorAplica;
        this.cuaUser = cuaUser;
        this.cuaFHR = cuaFHR;
    }

    public Long getCuaId() {
        return cuaId;
    }

    public void setCuaId(Long cuaId) {
        this.cuaId = cuaId;
    }

    public BigDecimal getCuaValorInicial() {
        return cuaValorInicial;
    }

    public void setCuaValorInicial(BigDecimal cuaValorInicial) {
        this.cuaValorInicial = cuaValorInicial;
    }

    public BigDecimal getCuaValorFinal() {
        return cuaValorFinal;
    }

    public void setCuaValorFinal(BigDecimal cuaValorFinal) {
        this.cuaValorFinal = cuaValorFinal;
    }

    public BigDecimal getCuaValorAplica() {
        return cuaValorAplica;
    }

    public void setCuaValorAplica(BigDecimal cuaValorAplica) {
        this.cuaValorAplica = cuaValorAplica;
    }

    public String getCuaUser() {
        return cuaUser;
    }

    public void setCuaUser(String cuaUser) {
        this.cuaUser = cuaUser;
    }

    public Date getCuaFHR() {
        return cuaFHR;
    }

    public void setCuaFHR(Date cuaFHR) {
        this.cuaFHR = cuaFHR;
    }

    public String getCuaTipo() {
        return cuaTipo;
    }

    public void setCuaTipo(String cuaTipo) {
        this.cuaTipo = cuaTipo;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }

    public BigDecimal getCuaPorcentaje() {
        return cuaPorcentaje;
    }

    public void setCuaPorcentaje(BigDecimal cuaPorcentaje) {
        this.cuaPorcentaje = cuaPorcentaje;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuaId != null ? cuaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuantia)) {
            return false;
        }
        Cuantia other = (Cuantia) object;
        if ((this.cuaId == null && other.cuaId != null) || (this.cuaId != null && !this.cuaId.equals(other.cuaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cuantia[ cuaId=" + cuaId + " ]";
    }

}
