
# SpringBoot学习指南，超级全，真的细！挑战全篇无图教会你Java Springboot的使用。


![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6fcd1731224941919f13fe3711d4b4c3~tplv-k3u1fbpfcp-jj-mark:0:0:0:0:q75.image#?w=1024&h=1024&s=5640517&e=gif&f=81&b=291f25)

在当今的软件开发领域中，将前端技能与后端开发相结合是构建全栈应用的趋势。对于前端开发者而言，掌握后端技术是一个有力的补充，而Spring Boot则是构建强大、可伸缩的Java后端应用的首选框架之一。本学习指南将带领你逐步学习Spring Boot，并融合前端开发技能，助你成为一位全栈开发者。



**为什么选择Spring Boot？**

> 学习的理由还要我给你编？要理由直接掏你裆件😏😏😏😏😏😏

**注意：**

> 在学习本篇文章之前请大家先配置好本地的环境变量JAVA，docker，Maven主要的这三块。准备好了就可以开始学习啦。
### 1、创建一个Maven项目


##### Groupid
```linux
com.practice
```
##### artifactId
```linux
springboot_02_mvc
```
### 2、配置pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
​
    <groupId>com.practice</groupId>
    <artifactId>springboot_02_mvc</artifactId>
    <version>1.0-SNAPSHOT</version>
​
    <!--    所有的Spring boot项目都要继承这个父工程，父工程对所有的jar包进行管理-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
    </parent>
    <dependencies>
        <!--问题1    为什么我现在就添加一个启动器依赖，项目就可以运行起来了，运行项目的jar包从何而来
                    因为项目中我们指定了一个父工程，在spring-boot-starter-parent中已经通过Maven的版本锁定了jar包的版本
        -->
​
        <!-- Spring Web启动器 -->
        <!--
             框架提供了很多的启动器（起步依赖），其实就是一组jar包的名称。
             web启动器：引入web开发相关的jar
         -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
​
        <!--Lombok的主要作用是通过简化常见的Java代码模式，减少样板代码的编写，提高开发效率，
        减少代码错误，增加代码的可读性和可维护性。它已经成为许多Java开发人员的常用工具之一，
        并在许多开源项目和企业应用中广泛使用。-->
​
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version> <!-- 请检查并使用最新的版本 -->
            <scope>provided</scope>
        </dependency>
        <!--
          @ConfigurationProperties(prefix = "spring.jdbc.datasource")
          需要配置spring-boot-configuration-processor依赖,用于生成metadata,否则会警告。
        -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
​
    </dependencies>
​
</project>
```

### 3、新建程序入口 Application

`src/main/java/com/practice/App.java`

```java
package com.practice;
​
​
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
​
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
    //    启动内置Tomcat服务器，即：初始化IOC容器（采用默认框架）
    //    Tomcat 默认端口配置是8080
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);//args为不定参数传不传无所谓
    }
}
​
​
```

### 4、新建一个javaBean

`src/main/java/com/practice/pojo/User.java`

```java
package com.practice.pojo; // 声明包名，这个类位于com.practice.pojo包下
​
import lombok.Getter;
import lombok.Setter;
​
import java.io.Serializable;
​
@Getter // 自动生成getter方法
@Setter // 自动生成setter方法
public class User implements Serializable { // 定义一个名为User的类，实现了Serializable接口，表示该对象可以序列化
​
    String username; // 声明一个字符串类型的字段，表示用户名
    String password; // 声明一个字符串类型的字段，表示密码
    Integer age; // 声明一个整数类型的字段，表示年龄
    String sex; // 声明一个字符串类型的字段，表示性别
}
```

### 5、新建一个Usercontroller

`/src/main/java/com/practice/controller/UserController.java`

```java
package com.practice.controller;
​
import com.practice.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
​
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
​
import static org.springframework.web.bind.annotation.RequestMethod.GET;
​
//组合两个注解 @Controller + @ResponseBody
@RestController
//@Controller
@RequestMapping(path ="/user")
public class UserController {
​
    @RequestMapping(path = "/findAll")
//    @ResponseBody 通过消息转换器HttpMessageConverter将返回的Bean 对象转换成json字符串
    public List<User> finAll(HttpServletRequest request){
        List<User> userList = new ArrayList<User>(); // 模拟用户列表
        User user1= new User();
        user1.setUsername("golang");
        user1.setAge(18);
        user1.setPassword("123456");
        user1.setSex("未知");
        userList.add(user1);
        return userList;
    }
​
    /**
     * 这段逻辑我们用来测试后面的http拦截器是否生效
     *  public enum RequestMethod {
     *      GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
     *  }
     */
    @RequestMapping(path = "/registry",method = GET)
    public void registryUser(HttpServletRequest request){
            String requestPath = request.getRequestURI(); // 获取请求的路径
            System.out.println("Request Path: " + requestPath);
            return;
    }
​
}
​
```

运行程序

### 6、静态资源目录

在WEB 开发中我们经常需要引入一些静态资源，例如：HTML，CSS,JS，图片等，如果是普通的项目静态资源可以放在项目的webapp 目录下

现在使用Spring Boot做开发，项目中没有webapp 目录，我们的项目是一个jar 工程，那么就没有 webapp，我们的静态资源该放哪里呢？

请注意 在早期的版本中在springboot 中有一个叫做 ResourceProperties的类，里面就定义了静态资源的默认值

最新的2.7.8版本中（`按两下shift 进入查找WebProperties.class`）

```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
    </parent>
```

已修改为org.springframework.boot.autoconfigure.web.WebProperties

```java
/**
 * {@link ConfigurationProperties Configuration properties} for general web concerns.
 *
 * @author Andy Wilkinson
 * @since 2.4.0
 */
@ConfigurationProperties("spring.web")
public class WebProperties {
  ....
  public static class Resources {
​
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
        "classpath:/resources/", "classpath:/static/", "classpath:/public/" };
    ........
    }
}
```

静态文件放置于resources里的4个目录中即可被自动加载。（这里建议使用static，语义化更清晰一点）

```Linux
.
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   └── resources
│   │       ├── META-INF
│   │       ├── public
│   │       ├── resources
│   │       └── static
│   └── test
│       └── java
└── target
    ├── classes
    │   └── com
    │       └── practice
    └── generated-sources
        └── annotations
​
​
```

尝试在static目录下新增一个hello.html的文件

`/src/main/resources/static/hello.html`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello Srpring-boot-mvc</title>
</head>
<body>
<h1>Hello world </h1>
</body>
</html>
```

服务启动

浏览器访问

```Linux
http://localhost:8080/hello.html
```

显示`hello world`

上面我们说的是Springboot出厂替我们设置好的几个指定的目录。

问题来了那么，如果我们需要自定义目录来存放我们的静态文件，需要怎么操作呢。

上面`org.springframework.boot.autoconfigure.web.WebProperties`中有Springboot为我们设置好的四个；路径，那么需要新增自定义配置目录我们就需要通过我们的`application.properties`或`application.yml`文件中设置对应的属性的来新增我们的可访问的静态目录。

这里我们使用application.yml来配置

`src/main/resources/application.yml`

```yml
spring:
  web:
    resources:
      static-locations: classpath:/webapp/
```

这里我们只需要在resource下新建webapp目录即可实现静态文件通过Tomcat被代理，实现静态文件可被访问。

*请注意，如果自定义的目录生效了，那么Springboot提供的默认目录即会失效！！！！*

### 7、拦截器

在Springboot项目中实现设置拦截器。

1.  编写一个拦截器
1.  通过WebMvcConfigurer注册拦截器

接下来我们一起来编写拦截器的代码

#### 7.1创建自定义拦截器类

这个类应该实现`HandlerInterceptor`接口。

`/src/main/java/com/practice/interceptor/Myinterceptor.java`

```java
package com.practice.interceptor;
​
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
​
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
​
​
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
​
​
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        System.out.println("the preHandle function in execution");
        // 返回 false 表示拦截请求并阻止其继续向下执行；返回 true 表示允许请求继续执行。
        return  false;
    }
​
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception {
        System.out.println("the postHandle function in execution");
        // 在请求处理后，视图渲染之前执行，可以用于对ModelAndView进行修改。
​
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                    Exception ex) throws Exception {
        System.out.println("the afterCompletion function in execution");
        // 在请求完成后执行，通常用于资源清理或日志记录。
    }
​
}
​
```

#### 7.2创建一个配置类

实现`WebMvcConfigurer`接口，用于注册拦截器。

在配置类中覆盖`addInterceptors`方法，然后在该方法中注册你的自定义拦截器。

`/src/main/java/com/practice/config/`

```java
package com.practice.config;
​
​
import com.practice.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
​
/**
 * 配置类，用于注册自定义拦截器。
 *
 * 1. @Configuration 注解指示这是一个Spring配置类。
 *
 * 2. MyConfig 类实现了 WebMvcConfigurer 接口，允许自定义Web MVC配置。
 */
​
​
@Configuration
public class MyConfig implements WebMvcConfigurer {
​
    @Autowired
    MyInterceptor myInterceptor;  // 自动注入 MyInterceptor 实例
​
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册你定义的拦截器，这里我们的拦截器是 MyInterceptor。
        // 使用 addInterceptor 方法添加拦截器实例，并定义拦截路径和排除路径。
​
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/user/**")  // 设置拦截路径
                .excludePathPatterns("/user/registry/");  // 设置不拦截的路径
    }
}
​
```

在上面的示例中，`WebMvcConfig` 是一个配置类，实现了`WebMvcConfigurer`接口。在`addInterceptors`方法中，我们使用`registry.addInterceptor()`方法注册了名为`MyInterceptor`的自定义拦截器，并指定了拦截路径和排除路径。

这样，`MyInterceptor`将会在`/user/**` 路径下的请求中执行，并排除不拦截`/user/registry/`路径下的请求。

记得将`WebMvcConfig`类放在Spring Boot应用程序的包扫描路径下，以便Spring Boot能够自动识别并加载它。一旦你完成了这些步骤，你的自定义拦截器就会在应用程序中生效。

接下来启动项目,发送请求测试是否拦截成功。

```Linux
​
 wujiahao@wujiahao  ~  curl -X GET  http://localhost:8080/user/findAll
  [{"username":"golang","password":"123456","age":18,"sex":"未知"}]% 
 wujiahao@wujiahao  ~  curl -X GET  http://localhost:8080/user/registry    
 wujiahao@wujiahao  ~  
```

通过上面的测试我们可以看到设置的拦截和过滤都生效了。

### 8、SpringBoot整合Spring Data JPA（本章有兴趣可以看看，没兴趣可以略过）

#### 介绍≈废话(你忍一下虽然文件有点大)

Spring Data JPA（Java Persistence API）是Spring Framework的一个子项目，用于简化数据持久化（数据库操作）的开发。它提供了一种更高级别的抽象，使开发者可以更容易地与关系数据库进行交互。Spring Data JPA通过使用注解和接口来定义数据模型和存储库，并自动生成与JPA（Java Persistence API）兼容的数据访问代码。

以下是Spring Data JPA的主要特点和用法：

0.  **简化数据持久化**：Spring Data JPA简化了与数据库的交互，减少了手动编写JPA查询和数据访问代码的工作量。
0.  **自动化查询方法**：Spring Data JPA根据方法命名规则自动生成查询，你只需要定义接口方法，而不需要实现查询逻辑。
0.  **支持JPA标准**：Spring Data JPA与标准JPA兼容，支持JPA注解、Entity管理、持久化上下文等。
0.  **声明式事务管理**：Spring Data JPA可以与Spring的事务管理机制集成，简化了事务处理。
0.  **Repository接口**：Spring Data JPA的核心是Repository接口，你可以创建自定义的Repository接口，并继承`CrudRepository`或`JpaRepository`来获得基本的CRUD操作。
0.  **自定义查询方法**：你可以在Repository接口中声明自定义的查询方法，Spring Data JPA会根据方法名自动生成查询语句。
0.  **动态查询**：Spring Data JPA支持构建动态查询，可以根据运行时条件来生成查询语句。
0.  **分页和排序**：Spring Data JPA提供了分页和排序的支持，使得容易实现分页查询。
0.  **支持不同的数据库**：Spring Data JPA支持多种关系数据库，包括MySQL、PostgreSQL、Oracle等。
0.  **集成Spring Boot**：Spring Data JPA与Spring Boot集成良好，可以轻松配置和使用。

示例使用Spring Data JPA的典型步骤包括：

0.  定义实体类，使用JPA注解来映射数据库表。
0.  创建一个继承`JpaRepository`或`CrudRepository`的Repository接口，定义数据操作方法。
0.  Spring Data JPA会根据方法名自动生成查询语句。
0.  在Spring Boot应用程序中配置数据源和启用Spring Data JPA。
0.  使用Repository接口的方法进行数据库操作。

