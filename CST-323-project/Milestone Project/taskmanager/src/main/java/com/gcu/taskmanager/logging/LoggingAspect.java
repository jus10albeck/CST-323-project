package com.gcu.taskmanager.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect provides centralized application logging
 * for the Task Manager application.
 * 
 * This class uses Spring AOP to automatically log:
 * method entry,
 * successful method exit,
 * and exceptions
 * across controllers, services, and repositories.
 */
@Aspect
@Component
public class LoggingAspect
{
    /**
     * Logger instance used for application logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method entry before execution.
     *
     * @param joinPoint contains method signature information
     */
    @Before("execution(* com.gcu.taskmanager..*(..))")
    public void logMethodEntry(JoinPoint joinPoint)
    {
        logger.info("ENTERING: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    /**
     * Logs successful method completion.
     *
     * @param joinPoint contains method signature information
     */
    @AfterReturning("execution(* com.gcu.taskmanager..*(..))")
    public void logMethodExit(JoinPoint joinPoint)
    {
        logger.info("EXITING: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    /**
     * Logs exceptions thrown by application methods.
     *
     * @param joinPoint contains method signature information
     * @param ex the exception thrown
     */
    @AfterThrowing(
        pointcut = "execution(* com.gcu.taskmanager..*(..))",
        throwing = "ex"
    )
    public void logException(JoinPoint joinPoint, Throwable ex)
    {
        logger.error("EXCEPTION in {}.{}: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getMessage(),
                ex);
    }
}