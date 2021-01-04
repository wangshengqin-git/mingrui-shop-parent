package com.baidu.shop.global;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.status.HTTPStatus;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @ClassName GlobalException
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2020/12/24
 * @Version V1.0
 **/
@RestControllerAdvice//增强restController // springapo通知-->advice 代理
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = RuntimeException.class)
    public Result<JSONObject> testException(RuntimeException e){

        log.error("code : {} , message : {}",HTTPStatus.ERROR,e.getMessage());

        return new Result<JSONObject>(HTTPStatus.ERROR,e.getMessage(),null);
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public Map<String,Object> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) throws Exception{
        // == ===区别???
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",HTTPStatus.PARAMS_VALIDATE_ERROR);

        /*String message = "";

        //按需重新封装需要返回的错误信息
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            message += "Field --> " + error.getField() + " : " + error.getDefaultMessage() + ",";
            log.error("Field --> " + error.getField() + " : " + error.getDefaultMessage());
        }
        map.put("massage",message.substring(0,message.lastIndexOf(",")));
        */

        List<String> msgList = new ArrayList<>();

        /*for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            msgList.add("Field --> " + error.getField() + " : " + error.getDefaultMessage());
            log.error("Field --> " + error.getField() + " : " + error.getDefaultMessage());
        }*/
        exception.getBindingResult().getFieldErrors().stream().forEach(error -> {
            msgList.add("Field --> " + error.getField() + " : " + error.getDefaultMessage());
            log.error("Field --> " + error.getField() + " : " + error.getDefaultMessage());
        });

        //ArrayList 是线程不安全的 -->
        //hadoop --> HDFS(存储数据\文件) mapreduce(计算)
        //reverse   //gc --> gc垃圾回收器 ps + po
        String message = msgList.parallelStream().collect(Collectors.joining(","));

        map.put("massage",message);
        return map;
    }
}
