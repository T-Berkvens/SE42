package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuctionMgr  {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        EntityManager em = emf.createEntityManager();
        ItemDAO itemDAO = new ItemDAOJPAImpl(em);
        em.getTransaction().begin();
        Item item = null;
        try {
            item = itemDAO.find(id);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
        finally {
            em.close();
        }
        return item;
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        EntityManager em = emf.createEntityManager();
        ItemDAO itemDAO = new ItemDAOJPAImpl(em);
        em.getTransaction().begin();
        ArrayList<Item> itemlst = new ArrayList<Item>();
        List<Item> items = null;
        try {
            items = itemDAO.findByDescription(description);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
        finally {
            em.close();
        }
        if (items != null) {
            for (Item i : items) {
                itemlst.add(i);
            }
        }
        return itemlst;
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        EntityManager em = emf.createEntityManager();
        ItemDAO itemDAO = new ItemDAOJPAImpl(em);
        em.getTransaction().begin();
        Bid bid = null;
        bid = item.newBid(buyer, amount);
        if (bid != null) {
            try {
                itemDAO.edit(item);
                em.getTransaction().commit();
            }
            catch(Exception ex) {
                ex.printStackTrace();
                em.getTransaction().rollback();
                bid = null;
            }
            finally {
                em.close();
            }
        }
        return bid;
    }
}
