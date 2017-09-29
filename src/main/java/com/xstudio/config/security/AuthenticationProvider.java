package com.xstudio.config.security;

import com.xstudio.common.AppUserDetails;
import com.xstudio.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        logger.debug(passwordEncoder.encode("spring"));
        boolean matches = passwordEncoder.matches(password, appUserDetails.getPassword());
        if (!matches) {
            this.logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
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
