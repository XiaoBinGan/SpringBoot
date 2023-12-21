package com.practice.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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
 *         strategy = GenerationType.IDENTITY 是注解中的一个参数，它指定了主键生成策略。在这种情况下，GenerationType.IDENTITY 表示使用数据库的自增（或自动增加）功能来生成主键值。
 *
 *         在许多数据库管理系统中，可以配置表的主键列，使其在插入新记录时自动增加。这意味着每次插入新记录时，主键列的值都会自动增加，不需要手动指定主键值。GenerationType.IDENTITY 就是告诉JPA使用数据库的这种自动增加功能来生成主键值。
 *
 *         举例来说，如果你有一个实体类，其中的某个字段被注解为主键，并且使用了 @GeneratedValue(strategy = GenerationType.IDENTITY)，当你将该实体保存到数据库时，主键字段的值将由数据库自动分配，而不需要在代码中明确指定。这对于自动管理主键值以及避免主键冲突非常有用。
 *
 *         需要注意的是，GenerationType.IDENTITY 主要适用于支持自增主键的数据库，如MySQL，但并不适用于所有数据库。在某些数据库中，可能需要使用不同的主键生成策略。
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

    @Transient //临时字段，无需映射，不生成表字段
    private String age;
    // if you didn't set the column the Framework will Auto reflect the type and name.it's like @Column(name ="name",Length =8)
    private String name;
}
