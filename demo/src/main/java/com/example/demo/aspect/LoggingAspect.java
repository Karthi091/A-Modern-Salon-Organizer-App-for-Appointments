package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect

@Component

public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.demo.service.*.*(..))")
    public void logBeforeMethodExecution() {
        logger.info("Method execution started.");
    }

    @AfterReturning("execution(* com.example.demo.service.*.*(..))")
    public void logAfterMethodExecution() {
        logger.info("Method execution completed.");
    }

    @Around("execution(* com.example.demo.service.*.*(..))")
    public Object logAroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Method {} execution started.", joinPoint.getSignature());
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Method {} execution completed in {} ms.", joinPoint.getSignature(), elapsedTime);
        return result;
    }
}
