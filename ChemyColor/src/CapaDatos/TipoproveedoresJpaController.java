/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import CapaDatos.exceptions.NonexistentEntityException;
import CapaDatos.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author gerso
 */
public class TipoproveedoresJpaController implements Serializable {

    public TipoproveedoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoproveedores tipoproveedores) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoproveedores);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoproveedores(tipoproveedores.getCodtipoprov()) != null) {
                throw new PreexistingEntityException("Tipoproveedores " + tipoproveedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipoproveedores tipoproveedores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoproveedores = em.merge(tipoproveedores);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tipoproveedores.getCodtipoprov();
                if (findTipoproveedores(id) == null) {
                    throw new NonexistentEntityException("The tipoproveedores with id " + id + " no longer exists.");
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
            Tipoproveedores tipoproveedores;
            try {
                tipoproveedores = em.getReference(Tipoproveedores.class, id);
                tipoproveedores.getCodtipoprov();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoproveedores with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoproveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipoproveedores> findTipoproveedoresEntities() {
        return findTipoproveedoresEntities(true, -1, -1);
    }

    public List<Tipoproveedores> findTipoproveedoresEntities(int maxResults, int firstResult) {
        return findTipoproveedoresEntities(false, maxResults, firstResult);
    }

    private List<Tipoproveedores> findTipoproveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoproveedores.class));
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

    public Tipoproveedores findTipoproveedores(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoproveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoproveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoproveedores> rt = cq.from(Tipoproveedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
