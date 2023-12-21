package com.practice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


/**
 * @SpringBootApplication 主要作用
 *
 * @SpringBootConfiguration（Srpingboot提供）用于声明配置类。等价于@Configuration（Spring提供）作用
 * @EnableAutoConfiguration 启用自动配置功能。一旦引入相关的启动器，默认相关的配置就会自动生效
 *       例如：引入spring-boot-starter-web启动器，那么，SpringMVC核心启动器，字符编码过滤器，试图解析器等就会自动生效。无需手动配置
 * @ComponentScan   默认扫描主程序所在的包以及子包，以后定义组件包时都存在主程序所在的包即可被扫码
 *
 * 以上就是SpringBootApplication这个包的大体包含的纾解和功能
 *
 */




//@ComponentScan(basePackages = "com.practice")//默认扫码主程序所在包及子包
@SpringBootApplication//申明当前是一个springboot项目
public class App {
//    启动内置Tomcat服务器，即：初始化IOC容器（采用默认框架）
//    Tomcat 默认端口配置是8080
    public static void main(String[] args) {
        /**
         * 自动初始化的过程：
         *    1、从加载的jar包中查找META-INF/spring.factories文件，构造出一些工厂对象，来初始容器
         *    2、会查找org.springframework.boot.autoconfigure.EnableAutoConfiguration名称的值
         *       获取127个配置类
         *           Springboot框架与其他框架的集成，提供大量的配置类。简化框架集成过程。就可以大大提高开发效率
         *           例如：引入spring-boot-starter-web启动器
         *                HttpEncodingAutoConfiguration 自动配置字符编码过滤器。就相当于在web.xml配置的字符编码过滤器，默认UTF-8
         *                MultipartAutoConfiguration    负责创建文件上传解析器
         *                WebMvcAutoConfiguration       负责穿件试图解析器等
         *                DispatcherServletAutoConfiguration  负责创建核心控制器
         *                。。。
         */
        SpringApplication.run(App.class, args);
    }
    @Bean
    public JavaVersion javaVersion() {
        return JavaVersion.EIGHT;
    }

}
/**
     *IOC(Inversion of Control) 即“控制反转”,是Spring框架的核心之一。
     *
     *     它的主要思想是:
     *
     *     将对象创建、依赖的控制权,从程序代码本身反转到外部容器,由容器动态注入对象的依赖。这样可以降低代码之间的耦合度。
     *
     *     IOC的工作流程是:
     *
     *     应用程序将对象类及依赖关系描述提交给IOC容器。
     *     当应用程序需要对象时,从IOC容器中获取,而不用自己创建。
     *     IOC的重要实现方式是依赖注入(DI),主要方式有:
     *
     *     构造器注入:通过构造器传入依赖对象。
     *     Setter注入:通过setXxx方法传入依赖对象。
     *     接口注入:使类实现对应的接口,容器调用接口方法设置依赖。
     *     Spring容器管理对象的生命周期,进行创建、初始化等。开发者只需要描述好对象关系,并从容器获取使用。
     *
     *     IOC提高了代码的灵活性和可维护性,使对象之间解耦,是一个非常重要的设计模式。
     *
     *     以上简要概括了IOC的主要思想和实现方式,如还有疑问可以继续讨论。
 */

