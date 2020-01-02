/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.listarTodo", query = "SELECT u FROM Usuario u ORDER BY u.usuLogin")
    , @NamedQuery(name = "Usuario.encontrarPorUsuarioId", query = "SELECT u FROM Usuario u WHERE u.usuId = :usuId")
    , @NamedQuery(name = "Usuario.findByUsuLogin", query = "SELECT u FROM Usuario u WHERE u.usuLogin = :usuLogin")
    , @NamedQuery(name = "Usuario.findByUsuContrasenia", query = "SELECT u FROM Usuario u WHERE u.usuContrasenia = :usuContrasenia")
    , @NamedQuery(name = "Usuario.findByUsuEmail", query = "SELECT u FROM Usuario u WHERE u.usuEmail = :usuEmail")
    , @NamedQuery(name = "Usuario.findByUsuEstado", query = "SELECT u FROM Usuario u WHERE u.usuEstado = :usuEstado")
    , @NamedQuery(name = "Usuario.findByUsuUser", query = "SELECT u FROM Usuario u WHERE u.usuUser = :usuUser")
    , @NamedQuery(name = "Usuario.findByUsuFHR", query = "SELECT u FROM Usuario u WHERE u.usuFHR = :usuFHR")
    , @NamedQuery(name = "Usuario.findByUsuFechaInicio", query = "SELECT u FROM Usuario u WHERE u.usuFechaInicio = :usuFechaInicio")
    , @NamedQuery(name = "Usuario.findByUsuFechaFin", query = "SELECT u FROM Usuario u WHERE u.usuFechaFin = :usuFechaFin")
    , @NamedQuery(name = "Usuario.encontrarUsuarioPorLoginContrasenia", query = "SELECT u FROM Usuario u WHERE u.usuLogin = :usuLogin AND u.usuContrasenia = :usuContrasenia")
    , @NamedQuery(name = "Usuario.encontrarUsuarioPorLoginEmail", query = "SELECT u FROM Usuario u WHERE u.usuLogin = :usuLogin AND u.usuEmail = :usuEmail")
    , @NamedQuery(name = "Usuario.findByUsuPerId", query = "SELECT u FROM Usuario u WHERE u.perId = :perId")
    , @NamedQuery(name = "Usuario.findByUsuLoginEditar", query = "SELECT u FROM Usuario u WHERE u.usuLogin = :usuLogin AND u.usuId != :usuId")
    , @NamedQuery(name = "Usuario.findByUsuEmailEditar", query = "SELECT u FROM Usuario u WHERE u.usuEmail = :usuEmail AND u.usuId != :usuId")
    , @NamedQuery(name = "Usuario.findByUsuPerIdEditar", query = "SELECT u FROM Usuario u WHERE u.perId = :perId AND u.usuId != :usuId")
    , @NamedQuery(name = "Usuario.findByUsuFirmaContrasenia", query = "SELECT u FROM Usuario u WHERE u.usuFirmaContrasenia = :usuFirmaContrasenia")
    , @NamedQuery(name = "Usuario.findByUsuPathFirma", query = "SELECT u FROM Usuario u WHERE u.usuPathFirma = :usuPathFirma")
    , @NamedQuery(name = "Usuario.findByUsuFirHuella", query = "SELECT u FROM Usuario u WHERE u.usuFirHuella = :usuFirHuella")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Usuario.listarTodo";
    public static final String LISTAR_TODO_SIN_WEB = "Usuario.listarTodoSinWeb";
    public static final String ENCONTRAR_POR_USUARIO_ID = "Usuario.encontrarPorUsuarioId";
    public static final String ENCONTRAR_USUARIO_POR_LOGIN_CONTRASENIA = "Usuario.encontrarUsuarioPorLoginContrasenia";
    public static final String ENCONTRAR_USUARIO_POR_NOMBRE = "Usuario.findByUsuLogin";
    public static final String BUSCAR_USUARIO_POR_ROL = "SELECT TOP 1 usu.* FROM Usuario usu "
            + " inner join Rol rol on usu.RolId=rol.RolId"
            + " where rol.RolId= ? ";
    public static final String LISTAR_USUARIOS_POR_ROL = "SELECT * FROM Usuario usu "
            + "inner join Rol rol on usu.RolId=rol.RolId"
            + " where rol.RolId= ? ";

    public static final String BUSCAR_USUARIO_LOGIN_EMAIL = "Usuario.encontrarUsuarioPorLoginEmail";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    public static final String BUSCAR_POR_LOGIN = "Usuario.findByUsuLogin";
    public static final String BUSCAR_POR_EMAIL = "Usuario.findByUsuEmail";
    public static final String BUSCAR_POR_PERID = "Usuario.findByUsuPerId";
    public static final String BUSCAR_POR_LOGIN_EDITAR = "Usuario.findByUsuLoginEditar";
    public static final String BUSCAR_POR_EMAIL_EDITAR = "Usuario.findByUsuEmailEditar";
    public static final String BUSCAR_POR_PERID_EDITAR = "Usuario.findByUsuPerIdEditar";

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsuId")
    private Long usuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "UsuLogin")
    private String usuLogin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "UsuContrasenia")
    private String usuContrasenia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "UsuEmail")
    private String usuEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "UsuEstado")
    private String usuEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "UsuUser")
    private String usuUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UsuFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFHR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UsuFechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UsuFechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaFin;
    @Size(max = 512)
    @Column(name = "UsuImagen")
    private String usuImagen;
    @Size(max = 2000)
    @Column(name = "UsuFirmaContrasenia")
    private String usuFirmaContrasenia;
    @Size(max = 2000)
    @Column(name = "UsuPathFirma")
    private String usuPathFirma;
    @Size(max = 2000)
    @Column(name = "UsuFirHuella")
    private String usuFirHuella;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<HistoricoSesion> historicoSesionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<UsuarioCaja> usuarioCajaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<CargaLaboral> cargaLaboralList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<Sesion> sesionList;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;
    @JoinColumn(name = "RolId", referencedColumnName = "RolId")
    @ManyToOne(optional = false)
    private Rol rolId;
    @JoinColumn(name = "ZonId", referencedColumnName = "ZonId")
    @ManyToOne(optional = false)
    private Zona zonId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<TramiteUsuario> tramiteUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<HistoricoContrasenia> historicoContraseniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<FacturaTrabajo> facturaTrabajoList;

    public Usuario() {
    }

    public Usuario(Long usuId) {
        this.usuId = usuId;
    }

    public Usuario(Long usuId, String usuLogin, String usuContrasenia, String usuEmail, String usuEstado, String usuUser, Date usuFHR, Date usuFechaInicio, Date usuFechaFin) {
        this.usuId = usuId;
        this.usuLogin = usuLogin;
        this.usuContrasenia = usuContrasenia;
        this.usuEmail = usuEmail;
        this.usuEstado = usuEstado;
        this.usuUser = usuUser;
        this.usuFHR = usuFHR;
        this.usuFechaInicio = usuFechaInicio;
        this.usuFechaFin = usuFechaFin;

    }

    public Long getUsuId() {
        return usuId;
    }

    public void setUsuId(Long usuId) {
        this.usuId = usuId;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuContrasenia() {
        return usuContrasenia;
    }

    public void setUsuContrasenia(String usuContrasenia) {
        this.usuContrasenia = usuContrasenia;
    }

    public String getUsuEmail() {
        return usuEmail;
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }

    public String getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
    }

    public String getUsuUser() {
        return usuUser;
    }

    public void setUsuUser(String usuUser) {
        this.usuUser = usuUser;
    }

    public Date getUsuFHR() {
        return usuFHR;
    }

    public void setUsuFHR(Date usuFHR) {
        this.usuFHR = usuFHR;
    }

    public Date getUsuFechaInicio() {
        return usuFechaInicio;
    }

    public void setUsuFechaInicio(Date usuFechaInicio) {
        this.usuFechaInicio = usuFechaInicio;
    }

    public Date getUsuFechaFin() {
        return usuFechaFin;
    }

    public void setUsuFechaFin(Date usuFechaFin) {
        this.usuFechaFin = usuFechaFin;
    }

    public String getUsuImagen() {
        return usuImagen;
    }

    public void setUsuImagen(String usuImagen) {
        this.usuImagen = usuImagen;
    }

    public String getUsuFirmaContrasenia() {
        return usuFirmaContrasenia;
    }

    public void setUsuFirmaContrasenia(String usuFirmaContrasenia) {
        this.usuFirmaContrasenia = usuFirmaContrasenia;
    }

    public String getUsuPathFirma() {
        return usuPathFirma;
    }

    public void setUsuPathFirma(String usuPathFirma) {
        this.usuPathFirma = usuPathFirma;
    }

    public String getUsuFirHuella() {
        return usuFirHuella;
    }

    public void setUsuFirHuella(String usuFirHuella) {
        this.usuFirHuella = usuFirHuella;
    }

    @XmlTransient
    public List<HistoricoSesion> getHistoricoSesionList() {
        return historicoSesionList;
    }

    public void setHistoricoSesionList(List<HistoricoSesion> historicoSesionList) {
        this.historicoSesionList = historicoSesionList;
    }

    @XmlTransient
    public List<UsuarioCaja> getUsuarioCajaList() {
        return usuarioCajaList;
    }

    public void setUsuarioCajaList(List<UsuarioCaja> usuarioCajaList) {
        this.usuarioCajaList = usuarioCajaList;
    }

    @XmlTransient
    public List<CargaLaboral> getCargaLaboralList() {
        return cargaLaboralList;
    }

    public void setCargaLaboralList(List<CargaLaboral> cargaLaboralList) {
        this.cargaLaboralList = cargaLaboralList;
    }

    @XmlTransient
    public List<Sesion> getSesionList() {
        return sesionList;
    }

    public void setSesionList(List<Sesion> sesionList) {
        this.sesionList = sesionList;
    }

    public Persona getPerId() {
        return perId;
    }

    public void setPerId(Persona perId) {
        this.perId = perId;
    }

    public Rol getRolId() {
        return rolId;
    }

    public void setRolId(Rol rolId) {
        this.rolId = rolId;
    }

    public Zona getZonId() {
        return zonId;
    }

    public void setZonId(Zona zonId) {
        this.zonId = zonId;
    }

    @XmlTransient
    public List<TramiteUsuario> getTramiteUsuarioList() {
        return tramiteUsuarioList;
    }

    public void setTramiteUsuarioList(List<TramiteUsuario> tramiteUsuarioList) {
        this.tramiteUsuarioList = tramiteUsuarioList;
    }

    @XmlTransient
    public List<HistoricoContrasenia> getHistoricoContraseniaList() {
        return historicoContraseniaList;
    }

    public void setHistoricoContraseniaList(List<HistoricoContrasenia> historicoContraseniaList) {
        this.historicoContraseniaList = historicoContraseniaList;
    }

    @XmlTransient
    public List<FacturaTrabajo> getFacturaTrabajoList() {
        return facturaTrabajoList;
    }

    public void setFacturaTrabajoList(List<FacturaTrabajo> facturaTrabajoList) {
        this.facturaTrabajoList = facturaTrabajoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Usuario[ usuId=" + usuId + " ]";
    }

}
