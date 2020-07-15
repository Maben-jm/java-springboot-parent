package com.maben.aopLog;

import com.alibaba.fastjson.JSON;
import com.maben.aopLog.po.SystemLog;
import com.maben.aopLog.service.SystemLogService;
import com.maben.util.SessionUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;


@Aspect
@Component
public class SystemLogAspect {

    //注入Service用于把日志保存数据库
    @Resource  //这里我用resource注解，一般用的是@Autowired，他们的区别如有时间我会在后面的博客中来写
    private SystemLogService systemLogService;

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    /*
       切点表达式表示执行任意类的任意方法.
       第一个 * 代表匹配任意修饰符及任意返回值,
       第二个 * 代表任意类的对象,
       第三个 * 代表任意方法,
       参数列表中的 ..  匹配任意数量的参数
    */
    //Controller层切点
    @Pointcut("execution (* com..*.controller..*.*(..))")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("==========执行controller前置通知===============");
        if (logger.isInfoEnabled()) {
            logger.info("before " + joinPoint);
        }
    }

    //配置controller环绕通知,使用在方法aspect()上注册的切入点
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        logger.info("==========开始执行controller环绕通知===============");
        long start = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            long end = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            }
            logger.info("==========结束执行controller环绕通知===============");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
        }
        return obj;
    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {

        try {
            SystemLog log = getSyslog(joinPoint);
            if (log == null) {
                return;
            }
            //保存数据库
            systemLogService.insert(log);
            logger.info("=====controller后置通知结束=====");
        } catch (Exception e) {
            e.printStackTrace();
            //记录本地异常日志
            logger.error("==后置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning("controllerAspect()")
    public void afterReturn(JoinPoint joinPoint) {
        logger.info("=====执行controller后置返回通知=====");
        if (logger.isInfoEnabled()) {
            logger.info("afterReturn " + joinPoint);
        }
    }

    /**
     * 异常通知 用于拦截记录异常日志
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {

        StringBuilder params = new StringBuilder();
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                params.append(JSON.toJSONString(joinPoint.getArgs()[i])).append(";");
            }
        }
        try {
            final SystemLog log = getSyslog(joinPoint);
            if (log == null) {
                return;
            }
            //保存数据库
            systemLogService.insert(log);
            logger.info("=====异常通知结束=====");
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());
        }
        /*==========记录本地异常日志==========*/
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params.toString());

    }


    private SystemLog getSyslog(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String operationType = "";
        String operationName = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    final Log annotation = method.getAnnotation(Log.class);
                    if (annotation == null) {
                        return null;
                    }
                    operationType = annotation.operationType();
                    operationName = annotation.operationName();
                    break;
                }
            }
        }
        //*========控制台输出=========*//
        logger.info("=====controller后置通知开始=====");
        logger.info("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()") + "." + operationType);
        logger.info("方法描述:" + operationName);
        logger.info("请求人:" + SessionUtils.getUserName());
        logger.info("请求IP:" + SessionUtils.getIp());
        //*========数据库日志=========*//
        SystemLog log = new SystemLog();
        log.setId(UUID.randomUUID().toString());
        log.setDescription(operationName);
        log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()") + "." + operationType);
        log.setLogType((long) 0);
        log.setRequestIp(SessionUtils.getIp());
        log.setExceptionCode(null);
        log.setExceptionDetail(null);
        log.setParams(null);
        log.setUser(SessionUtils.getUserName());
        log.setCreateTime(new Date());
        return log;
    }
}