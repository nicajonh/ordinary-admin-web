/**
 *  授权相关的VO类。
 *
 * CreatedAt: 2020-06-06 15:33
 *
 * @author llh
 */
package com.llh.server.pojo.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank


@ApiModel("登录信息的VO类")
data class LoginVO(
    @field:NotBlank(message = "用户名不能为空")
    @ApiModelProperty(required = true, value = "用户名")
    val username: String,
    @field:NotBlank(message = "密码不能为空")
    @ApiModelProperty(required = true, value = "密码")
    val password: String
)

@ApiModel("认证令牌模型类")
data class AuthTokenVO(
    @ApiModelProperty(value = "访问token")
    val accessToken: String,
    @ApiModelProperty(value = "刷新token")
    val refreshToken: String,
    @ApiModelProperty(value = "用户名")
    val username: String
)
