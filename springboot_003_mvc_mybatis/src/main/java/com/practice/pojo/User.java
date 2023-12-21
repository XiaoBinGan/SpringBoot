package com.practice.pojo; // 声明包名，这个类位于com.practice.pojo包下

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter // 自动生成getter方法
@Setter // 自动生成setter方法
public class User implements Serializable { // 定义一个名为User的类，实现了Serializable接口，表示该对象可以序列化

    String username; // 声明一个字符串类型的字段，表示用户名
    String password; // 声明一个字符串类型的字段，表示密码
    Integer age; // 声明一个整数类型的字段，表示年龄
    String sex; // 声明一个字符串类型的字段，表示性别
}