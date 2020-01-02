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
 * @author Marco
 */
@Entity
@Table(name = "RolMenu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolMenu.findAll", query = "SELECT r FROM RolMenu r ORDER BY r.menId.menNombre")
    , @NamedQuery(name = "RolMenu.findByRolMenId", query = "SELECT r FROM RolMenu r WHERE r.rolMenId = :rolMenId")
    , @NamedQuery(name = "RolMenu.findByRolMenInsert", query = "SELECT r FROM RolMenu r WHERE r.rolMenInsert = :rolMenInsert")
    , @NamedQuery(name = "RolMenu.findByRolMenUpdate", query = "SELECT r FROM RolMenu r WHERE r.rolMenUpdate = :rolMenUpdate")
    , @NamedQuery(name = "RolMenu.findByRolMenDelete", query = "SELECT r FROM RolMenu r WHERE r.rolMenDelete = :rolMenDelete")
    , @NamedQuery(name = "RolMenu.findByRolMenExcel", query = "SELECT r FROM RolMenu r WHERE r.rolMenExcel = :rolMenExcel")
    , @NamedQuery(name = "RolMenu.findByRolMenPdf", query = "SELECT r FROM RolMenu r WHERE r.rolMenPdf = :rolMenPdf")
    , @NamedQuery(name = "RolMenu.findByRolMenAcceso", query = "SELECT r FROM RolMenu r WHERE r.rolMenAcceso = :rolMenAcceso")
    , @NamedQuery(name = "RolMenu.findByRolMenFHR", query = "SELECT r FROM RolMenu r WHERE r.rolMenFHR = :rolMenFHR")
    , @NamedQuery(name = "RolMenu.findByRolMenUser", query = "SELECT r FROM RolMenu r WHERE r.rolMenUser = :rolMenUser")
    , @NamedQuery(name = "RolMenu.encontrarPorIdMenuYIdRol", query = "SELECT r FROM RolMenu r WHERE r.rolId = :RolId AND r.menId = :MenId")
    , @NamedQuery(name = "RolMenu.listarPadres", query = "SELECT r FROM RolMenu r WHERE r.rolId = :RolId AND r.menId = :MenId")
    , @NamedQuery(name = "RolMenu.findByRolMenDisplay", query = "SELECT r FROM RolMenu r WHERE r.rolMenDisplay = :rolMenDisplay")})
public class RolMenu implements Serializable {
    public static final String LISTAR_TODO = "RolMenu.findAll";
    public static final String LISTAR_POR_ID = "RolMenu.findByRolMenId";
    
    public static final String BUSCAR_POR_IDIDMENU_POR_IDROL = "RolMenu.encontrarPorIdMenuYIdRol";
    public static final String LISTAR_MENU_PADRES = "RolMenu.encontrarPorIdMenuYIdRol";

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RolMenId")
    private Long rolMenId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RolMenInsert")
    private String rolMenInsert;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RolMenUpdate")
    private String rolMenUpdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RolMenDelete")
    private String rolMenDelete;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RolMenExcel")
    private String rolMenExcel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RolMenPdf")
    private String rolMenPdf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RolMenAcceso")
    private String rolMenAcceso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RolMenFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolMenFHR;
    @Size(max = 20)
    @Column(name = "RolMenUser")
    private String rolMenUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RolMenDisplay")
    private String rolMenDisplay;
    @JoinColumn(name = "MenId", referencedColumnName = "MenId")
    @ManyToOne(optional = false)
    private Menu menId;
    @JoinColumn(name = "RolId", referencedColumnName = "RolId")
    @ManyToOne(optional = false)
    private Rol rolId;
    

    public RolMenu() {
    }

    public RolMenu(Long rolMenId) {
        this.rolMenId = rolMenId;
    }

    public RolMenu(Long rolMenId, String rolMenInsert, String rolMenUpdate, String rolMenDelete, String rolMenExcel, String rolMenPdf, String rolMenAcceso, Date rolMenFHR, String rolMenDisplay) {
        this.rolMenId = rolMenId;
        this.rolMenInsert = rolMenInsert;
        this.rolMenUpdate = rolMenUpdate;
        this.rolMenDelete = rolMenDelete;
        this.rolMenExcel = rolMenExcel;
        this.rolMenPdf = rolMenPdf;
        this.rolMenAcceso = rolMenAcceso;
        this.rolMenFHR = rolMenFHR;
        this.rolMenDisplay = rolMenDisplay;
    }

    public Long getRolMenId() {
        return rolMenId;
    }

    public void setRolMenId(Long rolMenId) {
        this.rolMenId = rolMenId;
    }

    public String getRolMenInsert() {
        return rolMenInsert;
    }

    public void setRolMenInsert(String rolMenInsert) {
        this.rolMenInsert = rolMenInsert;
    }

    public String getRolMenUpdate() {
        return rolMenUpdate;
    }

    public void setRolMenUpdate(String rolMenUpdate) {
        this.rolMenUpdate = rolMenUpdate;
    }

    public String getRolMenDelete() {
        return rolMenDelete;
    }

    public void setRolMenDelete(String rolMenDelete) {
        this.rolMenDelete = rolMenDelete;
    }

    public String getRolMenExcel() {
        return rolMenExcel;
    }

    public void setRolMenExcel(String rolMenExcel) {
        this.rolMenExcel = rolMenExcel;
    }

    public String getRolMenPdf() {
        return rolMenPdf;
    }

    public void setRolMenPdf(String rolMenPdf) {
        this.rolMenPdf = rolMenPdf;
    }

    public String getRolMenAcceso() {
        return rolMenAcceso;
    }

    public void setRolMenAcceso(String rolMenAcceso) {
        this.rolMenAcceso = rolMenAcceso;
    }

    public Date getRolMenFHR() {
        return rolMenFHR;
    }

    public void setRolMenFHR(Date rolMenFHR) {
        this.rolMenFHR = rolMenFHR;
    }

    public String getRolMenUser() {
        return rolMenUser;
    }

    public void setRolMenUser(String rolMenUser) {
        this.rolMenUser = rolMenUser;
    }

    public String getRolMenDisplay() {
        return rolMenDisplay;
    }

    public void setRolMenDisplay(String rolMenDisplay) {
        this.rolMenDisplay = rolMenDisplay;
    }

    public Menu getMenId() {
        return menId;
    }

    public void setMenId(Menu menId) {
        this.menId = menId;
    }

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolMenId != null ? rolMenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolMenu)) {
            return false;
        }
        RolMenu other = (RolMenu) object;
        if ((this.rolMenId == null && other.rolMenId != null) || (this.rolMenId != null && !this.rolMenId.equals(other.rolMenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.RolMenu[ rolMenId=" + rolMenId + " ]";
    }
    
}
