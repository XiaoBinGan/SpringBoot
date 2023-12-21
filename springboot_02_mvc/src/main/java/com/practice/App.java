package com.practice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;

/**
 * @SpringBootApplication 主要作用
 * @SpringBootConfiguration（Srpingboot提供）用于声明配置类。等价于@Configuration（Spring提供）作用
 * @EnableAutoConfiguration 启用自动配置功能。一旦引入相关的启动器，默认相关的配置就会自动生效
 * 例如：引入spring-boot-starter-web启动器，那么，SpringMVC核心启动器，字符编码过滤器，试图解析器等就会自动生效。无需手动配置
 * @ComponentScan 默认扫描主程序所在的包以及子包，以后定义组件包时都存在主程序所在的包即可被扫码
 * <p>
 * 以上就是SpringBootApplication这个包的大体包含的纾解和功能
 */
@SpringBootApplication
public class App {
    //    启动内置Tomcat服务器，即：初始化IOC容器（采用默认框架）
    //    Tomcat 默认端口配置是8080
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);//args为不定参数传不传无所谓
    }
}
