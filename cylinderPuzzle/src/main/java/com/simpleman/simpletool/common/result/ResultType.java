package com.simpleman.simpletool.common.result;

import java.io.Serializable;

public interface ResultType extends Serializable {
    /**
     * 状态码
     *
     * @return String
     */
    String getCode();

    /**
     * 结果类别
     *
     * @return String
     */
    String getType();

    /**
     * 消息
     *
     * @return string
     */
    String getMessage();
}
