package com.tricast.controllers.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthenticationFilter extends GenericFilterBean {

    private static final Logger LOG = LogManager.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;

        if (bypassAuthentication(request.getRequestURI().toLowerCase(), request.getMethod().toLowerCase())) {
            chain.doFilter(req, res);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        final String token = authHeader.substring(7); // The part after "Bearer "
        Map<String, Claim> claims = validateToken(token);

        request.setAttribute("authentication.roleId",
                claims.get(AuthenticationSettings.CLAIM_ROLE_IDENTIFIER).asInt());
        request.setAttribute("authentication.userId",
                claims.get(AuthenticationSettings.CLAIM_USER_IDENTIFIER).asLong());
        request.setAttribute("authentication.username", claims.get(AuthenticationSettings.CLAIM_USERNAME_IDENTIFIER).asString());

        chain.doFilter(request, res);
    }

    private boolean bypassAuthentication(String uri, String method) {
        return uri.equals("/workinghours/rest/users/login") && method.equals("post");
    }

    private Map<String, Claim> validateToken(String token) throws ServletException {

        Algorithm algorithm = Algorithm.HMAC256(AuthenticationSettings.SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(AuthenticationSettings.ISSUER).build();

        try {
            DecodedJWT decodedToken = verifier.verify(token);
            return decodedToken.getClaims();
        } catch (JWTVerificationException e) {
            throw new ServletException("Invalid token");
        } catch (Exception e) {
            LOG.error("Error at token validation", e);
            throw new ServletException("Token validation error");
        }
    }
}
