package middleware.Dtos.Account;

import play.data.validation.Constraints;

public class LoginDto {

    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.Required
    public String password;

    public Boolean refreshToken;
}
