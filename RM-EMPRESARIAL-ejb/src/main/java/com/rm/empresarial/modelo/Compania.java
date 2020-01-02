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
 * @author Prueba
 */
@Entity
@Table(name = "Compania")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compania.findAll", query = "SELECT c FROM Compania c")
    , @NamedQuery(name = "Compania.findByComId", query = "SELECT c FROM Compania c WHERE c.comId = :comId")
    , @NamedQuery(name = "Compania.findByComNombre", query = "SELECT c FROM Compania c WHERE c.comNombre = :comNombre")
    , @NamedQuery(name = "Compania.findByComConstructora", query = "SELECT c FROM Compania c WHERE c.comConstructora = :comConstructora")
    , @NamedQuery(name = "Compania.findByComCodigo", query = "SELECT c FROM Compania c WHERE c.comCodigo = :comCodigo")
    , @NamedQuery(name = "Compania.findByComAclaratoria", query = "SELECT c FROM Compania c WHERE c.comAclaratoria = :comAclaratoria")
    , @NamedQuery(name = "Compania.findByComRevocatoria", query = "SELECT c FROM Compania c WHERE c.comRevocatoria = :comRevocatoria")
    , @NamedQuery(name = "Compania.findByComUser", query = "SELECT c FROM Compania c WHERE c.comUser = :comUser")
    , @NamedQuery(name = "Compania.findByComFHR", query = "SELECT c FROM Compania c WHERE c.comFHR = :comFHR")
    , @NamedQuery(name = "Compania.findByComDes1", query = "SELECT c FROM Compania c WHERE c.comDes1 = :comDes1")
    , @NamedQuery(name = "Compania.findByComDes2", query = "SELECT c FROM Compania c WHERE c.comDes2 = :comDes2")})
public class Compania implements Serializable {

    public static final String LISTAR_TODOS = "Compania.findAll";
    public static final String BUSCAR_POR_ID_COMPANIA = "Compania.findByComId";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComId")
    private Long comId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ComNombre")
    private String comNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ComConstructora")
    private String comConstructora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ComCodigo")
    private String comCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ComAclaratoria")
    private short comAclaratoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ComRevocatoria")
    private short comRevocatoria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ComUser")
    private String comUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ComFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date comFHR;
    @Size(max = 200)
    @Column(name = "ComDes1")
    private String comDes1;
    @Size(max = 200)
    @Column(name = "ComDes2")
    private String comDes2;
    @JoinColumn(name = "ParId", referencedColumnName = "ParId")
    @ManyToOne(optional = false)
    private Parroquia parId;

    public Compania() {
    }

    public Compania(Long comId) {
        this.comId = comId;
    }

    public Compania(Long comId, String comNombre, String comConstructora, String comCodigo, short comAclaratoria, short comRevocatoria, String comUser, Date comFHR) {
        this.comId = comId;
        this.comNombre = comNombre;
        this.comConstructora = comConstructora;
        this.comCodigo = comCodigo;
        this.comAclaratoria = comAclaratoria;
        this.comRevocatoria = comRevocatoria;
        this.comUser = comUser;
        this.comFHR = comFHR;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public String getComNombre() {
        return comNombre;
    }

    public void setComNombre(String comNombre) {
        this.comNombre = comNombre;
    }

    public String getComConstructora() {
        return comConstructora;
    }

    public void setComConstructora(String comConstructora) {
        this.comConstructora = comConstructora;
    }

    public String getComCodigo() {
        return comCodigo;
    }

    public void setComCodigo(String comCodigo) {
        this.comCodigo = comCodigo;
    }

    public short getComAclaratoria() {
        return comAclaratoria;
    }

    public void setComAclaratoria(short comAclaratoria) {
        this.comAclaratoria = comAclaratoria;
    }

    public short getComRevocatoria() {
        return comRevocatoria;
    }

    public void setComRevocatoria(short comRevocatoria) {
        this.comRevocatoria = comRevocatoria;
    }

    public String getComUser() {
        return comUser;
    }

    public void setComUser(String comUser) {
        this.comUser = comUser;
    }

    public Date getComFHR() {
        return comFHR;
    }

    public void setComFHR(Date comFHR) {
        this.comFHR = comFHR;
    }

    public String getComDes1() {
        return comDes1;
    }

    public void setComDes1(String comDes1) {
        this.comDes1 = comDes1;
    }

    public String getComDes2() {
        return comDes2;
    }

    public void setComDes2(String comDes2) {
        this.comDes2 = comDes2;
    }

    public Parroquia getParId() {
        return parId;
    }

    public void setParId(Parroquia parId) {
        this.parId = parId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comId != null ? comId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compania)) {
            return false;
        }
        Compania other = (Compania) object;
        if ((this.comId == null && other.comId != null) || (this.comId != null && !this.comId.equals(other.comId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.Compania[ comId=" + comId + " ]";
    }
    
}
