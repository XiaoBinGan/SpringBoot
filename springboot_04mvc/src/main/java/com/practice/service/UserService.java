package com.practice.service;


import com.practice.pojo.User;

import java.util.List;

public interface UserService {

    /**
     * 获取所有用户信息。
     *
     * @return 包含所有用户信息的列表
     */
    List<User> findUser();

    /**
     * 根据用户ID查找特定用户。
     *
     * @param id 用户ID
     * @return 包含用户信息的User对象，如果未找到则返回null
     */
    User findUserById(long id);

    /**
     * 保存新用户信息。
     *
     * @param user 包含新用户信息的User对象
     */
    void saveUser(User user);

    /**
     * 更新现有用户信息。
     *
     * @param user 包含更新后用户信息的User对象
     */
    void updateUser(User user);

    /**
     * 根据用户ID删除特定用户。
     *
     * @param id 要删除的用户ID
     */
    void deleteUerById(Long id);}
