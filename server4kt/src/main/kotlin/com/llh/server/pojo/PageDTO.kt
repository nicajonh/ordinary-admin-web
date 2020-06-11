package com.llh.server.pojo

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * PageDTO
 *
 * CreatedAt: 2020-06-06 21:23
 *
 * @author llh
 */
data class PageDTO<T>(
    val content: List<T>?,
    val totalElements: Int,
    @JsonIgnore
    val pageSize: Int
) {
    val totalPage: Int
        get() = totalElements / pageSize
}
