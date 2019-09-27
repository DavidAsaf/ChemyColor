/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocios;

import CapaDatos.Tipousuarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import CapaDatos.Usuarios;
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
public class TipousuariosJpaController implements Serializable {

    public TipousuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipousuarios tipousuarios) throws PreexistingEntityException, Exception {
        if (tipousuarios.getUsuariosList() == null) {
            tipousuarios.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : tipousuarios.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getCodigousuario());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            tipousuarios.setUsuariosList(attachedUsuariosList);
            em.persist(tipousuarios);
            for (Usuarios usuariosListUsuarios : tipousuarios.getUsuariosList()) {
                Tipousuarios oldCodtipousuarioOfUsuariosListUsuarios = usuariosListUsuarios.getCodtipousuario();
                usuariosListUsuarios.setCodtipousuario(tipousuarios);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
                if (oldCodtipousuarioOfUsuariosListUsuarios != null) {
                    oldCodtipousuarioOfUsuariosListUsuarios.getUsuariosList().remove(usuariosListUsuarios);
                    oldCodtipousuarioOfUsuariosListUsuarios = em.merge(oldCodtipousuarioOfUsuariosListUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipousuarios(tipousuarios.getCodtipousuario()) != null) {
                throw new PreexistingEntityException("Tipousuarios " + tipousuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipousuarios tipousuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipousuarios persistentTipousuarios = em.find(Tipousuarios.class, tipousuarios.getCodtipousuario());
            List<Usuarios> usuariosListOld = persistentTipousuarios.getUsuariosList();
            List<Usuarios> usuariosListNew = tipousuarios.getUsuariosList();
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getCodigousuario());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            tipousuarios.setUsuariosList(usuariosListNew);
            tipousuarios = em.merge(tipousuarios);
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    usuariosListOldUsuarios.setCodtipousuario(null);
                    usuariosListOldUsuarios = em.merge(usuariosListOldUsuarios);
                }
            }
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    Tipousuarios oldCodtipousuarioOfUsuariosListNewUsuarios = usuariosListNewUsuarios.getCodtipousuario();
                    usuariosListNewUsuarios.setCodtipousuario(tipousuarios);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
                    if (oldCodtipousuarioOfUsuariosListNewUsuarios != null && !oldCodtipousuarioOfUsuariosListNewUsuarios.equals(tipousuarios)) {
                        oldCodtipousuarioOfUsuariosListNewUsuarios.getUsuariosList().remove(usuariosListNewUsuarios);
                        oldCodtipousuarioOfUsuariosListNewUsuarios = em.merge(oldCodtipousuarioOfUsuariosListNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = tipousuarios.getCodtipousuario();
                if (findTipousuarios(id) == null) {
                    throw new NonexistentEntityException("The tipousuarios with id " + id + " no longer exists.");
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
            Tipousuarios tipousuarios;
            try {
                tipousuarios = em.getReference(Tipousuarios.class, id);
                tipousuarios.getCodtipousuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipousuarios with id " + id + " no longer exists.", enfe);
            }
            List<Usuarios> usuariosList = tipousuarios.getUsuariosList();
            for (Usuarios usuariosListUsuarios : usuariosList) {
                usuariosListUsuarios.setCodtipousuario(null);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
            }
            em.remove(tipousuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipousuarios> findTipousuariosEntities() {
        return findTipousuariosEntities(true, -1, -1);
    }

    public List<Tipousuarios> findTipousuariosEntities(int maxResults, int firstResult) {
        return findTipousuariosEntities(false, maxResults, firstResult);
    }

    private List<Tipousuarios> findTipousuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipousuarios.class));
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

    public Tipousuarios findTipousuarios(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipousuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipousuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipousuarios> rt = cq.from(Tipousuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
