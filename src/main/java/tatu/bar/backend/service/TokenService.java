package tatu.bar.backend.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import tatu.bar.backend.entity.Usuario;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    
    public String generateToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            
            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(this.generateExpirationData())
                    .sign(algorithm);
                return token;

        }catch(JWTCreationException Exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch(JWTVerificationException e){
            return null;
        }
    }

    private Instant generateExpirationData(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}