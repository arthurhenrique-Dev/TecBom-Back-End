package com.tecbom.e_commerce.Infra.Security.Service;

import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("Outro_valor_que_normalmente_não_pode_estar_aqui")
    private String secret;

    public String generateToken(Object domainObject) {

        if (domainObject instanceof User) {
            return generateToken((User) domainObject);
        } else if (domainObject instanceof Master) {
            return generateToken((Master) domainObject);
        } else {
            throw new ValidationFailedException("Tipo de objeto não suportado para geração de token");
        }
    }
    private String generateToken(User user) {
        return createJwt(
                user.getCpf().toString(),
                user.getName().toString(),
                user.getEmail().toString(),
                user.getRole().name()
        );
    }
    private String generateToken(Master master){
        return createJwt(
                master.getCpf().toString(),
                master.getName().toString(),
                master.getEmail().toString(),
                master.getRole().name()
                );
    }

    private String createJwt(String cpf, String name, String email, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("tec-bom-api")
                    .withSubject(cpf)
                    .withClaim("name", name)
                    .withClaim("email", email)
                    .withClaim("role", role)
                    .withExpiresAt(LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("tec-bom-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }
}
