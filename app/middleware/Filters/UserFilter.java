package middleware.Filters;

import middleware.Bases.BaseFilter;

public class UserFilter extends BaseFilter {

    private String name;

    private String email;

    public UserFilter() {
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
}
