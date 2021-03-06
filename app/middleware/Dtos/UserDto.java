package middleware.Dtos;

import middleware.Bases.BaseDto;
import middleware.Enums.Status;
import play.data.validation.Constraints;

public class UserDto extends BaseDto {

    @Constraints.Required
    private String name;

    @Constraints.Required
    @Constraints.Email
    private String email;

    @Constraints.Required
    private String password;

    public UserDto() {
    }

    public UserDto(Long id, Status status, Long updatedUserId) {
        super(id, status, updatedUserId);
    }

    public UserDto(String name, String email, String password, Status status, Long updatedUserId) {
        this.name     = name;
        this.email    = email;
        this.password = password;
        setStatus(status);
        setUpdatedUserId(updatedUserId);
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
}
