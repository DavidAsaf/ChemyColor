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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gerso
 */
public class GruposJpaController implements Serializable {

    public GruposJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupos grupos) throws PreexistingEntityException, Exception {
        if (grupos.getArticulosList() == null) {
            grupos.setArticulosList(new ArrayList<Articulos>());
        }
        if (grupos.getLineasList() == null) {
            grupos.setLineasList(new ArrayList<Lineas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Articulos> attachedArticulosList = new ArrayList<Articulos>();
            for (Articulos articulosListArticulosToAttach : grupos.getArticulosList()) {
                articulosListArticulosToAttach = em.getReference(articulosListArticulosToAttach.getClass(), articulosListArticulosToAttach.getCodigoarticulo());
                attachedArticulosList.add(articulosListArticulosToAttach);
            }
            grupos.setArticulosList(attachedArticulosList);
            List<Lineas> attachedLineasList = new ArrayList<Lineas>();
            for (Lineas lineasListLineasToAttach : grupos.getLineasList()) {
                lineasListLineasToAttach = em.getReference(lineasListLineasToAttach.getClass(), lineasListLineasToAttach.getCodigolinea());
                attachedLineasList.add(lineasListLineasToAttach);
            }
            grupos.setLineasList(attachedLineasList);
            em.persist(grupos);
            for (Articulos articulosListArticulos : grupos.getArticulosList()) {
                Grupos oldCodigogrupoOfArticulosListArticulos = articulosListArticulos.getCodigogrupo();
                articulosListArticulos.setCodigogrupo(grupos);
                articulosListArticulos = em.merge(articulosListArticulos);
                if (oldCodigogrupoOfArticulosListArticulos != null) {
                    oldCodigogrupoOfArticulosListArticulos.getArticulosList().remove(articulosListArticulos);
                    oldCodigogrupoOfArticulosListArticulos = em.merge(oldCodigogrupoOfArticulosListArticulos);
                }
            }
            for (Lineas lineasListLineas : grupos.getLineasList()) {
                Grupos oldCodigogrupoOfLineasListLineas = lineasListLineas.getCodigogrupo();
                lineasListLineas.setCodigogrupo(grupos);
                lineasListLineas = em.merge(lineasListLineas);
                if (oldCodigogrupoOfLineasListLineas != null) {
                    oldCodigogrupoOfLineasListLineas.getLineasList().remove(lineasListLineas);
                    oldCodigogrupoOfLineasListLineas = em.merge(oldCodigogrupoOfLineasListLineas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupos(grupos.getCodigogrupo()) != null) {
                throw new PreexistingEntityException("Grupos " + grupos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupos grupos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupos persistentGrupos = em.find(Grupos.class, grupos.getCodigogrupo());
            List<Articulos> articulosListOld = persistentGrupos.getArticulosList();
            List<Articulos> articulosListNew = grupos.getArticulosList();
            List<Lineas> lineasListOld = persistentGrupos.getLineasList();
            List<Lineas> lineasListNew = grupos.getLineasList();
            List<Articulos> attachedArticulosListNew = new ArrayList<Articulos>();
            for (Articulos articulosListNewArticulosToAttach : articulosListNew) {
                articulosListNewArticulosToAttach = em.getReference(articulosListNewArticulosToAttach.getClass(), articulosListNewArticulosToAttach.getCodigoarticulo());
                attachedArticulosListNew.add(articulosListNewArticulosToAttach);
            }
            articulosListNew = attachedArticulosListNew;
            grupos.setArticulosList(articulosListNew);
            List<Lineas> attachedLineasListNew = new ArrayList<Lineas>();
            for (Lineas lineasListNewLineasToAttach : lineasListNew) {
                lineasListNewLineasToAttach = em.getReference(lineasListNewLineasToAttach.getClass(), lineasListNewLineasToAttach.getCodigolinea());
                attachedLineasListNew.add(lineasListNewLineasToAttach);
            }
            lineasListNew = attachedLineasListNew;
            grupos.setLineasList(lineasListNew);
            grupos = em.merge(grupos);
            for (Articulos articulosListOldArticulos : articulosListOld) {
                if (!articulosListNew.contains(articulosListOldArticulos)) {
                    articulosListOldArticulos.setCodigogrupo(null);
                    articulosListOldArticulos = em.merge(articulosListOldArticulos);
                }
            }
            for (Articulos articulosListNewArticulos : articulosListNew) {
                if (!articulosListOld.contains(articulosListNewArticulos)) {
                    Grupos oldCodigogrupoOfArticulosListNewArticulos = articulosListNewArticulos.getCodigogrupo();
                    articulosListNewArticulos.setCodigogrupo(grupos);
                    articulosListNewArticulos = em.merge(articulosListNewArticulos);
                    if (oldCodigogrupoOfArticulosListNewArticulos != null && !oldCodigogrupoOfArticulosListNewArticulos.equals(grupos)) {
                        oldCodigogrupoOfArticulosListNewArticulos.getArticulosList().remove(articulosListNewArticulos);
                        oldCodigogrupoOfArticulosListNewArticulos = em.merge(oldCodigogrupoOfArticulosListNewArticulos);
                    }
                }
            }
            for (Lineas lineasListOldLineas : lineasListOld) {
                if (!lineasListNew.contains(lineasListOldLineas)) {
                    lineasListOldLineas.setCodigogrupo(null);
                    lineasListOldLineas = em.merge(lineasListOldLineas);
                }
            }
            for (Lineas lineasListNewLineas : lineasListNew) {
                if (!lineasListOld.contains(lineasListNewLineas)) {
                    Grupos oldCodigogrupoOfLineasListNewLineas = lineasListNewLineas.getCodigogrupo();
                    lineasListNewLineas.setCodigogrupo(grupos);
                    lineasListNewLineas = em.merge(lineasListNewLineas);
                    if (oldCodigogrupoOfLineasListNewLineas != null && !oldCodigogrupoOfLineasListNewLineas.equals(grupos)) {
                        oldCodigogrupoOfLineasListNewLineas.getLineasList().remove(lineasListNewLineas);
                        oldCodigogrupoOfLineasListNewLineas = em.merge(oldCodigogrupoOfLineasListNewLineas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = grupos.getCodigogrupo();
                if (findGrupos(id) == null) {
                    throw new NonexistentEntityException("The grupos with id " + id + " no longer exists.");
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
            Grupos grupos;
            try {
                grupos = em.getReference(Grupos.class, id);
                grupos.getCodigogrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupos with id " + id + " no longer exists.", enfe);
            }
            List<Articulos> articulosList = grupos.getArticulosList();
            for (Articulos articulosListArticulos : articulosList) {
                articulosListArticulos.setCodigogrupo(null);
                articulosListArticulos = em.merge(articulosListArticulos);
            }
            List<Lineas> lineasList = grupos.getLineasList();
            for (Lineas lineasListLineas : lineasList) {
                lineasListLineas.setCodigogrupo(null);
                lineasListLineas = em.merge(lineasListLineas);
            }
            em.remove(grupos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupos> findGruposEntities() {
        return findGruposEntities(true, -1, -1);
    }

    public List<Grupos> findGruposEntities(int maxResults, int firstResult) {
        return findGruposEntities(false, maxResults, firstResult);
    }

    private List<Grupos> findGruposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupos.class));
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

    public Grupos findGrupos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupos.class, id);
        } finally {
            em.close();
        }
    }

    public int getGruposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupos> rt = cq.from(Grupos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
