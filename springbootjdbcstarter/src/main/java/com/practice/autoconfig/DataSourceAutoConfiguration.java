package com.practice.autoconfig;


import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
//javax.sql.DataSource 是 Java 标准库中的一个接口，它定义了一组方法，用于获取数据库连接。javax.sql.DataSource 接口通常用于在Java应用程序中管理数据库连接池，以提高数据库连接的性能和可用性。
/**
 * 配置类：用于创建数据源对象
 */

@SpringBootConfiguration //声明配置类，等价于@Configuration
@EnableConfigurationProperties(value = {DataSourceProperties.class})//代表是会先创建Value等于的class文件 但是用了EnableConfigurationProperties注解，那么被引入的class的@Component注解就不能用了。因为
public class DataSourceAutoConfiguration {

    @Autowired
    DataSourceProperties dataSourceProperties;
//    /**
//     * 声明Bean对象，相当于之前在spring-dao.xml配置文件中声明的数据源对象。
//     * 创建Druid数据源Bean
//     * @return Druid数据源
//     */
//    @Bean
//    public DataSource dataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        // 设置数据库驱动类全限定名
//        dataSource.setDriverClassName(dataSourceProperties.getDriveClassName());
//        // 设置数据库URL
//        dataSource.setUrl(dataSourceProperties.getUrl());
//        // 设置数据库用户名
//        dataSource.setUsername(dataSourceProperties.getUsername());
//        // 设置数据库密码
//        dataSource.setPassword(dataSourceProperties.getPassword());
//        return dataSource;
//    }

    /***
     * createDataSource
     * @return DataSource
     */
    @Bean
//    @ConditionalOnProperty 是 Spring Boot 提供的一个条件注解，用于在满足特定条件时进行配置或装配 Bean。
//    使用者需要提供这个属性配置，才会创建当前Bean对象
    @ConditionalOnProperty( value = "spring.jdbc.datasource.type",havingValue = "druid")
    public DataSource createDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        // 设置数据库驱动类全限定名
        dataSource.setDriverClassName(dataSourceProperties.getDriveClassName());
        // 设置数据库URL
        dataSource.setUrl(dataSourceProperties.getUrl());
        // 设置数据库用户名
        dataSource.setUsername(dataSourceProperties.getUsername());
        // 设置数据库密码
        dataSource.setPassword(dataSourceProperties.getPassword());
        return dataSource;
    }


    /**
     * createC3p0DataSource
     * @return DataSource
     * @throws Exception
     */

    @Bean
    @ConditionalOnProperty(value = "spring.jdbc.datasource.type",havingValue = "c3p0")
    public DataSource createC3p0DataSource() throws Exception {
        ComboPooledDataSource dataSource =new ComboPooledDataSource();
        dataSource.setDriverClass(dataSourceProperties.getDriveClassName());
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUser(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        return  dataSource;
    }
}
