package com.changan.carbond.config.security;

import com.changan.carbond.common.AppUserDetails;
import com.changan.carbond.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义用户验证
 * <p>
 * Created by xiaobiao on 2017/3/6.
 */
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        authentication.setAuthenticated(false);
        return super.authenticate(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        AppUserDetails appUserDetails = (AppUserDetails) userDetails;
        String password = (String) authentication.getCredentials();

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        AppUserDetails appUserDetails = userService.selectByUsername(username);
        if (null == appUserDetails) {
            throw new UsernameNotFoundException("Invalid Username: " + username);
        }
        return appUserDetails;
    }
}