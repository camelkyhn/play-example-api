package services.JWT;

import com.auth0.jwt.interfaces.DecodedJWT;
import play.libs.Json;

import java.util.Date;

public class VerifiedJWT {

    private String header;
    private String payload;
    private String issuer;
    private Long userId;
    private Boolean refreshToken;
    private Date expiresAt;

    public VerifiedJWT() {
    }

    public VerifiedJWT(DecodedJWT decodedJWT) {
        this(decodedJWT.getHeader(), decodedJWT.getPayload(), decodedJWT.getIssuer(), decodedJWT.getClaim("user_id").asLong(), decodedJWT.getClaim("refresh_token").asBoolean(), decodedJWT.getExpiresAt());
    }

    public VerifiedJWT(String header, String payload, String issuer, Long userId, Boolean refreshToken, Date expiresAt) {
        this.header       = header;
        this.payload      = payload;
        this.issuer       = issuer;
        this.userId       = userId;
        this.refreshToken = refreshToken;
        this.expiresAt    = expiresAt;
    }

    public String getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }

    public String getIssuer() {
        return issuer;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean isRefreshToken() {
        return refreshToken;
    }

    public String toString() {
        return Json.toJson(this).toString();
    }
}
