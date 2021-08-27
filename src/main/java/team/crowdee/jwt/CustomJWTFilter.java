package team.crowdee.jwt;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Stack;

import static java.util.Base64.getDecoder;

public class CustomJWTFilter {

    public static String findEmail(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        String[] payload = getPayload(bearerToken);
        if(payload.length==0){
            return null;
        }
        return payload[3];//이메일 정보
    }

    public static String findAuthority(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        String[] payload = getPayload(bearerToken);
        if(payload.length==0){
            return null;
        }
        return payload[7];//권한정보
    }

    public static String[] getPayload(String bearerToken) {
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            String[] token = bearerToken.substring(7).split("\\.");
            Base64.Decoder decoder = getDecoder();
            byte[] keyBytes = decoder.decode(token[1].getBytes());
            String payload = new String(keyBytes);
            String[] strings = payload.split("\"");
            return strings;//payload 배열
        }
        return null;
    }

    public static boolean isBacker(HttpServletRequest request) {
        String authority = findAuthority(request);
        if (StringUtils.hasText(authority)) {
            if (!authority.contains("backer")) {
                return false;
            }
        }
        return true;
    }
    public static boolean isCreator(HttpServletRequest request) {
        String authority = findAuthority(request);
        if (StringUtils.hasText(authority)) {
            if (!authority.contains("creator")) {
                return false;
            }
        }
        return true;
    }
    public static boolean isAdmin(HttpServletRequest request) {
        String authority = findAuthority(request);
        if (StringUtils.hasText(authority)) {
            if (!authority.contains("admin")) {
                return false;
            }
        }
        return true;

    }




}
