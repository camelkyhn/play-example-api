package middleware.Dtos;

import middleware.Bases.BaseDto;
import middleware.Enums.Status;

public class UserRoleDto extends BaseDto {

    private Long userId;

    private Long roleId;

    public UserRoleDto() {
    }

    public UserRoleDto(Long id, Status status, Long updatedUserId) {
        super(id, status, updatedUserId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
