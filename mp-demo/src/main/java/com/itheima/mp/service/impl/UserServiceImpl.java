package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.common.exception.BusinessException;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: itstack
 * @description:
 * @author: zzy
 * @create: 2026-02-25 23:39
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deductionBalance(Long id, Integer money) {

        User user = getById(id);

        if (user == null || user.getStatus() != 1){
            throw new BusinessException(1000,"用户不存在或状态异常");
        }

        if (user.getBalance() < money){
            throw new BusinessException(2000,"用户余额不足");
        }

        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getId, id);
        baseMapper.updateBalanceById(wrapper, money);
    }

    @Override
    public List<User> queryUsers(UserQuery userQuery) {
        String name = userQuery.getName();
        Integer status = userQuery.getStatus();
        Integer maxBalance = userQuery.getMaxBalance();
        Integer minBalance = userQuery.getMinBalance();
        List<User> list = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();
        return list;
    }
}
