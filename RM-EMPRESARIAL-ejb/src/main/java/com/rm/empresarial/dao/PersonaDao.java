/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.modelo.TramiteDetalle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class PersonaDao extends Generico<Persona> implements Serializable {

    private static final long serialVersionUID = -5960533381275816155L;

    public List<Persona> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Persona.LISTAR_TODO, null);
    }

    public Persona encontrarPersonaPorIdentificacion(String identificacion) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("perIdentificacion", identificacion.trim());
        return obtenerPorConsultaJpaNombrada(Persona.ENCONTRAR_PERSONA_POR_IDENTIFICACION, parametros);
    }

    public Persona encontrarPersonaPorCIRUC(String identificacion) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("perIdentificacion", identificacion.trim());
            return obtenerPorConsultaJpaNombrada(Persona.ENCONTRAR_PERSONA_POR_IDENTIFICACION, parametros);
        } catch (ServicioExcepcion e) {
            return null;
        }

    }

    public List<Persona> ListaPaginadaSp(Integer registros, Integer paginaNumero) throws ServicioExcepcion {
        List<Persona> listarPorConsultaNativa = null;
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("registros", registros);
        parametros.put("paginaNumero", paginaNumero);
        listarPorConsultaNativa = listarPorConsultaNativa(Persona.LISTAR_TODO_PAGINADO, parametros);
        return listarPorConsultaNativa;

    }

    public List<Persona> ListarPrimeros1000() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Persona.LISTAR_PRIMEROS_1000, null);
    }

    public Long asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.perId) from Persona a");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }

    public boolean personaDuplicado(String identificacion) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT Count(Persona.PerId) FROM Persona WHERE Persona.PerIdentificacion=?");
            q.setParameter("1", identificacion);
            int existe = (int) q.getSingleResult();

            if (existe > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    public Persona buscarPersona(String identificacion) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", identificacion);
            String sql = "SELECT * FROM Persona WHERE PerIdentificacion= ? ";
            return (Persona) obtenerPorConsultaNativa(sql, parametros, Persona.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public boolean persona(String identificacion) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT * FROM Persona WHERE Persona.PerIdentificacion=?");
            q.setParameter("1", identificacion);
            int existe = (int) q.getSingleResult();

            if (existe > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    public Persona buscarConyugue(Integer id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("perId", id);
        return obtenerPorConsultaJpaNombrada(Persona.BUSCAR_CONYUGUE, parametros);

    }

    public void guardarPersona(Persona persona) throws ServicioExcepcion {

        guardarSalida(persona);

    }

    public Persona guardarPersonaRetorno(Persona persona) throws ServicioExcepcion {
        persona.setPerId(asignarID());
        guardarRetorno(persona);
        Persona salida = find(persona, persona.getPerId());
        return salida;
    }

    public Persona buscarPersonaPorId(Long id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", id);
        return (Persona) obtenerPorConsultaNativa("SELECT * FROM Persona  WHERE PerId = ?", parametros, Persona.class);
    }

    public String buscarPersonaPorUsuario(String usuario) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT CONCAT(RTRIM(Persona.PerNombre),' ', "
                    + "RTRIM(Persona.PerApellidoPaterno),' ', "
                    + "RTRIM(Persona.PerApellidoMaterno)) "
                    + "AS Revisor FROM Persona "
                    + "WHERE Persona.PerId = "
                    + "(SELECT Usuario.PerId FROM Usuario WHERE Usuario.UsuLogin = ?)");
            q.setParameter("1", usuario);
            return (String) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<TramiteDetalle> listarPersonasPorLike(String identificacion, String apellidoPaterno, String apellidoMaterno, String nombre) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", identificacion);
            parametros.put("2", apellidoPaterno);
            parametros.put("3", apellidoMaterno);
            parametros.put("4", nombre);
            String sql = "SELECT * "
                    + "FROM TramiteDetalle "
                    + "WHERE "
                    + "tdtPerIdentificacion LIKE ? OR "
                    + "tdtPerApellidoPaterno LIKE ? OR "
                    + "tdtPerApellidoMaterno LIKE ? OR "
                    + "tdtPerNombre LIKE ? ";
            return listarPorConsultaNativa(sql, parametros, TramiteDetalle.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public List<Propietario> listaPropietarios() {
        String sql = "SELECT * FROM Propietario";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        return q.getResultList();
    }

    public List<Persona> listarPorNom_ApeP_ApeM(String apellidoPaterno, String apellidoMaterno, String nombre) {
        try {
            String query = "SELECT TOP(50) * FROM Persona WHERE ";
            if (!apellidoPaterno.isEmpty() && apellidoMaterno.isEmpty() && nombre.isEmpty()) {
                query += "PerApellidoPaterno LIKE '%" + apellidoPaterno + "%' ";
            } else if (apellidoPaterno.isEmpty() && !apellidoMaterno.isEmpty() && nombre.isEmpty()) {
                query += "PerApellidoMaterno LIKE '%" + apellidoMaterno + "%' ";
            } else if (apellidoPaterno.isEmpty() && apellidoMaterno.isEmpty() && !nombre.isEmpty()) {
                query += "PerNombre LIKE '%" + nombre + "%' ";
            } else if (!apellidoPaterno.isEmpty() && !apellidoMaterno.isEmpty() && nombre.isEmpty()) {
                query += "PerApellidoPaterno LIKE '%" + apellidoPaterno + "%' AND PerApellidoMaterno LIKE '%" + apellidoMaterno + "%' ";
            } else if (!apellidoPaterno.isEmpty() && apellidoMaterno.isEmpty() && !nombre.isEmpty()) {
                query += "PerApellidoPaterno LIKE '%" + apellidoPaterno + "%' AND PerNombre LIKE '%" + nombre + "%' ";
            } else if (apellidoPaterno.isEmpty() && !apellidoMaterno.isEmpty() && !nombre.isEmpty()) {
                query += "PerApellidoMaterno LIKE '%" + apellidoMaterno + "%' AND PerNombre LIKE '%" + nombre + "%' ";
            } else if (!apellidoPaterno.isEmpty() && !apellidoMaterno.isEmpty() && !nombre.isEmpty()) {
                query += "PerApellidoPaterno LIKE '%" + apellidoPaterno + "%' AND PerApellidoMaterno LIKE '%" + apellidoMaterno + "%' AND PerNombre LIKE '%" + nombre + "%' ";
            }
            query += "ORDER BY PerApellidoPaterno ASC";
            return listarPorConsultaNativa(query, null, Persona.class);
        } catch (ServicioExcepcion e) {
            return null;
        }

    }

    public List<Persona> listarPorIdent_Nom_ApeP_ApeM(List<String> params) {
        try {
            String query = "SELECT TOP(100) * FROM Persona WHERE ";
            if (params.size() > 1) {
                for (int i = 0; i < params.size(); i++) {
                    if (i != (params.size() - 1)) {
                        query += params.get(i) + " AND ";
                    } else {
                        query += params.get(i);
                    }
                }
            } else {
                query += params.get(0);
            }
            query += "ORDER BY PerApellidoPaterno ASC";
            return listarPorConsultaNativa(query, null, Persona.class);
        } catch (ServicioExcepcion e) {
            return null;
        }

    }

    public List<Persona> listarPersonasPorLikesPropietario(String identificacion, String apellidoPaterno, String apellidoMaterno, String nombre) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", identificacion);
            parametros.put("2", apellidoPaterno);
            parametros.put("3", apellidoMaterno);
            parametros.put("4", nombre);
            String sql = "SELECT TOP(100) p.PerId,p.PerIdentificacion,p.PerNombre,p.PerApellidoPaterno,p.PerApellidoMaterno "
                    + "FROM Persona p "
                    + "INNER JOIN Propietario pr ON pr.PerId = p.PerId "
                    + "WHERE "
                    + "(p.PerIdentificacion LIKE ? OR "
                    + "p.PerApellidoPaterno LIKE ? OR "
                    + "p.PerApellidoMaterno LIKE ? OR "
                    + "p.PerNombre LIKE ? ) AND "
                    + "pr.PprEstado = 'A' "
                    + "GROUP BY p.PerId,p.PerIdentificacion,p.PerNombre,p.PerApellidoPaterno,p.PerApellidoMaterno";
            return listarPorConsultaNativa(sql, parametros, Persona.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public List<Persona> listarPersonasPorLikesGraDetalle(String identificacion, String apellidoPaterno, String apellidoMaterno, String nombre) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", identificacion);
            parametros.put("2", apellidoPaterno);
            parametros.put("3", apellidoMaterno);
            parametros.put("4", nombre);
            String sql = "SELECT TOP(100) p.PerId,p.PerIdentificacion,p.PerNombre,p.PerApellidoPaterno,p.PerApellidoMaterno "
                    + "FROM Persona p "
                    + "INNER JOIN GravamenDetalle gr ON gr.PerId = p.PerId "
                    + "WHERE "
                    + "p.PerIdentificacion LIKE ? OR "
                    + "p.PerApellidoPaterno LIKE ? OR "
                    + "p.PerApellidoMaterno LIKE ? OR "
                    + "p.PerNombre LIKE ? "
                    + "GROUP BY p.PerId,p.PerIdentificacion,p.PerNombre,p.PerApellidoPaterno,p.PerApellidoMaterno";
            return listarPorConsultaNativa(sql, parametros, Persona.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public List<Persona> listarPersonas() {
        try {
//            Map<String, Object> parametros = new HashMap<>();
//            parametros.put("1", identificacion);
            String sql = "SELECT * FROM Persona,TipoTramite\n"
                    + "INNER JOIN TramiteDetalle ON TramiteDetalle.TdtTtrId = TipoTramite.TtrId \n"
                    + "INNER JOIN TipoCompareciente on TramiteDetalle.TdtTpcId = TipoCompareciente.TpcId\n"
                    + "WHERE Persona.PerId = TramiteDetalle.PerId \n"
                    + "AND TipoTramite.TrCodigoAgrupacion1='UAFE' \n"
                    + "AND TramiteDetalle.TdtNumeroRepertorio<>0\n"
                    + "ORDER BY TramiteDetalle.TdtNumeroRepertorio ";
            return listarPorConsultaNativa(sql, null, Persona.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public List<Persona> listarPersonasPropietarias(String numMatricula) {
        String sql = "SELECT * FROM Persona p "
                + "INNER JOIN Propietario pr ON pr.PerId = p.PerId "
                + "WHERE pr.PrdMatricula = ? AND pr.PprEstado = 'A'";
        Query q = getEntityManager().createNativeQuery(sql, Persona.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }
}
