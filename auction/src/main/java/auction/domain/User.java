package auction.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "select u from User as u"),
    @NamedQuery(name = "User.count", query = "select count(u) from User as u"),
    @NamedQuery(name = "User.findByUserEmail", query = "select u from User as u where u.email = :userEmail")
})
public class User {
    @Id
    private Long id;
    
    private String email;
    
    public User(){}

    public User(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
