/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.DatabaseCleaner;
import nl.fontys.util.Money;

/**
 *
 * @author Arno
 */
@WebService
public class Auction {
    final private AuctionMgr auctionMgr = new AuctionMgr();
    final private SellerMgr sellerMgr = new SellerMgr();
    
    public Item getItem(long id)
    {
        return auctionMgr.getItem(id);
    }
    
    public List<Item> findItemByDescription(String description)
    {
        return auctionMgr.findItemByDescription(description);
    }
    
    public Bid newBid(Item item, User buyer, Money amount)
    {
        return auctionMgr.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category cat, String description)
    {
        return sellerMgr.offerItem(seller, cat, description);
    }
    
    public boolean revokeItem(Item item)
    {
        return sellerMgr.revokeItem(item);
    }
    
    public Category getCategory(String description)
    {
        return new Category(description);
    }
    
    public Money getMoney(long cents, String currency)
    {
        return new Money(cents, currency);
    }
    
    public void cleanDB()
    {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
           EntityManager em = emf.createEntityManager();
           DatabaseCleaner cleaner = new DatabaseCleaner(em);
        try {
            cleaner.clean();
        } catch (SQLException ex) {
            Logger.getLogger(Auction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
