package auction.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import nl.fontys.util.Money;

@Entity
@NamedQueries({
    @NamedQuery(name = "Item.getAll", query = "select i from Item as i"),
    @NamedQuery(name = "Item.count", query = "select count(i) from Item as i"),
    @NamedQuery(name = "Item.findById", query = "select i from Item as i where i.id = :id"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description LIKE :description")
})
public class Item implements Comparable {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private User seller;
    @Embedded
    @AttributeOverrides({
     @AttributeOverride(name = "description",
        column = @Column(name = "c_description"))})
    private Category category;
    private String description;
    @OneToOne
    private Bid highest;

    public Item(){}
    
    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bid getHighest() {
        return highest;
    }

    public void setHighest(Bid highest) {
        this.highest = highest;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    @Override
    public int compareTo(Object arg0) {
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Item temp = (Item)o;
        if (temp.getId().equals(this.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        //TODO
        return 0;
    }
}
