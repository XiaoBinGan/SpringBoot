package com.practice.bean;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


//@Service使用相似，用来做语义的表示
//@Getter和@Setter注解，以便自动生成属性的getter和setter方法。不再需要手动编写这些方法。
@Component //自动创建DataSourceProperties这个bean然后提供给其他文件@Autowired注入使用
@ToString
@Getter
@Setter
//@ConfigurationProperties(prefix = "spring.jdbc.datasource")
//需要配置spring-boot-configuration-processor依赖,用于生成metadata,否则会警告。
@ConfigurationProperties(prefix = "spring.jdbc.datasource")
public class DataSourceProperties {
//@Value注解的使用
// @Value注释可以获取属性配置文件的属性值，缺点：只能单个获取，无需get/set方法，采用的事反射原理，成员变量名称和文件中的属性名称可以不一致
//    @Value("${server.port}")
//    private int serverPort;
//    @Value("${spring.jdbc.datasource.driverClassName}")
//    private String driverClassName;
//    @Value("${spring.jdbc.datasource.url}")
//    private String dataSourceUrl;
//    @Value("${spring.jdbc.datasource.username}")
//    private String dataSourceUsername;
//    @Value("${spring.jdbc.datasource.password}")
//    private String dataSourcePassword;


//@ConfigurationProperties
//采用@ConfigurationProperties注解批量注入属性值，设置属性的前缀即可，属性名称要一致
//    private int serverPort;
    private String driverClassName;
    private String url;
    private String username;
    private String password;


}



//`@Service` 和 `@Component` 是Spring Framework中的注解，用于标识类为Spring容器中的Bean。它们之间的主要区别在于它们的语义和用途：
//
//    1. `@Component` 注解：
//    - `@Component` 是一个通用的Spring组件注解，它用于标识一个类为Spring容器中的Bean。
//    - 它通常用于将任何普通的Java类注册为Spring Bean，例如工具类、辅助类等。
//    - `@Component` 不提供特定的语义，它只是将类纳入Spring的组件扫描和管理中，不关心类的职责。
//    - 因此，`@Component` 用于通用的Bean定义。
//
//    示例：
//    ```java
//@Component
//public class MyComponent {
//    // ...
//}
//```
//
//    2. `@Service` 注解：
//    - `@Service` 是 `@Component` 的一个特化，它用于表示一个类是业务逻辑层（Service层）的Bean。
//    - 它更具有语义性，用于标识类提供了特定的服务或业务逻辑。
//    - `@Service` 注解通常用于标识服务类，这些类负责处理应用程序的业务逻辑。
//    - 当使用Spring的`@Service`注解时，Spring更容易理解这是一个服务层组件，因此更容易进行组织和管理。
//
//    示例：
//    ```java
//@Service
//public class MyService {
//    // ...
//}
//```
//
//    总之，`@Component` 用于通用的Bean，而 `@Service` 更具语义性，用于表示服务层组件。您可以根据您的需求选择使用哪种注解，但通常建议在Service层使用 `@Service` 注解，以提高代码的可读性和可维护性。