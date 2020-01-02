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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Repertorio", uniqueConstraints = @UniqueConstraint(columnNames = {"TraNumero", "RepTtrId", "RepUser"}))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repertorio.findAll", query = "SELECT r FROM Repertorio r")
    , @NamedQuery(name = "Repertorio.findByRepNumero", query = "SELECT r FROM Repertorio r WHERE r.repNumero = :repNumero")
    , @NamedQuery(name = "Repertorio.findByRepTtrId", query = "SELECT r FROM Repertorio r WHERE r.repTtrId = :repTtrId")
    , @NamedQuery(name = "Repertorio.findByRepTtrDescripcion", query = "SELECT r FROM Repertorio r WHERE r.repTtrDescripcion = :repTtrDescripcion")
    , @NamedQuery(name = "Repertorio.findByRepEstado", query = "SELECT r FROM Repertorio r WHERE r.repEstado = :repEstado")
    , @NamedQuery(name = "Repertorio.findByRepUser", query = "SELECT r FROM Repertorio r WHERE r.repUser = :repUser")
    , @NamedQuery(name = "Repertorio.findByRepFHR", query = "SELECT r FROM Repertorio r WHERE r.repFHR = :repFHR")
    ,@NamedQuery(name = "Repertorio.encontrarMaxPorNumeroRepertorio", query = "SELECT r FROM Repertorio r WHERE r.repNumero=(SELECT MAX(r.repNumero) from Repertorio r)")
    ,@NamedQuery(name = "Repertorio.listarPorNumeroTramite", query = "SELECT r FROM Repertorio r WHERE r.traNumero.traNumero= :traNumero AND r.repEstado='A'")
    ,@NamedQuery(name = "Repertorio.encontrarReciente", query = "SELECT r FROM Repertorio r WHERE r.traNumero.traNumero= :traNumero AND r.repEstado='A' ORDER BY r.repFHR DESC")
    ,@NamedQuery(name = "Repertorio.listarPorNumeroTramiteyTipo", query = "SELECT r FROM Repertorio r WHERE r.traNumero= :traNumero AND r.repTtrId = :repTtrId")

})
public class Repertorio implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_ULTIMO_POR_REP_NUMERO = "Repertorio.encontrarMaxPorNumeroRepertorio";
    public static final String LISTAR_POR_TRA_NUMERO = "Repertorio.listarPorNumeroTramite";
    public static final String LISTAR_PORID = "Repertorio.findByRepNumero";
    public static final String LISTAR_POR_REP_NUMERO = "Repertorio.findByRepNumero";
    public static final String LISTAR_POR_TRA_NUMERO_TIPO = "Repertorio.listarPorNumeroTramiteyTipo";
    public static final String ENCONTRAR_RECIENTE_POR_NUM_TRAMITE = "Repertorio.encontrarReciente";
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RepNumero")
    private Long repNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RepTtrId")
    private short repTtrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "RepTtrDescripcion")
    private String repTtrDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "RepEstado")
    private String repEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RepUser")
    private String repUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RepFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repFHR;
    @Column(name = "RepNumeroImpresion")
    private Integer repNumeroImpresion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repNumero")
    private List<RepertorioDigital> repertorioDigitalList;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;
    @Getter
    @Setter
    @Transient
    private String estadoProceso;

    public Repertorio() {
    }

    public Repertorio(Long repNumero) {
        this.repNumero = repNumero;
    }

    public Repertorio(Long repNumero, short repTtrId, String repTtrDescripcion, String repEstado, String repUser, Date repFHR) {
        this.repNumero = repNumero;
        this.repTtrId = repTtrId;
        this.repTtrDescripcion = repTtrDescripcion;
        this.repEstado = repEstado;
        this.repUser = repUser;
        this.repFHR = repFHR;
    }

    public Long getRepNumero() {
        return repNumero;
    }

    public void setRepNumero(Long repNumero) {
        this.repNumero = repNumero;
    }

    public short getRepTtrId() {
        return repTtrId;
    }

    public void setRepTtrId(short repTtrId) {
        this.repTtrId = repTtrId;
    }

    public String getRepTtrDescripcion() {
        return repTtrDescripcion;
    }

    public void setRepTtrDescripcion(String repTtrDescripcion) {
        this.repTtrDescripcion = repTtrDescripcion;
    }

    public String getRepEstado() {
        return repEstado;
    }

    public void setRepEstado(String repEstado) {
        this.repEstado = repEstado;
    }

    public String getRepUser() {
        return repUser;
    }

    public void setRepUser(String repUser) {
        this.repUser = repUser;
    }

    public Date getRepFHR() {
        return repFHR;
    }

    public void setRepFHR(Date repFHR) {
        this.repFHR = repFHR;
    }

    @XmlTransient
    public List<RepertorioDigital> getRepertorioDigitalList() {
        return repertorioDigitalList;
    }

    public void setRepertorioDigitalList(List<RepertorioDigital> repertorioDigitalList) {
        this.repertorioDigitalList = repertorioDigitalList;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    public Integer getRepNumeroImpresion() {
        return repNumeroImpresion;
    }

    public void setRepNumeroImpresion(Integer repNumeroImpresion) {
        this.repNumeroImpresion = repNumeroImpresion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repNumero != null ? repNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repertorio)) {
            return false;
        }
        Repertorio other = (Repertorio) object;
        if ((this.repNumero == null && other.repNumero != null) || (this.repNumero != null && !this.repNumero.equals(other.repNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Repertorio[ repNumero=" + repNumero + " ]";
    }

}
