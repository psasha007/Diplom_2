package api.user;

public class User {
    public String email;
    public String password;
    public String name;
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String name) {
        this.name = name;
    }
}
