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
 * @author Marco
 */
@Entity
@Table(name = "ListaLinderos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaLinderos.findAll", query = "SELECT l FROM ListaLinderos l")
    , @NamedQuery(name = "ListaLinderos.findByRepNumero", query = "SELECT l FROM ListaLinderos l WHERE l.repNumero = :repNumero")
    , @NamedQuery(name = "ListaLinderos.findByPrdMatricula", query = "SELECT l FROM ListaLinderos l WHERE l.prdMatricula = :prdMatricula")
    , @NamedQuery(name = "ListaLinderos.findByPrdDescripcion1", query = "SELECT l FROM ListaLinderos l WHERE l.prdDescripcion1 = :prdDescripcion1")
    , @NamedQuery(name = "ListaLinderos.findByLdrNombre", query = "SELECT l FROM ListaLinderos l WHERE l.ldrNombre = :ldrNombre")})
public class ListaLinderos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RepNumero")
    private int repNumero;
    @Basic(optional = false)
    @Id
    @Column(name = "PrdMatricula")
    private int prdMatricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "PrdDescripcion1")
    private String prdDescripcion1;
    @Size(max = 600)
    @Column(name = "LdrNombre")
    private String ldrNombre;

    public ListaLinderos() {
    }

    public int getRepNumero() {
        return repNumero;
    }

    public void setRepNumero(int repNumero) {
        this.repNumero = repNumero;
    }

    public int getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(int prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    public String getPrdDescripcion1() {
        return prdDescripcion1;
    }

    public void setPrdDescripcion1(String prdDescripcion1) {
        this.prdDescripcion1 = prdDescripcion1;
    }

    public String getLdrNombre() {
        return ldrNombre;
    }

    public void setLdrNombre(String ldrNombre) {
        this.ldrNombre = ldrNombre;
    }
    
}
