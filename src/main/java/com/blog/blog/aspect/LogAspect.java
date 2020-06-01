package com.blog.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志记录类
 */

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义日志记录的类做所在位置
     */
    @Pointcut("execution(* com.blog.blog.web.*.*(..))")
    public void log(){}

    /**
     * 方法执行前
     *  获取用户访问信息：
     *          url
     *          ip
     *          classMethod
     *          args
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 用户请求的url
        StringBuffer url = request.getRequestURL();

        // 用户ip
        String ip = request.getRemoteAddr();

        // 请求的类名与方法 （格式：com.blog.blog.web.IndexController.index）
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();

        // 用户请求的参数
        Object[] args = joinPoint.getArgs();

        // 日志信息
        RequestLog requestLog = new RequestLog(url.toString(),ip,classMethod,args);

        logger.info("request：{}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("============doAfter=============");
    }

    @AfterReturning(returning="result",pointcut = "log()")
    public void doAfterRuturn(Object result){
        logger.info("Result:{}", result);
    }


    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog() {
        }

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        public String getUrl() {
            return url;
        }

        public String getIp() {
            return ip;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public Object[] getArgs() {
            return args;
        }

        @Override
        public String
        toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