Spring Data JPA是Spring生态系统中的一个有力工具，用于简化数据持久化的开发工作。它降低了开发者与数据库交互的复杂性，提高了开发效率。

好的，开始进入

#### 正题。

##### 1.我们新建Maven项项目

`com.practice.springboot_jpa`

```xml
    <groupId>com.practice</groupId>
    <artifactId>springboot_jpa</artifactId>
    <version>1.0-SNAPSHOT</version> 
```

##### 2.POM文件依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
​
    <groupId>com.practice</groupId>
    <artifactId>springboot_jpa</artifactId>
    <version>1.0-SNAPSHOT</version>
​
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <!--    所有的Spring boot项目都要继承这个父工程，父工程对所有的jar包进行管理-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
    </parent>
    <dependencies>
        <!-- Spring Web启动器 -->
        <!--
             框架提供了很多的启动器（起步依赖），其实就是一组jar包的名称。
             web启动器：引入web开发相关的jar
         -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
​
        <!--Lombok的主要作用是通过简化常见的Java代码模式，减少样板代码的编写，提高开发效率，
        减少代码错误，增加代码的可读性和可维护性。它已经成为许多Java开发人员的常用工具之一，
        并在许多开源项目和企业应用中广泛使用。-->
​
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version> <!-- 请检查并使用最新的版本 -->
            <scope>provided</scope>
        </dependency>
        <!--
          @ConfigurationProperties(prefix = "spring.jdbc.datasource")
          需要配置spring-boot-configuration-processor依赖,用于生成metadata,否则会警告。
        -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <!--使用自定义启动器，数据源启动器-->
        <dependency>
            <groupId>com.practice</groupId>
            <artifactId>spring-boot-jdbc-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- 单元测试启动器-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--  springboot JPA的初始依赖      -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- MySQL 连接驱动       -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>RELEASE</version>
        </dependency>
        <!--  Redis启动器      -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
    </dependencies>
​
</project>
```

##### 3.新增application.yml配置文件

`src/main/resources/application.yml`

```yml
logging:
  level:
    com.practice.dao: debug #Dao日志等级配置
spring:
  datasource:
    password: 
    username: root
    #数据库连接地址和名称以及编码配置 时区配置
    url: jdbc:mysql://127.0.0.1:3306/java？usenUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver #jdk8默认驱动
  jpa:
    database: mysql #持久化方案采用mysql
    show-sql: true #控制台展示sql语句
    generate-ddl: true #生成ddl语句打印在控制台中
    hibernate:
      ddl-auto: update #表不存在自动创建，表与类一致则不修改，类与表不一致则更新表结构
      naming:     #类使用驼峰命名方式表里可能是有下划线的
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
server:
  port: 9090 #端口号
```

##### 4.新增dao包

`src/main/java/com/practice/dao` （存放数据库交互操作）

##### 5.新增pojo包

`src/main/java/com/practice/pojo`（存放实体类当然有项目中有人起名叫daomian或entity或其他包名但是意在存放实体类）

###### 5.1.新建User.java

`src/main/java/com/practice/pojo/User.java`（User实体类）

```java
package com.practice.pojo;
​
import lombok.Getter;
import lombok.Setter;
​
import javax.persistence.*;
import java.io.Serializable;
​
/**
 * Author :吴佳浩（Alben）
 *
 * effect：
 *
 * use the jap to do the O-R Mapping reflect
 * purpose：可以进行正向的开发，通过实体类映射，可以动态创建表结构。生成DDL语句（Data Definition Language）
 *
 * explain this class:
 *
 * lombok Auto generate the Get and Set function
 * Serializable继承json序列化的方法
 * @Entity  //声明这是一个实体类
 * @Table(name = "jpa_user") //推荐使用jpa提供的注解（javax.persistence.*）声明表  User类和jpa_user做映射
 *  @Id //声明主键
 *  @GeneratedValue(strategy = GenerationType.IDENTITY) //是用于Java持久性编程中的注解，通常与JPA（Java Persistence API）一起使用，用于指定如何生成数据库表中的主键值。等与mysql中的auto_increment;
 *
 *  @GeneratedValue(strategy = GenerationType.IDENTITY) 是用于Java持久性编程中的注解，通常与JPA（Java Persistence API）一起使用，用于指定如何生成数据库表中的主键值。
 *
 *  @GeneratedValue 是一个注解，它用于指示主键生成策略。
 *
 *         strategy = GenerationType.IDENTITY 是注解中的一个参数，它指定了主键生成策略。在这种情况下，GenerationType.IDENTITY 表示使用数据库的自增（或自动增加）功能来生成主键值。
 *
 *         在许多数据库管理系统中，可以配置表的主键列，使其在插入新记录时自动增加。这意味着每次插入新记录时，主键列的值都会自动增加，不需要手动指定主键值。GenerationType.IDENTITY 就是告诉JPA使用数据库的这种自动增加功能来生成主键值。
 *
 *         举例来说，如果你有一个实体类，其中的某个字段被注解为主键，并且使用了 @GeneratedValue(strategy = GenerationType.IDENTITY)，当你将该实体保存到数据库时，主键字段的值将由数据库自动分配，而不需要在代码中明确指定。这对于自动管理主键值以及避免主键冲突非常有用。
 *
 *         需要注意的是，GenerationType.IDENTITY 主要适用于支持自增主键的数据库，如MySQL，但并不适用于所有数据库。在某些数据库中，可能需要使用不同的主键生成策略。
 *
 *  @Column(name = "id",length = 11,unique = true,nullable = false)//@Column(name = "id", length = 11, unique = true, nullable = false)：
 *
 *  这是一个注解，用于指定与数据库表中的列（column）相关的信息。下面是各个参数的解释：name = "id"：指定数据库列的名称为 "id"。这表示在数据库表中将有一个名为 "id" 的列与这个属性进行映射。length = 11：指定了数据库列的长度为 11。
 *
 *  这表示 "id" 列将容纳最大长度为 11 的数据。unique = true：指定 "id" 列的数值必须是唯一的，不允许有重复的值。如果设置为 true，则该列的值在整个表中必须唯一。nullable = false：指定 "id" 列不允许包含空（null）值。
 *
 *  这意味着在插入数据时，必须为 "id" 列提供一个非空的值。
 *
 */
​
​
​
​
@Getter
@Setter
@Entity
@Table(name = "jpa_user")
public class User implements Serializable {
    @Id //声明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //是用于Java持久性编程中的注解，通常与JPA（Java Persistence API）一起使用，用于指定如何生成数据库表中的主键值。等与mysql中的auto_increment;
    @Column(name = "id",length = 11,unique = true,nullable = false)
    private Long id;
    @Column( name = "username",length = 16)
    private String username;
    @Column( name = "password",length = 16)
    private String password;
​
    @Transient //临时字段，无需映射，不生成表字段
    private String age;
    // if you didn't set the column the Framework will Auto reflect the type and name.it's like @Column(name ="name",Length =8)
    private String name;
}
​
```

写到这里我们来看一下当前关于jpa部分的项目结构

```Linux
├── pom.xml
├── springboot_jpa.iml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── practice
│   │   │           ├── dao
│   │   │           └── pojo 
│   │   │               ├── User.java
│   │   │               └── pojo.md
│   │   └── resources
│   │       └── application.yml
│   └── test
│       └── java
└── target
    .......
    └──
​
​
```

##### 6.新增UserDao.java

``src/main/java/com/practice/dao/UserDao.java ``（UserData---- Access Object，DAO）

仅需简单的继承即可获取大部分的CRUD的方法

```java
package com.practice.dao;
​
import com.practice.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
//仅需要继承父接口，JpaRepository<T, ID>父接口中已经提供了CRUD操作，自己无需声明这些方法。
public interface UserDao extends JpaRepository<User,Long> {
}
​
​
```

##### 7.新增service包

`src/main/java/com/practice/service`

###### 7.1定义UserService.java

`src/main/java/com/practice/service/UserService.java`

```java
package com.practice.service;
​
import com.practice.pojo.User;
​
import java.util.List;
​
/**
 * 用户服务接口，定义了对用户信息的基本操作。
 */
@Service
public interface UserService {
​
    /**
     * 获取所有用户信息。
     *
     * @return 包含所有用户信息的列表
     */
    List<User> findUser();
​
    /**
     * 根据用户ID查找特定用户。
     *
     * @param id 用户ID
     * @return 包含用户信息的User对象，如果未找到则返回null
     */
    User findUserById(long id);
​
    /**
     * 保存新用户信息。
     *
     * @param user 包含新用户信息的User对象
     */
    void saveUser(User user);
​
    /**
     * 更新现有用户信息。
     *
     * @param user 包含更新后用户信息的User对象
     */
    void updateUser(User user);
​
    /**
     * 根据用户ID删除特定用户。
     *
     * @param id 要删除的用户ID
     */
    void deleteUerById(Long id);
}
​
```

###### 7.2创建impl目录用来存放server当中的接口实现

`src/main/java/com/practice/service/impl`

###### 7.3创建UserServiceiml.java (实现UserService的接口)

```java

package com.practice.service.impl;
​
import com.practice.dao.UserDao;
import com.practice.pojo.User;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
​
import java.util.List;
​
public class UserServiceimpl implements UserService {
​
​
    @Autowired
    UserDao userDao;
​
​
    @Override
    public List<User> findUser() {
        return userDao.findAll();
    }
​
​
    /**
     * explain :
     * 返回类型不同：
     * * getReferenceById(id)返回的是一个可选的实体对象，该对象可以直接用来进行后续的操作，例如调用其方法或访问其字段等。
     * * findById(id).get()返回的是一个实体对象，但该对象有可能为null，因此在使用前必须先检查其是否为null。
     * 数据库查询方式不同：
     * * getReferenceById(id)并不会立即执行数据库查询，而是在首次访问返回的对象时才去执行查询。
     * 这种方式可以提高性能，因为它减少了不必要的数据库查询。
     * * findById(id).get()则会立即执行数据库查询，并返回查询结果。如果查询结果不存在，则返回null。
     * 综上所述，在选择使用哪种方法时，应根据具体的需求来进行选择。
     * 如果你需要立即得到查询结果，并且不关心性能问题，那么可以使用findById(id).get()；
     * 如果你希望减少不必要的数据库查询，提高性能，那么可以使用getReferenceById(id)。
     * @param id 用户ID
     * @return User
     */
    @Override
    public User findUserById(long id) {
        return userDao.getReferenceById(id);
    }
​
    @Override
    public void saveUser(User user) {
        //user对象主键值为null，进行保存操作
        userDao.save(user);
    }
​
    @Override
    public void updateUser(User user) {
        //user对象含有主键值，进行更新操作
        userDao.save(user);//一个方法两用
    }
​
    @Override
    public void deleteUerById(Long id) {
        userDao.deleteById(id);
    }
}
​
​
```

##### 8.声明controller包

`src/main/java/com/practice/controller` （存放业务接口）

###### 8.1声明UserController.java

`src/main/java/com/practice/controller/UserController.java`

```java

package com.practice.controller;
​
​
import com.practice.pojo.User;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
​
import java.util.List;
​
@RestController //组合两个注解 @Controller + @ResponseBody
@RequestMapping("/user") //the path group name
public class UserController {
​
    @Autowired
    UserService userServiceImpl;
    @RequestMapping("/finAllUser")
    public List<User> finAll(){
        List<User> users =userServiceImpl.findUser();
        return users;
    }
}
​
​
```

##### 9.声明程序主入口

`src/main/java/com/practice/App.java` （程序主入口）

```java

package com.practice;
​
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
​
@SpringBootApplication//申明当前是一个springboot项目
public class App {
       public static void main(String arg[]){
           SpringApplication.run(App.class,arg);
       }
​
}
​
```

当然这里简单的查询和分段式查询由业务层去组装数据是可以使用我们的JPA，复杂的多表查询等不建议使用。

### 9.SpringBoot综合使用

#### 1.项目准备

##### 1.1 数据库环境搭建

```sql
CREATE DATABASE springboot CHARACTER SET utf8;
USE springboot;
​
CREATE TABLE `springboot_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `gender` VARCHAR(5) DEFAULT NULL,
  `age` INT(11) DEFAULT NULL,
  `address` VARCHAR(32) DEFAULT NULL,
  `qq` VARCHAR(20) DEFAULT NULL,
  `email` VARCHAR(50) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `phone` VARCHAR(11) DEFAULT NULL,
  `password` VARCHAR(255) NOT NULL,  -- Assuming a VARCHAR length of 255 for the password
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
​
INSERT INTO springboot_user (id, name, gender, age, address, phone, email, username, password)
VALUES 
  (1, '马1蓉', '女', 38, '绿岛', '11111111111', 'marong222@qq.com', 'marong', 'marong121233'),
  (2, '马斯克2', '男', 58, '湖北省武汉市', '22222222222', 'elmasike@qq.com', 'masike', 'masike121233'),
  (3, '雷纳兹托瓦斯3', '男', 18, '湖北省荆门市', '33333333333', 'lnztws@qq.com', 'lnztws', 'lnztws121233'),
  (4, '黄仁勋4', '男', 30, '扬州', '44444444444', 'huanrenxun@qq.com', 'huangrenxun', 'huangrenxun23123');
​
```

