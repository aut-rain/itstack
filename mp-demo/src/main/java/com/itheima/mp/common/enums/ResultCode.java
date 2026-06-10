package com.itheima.mp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一错误码枚举
 * 
 * 错误码规范:
 * - 1xxx: 通用错误
 *
 * @author zzy
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // ==================== 通用错误 1xxx ====================
    /**
     * 成功
     */
    SUCCESS("200", "操作成功"),
    
    /**
     * 系统异常
     */
    SYSTEM_ERROR("500", "系统异常,请联系管理员"),
    
    /**
     * 参数校验失败
     */
    PARAM_INVALID("1001", "参数校验失败"),
    
    /**
     * 请求方法不支持
     */
    METHOD_NOT_SUPPORTED("1002", "请求方法不支持"),
    
    /**
     * 资源不存在
     */
    RESOURCE_NOT_FOUND("1003", "请求的资源不存在"),
    
    /**
     * 业务异常
     */
    BUSINESS_ERROR("1004", "业务处理失败"),
    
    /**
     * 参数错误
     */
    PARAM_ERROR("1005", "参数错误");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String message;

    /**
     * 根据错误码获取枚举
     *
     * @param code 错误码
     * @return 错误码枚举
     */
    public static ResultCode fromCode(String code) {
        for (ResultCode errorCode : ResultCode.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return SYSTEM_ERROR;
    }

    /**
     * 判断是否为成功状态
     *
     * @param code 错误码
     * @return 是否成功
     */
    public static boolean isSuccess(String code) {
        return SUCCESS.getCode().equals(code);
    }
}
