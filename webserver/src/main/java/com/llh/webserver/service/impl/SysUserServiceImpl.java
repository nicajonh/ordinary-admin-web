package com.llh.webserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.llh.webserver.common.exception.BusinessException;
import com.llh.webserver.common.exception.msg.BasicExpEnum;
import com.llh.webserver.model.SysUser;
import com.llh.webserver.repository.SysUserRepo;
import com.llh.webserver.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * SysUserServiceImpl
 * <p>
 * CreatedAt: 2020-04-18 17:00
 *
 * @author llh
 */
@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public SysUser save(SysUser entity) {
        entity.setCreatedAt(getNow());
        entity.setUpdatedAt(getNow());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setDataStatus(PERSISTENCE);
        entity.setAccountStatus(ACTIVE_ACCOUNT);
        return userRepo.save(entity);
    }

    @Override
    @Transactional
    public SysUser remove(String id) {
        SysUser user = findById(id);
        user.setDataStatus(REMOVE);
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public SysUser updateById(SysUser entity) {
        SysUser user = findById(entity.getId());
        if (StrUtil.isNotBlank(entity.getNewPassword()))
            user.setPassword(passwordEncoder.encode(entity.getNewPassword()));
        BeanUtil.copyProperties(entity, user, getCopyOptions());
        user.setUpdatedAt(getNow());
        return userRepo.save(user);
    }

    @Override
    public SysUser findById(String id) {
        Optional<SysUser> optional = userRepo.findById(id);
        if (!optional.isPresent()) {
            log.debug(" 找不到 SysUser 数据，id为：{} 。", id);
            throw new BusinessException(BasicExpEnum.DATA_NONENTITY_ERROR);
        }
        return optional.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SysUser> optional = userRepo.findTopByUsername(username);
        if (!optional.isPresent()) {
            log.info("找不到用户名为 {} 的数据。", username);
            throw new BusinessException(BasicExpEnum.Auth_ERROR);
        }
        return optional.get();
    }
}
