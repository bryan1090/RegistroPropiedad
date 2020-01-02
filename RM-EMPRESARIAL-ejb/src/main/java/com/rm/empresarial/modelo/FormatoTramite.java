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
 * @author Marco
 */
@Entity
@Table(name = "FormatoTramite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormatoTramite.findAll", query = "SELECT f FROM FormatoTramite f")
    , @NamedQuery(name = "FormatoTramite.findByFmtId", query = "SELECT f FROM FormatoTramite f WHERE f.fmtId = :fmtId")
    , @NamedQuery(name = "FormatoTramite.findByTramId", query = "SELECT f FROM FormatoTramite f WHERE f.ttrId = :ttrId")
    , @NamedQuery(name = "FormatoTramite.findByFmtLinea", query = "SELECT f FROM FormatoTramite f WHERE f.fmtLinea = :fmtLinea")
    , @NamedQuery(name = "FormatoTramite.findByFmtIdCuadro", query = "SELECT f FROM FormatoTramite f WHERE f.fmtIdCuadro = :fmtIdCuadro")
    , @NamedQuery(name = "FormatoTramite.findByFmtTitulo", query = "SELECT f FROM FormatoTramite f WHERE f.fmtTitulo = :fmtTitulo")
    , @NamedQuery(name = "FormatoTramite.findByFmtTexto", query = "SELECT f FROM FormatoTramite f WHERE f.fmtTexto = :fmtTexto")
    , @NamedQuery(name = "FormatoTramite.findByFmtBold", query = "SELECT f FROM FormatoTramite f WHERE f.fmtBold = :fmtBold")
    , @NamedQuery(name = "FormatoTramite.findByFmtUser", query = "SELECT f FROM FormatoTramite f WHERE f.fmtUser = :fmtUser")
    , @NamedQuery(name = "FormatoTramite.findByFmtFHR", query = "SELECT f FROM FormatoTramite f WHERE f.fmtFHR = :fmtFHR")})
public class FormatoTramite implements Serializable {
    
    public static final String BUSCAR_POR_TRAMITE_ID = "FormatoTramite.findByTramId";

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FmtId")
    private Long fmtId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FmtLinea")
    private short fmtLinea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FmtIdCuadro")
    private String fmtIdCuadro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "FmtTitulo")
    private String fmtTitulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "FmtTexto")
    private String fmtTexto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FmtBold")
    private Character fmtBold;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FmtUser")
    private String fmtUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FmtFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fmtFHR;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;

    public FormatoTramite() {
    }

    public FormatoTramite(Long fmtId) {
        this.fmtId = fmtId;
    }

    public FormatoTramite(Long fmtId, short fmtLinea, String fmtIdCuadro, String fmtTitulo, String fmtTexto, Character fmtBold, String fmtUser, Date fmtFHR) {
        this.fmtId = fmtId;
        this.fmtLinea = fmtLinea;
        this.fmtIdCuadro = fmtIdCuadro;
        this.fmtTitulo = fmtTitulo;
        this.fmtTexto = fmtTexto;
        this.fmtBold = fmtBold;
        this.fmtUser = fmtUser;
        this.fmtFHR = fmtFHR;
    }

    public Long getFmtId() {
        return fmtId;
    }

    public void setFmtId(Long fmtId) {
        this.fmtId = fmtId;
    }

    public short getFmtLinea() {
        return fmtLinea;
    }

    public void setFmtLinea(short fmtLinea) {
        this.fmtLinea = fmtLinea;
    }

    public String getFmtIdCuadro() {
        return fmtIdCuadro;
    }

    public void setFmtIdCuadro(String fmtIdCuadro) {
        this.fmtIdCuadro = fmtIdCuadro;
    }

    public String getFmtTitulo() {
        return fmtTitulo;
    }

    public void setFmtTitulo(String fmtTitulo) {
        this.fmtTitulo = fmtTitulo;
    }

    public String getFmtTexto() {
        return fmtTexto;
    }

    public void setFmtTexto(String fmtTexto) {
        this.fmtTexto = fmtTexto;
    }

    public Character getFmtBold() {
        return fmtBold;
    }

    public void setFmtBold(Character fmtBold) {
        this.fmtBold = fmtBold;
    }

    public String getFmtUser() {
        return fmtUser;
    }

    public void setFmtUser(String fmtUser) {
        this.fmtUser = fmtUser;
    }

    public Date getFmtFHR() {
        return fmtFHR;
    }

    public void setFmtFHR(Date fmtFHR) {
        this.fmtFHR = fmtFHR;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fmtId != null ? fmtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormatoTramite)) {
            return false;
        }
        FormatoTramite other = (FormatoTramite) object;
        if ((this.fmtId == null && other.fmtId != null) || (this.fmtId != null && !this.fmtId.equals(other.fmtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.menun.FormatoTramite[ fmtId=" + fmtId + " ]";
    }
    
}
