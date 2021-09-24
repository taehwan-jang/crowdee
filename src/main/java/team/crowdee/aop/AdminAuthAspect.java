package team.crowdee.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.crowdee.security.CustomJWTFilter;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@RequiredArgsConstructor
public class AdminAuthAspect {

    private final CustomJWTFilter customJWTFilter;

    @Before("@annotation(team.crowdee.customAnnotation.AdminAuth)")
    public void findAdminAuth(JoinPoint joinPoint) {
        for (Object object : joinPoint.getArgs()) {
            if (object instanceof HttpServletRequest || object instanceof MultipartHttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
                boolean flag = customJWTFilter.isAdmin(request);
                if (!flag) {
                    throw new IllegalStateException("권한이 없습니다.");
                }
            }
        }
    }
}
