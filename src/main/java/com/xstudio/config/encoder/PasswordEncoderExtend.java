package com.xstudio.config.encoder;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xiaobiao
 * @version 1.0.0 on 2017/6/12.
 */
public interface PasswordEncoderExtend extends PasswordEncoder {
    boolean matches(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);
}
