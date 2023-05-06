package com.sunkz.starters.log.aop;

import com.google.gson.Gson;
import com.sunkz.starters.log.model.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class LogAspect {

    private final Gson gson = new Gson();
    private final ThreadLocal<Log> tl = new ThreadLocal<>();

    @Pointcut("@annotation(com.sunkz.starters.log.annotation.Log)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Log log = new Log();
        log.setReq((args == null || args.length == 0) ? "" : args);
        tl.set(log);
    }

    @AfterReturning(value = "cut()", returning = "result")
    public void afterReturning(Object result) {
        Log log = tl.get();
        log.setRes(result == null ? "" : result);
    }

    @After("cut()")
    public void after() {
        log.info(gson.toJson(tl.get()));
    }

}
