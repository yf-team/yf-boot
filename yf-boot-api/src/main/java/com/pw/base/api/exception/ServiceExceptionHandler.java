package com.pw.base.api.exception;

import com.pw.base.api.api.ApiRest;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 * @author bool
 * @date 2019-06-21 19:27
 */
@RestControllerAdvice
public class ServiceExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initWebBinder(WebDataBinder binder){

    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttribute(Model model) {

    }

    /**
     * 捕获ServiceException
     * @param e
     * @return
     */
    @ExceptionHandler({ServiceException.class})
    @ResponseStatus(HttpStatus.OK)
    public ApiRest serviceExceptionHandler(ServiceException e) {
        return new ApiRest(e);
    }


    /**
     * Shiro异常
     * @param e
     * @return
     */
    @ExceptionHandler({AuthorizationException.class})
    @ResponseStatus(HttpStatus.OK)
    public ApiRest shiroExceptionHandler(AuthorizationException e) {
        ApiRest rest = new ApiRest();
        rest.setMsg("您无权执行此操作，请联系管理员！");
        rest.setCode(-1);
        return rest;
    }


    /**
     * 处理数据校验返回
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiRest HandleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ApiRest apiRest = new ApiRest();
        apiRest.setCode(1);
        apiRest.setMsg(e.getBindingResult().getFieldError().getDefaultMessage());
        return apiRest;
    }


}
