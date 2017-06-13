/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.dao;

import auction.domain.Item;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Arno
 */
public class ItemDAOJPAImpl implements ItemDAO{
    private final EntityManager em;

    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public int count() {
        Query q = em.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        if (find(item.getId()) != null) {
            throw new EntityExistsException();
        }
        em.persist(item);
    }

    @Override
    public void edit(Item item) {
        if (find(item.getId()) == null) {
            throw new IllegalArgumentException();
        }
        em.merge(item);
    }

    @Override
    public Item find(Long id) {
        try{
            Query q = em.createNamedQuery("Item.findById", Item.class);
            q.setParameter("id", id);
            return (Item) q.getSingleResult();
        }catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Item> findAll() {
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
            return em.createQuery(cq).getResultList();
        }catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Item> findByDescription(String description) {
        try{
            Query q = em.createNamedQuery("Item.findByDescription", Item.class);
            q.setParameter("description", description);
            return (List<Item>) q.getResultList();
        }catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(Item item) {
        em.remove(em.merge(item));
    }
    
}
