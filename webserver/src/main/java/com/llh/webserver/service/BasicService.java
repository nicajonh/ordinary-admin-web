package com.llh.webserver.service;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ArrayUtil;
import com.llh.webserver.common.constant.DataStatus;
import com.llh.webserver.model.BasicModel;

import java.time.LocalDateTime;

/**
 * BasicService
 * 基本服务层。
 * <p>
 * CreatedAt: 2020-04-18 16:43
 *
 * @author llh
 */
public interface BasicService<T extends BasicModel> {
    /**
     * 数据移除状态
     */
    int REMOVE = DataStatus.REMOVE;
    /**
     * 数据保存状态
     */
    int PERSISTENCE = DataStatus.PERSISTENCE;

    T save(T entity);

    T remove(String id);

    T updateById(T entity);

    T findById(String id);

    default LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    /**
     * 获取属性拷贝工具配置
     *
     * @return 属性拷贝工具的配置
     */
    default MyCopyOptions getCopyOptions() {
        return new MyCopyOptions();
    }
}

/**
 * 属性拷贝工具配置拓展。
 * 默认忽略空值，可以再添加忽略属性。
 */
class MyCopyOptions extends CopyOptions {
    public MyCopyOptions() {
        // 默认忽略空值
        this.setIgnoreNullValue(true);
        // 设置默认忽略的属性。
        this.setIgnoreProperties("password", "version", "id");
    }

    /**
     * 向已有的属性列表中再添加属性
     * 因为拓展方法，不支持链式调用
     *
     * @param prop 不需要拷贝的属性
     */
    public void addIgnoreProperties(String... prop) {
        ArrayUtil.append(this.ignoreProperties, prop);
    }
}
