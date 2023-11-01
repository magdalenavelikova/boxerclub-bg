package bg.boxerclub.boxerclubbgrestserver.core.jwt;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.service.user.AppUserDetailService;
import bg.boxerclub.boxerclubbgrestserver.service.user.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "https://www.boxerclub-bg.org/"},
        allowCredentials = "true", allowedHeaders = "true")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserDetailService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, AppUserDetailService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (!StringUtils.hasText(authHeader) ||
                (StringUtils.hasText(authHeader) && !authHeader.startsWith("Bearer "))) {
            //todo add header for JWT in production
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

            filterChain.doFilter(request, response);
            return;
        }
//token after prefix
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);

        //Generate an Authentication object using the provided username and password,
// and subsequently store it in the SecurityContextHolder.

        if (StringUtils.hasText(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            BoxerClubUserDetails userDetails = (BoxerClubUserDetails) userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }


}
