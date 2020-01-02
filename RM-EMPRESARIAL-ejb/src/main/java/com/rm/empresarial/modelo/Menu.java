/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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

/**
 *
 * @author Marco
 */
@Entity
@Table(name = "Menu")
@XmlRootElement
@NamedQueries({
    
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m ")    
    , @NamedQuery(name = "Menu.find", query = "SELECT m FROM Menu m WHERE m.menId != 0")
    , @NamedQuery(name = "Menu.findByMenPadre", query = "SELECT m FROM Menu m WHERE m.menPadre = :menPadre")
    , @NamedQuery(name = "Menu.findByMenId", query = "SELECT m FROM Menu m WHERE m.menId = :menId")
    , @NamedQuery(name = "Menu.findByMenNombre", query = "SELECT m FROM Menu m WHERE m.menNombre = :menNombre")
    , @NamedQuery(name = "Menu.findByMenLink", query = "SELECT m FROM Menu m WHERE m.menLink = :menLink")
    , @NamedQuery(name = "Menu.findByMenIcono", query = "SELECT m FROM Menu m WHERE m.menIcono = :menIcono")
    , @NamedQuery(name = "Menu.findByMenEstado", query = "SELECT m FROM Menu m WHERE m.menEstado = :menEstado")
    , @NamedQuery(name = "Menu.findByMenFHR", query = "SELECT m FROM Menu m WHERE m.menFHR = :menFHR")
    , @NamedQuery(name = "Menu.findByMenUser", query = "SELECT m FROM Menu m WHERE m.menUser = :menUser")
    , @NamedQuery(name = "Menu.findByMenNombreEditar", query = "SELECT m FROM Menu m WHERE m.menNombre = :menNombre AND m.menId != :menId")})
public class Menu implements Serializable {
    public static final String LISTAR_TODO = "Menu.findAll";
    public static final String LISTAR = "Menu.find";
    public static final String BUSCAR_POR_ID="Menu.findByMenId";
    public static final String LISTAR_POR_PADRES="Menu.findByPadre";
    public static final String BUSCAR_POR_NOM="Menu.findByMenNombre";
    public static final String BUSCAR_POR_NOM_EDITAR="Menu.findByMenNombreEditar";
   

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
 
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MenId")
    private String menId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "MenNombre")
    private String menNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "MenLink")
    private String menLink;
   
    @Size(max = 40)
    @Column(name = "MenIcono")
    private String menIcono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "MenEstado")
    private String menEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MenFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date menFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "MenUser")
    private String menUser;
    @Size(max = 40)
    @Column(name = "MenAgrupa1")
    private String menAgrupa1;
    @Column(name = "MenAgrupa2")
    private BigDecimal menAgrupa2;
    @Size(max = 3)
    @Column(name = "MenAgrupa0")
    private String menAgrupa0;
    @JoinColumn(name = "MenPadre", referencedColumnName = "MenId")
    @ManyToOne
    private Menu menPadre;
    

    public Menu() {
    }

    public Menu(String menId) {
        this.menId = menId;
    }

    public Menu(String menId, String menNombre, String menLink, String menEstado, Date menFHR, String menUser) {
        this.menId = menId;
        this.menNombre = menNombre;
        this.menLink = menLink;
        this.menEstado = menEstado;
        this.menFHR = menFHR;
        this.menUser = menUser;
    }

    public String getMenId() {
        return menId;
    }

 

    public void setMenId(String menId) {
        this.menId = menId;
    }

    public String getMenNombre() {
        return menNombre;
    }

    public void setMenNombre(String menNombre) {
        this.menNombre = menNombre;
    }

    public String getMenLink() {
        return menLink;
    }

    public void setMenLink(String menLink) {
        this.menLink = menLink;
    }

    public Menu getMenPadre() {
        return menPadre;
    }

    public void setMenPadre(Menu menPadre) {
        this.menPadre = menPadre;
    }

    public String getMenIcono() {
        return menIcono;
    }

    public void setMenIcono(String menIcono) {
        this.menIcono = menIcono;
    }

    public String getMenEstado() {
        return menEstado;
    }

    public void setMenEstado(String menEstado) {
        this.menEstado = menEstado;
    }

    public Date getMenFHR() {
        return menFHR;
    }

    public void setMenFHR(Date menFHR) {
        this.menFHR = menFHR;
    }

    public String getMenUser() {
        return menUser;
    }

    public void setMenUser(String menUser) {
        this.menUser = menUser;
    }

    public String getMenAgrupa1() {
        return menAgrupa1;
    }

    public void setMenAgrupa1(String menAgrupa1) {
        this.menAgrupa1 = menAgrupa1;
    }

    public BigDecimal getMenAgrupa2() {
        return menAgrupa2;
    }

    public void setMenAgrupa2(BigDecimal menAgrupa2) {
        this.menAgrupa2 = menAgrupa2;
    }

    public String getMenAgrupa0() {
        return menAgrupa0;
    }

    public void setMenAgrupa0(String menAgrupa0) {
        this.menAgrupa0 = menAgrupa0;
    }

    
    /*
    @JoinColumn(name = "MenPadre", referencedColumnName = "MenId")
    @ManyToOne
    private String menPadre;
*/

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menId != null ? menId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.menId == null && other.menId != null) || (this.menId != null && !this.menId.equals(other.menId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.menun.Menu[ menId=" + menId + " ]";
    }
    
}
