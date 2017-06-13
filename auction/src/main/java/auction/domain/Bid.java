package auction.domain;

import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

public class Bid {
    @Embedded
    private FontysTime time;
    @ManyToOne
    private User buyer;
    @Embedded
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
