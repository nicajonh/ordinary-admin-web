package com.llh.server.service.sys

import com.llh.server.model.SysLog
import com.llh.server.pojo.PageDTO
import com.llh.server.pojo.SimplePageQueryVO
import com.llh.server.service.BasicService

/**
 * SysLogService
 *
 * CreatedAt: 2020-06-21 16:35
 *
 * @author llh
 */
interface SysLogService : BasicService<SysLog> {
    /**
     * 简单信息的分页信息
     */
    fun page(queryVO: SimplePageQueryVO<SysLog>): PageDTO<SysLog>
}