#### 1.2项目初始化

`com.practice`

`springboot_practice`(maven 创建)

当前的目录结构

```Linux
├── pom.xml
├── springboot_practice.iml
└── src
    ├── main
    │   ├── java
    │   └── resources
    └── test
        └── java
​
```

#### 1.3修改pom文件添加我们需要的依赖

`pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
​
    <groupId>com.practice</groupId>
    <artifactId>springboot_practice</artifactId>
    <version>1.0-SNAPSHOT</version>
​
    <!-- Parent -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
    </parent>
​
    <!-- Dependencies -->
    <dependencies>
        <!-- Unit Testing Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
​
        <!-- Common Mapper Starter -->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>2.1.5</version> <!-- Use the latest version -->
        </dependency>
​
        <!-- JDBC Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
​
        <!-- MySQL Driver -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.23</version> <!-- Use the latest version -->
        </dependency>
​
        <!-- Druid Starter -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.17</version> <!-- Use the latest version -->
        </dependency>
​
        <!-- Web Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
​
        <!-- Spring Boot Actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
​
        <!-- Apache Commons Lang3 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version> <!-- Use the latest version -->
        </dependency>
​
        <!--Lombok的主要作用是通过简化常见的Java代码模式，减少样板代码的编写，提高开发效率，
        减少代码错误，增加代码的可读性和可维护性。它已经成为许多Java开发人员的常用工具之一，
        并在许多开源项目和企业应用中广泛使用。-->
​
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version> <!-- 请检查并使用最新的版本 -->
            <scope>provided</scope>
        </dependency>
        <!-- Spring Boot DevTools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>
​
```

#### 1.4依赖说明

1.  Springboot父工程

1.  单元测试启动器

1.  通用mapper启动器

    `mapper-spring-boot-starter` 是通用 Mapper 项目提供的 Spring Boot 启动器，用于简化 MyBatis 与通用 Mapper 的集成。通用 Mapper 是一个用于简化 MyBatis 开发的开源项目，它可以通过简单的配置来实现通用的 CRUD（Create, Read, Update, Delete）操作，避免手动编写重复的 SQL 语句。

    以下是 `mapper-spring-boot-starter` 的主要作用：

    1.  **简化 MyBatis 配置：** 通过引入 `mapper-spring-boot-starter`，你可以更简单地配置 MyBatis，不再需要手动配置 MyBatis 的 Mapper 接口和 XML 映射文件。
    1.  **集成通用 Mapper：** 通用 Mapper 提供了通用的 CRUD 操作，可以通过简单的继承和注解配置来实现对数据库表的增、删、改、查等操作，无需手动编写 SQL。
    1.  **提供通用的查询方法：** 通用 Mapper 提供了一系列通用的查询方法，如根据主键查询、条件查询、分页查询等，简化了数据访问的操作。
    1.  **支持动态 SQL：** 通用 Mapper 支持使用动态 SQL 进行查询，可以根据不同的条件生成不同的 SQL 语句，提高了灵活性。

    为了使用 `mapper-spring-boot-starter`，你需要在项目的 `pom.xml` 文件中添加相应的依赖：

    ```xml
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper-spring-boot-starter</artifactId>
        <version>2.1.5</version> <!-- 使用最新版本 -->
    </dependency>
    ```

    然后，在你的实体类上使用通用 Mapper 提供的注解，例如 `@Id`、`@Table`，并创建继承自通用 Mapper 的接口，即可完成对数据库表的 CRUD 操作，无需手动编写 SQL 语句。

    ```java

    import javax.persistence.Id;
    import javax.persistence.Table;
    ​
    @Table(name = "your_table")
    public class YourEntity {
        
        @Id
        private Long id;
        
        // other fields, getters, setters...
    }
    ```

    ```java

    import tk.mybatis.mapper.common.Mapper;
    ​
    public interface YourEntityMapper extends Mapper<YourEntity> {
        // additional methods if needed
    }
    ```

    通过引入 `mapper-spring-boot-starter`，你可以更加方便地使用通用 Mapper 进行数据库操作，提高了开发效率，并减少了对 MyBatis 的配置工作。

1.  JDBC启动器

    `spring-boot-starter-jdbc` 是 Spring Boot 中用于简化 JDBC 开发的启动器。它提供了 JDBC 相关的基础配置，让开发者能够更轻松地使用 Spring JDBC 访问数据库。

    以下是 `spring-boot-starter-jdbc` 的主要作用：

    1.  **数据源的自动配置：** 该启动器自动配置了 Spring 的数据源（`DataSource`），使得开发者不需要手动配置数据源，减少了繁琐的配置工作。
    1.  **Spring JDBC 的核心功能：** `spring-boot-starter-jdbc` 集成了 Spring JDBC 模块，使得你能够方便地使用 Spring 提供的 JdbcTemplate 等核心功能，简化了 JDBC 操作。
    1.  **事务管理：** 该启动器还自动配置了 Spring 的事务管理，使得在使用 Spring JDBC 进行数据库操作时，可以方便地开启、提交、回滚事务。
    1.  **支持嵌入式数据库：** 如果你的应用是基于嵌入式数据库（如 H2、HSQLDB）的，该启动器也会自动配置相应的数据源，让开发者能够无缝切换不同的数据库。

    以下是一个简单的 `spring-boot-starter-jdbc` 的使用示例：

    ```xml
    <!-- 在 pom.xml 中添加依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    ```

    然后，你可以在 Spring Boot 的配置文件（`application.properties` 或 `application.yml`）中添加数据库相关的配置，例如：

    ```YAML
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/mydatabase
        username: root
        password: password
        driver-class-name: com.mysql.cj.jdbc.Driver
    ```

    在代码中，你可以使用 `JdbcTemplate` 进行数据库操作，而不必手动配置数据源和处理数据库连接的开启和关闭。Spring Boot 会根据你的配置自动完成这些工作，让你的代码更简洁、更易于维护。

1.  mysql驱动

    `mysql-connector-java` 是 MySQL 官方提供的 MySQL 数据库连接器的 Java 实现，也就是 MySQL 驱动。在使用 Java 进行与 MySQL 数据库的交互时，你需要引入这个驱动，以便能够在 Java 应用程序中连接和操作 MySQL 数据库。

    以下是 `mysql-connector-java` 的主要作用：

    1.  **数据库连接：** 提供了与 MySQL 数据库建立连接的能力。通过这个驱动，Java 应用程序可以与 MySQL 数据库建立连接，并进行数据的读写操作。
    1.  **JDBC 接口实现：** `mysql-connector-java` 实现了 JDBC（Java Database Connectivity）接口，这是 Java 标准库提供的用于与关系型数据库进行交互的 API。通过 JDBC，Java 应用程序可以使用标准的 SQL 语句执行查询、更新等数据库操作。
    1.  **支持连接池：** `mysql-connector-java` 本身不包含连接池功能，但可以与连接池整合使用，例如与 `Druid` 连接池结合，以提高数据库连接的效率和性能。

    为了在 Maven 项目中使用 `mysql-connector-java`，你需要在项目的 `pom.xml` 文件中添加相应的依赖：

    ```xml
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.23</version> <!-- 使用最新版本 -->
    </dependency>
    ```

    在引入这个依赖之后，你就可以在 Java 代码中使用 JDBC 连接 MySQL 数据库。通常，你需要提供数据库的连接信息，包括数据库 URL、用户名和密码。以下是一个简单的连接示例：

    ```java
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;
    ​
    public class MySqlConnectionExample {
    ​
        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/your_database";
            String username = "your_username";
            String password = "your_password";
    ​
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                // 连接成功，可以进行数据库操作
                System.out.println("Connected to the database!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    ```

    这里，`jdbc:mysql://localhost:3306/your_database` 是数据库连接 URL，`your_username` 和 `your_password` 是数据库的用户名和密码。连接成功后，你可以在 `try` 块中执行你的数据库操作。

1.  druid启动器

    `druid-spring-boot-starter` 是阿里巴巴开源的连接池项目 Druid 集成到 Spring Boot 中的启动器。Druid 是一个高效、功能强大且可扩展的数据库连接池，它提供了许多有用的特性，包括连接池监控、SQL防火墙、性能监控等。

    使用 `druid-spring-boot-starter` 的主要作用有以下几点：

    1.  **数据库连接池：** Druid 提供了高效的数据库连接池，能够有效地管理数据库连接，提高数据库访问性能。
    1.  **监控和统计：** Druid 集成了强大的监控和统计功能，可以方便地监控数据库连接池的状态，包括活动连接数、空闲连接数、执行 SQL 的次数等，通过监控数据，可以更好地了解数据库的使用情况和性能状况。
    1.  **SQL 防火墙：** Druid 提供了 SQL 防火墙功能，可以防止恶意 SQL 注入攻击，保障数据库的安全性。
    1.  **配置灵活：** 通过在 Spring Boot 项目中引入 `druid-spring-boot-starter`，可以使用 Spring Boot 的自动配置功能，减少了繁琐的配置，让配置更为简单。
    1.  **性能监控：** Druid 提供了 Web 界面，可以直观地查看数据库连接池的运行情况和 SQL 的执行情况，有助于识别潜在的性能问题。

    要使用 `druid-spring-boot-starter`，只需在你的 Spring Boot 项目中添加相应的依赖，并在配置文件中配置数据源相关信息。以下是一个简单的示例：

    ```
    <!-- 在 pom.xml 中添加依赖 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.1.17</version> <!-- 使用最新版本 -->
    </dependency>
    ```

    然后，在 `application.properties` 或 `application.yml` 中配置 Druid 数据源的相关信息，例如：

    ```
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/mydatabase
        username: root
        password: password
        driver-class-name: com.mysql.cj.jdbc.Driver
        type: com.alibaba.druid.pool.DruidDataSource
        # 其他 Druid 配置可以在这里添加
        druid:
          initial-size: 5
          min-idle: 5
          max-active: 20
          max-wait: 60000
          time-between-eviction-runs-millis: 60000
          validation-query: SELECT 1 FROM DUAL
          test-while-idle: true
          test-on-borrow: false
          test-on-return: false
          pool-prepared-statements: true
          max-pool-prepared-statement-per-connection-size: 20
    ```

    以上是一个简单的配置示例，你可以根据实际需求进行更详细的配置。希望这些信息能够帮助你理解 `druid-spring-boot-starter` 的作用。

1.  Web启动器

1.  spring boot Actuator依赖

    `spring-boot-starter-actuator` 是 Spring Boot 提供的一个用于生产环境监控和管理的模块。它为 Spring Boot 应用程序提供了一组内置的、用于监视和管理应用程序的端点（endpoints）。这些端点包括健康检查、信息展示、环境属性、配置信息、日志等，允许你监控应用程序的运行状况、收集信息和进行一些管理操作。

    以下是 `spring-boot-starter-actuator` 的一些主要功能和作用：

    1.  **健康检查（Health Endpoint）：** 提供 `/actuator/health` 端点，用于检查应用程序的健康状况。这对于在生产环境中监控应用程序的运行状态非常有用。
    1.  **信息展示（Info Endpoint）：** 提供 `/actuator/info` 端点，用于展示应用程序的信息，例如版本信息、自定义属性等。
    1.  **环境属性（Environment Endpoint）：** 提供 `/actuator/env` 端点，用于查看应用程序的环境属性，包括配置属性、系统属性等。
    1.  **配置信息（Config Props Endpoint）：** 提供 `/actuator/configprops` 端点，用于查看应用程序中所有的配置属性。
    1.  **日志管理（Loggers Endpoint）：** 提供 `/actuator/loggers` 端点，允许动态更改应用程序的日志级别，方便在运行时调整日志输出。
    1.  **线程信息（Thread Dump Endpoint）：** 提供 `/actuator/threaddump` 端点，用于获取线程的堆栈跟踪，方便定位应用程序中的线程问题。
    1.  **性能监控（Metrics Endpoint）：** 提供 `/actuator/metrics` 端点，用于查看应用程序的性能指标，例如内存使用、线程池状态等。
    1.  **应用程序状态（Application Endpoint）：** 提供 `/actuator/application` 端点，用于查看应用程序的运行时信息，例如应用程序的名称、管理端口等。

    通过引入 `spring-boot-starter-actuator`，你可以在运行时更方便地监控和管理你的 Spring Boot 应用程序。这对于生产环境中的运维工作、故障排查以及性能优化都是非常有帮助的。在生产环境中，你可以选择开启或关闭特定的端点，以保障安全性。

