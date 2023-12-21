package com.practice.autoconfig;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Service使用相似，用来做语义的表示
//@Getter和@Setter注解，以便自动生成属性的getter和setter方法。不再需要手动编写这些方法。
//@Component //自动创建DataSourceProperties这个bean然后提供给其他文件@Autowired注入使用
//@EnableConfigurationProperties(value = {DataSourceProperties.class})//代表是会先创建Value等于的class文件 但是用了EnableConfigurationProperties注解，那么被引入的class的@Component注解就不能用了。因为
//因为调用方方会使用@EnableConfigurationProperties这个注解
@ToString
@Getter
@Setter
//需要配置spring-boot-configuration-processor依赖,用于生成metadata,否则会警告。
//@ConfigurationProperties
@ConfigurationProperties(prefix = "spring.jdbc.datasource")//属性由使用启动器的人提供
public class DataSourceProperties {
//采用@ConfigurationProperties注解批量注入属性值，设置属性的前缀即可，属性名称要一致
    private String driveClassName;
    private String url;
    private String username;
    private String password;
}