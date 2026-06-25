package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Exercise 3: Logging aspect that measures method execution time.
 * Exercise 8: Full AOP aspect with @Before and @After advice methods.
 *
 * The @Aspect annotation marks this class as an AspectJ aspect.
 * The @Component annotation registers it as a Spring-managed bean
 * so it is picked up by component scanning.
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Pointcut — matches every method inside com.library.service package.
     */
    @Pointcut("execution(* com.library.service.*.*(..))")
    public void serviceLayer() {}

    /**
     * Pointcut — matches every method inside com.library.repository package.
     */
    @Pointcut("execution(* com.library.repository.*.*(..))")
    public void repositoryLayer() {}

    // -------------------------------------------------------
    // Exercise 8: @Before advice — runs BEFORE the method
    // -------------------------------------------------------
    @Before("serviceLayer() || repositoryLayer()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP - BEFORE] Method called: "
                + joinPoint.getSignature().toShortString());
    }

    // -------------------------------------------------------
    // Exercise 8: @After advice — runs AFTER the method (always)
    // -------------------------------------------------------
    @After("serviceLayer() || repositoryLayer()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[AOP - AFTER]  Method finished: "
                + joinPoint.getSignature().toShortString());
    }

    // -------------------------------------------------------
    // Exercise 3: @Around advice — measures execution time
    // -------------------------------------------------------
    @Around("serviceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();   // Execute the actual method

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[AOP - AROUND] " + joinPoint.getSignature().toShortString()
                + " executed in " + elapsed + " ms");

        return result;
    }

    // -------------------------------------------------------
    // Exercise 8: @AfterReturning — logs return value on success
    // -------------------------------------------------------
    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[AOP - RETURNING] " + joinPoint.getSignature().toShortString()
                + " returned: " + result);
    }

    // -------------------------------------------------------
    // Exercise 8: @AfterThrowing — logs exception if one is thrown
    // -------------------------------------------------------
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        System.err.println("[AOP - THROWING] " + joinPoint.getSignature().toShortString()
                + " threw: " + ex.getMessage());
    }
}
