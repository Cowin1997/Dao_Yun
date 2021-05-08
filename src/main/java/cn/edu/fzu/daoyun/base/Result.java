package cn.edu.fzu.daoyun.base;

import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="基础返回类",description="基础返回类")
public class Result<T> implements Serializable {
    @ApiModelProperty(required=true,position=1,value="响应状态码",example = "2001")
    private Integer code;
    // 业务提示语
    @ApiModelProperty(required=true,position=3,value="响应消息",example = "登录成功")
    private String msg;
    // 业务提示语
    @ApiModelProperty(required=true,position=5,value="错误消息",example = "登录类型参数不支持")
    private String error;
    // 数据对象
    @ApiModelProperty(required=true,position=2,value="响应数据",example = "token")
    private T data;
    // 是否成功
    @ApiModelProperty(required=true,position=4,value="请求是否成功",example = "true")
    private Boolean success;

    private Result(ResultCodeEnum resultCode){
        this.setCode(resultCode.getCode());     //状态码
        this.setMsg(resultCode.getMessage());   //消息
    }

    public static<T> Result<T> success(ResultCodeEnum codeEnum, T data){
        Result<T> result = new Result<T>(codeEnum);
        result.setData(data); //设置数据
        result.setSuccess(true); //请求成功
        return result;
    }

    public static<T> Result<T> success(ResultCodeEnum codeEnum){
        Result<T> result = new Result<T>(codeEnum);
        result.setSuccess(true); //请求成功
        return result;
    }
    public static<T> Result<T> failure(ResultCodeEnum errorCode){
        Result<T> result = new Result<T>(errorCode);
        //失败不设置数据
        result.setSuccess(false); //请求失败
        return result;
    }
    public static<T> Result<T> failure(ResultCodeEnum errorCode, String error){
        Result<T> result = new Result<T>(errorCode);
        result.setError(error); //设置数据
        result.setSuccess(false); //请求成功
        return result;
    }





}
