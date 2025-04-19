package np.com.debid.restroauthservice.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import np.com.debid.restroauthservice.dto.RestaurantResponse;
import np.com.debid.restroauthservice.service.RestaurantClient;
import np.com.debid.restroauthservice.service.impl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${restro.jwt.secret-key}")
    private String jwtSecret;

    @Value("${restro.jwt.expiration-time}")
    private int jwtExpiration;

    @Autowired
    RestaurantClient restaurantClient;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        List<RestaurantResponse> restaurantResponse = restaurantClient.getUserById(userPrincipal.getId());
        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .claim("userId", userPrincipal.getId())
                .claim("restaurantId", restaurantResponse.get(0).getId())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}