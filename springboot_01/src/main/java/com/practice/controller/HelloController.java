package com.practice.controller;


import com.practice.bean.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

//组合两个注解 @Controller + @ResponseBody
@RestController
//@EnableConfigurationProperties(value = {DataSourceProperties.class})
//启动属性配置类：带@ConfigurationProperties注解的类要先生效
public class HelloController {
//    使用 @Autowired 注解来注入 DataSourceProperties 对象并访问其属性，可以按照以下方式操作：
//    首先，确保 DataSourceProperties 类上标记了适当的注解（通常是 @Component 或 @Service），以使其成为 Spring 管理的 Bean。
//    在需要使用 DataSourceProperties 的地方，可以直接使用 @Autowired 注解进行注入，就像您所示例的那样：
    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    DataSource DataSource;

    @RequestMapping("/hello")
    public String sayHello(){
        System.out.println("DATA starter"+DataSource.getClass());
        System.out.println(dataSourceProperties.toString());
        return "hello spring boot!";
    }
}
