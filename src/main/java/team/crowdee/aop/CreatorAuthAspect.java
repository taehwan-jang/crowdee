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
@RequiredArgsConstructor
@Aspect
public class CreatorAuthAspect {
    private final CustomJWTFilter customJWTFilter;

    @Before("@annotation(team.crowdee.customAnnotation.CreatorAuth))")
    public void confirmCreatorAuth(JoinPoint joinPoint) {
        for (Object object : joinPoint.getArgs()) {
            if (object instanceof HttpServletRequest || object instanceof MultipartHttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
                boolean flag = customJWTFilter.isCreator(request);
                boolean flag2 = customJWTFilter.isAdmin(request);
                if (!(flag || flag2)) {
                    throw new IllegalStateException("권한이 없습니다.");
                }
            }
        }
    }
}
