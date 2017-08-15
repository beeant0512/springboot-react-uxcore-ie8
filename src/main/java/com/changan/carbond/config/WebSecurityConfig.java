package com.changan.carbond.config;

import com.changan.carbond.config.security.AuthenticationFailureHandler;
import com.changan.carbond.config.security.AuthenticationProvider;
import com.changan.carbond.config.security.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * web安全配置
 *
 * @author Beeant
 * @version 1.0.0 on 2017/3/3.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    /*
     * 登录表单，用户名字段name
     */
    private static String USERNAME = "usr";
    /*
     * 登录表单，密码字段name
     */
    private static String PASSWORD = "pwd";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 自定义验证成功处理器
     *
     * @return AuthenticationSuccessHandler
     */
    @Bean
    public static AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler();
    }

    /**
     * 自定义验证失败处理器
     *
     * @return AuthenticationFailureHandler
     */
    @Bean
    public static AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler();
    }

    /**
     * 自定义用户详情provider
     *
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider customAuthenticationProvider = new AuthenticationProvider();
        customAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return customAuthenticationProvider;
    }


    @Configuration
    @Order(2)
    public static class BaseWebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/**")
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").permitAll()
                    .usernameParameter(USERNAME)
                    .passwordParameter(PASSWORD)
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/favicon.ico", "/static/**");
        }

    }
}
