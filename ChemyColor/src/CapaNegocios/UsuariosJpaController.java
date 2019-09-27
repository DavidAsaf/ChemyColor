/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import CapaDatos.Tipousuarios;
import CapaDatos.Usuarios;
import CapaNegocios.exceptions.NonexistentEntityException;
import CapaNegocios.exceptions.PreexistingEntityException;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mario
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuarios codtipousuario = usuarios.getCodtipousuario();
            if (codtipousuario != null) {
                codtipousuario = em.getReference(codtipousuario.getClass(), codtipousuario.getCodtipousuario());
                usuarios.setCodtipousuario(codtipousuario);
            }
            em.persist(usuarios);
            if (codtipousuario != null) {
                codtipousuario.getUsuariosList().add(usuarios);
                codtipousuario = em.merge(codtipousuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getCodigousuario()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getCodigousuario());
            Tipousuarios codtipousuarioOld = persistentUsuarios.getCodtipousuario();
            Tipousuarios codtipousuarioNew = usuarios.getCodtipousuario();
            if (codtipousuarioNew != null) {
                codtipousuarioNew = em.getReference(codtipousuarioNew.getClass(), codtipousuarioNew.getCodtipousuario());
                usuarios.setCodtipousuario(codtipousuarioNew);
            }
            usuarios = em.merge(usuarios);
            if (codtipousuarioOld != null && !codtipousuarioOld.equals(codtipousuarioNew)) {
                codtipousuarioOld.getUsuariosList().remove(usuarios);
                codtipousuarioOld = em.merge(codtipousuarioOld);
            }
            if (codtipousuarioNew != null && !codtipousuarioNew.equals(codtipousuarioOld)) {
                codtipousuarioNew.getUsuariosList().add(usuarios);
                codtipousuarioNew = em.merge(codtipousuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = usuarios.getCodigousuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getCodigousuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Tipousuarios codtipousuario = usuarios.getCodtipousuario();
            if (codtipousuario != null) {
                codtipousuario.getUsuariosList().remove(usuarios);
                codtipousuario = em.merge(codtipousuario);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuarios findUsuarios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
