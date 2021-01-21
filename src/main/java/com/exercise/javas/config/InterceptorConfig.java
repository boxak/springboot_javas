package com.exercise.javas.config;

import com.exercise.javas.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthEmployeeInterceptor())
                .addPathPatterns("/board/wantad/InsertForm")
                .addPathPatterns("/board/wantad/UpdateForm");
        registry.addInterceptor(new AuthEmployerInterceptor())
                .addPathPatterns("/board/jobad/InsertForm")
                .addPathPatterns("/board/jobad/UpdateForm");
        registry.addInterceptor(new AuthLoginInterceptor())
                .addPathPatterns("/board/wantad/InsertForm")
                .addPathPatterns("/board/jobad/InsertForm")
                .addPathPatterns("/board/wantad/UpdateForm")
                .addPathPatterns("/board/jobad/UpdateForm")
                .addPathPatterns("/mypage")
                .addPathPatterns("/afterlogin");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/login");
        registry.addInterceptor(new LogoutInterceptor())
                .addPathPatterns("/logout");
    }
}
