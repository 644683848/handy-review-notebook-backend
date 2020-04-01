package com.ori.notebook.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class JwtUtils {
    private static final String secret = "ori";
    private static final String claimName = "principal";
    private static long timeOut;
    private static Algorithm algorithm;

    static {
        algorithm = Algorithm.HMAC256(secret);
    }

    @Value("${global.user.time-out}")
    public void setTimeOut(long timeOut) {
        JwtUtils.timeOut = timeOut;
    }

    public static long getTimeOut() {
        return JwtUtils.timeOut;
    }
    public static String getToken(String userId, String username) {
        return JWT.create()
                .withClaim(claimName, new HashMap<String, String>(){{
                    put("userId", userId);
                    put("username", username);
                }})
                .withExpiresAt(new Date())
                .sign(algorithm);
    }

    public static Map<String, Object> verifierToken(String token) {
        Map<String, Object> principal = null;
        JWTVerifier verifier = JWT.require(algorithm)
                .acceptExpiresAt(timeOut)
                .build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            Claim claim = jwt.getClaim(claimName);
            principal = claim.asMap();
        } catch (TokenExpiredException exception) {
            log.trace("token过期了: {}", exception.getClass().getName());
        } catch (SignatureVerificationException exception) {
            log.trace("token错误: {}", exception.getClass().getName());
        } catch (JWTDecodeException exception) {
            log.trace("token解码错误: {}", exception.getClass().getName());
        }
        return principal;
    }
}