1.  编码工具包 common3-lang3

    `commons-lang3` 是 Apache Commons Lang 库的第三个版本，是一个为 Java 提供通用工具类和基础库的开源项目。它包含了一系列实用的工具方法，用于简化 Java 编程中的一些常见任务，例如字符串处理、数组操作、日期和时间处理、异常处理等。

    以下是 `commons-lang3` 的一些主要功能和作用：

    1.  **字符串处理：** 提供了一系列用于处理字符串的工具方法，包括字符串截取、拼接、替换、大小写转换等。

        ```java
        String result = StringUtils.substring("Hello, World!", 0, 5);  // 截取字符串
        ```

    1.  **数组操作：** 提供了一些方便的数组操作方法，包括数组的比较、查找、复制等。

        ```java
        boolean isEqual = ArrayUtils.isEquals(array1, array2);  // 比较数组是否相等
        ```

    1.  **日期和时间处理：** 提供了一些用于日期和时间操作的工具方法，如格式化日期、计算日期差等。

        ```java
        String formattedDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");  // 格式化日期
        ```

    1.  **异常处理：** 提供了一些关于异常的工具方法，如获取异常堆栈信息、判断异常类型等。

        ```java
        String stackTrace = ExceptionUtils.getStackTrace(exception);  // 获取异常堆栈信息
        ```

    1.  **其他实用工具方法：** 还包括其他一些实用的工具方法，如随机数生成、系统属性获取、反射操作等。

        ```java
        String javaVersion = SystemUtils.JAVA_VERSION;  // 获取Java版本信息
        ```

    通过引入 `commons-lang3` 依赖，你可以在项目中直接使用这些实用的工具方法，避免了重复实现一些常见的功能，提高了代码的可读性和可维护性。此外，这个库经过广泛使用和测试，是 Java 开发中常用的工具库之一。在开发中，如果遇到需要处理字符串、数组、日期等常见操作的场景，使用 `commons-lang3` 可以帮助你更高效地完成任务。

1.  热部署 spring-boot-devtools

    `spring-boot-devtools` 是 Spring Boot 提供的一个用于开发环境的工具，旨在提高开发者的开发体验。它为开发者提供了一些功能，使得在开发阶段进行代码修改后能够更快速地进行应用程序重启，以便查看修改的效果，从而加速开发周期。

    以下是 `spring-boot-devtools` 的一些主要功能和作用：

    1.  **自动重启应用程序：** `spring-boot-devtools` 允许在开发阶段对应用程序进行热部署。当你修改了项目的源代码、类路径资源或静态资源文件时，应用程序将会自动进行重启，而无需手动停止和启动。
    1.  **禁用模板缓存：** 对于使用模板引擎的项目（如 Thymeleaf、FreeMarker 等），`spring-boot-devtools` 会自动禁用模板缓存，以确保每次修改模板文件后都能立即生效。
    1.  **开发者工具页面：** 在应用程序启动后，`spring-boot-devtools` 还提供了一个开发者工具页面，你可以通过访问 `http://localhost:8080/actuator` 查看应用程序的热部署状态，包括热部署类的触发、重启次数等信息。
    1.  **远程调试支持：** `spring-boot-devtools` 允许你在开发模式下远程连接到应用程序，进行调试和查看应用程序的内部状态。

    为了使用 `spring-boot-devtools`，你需要在项目的 `pom.xml` 文件中添加相应的依赖：

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    ```

    需要注意的是，`<optional>true</optional>` 的配置表示这个依赖是可选的，它只在开发环境下起作用。在生产环境中，这个依赖不会被引入，以避免潜在的性能问题。

    总的来说，`spring-boot-devtools` 是一个在开发阶段提高效率的工具，能够减少代码修改后的重启时间，帮助开发者更快速地迭代和调试应用程序。

1.  Lombok 依赖

    Lombok 是一个 Java 库，旨在通过简化常见的 Java 代码模式，减少样板代码的编写，提高开发效率，减少代码错误，增加代码的可读性和可维护性。它通过自动为 Java 类生成一些常见的方法（如 Getter、Setter、toString 等），以及通过注解简化一些常见的设计模式，来达到这个目的。

    具体而言，以下是 Lombok 的一些主要作用：

    0.  **自动生成 Getter 和 Setter 方法：** 使用 `@Getter` 和 `@Setter` 注解，可以自动生成字段的 Getter 和 Setter 方法，减少冗余的样板代码。

        ```java
        @Getter @Setter
        private String name;
        ```

    0.  **自动生成构造方法：** 使用 `@AllArgsConstructor` 和 `@NoArgsConstructor` 注解，可以自动生成全参和无参构造方法。

        ```java
        @AllArgsConstructor
        public class Example {
            private String field1;
            private int field2;
        }
        ```

    0.  **自动生成 `toString` 方法：** 使用 `@ToString` 注解，可以自动生成 `toString` 方法。

        ```java
        @ToString
        public class Example {
            private String field1;
            private int field2;
        }
        ```

    0.  **自动生成 `hashCode` 和 `equals` 方法：** 使用 `@EqualsAndHashCode` 注解，可以自动生成 `hashCode` 和 `equals` 方法。

        ```java
        @EqualsAndHashCode
        public class Example {
            private String field1;
            private int field2;
        }
        ```

    0.  **简化日志记录：** 使用 `@Slf4j` 注解，可以自动生成一个 SLF4J 的日志变量，避免手动创建 `private static final Logger` 字段。

        ```java
        @Slf4j
        public class Example {
            public void exampleMethod() {
                log.info("Logging with Lombok!");
            }
        ```

    0.  **其他注解：** Lombok 还提供了其他一些注解，如 `@Data`（相当于 `@Getter @Setter @ToString @EqualsAndHashCode` 的组合）、`@Builder`（用于构建者模式）等，可以根据需要选择使用。

    总体而言，Lombok 的目标是简化 Java 代码，提高开发效率，并且它已经在许多 Java 项目中广泛应用。

1.  build Maven打包

    这 Maven 配置片段是用于配置 Spring Boot 项目的 Maven 插件，主要作用是将项目打包成可执行的 JAR 文件。具体来说，这段配置使用了 Spring Boot 提供的 `spring-boot-maven-plugin` 插件，它提供了一些特定于 Spring Boot 项目的功能，例如创建可执行 JAR 文件、自动重新加载等。

    以下是这个配置片段的主要作用：

    0.  **打包成可执行的 JAR 文件：** 通过配置 `spring-boot-maven-plugin` 插件，Maven 在构建项目时将会执行插件的 `repackage` 目标。这个目标会重新打包项目，将所有依赖和项目的类文件打包到一个可执行的 JAR 文件中。

        ```xml
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>
        ```

        使用 Maven 命令进行项目构建，生成可执行 JAR 文件：

        ```linux
        mvn clean package
        ```

    0.  **支持自动重新加载（可选）：** 这个配置片段并未显式启用自动重新加载，但默认情况下，`spring-boot-maven-plugin` 插件会在开发模式下启用自动重新加载。在开发阶段，你可以使用 `mvn spring-boot:run` 命令启动应用程序，并在代码变更时自动重新启动应用。

        ```xml
        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <configuration>
                        <fork>true</fork>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        ```

        使用 Maven 命令启动应用程序，支持自动重新加载：

        ```linux
        mvn spring-boot:run
        ```

    总之，这段 Maven 配置的主要目的是使用 `spring-boot-maven-plugin` 插件将 Spring Boot 项目打包成可执行的 JAR 文件，方便部署和运行。

#### 2.项目主文件入口定义

`/src/main/java/com/practice/App.class`

```java

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
```

#### 3.定义application.yml配置文件

`/src/main/resources/application.yml`

```yaml
# 服务器端口配置
server:
  port: 9090
​
# Spring 数据源配置
spring:
  datasource:
    # 数据库连接 URL
    url: jdbc:mysql://127.0.0.1:3306/springboot
    # MySQL 驱动类
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 数据库用户名
    username: root
    # 数据库密码
    password:
    # 数据源类型（这里使用了 Druid 数据源）
    type: com.alibaba.druid.pool.DruidDataSource
​
# MyBatis 配置
mybatis:
  # MyBatis 类型别名包扫描路径，用于扫描指定包下的类作为 MyBatis 实体类
  type-aliases-package: com.practice.pojo
​
```

#### 4.创建pojo包

`src/main/java/com/practice/pojo`(存放实体类)

#### 5.创建User 实体类

`src/main/java/com/practice/pojo/User.java`

```java

package com.practice.pojo;
​
​
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
​
import javax.persistence.*;
import java.io.Serializable;
​
/**
 * @Entity 注解声明这是一个 JPA 实体类。
 * @Table 注解用于映射表名。
 * @Id 和 @GeneratedValue 注解标识主键，并指定主键的生成策略。
 * @Column 注解用于映射数据库表字段，包括字段名、是否可为空、长度等属性。
 * @Getter 和 @Setter 注解由 Lombok 自动生成 Getter 和 Setter 方法。
 * @ToString 自动生成 toString 方法
 * Serializable 接口是一个标记接口，表示该类的对象可以被序列化。
 * 根据实际需要，你还可以添加构造方法、toString 方法，以及重写 equals 和 hashCode 方法。这取决于你是否需要在比较对象时使用这些方法。
 * 在这个示例中除了主键之外可指定也可不指定@Column
 */
@ToString
@Getter
@Setter
@Entity
@Table(name = "springboot_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
​
    @Column(name = "name", nullable = false, length = 20)
    private String name;
​
    @Column(name = "gender", length = 5)
    private String gender;
​
    @Column(name = "age")
    private Integer age;
​
    @Column(name = "address", length = 32)
    private String address;
​
    @Column(name = "qq", length = 20)
    private String qq;
​
    @Column(name = "email", nullable = false, length = 50)
    private String email;
​
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;
​
    @Column(name = "phone", length = 11)
    private String phone;
​
    @Column(name = "password", nullable = false, length = 255)
    private String password;
​
​
}
​
```

#### 6.创建dao包

`src/main/java/com/practice/dao`

#### 7.Mapper接口的映射和配置

`src/main/java/com/practice/dao/UserMapper.java`

```java

package com.practice.dao;
​
​
import com.practice.pojo.User;
import tk.mybatis.mapper.common.Mapper;
/**
 * 用户数据访问层接口
 * 通过 Mapper 框架，在 MyBatis 基础上进行封装，可以动态生成 SQL 语句，提供基本 CRUD 操作
 * 通过继承 Mapper 框架提供的 Mapper 接口，实现了对 User 实体的基本数据库操作
 * @Mapper 接口是 MyBatis 提供的用于实现数据库操作的接口。
 * UserMapper 继承了 tk.mybatis.mapper.common.Mapper 接口，它是通用 Mapper 的基础接口，提供了一些常见的数据库操作方法。
 * 注释中强调了通过继承 Mapper 接口，可以动态生成 SQL 语句，并实现了基本的 CRUD（Create, Read, Update, Delete）操作，减少了手动编写 SQL 语句的工作。
 * UserMapper 接口主要用于定义对 User 实体的数据库操作方法，而具体的实现由 MyBatis 的通用 Mapper 框架负责。
 */
public interface UserMapper extends Mapper<User> {
}
​
```

#### 8.创建Test,查看是否成功继承通用Mapper的方法

`/src/test/java/com/practice/UserMapperTest.java`

```java

package com.practice;
​
​
import com.practice.dao.UserMapper;
import com.practice.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
​
import java.util.List;
​
/**
 * @RunWith(SpringRunner.class) 和 @SpringBootTest 注解用于在测试环境中启动 Spring Boot 应用上下文。
 * @Autowired 注解用于将 UserMapper bean 注入到测试类中。
 * @Test 注解标记了测试方法，测试方法调用了 userMapper.selectAll() 查询所有用户，并打印了查询结果。
 * 请确保 UserMapper 接口定义了正确的查询方法，且 MyBatis 的配置正确。此外，检查测试类所在的包路径是否能够正确扫描到 UserMapper 接口。运行该测试类时，你应该能够看到查询结果输出到控制台。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
​
    @Autowired
    UserMapper userMapper;//如果 Error 提示：“Could not autowire. No beans of"UserMapper' type found. more... (SB F1) ”无需搭理直接运行即可
​
    @Test
    public void testFindAll(){
        List<User> list =userMapper.selectAll();
        for (User user:list
             ) {System.out.println("user"+user.getId()+"="+user);
        }
    }
}
​
```

执行上面文件后Terminal输出：

```linux
​
  .   ____          _            __ _ _
 /\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )___ | '_ | '_| | '_ / _` | \ \ \ \
 \/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |___, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.8)
​
2023-11-15 16:05:19.833  INFO 82708 --- [           main] com.practice.UserMapperTest              : Starting UserMapperTest using Java 1.8.0_121 on wujiahao with PID 82708 (started by wujiahao in /Users/wujiahao/studydemo/JAVA/springboot_03_practice)
2023-11-15 16:05:19.835  INFO 82708 --- [           main] com.practice.UserMapperTest              : No active profile set, falling back to 1 default profile: "default"
2023-11-15 16:05:21.229  INFO 82708 --- [           main] c.a.d.s.b.a.DruidDataSourceAutoConfigure : Init DruidDataSource
2023-11-15 16:05:21.428  INFO 82708 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
2023-11-15 16:05:23.021  INFO 82708 --- [           main] t.m.m.autoconfigure.MapperCacheDisabler  : Clear tk.mybatis.mapper.util.MsUtil CLASS_CACHE cache.
2023-11-15 16:05:23.022  INFO 82708 --- [           main] t.m.m.autoconfigure.MapperCacheDisabler  : Clear tk.mybatis.mapper.genid.GenIdUtil CACHE cache.
2023-11-15 16:05:23.023  INFO 82708 --- [           main] t.m.m.autoconfigure.MapperCacheDisabler  : Clear tk.mybatis.mapper.version.VersionUtil CACHE cache.
2023-11-15 16:05:23.024  INFO 82708 --- [           main] t.m.m.autoconfigure.MapperCacheDisabler  : Clear EntityHelper entityTableMap cache.
2023-11-15 16:05:23.040  INFO 82708 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint(s) beneath base path '/actuator'
2023-11-15 16:05:23.120  INFO 82708 --- [           main] com.practice.UserMapperTest              : Started UserMapperTest in 3.654 seconds (JVM running for 4.585)
user1=User(id=1, name=马1蓉, gender=女, age=38, address=绿岛, qq=null, email=marong222@qq.com, username=marong, phone=11111111111, password=marong121233)
user2=User(id=2, name=马斯克2, gender=男, age=58, address=湖北省武汉市, qq=null, email=elmasike@qq.com, username=masike, phone=22222222222, password=masike121233)
user3=User(id=3, name=雷纳兹托瓦斯3, gender=男, age=18, address=湖北省荆门市, qq=null, email=lnztws@qq.com, username=lnztws, phone=33333333333, password=lnztws121233)
user4=User(id=4, name=黄仁勋4, gender=男, age=30, address=扬州, qq=null, email=huanrenxun@qq.com, username=huangrenxun, phone=44444444444, password=huangrenxun23123)
2023-11-15 16:05:23.862  INFO 82708 --- [ionShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} closing ...
2023-11-15 16:05:23.866  INFO 82708 --- [ionShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} closed
​
Process finished with exit code 0
```

#### 9.接下来我们来创建service包

`/src/main/java/com/practice/service/`（用户服务逻辑）

#### 10.创建UserService.java

`/src/main/java/com/practice/service/UserService.java`(用户服务逻辑的接口定义)

```java
package com.practice.service;
​
​
import com.practice.pojo.User;
​
import java.util.List;
​
public interface UserService {
​
    /**
     * 获取所有用户信息。
     *
     * @return 包含所有用户信息的列表
     */
    List<User> findUser();
​
    /**
     * 根据用户ID查找特定用户。
     *
     * @param id 用户ID
     * @return 包含用户信息的User对象，如果未找到则返回null
     */
    User findUserById(long id);
​
    /**
     * 保存新用户信息。
     *
     * @param user 包含新用户信息的User对象
     */
    void saveUser(User user);
​
    /**
     * 更新现有用户信息。
     *
     * @param user 包含更新后用户信息的User对象
     */
    void updateUser(User user);
​
    /**
     * 根据用户ID删除特定用户。
     *
     * @param id 要删除的用户ID
     */
    void deleteUerById(Long id);}
