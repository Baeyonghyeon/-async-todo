package com.kurt.asynctodo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.text.NumberFormat;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("execution(* com.kurt.asynctodo.service.*.*(..))")
    public void businessService() {}

    @Around("businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        StringBuilder sb = new StringBuilder();

        sb.append("성능 측정을 시작합니다.\n");
        StopWatch clock = new StopWatch("Profiling for " + pjp.getSignature());
        clock.start(pjp.toShortString());

        Object result = pjp.proceed();

        sb.append("---------------------------------------------\n");
        sb.append("ms       |     Task name\n");
        sb.append("---------------------------------------------\n");

        clock.stop();

        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);

        sb.append(clock.getTotalTimeMillis()).append("  ");
        sb.append(clock.getLastTaskName());
        log.info(String.valueOf(sb));

        return result;
    }
}
