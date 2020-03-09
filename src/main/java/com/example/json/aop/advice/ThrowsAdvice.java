package com.example.json.aop.advice;

import java.lang.reflect.Method;

/**
 * @author hexian
 * 异常通知接口
 */
public interface ThrowsAdvice extends Advice {
	
    /**
     * 异常方法
     */
    void afterThrowing(Class<?> clz, Method method, Object[] args, Throwable e);
    
}