package com.practice.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Result implements Serializable {
    private boolean status; // 响应状态，true表示成功，false表示失败
    private String message; // 响应消息
    private Object data; // 响应数据

    /**
     * 创建一个表示成功操作的Result对象
     *
     * @param data 响应数据
     * @return Result对象
     */
    public static Result ok(Object data) {
        Result result = new Result();
        result.setStatus(true);
        result.setMessage("Response is successful");
        result.setData(data);
        return result;
    }

    /**
     * 创建一个表示失败操作的Result对象
     *
     * @param data 响应数据
     * @return Result对象
     */
    public static Result error(Object data) {
        Result result = new Result();
        result.setStatus(false);
        result.setMessage("Response is unsuccessful");
        result.setData(data);
        return result;
    }
}

