package middleware.Entities;

import middleware.Enums.Status;
import play.data.validation.Constraints;

import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
public class Role extends Entity {

    @Constraints.Required
    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name, Status status, User updatedUser) {
        super(id, status, updatedUser);
        this.name = name;
    }

    public Role(Long id, String name, Status status, User updatedUser, Date createdDate, Date updatedDate) {
        super(id, status, updatedUser, createdDate, updatedDate);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    @JsonIgnore
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
