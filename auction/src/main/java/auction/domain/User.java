package auction.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "select u from User as u"),
    @NamedQuery(name = "User.count", query = "select count(u) from User as u"),
    @NamedQuery(name = "User.findByUserEmail", query = "select u from User as u where u.email = :userEmail")
})
public class User {
    @Id
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="seller")
    private Set<Item> offeredItems;
    
    public User(){
        offeredItems = new HashSet<Item>();
    }

    public User(String email) {
        this.email = email;
        offeredItems = new HashSet<Item>();
    }
    
    public Item createItem(Category category, String description){
        Item newItem = new Item(this, category, description);
        addItem(newItem);
        return newItem;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    private void addItem(Item i){
        offeredItems.add(i);
    }
    
    public Set<Item> getOfferedItems() {
        return offeredItems;
    }
    
    public int numberOfOfferedItems(){
        return offeredItems.size();
    }
    
    @Override
    public boolean equals(Object toCompare) {
        if (this.getClass() != toCompare.getClass()) {
            return false;
        }
        User temp = (User)toCompare;
        if (temp.getEmail().equals(this.email)) {
            return true;
        }
        return false;
    }
}
