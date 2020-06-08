package com.llh.webserver.common.exception.handler;

import com.llh.webserver.common.exception.BusinessException;
import com.llh.webserver.common.exception.msg.BasicExpEnum;
import com.llh.webserver.pojo.JsonWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * RestExceptionHandler
 * <p>
 * CreatedAt: 2020-04-20 22:15
 *
 * @author llh
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public JsonWrapper exception(Exception e) {
        log.error("Exception : {} ", e.getMessage(), e);
        return JsonWrapper.exception(BasicExpEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public JsonWrapper runtimeException(RuntimeException e) {
        log.error("Exception : {} ", e.getMessage(), e);
        return JsonWrapper.exception(BasicExpEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public JsonWrapper exception(AuthenticationException e) {
        log.error("AuthenticationException : {} ", e.getMessage(), e);
        return JsonWrapper.exception(BasicExpEnum.AUTH_ACCOUNT_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    public JsonWrapper businessException(BusinessException e) {
        log.error("BusinessException : {} ", e.getMessage(), e);
        return JsonWrapper.exception(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public JsonWrapper exception(AccessDeniedException e) {
        log.error("AccessDeniedException : {} ", e.getMessage(), e);
        return JsonWrapper.exception(BasicExpEnum.AUTH_DENIED_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonWrapper exception(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : {} ", e.getMessage(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return JsonWrapper.exception(BasicExpEnum.DATA_VALIDATE_ERROR, assembleExpInfo(errors));
    }

    private String[] assembleExpInfo(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        for (int i = 0; i < errors.size(); i++) {
            msgs[i] = errors.get(i).getDefaultMessage();
        }
        return msgs;
    }
}
