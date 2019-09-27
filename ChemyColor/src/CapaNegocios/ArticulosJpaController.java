/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import CapaDatos.Articulos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import CapaDatos.Bodegas;
import CapaDatos.Grupos;
import CapaDatos.Lineas;
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
public class ArticulosJpaController implements Serializable {

    public ArticulosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulos articulos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bodegas codigobodega = articulos.getCodigobodega();
            if (codigobodega != null) {
                codigobodega = em.getReference(codigobodega.getClass(), codigobodega.getCodigobodega());
                articulos.setCodigobodega(codigobodega);
            }
            Grupos codigogrupo = articulos.getCodigogrupo();
            if (codigogrupo != null) {
                codigogrupo = em.getReference(codigogrupo.getClass(), codigogrupo.getCodigogrupo());
                articulos.setCodigogrupo(codigogrupo);
            }
            Lineas codigolinea = articulos.getCodigolinea();
            if (codigolinea != null) {
                codigolinea = em.getReference(codigolinea.getClass(), codigolinea.getCodigolinea());
                articulos.setCodigolinea(codigolinea);
            }
            em.persist(articulos);
            if (codigobodega != null) {
                codigobodega.getArticulosList().add(articulos);
                codigobodega = em.merge(codigobodega);
            }
            if (codigogrupo != null) {
                codigogrupo.getArticulosList().add(articulos);
                codigogrupo = em.merge(codigogrupo);
            }
            if (codigolinea != null) {
                codigolinea.getArticulosList().add(articulos);
                codigolinea = em.merge(codigolinea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArticulos(articulos.getCodigoarticulo()) != null) {
                throw new PreexistingEntityException("Articulos " + articulos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulos articulos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos persistentArticulos = em.find(Articulos.class, articulos.getCodigoarticulo());
            Bodegas codigobodegaOld = persistentArticulos.getCodigobodega();
            Bodegas codigobodegaNew = articulos.getCodigobodega();
            Grupos codigogrupoOld = persistentArticulos.getCodigogrupo();
            Grupos codigogrupoNew = articulos.getCodigogrupo();
            Lineas codigolineaOld = persistentArticulos.getCodigolinea();
            Lineas codigolineaNew = articulos.getCodigolinea();
            if (codigobodegaNew != null) {
                codigobodegaNew = em.getReference(codigobodegaNew.getClass(), codigobodegaNew.getCodigobodega());
                articulos.setCodigobodega(codigobodegaNew);
            }
            if (codigogrupoNew != null) {
                codigogrupoNew = em.getReference(codigogrupoNew.getClass(), codigogrupoNew.getCodigogrupo());
                articulos.setCodigogrupo(codigogrupoNew);
            }
            if (codigolineaNew != null) {
                codigolineaNew = em.getReference(codigolineaNew.getClass(), codigolineaNew.getCodigolinea());
                articulos.setCodigolinea(codigolineaNew);
            }
            articulos = em.merge(articulos);
            if (codigobodegaOld != null && !codigobodegaOld.equals(codigobodegaNew)) {
                codigobodegaOld.getArticulosList().remove(articulos);
                codigobodegaOld = em.merge(codigobodegaOld);
            }
            if (codigobodegaNew != null && !codigobodegaNew.equals(codigobodegaOld)) {
                codigobodegaNew.getArticulosList().add(articulos);
                codigobodegaNew = em.merge(codigobodegaNew);
            }
            if (codigogrupoOld != null && !codigogrupoOld.equals(codigogrupoNew)) {
                codigogrupoOld.getArticulosList().remove(articulos);
                codigogrupoOld = em.merge(codigogrupoOld);
            }
            if (codigogrupoNew != null && !codigogrupoNew.equals(codigogrupoOld)) {
                codigogrupoNew.getArticulosList().add(articulos);
                codigogrupoNew = em.merge(codigogrupoNew);
            }
            if (codigolineaOld != null && !codigolineaOld.equals(codigolineaNew)) {
                codigolineaOld.getArticulosList().remove(articulos);
                codigolineaOld = em.merge(codigolineaOld);
            }
            if (codigolineaNew != null && !codigolineaNew.equals(codigolineaOld)) {
                codigolineaNew.getArticulosList().add(articulos);
                codigolineaNew = em.merge(codigolineaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = articulos.getCodigoarticulo();
                if (findArticulos(id) == null) {
                    throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.");
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
            Articulos articulos;
            try {
                articulos = em.getReference(Articulos.class, id);
                articulos.getCodigoarticulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.", enfe);
            }
            Bodegas codigobodega = articulos.getCodigobodega();
            if (codigobodega != null) {
                codigobodega.getArticulosList().remove(articulos);
                codigobodega = em.merge(codigobodega);
            }
            Grupos codigogrupo = articulos.getCodigogrupo();
            if (codigogrupo != null) {
                codigogrupo.getArticulosList().remove(articulos);
                codigogrupo = em.merge(codigogrupo);
            }
            Lineas codigolinea = articulos.getCodigolinea();
            if (codigolinea != null) {
                codigolinea.getArticulosList().remove(articulos);
                codigolinea = em.merge(codigolinea);
            }
            em.remove(articulos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulos> findArticulosEntities() {
        return findArticulosEntities(true, -1, -1);
    }

    public List<Articulos> findArticulosEntities(int maxResults, int firstResult) {
        return findArticulosEntities(false, maxResults, firstResult);
    }

    private List<Articulos> findArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulos.class));
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

    public Articulos findArticulos(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulos> rt = cq.from(Articulos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
