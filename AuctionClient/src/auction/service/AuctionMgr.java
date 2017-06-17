package auction.service;

import web.Category;
import web.Money;

public class AuctionMgr  {

    public static web.Item getItem(long arg0) {
        web.AuctionService service = new web.AuctionService();
        web.Auction port = service.getAuctionPort();
        return port.getItem(arg0);
    }
    
    public static web.Bid newBid(web.Item arg0, web.User arg1, web.Money arg2) {
        web.AuctionService service = new web.AuctionService();
        web.Auction port = service.getAuctionPort();
        return port.newBid(arg0, arg1, arg2);
    }

    public static java.util.List<web.Item> findItemByDescription(java.lang.String arg0) {
        web.AuctionService service = new web.AuctionService();
        web.Auction port = service.getAuctionPort();
        return port.findItemByDescription(arg0);
    }

    public static Category getCategory(java.lang.String arg0) {
        web.AuctionService service = new web.AuctionService();
        web.Auction port = service.getAuctionPort();
        return port.getCategory(arg0);
    }

    public static Money getMoney(long arg0, java.lang.String arg1) {
        web.AuctionService service = new web.AuctionService();
        web.Auction port = service.getAuctionPort();
        return port.getMoney(arg0, arg1);
    }
    
   
}
