package team.crowdee.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

import static java.util.Base64.getDecoder;

@Component
public class CustomJWTFilter {

    public String findEmail(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        String[] payload = getPayload(bearerToken);
        if(payload==null){
            return null;
        }
        return payload[3];//이메일 정보
    }

    public String findAuthority(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        String[] payload = getPayload(bearerToken);
        if(payload==null){
            return null;
        }
        return payload[7];//권한정보
    }

    public String[] getPayload(String bearerToken) {
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            String[] token = bearerToken.substring(7).split("\\.");
            Base64.Decoder decoder = getDecoder();
            if (token.length == 1) {
                return null;
            }
            byte[] keyBytes = decoder.decode(token[1].getBytes());
            String payload = new String(keyBytes);
            String[] strings = payload.split("\"");
            return strings;//payload 배열
        }
        return null;
    }

    public HttpHeaders getHeaders(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);
        return httpHeaders;
    }

    public boolean isBacker(HttpServletRequest request) {
        String authority = findAuthority(request);
        if (StringUtils.hasText(authority)) {
            if (!authority.contains("backer")) {
                return false;
            }
        }
        return true;
    }
    public boolean isCreator(HttpServletRequest request) {
        String authority = findAuthority(request);
        if (StringUtils.hasText(authority)) {
            if (!authority.contains("creator")) {
                return false;
            }
        }
        return true;
    }
    public boolean isAdmin(HttpServletRequest request) {
        String authority = findAuthority(request);
        if (StringUtils.hasText(authority)) {
            if (!authority.contains("admin")) {
                return false;
            }
        }
        return true;

    }




}
