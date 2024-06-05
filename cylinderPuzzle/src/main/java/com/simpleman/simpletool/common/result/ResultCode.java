package com.simpleman.simpletool.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ResultCode implements ResultType {

    /**
     * 操作成功
     */
    SUCCESS("200", "success", "操作成功!"),

    /**
     * 动态错误信息
     */
    FAILURE("500", "SYSTEM", "{}");

    /**
     * 结果码
     */
    private String code;

    /**
     * 结果类别
     */
    private String type;

    /**
     * 结果描述
     */
    private String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
