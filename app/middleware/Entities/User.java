package middleware.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import middleware.Enums.Status;
import play.data.validation.Constraints;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Table(name = "usr")
public class User extends Entity {

    @Constraints.Required
    private String name;

    @Constraints.Required
    @Constraints.Email
    private String email;

    @Constraints.MinLength(6)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "updatedUser")
    @JsonManagedReference
    private List<Role> updatedRoles;

    @OneToMany(mappedBy = "updatedUser")
    @JsonManagedReference
    private List<User> updatedUsers;

    @OneToMany(mappedBy = "updatedUser")
    @JsonManagedReference
    private List<UserRole> updatedUserRoles;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    public User(Long id, Status status, User updatedUser, String name, String email, String password) {
        super(id, status, updatedUser);
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    public User(Long id, String name, String email, String password, Status status, User updatedUser, Date createdDate, Date updatedDate) {
        super(id, status, updatedUser, createdDate, updatedDate);
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
