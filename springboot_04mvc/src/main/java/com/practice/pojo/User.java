package com.practice.pojo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Entity 注解声明这是一个 JPA 实体类。
 * @Table 注解用于映射表名。
 * @Id 和 @GeneratedValue 注解标识主键，并指定主键的生成策略。
 * @Column 注解用于映射数据库表字段，包括字段名、是否可为空、长度等属性。
 * @Getter 和 @Setter 注解由 Lombok 自动生成 Getter 和 Setter 方法。
 * @ToString 自动生成 toString 方法
 * Serializable 接口是一个标记接口，表示该类的对象可以被序列化。
 * 根据实际需要，你还可以添加构造方法、toString 方法，以及重写 equals 和 hashCode 方法。这取决于你是否需要在比较对象时使用这些方法。
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

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "gender", length = 5)
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address", length = 32)
    private String address;

    @Column(name = "qq", length = 20)
    private String qq;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "password", nullable = false, length = 255)
    private String password;


}
