package com.mazihao.market.common.exception;

/**
 * 统一异常
 */
public class shengxianmarketException extends RuntimeException{
    private final Integer code;
    private final String message;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public shengxianmarketException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public shengxianmarketException(shengxianmarketExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
