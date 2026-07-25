package com.cognizant.springlearn.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Hands-on 5: Create authentication service that returns JWT.
 * GET /authenticate (secured with HTTP Basic) -> {"token": "..."}
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    // NOTE: for learning purposes only - in a real application this key must be externalized
    // (e.g. environment variable / secrets manager) and never hard coded.
    private static final String SECRET_KEY = "secretkey";

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("START");
        LOGGER.debug("authHeader={}", authHeader);

        String user = getUser(authHeader);
        String token = generateJwt(user);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        LOGGER.info("END");
        return map;
    }

    /**
     * Decodes the Basic auth header ("Basic base64(user:pwd)") and returns the username.
     */
    private String getUser(String authHeader) {
        LOGGER.debug("Decoding Authorization header");
        String encodedCredentials = authHeader.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decoded = new String(decodedBytes);
        String user = decoded.substring(0, decoded.indexOf(':'));
        LOGGER.debug("Resolved user={}", user);
        return user;
    }

    /**
     * Generates a signed JWT for the given user, expiring 20 minutes from now.
     */
    private String generateJwt(String user) {
        LOGGER.debug("Generating JWT for user={}", user);
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user);
        builder.setIssuedAt(new Date());
        builder.setExpiration(new Date((new Date()).getTime() + 1200000));
        builder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
        return builder.compact();
    }
}
