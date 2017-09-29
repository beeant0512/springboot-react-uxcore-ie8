package com.xstudio.config.security;

import com.xstudio.common.Msg;
import com.xstudio.common.enums.EnError;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败自定义返回
 * <p>
 * Created by Beeant on 2017/3/4.
 */
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        Msg<String> msg = new Msg<>();
        msg.setResult(EnError.INVALID_PASSWORD);
        if (exception instanceof UsernameNotFoundException) {
            msg.setResult(EnError.INVALID_USERNAME);
        } else if (exception instanceof LockedException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("用户已被锁定");
        } else if (exception instanceof DisabledException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("用户已被禁用");
        } else if (exception instanceof AccountExpiredException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("登录已过期");
        } else if (exception instanceof CredentialsExpiredException) {
            msg.setResult(EnError.NO_MATCH);
            msg.setMsg("密码错误");
        }

        objectMapper.writeValue(jsonGenerator, msg);
    }
}
