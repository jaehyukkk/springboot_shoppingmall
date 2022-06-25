package com.dream.study01.aop;

import com.dream.study01.error.ErrorCode;
import com.dream.study01.error.ForbiddenException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Log4j2
@Component
@Aspect
public class UserAspect {

    @Around("@annotation(UserRights)")
    public Object userRights(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        UserRights userRights = method.getAnnotation(UserRights.class);
        String role = userRights.value();

        if(! request.isUserInRole(role)){
            throw new ForbiddenException("권한이 없습니다.", ErrorCode.FORBIDDEN);
        }
        return joinPoint.proceed();
    }
}
