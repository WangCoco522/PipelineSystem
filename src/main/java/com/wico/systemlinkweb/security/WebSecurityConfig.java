package com.wico.systemlinkweb.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 允许所有用户访问"/verify"
                .authorizeRequests()
                .antMatchers("/verify").permitAll()
                .anyRequest().authenticated()
                .and()
                // 使用表单登录
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                // 允许用户注销
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这里配置认证方式，例如内存认证、数据库认证等
        // 以下是使用内存中的用户进行认证的示例
        auth
                .inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER");
    }
}
