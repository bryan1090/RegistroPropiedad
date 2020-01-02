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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Institucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institucion.findAll", query = "SELECT i FROM Institucion i ORDER BY i.insNombre")
    , @NamedQuery(name = "Institucion.findByInsId", query = "SELECT i FROM Institucion i WHERE i.insId = :insId")
    , @NamedQuery(name = "Institucion.findByInsRuc", query = "SELECT i FROM Institucion i WHERE i.insRuc = :insRuc")
    , @NamedQuery(name = "Institucion.findByInsNombre", query = "SELECT i FROM Institucion i WHERE i.insNombre = :insNombre")
    , @NamedQuery(name = "Institucion.findByInsTelefono", query = "SELECT i FROM Institucion i WHERE i.insTelefono = :insTelefono")
    , @NamedQuery(name = "Institucion.findByInsCelular", query = "SELECT i FROM Institucion i WHERE i.insCelular = :insCelular")
    , @NamedQuery(name = "Institucion.findByInsLogo", query = "SELECT i FROM Institucion i WHERE i.insLogo = :insLogo")
    , @NamedQuery(name = "Institucion.findByInsEstado", query = "SELECT i FROM Institucion i WHERE i.insEstado = :insEstado")
    , @NamedQuery(name = "Institucion.findByInsFHR", query = "SELECT i FROM Institucion i WHERE i.insFHR = :insFHR")
    , @NamedQuery(name = "Institucion.findByInsUser", query = "SELECT i FROM Institucion i WHERE i.insUser = :insUser")
    , @NamedQuery(name = "Institucion.findByInsLinkLogo", query = "SELECT i FROM Institucion i WHERE i.insLinkLogo = :insLinkLogo")})
public class Institucion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insId")
    private List<Zona> zonaList;

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Institucion.findAll";
    public static final String LISTAR_PORID = "Institucion.findByInsId";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id    
    @NonNull
    @Basic(optional = false)
    @Column(name = "InsId")
    private Long insId;
    @Basic(optional = false)
    @Column(name = "InsRuc")
    private String insRuc;
    @Basic(optional = false)
    @Column(name = "InsNombre")
    private String insNombre;
    @Basic(optional = false)
    @Column(name = "InsTelefono")
    private String insTelefono;
    @Column(name = "InsCelular")
    private String insCelular;
    @Basic(optional = false)
    @Column(name = "InsLogo")
    private String insLogo;
    @Basic(optional = false)
    @Column(name = "InsEstado")
    private String insEstado;
    @Basic(optional = false)
    @Column(name = "InsFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insFHR;
    @Basic(optional = false)
    @Column(name = "InsUser")
    private String insUser;
    @Size(max = 200)
    @Column(name = "InsLinkLogo")
    private String insLinkLogo;     
    @Column(name = "InsCanId")
    private Long insCanId;

    @Getter
    @Setter
    @Column(name = "InsIva")
    private BigDecimal insIva;

    @Getter
    @Setter
    @Column(name = "InsAmbiente")
    private String insAmbiente;

    @Getter
    @Setter
    @Column(name = "InsEstablecimiento")
    private String insEstablecimiento;

    @Getter
    @Setter
    @Column(name = "InsDireccion")
    private String insDireccion;

    @Getter
    @Setter
    @Column(name = "InsRazonSocial")
    private String insRazonSocial;
    @Getter
    @Setter
    @Size(max = 500)
    @Column(name = "InsDescripcion")
    private String insDescripcion;
    @Getter
    @Setter
    @Size(max = 500)
    @Column(name = "InsDocumentoDescripcion")
    private String insDocumentoDescripcion;
    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "InsCodigoRegistro")
    private String insCodigoRegistro;
   

    public Institucion() {
        this.insUser = "ugenerico";

        this.insFHR = Calendar.getInstance().getTime();
    }

    public Institucion(Long insId) {
        this.insId = insId;
    }

    public Institucion(Long insId, String insRuc, String insNombre, String insTelefono, String insLogo, String insEstado, Date insFHR, String insUser) {
        //DATOS QUEMADOS
        this.insFHR = Calendar.getInstance().getTime();
        this.insUser = "ugenerico";

        this.insId = insId;
        this.insRuc = insRuc;
        this.insNombre = insNombre;
        this.insTelefono = insTelefono;
        this.insLogo = insLogo;
        this.insEstado = insEstado;
//        this.insFHR = insFHR;
//        this.insUser = insUser;
    }

    public Long getInsId() {
        return insId;
    }

    public void setInsId(Long insId) {
        this.insId = insId;
    }

    public String getInsRuc() {
        return insRuc;
    }

    public void setInsRuc(String insRuc) {
        this.insRuc = insRuc;
    }

    public String getInsNombre() {
        return insNombre;
    }

    public void setInsNombre(String insNombre) {
        this.insNombre = insNombre;
    }

    public String getInsTelefono() {
        return insTelefono;
    }

    public void setInsTelefono(String insTelefono) {
        this.insTelefono = insTelefono;
    }

    public String getInsCelular() {
        return insCelular;
    }

    public void setInsCelular(String insCelular) {
        this.insCelular = insCelular;
    }

    public String getInsLogo() {
        return insLogo;
    }

    public void setInsLogo(String insLogo) {
        this.insLogo = insLogo;
    }

    public String getInsEstado() {
        return insEstado;
    }

    public void setInsEstado(String insEstado) {
        this.insEstado = insEstado;
    }

    public Date getInsFHR() {
        return insFHR;
    }

    public void setInsFHR(Date insFHR) {
        this.insFHR = insFHR;
    }

    public String getInsUser() {
        return insUser;
    }

    public void setInsUser(String insUser) {
        this.insUser = insUser;
    }
    
    public String getInsLinkLogo() {
        return insLinkLogo;
    }
    public Long getInsCanId() {
        return insCanId;
    }

    public void setInsCanId(Long insCanId) {
        this.insCanId = insCanId;
    }

    public void setInsLinkLogo(String insLinkLogo) {
        this.insLinkLogo = insLinkLogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insId != null ? insId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institucion)) {
            return false;
        }
        Institucion other = (Institucion) object;
        if ((this.insId == null && other.insId != null) || (this.insId != null && !this.insId.equals(other.insId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Institucion[ insId=" + insId + " ]";
    }

    @XmlTransient
    public List<Zona> getZonaList() {
        return zonaList;
    }

    public void setZonaList(List<Zona> zonaList) {
        this.zonaList = zonaList;
    }

}
