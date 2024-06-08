package com.alura.forohub.infra.security;

import com.alura.forohub.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;
    public String generateToken(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("forohub_api")
                    .withSubject("martines")
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate(2))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }

    }

    public String getSubject(String token) {
        if (token==null) throw new RuntimeException();

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier=  JWT.require(algorithm)
                    .withIssuer("forohub_api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception){
            throw new RuntimeException(exception.toString());
        }
        if (verifier.getSubject() == null)
            throw new RuntimeException("verifier invalido");
        return verifier.getSubject();
    }
    private Instant generateExpirationDate(int time){
        return LocalDateTime.now().plusHours(time).toInstant(ZoneOffset.of("-05:00"));
    }
}
