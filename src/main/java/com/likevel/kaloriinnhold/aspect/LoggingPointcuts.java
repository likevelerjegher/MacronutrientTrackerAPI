package com.likevel.kaloriinnhold.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class LoggingPointcuts {
    @Pointcut("execution(* com.likevel.kaloriinnhold.controllers.*.*(..))")
    public void allMethodsFromControllers() {
    }

    @Pointcut("execution(* com.likevel.kaloriinnhold.services.*.*(..))")
    public void allMethodsFromServices() {
    }
}
