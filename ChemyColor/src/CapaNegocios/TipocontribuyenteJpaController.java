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
import CapaDatos.Tipocontribuyente;
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
public class TipocontribuyenteJpaController implements Serializable {

    public TipocontribuyenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipocontribuyente tipocontribuyente) throws PreexistingEntityException, Exception {
        if (tipocontribuyente.getProveedoresList() == null) {
            tipocontribuyente.setProveedoresList(new ArrayList<Proveedores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proveedores> attachedProveedoresList = new ArrayList<Proveedores>();
            for (Proveedores proveedoresListProveedoresToAttach : tipocontribuyente.getProveedoresList()) {
                proveedoresListProveedoresToAttach = em.getReference(proveedoresListProveedoresToAttach.getClass(), proveedoresListProveedoresToAttach.getCodigoproveedor());
                attachedProveedoresList.add(proveedoresListProveedoresToAttach);
            }
            tipocontribuyente.setProveedoresList(attachedProveedoresList);
            em.persist(tipocontribuyente);
            for (Proveedores proveedoresListProveedores : tipocontribuyente.getProveedoresList()) {
                Tipocontribuyente oldCodigotipocontribuyenteOfProveedoresListProveedores = proveedoresListProveedores.getCodigotipocontribuyente();
                proveedoresListProveedores.setCodigotipocontribuyente(tipocontribuyente);
                proveedoresListProveedores = em.merge(proveedoresListProveedores);
                if (oldCodigotipocontribuyenteOfProveedoresListProveedores != null) {
                    oldCodigotipocontribuyenteOfProveedoresListProveedores.getProveedoresList().remove(proveedoresListProveedores);
                    oldCodigotipocontribuyenteOfProveedoresListProveedores = em.merge(oldCodigotipocontribuyenteOfProveedoresListProveedores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipocontribuyente(tipocontribuyente.getCodigotipocontribuyente()) != null) {
                throw new PreexistingEntityException("Tipocontribuyente " + tipocontribuyente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipocontribuyente tipocontribuyente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipocontribuyente persistentTipocontribuyente = em.find(Tipocontribuyente.class, tipocontribuyente.getCodigotipocontribuyente());
            List<Proveedores> proveedoresListOld = persistentTipocontribuyente.getProveedoresList();
            List<Proveedores> proveedoresListNew = tipocontribuyente.getProveedoresList();
            List<Proveedores> attachedProveedoresListNew = new ArrayList<Proveedores>();
            for (Proveedores proveedoresListNewProveedoresToAttach : proveedoresListNew) {
                proveedoresListNewProveedoresToAttach = em.getReference(proveedoresListNewProveedoresToAttach.getClass(), proveedoresListNewProveedoresToAttach.getCodigoproveedor());
                attachedProveedoresListNew.add(proveedoresListNewProveedoresToAttach);
            }
            proveedoresListNew = attachedProveedoresListNew;
            tipocontribuyente.setProveedoresList(proveedoresListNew);
            tipocontribuyente = em.merge(tipocontribuyente);
            for (Proveedores proveedoresListOldProveedores : proveedoresListOld) {
                if (!proveedoresListNew.contains(proveedoresListOldProveedores)) {
                    proveedoresListOldProveedores.setCodigotipocontribuyente(null);
                    proveedoresListOldProveedores = em.merge(proveedoresListOldProveedores);
                }
            }
            for (Proveedores proveedoresListNewProveedores : proveedoresListNew) {
                if (!proveedoresListOld.contains(proveedoresListNewProveedores)) {
                    Tipocontribuyente oldCodigotipocontribuyenteOfProveedoresListNewProveedores = proveedoresListNewProveedores.getCodigotipocontribuyente();
                    proveedoresListNewProveedores.setCodigotipocontribuyente(tipocontribuyente);
                    proveedoresListNewProveedores = em.merge(proveedoresListNewProveedores);
                    if (oldCodigotipocontribuyenteOfProveedoresListNewProveedores != null && !oldCodigotipocontribuyenteOfProveedoresListNewProveedores.equals(tipocontribuyente)) {
                        oldCodigotipocontribuyenteOfProveedoresListNewProveedores.getProveedoresList().remove(proveedoresListNewProveedores);
                        oldCodigotipocontribuyenteOfProveedoresListNewProveedores = em.merge(oldCodigotipocontribuyenteOfProveedoresListNewProveedores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tipocontribuyente.getCodigotipocontribuyente();
                if (findTipocontribuyente(id) == null) {
                    throw new NonexistentEntityException("The tipocontribuyente with id " + id + " no longer exists.");
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
            Tipocontribuyente tipocontribuyente;
            try {
                tipocontribuyente = em.getReference(Tipocontribuyente.class, id);
                tipocontribuyente.getCodigotipocontribuyente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipocontribuyente with id " + id + " no longer exists.", enfe);
            }
            List<Proveedores> proveedoresList = tipocontribuyente.getProveedoresList();
            for (Proveedores proveedoresListProveedores : proveedoresList) {
                proveedoresListProveedores.setCodigotipocontribuyente(null);
                proveedoresListProveedores = em.merge(proveedoresListProveedores);
            }
            em.remove(tipocontribuyente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipocontribuyente> findTipocontribuyenteEntities() {
        return findTipocontribuyenteEntities(true, -1, -1);
    }

    public List<Tipocontribuyente> findTipocontribuyenteEntities(int maxResults, int firstResult) {
        return findTipocontribuyenteEntities(false, maxResults, firstResult);
    }

    private List<Tipocontribuyente> findTipocontribuyenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipocontribuyente.class));
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

    public Tipocontribuyente findTipocontribuyente(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipocontribuyente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipocontribuyenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipocontribuyente> rt = cq.from(Tipocontribuyente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
