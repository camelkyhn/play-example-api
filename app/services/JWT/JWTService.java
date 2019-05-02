package services.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.typesafe.config.Config;
import middleware.Core.GeneralResult;

import javax.inject.Inject;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class JWTService {

    private Config _config;
    private String _secret;
    private JWTVerifier _verifier;

    @Inject
    public JWTService(Config config) {
        _config   = config;
        _secret   = _config.getString("play.http.secret.key");
        _verifier = JWT.require(Algorithm.HMAC256(_secret)).withIssuer("playexample").build();
    }

    public GeneralResult<String> getToken(Long userId, Boolean refreshToken) {
        GeneralResult<String> result = new GeneralResult<>();
        try {
            Algorithm algorithm = Algorithm.HMAC256(_secret);
            String token = JWT.create()
                    .withIssuer("playexample")
                    .withClaim("user_id", userId)
                    .withClaim("refresh_token", refreshToken)
                    .withExpiresAt(refreshToken
                            ? Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusHours(24L).toInstant())
                            : Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusHours(2L).toInstant()))
                    .sign(algorithm);
            result.Success(token);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    public GeneralResult<VerifiedJWT> verifyToken(String token) {
        GeneralResult<VerifiedJWT> result = new GeneralResult<>();
        try {
            DecodedJWT  jwt         = _verifier.verify(token);
            VerifiedJWT verifiedJWT = new VerifiedJWT(jwt);
            result.Success(verifiedJWT);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }
}
