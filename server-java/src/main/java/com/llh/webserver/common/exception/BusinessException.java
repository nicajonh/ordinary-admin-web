package com.llh.webserver.common.exception;

import com.llh.webserver.common.exception.msg.RespCodeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BusinessException
 * <p>
 * CreatedAt: 2020-04-18 17:20
 *
 * @author llh
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    /**
     * 异常 code
     */
    private final Integer code;

    /**
     * 异常提示
     */
    public final String msg;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(RespCodeMsg respInfo) {
        super(respInfo.getMsg());
        this.code = respInfo.getCode();
        this.msg = respInfo.getMsg();
    }
}
