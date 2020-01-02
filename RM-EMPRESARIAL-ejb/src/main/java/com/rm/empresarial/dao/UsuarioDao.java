/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class UsuarioDao extends Generico<Usuario> implements Serializable {

    private static final long serialVersionUID = 1850976528415530130L;

    public List<Usuario> listarTodo() {
        try {
            return listarPorConsultaJpaNombrada(Usuario.LISTAR_TODO, null);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public List<Usuario> listarTodoSinWeb() {
        String sql = "SELECT * FROM Usuario u "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE r.RolNombre <> 'WEB'";
        Query q = getEntityManager().createNativeQuery(sql, Usuario.class);
        return q.getResultList();
       
    }

    public Usuario listarPorId(Long id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", id);
        return obtenerPorConsultaJpaNombrada(Usuario.ENCONTRAR_POR_USUARIO_ID, parametros);
    }

    public Usuario encontrarUsuarioPorNombreContrasenia(String login, String contrasenia) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", login);
        parametros.put("2", contrasenia);
        return (Usuario) obtenerPorConsultaNativa("SELECT Usuario.* FROM Usuario "
                + "WHERE Usuario.UsuLogin = ? "
                + "AND Usuario.UsuContrasenia = ? "
                + "AND (Usuario.UsuEstado = 'A' OR Usuario.UsuEstado = 'R' OR Usuario.UsuEstado = 'C')", parametros, Usuario.class);
    }

    public Usuario encontrarUsuarioPorNombreUsuario(String usuario) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuLogin", usuario);
            return obtenerPorConsultaJpaNombrada(Usuario.ENCONTRAR_USUARIO_POR_NOMBRE, parametros);
        } catch (Exception e) {
            return null;
        }

    }

    public Usuario buscarUsuarioPorRol(Long id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", id);
        Object object = null;
        Usuario usuario = null;

        usuario = (Usuario) obtenerPorConsultaNativa(Usuario.BUSCAR_USUARIO_POR_ROL, parametros, Usuario.class);
//        System.out.println("usuario nombre: "+usuario.getUsuLogin());
        return usuario;
    }

    public List<Usuario> listarUsuariosPorRol(Long id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", id);
        Usuario usuario = null;
        return listarPorConsultaNativa(Usuario.LISTAR_USUARIOS_POR_ROL, parametros, Usuario.class);
    }

    public Usuario buscarUsuarioPorId(String usuId) throws ServicioExcepcion {
        Long id = new Long(usuId);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", id);
        return obtenerPorConsultaJpaNombrada(Usuario.ENCONTRAR_POR_USUARIO_ID, parametros);
    }

    public Usuario encontrarUsuarioPorNombreEmail(String login, String email) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuLogin", login);
        parametros.put("usuEmail", email);
        return obtenerPorConsultaJpaNombrada(Usuario.BUSCAR_USUARIO_LOGIN_EMAIL, parametros);
    }

    public boolean validarLoginCrear(String usuLogin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuLogin", usuLogin);
        return listarPorConsultaJpaNombrada(Usuario.BUSCAR_POR_LOGIN, parametros).isEmpty();
    }

    public boolean validarEmailCrear(String usuEmail) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuEmail", usuEmail);
        return listarPorConsultaJpaNombrada(Usuario.BUSCAR_POR_EMAIL, parametros).isEmpty();
    }

    public boolean validarPerIdCrear(Persona perId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("perId", perId);
        return listarPorConsultaJpaNombrada(Usuario.BUSCAR_POR_PERID, parametros).isEmpty();
    }

    public boolean validarLoginEditar(Long usuId, String usuLogin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", usuId);
        parametros.put("usuLogin", usuLogin);
        return listarPorConsultaJpaNombrada(Usuario.BUSCAR_POR_LOGIN_EDITAR, parametros).isEmpty();
    }

    public boolean validarEmailEditar(Long usuId, String usuEmail) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", usuId);
        parametros.put("usuEmail", usuEmail);
        return listarPorConsultaJpaNombrada(Usuario.BUSCAR_POR_EMAIL_EDITAR, parametros).isEmpty();
    }

    public boolean validarPerIdEditar(Long usuId, Persona perId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", usuId);
        parametros.put("perId", perId);
        return listarPorConsultaJpaNombrada(Usuario.BUSCAR_POR_PERID_EDITAR, parametros).isEmpty();
    }

    public List<Usuario> listarUsuarioRolRvt(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<Usuario> lista = listarPorConsultaNativa("SELECT Usuario.* FROM Usuario where RolId='2' AND UsuId <> ? ",
                parametros, Usuario.class);
        return lista;
    }

    public List<Usuario> listarUsuarioRolINS(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<Usuario> lista = listarPorConsultaNativa("SELECT Usuario.* FROM Usuario where RolId=57 AND UsuId <> ? ",
                parametros, Usuario.class);
        return lista;
    }
    
    public List<Usuario> listarUsuarioRolMAT(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<Usuario> lista = listarPorConsultaNativa("SELECT Usuario.* FROM Usuario where RolId=63 AND UsuId <> ? ",
                parametros, Usuario.class);
        return lista;
    }

    public List<Usuario> listarUsuarioRolIMP(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<Usuario> lista = listarPorConsultaNativa("SELECT Usuario.* FROM Usuario where RolId=67 AND UsuId <> ? ",
                parametros, Usuario.class);
        return lista;
    }

    public List<Usuario> listarUsuarioRolCER(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<Usuario> lista = listarPorConsultaNativa("SELECT Usuario.* FROM Usuario where RolId=69 AND UsuId <> ? ",
                parametros, Usuario.class);
        return lista;
    }

    public List<Usuario> listarUsuarioRolMRG(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<Usuario> lista = listarPorConsultaNativa("SELECT Usuario.* FROM Usuario where RolId=6 AND UsuId <> ? ",
                parametros, Usuario.class);
        return lista;
    }

    public List<Usuario> listarUsuarioRolDIG(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<Usuario> lista = listarPorConsultaNativa("SELECT Usuario.* FROM Usuario where RolId=4 AND UsuId <> ? ",
                parametros, Usuario.class);
        return lista;
    }

    public Persona buscarPersona(String login) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", login);
            String sql = "SELECT * FROM Usuario INNER JOIN Persona ON Usuario.PerId = Persona.PerId WHERE Usuario.UsuLogin = ?";
            return (Persona) obtenerPorConsultaNativa(sql, parametros, Persona.class);
        } catch (Exception e) {
            return null;
        }
    }

}
