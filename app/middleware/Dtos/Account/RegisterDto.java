package middleware.Dtos.Account;

import play.data.validation.Constraints;

public class RegisterDto {

    @Constraints.Required
    public String name;

    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.Required
    public String password;
}
