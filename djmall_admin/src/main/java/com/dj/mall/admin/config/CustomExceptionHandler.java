package com.dj.mall.admin.config;

import com.alibaba.fastjson.JSONObject;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.ResultModel;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 自定义异常处理器
 * @author 86150
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ResultModel businessExceptionHandler(BusinessException ex) {
        ex.printStackTrace();
        return new ResultModel().error(ex.getErrorCode(), ex.getErrorMsg());
    }

    /**
     * 参数异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultModel illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ResultModel().error(ex.getMessage());
    }

    /**
     * 参数异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IllegalStateException.class)
    public ResultModel illegalStateExceptionExceptionHandler(IllegalStateException ex) {
        ex.printStackTrace();
        return new ResultModel().error(ex.getMessage());
    }

    /**
     * 未授权异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public void unauthorizedExceptionHandler(HttpServletRequest request, HttpServletResponse response, UnauthorizedException ex) {
        ex.printStackTrace();
        try {
            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.setStatus(HttpStatus.OK.value());
                response.setContentType("text/json;charset=UTF-8");
                response.getWriter().print(JSONObject.toJSON(new ResultModel().error(403, "403")));
            } else {
                response.sendRedirect(request.getContextPath() + "/403.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 未知异常处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResultModel exceptionHandler(Exception ex) {
        ex.printStackTrace();
        return new ResultModel().error(-2, "服务器在开小差，请稍后再试");
    }

}
