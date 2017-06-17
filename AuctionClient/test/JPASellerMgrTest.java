

import auction.service.*;
import static org.junit.Assert.*;

import nl.fontys.util.Money;

import org.junit.Before;
import org.junit.Test;



import web.Category;
import web.Item;
import web.User;

public class JPASellerMgrTest {

    private AuctionMgr auctionMgr;
    private RegistrationMgr registrationMgr;
    private SellerMgr sellerMgr;

    @Before
    public void setUp() throws Exception {
        registrationMgr = new RegistrationMgr();
        auctionMgr = new AuctionMgr();
        sellerMgr = new SellerMgr();
        auctionMgr.cleanDB();
    }

    /**
     * Test of offerItem method, of class SellerMgr.
     */
    @Test
    public void testOfferItem() {
        String omsch = "omsch";

        User user1 = registrationMgr.registerUser("xx@nl");
        web.Category cat = auctionMgr.getCategory("cat1");
        Item item1 = sellerMgr.offerItem(user1, cat, omsch);
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";
        
    
        User seller = registrationMgr.registerUser("sel@nl");
        User buyer = registrationMgr.registerUser("buy@nl");
        web.Category cat = auctionMgr.getCategory("cat1");
        
            // revoke before bidding
        Item item1 = sellerMgr.offerItem(seller, cat, omsch);
        boolean res = sellerMgr.revokeItem(item1);
        assertTrue(res);
        int count = auctionMgr.findItemByDescription(omsch).size();
        assertEquals(0, count);
        
            // revoke after bid has been made
        Item item2 = sellerMgr.offerItem(seller, cat, omsch2);
        web.Money mon1 = auctionMgr.getMoney(100, "eur");
        auctionMgr.newBid(item2, buyer, mon1);
        boolean res2 = sellerMgr.revokeItem(item2);
        assertFalse(res2);
        int count2 = auctionMgr.findItemByDescription(omsch2).size();
        assertEquals(1, count2);
        
        
        
        
    }

}
