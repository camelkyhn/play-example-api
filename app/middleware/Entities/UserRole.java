package middleware.Entities;

import middleware.Enums.Status;

import javax.persistence.ManyToOne;
import java.util.Date;

@javax.persistence.Entity
public class UserRole extends Entity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public UserRole(Long id, Status status, User updatedUser, User user, Role role) {
        super(id, status, updatedUser);
        this.user = user;
        this.role = role;
    }

    public UserRole(Long id, User user, Role role, Status status, User updatedUser, Date createdDate, Date updatedDate) {
        super(id, status, updatedUser, createdDate, updatedDate);
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
