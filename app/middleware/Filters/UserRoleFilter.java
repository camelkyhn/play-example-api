package middleware.Filters;

import middleware.Bases.BaseFilter;

public class UserRoleFilter extends BaseFilter {

    private String userName;

    private String roleName;

    public UserRoleFilter() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