```

#### 11.UserService的实现UserServiceimpl.java

implement目录创建

`/src/main/java/com/practice/service/impl`

Service的接口实现

`/src/main/java/com/practice/service/impl/UserServiceimpl.java`

```java
package com.practice.service.impl;
​
import com.practice.dao.UserMapper;
import com.practice.pojo.User;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
​
import java.util.List;
​
​
/**
 * 这是一个用户服务的实现类。
 * 该类使用@Service注解将其标记为Spring的服务组件，
 * 并使用@Transactional注解指定了事务的范围。
 *
 * 类中的方法完成了对用户的增删改查操作，
 * 通过@Override注解表明这些方法是接口UserService中定义的方法的实现。
 * 方法中使用UserMapper来执行对数据库的操作，比如插入、更新、查询等。
 */
​
@Service
@Transactional
public class UserServiceimpl implements UserService {
​
    @Autowired
    UserMapper userMapper;//Could not autowire. No beans of"UserMapper' type found. more... (SB F1)不用去管
​
    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Override
    public List<User> findUser() {
        return userMapper.selectAll();
    }
​
    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public User findUserById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }
​
    /**
     * 保存用户
     *
     * @param user 用户对象
     */
    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }
​
    /**
     * 更新用户
     *
     * @param user 用户对象
     */
    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
    }
​
    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    @Override
    public void deleteUerById(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
```

##### 说明：

这是一个用户服务的实现类。

`@Transactional` 是一个注解，用于在 Spring 应用程序中定义事务的行为。它可以应用于类、方法或接口上。

`@Transactional` 注解的作用是将带有注解的方法或类标记为事务性操作。当方法被调用时，Spring 将创建一个事务，并在方法执行结束后根据方法的结果来决定是提交事务还是回滚事务。如果方法执行成功，事务将被提交，如果方法抛出异常，事务将会回滚。

`@Transactional` 注解可以具有不同的属性和选项，以控制事务的行为，例如：

1.  **事务传播行为（Propagation）：** 定义事务方法与现有事务之间的相互关系。例如，如果方法在一个现有事务中被调用，它可以选择加入该事务或创建一个新的事务。
1.  **事务隔离级别（Isolation）：** 定义事务在并发环境下的隔离程度。不同的隔离级别提供了不同的数据一致性和并发控制机制。
1.  **超时时间（Timeout）：** 定义事务的最大执行时间。如果事务在指定的时间内没有完成，将被强制回滚。
1.  **只读（Read-only）：** 指示事务是否只读取数据而不做修改。只读事务可以提供额外的性能优势。
1.  **回滚规则（Rollback）：** 定义哪些异常会触发事务回滚。可以指定特定的异常类型或通过通配符匹配。

通过使用 `@Transactional` 注解，您可以轻松管理事务边界，确保一组操作要么全部成功提交，要么全部回滚，从而保持数据的一致性和完整性。这样可以简化事务管理的代码，并提供更好的可读性和可维护性。

##### 11.1 .UserServiceTest测试我们的UserService

`/src/test/java/com/practice/UserServiceTest.java`

```java
package com.practice;
​
​
import com.practice.pojo.User;
import com.practice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
​
import java.util.List;
​
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
​
    @Autowired
    UserService userService;
​
​
​
    @Test
    public void finAllUser(){
        List<User> users =userService.findUser();
        System.out.println(users);
    }
​
}
​
```

#### 12.通用的Result处理

我们将通用的Result处理暂时放在vo包下处理

##### 12.1新建vo包

`src/main/java/com/practice/vo/`

##### 12.2新建Result.java

`src/main/java/com/practice/vo/Result.java`

```java
  package com.practice.vo;
​
import lombok.Getter;
import lombok.Setter;
​
import java.io.Serializable;
​
@Setter
@Getter
public class Result implements Serializable {
    private boolean status; // 响应状态，true表示成功，false表示失败
    private String message; // 响应消息
    private Object data; // 响应数据
​
    /**
     * 创建一个表示成功操作的Result对象
     *
     * @param data 响应数据
     * @return Result对象
     */
    public static Result ok(Object data) {
        Result result = new Result();
        result.setStatus(true);
        result.setMessage("Response is successful");
        result.setData(data);
        return result;
    }
​
    /**
     * 创建一个表示失败操作的Result对象
     *
     * @param data 响应数据
     * @return Result对象
     */
    public static Result error(Object data) {
        Result result = new Result();
        result.setStatus(false);
        result.setMessage("Response is unsuccessful");
        result.setData(data);
        return result;
    }
}
​
​
```

##### 说明：

上述代码是一个名为 `Result` 的类，用于封装响应结果。该类实现了 `Serializable` 接口，表示对象可以进行序列化和反序列化。

`@Getter` 和 `@Setter` 是 Lombok 库提供的注解，用于自动生成属性的 getter 和 setter 方法，以简化 Java 类的编写。使用这些注解可以避免手动编写繁琐的 getter 和 setter 方法。

-   `@Getter`：该注解应用于类级别或属性级别。当应用于类级别时，它会为该类的所有非静态字段生成相应的 getter 方法。当应用于属性级别时，它只会为该属性生成 getter 方法。
-   `@Setter`：该注解应用于类级别或属性级别。当应用于类级别时，它会为该类的所有非静态字段生成相应的 setter 方法。当应用于属性级别时，它只会为该属性生成 setter 方法。

在 `Result` 类中使用 `@Getter` 和 `@Setter` 注解，将为每个类成员变量自动生成对应的 getter 和 setter 方法。这样可以通过调用这些方法来访问和修改类的私有成员变量，而无需手动编写这些方法。

例如，`status` 字段具有 `@Getter` 和 `@Setter` 注解，那么将自动生成以下方法：

```java
public boolean isStatus() {
    return status;
}
​
public void setStatus(boolean status) {
    this.status = status;
}
```

通过使用 `@Getter` 和 `@Setter` 注解，可以简化代码，并提高代码的可读性和可维护性。需要注意的是，在使用 Lombok 注解时，确保已正确配置和集成 Lombok 插件，以使注解生效。

该类包含以下成员变量：

-   `status`：响应状态，用于表示操作的成功或失败。`true` 表示成功，`false` 表示失败。
-   `message`：响应消息，用于描述操作的结果信息。
-   `data`：响应数据，用于存储返回的数据对象。

在类中定义了两个静态方法：

-   `ok` 方法：创建一个表示成功操作的 `Result` 对象。它接收一个 `data` 参数，将传递的数据对象存储到 `data` 成员变量中，并设置 `status` 为 `true`，`message` 为 "Response is successful"。
-   `error` 方法：创建一个表示失败操作的 `Result` 对象。它也接收一个 `data` 参数，将传递的数据对象存储到 `data` 成员变量中，并设置 `status` 为 `false`，`message` 为 "Response is unsuccessful"。

通过这种方式，可以方便地创建一个包含响应状态、消息和数据的 `Result` 对象，以便在应用程序中进行统一的响应处理。

`Serializable` 是 Java 中的一个接口，用于标识一个类的对象可以被序列化和反序列化。当一个类实现了 `Serializable` 接口时，它的对象可以被转换为字节序列，以便在网络上传输或持久化到磁盘中，同时也可以将字节序列重新转换为对象。

-   `Serializable` 接口的作用主要有以下几个方面：

1.  **对象的持久化：** 通过实现 `Serializable` 接口，可以将对象转换为字节序列并将其写入到磁盘文件或数据库等持久化存储介质中。这样，在程序终止后，对象的状态可以被保存下来，下次程序启动时可以重新加载对象。
1.  **对象的网络传输：** 在分布式系统中，对象需要在不同的计算节点之间进行传输。通过实现 `Serializable` 接口，可以将对象序列化为字节序列，然后通过网络传输到其他节点，在目标节点上反序列化为对象。这样可以方便地在分布式系统中进行远程调用或消息传递。
1.  **对象的深拷贝：** 通过对象的序列化和反序列化，可以实现对象的深拷贝。深拷贝是指创建一个新的对象，该对象的所有字段都与原始对象相同，但是在内存中占据不同的位置。这对于需要复制对象并对其进行独立修改的情况非常有用。

需要注意的是，实现 `Serializable` 接口并不是默认安全的。在某些情况下，对敏感数据的序列化可能会导致安全问题。因此，在将类声明为可序列化之前，需要仔细考虑该类的安全性和敏感数据的保护。

要实现 `Serializable` 接口，只需在类声明中添加 `implements Serializable`，并确保类的所有字段都是可序列化的（即它们的类型也是可序列化的）或标记为 `transient`（将不会被序列化）。

#### 13.controller包的创建

`/src/main/java/com/practice/controller`

##### 说明：

在软件开发中，Controller（控制器）是一种模式或组件，用于处理用户请求并协调其他组件的行为。在Web应用程序中，Controller通常是MVC（Model-View-Controller）架构中的一部分，负责接收用户的请求，处理请求参数，调用适当的业务逻辑，并返回响应给用户。

主要作用如下：

1.  处理用户请求：Controller负责接收来自用户的请求，可以是通过URL、表单提交、AJAX等方式发送的请求。
1.  解析请求参数：Controller从请求中获取参数，这些参数可以是URL路径参数、查询参数、表单参数、请求头信息等。
1.  调用业务逻辑：Controller根据请求的内容，调用相应的业务逻辑组件（Service）来处理请求。它将解析的参数传递给业务逻辑组件，并根据业务需求执行相应的操作。
1.  数据准备与转换：Controller负责准备和处理数据，将从业务逻辑组件获取的数据转换为适合呈现给用户的格式，如JSON、HTML等。
1.  调用视图层：在MVC架构中，Controller将处理完请求后，将数据传递给视图层（View）进行展示。视图层负责将处理后的数据以用户可读的形式呈现出来。
1.  处理异常和错误：Controller负责捕获和处理业务逻辑中的异常和错误情况。它可以根据具体情况返回适当的错误信息或跳转到错误页面。
1.  路由和URL映射：Controller负责根据请求的URL路径，将请求路由到适当的处理方法。它通常使用路由配置来定义URL与处理方法之间的映射关系。

总体而言，Controller在应用程序中扮演着请求处理和业务逻辑协调的角色。它帮助将用户的请求转化为具体的操作，并将相应的结果返回给用户。同时，Controller也负责处理异常和错误情况，确保应用程序的稳定性和可靠性。

#### 14.UserController.java创建

`/src/main/java/com/practice/controller/UserController.java`

```java
package com.practice.controller;
import com.practice.pojo.User;
import com.practice.service.UserService;
import com.practice.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
​
​
import java.util.List;
​
@RestController
@RequestMapping("/user") //分配请求组前缀相于二级路径
public class UserController {
​
    @Autowired
    UserService userService;
​
    /**
     * function name findAll
     * explain
     * find all user from the service -> dao
     * @return all user information
     */
​
    @RequestMapping("/findAllUser")
    public Result findAll(){
        List<User> list = userService.findUser();
        return Result.ok(list);
    }
}
​
```

##### 说明：

-   `@RestController`: 这是一个Spring注解，表示该类是一个RESTful控制器，用于处理HTTP请求并返回RESTful风格的响应。
-   `@RequestMapping("/user")`: 这是一个类级别的注解，指定了处理该控制器中所有请求的基础路径。所有以"/user"开头的请求都将由该控制器处理。
-   `@Autowired`: 这是一个Spring注解，用于自动注入`UserService`的实例。
-   `@RequestMapping("/findAllUser")`: 这是一个方法级别的注解，指定了处理"/user/findAllUser"请求的路径。
-   `Result`: 这是一个自定义的响应结果类，用于封装请求的返回结果。
-   `findAll()`: 这是一个处理"/user/findAllUser"请求的方法。它通过调用`userService.findUser()`方法获取所有用户的信息，并将结果封装到`Result`对象中返回。

该控制器类的作用是处理与用户相关的请求，其中`findAll()`方法用于获取所有用户的信息。

#### 15.Redis集成

##### 1.redis安装

要在Docker中安装和启动Redis，你可以按照以下步骤进行操作：

安装Docker：首先，确保你已经在你的系统上安装了Docker。你可以根据你的操作系统在Docker官方网站上找到适合你的安装方法和文档。

拉取Redis镜像：使用以下命令从Docker Hub上拉取Redis镜像。

```linux
docker pull redis
```

启动Redis容器：使用以下命令启动Redis容器。

```linux
docker run --name my-redis -p 6379:6379 -d redis
```

上述命令将创建一个名为"my-redis"的容器，并将Redis的默认端口6379映射到主机的6379端口。你可以根据需要修改端口映射。

验证Redis启动：可以使用以下命令来验证Redis容器是否成功启动。

```linux
docker ps
```

上述命令将列出正在运行的Docker容器，你应该能够看到名为"my-redis"的容器。

至此，你已经成功安装和启动了Redis容器。你可以使用任何支持Redis的客户端工具（如Redis CLI或Redis Desktop Manager）连接到Redis服务器，并进行操作。

如果你希望在容器启动时保留Redis数据，你可以使用数据卷或映射主机目录来持久化Redis数据。这样，在容器重新启动时，数据将得以保留。

##### 2.新增Redis 启动器依赖

`pom.xml`

```xml
    <!-- Redis 启动器依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
