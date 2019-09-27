/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import CapaDatos.Proveedores;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import CapaDatos.Tipocontribuyente;
import CapaDatos.Tipoproveedores;
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
public class ProveedoresJpaController implements Serializable {

    public ProveedoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedores proveedores) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipocontribuyente codigotipocontribuyente = proveedores.getCodigotipocontribuyente();
            if (codigotipocontribuyente != null) {
                codigotipocontribuyente = em.getReference(codigotipocontribuyente.getClass(), codigotipocontribuyente.getCodigotipocontribuyente());
                proveedores.setCodigotipocontribuyente(codigotipocontribuyente);
            }
            Tipoproveedores codtipoprov = proveedores.getCodtipoprov();
            if (codtipoprov != null) {
                codtipoprov = em.getReference(codtipoprov.getClass(), codtipoprov.getCodtipoprov());
                proveedores.setCodtipoprov(codtipoprov);
            }
            em.persist(proveedores);
            if (codigotipocontribuyente != null) {
                codigotipocontribuyente.getProveedoresList().add(proveedores);
                codigotipocontribuyente = em.merge(codigotipocontribuyente);
            }
            if (codtipoprov != null) {
                codtipoprov.getProveedoresList().add(proveedores);
                codtipoprov = em.merge(codtipoprov);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedores(proveedores.getCodigoproveedor()) != null) {
                throw new PreexistingEntityException("Proveedores " + proveedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedores proveedores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores persistentProveedores = em.find(Proveedores.class, proveedores.getCodigoproveedor());
            Tipocontribuyente codigotipocontribuyenteOld = persistentProveedores.getCodigotipocontribuyente();
            Tipocontribuyente codigotipocontribuyenteNew = proveedores.getCodigotipocontribuyente();
            Tipoproveedores codtipoprovOld = persistentProveedores.getCodtipoprov();
            Tipoproveedores codtipoprovNew = proveedores.getCodtipoprov();
            if (codigotipocontribuyenteNew != null) {
                codigotipocontribuyenteNew = em.getReference(codigotipocontribuyenteNew.getClass(), codigotipocontribuyenteNew.getCodigotipocontribuyente());
                proveedores.setCodigotipocontribuyente(codigotipocontribuyenteNew);
            }
            if (codtipoprovNew != null) {
                codtipoprovNew = em.getReference(codtipoprovNew.getClass(), codtipoprovNew.getCodtipoprov());
                proveedores.setCodtipoprov(codtipoprovNew);
            }
            proveedores = em.merge(proveedores);
            if (codigotipocontribuyenteOld != null && !codigotipocontribuyenteOld.equals(codigotipocontribuyenteNew)) {
                codigotipocontribuyenteOld.getProveedoresList().remove(proveedores);
                codigotipocontribuyenteOld = em.merge(codigotipocontribuyenteOld);
            }
            if (codigotipocontribuyenteNew != null && !codigotipocontribuyenteNew.equals(codigotipocontribuyenteOld)) {
                codigotipocontribuyenteNew.getProveedoresList().add(proveedores);
                codigotipocontribuyenteNew = em.merge(codigotipocontribuyenteNew);
            }
            if (codtipoprovOld != null && !codtipoprovOld.equals(codtipoprovNew)) {
                codtipoprovOld.getProveedoresList().remove(proveedores);
                codtipoprovOld = em.merge(codtipoprovOld);
            }
            if (codtipoprovNew != null && !codtipoprovNew.equals(codtipoprovOld)) {
                codtipoprovNew.getProveedoresList().add(proveedores);
                codtipoprovNew = em.merge(codtipoprovNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = proveedores.getCodigoproveedor();
                if (findProveedores(id) == null) {
                    throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.");
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
            Proveedores proveedores;
            try {
                proveedores = em.getReference(Proveedores.class, id);
                proveedores.getCodigoproveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.", enfe);
            }
            Tipocontribuyente codigotipocontribuyente = proveedores.getCodigotipocontribuyente();
            if (codigotipocontribuyente != null) {
                codigotipocontribuyente.getProveedoresList().remove(proveedores);
                codigotipocontribuyente = em.merge(codigotipocontribuyente);
            }
            Tipoproveedores codtipoprov = proveedores.getCodtipoprov();
            if (codtipoprov != null) {
                codtipoprov.getProveedoresList().remove(proveedores);
                codtipoprov = em.merge(codtipoprov);
            }
            em.remove(proveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedores> findProveedoresEntities() {
        return findProveedoresEntities(true, -1, -1);
    }

    public List<Proveedores> findProveedoresEntities(int maxResults, int firstResult) {
        return findProveedoresEntities(false, maxResults, firstResult);
    }

    private List<Proveedores> findProveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedores.class));
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

    public Proveedores findProveedores(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedores> rt = cq.from(Proveedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
