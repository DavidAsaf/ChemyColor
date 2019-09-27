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
import CapaDatos.Proveedores;
import CapaDatos.Tipoproveedores;
import CapaNegocios.exceptions.NonexistentEntityException;
import CapaNegocios.exceptions.PreexistingEntityException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mario
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
        if (tipoproveedores.getProveedoresList() == null) {
            tipoproveedores.setProveedoresList(new ArrayList<Proveedores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proveedores> attachedProveedoresList = new ArrayList<Proveedores>();
            for (Proveedores proveedoresListProveedoresToAttach : tipoproveedores.getProveedoresList()) {
                proveedoresListProveedoresToAttach = em.getReference(proveedoresListProveedoresToAttach.getClass(), proveedoresListProveedoresToAttach.getCodigoproveedor());
                attachedProveedoresList.add(proveedoresListProveedoresToAttach);
            }
            tipoproveedores.setProveedoresList(attachedProveedoresList);
            em.persist(tipoproveedores);
            for (Proveedores proveedoresListProveedores : tipoproveedores.getProveedoresList()) {
                Tipoproveedores oldCodtipoprovOfProveedoresListProveedores = proveedoresListProveedores.getCodtipoprov();
                proveedoresListProveedores.setCodtipoprov(tipoproveedores);
                proveedoresListProveedores = em.merge(proveedoresListProveedores);
                if (oldCodtipoprovOfProveedoresListProveedores != null) {
                    oldCodtipoprovOfProveedoresListProveedores.getProveedoresList().remove(proveedoresListProveedores);
                    oldCodtipoprovOfProveedoresListProveedores = em.merge(oldCodtipoprovOfProveedoresListProveedores);
                }
            }
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
            Tipoproveedores persistentTipoproveedores = em.find(Tipoproveedores.class, tipoproveedores.getCodtipoprov());
            List<Proveedores> proveedoresListOld = persistentTipoproveedores.getProveedoresList();
            List<Proveedores> proveedoresListNew = tipoproveedores.getProveedoresList();
            List<Proveedores> attachedProveedoresListNew = new ArrayList<Proveedores>();
            for (Proveedores proveedoresListNewProveedoresToAttach : proveedoresListNew) {
                proveedoresListNewProveedoresToAttach = em.getReference(proveedoresListNewProveedoresToAttach.getClass(), proveedoresListNewProveedoresToAttach.getCodigoproveedor());
                attachedProveedoresListNew.add(proveedoresListNewProveedoresToAttach);
            }
            proveedoresListNew = attachedProveedoresListNew;
            tipoproveedores.setProveedoresList(proveedoresListNew);
            tipoproveedores = em.merge(tipoproveedores);
            for (Proveedores proveedoresListOldProveedores : proveedoresListOld) {
                if (!proveedoresListNew.contains(proveedoresListOldProveedores)) {
                    proveedoresListOldProveedores.setCodtipoprov(null);
                    proveedoresListOldProveedores = em.merge(proveedoresListOldProveedores);
                }
            }
            for (Proveedores proveedoresListNewProveedores : proveedoresListNew) {
                if (!proveedoresListOld.contains(proveedoresListNewProveedores)) {
                    Tipoproveedores oldCodtipoprovOfProveedoresListNewProveedores = proveedoresListNewProveedores.getCodtipoprov();
                    proveedoresListNewProveedores.setCodtipoprov(tipoproveedores);
                    proveedoresListNewProveedores = em.merge(proveedoresListNewProveedores);
                    if (oldCodtipoprovOfProveedoresListNewProveedores != null && !oldCodtipoprovOfProveedoresListNewProveedores.equals(tipoproveedores)) {
                        oldCodtipoprovOfProveedoresListNewProveedores.getProveedoresList().remove(proveedoresListNewProveedores);
                        oldCodtipoprovOfProveedoresListNewProveedores = em.merge(oldCodtipoprovOfProveedoresListNewProveedores);
                    }
                }
            }
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
            List<Proveedores> proveedoresList = tipoproveedores.getProveedoresList();
            for (Proveedores proveedoresListProveedores : proveedoresList) {
                proveedoresListProveedores.setCodtipoprov(null);
                proveedoresListProveedores = em.merge(proveedoresListProveedores);
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