```

##### 3.编写配置文件向application文件追加配置

```yaml
spring:
  redis:
    host: localhost  # Redis服务器主机名
    port: 6379  # Redis服务器端口
    password:  # Redis服务器密码（如果有的话）
```

###### *说明 :*

-   `host`: Redis服务器的主机名或IP地址。如果Redis服务器运行在本地，可以使用默认的"localhost"。
-   `port`: Redis服务器的端口号。默认的Redis端口是6379。
-   `password`: Redis服务器的密码。如果你的Redis服务器需要密码验证，可以在这里填写密码。如果不需要密码验证，可以将此属性留空或删除。

根据你的实际配置需求，可以添加其他的Redis配置项。例如，你可以配置连接池相关的属性，如最大连接数、最大空闲连接数等。

以下是一个包含连接池配置的示例：

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password:
    jedis:
      pool:
        max-active: 10  # 最大活动连接数
        max-idle: 5  # 最大空闲连接数
        min-idle: 1  # 最小空闲连接数
        timeout: 3000  # 连接超时时间（毫秒）
```

在上述示例中，我们添加了`jedis.pool`下的属性来配置连接池的相关设置。

请根据你的实际需求进行Redis的YAML配置，并确保将配置文件命名为`application.yml`并放置在正确的位置，以便Spring Boot应用程序能够正确加载和使用Redis配置。

##### 4.在我们的项目中测试使用redis

修改`/src/main/java/com/practice/service/impl/UserServiceimpl.java`中的逻辑来测试，我们随便找个findUser方法来修改

```java
package com.practice.service.impl;
​
import com.practice.dao.UserMapper;
import com.practice.pojo.User;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
​
import java.util.List;
​
​
/**
 * 这是一个用户服务的实现类。
 * 该类使用@Service注解将其标记为Spring的服务组件，
 * 并使用@Transactional注解指定了事务的范围。
 *
 * 类中的方法完成了对用户的增删改查操作，
 * 通过@Override注解表明这些方法是接口UserService中定义的方法的实现。
 * 方法中使用UserMapper来执行对数据库的操作，比如插入、更新、查询等。
 */
​
@Service
@Transactional
public class UserServiceimpl implements UserService {
​
    @Autowired
    UserMapper userMapper;//Could not autowire. No beans of"UserMapper' type found. more... (SB F1)不用去管
​
    @Autowired
    private RedisTemplate redisTemplate;//Could not autowire. No beans of"RedisTemplate' type found. more... (SB F1)不用去管
​
    /**
     * 查询所有用户，先从缓存中获取，如果缓存中没有，则从数据库中获取，并将结果存入缓存。
     *
     * @return 用户列表
     */
    @Override
    public List<User> findUser() {
        // 尝试从缓存中获取用户列表
        List<User> userList = (List<User>) redisTemplate.boundValueOps("UserList").get();
​
        // 如果缓存中存在数据，直接返回缓存中的数据
        if (userList != null && !userList.isEmpty()) {
            System.out.println("Read the data from the cache. List: " + userList);
            return userList;
        }
​
        // 缓存中没有数据，从数据库中获取用户列表
        userList = userMapper.selectAll();
​
        // 将从数据库中获取的用户列表存入缓存
        redisTemplate.boundValueOps("UserList").set(userList);
​
        System.out.println("Read the data from the database. List: " + userList);
​
        return userList;
    }
​
    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public User findUserById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }
​
    /**
     * 保存用户
     *
     * @param user 用户对象
     */
    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }
​
    /**
     * 更新用户
     *
     * @param user 用户对象
     */
    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
    }
​
    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    @Override
    public void deleteUerById(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
​
​
​
```

###### 说明：

`@Autowired private RedisTemplate redisTemplate;` 是Spring框架的依赖注入注解，用于将一个名为 `redisTemplate` 的 `RedisTemplate` 类型的 Bean 注入到当前类中。

在这里，`RedisTemplate` 是Spring Data Redis提供的一个模板类，用于简化对Redis的操作。它提供了一组操作方法，允许你以更高层次的抽象方式与Redis进行交互，而不必直接使用底层的Redis命令。

`redisTemplate` 的作用可以包括但不限于：

0.  **简化Redis操作：** 通过 `redisTemplate`，你可以使用一种更高级别的API（例如`opsForValue()`，`opsForHash()`等）来执行常见的Redis操作，而不必直接调用底层的Redis命令。
0.  **提供类型转换：** `RedisTemplate` 提供了对对象的序列化和反序列化支持，因此你可以直接存储和检索Java对象，而不必手动进行序列化和反序列化。
0.  **统一管理连接：** `RedisTemplate` 管理着与Redis的连接，确保连接的合理使用，避免了手动管理连接的复杂性。
0.  **集成Spring特性：** 由于使用了 `@Autowired` 注解，这个 `RedisTemplate` 可能受益于Spring的其他功能，如事务管理、AOP等。

在你的代码中，这个注入的 `RedisTemplate` 实例可以用于执行与Redis相关的操作，例如在 `findUser()` 方法中，你使用了它来从Redis缓存中获取用户列表并将其存入缓存。

##### 5.查看docker 中的redis服务是否存入

首先，让我们看看如何使用Redis的命令行工具来查询键的值：

1.  打开终端，并连接到Redis容器。使用以下命令连接到Redis容器：

    ```linux
    docker exec -it <redis_container_id_or_name> redis-cli
    ```

    将 `<redis_container_id_or_name>` 替换为你的Redis容器的ID或名称。

1.  在Redis命令行中，使用以下命令查询键"UserList"的值：

    ```linux
    GET UserList
    ```

    如果键存在，将返回存储在该键下的值。如果键不存在，将返回空值(nil)。

##### 6.【扩展-可自行研究】工欲善其事必先利其器 封装一个redis的通用class

新建一个utils的包`/src/main/java/com/practice/utils`

在utils的包下新建一个RedisUtil.java

`/src/main/java/com/practice/utils/RedisUtil.java`(通用的Redis分装)

```java
package com.practice.utils;
​
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;
​
/**
 * RedisUtil is a utility class for interacting with Redis.
 * It provides common operations for storing, retrieving, and managing data in Redis.
 *
 * @param <T> the type of the value stored in Redis
 * @Author Wujiahao
 */
/**
 * RedisUtil是一个用于与Redis进行交互的工具类。
 * 它提供了在Redis中存储、检索和管理数据的常见操作。
 *
 * @param <T> 存储在Redis中的值的类型
 * @Author Wujiahao
 */
@Component
public class RedisUtil<T> {
​
    //    通过自动配置生成 `RedisHealthContributorAutoConfiguration.class`和`RedisAutoConfiguration.java`是Spring Boot中与Redis相关的自动配置类。
    // Redis template for storing key-value pairs
    // 用于存储键值对到Redis的Redis模板
    @Autowired
    private RedisTemplate<String, T> redisTemplate;    // 存储键值对到 Redis 中
​
​
    /**
     * Set a key-value pair in Redis.
     *
     * @param key   the key
     * @param value the value
     */
    /**
     * 在Redis中设置键值对。
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }
​
​
    /**
     * Set a key-value pair in Redis with an expiration time (in seconds).
     *
     * @param key     the key
     * @param value   the value
     * @param timeout the expiration time in seconds
     */
    /**
     * 在Redis中设置键值对，并设置过期时间（单位：秒）。
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间（秒）
     */
    // 存储键值对到 Redis 中并设置过期时间（单位：秒）
    public void set(String key, T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
​
​
    /**
     * Get the value associated with a key from Redis.
     *
     * @param key the key
     * @return the value associated with the key
     */
    /**
     * 根据键从Redis中获取关联的值。
     *
     * @param key 键
     * @return 与键关联的值
     */
    // 根据键获取存储在 Redis 中的值
    public T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * Delete the value associated with a key from Redis.
     *
     * @param key the key
     */
    /**
     * 根据键从Redis中删除关联的值。
     *
     * @param key 键
     */
    // 根据键删除存储在 Redis 中的值
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    /**
     * Returns the BoundValueOperations associated with the given key.
     *
     * @param key the key
     * @return the BoundValueOperations associated with the key
     */
    /**
     * 返回与给定键关联的BoundValueOperations对象。
     *
     * @param key 键
     * @return 与键关联的BoundValueOperations对象
     */
    // 返回与给定键关联的 BoundValueOperations 对象
    public BoundValueOperations<String, T> boundValueOps(String key) {
        return redisTemplate.boundValueOps(key);
    }
    /**
     * Check if a key exists in Redis.
     *
     * @param key the key
     * @return true if the key exists, false otherwise
     */
    /**
     * 检查键是否存在于Redis中。
     *
     * @param key 键
     * @return 如果键存在，则返回true；否则返回false
     */
    // 检查键是否存在于 Redis 中
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * Set the expiration time (in seconds) for a key.
     *
     * @param key     the key
     * @param timeout the expiration time in seconds
     */
    /**
     * 设置键的过期时间（单位：秒）。
     *
     * @param key     键
     * @param timeout 过期时间（秒）
     */
    // 设置键的过期时间（单位：秒）
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
    /**
     * Get the expiration time (in seconds) for a key.
     *
     * @param key the key
     * @return the expiration time in seconds, or -1 if the key does not exist or does not have an expiration time
     */
    /**
     * 获取键的过期时间（单位：秒）。
     *
     * @param key 键
     * @return 键的过期时间（秒），如果键不存在或没有设置过期时间，则返回-1
     */
    // 获取键的过期时间（单位：秒）
    public long getExpiration(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
```

