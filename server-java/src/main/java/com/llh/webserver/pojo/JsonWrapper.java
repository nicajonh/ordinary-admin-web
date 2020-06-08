package com.llh.webserver.pojo;

import com.llh.webserver.common.exception.msg.RespCodeMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * JsonWrapper
 * <p>
 * CreatedAt: 2020-04-18 17:48
 *
 * @author llh
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@ApiModel("响应数据模型")
public class JsonWrapper {
    @ApiModelProperty("自定义响应代码")
    private Integer code;
    @ApiModelProperty("响应信息")
    private String msg;
    @ApiModelProperty("响应数据")
    private Object data;

    public JsonWrapper(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonWrapper ok(Object data) {
        return new JsonWrapper(RespCodeMsg.SUCCESS_CODE, "", data);
    }

    public static JsonWrapper wrapper(Integer code, String msg, Object data) {
        return new JsonWrapper(code, msg, data);
    }

    public static JsonWrapper exception(Integer code, String msg) {
        return new JsonWrapper(code, msg, "");
    }

    public static JsonWrapper exception(RespCodeMsg respInfo) {
        return new JsonWrapper(respInfo.getCode(), respInfo.getMsg(), "");
    }

    public static JsonWrapper exception(RespCodeMsg respInfo, Object data) {
        return new JsonWrapper(respInfo.getCode(), respInfo.getMsg(), data);
    }
}
