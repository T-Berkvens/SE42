package auction.domain;

public class User {
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
