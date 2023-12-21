package com.practice.config;


import com.practice.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，用于注册自定义拦截器。
 *
 * 1. @Configuration 注解指示这是一个Spring配置类。
 *
 * 2. MyConfig 类实现了 WebMvcConfigurer 接口，允许自定义Web MVC配置。
 */


@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Autowired
    MyInterceptor myInterceptor;  // 自动注入 MyInterceptor 实例

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册你定义的拦截器，这里我们的拦截器是 MyInterceptor。
        // 使用 addInterceptor 方法添加拦截器实例，并定义拦截路径和排除路径。
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/user/**")  // 设置拦截路径
                .excludePathPatterns("/user/registry/");  // 设置不拦截的路径
    }
}
