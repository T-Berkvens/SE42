package auction.service;

import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;


public class SellerMgr {

    public static boolean revokeItem(web.Item arg0) {
        web.AuctionService service = new web.AuctionService();
        web.Auction port = service.getAuctionPort();
        return port.revokeItem(arg0);
    }

    public static web.Item offerItem(web.User arg0, web.Category arg1, java.lang.String arg2) {
        web.AuctionService service = new web.AuctionService();
        web.Auction port = service.getAuctionPort();
        return port.offerItem(arg0, arg1, arg2);
    }

    
}
