package com.wico.systemlinkweb.config;

import com.wico.systemlinkweb.interceptor.JwtInterceptor;
import com.wico.systemlinkweb.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;
    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/manufacturer/list")
                .order(1);
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/register", "/manufacturer/list")
                .order(2);
    }
}
