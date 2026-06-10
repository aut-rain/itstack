package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username", exist = true)
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password", exist = true)
    private String password;

    /**
     * 注册手机号
     */
    @TableField(value = "phone", exist = true)
    private String phone;

    /**
     * 详细信息
     */
    @TableField(value = "info", exist = true)
    private String info;

    /**
     * 使用状态（1正常 2冻结）
     */
    @TableField(value = "status", exist = true)
    private Integer status;

    /**
     * 账户余额
     */
    @TableField(value = "balance", exist = true)
    private Integer balance;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", exist = true)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", exist = true)
    private LocalDateTime updateTime;
}
