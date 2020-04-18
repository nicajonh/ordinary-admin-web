package com.llh.webserver.pojo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * LoginVO 登录信息的VO类
 * <p>
 * CreatedAt: 2020-04-18 17:57
 *
 * @author llh
 */
@Data
@ApiModel("登录信息的VO类")
public class LoginVO {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(required = true, value = "用户名")
    private String username;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(required = true, value = "密码")
    private String password;
}
