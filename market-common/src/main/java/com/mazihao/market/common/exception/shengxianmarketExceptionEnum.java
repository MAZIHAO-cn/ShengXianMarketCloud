package com.mazihao.market.common.exception;

/**
 * 异常枚举
 */
public enum shengxianmarketExceptionEnum {
    REQUEST_PARAM_ERROR(10001, "参数错误"),
    NO_ENUM(10002, "未找到对应的枚举类"),
    NEED_USER_NAME(10003, "用户名不能为空"),
    NEED_PASSWORD(10004, "密码不能为空"),
    PASSWORD_TOO_SHORT(10005, "密码长度不能小于8位"),
    NAME_NOT_PERMIT_SAME(10006, "不允许重名"),
    INSERT_FAILED(10007, "插入失败,请重试"),
    NEED_LOGIN(10008, "用户未登录"),
    NEED_ADMIN(10009,"无管理员权限"),
    UPDATE_FAILED(10010,"更新失败"),
    WRONG_PASSWORD(10011, "密码错误"),
    CREATE_FAILED(10012, "新增失败"),
    DELETE_FAILED(10013, "删除失败"),
    MKDIR_FAILED(10014, "文件夹创建失败"),
    UPLOAD_FAILED(10015, "图片上传失败"),
    NOT_SALE(10016, "商品状态不可售"),
    NOT_ENOUGH(10017, "商品库存不足"),
    CART_EMPTY(10018, "购物车已勾选的商品为空"),
    NO_ORDER(10019, "订单不存在"),
    NOT_YOUR_ORDER(10020, "订单不属于你"),
    WRONG_ORDER_STATUS(10021, "订单状态不符"),

    SYSTEM_ERROR(20000, "系统异常");

    // 异常码格式
    public Integer code;
    public String msg;
    shengxianmarketExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
