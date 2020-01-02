/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
@Table(name = "Persona")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "Persona.listarTodo", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findByPerId", query = "SELECT p FROM Persona p WHERE p.perId = :perId")
    , @NamedQuery(name = "Persona.findByPerTipoIdentificacion", query = "SELECT p FROM Persona p WHERE p.perTipoIdentificacion = :perTipoIdentificacion")
    , @NamedQuery(name = "Persona.findByPerIdentificacion", query = "SELECT p FROM Persona p WHERE p.perIdentificacion = :perIdentificacion")
    , @NamedQuery(name = "Persona.findByPerNombre", query = "SELECT p FROM Persona p WHERE p.perNombre = :perNombre")
    , @NamedQuery(name = "Persona.findByPerApellidoPaterno", query = "SELECT p FROM Persona p WHERE p.perApellidoPaterno = :perApellidoPaterno")
    , @NamedQuery(name = "Persona.findByPerApellidoMaterno", query = "SELECT p FROM Persona p WHERE p.perApellidoMaterno = :perApellidoMaterno")
    , @NamedQuery(name = "Persona.findByPerFechaNacimiento", query = "SELECT p FROM Persona p WHERE p.perFechaNacimiento = :perFechaNacimiento")
    , @NamedQuery(name = "Persona.findByPerFechaDefuncion", query = "SELECT p FROM Persona p WHERE p.perFechaDefuncion = :perFechaDefuncion")
    , @NamedQuery(name = "Persona.findByPerTelefono", query = "SELECT p FROM Persona p WHERE p.perTelefono = :perTelefono")
    , @NamedQuery(name = "Persona.findByPerCelular", query = "SELECT p FROM Persona p WHERE p.perCelular = :perCelular")
    , @NamedQuery(name = "Persona.findByPerEmail", query = "SELECT p FROM Persona p WHERE p.perEmail = :perEmail")
    , @NamedQuery(name = "Persona.findByPerDireccion", query = "SELECT p FROM Persona p WHERE p.perDireccion = :perDireccion")
    , @NamedQuery(name = "Persona.findByPerAlt1", query = "SELECT p FROM Persona p WHERE p.perAlt1 = :perAlt1")
    , @NamedQuery(name = "Persona.findByPerAlt2", query = "SELECT p FROM Persona p WHERE p.perAlt2 = :perAlt2")
    , @NamedQuery(name = "Persona.findByPerAlt3", query = "SELECT p FROM Persona p WHERE p.perAlt3 = :perAlt3")
    , @NamedQuery(name = "Persona.findByPerAlt4", query = "SELECT p FROM Persona p WHERE p.perAlt4 = :perAlt4")
    , @NamedQuery(name = "Persona.findByPerAlt5", query = "SELECT p FROM Persona p WHERE p.perAlt5 = :perAlt5")
    , @NamedQuery(name = "Persona.findByPerEstado", query = "SELECT p FROM Persona p WHERE p.perEstado = :perEstado")
    , @NamedQuery(name = "Persona.findByPerUser", query = "SELECT p FROM Persona p WHERE p.perUser = :perUser")
    , @NamedQuery(name = "Persona.findByPerFHR", query = "SELECT p FROM Persona p WHERE p.perFHR = :perFHR")
    , @NamedQuery(name = "Persona.findByPerEstadoCivil", query = "SELECT p FROM Persona p WHERE p.perEstadoCivil = :perEstadoCivil")
    , @NamedQuery(name = "Persona.findByPerCalidad", query = "SELECT p FROM Persona p WHERE p.perCalidad = :perCalidad")
    , @NamedQuery(name = "Persona.findByPerSexo", query = "SELECT p FROM Persona p WHERE p.perSexo = :perSexo")
    , @NamedQuery(name = "Persona.findByPerGenero", query = "SELECT p FROM Persona p WHERE p.perGenero = :perGenero")
    , @NamedQuery(name = "Persona.findByPerDisolucionConyugal", query = "SELECT p FROM Persona p WHERE p.perDisolucionConyugal = :perDisolucionConyugal")
    , @NamedQuery(name = "Persona.findByPerTipoContribuyente", query = "SELECT p FROM Persona p WHERE p.perTipoContribuyente = :perTipoContribuyente")
    , @NamedQuery(name = "PERSONA.LISTAPAGINADASP", query = " SELECT p FROM Persona p WHERE p.perFHR = :perFHR")
    , @NamedQuery(name = "Persona.LISTATOP1000", query = " SELECT p FROM Persona p WHERE p.perId<1000 ")})

