package com.practice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.practice.dao")// @MapperScan 注解告诉 MyBatis 在指定的包中查找 Mapper 接口。Mapper 接口是用于与数据库交互的接口，其中定义了数据库操作的方法。
@EnableTransactionManagement//开启声明式事务，默认开启
public class App {
    public static void main(String[] args){
        SpringApplication.run(App.class,args);
    }
}
