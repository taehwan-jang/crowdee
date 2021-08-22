package team.crowdee.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@Slf4j
// jwt.secret, jwt.token-validity-in-seconds 주입
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds;


    private Key key;

    public TokenProvider(
            @Value("${jwt.secret})") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
            this.secret = secret;
            this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    // 빈생성, 의존성 주입 후 주입받은 secret값을
    // base64 decode 하여 변수에 할당하기 위한 오버라이딩
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Authentication 객체에 포함되어있는 권한 정보들을 담은 토큰 생성
    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date().getTime());
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    // 토큰에 담겨있는 권한 정보들을 이용해 Authentication 객체를 return
    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰 검증
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            e.printStackTrace();
            log.info("잘못된 JWT 서명입니다.");
        }
        catch(ExpiredJwtException e){
            log.info("만료된 JWT 토큰입니다.");
        }
        catch(UnsupportedJwtException e){
            log.info("지원하지 않는 JWT 토큰입니다.");
        }
        catch(IllegalArgumentException e){
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;

    }

    public boolean findCoffeeAll(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("jwt");
        boolean checkJWT = validateToken(token);
        return checkJWT;
    }
}