public class Persona implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perId")
    private Collection<Factura> facturaCollection;

    private static final long serialVersionUID = 1L;

    public static final String LISTAR_TODO_PAGINADO = "PERSONA.LISTAPAGINADASP";
    public static final String LISTAR_TODO = "Persona.listarTodo";
    public static final String ENCONTRAR_PERSONA_POR_IDENTIFICACION = "Persona.findByPerIdentificacion";
    public static final String LISTAR_PRIMEROS_1000 = "Persona.LISTATOP1000";
    public static final String BUSCAR_CONYUGUE="Persona.findByPerId";
    public static final String BUSCAR_PERSONA_POR_ID="Persona.findByPerId";

    @Id
    @Basic(optional = false)
    @Column(name = "PerId")
    private Long perId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 10)
    @Column(name = "PerTipoIdentificacion",nullable = false)
    private String perTipoIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "PerIdentificacion")
    private String perIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(max = 200)
    @Column(name = "PerNombre")
    private String perNombre;
    @Basic(optional = false)
    @NotNull
    @Size(max = 200)
    @Column(name = "PerApellidoPaterno")
    private String perApellidoPaterno;
    @Size(max = 200)
    @Column(name = "PerApellidoMaterno")
    private String perApellidoMaterno;
    @Column(name = "PerFechaNacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechaNacimiento;
    @Column(name = "PerFechaDefuncion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechaDefuncion;
    @Size(max = 20)
    @Column(name = "PerTelefono")
    private String perTelefono;
    @Size(max = 20)
    @Column(name = "PerCelular")
    private String perCelular;
    @Size(max = 100)
    @Column(name = "PerEmail")
    private String perEmail;
    @Size(max = 1024)
    @Column(name = "PerDireccion")
    private String perDireccion;
    @Size(max = 400)
    @Column(name = "PerAlt1")
    private String perAlt1;
    @Size(max = 400)
    @Column(name = "PerAlt2")
    private String perAlt2;
    @Size(max = 400)
    @Column(name = "PerAlt3")
    private String perAlt3;
    @Size(max = 400)
    @Column(name = "PerAlt4")
    private String perAlt4;
    @Size(max = 400)
    @Column(name = "PerAlt5")
    private String perAlt5;
    @Size(max = 3)
    @Column(name = "PerEstado")
    private String perEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PerUser")
    private String perUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PerFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFHR;
    @Size(max = 3)
    @Column(name = "PerEstadoCivil")
    private String perEstadoCivil;
    @Size(max = 2)
    @Column(name = "PerCalidad")
    private String perCalidad;
    @Size(max = 2)
    @Column(name = "PerSexo")
    private String perSexo;
    @Size(max = 3)
    @Column(name = "PerGenero")
    private String perGenero;
    @Size(max = 1)
    @Column(name = "PerDisolucionConyugal")
    private String perDisolucionConyugal;
    @Size(max = 10)
    @Column(name = "PerTipoContribuyente")
    private String perTipoContribuyente;
    @OneToMany(mappedBy = "perIdConyuge")
    private Collection<Persona> personaCollection;
    @JoinColumn(name = "PerIdConyuge", referencedColumnName = "PerId")
    @ManyToOne
    private Persona perIdConyuge;
     @Column(name = "PerNacionalidad")
    private String perNacionalidad;
    @Getter
    @Setter
    @Size(max = 2)
    @Column(name = "PerMigrado")
    private String perMigrado;

    public String getPerNacionalidad() {
        return perNacionalidad;
    }

    public void setPerNacionalidad(String perNacionalidad) {
        this.perNacionalidad = perNacionalidad;
    }

   
    public Persona(Long perId) {
        this.perId = perId;
    }

     public Long getPerId() {
        return perId;
    }

    public void setPerId(Long perId) {
        this.perId = perId;
    }

    public String getPerTipoIdentificacion() {
        return perTipoIdentificacion;
    }

    public void setPerTipoIdentificacion(String perTipoIdentificacion) {
        this.perTipoIdentificacion = perTipoIdentificacion;
    }

    public String getPerIdentificacion() {
        return perIdentificacion;
    }

    public void setPerIdentificacion(String perIdentificacion) {
        this.perIdentificacion = perIdentificacion;
    }

    public String getPerNombre() {
        return perNombre;
    }

    public void setPerNombre(String perNombre) {
        this.perNombre = perNombre;
    }

    public String getPerApellidoPaterno() {
        return perApellidoPaterno;
    }

    public void setPerApellidoPaterno(String perApellidoPaterno) {
        this.perApellidoPaterno = perApellidoPaterno;
    }

    public String getPerApellidoMaterno() {
        return perApellidoMaterno;
    }

    public void setPerApellidoMaterno(String perApellidoMaterno) {
        this.perApellidoMaterno = perApellidoMaterno;
    }

    public Date getPerFechaNacimiento() {
        return perFechaNacimiento;
    }

    public void setPerFechaNacimiento(Date perFechaNacimiento) {
        this.perFechaNacimiento = perFechaNacimiento;
    }

    public Date getPerFechaDefuncion() {
        return perFechaDefuncion;
    }

    public void setPerFechaDefuncion(Date perFechaDefuncion) {
        this.perFechaDefuncion = perFechaDefuncion;
    }

    public String getPerTelefono() {
        return perTelefono;
    }

    public void setPerTelefono(String perTelefono) {
        this.perTelefono = perTelefono;
    }

    public String getPerCelular() {
        return perCelular;
    }

    public void setPerCelular(String perCelular) {
        this.perCelular = perCelular;
    }

    public String getPerEmail() {
        return perEmail;
    }

    public void setPerEmail(String perEmail) {
        this.perEmail = perEmail;
    }

    public String getPerDireccion() {
        return perDireccion;
    }

    public void setPerDireccion(String perDireccion) {
        this.perDireccion = perDireccion;
    }

    public String getPerAlt1() {
        return perAlt1;
    }

    public void setPerAlt1(String perAlt1) {
        this.perAlt1 = perAlt1;
    }

    public String getPerAlt2() {
        return perAlt2;
    }

    public void setPerAlt2(String perAlt2) {
        this.perAlt2 = perAlt2;
    }

    public String getPerAlt3() {
        return perAlt3;
    }

    public void setPerAlt3(String perAlt3) {
        this.perAlt3 = perAlt3;
    }

    public String getPerAlt4() {
        return perAlt4;
    }

    public void setPerAlt4(String perAlt4) {
        this.perAlt4 = perAlt4;
    }

    public String getPerAlt5() {
        return perAlt5;
    }

    public void setPerAlt5(String perAlt5) {
        this.perAlt5 = perAlt5;
    }

    public String getPerEstado() {
        return perEstado;
    }

    public void setPerEstado(String perEstado) {
        this.perEstado = perEstado;
    }

    public String getPerUser() {
        return perUser;
    }

    public void setPerUser(String perUser) {
        this.perUser = perUser;
    }

    public Date getPerFHR() {
        return perFHR;
    }

    public void setPerFHR(Date perFHR) {
        this.perFHR = perFHR;
    }

    public String getPerEstadoCivil() {
        return perEstadoCivil;
    }

    public void setPerEstadoCivil(String perEstadoCivil) {
        this.perEstadoCivil = perEstadoCivil;
    }

    public String getPerCalidad() {
        return perCalidad;
    }

    public void setPerCalidad(String perCalidad) {
        this.perCalidad = perCalidad;
    }

    public String getPerSexo() {
        return perSexo;
    }

    public void setPerSexo(String perSexo) {
        this.perSexo = perSexo;
    }

    public String getPerGenero() {
        return perGenero;
    }

    public void setPerGenero(String perGenero) {
        this.perGenero = perGenero;
    }

    public String getPerDisolucionConyugal() {
        return perDisolucionConyugal;
    }

    public void setPerDisolucionConyugal(String perDisolucionConyugal) {
        this.perDisolucionConyugal = perDisolucionConyugal;
    }

    public String getPerTipoContribuyente() {
        return perTipoContribuyente;
    }

    public void setPerTipoContribuyente(String perTipoContribuyente) {
        this.perTipoContribuyente = perTipoContribuyente;
    }

    @XmlTransient
    public Collection<Persona> getPersonaCollection() {
        return personaCollection;
    }

    public void setPersonaCollection(Collection<Persona> personaCollection) {
        this.personaCollection = personaCollection;
    }

    public Persona getPerIdConyuge() {
        return perIdConyuge;
    }

    public void setPerIdConyuge(Persona perIdConyuge) {
        this.perIdConyuge = perIdConyuge;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perId != null ? perId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.perId == null && other.perId != null) || (this.perId != null && !this.perId.equals(other.perId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persona{" + "perId=" + perId + ", perNombre=" + perNombre + '}';
    }

    
    
    public Persona() {
//        this.perFechaNacimiento = Calendar.getInstance().getTime();
        this.perDireccion = "";
        this.perAlt1 = "S";
        this.perAlt2 = "S";
        this.perAlt3 = "S";
        this.perAlt4 = "S";
        this.perAlt5 = "S";
//        this.perEstadoCivil="s";
//        this.perUser = "ugenerico";
        this.perFHR = Calendar.getInstance().getTime();
    }

    
    public Persona(Long perId, String perTipoIdentificacion, String perIdentificacion, String perNombre, String perApellidoPaterno, String perApellidoMaterno, Date perFechaNacimiento, String perDireccion, String perAlt1, String perAlt2, String perAlt3, String perAlt4, String perAlt5, String perEstado, String perUser, Date perFHR, BigInteger perIdConyuge, String perEstadoCivil) {
        this.perId = perId;
        this.perTipoIdentificacion = perTipoIdentificacion;
        this.perIdentificacion = perIdentificacion;
        this.perNombre = perNombre;
        this.perApellidoPaterno = perApellidoPaterno;
        this.perApellidoMaterno = perApellidoMaterno;
//        this.perFechaNacimiento = perFechaNacimiento;
//        this.perAlt1 = perAlt1;
//        this.perDireccion = perDireccion;
//        this.perAlt2 = perAlt2;
//        this.perAlt3 = perAlt3;
//        this.perAlt4 = perAlt4;
//        this.perAlt5 = perAlt5;
//        this.perUser = perUser;
//        this.perFHR = perFHR;
        this.perFechaNacimiento = Calendar.getInstance().getTime();
        this.perDireccion = "S";
        this.perAlt1 = "S";
        this.perAlt2 = "S";
        this.perAlt3 = "S";
        this.perAlt4 = "S";
        this.perAlt5 = "S";
        this.perEstadoCivil="s";
        this.perEstado = perEstado;
        this.perUser = "ugenerico";
        this.perFHR = Calendar.getInstance().getTime();
        this.perCalidad = perCalidad;
        this.perSexo = perSexo;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }
}
