package middleware.Bases;

import middleware.Enums.Status;

public class BaseDto {

    private Long id;

    private Status status;

    private Long updatedUserId;

    public BaseDto() {
    }

    public BaseDto(Long id, Status status, Long updatedUserId) {
        this.id            = id;
        this.status        = status;
        this.updatedUserId = updatedUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }
}
