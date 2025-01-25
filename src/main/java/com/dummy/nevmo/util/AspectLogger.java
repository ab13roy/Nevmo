package com.dummy.nevmo.util;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {

    static final Logger logger = LoggerFactory.getLogger(AspectLogger.class);

    private long startTime;

    @Before("execution(* demo.service.*.*(..))")
    public void logBefore() {
        logger.info("Service methods invoked");
    }

    @Before("execution(* demo.repository.*.*(..))")
    public void logAround() {
        startTime = System.currentTimeMillis();
        logger.info("Repository methods invoked");
    }

    @After("execution(* demo.repository.*.*(..))")
    public void logAfter() {
        logger.info("Time taken to execute the repository method: {}ms", System.currentTimeMillis() - startTime);
    }
}
