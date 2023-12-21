#application 文件说明


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