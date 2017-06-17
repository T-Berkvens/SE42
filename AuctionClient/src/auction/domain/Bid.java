package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

public class Bid {
    private Long id;
    private FontysTime time;
    private User buyer;
    private Money amount;

    public Bid(){}
    
    public Bid(User buyer, Money amount) {
        if(buyer == null){
            throw new IllegalArgumentException("tried creating a Bid with buyer null");
        }else if(amount == null){
            throw new IllegalArgumentException("tried to create a bid with Money as null");
        }
        this.buyer = buyer;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(FontysTime time) {
        this.time = time;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
}
