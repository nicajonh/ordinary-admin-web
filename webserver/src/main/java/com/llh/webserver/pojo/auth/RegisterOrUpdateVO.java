package com.llh.webserver.pojo.auth;

import com.llh.webserver.model.SysUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * RegisterOrUpdateVO 用户注册或更新信息的VO类
 * <p>
 * CreatedAt: 2020-04-18 18:03
 *
 * @author llh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户注册VO类")
public class RegisterOrUpdateVO extends SysUser {
    private String password;
}
