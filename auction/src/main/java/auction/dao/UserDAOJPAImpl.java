package auction.dao;

import auction.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager em;

    public UserDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        em.persist(user);
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        em.merge(user);
    }


    @Override
    public List<User> findAll() {
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            return em.createQuery(cq).getResultList();
        }catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try{
            Query q = em.createNamedQuery("User.findByUserEmail", User.class);
            q.setParameter("userEmail", email);
            return (User) q.getSingleResult();
        }catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(User user) {
        em.remove(em.merge(user));
    }
}
