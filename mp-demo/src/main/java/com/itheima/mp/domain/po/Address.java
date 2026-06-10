package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: itstack
 * @description: 地址
 * @author: zzy
 * @create: 2026-02-25 15:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("adress")
public class Address {
    /**
     * 地址id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id", exist = true)
    private Long userId;

    /**
     * 省份
     */
    @TableField(value = "province", exist = true)
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city", exist = true)
    private String city;

    /**
     * 区域
     */
    @TableField(value = "town", exist = true)
    private String town;

    /**
     * 手机号
     */
    @TableField(value = "mobile", exist = true)
    private String mobile;

    /**
     * 详细地址
     */
    @TableField(value = "street", exist = true)
    private String street;

    /**
     * 联系人
     */
    @TableField(value = "connect", exist = true)
    private String connect;

    /**
     * 是否默认地址 1默认 0否
     */
    @TableField(value = "is_default", exist = true)
    private byte isDefault;

    /**
     * 备注
     */
    @TableField(value = "notes", exist = true)
    private String notes;

    /**
     * 删除标记 1删除 0未删除
     */
    @TableField(value = "deleted", exist = true)
    private byte deleted;
}
