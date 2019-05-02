package middleware.Attributes;

import middleware.Core.GeneralResult;
import middleware.Entities.User;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import services.JWT.JWTService;
import services.JWT.VerifiedJWT;
import services.User.UserService;

import javax.inject.Inject;
import java.util.Optional;

public class Secured extends Security.Authenticator {

    private UserService _userService;
    private JWTService _jwtService;

    @Inject
    public Secured(UserService userService, JWTService jwtService) {
        _userService = userService;
        _jwtService  = jwtService;
    }

    @Override
    public Optional<String> getUsername(Http.Request req) {
        if (req.getHeaders().get("access_token").isPresent()) {
            GeneralResult<VerifiedJWT> jwtResult = _jwtService.verifyToken(req.getHeaders().get("access_token").get());
            if (jwtResult.isFailed()) {
                return null;
            }

            GeneralResult<User> userResult = _userService.detail(jwtResult.getData().getUserId());
            if (userResult.isFailed()) {
                return null;
            }

            return Optional.of(userResult.getData().getEmail());
        }

        return null;
    }

    @Override
    public Result onUnauthorized(Http.Request req) {
        GeneralResult result = new GeneralResult("NotAuthorized", "You don't have permission to access it!");
        return Results.status(401, Json.toJson(result));
    }
}
