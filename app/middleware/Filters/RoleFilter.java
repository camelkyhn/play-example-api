package middleware.Filters;

import middleware.Bases.BaseFilter;

public class RoleFilter extends BaseFilter {

    private String name;

    public RoleFilter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
