package com.practice.dao;


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
