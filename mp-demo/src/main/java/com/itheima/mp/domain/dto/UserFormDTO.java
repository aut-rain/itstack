package com.itheima.mp.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户表单实体")
public class UserFormDTO {

    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    @Schema(description = "密码", example = "123456")
    private String password;

    @Schema(description = "注册手机号", example = "13800138000")
    private String phone;

    @Schema(description = "详细信息，JSON风格", example = "{\"age\": 25, \"intro\": \"挨踢大叔\", \"gender\": \"male\"}")
    private String info;

    @Schema(description = "账户余额", example = "10000")
    private Integer balance;
}
