package com.dream.study01.aop;

import com.dream.study01.error.ErrorCode;
import com.dream.study01.error.ForbiddenException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
public class AdminAspect {

    final String USER_ROLE = "ROLE_ADMIN";

    @Around("@annotation(AdminRights)")
    public Object adminRights(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        if(! request.isUserInRole(USER_ROLE)){
            throw new ForbiddenException("FORBIDDEN", ErrorCode.FORBIDDEN);
        }

        Object proceed = joinPoint.proceed();

        return proceed;
    }

}