###### 说明：

上面这段代码紧紧代表一个封装的示例，有需要的各人请自己在细化封装的内容

`RedisHealthContributorAutoConfiguration.class`和`RedisAutoConfiguration.java`是Spring Boot中与Redis相关的自动配置类。

`RedisHealthContributorAutoConfiguration.class`是用于配置Redis的健康检查相关的自动配置。它提供了一个`RedisHealthIndicator`，用于检查Redis连接的健康状态，并将该指标添加到应用程序的健康检查中。

`RedisAutoConfiguration.java`是用于配置Redis的自动配置类。它提供了`RedisTemplate`、`StringRedisTemplate`和其他与Redis相关的Bean的自动配置。

在你的示例中，通过使用`@Autowired`注解将`RedisTemplate`注入到一个组件中，该组件可以使用Redis功能。

自动配置的原理如下：

1.  当你引入了`spring-boot-starter-data-redis`依赖时，Spring Boot会自动扫描并加载`RedisAutoConfiguration.java`，该类包含了与Redis相关的自动配置逻辑。
1.  `RedisAutoConfiguration.java`中的自动配置类会根据应用程序的配置文件（如`application.properties`或`application.yml`）中的属性，创建一个`RedisConnectionFactory`对象，用于与Redis服务器建立连接。
1.  接着，自动配置类会根据`RedisConnectionFactory`创建一个`RedisTemplate`对象，用于执行Redis操作。`RedisTemplate`是Spring Data Redis提供的一个核心类，它封装了对Redis的各种操作，如存储、检索、删除等。
1.  当你在其他组件中使用`@Autowired`注解将`RedisTemplate`注入时，Spring会检测到`RedisTemplate`的存在，并自动将其注入到对应的组件中。

因此，通过`@Autowired`注解将`RedisTemplate`注入到组件中，利用了Spring Boot的自动配置机制，使得你可以方便地在应用程序中使用Redis功能，而无需手动创建和配置`RedisTemplate`实例。

需要注意的是，自动配置是根据一系列默认规则和条件进行的，如果你想要自定义Redis的配置，可以在配置文件中指定相应的属性，或者通过编写自定义的配置类来覆盖默认的自动配置行为

##### 7.【扩展-可自行研究】redis通用工具类的使用测试（TEST）

在你的 Spring Boot 项目中，确保已经配置了 Redis 相关的依赖和连接信息。你可以在 application.properties 或 application.yml 文件中配置 Redis 的连接信息。 在需要使用 Redis 的地方，引入 RedisUtil 类，可以通过 @Autowired 或构造函数注入。 使用 RedisUtil 对象调用相应的方法进行 Redis 操作。 下面是一个使用示例：

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class YourClass {
    @Autowired
    private RedisUtil<String> redisUtil;
    public void yourMethod() {
        // 存储值到 Redis
        redisUtil.set("myKey", "myValue");
        // 获取 Redis 中的值
        String value = redisUtil.get("myKey");
        // 判断键是否存在
        boolean exists = redisUtil.exists("myKey");
        // 设置键的过期时间
        redisUtil.expire("myKey", 60);
        // 获取键的过期时间
        long expiration = redisUtil.getExpiration("myKey");
        // 删除键
        redisUtil.delete("myKey");
    }
}
```

#### 16.SpringBoot Actuator 组件

##### 简介：

在Spring Boot中，执行器（Actuator）是一个重要的组件，它提供了对应用程序的监控、管理和操作的功能。执行器允许你通过HTTP端点或JMX（Java Management Extensions）来获取应用程序的各种信息，包括健康状况、度量指标、环境配置等。

以下是一些执行器组件的常用功能：

1.  健康指标（Health Indicators）：执行器提供了一个用于检查应用程序健康状态的端点（endpoint）。通过检查该端点，你可以了解应用程序是否运行正常。
1.  信息指标（Info Endpoint）：执行器提供了一个用于获取应用程序信息的端点。你可以自定义并在应用程序信息中添加自己的属性。
1.  环境配置（Environment Endpoint）：执行器的环境配置端点允许你获取应用程序的配置属性，包括系统属性、环境变量和应用程序特定的属性。
1.  度量指标（Metrics Endpoint）：执行器提供了一组用于收集和监控应用程序度量指标的端点。这些指标可以包括请求计数、响应时间、内存使用等信息。
1.  日志文件（Logfile Endpoint）：执行器允许你通过一个端点来访问应用程序的日志文件，你可以查看最近的日志内容。
1.  线程转储（Thread Dump Endpoint）：执行器提供了一个线程转储端点，用于获取应用程序当前活动线程的转储信息。
1.  运行时配置（Configuration Properties）：执行器可以显示应用程序中使用的配置属性，并提供了一种动态更改这些属性的方式。

执行器组件在Spring Boot中通过依赖添加到项目中，通常使用`spring-boot-starter-actuator`依赖。在`pom.xml`文件中添加以下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

启用执行器组件后，你可以通过HTTP端点或JMX来访问执行器的功能。默认情况下，执行器的HTTP端点路径为`/actuator`，例如`/actuator/health`用于检查健康状态。

你还可以通过配置文件（如`application.properties`或`application.yml`）来自定义执行器的端点路径和其他属性。

执行器的详细配置和使用方法可以参考Spring Boot的官方文档：

-   [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)

注意：执行器提供了对应用程序的监控和管理功能，因此在生产环境中应谨慎使用，确保适当地保护和限制执行器端点的访问。

##### 1.在我的项目里添加SpringBoot Actuator 组件

`pom.xml`上面项目初始化时我们已经添加了

##### 2.Actuator配置

当使用`application.yml`进行配置时，你可以根据需要添加以下配置来自定义Spring Boot Actuator的行为：

```yaml
# 配置Actuator端点的访问路径
management:
  endpoints:
    web:
      base-path: /health # 设置Actuator的基本路径，默认为/actuator，这里我们设置为/health
      exposure:
        include: "*" # 包括所有Actuator端点
        exclude: env # 排除env端点
    health:
      show-details: always # 显示详细的健康信息
  endpoint:
    health:
      show-details: always # 显示详细的健康信息
​
# 配置Actuator的安全性
spring:
  security:
    user:
      name: admin # 设置Actuator的用户名
      password: password # 设置Actuator的密码
```

上述配置示例包括了几个常见的Actuator配置选项。你可以根据需要进行自定义配置：

-   `management.endpoints.web.base-path`：设置Actuator的基本路径，默认为`/actuator`。你可以将其更改为其他路径，以适应你的项目需要。
-   `management.endpoints.web.exposure.include`：指定要暴露的Actuator端点。在示例中，使用通配符`*`表示包括所有端点。你可以根据需要指定特定的端点或排除某些端点。
-   `management.endpoints.web.exposure.exclude`：指定要排除的Actuator端点。在示例中，`env`端点被排除在外。你可以根据需要排除其他端点。
-   `management.endpoint.health.show-details`：配置健康端点是否显示详细信息。在示例中，设置为`always`表示始终显示详细信息。你也可以将其设置为`never`以隐藏详细信息。
-   `spring.security.user.name`和`spring.security.user.password`：配置Actuator的用户名和密码。在示例中，用户名为`admin`，密码为`password`。你可以根据需求设置不同的用户名和密码。

请注意，这只是一些常见的配置选项示例。你可以根据具体需求进行更多的自定义配置。同时，确保在生产环境中使用强大的密码和适当的安全措施来保护Actuator端点。

###### 当前的`application.yml`文件的所有配置

`/src/main/resource/application.yml`

```yaml
# 服务器端口配置
server:
  port: 9090
​
# Spring 数据源配置
spring:
  datasource:
    # 数据库连接 URL
    url: jdbc:mysql://127.0.0.1:3306/springboot
    # MySQL 驱动类
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 数据库用户名
    username: root
    # 数据库密码
    password:
    # 数据源类型（这里使用了 Druid 数据源）
    type: com.alibaba.druid.pool.DruidDataSource
  redis:
    host: 127.0.0.1
    port: 6379
    password:
  # 配置Actuator的安全性
  security:
    user:
      name: admin # 设置Actuator的用户名
      password: password # 设置Actuator的密码
​
# 配置Actuator端点的访问路径
management:
  endpoints:
    web:
      base-path: /health # 设置Actuator的基本路径，默认为/actuator，这里我们设置为/health
      exposure:
        include: "*" # 包括所有Actuator端点 对外暴露所有的访问路径 ，默认是/health 和 /info
      # exclude: env # 排除env端点
    health:
      show-details: always # 显示详细的健康信息
  server:
    port: 9091 #设置Actuator的基的访问端口，不设置的话默认是同Web启动器的server.port一致
# MyBatis 配置
mybatis:
  # MyBatis 类型别名包扫描路径，用于扫描指定包下的类作为 MyBatis 实体类
  type-aliases-package: com.practice.pojo
