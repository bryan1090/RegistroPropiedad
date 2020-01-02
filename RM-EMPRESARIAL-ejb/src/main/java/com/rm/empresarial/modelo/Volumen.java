/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author JeanCarlos
 */
@Entity
@Table(name = "Volumen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Volumen.findAll", query = "SELECT v FROM Volumen v")
    , @NamedQuery(name = "Volumen.findByVleId", query = "SELECT v FROM Volumen v WHERE v.vleId = :vleId")
    , @NamedQuery(name = "Volumen.findByVleVolumen", query = "SELECT v FROM Volumen v WHERE v.vleVolumen = :vleVolumen")
    , @NamedQuery(name = "Volumen.findByVleHojas", query = "SELECT v FROM Volumen v WHERE v.vleHojas = :vleHojas")
    , @NamedQuery(name = "Volumen.findByVleFojaInicial", query = "SELECT v FROM Volumen v WHERE v.vleFojaInicial = :vleFojaInicial")
    , @NamedQuery(name = "Volumen.findByVleFojaFinal", query = "SELECT v FROM Volumen v WHERE v.vleFojaFinal = :vleFojaFinal")
    , @NamedQuery(name = "Volumen.findByVleEstado", query = "SELECT v FROM Volumen v WHERE v.vleEstado = :vleEstado")
    , @NamedQuery(name = "Volumen.findByVleUser", query = "SELECT v FROM Volumen v WHERE v.vleUser = :vleUser")
    , @NamedQuery(name = "Volumen.findByVleFHR", query = "SELECT v FROM Volumen v WHERE v.vleFHR = :vleFHR")})
public class Volumen implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Volumen.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VleId")
    private Long vleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VleVolumen")
    private Long vleVolumen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VleHojas")
    private int vleHojas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VleFojaInicial")
    private BigInteger vleFojaInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VleFojaFinal")
    private BigInteger vleFojaFinal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "VleEstado")
    private String vleEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "VleUser")
    private String vleUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VleFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vleFHR;

    @Size(max = 40)
    @Column(name = "VleFila")
    private String vleFila;
    @Size(max = 40)
    @Column(name = "VleColumna")
    private String vleColumna;
    @Size(max = 40)
    @Column(name = "VleEstante")
    private String vleEstante;
    @Size(max = 40)
    @Column(name = "VleBloque")
    private String vleBloque;
    @Column(name = "VleInscripcionFinal")
    private String vleInscripcionFinal;
    @Column(name = "VleInscripcionInicial")
    private String vleInscripcionInicial;
    @JoinColumn(name = "TomId", referencedColumnName = "TomId")
    @ManyToOne(optional = false)
    private Tomo tomId;

    public Volumen() {
    }

    public Volumen(Long vleId) {
        this.vleId = vleId;
    }

    public Volumen(Long vleId, Long vleVolumen, int vleHojas, BigInteger vleFojaInicial, BigInteger vleFojaFinal, String vleEstado, String vleUser, Date vleFHR) {
        this.vleId = vleId;
        this.vleVolumen = vleVolumen;
        this.vleHojas = vleHojas;
        this.vleFojaInicial = vleFojaInicial;
        this.vleFojaFinal = vleFojaFinal;
        this.vleEstado = vleEstado;
        this.vleUser = vleUser;
        this.vleFHR = vleFHR;
    }

    public Volumen(Long vleVolumen, int vleHojas, BigInteger vleFojaInicial, BigInteger vleFojaFinal, String vleEstado, String vleUser, Date vleFHR, String vleFila, String vleColumna, String vleEstante, String vleBloque, Tomo tomId, String vleInscripcionInicial, String vleInscripcionFinal) {
        this.vleVolumen = vleVolumen;
        this.vleHojas = vleHojas;
        this.vleFojaInicial = vleFojaInicial;
        this.vleFojaFinal = vleFojaFinal;
        this.vleEstado = vleEstado;
        this.vleUser = vleUser;
        this.vleFHR = vleFHR;
        this.vleFila = vleFila;
        this.vleColumna = vleColumna;
        this.vleEstante = vleEstante;
        this.vleBloque = vleBloque;
        this.tomId = tomId;
        this.vleInscripcionInicial = vleInscripcionInicial;
        this.vleInscripcionFinal = vleInscripcionFinal;
    }

    public String getVleInscripcionFinal() {
        return vleInscripcionFinal;
    }

    public void setVleInscripcionFinal(String vleInscripcionFinal) {
        this.vleInscripcionFinal = vleInscripcionFinal;
    }

    public String getVleInscripcionInicial() {
        return vleInscripcionInicial;
    }

    public void setVleInscripcionInicial(String vleInscripcionInicial) {
        this.vleInscripcionInicial = vleInscripcionInicial;
    }
    
    

    public Long getVleId() {
        return vleId;
    }

    public void setVleId(Long vleId) {
        this.vleId = vleId;
    }

    public Long getVleVolumen() {
        return vleVolumen;
    }

    public void setVleVolumen(Long vleVolumen) {
        this.vleVolumen = vleVolumen;
    }

    public int getVleHojas() {
        return vleHojas;
    }

    public void setVleHojas(int vleHojas) {
        this.vleHojas = vleHojas;
    }

    public BigInteger getVleFojaInicial() {
        return vleFojaInicial;
    }

    public void setVleFojaInicial(BigInteger vleFojaInicial) {
        this.vleFojaInicial = vleFojaInicial;
    }

    public BigInteger getVleFojaFinal() {
        return vleFojaFinal;
    }

    public void setVleFojaFinal(BigInteger vleFojaFinal) {
        this.vleFojaFinal = vleFojaFinal;
    }

    public String getVleEstado() {
        return vleEstado;
    }

    public void setVleEstado(String vleEstado) {
        this.vleEstado = vleEstado;
    }

    public String getVleUser() {
        return vleUser;
    }

    public void setVleUser(String vleUser) {
        this.vleUser = vleUser;
    }

    public Date getVleFHR() {
        return vleFHR;
    }

    public void setVleFHR(Date vleFHR) {
        this.vleFHR = vleFHR;
    }

    public String getVleFila() {
        return vleFila;
    }

    public void setVleFila(String vleFila) {
        this.vleFila = vleFila;
    }

    public String getVleColumna() {
        return vleColumna;
    }

    public void setVleColumna(String vleColumna) {
        this.vleColumna = vleColumna;
    }

    public String getVleEstante() {
        return vleEstante;
    }

    public void setVleEstante(String vleEstante) {
        this.vleEstante = vleEstante;
    }

    public String getVleBloque() {
        return vleBloque;
    }

    public void setVleBloque(String vleBloque) {
        this.vleBloque = vleBloque;
    }

    public Tomo getTomId() {
        return tomId;
    }

    public void setTomId(Tomo tomId) {
        this.tomId = tomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vleId != null ? vleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Volumen)) {
            return false;
        }
        Volumen other = (Volumen) object;
        if ((this.vleId == null && other.vleId != null) || (this.vleId != null && !this.vleId.equals(other.vleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Volumen[ vleId=" + vleId + " ]";
    }

}
