package team.crowdee.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class MemberAuthAop {

    @Around("@annotation(team.crowdee.customAnnotation.MemberAOP)")
    public Object confirmMemberAuth(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;

        for (Object object : proceedingJoinPoint.getArgs()) {
            if (object instanceof HttpServletRequest || object instanceof MultipartHttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) object;
            }
        }

        return result;
    }

}
