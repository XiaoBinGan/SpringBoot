+ #0 application 文件说明
  
  
  spring-boot框架规定配置文件的名称必须为application.properties或application.yml
  在这个文件中在进行默认配置的修改
  这里将Tomcat默认的端口修改成8090，用来覆盖启动其中jar包的默认配置
  
  ### application.properties
  ```
  server.port=8090
  spring.jdbc.datasource.driverClassName=com.mysql.jdbc.Driver
  spring.jdbc.datasource.url=jdbc:///springboot_01
  spring.jdbc.datasource.username=root
  spring.jdbc.datasource.password=root
  ```
  
  ### application.yml 
  
  ```
  server:
    port: 8090
  
  spring:
    jdbc:
      datasource:
        driverClassName: com.mysql.jdbc.Driver
        url: jdbc:mysql://localhost:3306/springboot_01
        username: root
        password: root
  
  ```
  为了多环境的区分这里使用spring.profiles.active来确定到底命中的是什么什么环境
  为此你可能需要多个申明环境的yml文件，如 application-pro.yml application-dev.yml 
  环境表示用中横线链接。
  ```
  spring:
    profiles:
      active: dev #使用什么环境的文件 application-dev.yml  如果是pro同理application-pro.yml
  ```
  ***注意***
  如果properties和yml文件都存在，不存在spring.profiles.active设置，如有重叠的属性设置，默认以properties的配置同名属性优先，
  如果设置了spring.profiles.active，并且有重叠属性，以active设置优先。
  可以再两种文件中分别增加server.prot属性置顶不同的端口，启动项目项目查看控制台的端口号进行测试。



+ 1.Sprongboot核心作用
   1.1 启动器
   1.2 自动配置
   1.3 健康检查
+ 2.Springboot使用
   2.1继承父工程
   2.2需要引入相关的启动器
   2.3创建main程序，用于启动内置服务器
   2.4定义属性文件或yml文件，用于修改框架默认的配置
+ 3.相关常用启动器：（启动器就是一组相关的jar包）
   spring-boot-starter  核心启动器，包含自动配置、logging、YAML
   spring-boot-starter-thymeleaf 
   spring-boot-starter-test
   spring-boot-starter-actuator  健康监控启动器
   spring-boot-starter-tomcat    内置tomcat启动器
   spring-boot-starter-json
   spring-boot-starter-web  Web启动器：包含RESTFul，SpringMVC，Tomcat
+ 4.常用属性
   server.port  默认8080
   server.servlet.context-path  默认' '
   spring.application.name 
   
+ 5.核心注解
   @SpringBootApplication
   @SpringBootConfiguration（SpringBoot提供） 用与生命配置类。等价于@Configuration（Spring提供）作用。
   @EnableAutoConfiguration 启用自动配置功能。一旦引入相关的启动器，默认相关配置就会自动生效。
   例如：引入spring-boot-starter-web启动器。那么，SpringMVC核心启动器，字符编码过滤器，试图解析器就会自动神效。无需手动配置。
   @ComponentScam  默认扫描主程序所在的包以及子包。以后定义组件包时都存放在主程序所在的包即可被扫描
+ 6.自动初始化的过程：
       5.1、从加载的jar包中查找META-INF/spring.factories文件，构造出一些工厂对象，来初始容器
       5.2、会查找org.springframework.boot.autoconfigure.EnableAutoConfiguration属性，获取127个配置类
       配置类如何生效：
           5.2.1 引入相关启动器
           5.2.2 根据条件注解判断是否生效（@ConditionalOnXxx开头的注解）
+ 7.Bean对象初始化
        @Value 依赖注入，Spring提供，一个个注入，可以使用${}SpringEL表达式
        @Autowired 依赖注入，Spring提供，  先byType再byName
        @Resource 依赖注入，JDK提供，通用。 先byName再ByType
        @ConfigurationProperties(prefix = ""spring.jdbc.datasource") 批量注入
        @EnableConfigurationProperties（value ={DataSourceProperties.class} ）设置相关类生效，即创建对象，初始化
+ 8.8.自定义启动器开发
    8.1开发启动器
    （1）依赖配置
                <parent>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-parent</artifactId>
                    <version>2.7.8</version>
                </parent>
                <properties>
                    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
                    <java.version>1.8</java.version>
                </properties>
            
                <dependencies>
                <!-- 引入spring-boot-starter:所有starter的基本配置 -->
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter</artifactId>
                </dependency>
            
                <!-- 数据库连接池 -->
                <dependency>
                    <groupId>com.alibaba</groupId>
                    <artifactId>druid</artifactId>
                    <version>1.1.12</version>
                </dependency>
                    <!--Lombok的主要作用是通过简化常见的Java代码模式，减少样板代码的编写，提高开发效率，
                    减少代码错误，增加代码的可读性和可维护性。它已经成为许多Java开发人员的常用工具之一，
                    并在许多开源项目和企业应用中广泛使用。-->
            
                    <dependency>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                        <version>1.18.22</version> <!-- 请检查并使用最新的版本 -->
                        <scope>provided</scope>
                    </dependency>
                    <dependency>
                        <groupId>com.mchange</groupId>
                        <artifactId>c3p0</artifactId>
                        <version>0.9.5.5</version>
                    </dependency>
            
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-configuration-processor</artifactId>
                        <optional>true</optional>
                    </dependency>
                </dependencies>
    （2）增加属性类，封装属性配置
                package com.practice.autoconfig;
                import lombok.Getter;
                import lombok.Setter;
                import lombok.ToString;
                import org.springframework.boot.context.properties.ConfigurationProperties;
                import org.springframework.stereotype.Component;
                
                //@Service使用相似，用来做语义的表示
                //@Getter和@Setter注解，以便自动生成属性的getter和setter方法。不再需要手动编写这些方法。
                @Component //自动创建DataSourceProperties这个bean然后提供给其他文件@Autowired注入使用
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
                 
    (3）定义配置类，创建数据源
                package com.practice.autoconfig;
                import com.alibaba.druid.pool.DruidDataSource;
                import org.springframework.beans.factory.annotation.Autowired;
                import org.springframework.boot.SpringBootConfiguration;
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
                    /**
                     * 声明Bean对象，相当于之前在spring-dao.xml配置文件中声明的数据源对象。
                     * 创建Druid数据源Bean
                     * @return Druid数据源
                     */
                    @Bean
                    public DataSource dataSource(){
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

}
    (4）在resources文件下面新建META-INF/spring.factories    
                #Auto Configure
                org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.practice.autoconfig.DataSourceAutoConfiguration 
                做完之后注意执行install，安装项目
                
