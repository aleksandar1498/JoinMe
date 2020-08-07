package com.enjoyit.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility class used for the control of the JWTToken
 */
@Component
public class JwtTokenUtil {
    private static final String SECRET = "secret";
    private static final String ROLES_CLAIM = "roles";
    public static final long JWT_TOKEN_VALIDITY = 1L * 60 * 60;

    private String doGenerateToken(final Map<String, Object> claims, final User user) {
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * @param user
     *            is the user for whom a token has to be created
     * @return a valid jwt token
     */
    public String generateToken(final User user) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put(ROLES_CLAIM,
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return doGenerateToken(claims, user);
    }

    public String resolveToken(final HttpServletRequest req) {
        final String token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7, token.length());
        }
        return null;
    }

    /**
     * @param token
     *            from which all claims have to be extracted
     * @return the Claims present in the token
     */
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * Generic method used to retrieve a specific claim from a token
     *
     * @param <T>
     *            is the type of the claim that will be returned
     * @param token
     *            from which a specific claim has to be extracted
     * @param claimsResolver
     *            function used to extract a specific clain
     * @return the value of a specific claim
     */
    private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @param token
     *            from which the expiration date has to be extracted
     * @return the expiration date
     */
    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    /**
     * @param token
     *            from which the username has to be extracted
     * @return the username
     */
    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * @param token
     *            from which the expiration validity of the token is checked
     * @return the expiration date
     */
    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * @param token
     *            from which the username has to be extracted
     * @return if the token is valid
     */
    public Boolean validateToken(final String token) {
        try {
            final Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Expired or invalid JWT token");
        }
    }
}
