/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marco
 */
@Entity
@Table(name = "ListaMenu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaMenu.findAll", query = "SELECT l FROM ListaMenu l")
    , @NamedQuery(name = "ListaMenu.findByMenId", query = "SELECT l FROM ListaMenu l WHERE l.menId = :menId")
    , @NamedQuery(name = "ListaMenu.findByMenNombre", query = "SELECT l FROM ListaMenu l WHERE l.menNombre = :menNombre")
    , @NamedQuery(name = "ListaMenu.findByMenLink", query = "SELECT l FROM ListaMenu l WHERE l.menLink = :menLink")
    , @NamedQuery(name = "ListaMenu.findByMenIcono", query = "SELECT l FROM ListaMenu l WHERE l.menIcono = :menIcono")    
    , @NamedQuery(name = "ListaMenu.findByNivel", query = "SELECT l FROM ListaMenu l WHERE l.nivel = :nivel")})
    
public class ListaMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 20)
    @Column(name = "MenId")
    private String menId;
    @Size(max = 40)
    @Column(name = "MenNombre")
    private String menNombre;
    @Size(max = 1000)
    @Column(name = "MenLink")
    private String menLink;
    @Size(max = 40)
    @Column(name = "MenIcono")
    private String menIcono;
    @Column(name = "Nivel")
    private Integer nivel;
    @Size(max = 20)
    @Column(name = "MenPadre")
    private String menPadre;

    public String getMenPadre() {
        return menPadre;
    }

    public void setMenPadre(String menPadre) {
        this.menPadre = menPadre;
    }
    

    public ListaMenu() {
    }

    public ListaMenu(String menId) {
        this.menId = menId;
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

    public String getMenIcono() {
        return menIcono;
    }

    public void setMenIcono(String menIcono) {
        this.menIcono = menIcono;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menId != null ? menId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaMenu)) {
            return false;
        }
        ListaMenu other = (ListaMenu) object;
        if ((this.menId == null && other.menId != null) || (this.menId != null && !this.menId.equals(other.menId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Controlador.ListaMenu[ menId=" + menId + " ]";
    }
    
}
