package com.playsafe.uc.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
    }


    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {

    }


    @Pointcut("within(com.playsafe.uc..*)" +
            " || within(com.playsafe.uc.service..*)" +
            " || within(com.playsafe.uc.web.rest..*)")
    public void applicationPackagePointcut() {

    }

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger(joinPoint)
                .error(
                        "Exception in {}() with cause = \'{}\' and exception = \'{}\'",
                        joinPoint.getSignature().getName(),
                        e.getCause() != null ? e.getCause() : "NULL",
                        e.getMessage(),
                        e
                );
        logger(joinPoint)
                .error(
                        "Exception in {}() with cause = {}",
                        joinPoint.getSignature().getName(),
                        e.getCause() != null ? e.getCause() : "NULL"
                );
    }


    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        Instant start = Instant.now();
        if (true) {
            log.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (true) {
                Instant end = Instant.now();
                log.info("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
                log.info("API Call: {}() executed in = {} ms", joinPoint.getSignature().getName(), getTimeDifference(start,end));
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }

    private Object getTimeDifference(Instant start, Instant end) {
       return  start.until(end, ChronoUnit.MILLIS);
    }
}
