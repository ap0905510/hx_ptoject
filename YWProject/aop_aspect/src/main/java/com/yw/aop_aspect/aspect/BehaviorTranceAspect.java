package com.yw.aop_aspect.aspect;

import android.util.Log;

import com.yw.aop_aspect.annotation.BehaviorTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 作者：create by YW
 * 日期：2018.03.08 11:29
 * 描述：
 */
@Aspect
public class BehaviorTranceAspect {

    //定义切面的规则
    //1.就在原来应用中哪些注释的地方放到当前切面进行处理
    //execution(注释名   注释用的地方)
    @Pointcut("execution(@com.yw.aop_aspect.annotation.BehaviorTrace * *(..))")
    public void methodAnnotatedWithBehaviorTrace() {}

    //2.对进行切面的内容如何处理
    //advice
    //@Before()  在切入点之前运行
    //@After()   在切入点之后运行
    //@Around()  在切入点前后都运行
    @Around("methodAnnotatedWithBehaviorTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取消息体的各种信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getName(); // 方法名
        Method methodName = signature.getMethod(); //类名 + 方法名
        String simpleName = signature.getDeclaringType().getSimpleName(); //类名
        String funName = signature.getMethod().getAnnotation(BehaviorTrace.class).value();//value "摇一摇"


        //统计时间
        long begin=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long duration=System.currentTimeMillis()-begin;
        Log.e("YW",String.format("功能：%s,%s类的%s方法执行了，用时%d ms",
                funName,simpleName,methodName,duration));
        return result;
    }
}
