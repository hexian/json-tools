package com.example.json.aop.advice;

import java.lang.reflect.Method;

/**
 * @author hexian
 * 返回通知接口
 */
public interface AfterReturningAdvice extends Advice {
	
    /**
     * 返回后方法
     */
    void afterReturning(Class<?> clz, Object returnValue, Method method, Object[] args) throws Throwable;
    
}