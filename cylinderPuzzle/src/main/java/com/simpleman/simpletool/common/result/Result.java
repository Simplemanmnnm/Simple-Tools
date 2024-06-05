package com.simpleman.simpletool.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String type;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回结果内容
     */
    private T data;

    public Result(ResultType resultType) {
        this(resultType, null);
    }

    public Result(String code, String message, String type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public Result(ResultType resultType, T data) {
        this.code = resultType.getCode();
        this.message = resultType.getMessage();
        this.type = resultType.getType();
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.FAILURE);
    }

    public static <T> Result<T> fail(ResultType resultType, String message) {
        return new Result<>(resultType.getCode(), message, resultType.getType());
    }
}
