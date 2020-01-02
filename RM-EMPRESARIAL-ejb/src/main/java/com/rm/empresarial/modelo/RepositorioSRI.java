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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "RepositorioSRI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepositorioSRI.buscarTodo", query = "SELECT r FROM RepositorioSRI r")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriRuc", query = "SELECT r FROM RepositorioSRI r WHERE r.sriRuc = :sriRuc")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriRazonSocial", query = "SELECT r FROM RepositorioSRI r WHERE r.sriRazonSocial = :sriRazonSocial")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriNombreComercial", query = "SELECT r FROM RepositorioSRI r WHERE r.sriNombreComercial = :sriNombreComercial")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriEstadoContribuyente", query = "SELECT r FROM RepositorioSRI r WHERE r.sriEstadoContribuyente = :sriEstadoContribuyente")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriClaseContribuyente", query = "SELECT r FROM RepositorioSRI r WHERE r.sriClaseContribuyente = :sriClaseContribuyente")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriFechaInicioActividades", query = "SELECT r FROM RepositorioSRI r WHERE r.sriFechaInicioActividades = :sriFechaInicioActividades")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriFechaActualizacion", query = "SELECT r FROM RepositorioSRI r WHERE r.sriFechaActualizacion = :sriFechaActualizacion")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriFechaSuspencionDefinitiva", query = "SELECT r FROM RepositorioSRI r WHERE r.sriFechaSuspencionDefinitiva = :sriFechaSuspencionDefinitiva")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriFechaReinicioActividades", query = "SELECT r FROM RepositorioSRI r WHERE r.sriFechaReinicioActividades = :sriFechaReinicioActividades")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriObligado", query = "SELECT r FROM RepositorioSRI r WHERE r.sriObligado = :sriObligado")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriTipoContribuyente", query = "SELECT r FROM RepositorioSRI r WHERE r.sriTipoContribuyente = :sriTipoContribuyente")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriNumeroEstablecimiento", query = "SELECT r FROM RepositorioSRI r WHERE r.sriNumeroEstablecimiento = :sriNumeroEstablecimiento")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriNombreFantasiaComercial", query = "SELECT r FROM RepositorioSRI r WHERE r.sriNombreFantasiaComercial = :sriNombreFantasiaComercial")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriCalle", query = "SELECT r FROM RepositorioSRI r WHERE r.sriCalle = :sriCalle")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriNumero", query = "SELECT r FROM RepositorioSRI r WHERE r.sriNumero = :sriNumero")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriInterseccion", query = "SELECT r FROM RepositorioSRI r WHERE r.sriInterseccion = :sriInterseccion")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriEstadoEstablecimiento", query = "SELECT r FROM RepositorioSRI r WHERE r.sriEstadoEstablecimiento = :sriEstadoEstablecimiento")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriDescripcionProvincia", query = "SELECT r FROM RepositorioSRI r WHERE r.sriDescripcionProvincia = :sriDescripcionProvincia")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriDescripcionCanton", query = "SELECT r FROM RepositorioSRI r WHERE r.sriDescripcionCanton = :sriDescripcionCanton")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriDescripcionParroquia", query = "SELECT r FROM RepositorioSRI r WHERE r.sriDescripcionParroquia = :sriDescripcionParroquia")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriCodigoCiiu", query = "SELECT r FROM RepositorioSRI r WHERE r.sriCodigoCiiu = :sriCodigoCiiu")
    , @NamedQuery(name = "RepositorioSRI.buscarPorSriActividadEconomica", query = "SELECT r FROM RepositorioSRI r WHERE r.sriActividadEconomica = :sriActividadEconomica")})
public class RepositorioSRI implements Serializable {

    private static final long serialVersionUID = -6196186692735537874L;

