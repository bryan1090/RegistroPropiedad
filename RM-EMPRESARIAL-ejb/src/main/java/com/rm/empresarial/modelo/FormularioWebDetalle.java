/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author DJimenez
 */
@Entity
@Table(name = "FormularioWebDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormularioWebDetalle.findAll", query = "SELECT f FROM FormularioWebDetalle f")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdId", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdId = :fwdId")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdDescripcion", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdDescripcion = :fwdDescripcion")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdGrupo", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdGrupo = :fwdGrupo")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdLinea", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdLinea = :fwdLinea")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdVariable", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdVariable = :fwdVariable")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorC01", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorC01 = :fwdValorC01")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorC02", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorC02 = :fwdValorC02")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorC03", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorC03 = :fwdValorC03")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorC04", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorC04 = :fwdValorC04")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorC05", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorC05 = :fwdValorC05")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorN01", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorN01 = :fwdValorN01")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorN02", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorN02 = :fwdValorN02")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorN03", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorN03 = :fwdValorN03")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorN04", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorN04 = :fwdValorN04")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorN05", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorN05 = :fwdValorN05")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorF01", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorF01 = :fwdValorF01")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorF02", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorF02 = :fwdValorF02")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorF03", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorF03 = :fwdValorF03")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorF04", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorF04 = :fwdValorF04")
    , @NamedQuery(name = "FormularioWebDetalle.findByFwdValorF05", query = "SELECT f FROM FormularioWebDetalle f WHERE f.fwdValorF05 = :fwdValorF05")})
