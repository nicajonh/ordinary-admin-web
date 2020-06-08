package com.llh.webserver.pojo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * AuthTokenVO 认证令牌信息类
 * <p>
 * CreatedAt: 2020-04-18 18:06
 *
 * @author llh
 */
@Data
@Accessors(chain = true)
@ApiModel("认证令牌模型类")
public class AuthTokenVO {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "正常的业务token")
    private String accessToken;
    @ApiModelProperty(value = "刷新token")
    private String refreshToken;
    @ApiModelProperty(value = "用户名")
    private String username;
}
