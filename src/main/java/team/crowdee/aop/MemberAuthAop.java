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
public class MemberAuthAop {

    private final CustomJWTFilter customJWTFilter;

    @Around("@annotation(team.crowdee.customAnnotation.MemberAOP)")
    public Object confirmMemberAuth(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        for (Object object : proceedingJoinPoint.getArgs()) {
            if (object instanceof HttpServletRequest || object instanceof MultipartHttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
                boolean flag = customJWTFilter.isBacker(request);
                boolean flag2 = customJWTFilter.isCreator(request);
                if (!(flag || flag2)) {
                    return new ResponseEntity<>("로그인 후 이용해주세요", HttpStatus.BAD_REQUEST);
                }
            }
        }
        result = proceedingJoinPoint.proceed();
        return result;
    }

}
