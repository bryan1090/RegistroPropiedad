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
 * @author JeanCarlos
 */
@Entity
@Table(name = "HostMail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HostMail.findAll", query = "SELECT h FROM HostMail h")
    , @NamedQuery(name = "HostMail.findByHtmId", query = "SELECT h FROM HostMail h WHERE h.htmId = :htmId")
    , @NamedQuery(name = "HostMail.findByHtmCorreo", query = "SELECT h FROM HostMail h WHERE h.htmCorreo = :htmCorreo")
    , @NamedQuery(name = "HostMail.findByHtmContrasenia", query = "SELECT h FROM HostMail h WHERE h.htmContrasenia = :htmContrasenia")
    , @NamedQuery(name = "HostMail.findByHtmPuerto", query = "SELECT h FROM HostMail h WHERE h.htmPuerto = :htmPuerto")
    , @NamedQuery(name = "HostMail.findByHtmSmtp", query = "SELECT h FROM HostMail h WHERE h.htmSmtp = :htmSmtp")
    , @NamedQuery(name = "HostMail.findByHtmRemitente", query = "SELECT h FROM HostMail h WHERE h.htmRemitente = :htmRemitente")
    , @NamedQuery(name = "HostMail.findByHtmSeguridad", query = "SELECT h FROM HostMail h WHERE h.htmSeguridad = :htmSeguridad")
    , @NamedQuery(name = "HostMail.findByHtmAutentificacion", query = "SELECT h FROM HostMail h WHERE h.htmAutentificacion = :htmAutentificacion")
    , @NamedQuery(name = "HostMail.findByHtmFirma", query = "SELECT h FROM HostMail h WHERE h.htmFirma = :htmFirma")
    , @NamedQuery(name = "HostMail.findByHtmUser", query = "SELECT h FROM HostMail h WHERE h.htmUser = :htmUser")
    , @NamedQuery(name = "HostMail.findByHtmFHR", query = "SELECT h FROM HostMail h WHERE h.htmFHR = :htmFHR")})
public class HostMail implements Serializable {

    public static final String LISTAR_PORCORREO = "HostMail.findByHtmCorreo";
    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "HostMail.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HtmId")
    private Long htmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "HtmCorreo")
    private String htmCorreo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "HtmContrasenia")
    private String htmContrasenia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HtmPuerto")
    private short htmPuerto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "HtmSmtp")
    private String htmSmtp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "HtmRemitente")
    private String htmRemitente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HtmSeguridad")
    private short htmSeguridad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HtmAutentificacion")
    private short htmAutentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "HtmFirma")
    private String htmFirma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "HtmUser")
    private String htmUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HtmFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date htmFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "HtmUsuario")
    private String htmUsuario;

    public HostMail() {
    }

    public HostMail(Long htmId) {
        this.htmId = htmId;
    }

    public HostMail(Long htmId, String htmCorreo, String htmContrasenia, short htmPuerto, String htmSmtp, String htmRemitente, short htmSeguridad, short htmAutentificacion, String htmFirma, String htmUser, Date htmFHR, String htmUsuario) {
        this.htmId = htmId;
        this.htmCorreo = htmCorreo;
        this.htmContrasenia = htmContrasenia;
        this.htmPuerto = htmPuerto;
        this.htmSmtp = htmSmtp;
        this.htmRemitente = htmRemitente;
        this.htmSeguridad = htmSeguridad;
        this.htmAutentificacion = htmAutentificacion;
        this.htmFirma = htmFirma;
        this.htmUser = htmUser;
        this.htmFHR = htmFHR;
        this.htmUsuario = htmUsuario;
    }

    public Long getHtmId() {
        return htmId;
    }

    public void setHtmId(Long htmId) {
        this.htmId = htmId;
    }

    public String getHtmCorreo() {
        return htmCorreo;
    }

    public void setHtmCorreo(String htmCorreo) {
        this.htmCorreo = htmCorreo;
    }

    public String getHtmContrasenia() {
        return htmContrasenia;
    }

    public void setHtmContrasenia(String htmContrasenia) {
        this.htmContrasenia = htmContrasenia;
    }

    public short getHtmPuerto() {
        return htmPuerto;
    }

    public void setHtmPuerto(short htmPuerto) {
        this.htmPuerto = htmPuerto;
    }

    public String getHtmSmtp() {
        return htmSmtp;
    }

    public void setHtmSmtp(String htmSmtp) {
        this.htmSmtp = htmSmtp;
    }

    public String getHtmRemitente() {
        return htmRemitente;
    }

    public void setHtmRemitente(String htmRemitente) {
        this.htmRemitente = htmRemitente;
    }

    public short getHtmSeguridad() {
        return htmSeguridad;
    }

    public void setHtmSeguridad(short htmSeguridad) {
        this.htmSeguridad = htmSeguridad;
    }

    public short getHtmAutentificacion() {
        return htmAutentificacion;
    }

    public void setHtmAutentificacion(short htmAutentificacion) {
        this.htmAutentificacion = htmAutentificacion;
    }

    public String getHtmFirma() {
        return htmFirma;
    }

    public void setHtmFirma(String htmFirma) {
        this.htmFirma = htmFirma;
    }

    public String getHtmUser() {
        return htmUser;
    }

    public void setHtmUser(String htmUser) {
        this.htmUser = htmUser;
    }

    public Date getHtmFHR() {
        return htmFHR;
    }

    public void setHtmFHR(Date htmFHR) {
        this.htmFHR = htmFHR;
    }
    
    public String getHtmUsuario() {
        return htmUsuario;
    }

    public void setHtmUsuario(String htmUsuario) {
        this.htmUsuario = htmUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (htmId != null ? htmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostMail)) {
            return false;
        }
        HostMail other = (HostMail) object;
        if ((this.htmId == null && other.htmId != null) || (this.htmId != null && !this.htmId.equals(other.htmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.HostMail[ htmId=" + htmId + " ]";

    }
    
}