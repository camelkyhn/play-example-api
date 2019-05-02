package middleware.Dtos;

import middleware.Bases.BaseDto;
import middleware.Enums.Status;
import play.data.validation.Constraints;

public class RoleDto extends BaseDto {

    @Constraints.Required
    private String name;

    public RoleDto() {
    }

    public RoleDto(Long id, String name, Status status, Long updatedUser) {
        super(id, status, updatedUser);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
