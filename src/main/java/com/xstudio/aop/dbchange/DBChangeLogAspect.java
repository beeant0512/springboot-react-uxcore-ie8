package com.xstudio.aop.dbchange;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录数据库字段变更日志
 *
 * @author xiaobiao
 * @version 1
 * @date 2017/9/29
 */

@Aspect
@Component
public class DBChangeLogAspect {

    @Pointcut("@annotation(com.xstudio.aop.dbchange.DBChangeLog)")
    public void pointcut() {
        return;
    }

    //环绕通知方法
    @Around(value = "pointcut()")
    public Object doWriteLog(ProceedingJoinPoint pjp) throws Throwable {
        // 拦截的实体类
        Object target = pjp.getTarget();
        // 拦截的方法名称
        String methodName = pjp.getSignature().getName();
        // 拦截的方法参数
        Object[] args = pjp.getArgs();

        // 拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();

        Object object = null;

        //需要转换成Json的HashMap
        Map<String, Object> maps = new HashMap<>();
        Map<String, Object> parammaps = new HashMap<>();
        // 获得被拦截的方法
        Method method = target.getClass().getMethod(methodName, parameterTypes);
        if (null != method) {
            // 判断是否包含自定义的注解
            if (method.isAnnotationPresent(DBChangeLog.class)) {
                // 获取自定义注解实体
                DBChangeLog myAnnotation = method.getAnnotation(DBChangeLog.class);

                //循环获得所有参数对象
                for (int i = 0; i < args.length; i++) {
                    if (null != args[i]) {
                        parammaps.put("args[" + i + "]", args[i]);
                    } else {
                        parammaps.put("args[" + i + "]", "空参数");
                    }
                }
                maps.put("参数", parammaps);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                maps.put("操作时间", sdf.format(new Date()));
                // 获取服务运行结果
                try {
                    object = pjp.proceed();// 执行该方法
                    maps.put("状态", "成功");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    maps.put("状态", "失败");

                }
            } else { // 没有包含该注解则不进行其他处理
                object = pjp.proceed();// 执行该方法
            }
        } else { // 不需要拦截，直接运行
            object = pjp.proceed(); // 执行该方法
        }
        return object;
    }
}
