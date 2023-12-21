package com.practice.dao;

import com.practice.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
//仅需要继承父接口，JpaRepository<T, ID>父接口中已经提供了CRUD操作，自己无需声明这些方法。
//当你继承了JPA的父类之后便不用再定义映射配置文件了，SQL语句通过正向开发自动生成，特殊场景除外。
public interface UserDao extends JpaRepository<User,Long> {
}

