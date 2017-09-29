package com.xstudio.common.utils;

import com.xstudio.common.AppUserDetails;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author xiaobiao
 * @version 1.0.0 on 2017/4/11.
 */
public class ContextUtil {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        ContextUtil.context = context;
    }

    public static AppUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        AppUserDetails user;
        if (principal instanceof AppUserDetails) {
            user = (AppUserDetails) principal;
        } else {
            user = null;
        }
        return user;
    }

    public static <T> T getBean(Class<T> requiredType){
        return context.getBean(requiredType);
    }
}
