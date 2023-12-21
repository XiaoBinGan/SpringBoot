package com.practice.service.impl;

import com.practice.dao.UserMapper;
import com.practice.pojo.User;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 这是一个用户服务的实现类。
 * 该类使用@Service注解将其标记为Spring的服务组件，
 * 并使用@Transactional注解指定了事务的范围。
 *
 * 类中的方法完成了对用户的增删改查操作，
 * 通过@Override注解表明这些方法是接口UserService中定义的方法的实现。
 * 方法中使用UserMapper来执行对数据库的操作，比如插入、更新、查询等。
 */

@Service
@Transactional
public class UserServiceimpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有用户，先从缓存中获取，如果缓存中没有，则从数据库中获取，并将结果存入缓存。
     *
     * @return 用户列表
     */
    @Override
    public List<User> findUser() {
        // 尝试从缓存中获取用户列表
        List<User> userList = (List<User>) redisTemplate.boundValueOps("UserList").get();
        // 如果缓存中存在数据，直接返回缓存中的数据
        if (userList != null && !userList.isEmpty()) {
            System.out.println("Read the data from the cache. List: " + userList);
            return userList;
        }
        // 缓存中没有数据，从数据库中获取用户列表
        userList = userMapper.selectAll();
        // 将从数据库中获取的用户列表存入缓存
        redisTemplate.boundValueOps("UserList").set(userList);
        System.out.println("Read the data from the database. List: " + userList);
        return userList;
    }

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

    /**
     * 保存用户
     *
     * @param user 用户对象
     */
    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }

    /**
     * 更新用户
     *
     * @param user 用户对象
     */
    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
    }

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