public class FormularioWebDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FwdId")
    private Integer fwdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "FwdDescripcion")
    private String fwdDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FwdGrupo")
    private String fwdGrupo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FwdLinea")
    private int fwdLinea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FwdVariable")
    private String fwdVariable;
    @Size(max = 100)
    @Column(name = "FwdValorC01")
    private String fwdValorC01;
    @Size(max = 100)
    @Column(name = "FwdValorC02")
    private String fwdValorC02;
    @Size(max = 100)
    @Column(name = "FwdValorC03")
    private String fwdValorC03;
    @Size(max = 100)
    @Column(name = "FwdValorC04")
    private String fwdValorC04;
    @Size(max = 100)
    @Column(name = "FwdValorC05")
    private String fwdValorC05;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FwdValorN01")
    private Long fwdValorN01;
    @Column(name = "FwdValorN02")
    private Long fwdValorN02;
    @Column(name = "FwdValorN03")
    private Long fwdValorN03;
    @Column(name = "FwdValorN04")
    private Long fwdValorN04;
    @Column(name = "FwdValorN05")
    private Long fwdValorN05;
    @Column(name = "FwdValorF01")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fwdValorF01;
    @Column(name = "FwdValorF02")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fwdValorF02;
    @Column(name = "FwdValorF03")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fwdValorF03;
    @Column(name = "FwdValorF04")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fwdValorF04;
    @Column(name = "FwdValorF05")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fwdValorF05;
    @JoinColumn(name = "FlwId", referencedColumnName = "FlwId")
    @ManyToOne(optional = false)
    private FormularioWeb flwId;

    public FormularioWebDetalle() {
    }

    public FormularioWebDetalle(Integer fwdId) {
        this.fwdId = fwdId;
    }

    public FormularioWebDetalle(Integer fwdId, String fwdDescripcion, String fwdGrupo, int fwdLinea, String fwdVariable) {
        this.fwdId = fwdId;
        this.fwdDescripcion = fwdDescripcion;
        this.fwdGrupo = fwdGrupo;
        this.fwdLinea = fwdLinea;
        this.fwdVariable = fwdVariable;
    }

    public Integer getFwdId() {
        return fwdId;
    }

    public void setFwdId(Integer fwdId) {
        this.fwdId = fwdId;
    }

    public String getFwdDescripcion() {
        return fwdDescripcion;
    }

    public void setFwdDescripcion(String fwdDescripcion) {
        this.fwdDescripcion = fwdDescripcion;
    }

    public String getFwdGrupo() {
        return fwdGrupo;
    }

    public void setFwdGrupo(String fwdGrupo) {
        this.fwdGrupo = fwdGrupo;
    }

    public int getFwdLinea() {
        return fwdLinea;
    }

    public void setFwdLinea(int fwdLinea) {
        this.fwdLinea = fwdLinea;
    }

    public String getFwdVariable() {
        return fwdVariable;
    }

    public void setFwdVariable(String fwdVariable) {
        this.fwdVariable = fwdVariable;
    }

    public String getFwdValorC01() {
        return fwdValorC01;
    }

    public void setFwdValorC01(String fwdValorC01) {
        this.fwdValorC01 = fwdValorC01;
    }

    public String getFwdValorC02() {
        return fwdValorC02;
    }

    public void setFwdValorC02(String fwdValorC02) {
        this.fwdValorC02 = fwdValorC02;
    }

    public String getFwdValorC03() {
        return fwdValorC03;
    }

    public void setFwdValorC03(String fwdValorC03) {
        this.fwdValorC03 = fwdValorC03;
    }

    public String getFwdValorC04() {
        return fwdValorC04;
    }

    public void setFwdValorC04(String fwdValorC04) {
        this.fwdValorC04 = fwdValorC04;
    }

    public String getFwdValorC05() {
        return fwdValorC05;
    }

    public void setFwdValorC05(String fwdValorC05) {
        this.fwdValorC05 = fwdValorC05;
    }

    public Long getFwdValorN01() {
        return fwdValorN01;
    }

    public void setFwdValorN01(Long fwdValorN01) {
        this.fwdValorN01 = fwdValorN01;
    }

    public Long getFwdValorN02() {
        return fwdValorN02;
    }

    public void setFwdValorN02(Long fwdValorN02) {
        this.fwdValorN02 = fwdValorN02;
    }

    public Long getFwdValorN03() {
        return fwdValorN03;
    }

    public void setFwdValorN03(Long fwdValorN03) {
        this.fwdValorN03 = fwdValorN03;
    }

    public Long getFwdValorN04() {
        return fwdValorN04;
    }

    public void setFwdValorN04(Long fwdValorN04) {
        this.fwdValorN04 = fwdValorN04;
    }

    public Long getFwdValorN05() {
        return fwdValorN05;
    }

    public void setFwdValorN05(Long fwdValorN05) {
        this.fwdValorN05 = fwdValorN05;
    }

    public Date getFwdValorF01() {
        return fwdValorF01;
    }

    public void setFwdValorF01(Date fwdValorF01) {
        this.fwdValorF01 = fwdValorF01;
    }

    public Date getFwdValorF02() {
        return fwdValorF02;
    }

    public void setFwdValorF02(Date fwdValorF02) {
        this.fwdValorF02 = fwdValorF02;
    }

    public Date getFwdValorF03() {
        return fwdValorF03;
    }

    public void setFwdValorF03(Date fwdValorF03) {
        this.fwdValorF03 = fwdValorF03;
    }

    public Date getFwdValorF04() {
        return fwdValorF04;
    }

    public void setFwdValorF04(Date fwdValorF04) {
        this.fwdValorF04 = fwdValorF04;
    }

    public Date getFwdValorF05() {
        return fwdValorF05;
    }

    public void setFwdValorF05(Date fwdValorF05) {
        this.fwdValorF05 = fwdValorF05;
    }

    public FormularioWeb getFlwId() {
        return flwId;
    }

    public void setFlwId(FormularioWeb flwId) {
        this.flwId = flwId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fwdId != null ? fwdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioWebDetalle)) {
            return false;
        }
        FormularioWebDetalle other = (FormularioWebDetalle) object;
        if ((this.fwdId == null && other.fwdId != null) || (this.fwdId != null && !this.fwdId.equals(other.fwdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.FormularioWebDetalle[ fwdId=" + fwdId + " ]";
    }
    
}
