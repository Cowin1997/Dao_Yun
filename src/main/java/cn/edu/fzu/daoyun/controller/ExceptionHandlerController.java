package cn.edu.fzu.daoyun.controller;

import cn.edu.fzu.daoyun.base.Result;
import cn.edu.fzu.daoyun.constant.ResultCodeEnum;
import cn.edu.fzu.daoyun.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<List<String>>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errorMsgs = new ArrayList<>();
        allErrors.forEach(objectError -> {
            StringBuilder errorMsg = new StringBuilder();
            FieldError fieldError = (FieldError)objectError;
            errorMsg.append(fieldError.getField());
            errorMsg.append(":");
            errorMsg.append(fieldError.getDefaultMessage());
            errorMsgs.add(errorMsg.toString());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure(ResultCodeEnum.INVALID_PARAM,errorMsgs.toString()));
    }

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Result> badRequestException(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure(ResultCodeEnum.INVALID_PARAM,e.getMessage()));
    }

}
