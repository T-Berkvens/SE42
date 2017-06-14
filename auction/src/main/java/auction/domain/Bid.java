package auction.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Entity
public class Bid {
    @Id @GeneratedValue
    private Long id;
    @Embedded
    private FontysTime time;
    @ManyToOne
    private User buyer;
    @Embedded
    private Money amount;
    @OneToOne @Column(nullable = false)
    private Item highestItem;

    

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
    
    public Item getHighestItem() {
        return highestItem;
    }

    public void setHighestItem(Item highestItem) {
        this.highestItem = highestItem;
    }
}
