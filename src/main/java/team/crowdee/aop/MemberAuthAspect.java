package team.crowdee.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MemberAuthAspect {

    private final CustomJWTFilter customJWTFilter;

    @Before("@annotation(team.crowdee.customAnnotation.MemberAuth)")
    public void confirmMemberAuth(JoinPoint joinPoint) {
        log.info("member 권한 확인 AOP 호출");
        for (Object object : joinPoint.getArgs()) {
            if (object instanceof HttpServletRequest || object instanceof MultipartHttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
                boolean flag = customJWTFilter.isBacker(request);
                boolean flag2 = customJWTFilter.isCreator(request);
                if (!(flag || flag2)) {
                    throw new IllegalStateException("권한이 없습니다.");
                }
            }
        }
    }

}
