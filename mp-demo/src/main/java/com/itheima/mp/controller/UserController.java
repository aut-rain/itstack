package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.common.result.Result;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: itstack
 * @description:
 * @author: zzy
 * @create: 2026-02-26 00:25
 **/
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {

    private final UserService userService;

    @Operation(summary = "新增用户接口")
    @PostMapping
    public Result<Void> save(@RequestBody @Parameter(description = "用户表单") UserFormDTO userFormDTO) {
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        userService.save(user);
        return Result.success();
    }

    @Operation(summary = "删除用户接口")
    @DeleteMapping("/{id}")
    public Result<Void> deleteUserById(@PathVariable @Parameter(description = "用户ID") Long id) {
        userService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询用户接口")
    @GetMapping("/{id}")
    public Result<UserVO> getUserById(@PathVariable @Parameter(description = "用户ID") Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return Result.success(userVO);
    }

    @Operation(summary = "根据ID批量查询用户接口")
    @GetMapping
    public Result<List<UserVO>> queryUserByIds(@RequestParam("ids") @Parameter(description = "用户ID列表") List<Long> ids) {
        List<User> userList = userService.listByIds(ids);
        List<UserVO> userVOList = BeanUtil.copyToList(userList, UserVO.class);
        return Result.success(userVOList);
    }

    @Operation(summary = "根据ID扣减余额接口")
    @PutMapping("/{id}/deduction/{money}")
    public Result<Void> deductionBalance(
            @PathVariable @Parameter(description = "用户ID") Long id,
            @PathVariable @Parameter(description = "扣减金额") Integer money) {
        try {
            userService.deductionBalance(id, money);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }

    @Operation(summary = "根据复杂条件查询用户接口")
    @GetMapping("/list")
    public Result<List<UserVO>> queryUsers(UserQuery userQuery) {
        List<User> userList = userService.queryUsers(userQuery);
        List<UserVO> userVOList = BeanUtil.copyToList(userList, UserVO.class);
        return Result.success(userVOList);
    }

}