    public static final String BUSCAR_POR_IDENTIFICACION = "RepositorioSRI.buscarPorSriRuc";
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriRuc")
    private String sriRuc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SriRazonSocial")
    private String sriRazonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SriNombreComercial")
    private String sriNombreComercial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriEstadoContribuyente")
    private String sriEstadoContribuyente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriClaseContribuyente")
    private String sriClaseContribuyente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriFechaInicioActividades")
    private String sriFechaInicioActividades;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriFechaActualizacion")
    private String sriFechaActualizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriFechaSuspencionDefinitiva")
    private String sriFechaSuspencionDefinitiva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriFechaReinicioActividades")
    private String sriFechaReinicioActividades;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SriObligado")
    private Character sriObligado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "SriTipoContribuyente")
    private String sriTipoContribuyente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SriNumeroEstablecimiento")
    private short sriNumeroEstablecimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SriNombreFantasiaComercial")
    private String sriNombreFantasiaComercial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SriCalle")
    private String sriCalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriNumero")
    private String sriNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SriInterseccion")
    private String sriInterseccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriEstadoEstablecimiento")
    private String sriEstadoEstablecimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SriDescripcionProvincia")
    private String sriDescripcionProvincia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SriDescripcionCanton")
    private String sriDescripcionCanton;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SriDescripcionParroquia")
    private String sriDescripcionParroquia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SriCodigoCiiu")
    private String sriCodigoCiiu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "SriActividadEconomica")
    private String sriActividadEconomica;

    public RepositorioSRI() {
    }

    public RepositorioSRI(String sriRuc) {
        this.sriRuc = sriRuc;
    }

    public RepositorioSRI(String sriRuc, String sriRazonSocial, String sriNombreComercial, String sriEstadoContribuyente, String sriClaseContribuyente, String sriFechaInicioActividades, String sriFechaActualizacion, String sriFechaSuspencionDefinitiva, String sriFechaReinicioActividades, Character sriObligado, String sriTipoContribuyente, short sriNumeroEstablecimiento, String sriNombreFantasiaComercial, String sriCalle, String sriNumero, String sriInterseccion, String sriEstadoEstablecimiento, String sriDescripcionProvincia, String sriDescripcionCanton, String sriDescripcionParroquia, String sriCodigoCiiu, String sriActividadEconomica) {
        this.sriRuc = sriRuc;
        this.sriRazonSocial = sriRazonSocial;
        this.sriNombreComercial = sriNombreComercial;
        this.sriEstadoContribuyente = sriEstadoContribuyente;
        this.sriClaseContribuyente = sriClaseContribuyente;
        this.sriFechaInicioActividades = sriFechaInicioActividades;
        this.sriFechaActualizacion = sriFechaActualizacion;
        this.sriFechaSuspencionDefinitiva = sriFechaSuspencionDefinitiva;
        this.sriFechaReinicioActividades = sriFechaReinicioActividades;
        this.sriObligado = sriObligado;
        this.sriTipoContribuyente = sriTipoContribuyente;
        this.sriNumeroEstablecimiento = sriNumeroEstablecimiento;
        this.sriNombreFantasiaComercial = sriNombreFantasiaComercial;
        this.sriCalle = sriCalle;
        this.sriNumero = sriNumero;
        this.sriInterseccion = sriInterseccion;
        this.sriEstadoEstablecimiento = sriEstadoEstablecimiento;
        this.sriDescripcionProvincia = sriDescripcionProvincia;
        this.sriDescripcionCanton = sriDescripcionCanton;
        this.sriDescripcionParroquia = sriDescripcionParroquia;
        this.sriCodigoCiiu = sriCodigoCiiu;
        this.sriActividadEconomica = sriActividadEconomica;
    }

    public String getSriRuc() {
        return sriRuc;
    }

    public void setSriRuc(String sriRuc) {
        this.sriRuc = sriRuc;
    }

    public String getSriRazonSocial() {
        return sriRazonSocial;
    }

    public void setSriRazonSocial(String sriRazonSocial) {
        this.sriRazonSocial = sriRazonSocial;
    }

    public String getSriNombreComercial() {
        return sriNombreComercial;
    }

    public void setSriNombreComercial(String sriNombreComercial) {
        this.sriNombreComercial = sriNombreComercial;
    }

    public String getSriEstadoContribuyente() {
        return sriEstadoContribuyente;
    }

    public void setSriEstadoContribuyente(String sriEstadoContribuyente) {
        this.sriEstadoContribuyente = sriEstadoContribuyente;
    }

    public String getSriClaseContribuyente() {
        return sriClaseContribuyente;
    }

    public void setSriClaseContribuyente(String sriClaseContribuyente) {
        this.sriClaseContribuyente = sriClaseContribuyente;
    }

    public String getSriFechaInicioActividades() {
        return sriFechaInicioActividades;
    }

    public void setSriFechaInicioActividades(String sriFechaInicioActividades) {
        this.sriFechaInicioActividades = sriFechaInicioActividades;
    }

    public String getSriFechaActualizacion() {
        return sriFechaActualizacion;
    }

    public void setSriFechaActualizacion(String sriFechaActualizacion) {
        this.sriFechaActualizacion = sriFechaActualizacion;
    }

    public String getSriFechaSuspencionDefinitiva() {
        return sriFechaSuspencionDefinitiva;
    }

    public void setSriFechaSuspencionDefinitiva(String sriFechaSuspencionDefinitiva) {
        this.sriFechaSuspencionDefinitiva = sriFechaSuspencionDefinitiva;
    }

    public String getSriFechaReinicioActividades() {
        return sriFechaReinicioActividades;
    }

    public void setSriFechaReinicioActividades(String sriFechaReinicioActividades) {
        this.sriFechaReinicioActividades = sriFechaReinicioActividades;
    }

    public Character getSriObligado() {
        return sriObligado;
    }

    public void setSriObligado(Character sriObligado) {
        this.sriObligado = sriObligado;
    }

    public String getSriTipoContribuyente() {
        return sriTipoContribuyente;
    }

    public void setSriTipoContribuyente(String sriTipoContribuyente) {
        this.sriTipoContribuyente = sriTipoContribuyente;
    }

    public short getSriNumeroEstablecimiento() {
        return sriNumeroEstablecimiento;
    }

    public void setSriNumeroEstablecimiento(short sriNumeroEstablecimiento) {
        this.sriNumeroEstablecimiento = sriNumeroEstablecimiento;
    }

    public String getSriNombreFantasiaComercial() {
        return sriNombreFantasiaComercial;
    }

    public void setSriNombreFantasiaComercial(String sriNombreFantasiaComercial) {
        this.sriNombreFantasiaComercial = sriNombreFantasiaComercial;
    }

    public String getSriCalle() {
        return sriCalle;
    }

    public void setSriCalle(String sriCalle) {
        this.sriCalle = sriCalle;
    }

    public String getSriNumero() {
        return sriNumero;
    }

    public void setSriNumero(String sriNumero) {
        this.sriNumero = sriNumero;
    }

    public String getSriInterseccion() {
        return sriInterseccion;
    }

    public void setSriInterseccion(String sriInterseccion) {
        this.sriInterseccion = sriInterseccion;
    }

    public String getSriEstadoEstablecimiento() {
        return sriEstadoEstablecimiento;
    }

    public void setSriEstadoEstablecimiento(String sriEstadoEstablecimiento) {
        this.sriEstadoEstablecimiento = sriEstadoEstablecimiento;
    }

    public String getSriDescripcionProvincia() {
        return sriDescripcionProvincia;
    }

    public void setSriDescripcionProvincia(String sriDescripcionProvincia) {
        this.sriDescripcionProvincia = sriDescripcionProvincia;
    }

    public String getSriDescripcionCanton() {
        return sriDescripcionCanton;
    }

    public void setSriDescripcionCanton(String sriDescripcionCanton) {
        this.sriDescripcionCanton = sriDescripcionCanton;
    }

    public String getSriDescripcionParroquia() {
        return sriDescripcionParroquia;
    }

    public void setSriDescripcionParroquia(String sriDescripcionParroquia) {
        this.sriDescripcionParroquia = sriDescripcionParroquia;
    }

    public String getSriCodigoCiiu() {
        return sriCodigoCiiu;
    }

    public void setSriCodigoCiiu(String sriCodigoCiiu) {
        this.sriCodigoCiiu = sriCodigoCiiu;
    }

    public String getSriActividadEconomica() {
        return sriActividadEconomica;
    }

    public void setSriActividadEconomica(String sriActividadEconomica) {
        this.sriActividadEconomica = sriActividadEconomica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sriRuc != null ? sriRuc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepositorioSRI)) {
            return false;
        }
        RepositorioSRI other = (RepositorioSRI) object;
        if ((this.sriRuc == null && other.sriRuc != null) || (this.sriRuc != null && !this.sriRuc.equals(other.sriRuc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.RepositorioSRI[ sriRuc=" + sriRuc + " ]";
    }

}
