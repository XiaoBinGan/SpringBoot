package com.practice.service.impl;

import com.practice.dao.UserDao;
import com.practice.pojo.User;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceimpl implements UserService {


    @Autowired
    UserDao userDao;


    @Override
    public List<User> findUser() {
        return userDao.findAll();
    }


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

    @Override
    public void saveUser(User user) {
        //user对象主键值为null，进行保存操作
        userDao.save(user);
    }

    @Override
    public void updateUser(User user) {
        //user对象含有主键值，进行更新操作
        userDao.save(user);//一个方法两用
    }

    @Override
    public void deleteUerById(Long id) {
        userDao.deleteById(id);
    }
}
