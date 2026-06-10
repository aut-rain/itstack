package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;

import java.util.List;

/**
 * @program: itstack
 * @description:
 * @author: zzy
 * @create: 2026-02-25 23:39
 **/
public interface UserService extends IService<User> {
    void deductionBalance(Long id, Integer money);

    List<User> queryUsers(UserQuery userQuery);
}
