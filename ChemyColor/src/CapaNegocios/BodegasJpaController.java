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
import CapaDatos.Articulos;
import CapaDatos.Bodegas;
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
public class BodegasJpaController implements Serializable {

    public BodegasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bodegas bodegas) throws PreexistingEntityException, Exception {
        if (bodegas.getArticulosList() == null) {
            bodegas.setArticulosList(new ArrayList<Articulos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Articulos> attachedArticulosList = new ArrayList<Articulos>();
            for (Articulos articulosListArticulosToAttach : bodegas.getArticulosList()) {
                articulosListArticulosToAttach = em.getReference(articulosListArticulosToAttach.getClass(), articulosListArticulosToAttach.getCodigoarticulo());
                attachedArticulosList.add(articulosListArticulosToAttach);
            }
            bodegas.setArticulosList(attachedArticulosList);
            em.persist(bodegas);
            for (Articulos articulosListArticulos : bodegas.getArticulosList()) {
                Bodegas oldCodigobodegaOfArticulosListArticulos = articulosListArticulos.getCodigobodega();
                articulosListArticulos.setCodigobodega(bodegas);
                articulosListArticulos = em.merge(articulosListArticulos);
                if (oldCodigobodegaOfArticulosListArticulos != null) {
                    oldCodigobodegaOfArticulosListArticulos.getArticulosList().remove(articulosListArticulos);
                    oldCodigobodegaOfArticulosListArticulos = em.merge(oldCodigobodegaOfArticulosListArticulos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBodegas(bodegas.getCodigobodega()) != null) {
                throw new PreexistingEntityException("Bodegas " + bodegas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bodegas bodegas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bodegas persistentBodegas = em.find(Bodegas.class, bodegas.getCodigobodega());
            List<Articulos> articulosListOld = persistentBodegas.getArticulosList();
            List<Articulos> articulosListNew = bodegas.getArticulosList();
            List<Articulos> attachedArticulosListNew = new ArrayList<Articulos>();
            for (Articulos articulosListNewArticulosToAttach : articulosListNew) {
                articulosListNewArticulosToAttach = em.getReference(articulosListNewArticulosToAttach.getClass(), articulosListNewArticulosToAttach.getCodigoarticulo());
                attachedArticulosListNew.add(articulosListNewArticulosToAttach);
            }
            articulosListNew = attachedArticulosListNew;
            bodegas.setArticulosList(articulosListNew);
            bodegas = em.merge(bodegas);
            for (Articulos articulosListOldArticulos : articulosListOld) {
                if (!articulosListNew.contains(articulosListOldArticulos)) {
                    articulosListOldArticulos.setCodigobodega(null);
                    articulosListOldArticulos = em.merge(articulosListOldArticulos);
                }
            }
            for (Articulos articulosListNewArticulos : articulosListNew) {
                if (!articulosListOld.contains(articulosListNewArticulos)) {
                    Bodegas oldCodigobodegaOfArticulosListNewArticulos = articulosListNewArticulos.getCodigobodega();
                    articulosListNewArticulos.setCodigobodega(bodegas);
                    articulosListNewArticulos = em.merge(articulosListNewArticulos);
                    if (oldCodigobodegaOfArticulosListNewArticulos != null && !oldCodigobodegaOfArticulosListNewArticulos.equals(bodegas)) {
                        oldCodigobodegaOfArticulosListNewArticulos.getArticulosList().remove(articulosListNewArticulos);
                        oldCodigobodegaOfArticulosListNewArticulos = em.merge(oldCodigobodegaOfArticulosListNewArticulos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = bodegas.getCodigobodega();
                if (findBodegas(id) == null) {
                    throw new NonexistentEntityException("The bodegas with id " + id + " no longer exists.");
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
            Bodegas bodegas;
            try {
                bodegas = em.getReference(Bodegas.class, id);
                bodegas.getCodigobodega();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bodegas with id " + id + " no longer exists.", enfe);
            }
            List<Articulos> articulosList = bodegas.getArticulosList();
            for (Articulos articulosListArticulos : articulosList) {
                articulosListArticulos.setCodigobodega(null);
                articulosListArticulos = em.merge(articulosListArticulos);
            }
            em.remove(bodegas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bodegas> findBodegasEntities() {
        return findBodegasEntities(true, -1, -1);
    }

    public List<Bodegas> findBodegasEntities(int maxResults, int firstResult) {
        return findBodegasEntities(false, maxResults, firstResult);
    }

    private List<Bodegas> findBodegasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bodegas.class));
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

    public Bodegas findBodegas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bodegas.class, id);
        } finally {
            em.close();
        }
    }

    public int getBodegasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodegas> rt = cq.from(Bodegas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
