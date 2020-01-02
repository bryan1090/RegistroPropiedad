/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.generico;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.Getter;

/**
 *
 * @author WHerrera
 */
public class Generico<T> {

    @PersistenceContext(unitName = "RMM-EMPRESARIAL-PU")
    @Getter
    private EntityManager entityManager;

//     private Class<T> entityClass;
//     public Generico(Class<T> entityClass)
//     {
//         this.entityClass=entityClass;
//     }
    public void guardar(final T t) throws ServicioExcepcion {
        try {
            if (!constraintValidationsDetected(t)) {
                getEntityManager().merge(t);
            }
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }
    }

    public void guardarSalida(final T t) throws ServicioExcepcion {

        try {
            if (!constraintValidationsDetected(t)) {
                getEntityManager().persist(t);
            }

        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }
    }

    public void guardarLote(final List<T> lista) throws ServicioExcepcion {
        try {
            for (T t : lista) {
                getEntityManager().merge(t);
            }
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }
    }

    public List<T> listarPorConsultaJpaNombrada(final String jpaQl,
            final Map<String, Object> parametros) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createNamedQuery(jpaQl);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());

                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    public T obtenerPorConsultaJpaNombrada(final String jpaQl,
            final Map<String, Object> parametros) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createNamedQuery(jpaQl);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());
                }
            }
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    public List<T> listarPorConsultaJpa(final String jpaQl,
            final Map<String, Object> parametros) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createQuery(jpaQl);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    public T obtenerPorConsultaJpa(final String jpaQl,
            final Map<String, Object> parametros) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createQuery(jpaQl);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());
                }
            }
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    public List<T> listarPorConsultaNativa(final String sql,
            final Map<String, Object> parametros) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createNativeQuery(sql);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    public T obtenerPorConsultaNativa(final String sql,
            final Map<String, Object> parametros) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createNativeQuery(sql);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());
                }
            }
            return (T) query.getSingleResult();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    public List listarPorConsultaNativa(final String sql,
            final Map<String, Object> parametros,
            final Class clase) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createNativeQuery(sql, clase);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());
                }
            }
            return query.getResultList();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    public Object obtenerPorConsultaNativa(final String sql,
            final Map<String, Object> parametros,
            final Class clase) throws ServicioExcepcion {
        try {
            Query query = getEntityManager().createNativeQuery(sql, clase);
            if (parametros != null) {
                for (Map.Entry en : parametros.entrySet()) {
                    query.setParameter(en.getKey().toString(), en.getValue());
                }
            }
            return query.getSingleResult();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }

    }

    //Metodos agregados de clase abstractFacade
    public void create(T entity) {
        try {
            if (!constraintValidationsDetected(entity)) {
                getEntityManager().persist(entity);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
//        if (constraintViolations.size() > 0) {
//            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
//            while (iterator.hasNext()) {
//                ConstraintViolation<T> cv = iterator.next();
//                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
//
//                // JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName()+"."+cv.getPropertyPath() + " " +cv.getMessage());
//            }
//        } else {
//            getEntityManager().persist(entity);
//        }
    }

    public void edit(T entity) {
        if (!constraintValidationsDetected(entity)) {
            getEntityManager().merge(entity);

        }

//        try {
//            getEntityManager().merge(entity);
//        } catch (ConstraintViolationException e) {
//            System.out.println("Error al persistir(merge)");
//            for (Object object : e.getConstraintViolations()) {
//                System.out.println("Violación de constraints");
//                System.out.println("" + object.toString());
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }

    private boolean constraintValidationsDetected(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
//                JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validación de campos en la base de Datos", ""));

//            JsfUtil.addErrorMessage();
            return true;
        } else {
            return false;
        }
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(T entity, Object id) {
        return (T) getEntityManager().find(entity.getClass(), id);
    }
//
//    public List<T> findAll() {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        return getEntityManager().createQuery(cq).getResultList();
//    }
//

    public List<T> findRange(T entity, int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entity.getClass()));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//
//    public int count() {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        return ((Long) q.getSingleResult()).intValue();
//    }
//   

    public T guardarRetorno(T t) throws ServicioExcepcion {
        try {
            if (!constraintValidationsDetected(t)) {
                T tipo = getEntityManager().merge(t);
                getEntityManager().flush();
                return tipo;
            }
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }
        return null;
    }
}
