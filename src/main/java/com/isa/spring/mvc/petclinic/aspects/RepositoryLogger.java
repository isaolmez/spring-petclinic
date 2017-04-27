package com.isa.spring.mvc.petclinic.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RepositoryLogger {
    private static final Logger LOG = LoggerFactory.getLogger(RepositoryLogger.class);

    @Pointcut("execution(public * com.isa..data.noooo..*(..))")
    private void anyPublicOperationInRepository() {
    }

    @Around("anyPublicOperationInRepository()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info("Entered: {}", joinPoint.getSignature());
        Object returnValue = joinPoint.proceed();
        LOG.info("Exited: {}", joinPoint.getSignature());
        return returnValue;
    }
}
