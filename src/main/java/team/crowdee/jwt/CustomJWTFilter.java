package team.crowdee.jwt;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

import static java.util.Base64.getDecoder;

public class CustomJWTFilter {

    public static String findEmail(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        String email = "";
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7);
            int firstDot = token.indexOf(".");
            int lastDot = token.lastIndexOf(".");
            String substring = token.substring(firstDot+1, lastDot);
            Base64.Decoder decoder = getDecoder();
            byte[] keyBytes = decoder.decode(substring.getBytes());
            String payload = new String(keyBytes);
            String[] strings = payload.split("\"");
            return strings[3];
        }
        return null;
    }

    public static String findAuthority(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        String email = "";
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7);
            int firstDot = token.indexOf(".");
            int lastDot = token.lastIndexOf(".");
            String substring = token.substring(firstDot+1, lastDot);
            Base64.Decoder decoder = getDecoder();
            byte[] keyBytes = decoder.decode(substring.getBytes());
            String payload = new String(keyBytes);
            String[] strings = payload.split("\"");
            return strings[7];
        }
        return null;
    }




}
