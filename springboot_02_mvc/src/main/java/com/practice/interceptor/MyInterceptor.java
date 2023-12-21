package com.practice.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * effect : 这是一个自定义拦截器类，用于在处理HTTP请求之前或之后执行自定义的逻辑。
 * explain:
 * 1. @Component 注解将这个类声明为Spring组件，允许Spring自动扫描和管理它。
 *
 * 2. MyInterceptor 类实现了 HandlerInterceptor 接口，这是Spring MVC中的拦截器接口，包括preHandle，postHandle，afterCompletion方法。
 *
 * 3. preHandle 方法：在请求处理之前执行，可以用于执行拦截器逻辑，例如身份验证、权限检查等。
 *
 * 4. postHandle 方法：在请求处理后，视图渲染之前执行，可以用于对ModelAndView进行修改。
 *
 * 5. afterCompletion 方法：在请求完成后执行，无论是否发生异常都会执行，通常用于资源清理或日志记录。
 */


@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        System.out.println("the preHandle function in execution");
        // 返回 false 表示拦截请求并阻止其继续向下执行；返回 true 表示允许请求继续执行。
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception {
        System.out.println("the postHandle function in execution");
        // 在请求处理后，视图渲染之前执行，可以用于对ModelAndView进行修改。

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                    Exception ex) throws Exception {
        System.out.println("the afterCompletion function in execution");
        // 在请求完成后执行，通常用于资源清理或日志记录。
    }

}
