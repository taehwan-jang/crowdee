package team.crowdee.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.crowdee.security.CustomJWTFilter;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@RequiredArgsConstructor
public class AdminAuthAop {

    private final CustomJWTFilter customJWTFilter;

    @Around("@annotation(team.crowdee.customAnnotation.AdminAOP)")
    public Object findAdminAuth(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object result = null;
        for (Object object : proceedingJoinPoint.getArgs()) {
            if (object instanceof HttpServletRequest || object instanceof MultipartHttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
                boolean flag = customJWTFilter.isAdmin(request);
                if (!flag) {
                    return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.FORBIDDEN);
                }
            }
        }

        return result;
    }
}
