/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "INDICES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indices.findAll", query = "SELECT i FROM Indices i")
    , @NamedQuery(name = "Indices.findByIndInscripcion", query = "SELECT i FROM Indices i WHERE i.indInscripcion = :indInscripcion")
    , @NamedQuery(name = "Indices.findByIndApellidop", query = "SELECT i FROM Indices i WHERE i.indicesPK.indApellidop = :indApellidop")
    , @NamedQuery(name = "Indices.findByIndApellidom", query = "SELECT i FROM Indices i WHERE i.indicesPK.indApellidom = :indApellidom")
    , @NamedQuery(name = "Indices.findByIndNombres", query = "SELECT i FROM Indices i WHERE i.indicesPK.indNombres = :indNombres")
    , @NamedQuery(name = "Indices.findByIndApellidop1", query = "SELECT i FROM Indices i WHERE i.indApellidop1 = :indApellidop1")
    , @NamedQuery(name = "Indices.findByIndApellidom1", query = "SELECT i FROM Indices i WHERE i.indApellidom1 = :indApellidom1")
    , @NamedQuery(name = "Indices.findByIndNombres1", query = "SELECT i FROM Indices i WHERE i.indNombres1 = :indNombres1")
    , @NamedQuery(name = "Indices.findByIndFojas", query = "SELECT i FROM Indices i WHERE i.indFojas = :indFojas")
    , @NamedQuery(name = "Indices.findByIndTomo", query = "SELECT i FROM Indices i WHERE i.indTomo = :indTomo")
    , @NamedQuery(name = "Indices.findByIndVolumen", query = "SELECT i FROM Indices i WHERE i.indVolumen = :indVolumen")
    , @NamedQuery(name = "Indices.findByIndFecha", query = "SELECT i FROM Indices i WHERE i.indFecha = :indFecha")
    , @NamedQuery(name = "Indices.findByIndBis", query = "SELECT i FROM Indices i WHERE i.indBis = :indBis")
    , @NamedQuery(name = "Indices.findByIndLibro", query = "SELECT i FROM Indices i WHERE i.indLibro = :indLibro")
    , @NamedQuery(name = "Indices.findByIndParroquia", query = "SELECT i FROM Indices i WHERE i.indParroquia = :indParroquia")
    , @NamedQuery(name = "Indices.findByIndCuantia", query = "SELECT i FROM Indices i WHERE i.indCuantia = :indCuantia")
    , @NamedQuery(name = "Indices.findByIndRepertorio", query = "SELECT i FROM Indices i WHERE i.indRepertorio = :indRepertorio")
    , @NamedQuery(name = "Indices.findByIndCompareciente", query = "SELECT i FROM Indices i WHERE i.indCompareciente = :indCompareciente")
    , @NamedQuery(name = "Indices.findByIndCompareciente1", query = "SELECT i FROM Indices i WHERE i.indCompareciente1 = :indCompareciente1")
    , @NamedQuery(name = "Indices.findByIndContrato", query = "SELECT i FROM Indices i WHERE i.indContrato = :indContrato")
    , @NamedQuery(name = "Indices.findByIndCedula", query = "SELECT i FROM Indices i WHERE i.indCedula = :indCedula")
    , @NamedQuery(name = "Indices.findByIndCodlibro", query = "SELECT i FROM Indices i WHERE i.indCodlibro = :indCodlibro")
    , @NamedQuery(name = "Indices.findByIndBloqueo", query = "SELECT i FROM Indices i WHERE i.indBloqueo = :indBloqueo")
    , @NamedQuery(name = "Indices.findByIndCodigo", query = "SELECT i FROM Indices i WHERE i.indicesPK.indCodigo = :indCodigo")
    , @NamedQuery(name = "Indices.findByBUSQUEDA", query = "SELECT i FROM Indices i WHERE i.indCedula LIKE :indCedula AND i.indApellidop1 LIKE :indApellidop1 AND i.indApellidom1 LIKE :indApellidom1 AND i.indNombres1 LIKE :indNombres1")})
public class Indices implements Serializable {

