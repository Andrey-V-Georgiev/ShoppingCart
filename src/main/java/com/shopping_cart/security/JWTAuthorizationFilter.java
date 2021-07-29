package com.shopping_cart.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.shopping_cart.constants.GlobalConstants.*;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        try {
            if (requestHasAuthorizationHeader(request)) {

                Claims claims = parseTokenFromRequest(request);
                boolean claimsHaveUserId = claims.get(CLAIMS_JTI) != null;
                boolean claimsHaveUserAuthorities = claims.get(CLAIMS_AUTHORITIES) != null;

                if (claimsHaveUserId && claimsHaveUserAuthorities) {
                    this.setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);

        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private Claims parseTokenFromRequest(HttpServletRequest request) {
        String jwtToken = request.getHeader(AUTHORIZATION_HEADER).replace(AUTHORIZATION_PREFIX, "");
        JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY.getBytes());
        return jwtParser.parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authoritiesRaw = (List) claims.get(CLAIMS_AUTHORITIES);
        String userId = (String) claims.get(CLAIMS_JTI);

        List<SimpleGrantedAuthority> authorities = authoritiesRaw
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, authorities));
    }

    private boolean requestHasAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER) != null;
    }
}
