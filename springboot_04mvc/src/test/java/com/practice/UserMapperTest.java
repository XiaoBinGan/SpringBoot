package com.practice;


import com.practice.dao.UserMapper;
import com.practice.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @RunWith(SpringRunner.class) 和 @SpringBootTest 注解用于在测试环境中启动 Spring Boot 应用上下文。
 * @Autowired 注解用于将 UserMapper bean 注入到测试类中。
 * @Test 注解标记了测试方法，测试方法调用了 userMapper.selectAll() 查询所有用户，并打印了查询结果。
 * 请确保 UserMapper 接口定义了正确的查询方法，且 MyBatis 的配置正确。此外，检查测试类所在的包路径是否能够正确扫描到 UserMapper 接口。运行该测试类时，你应该能够看到查询结果输出到控制台。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> list = userMapper.selectAll();
        for (User user : list
        ) {
            System.out.println("user" + user.getId() + "=" + user);
        }
    }
}