    public static final String LISTAR_TODO = "Indices.findAll";
    public static final String LISTAR_POR_BUSQUEDA = "Indices.findByBUSQUEDA";

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndicesPK indicesPK;
    @Column(name = "IND_INSCRIPCION")
    private Long indInscripcion;
    @Size(max = 15)
    @Column(name = "IND_APELLIDOP1")
    private String indApellidop1;
    @Size(max = 15)
    @Column(name = "IND_APELLIDOM1")
    private String indApellidom1;
    @Size(max = 100)
    @Column(name = "IND_NOMBRES1")
    private String indNombres1;
    @Column(name = "IND_FOJAS")
    private Long indFojas;
    @Column(name = "IND_TOMO")
    private Short indTomo;
    @Column(name = "IND_VOLUMEN")
    private Short indVolumen;
    @Column(name = "IND_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date indFecha;
    @Column(name = "IND_BIS")
    private Short indBis;
    @Size(max = 25)
    @Column(name = "IND_LIBRO")
    private String indLibro;
    @Size(max = 25)
    @Column(name = "IND_PARROQUIA")
    private String indParroquia;
    @Column(name = "IND_CUANTIA")
    private Short indCuantia;
    @Column(name = "IND_REPERTORIO")
    private Long indRepertorio;
    @Size(max = 40)
    @Column(name = "IND_COMPARECIENTE")
    private String indCompareciente;
    @Size(max = 40)
    @Column(name = "IND_COMPARECIENTE1")
    private String indCompareciente1;
    @Size(max = 60)
    @Column(name = "IND_CONTRATO")
    private String indContrato;
    @Size(max = 13)
    @Column(name = "IND_CEDULA")
    private String indCedula;
    @Column(name = "IND_CODLIBRO")
    private Short indCodlibro;
    @Column(name = "IND_BLOQUEO")
    private Short indBloqueo;

    public Indices() {
    }

    public Indices(IndicesPK indicesPK) {
        this.indicesPK = indicesPK;
    }

    public Indices(String indApellidop, String indApellidom, String indNombres, long indCodigo) {
        this.indicesPK = new IndicesPK(indApellidop, indApellidom, indNombres, indCodigo);
    }

    public IndicesPK getIndicesPK() {
        return indicesPK;
    }

    public void setIndicesPK(IndicesPK indicesPK) {
        this.indicesPK = indicesPK;
    }

    public Long getIndInscripcion() {
        return indInscripcion;
    }

    public void setIndInscripcion(Long indInscripcion) {
        this.indInscripcion = indInscripcion;
    }

    public String getIndApellidop1() {
        return indApellidop1;
    }

    public void setIndApellidop1(String indApellidop1) {
        this.indApellidop1 = indApellidop1;
    }

    public String getIndApellidom1() {
        return indApellidom1;
    }

    public void setIndApellidom1(String indApellidom1) {
        this.indApellidom1 = indApellidom1;
    }

    public String getIndNombres1() {
        return indNombres1;
    }

    public void setIndNombres1(String indNombres1) {
        this.indNombres1 = indNombres1;
    }

    public Long getIndFojas() {
        return indFojas;
    }

    public void setIndFojas(Long indFojas) {
        this.indFojas = indFojas;
    }

    public Short getIndTomo() {
        return indTomo;
    }

    public void setIndTomo(Short indTomo) {
        this.indTomo = indTomo;
    }

    public Short getIndVolumen() {
        return indVolumen;
    }

    public void setIndVolumen(Short indVolumen) {
        this.indVolumen = indVolumen;
    }

    public Date getIndFecha() {
        return indFecha;
    }

    public void setIndFecha(Date indFecha) {
        this.indFecha = indFecha;
    }

    public Short getIndBis() {
        return indBis;
    }

    public void setIndBis(Short indBis) {
        this.indBis = indBis;
    }

    public String getIndLibro() {
        return indLibro;
    }

    public void setIndLibro(String indLibro) {
        this.indLibro = indLibro;
    }

    public String getIndParroquia() {
        return indParroquia;
    }

    public void setIndParroquia(String indParroquia) {
        this.indParroquia = indParroquia;
    }

    public Short getIndCuantia() {
        return indCuantia;
    }

    public void setIndCuantia(Short indCuantia) {
        this.indCuantia = indCuantia;
    }

    public Long getIndRepertorio() {
        return indRepertorio;
    }

    public void setIndRepertorio(Long indRepertorio) {
        this.indRepertorio = indRepertorio;
    }

    public String getIndCompareciente() {
        return indCompareciente;
    }

    public void setIndCompareciente(String indCompareciente) {
        this.indCompareciente = indCompareciente;
    }

    public String getIndCompareciente1() {
        return indCompareciente1;
    }

    public void setIndCompareciente1(String indCompareciente1) {
        this.indCompareciente1 = indCompareciente1;
    }

    public String getIndContrato() {
        return indContrato;
    }

    public void setIndContrato(String indContrato) {
        this.indContrato = indContrato;
    }

    public String getIndCedula() {
        return indCedula;
    }

    public void setIndCedula(String indCedula) {
        this.indCedula = indCedula;
    }

    public Short getIndCodlibro() {
        return indCodlibro;
    }

    public void setIndCodlibro(Short indCodlibro) {
        this.indCodlibro = indCodlibro;
    }

    public Short getIndBloqueo() {
        return indBloqueo;
    }

    public void setIndBloqueo(Short indBloqueo) {
        this.indBloqueo = indBloqueo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indicesPK != null ? indicesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Indices)) {
            return false;
        }
        Indices other = (Indices) object;
        if ((this.indicesPK == null && other.indicesPK != null) || (this.indicesPK != null && !this.indicesPK.equals(other.indicesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.util.Indices[ indicesPK=" + indicesPK + " ]";
    }

}