​
​
```

##### 3.访问执行器端点：启用执行器后，你可以通过HTTP端点或JMX来访问执行器的各种功能。以下是一些常用的执行器端点和其对应的功能：

*注意：我这里是默认路径，按照上面的设置，路径为`/health/***`*

以下是常用的Spring Boot Actuator端点及其对应的功能和请求方式：

| 端点路径                     | 功能                                 | 请求方法        |
| ------------------------ | ---------------------------------- | ----------- |
| /actuator/auditevents    | 查看审计事件（Audit Events）               | GET         |
| /actuator/beans          | 查看应用程序中所有可用的Bean                   | GET         |
| /actuator/conditions     | 查看自动配置条件是否满足                       | GET         |
| /actuator/configprops    | 查看所有配置属性（Configuration Properties） | GET         |
| /actuator/env            | 查看应用程序的环境配置（Environment）           | GET         |
| /actuator/flyway         | 查看数据库迁移信息（Flyway）                  | GET         |
| /actuator/health         | 查看应用程序的健康状况（Health）                | GET         |
| /actuator/info           | 查看应用程序的信息                          | GET         |
| /actuator/loggers        | 查看和修改日志记录器的配置                      | GET, POST   |
| /actuator/liquibase      | 查看数据库迁移信息（Liquibase）               | GET         |
| /actuator/metrics        | 查看应用程序的度量指标（Metrics）               | GET         |
| /actuator/prometheus     | 以Prometheus格式暴露应用程序的度量指标           | GET         |
| /actuator/scheduledtasks | 查看应用程序中的定时任务（Scheduled Tasks）      | GET         |
| /actuator/sessions       | 查看和失效用户会话（Sessions）                | GET, DELETE |
| /actuator/shutdown       | 关闭应用程序（仅在启用时可用，需配置安全性）             | POST        |
| /actuator/threaddump     | 获取应用程序当前活动线程的转储信息                  | GET         |
| /actuator/mappings       | 查看应用程序的URL映射（URL Mappings）         | GET         |
| /actuator/heapdump       | 获取应用程序的堆转储信息                       | GE          |
| /actuator/jolokia        | 暴露JMX beans（需要Jolokia依赖）           | GET         |

这些端点提供了各种有用的功能，可以帮助你监控和管理Spring Boot应用程序。具体使用的请求方式取决于每个端点的设计和配置。

你可以使用类似`curl`、浏览器或其他HTTP客户端工具来访问这些端点。例如，使用`curl`命令来获取健康状况，如果你的配置同我一致请执行：

```linux
curl http://localhost:8080/health/health
```

如果你是默认的配置请访问：

```linux
curl http://localhost:8080/actuator/health
```

执行结果如下:

```linux
{
"status": "UP",
"components": {
"db": {
"status": "UP",
"details": {
"database": "MySQL",
"validationQuery": "isValid()"
}
},
"diskSpace": {
"status": "UP",
"details": {
"total": 499963174912,
"free": 115618381824,
"threshold": 10485760,
"exists": true
}
},
"ping": {
"status": "UP"
},
"redis": {
"status": "UP",
"details": {
"version": "6.2.6"
}
}
}
}
​
```

你还可以使用JMX工具（如JConsole或VisualVM）来连接到应用程序并查看执行器的MBeans。

##### 4.SpringBoot Admin 组件

###### 简介

Spring Boot Admin是一个用于监控和管理Spring Boot应用程序的开源组件。它提供了一个用户界面，用于实时监控应用程序的健康状况、运行状态、性能指标等，并且可以对应用程序进行远程管理操作。

以下是Spring Boot Admin的一些主要特性：

1.  应用程序监控：Spring Boot Admin可以监控注册在其上的多个Spring Boot应用程序。它会收集应用程序的健康状况、内存使用情况、线程信息、日志等，并以可视化的方式展示在用户界面上。
1.  健康状况检查：Spring Boot Admin会定期检查应用程序的健康状况，并提供实时的健康报告。你可以查看应用程序的健康状态，包括是否健康、是否存活、具体的健康指标等。
1.  运行状态监控：Spring Boot Admin可以监控应用程序的运行状态，包括内存使用情况、线程数量、加载的类数等。这些指标可以帮助你了解应用程序的性能和资源利用情况。
1.  日志管理：Spring Boot Admin提供了查看和管理应用程序日志的功能。你可以查看应用程序的日志文件，并支持日志级别的动态调整。
1.  远程操作：Spring Boot Admin允许你对注册的应用程序进行远程操作，如重启应用程序、关闭应用程序、动态修改配置等。这使得你可以在不登录到服务器的情况下管理应用程序。
1.  事件通知：Spring Boot Admin支持事件通知机制，可以将应用程序的状态变化、健康状况等信息发送到外部系统，如Slack、Email等。

Spring Boot Admin提供了一个易于使用和直观的用户界面，使得监控和管理Spring Boot应用程序变得简单和便捷。你可以通过引入相应的依赖并进行配置，将Spring Boot Admin集成到你的应用程序中。

注意，Spring Boot Admin是一个独立的项目，与Spring Boot本身并不直接相关。它是使用Spring Boot和Spring Cloud等技术栈构建的，但需要单独进行配置和部署。

###### 操作

使用IntelliJ IDEA创建新的Maven项目并添加Spring Boot Admin组件

步骤1：打开IntelliJ IDEA

首先，启动IntelliJ IDEA并打开主界面。

步骤2：创建新项目

-   在主界面上选择 "Create New Project" 或 "New Project"，进入新项目创建向导。

步骤3：选择项目类型

-   在向导中选择 "Maven" 作为项目类型，并点击 "Next"。

步骤4：配置项目

-   在 "GroupId" 字段中输入 "com.practice"，在 "ArtifactId" 字段中输入 "springboot_admin_server"，并选择存储项目的位置。然后点击 "Next"。

步骤5：这里我们不选择Maven模板

-   然后点击 "Next"。

步骤6：配置项目名称

-   在 "Project Name" 页面上，输入你的项目名称，并点击 "Finish"。

步骤7：POM文件配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
​
    <groupId>com.practice</groupId>
    <artifactId>springboot_admin_server</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!-- 所有的Spring boot项目都要继承这个父工程，父工程对所有的jar包进行管理 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
    </parent>
    <properties>
        <!--        将Java版本设置为1.8-->
        <java.version>1.8</java.version>
    </properties>
​
    <!-- 依赖 -->
    <dependencies>
        <!-- Web启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- Spring Boot Admin Server -->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.7.13</version>
        </dependency>
    </dependencies>
</project>
```

注意`spring-boot-admin-starter-server` 对jdk的版本有要求请勿使用最新的3.X版本

步骤8：创建App.java文件

-   在 `src/main/java` 目录下创建一个多级的目录`/com/practice/`，在该目录下创建一个名为 `App.java` 的Java类文件，并将以下代码添加到文件中：

    `/src/main/java/com/practice/App.java`

```java
package com.practice;
​
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
​
@SpringBootApplication
@EnableAdminServer // 启用Spring Boot Admin Server
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args); // 启动Spring Boot应用程序
    }
}
```

在这段代码中，我们创建了一个简单的Spring Boot应用程序的入口点。

我们使用了`@EnableAdminServer`注解来启用Spring Boot Admin Server。这个注解告诉Spring Boot应用程序要将当前应用程序作为Admin Server来运行，以监控和管理其他Spring Boot应用程序。

步骤9：创建application.yml文件

-   在 `src/main/resources` 目录下，创建一个名为 `application.yml` 的配置文件，并将以下内容添加到文件中：

    `src/main/resources/application.yml`

```yaml
server:
  port: 8080  # 设置应用程序的端口号
​
spring:
  application:
    name: my-Admin  # 设置应用程序的名称
```

在这个示例的配置文件中，我们设置了应用程序的端口为8080，并将应用程序的名称设置为"my-Admin"。你可以根据你的需求添加其他配置。

完成上述步骤后，你已经成功创建了一个包含Spring Boot Admin组件的Maven项目。

接下来可以再浏览器当中输入已下的地址就能看到我们的服务生效啦。

```linux
http://localhost:8888/applications
```

##### 5.添加Spring Boot Admin Client组件

###### 1.这里的依赖是需要添加到我们被监控的服务当中的所以就会修改我们之前的项目的POM文件；

注意项目是

`com.practice`

`springboot_practice`

别添加错了!!!!

别添加错了!!!

别添加错了!!

重要的事情说三遍！！！！！

添加依赖:

```xml
        <!-- Spring Boot Admin Client 在POM文件中添加Client的依赖注意要和Server的依赖配套：-->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-client</artifactId>
            <version>2.7.13</version>
        </dependency>
```

在POM文件中添加Client的依赖注意要和Server的依赖配套,POM文件修改如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
​
    <groupId>com.practice</groupId>
    <artifactId>springboot_04mvc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!-- 所有的Spring boot项目都要继承这个父工程，父工程对所有的jar包进行管理 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
    </parent>
​
    <!-- 依赖 -->
    <dependencies>
        <!-- 单元测试启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
​
        <!-- 通用mapper启动器 -->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>2.1.5</version> <!-- 使用最新版本 -->
        </dependency>
​
        <!-- JDBC启动器spring-boot-starter-jdbc 是 Spring Boot 中用于简化 JDBC 开发的启动器。它提供了 JDBC 相关的基础配置，让开发者能够更轻松地使用 Spring JDBC 访问数据库。 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
​
        <!-- mysql驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.23</version> <!-- 使用最新版本 -->
        </dependency>
​
        <!-- druid启动器 数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.17</version> <!-- 使用最新版本 -->
        </dependency>
​
        <!-- Web启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
​
        <!-- spring boot Actuator依赖  spring-boot-starter-actuator 是 Spring Boot 提供的一个用于生产环境监控和管理的模块。它为 Spring Boot 应用程序提供了一组内置的、用于监视和管理应用程序的端点（endpoints）。这些端点包括健康检查、信息展示、环境属性、配置信息、日志等，允许你监控应用程序的运行状况、收集信息和进行一些管理操作。-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
​
        <!-- 编码工具包 common3-lang3 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version> <!-- 使用最新版本 -->
        </dependency>
​
        <!-- 热部署 spring-boot-devtools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
        <!-- Redis 启动器依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!--Lombok的主要作用是通过简化常见的Java代码模式，减少样板代码的编写，提高开发效率，
       减少代码错误，增加代码的可读性和可维护性。它已经成为许多Java开发人员的常用工具之一，
       并在许多开源项目和企业应用中广泛使用。-->
​
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version> <!-- 请检查并使用最新的版本 -->
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- Spring Boot Admin Client 在POM文件中添加Client的依赖注意要和Server的依赖配套：-->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-client</artifactId>
            <version>2.7.13</version>
        </dependency>
​
    </dependencies>
    <!-- 打包成jar   -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
​
```

###### 2.这里配置是需要添加到我们被监控的服务当中的所以就会修改我们之前的项目的application.yml文件；

注意项目是

`com.practice`

`springboot_practice`

别添加错了!!!!

别添加错了!!!

别添加错了!!

重要的事情说三遍！！！！！

添加以下配置：

```yaml
  # Spring Boot Admin Client
  application:
    name: my-Client #名称随便起的
  boot:
    admin:
      client:
        url: http://localhost:8888 # 指定注册的地址，地址指向 Spring Boot 
```

修改 `src/main/resources/application.yml`

```yaml
# 服务器端口配置
server:
  port: 9090
​
# Spring 数据源配置
spring:
  datasource:
    # 数据库连接 URL
    url: jdbc:mysql://127.0.0.1:3306/springboot
    # MySQL 驱动类
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 数据库用户名
    username: root
    # 数据库密码
    password:
    # 数据源类型（这里使用了 Druid 数据源）
    type: com.alibaba.druid.pool.DruidDataSource
  redis:
    host: 127.0.0.1
    port: 6379
    password:
  # 配置Actuator的安全性
  security:
    user:
      name: admin # 设置Actuator的用户名
      password: password # 设置Actuator的密码
  # Spring Boot Admin Client
  application:
    name: my-Client #名称随便起的
  boot:
    admin:
      client:
        url: http://localhost:8888 # 指定注册的地址，地址指向 Spring Boot Admin Server地址
​
​
```

至此重新启动`springboot_practice`项目和`springboot_admin_server项目

通过Chrome访问<http://localhost:8888>即可访问Springboot Admin Client，你将会看到`springboot_practice`

指标以及一些运行信息被注册到Springboot Admin Server的信息。

###### 说明：

这里设置了应用程序的名称为 "my-Client"。这个名称是在 Spring Boot Admin 服务器中用于标识该应用程序的唯一标识符。你可以根据自己的需求来设置应用程序的名称。

这里指定了 Spring Boot Admin 服务器的地址。通过将应用程序注册到指定的 URL，应用程序就可以与 Spring Boot Admin 服务器建立连接，并向服务器发送应用程序的信息和指标数据。

在这个示例中，URL被设置为 `http://localhost:8888`，这意味着 Spring Boot Admin 服务器应该在本地主机上的 8888 端口运行。你需要根据实际情况将该 URL 替换为你的 Spring Boot Admin 服务器的地址。

通过使用这个配置，你的 Spring Boot 应用程序就可以将自身注册到 Spring Boot Admin 服务器，并将其监控和管理功能暴露给 Spring Boot Admin 服务器。这样，你就可以在 Spring Boot Admin 服务器的管理界面上查看和监控你的应用程序的状态、指标数据和日志信息，以便更好地管理和调试应用程序。

请注意，为了使这个配置生效，你需要在你的 Spring Boot 应用程序的类路径中包含适当的依赖项，以支持 Spring Boot Admin 客户端功能。





### 10.JAR包

##### 介绍：

至此我们Springboot的相关内容就大致结束了，最后将项目打成jar包本地运行看看吧看看是否可以打包成功。

依然是`springboot_practice`项目，`springboot_admin_server`项目先不要动放在那里。

我们找到`springboot_practice`项目中的。

`POM.xml`

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

##### 说明:

主要作用是将 Spring Boot 应用程序打包成可执行的 JAR 文件。以下是每个部分的作用：

1.  `<build>`: Maven 中的 `<build>` 元素用于配置构建过程。

1.  `<plugins>`: `<plugins>` 元素用于配置 Maven 构建过程中要使用的插件。

1.  `<plugin>`: 具体的插件配置。在这里，配置了 Spring Boot Maven 插件。

    -   `<groupId>`: 插件的 Group ID，指定插件的组织或提供者。在这里，`org.springframework.boot` 表示 Spring Boot 提供的插件。
    -   `<artifactId>`: 插件的 Artifact ID，指定插件的名称。在这里，`spring-boot-maven-plugin` 是 Spring Boot 提供的 Maven 插件的名称。

这个插件的作用是将 Spring Boot 项目打包成可执行的 JAR 文件，并且可以包含所有依赖，使得应用程序可以独立运行，无需外部容器（例如，Tomcat）。

##### 操作打包：

1.  点开`springboot_practice`项目,在`Intellij IDEA`编辑器的右边侧边栏找到`Maven`
1.  点开`Maven`操作栏，找到：`Lifecycle`
1.  点击`Lifecycle` 下的`package` 等待打包完成。
1.  生成的jar包存放在target目录下
1.  生成的 JAR 文件包含了应用程序的所有依赖，你可以通过命令行运行该 JAR 文件，例如：

```linux
java -jar springboot_practice-1.0-SNAPSHOT.jar
```

这样就启动了 Spring Boot 应用程序，而不需要额外配置或运行外部服务器。这种打包方式使得部署和运行 Spring Boot 应用程序变得非常（不）方（好）便。



### 总结

希望这篇文章看完对你有帮助，没帮助那肯定是你没好好学。😁肯定不是我文章的问题，看完别白嫖啊，有啥想说的记得给我留言。
希望在这个技术寒冬用一点火光照亮你的求职之路，如果你正在经历一些职场变动，或者期待来年换一份不错的工作，那么希望你认真的学习，能够更加专注的去投资自己。希望大家顺顺利利。
接下来还会出一篇SpringCloud相关的笔记，有兴趣记得关注！！
蟹蟹🦀🦀


[源码地址：https://github.com/XiaoBinGan/SpringBoot](https://github.com/XiaoBinGan/SpringBoot)







