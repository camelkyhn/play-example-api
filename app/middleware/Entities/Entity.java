package middleware.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;
import middleware.Enums.Status;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class Entity extends Model {

    @Id
    protected Long id;

    @ManyToOne
    @JsonBackReference
    protected User updatedUser;

    protected Status status;

    @CreatedTimestamp
    protected Date createdDate;

    @UpdatedTimestamp
    protected Date updatedDate;

    public Entity() {
    }

    public Entity(Long id, Status status, User updatedUser) {
        this.id          = id;
        this.updatedUser = updatedUser;
        this.status      = status;
    }

    public Entity(Long id, Status status, User updatedUser, Date createdDate, Date updatedDate) {
        this.id          = id;
        this.updatedUser = updatedUser;
        this.status      = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
