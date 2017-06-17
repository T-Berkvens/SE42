

import auction.service.*;
import static org.junit.Assert.*;

import nl.fontys.util.Money;

import org.junit.Before;
import org.junit.Test;

import web.Bid;
import web.Item;
import web.User;
import java.util.ArrayList;
import web.Category;

public class JPAAuctionMgrTest {

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

    @Test
    public void getItem() {
        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = registrationMgr.registerUser(email);
        web.Category cat = auctionMgr.getCategory("cat2");
        Item item1 = sellerMgr.offerItem(seller1, cat, omsch);
        Item item2 = auctionMgr.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = registrationMgr.registerUser(email3);
        User seller4 = registrationMgr.registerUser(email4);
        web.Category cat = auctionMgr.getCategory("cat3");
        Item item1 = sellerMgr.offerItem(seller3, cat, omsch);
        Item item2 = sellerMgr.offerItem(seller4, cat, omsch);

        ArrayList<Item> res = (ArrayList<Item>) auctionMgr.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = (ArrayList<Item>) auctionMgr.findItemByDescription(omsch);
        assertEquals(2, res.size());

    }

    @Test
    public void newBid() {
        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = registrationMgr.registerUser(email);
        User buyer = registrationMgr.registerUser(emailb);
        User buyer2 = registrationMgr.registerUser(emailb2);
        // eerste bod
        Category cat = new Category();
        
        Item item1 = sellerMgr.offerItem(seller, cat, omsch);
        web.Money mon1 = auctionMgr.getMoney(10, "eur");
        Bid new1 = auctionMgr.newBid(item1, buyer, mon1);
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        web.Money mon2 = auctionMgr.getMoney(9, "eur");
        Bid new2 = auctionMgr.newBid(item1, buyer2, mon2);
        assertNull(new2);

        // hoger bod
        web.Money mon3 = auctionMgr.getMoney(11, "eur");
        Bid new3 = auctionMgr.newBid(item1, buyer2, mon3);
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
}
