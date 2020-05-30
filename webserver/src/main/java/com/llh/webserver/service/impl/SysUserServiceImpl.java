package com.llh.webserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.llh.webserver.common.exception.BusinessException;
import com.llh.webserver.common.exception.msg.BasicExpEnum;
import com.llh.webserver.model.QSysUser;
import com.llh.webserver.model.SysUser;
import com.llh.webserver.pojo.SimplePageQueryVO;
import com.llh.webserver.repository.SysUserRepo;
import com.llh.webserver.service.SysUserService;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        checkVersion(user, entity);
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

    @Override
    public Page<SysUser> pageQuery(SimplePageQueryVO<SysUser> queryVO) {
        if (queryVO == null || queryVO.getPageNumber() == null || queryVO.getPageSize() == null) {
            log.error("分页查询参数缺失：{}。", queryVO);
            throw new BusinessException(BasicExpEnum.DATA_VALIDATE_ERROR);
        }
        Predicate predicate = handleQueryParam(queryVO);
        PageRequest pageRequest = handlePageAndOrderParam(queryVO);
        return null == predicate
            ? userRepo.findAll(pageRequest)
            : userRepo.findAll(predicate, pageRequest); // 第一个参数不能为null，真的麻烦。
    }

    private PageRequest handlePageAndOrderParam(SimplePageQueryVO<SysUser> queryVO) {
        Sort sort = Sort.unsorted();
        if (StrUtil.isNotBlank(queryVO.getOrderField()))
            sort = Sort.by(StrUtil.isNotBlank(queryVO.getOrderType())
                ? Sort.Direction.fromString(queryVO.getOrderType())
                : Sort.Direction.ASC, queryVO.getOrderField());
        return PageRequest.of(queryVO.getPageNumber(), queryVO.getPageSize(), sort);
    }

    private Predicate handleQueryParam(SimplePageQueryVO<SysUser> queryVO) {
        if (null == queryVO.getModel()) return null;
        SysUser model = queryVO.getModel();
        QSysUser queryUser = QSysUser.sysUser;
        Predicate predicate = null;
        if (StrUtil.isNotBlank(model.getUsername())) {
            predicate = queryUser.username.like(model.getUsername());
        }
        return predicate;
    }
}
