package team.crowdee.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class RepositoryAspect {

    @Before("execution(* team.crowdee.repository.*.*(..))")
    public void findWhichMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getTarget().toString();
        String joinPointName = joinPoint.toString();

        log.info("Query[{},{}]",methodName,System.currentTimeMillis());
        log.info("Query[{},{}]",joinPointName,System.currentTimeMillis());

    }
}
