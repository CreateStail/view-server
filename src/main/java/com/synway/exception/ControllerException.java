package com.synway.exception;

import com.synway.utils.JsonData;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerException {

/*    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public JsonData handle401(ShiroException e){
        return JsonData.buildError(401,e.getMessage(),null);
    }*/


    @ExceptionHandler(ShiroException.class)
    public JsonData doHandleShiroException(ShiroException e) {
        JsonData r=null;
        if(e instanceof UnknownAccountException) {
            r=JsonData.buildError("用户不存在");
        }else if(e instanceof LockedAccountException) {
            r=JsonData.buildError("账户已被禁用");
        }else if(e instanceof IncorrectCredentialsException) {
            r=JsonData.buildError("用户名或者密码不正确");
        }else if(e instanceof AuthorizationException) {
            r=JsonData.buildError("没有此操作权限");
        }else {
            r=JsonData.buildError("系统维护中");
        }
        return r;
    }



/*    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public JsonData handler401(){
        return JsonData.buildError(401,"Unauthorized",null);
    }*/

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonData globalException(HttpServletRequest request, Throwable ex) {
        return JsonData.buildError(getStatus(request).value(), ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
