package com.example.demo.config.aop;

import com.example.demo.config.exception.MyException;
import com.example.demo.config.result.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 15:31
 * 描述：
 */
@Slf4j
@Aspect
@Component
public class MyAop {


    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcut1() {
    }

    /**
     * 定义一个拦截的目录
     */
    @Pointcut("execution(* com.example.demo..*.*(..))")
    public void pointcut(){}

    /**
     *开 发 者：连磊
     *开发时间：2018/11/13 15:39
     *方 法 名：doBefore
     *传入参数：[joinPoint]
     *返 回 值：void
     *描    述：前置通知
     **/
    @Before("pointcut1() && pointcut()")
    public void doBefore(JoinPoint joinPoint){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            log.info("pointcut-log: url={}", request.getRequestURI());
            log.info("pointcut-log: method={}", request.getMethod());
            log.info("pointcut-log: args={}", joinPoint.getArgs());
            log.info("pointcut-log: lassMethod={}", methodName);
            log.info("pointcut-log: targetName={}", targetName);
        } catch (Exception e) {

        }
    }


    /**
     *开 发 者：连磊
     *开发时间：2018/11/13 15:41
     *方 法 名：doAround
     *传入参数：[proceedingJoinPoint]
     *返 回 值：void
     *描    述：环绕通知
     **/
    @Around("pointcut1() && pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        }catch (Exception e) {
            if (proceed instanceof MyException) {
                MyException myException = (MyException) e;
                proceed = ResultMessage.setResult(proceed, myException.getMessage() , myException.getCode());
            }
        }
        log.info("执行完成好事，耗时：" + (System.currentTimeMillis()-startTime));
        return proceed;
    }

    @AfterReturning(pointcut = "pointcut1() && pointcut() ", returning = "object")//打印
    public void doAfterReturing(ResultMessage object) {

    }

}
