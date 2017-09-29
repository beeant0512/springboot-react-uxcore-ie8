package com.xstudio.config.security;

import com.xstudio.common.AppUserDetails;
import com.xstudio.common.HttpClientUtil;
import com.xstudio.common.Msg;
import com.xstudio.common.enums.EnError;
import com.xstudio.common.utils.ContextUtil;
import com.xstudio.spring.model.LogUserLogin;
import com.xstudio.spring.service.impl.LogUserLoginServiceImpl;
import com.xstudio.spring.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后处理逻辑
 * <p>
 * Created by Beeant on 2017/3/3.
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private String defaultSuccessUrl = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        UserServiceImpl userService = ContextUtil.getBean(UserServiceImpl.class);

        // 获得授权后可得到用户信息   可使用 SUserService 进行数据库操作
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = defaultSuccessUrl;
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
        }
        // 输出登录提示信息
        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");

        response.setHeader("redirect", targetUrl);
        response.setStatus(200);
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        Msg<String> msg = new Msg<>();
        msg.setResult(EnError.DEFAULT);
        msg.setData(targetUrl);
        objectMapper.writeValue(jsonGenerator, msg);

        // 更新最后时间
        userService.updateLastLoginTime(userDetails.getUserId());
        LogUserLogin logUserLogin = new LogUserLogin();
        logUserLogin.setCreateBy(userDetails.getUserId());
        logUserLogin.setAgent(request.getHeader("user-agent"));
        logUserLogin.setIp(HttpClientUtil.getIp(request));
        LogUserLoginServiceImpl logUserLoginService = ContextUtil.getBean(LogUserLoginServiceImpl.class);
        logUserLoginService.insertSelective(logUserLogin);
    }
}
