package com.itheima.mp.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户查询条件实体")
public class UserQuery {

    @Schema(description = "用户名关键字", example = "张三")
    private String name;

    @Schema(description = "用户状态：1-正常，2-冻结", example = "1")
    private Integer status;

    @Schema(description = "余额最小值", example = "100")
    private Integer minBalance;

    @Schema(description = "余额最大值",example = "10000")
    private Integer maxBalance;
}
