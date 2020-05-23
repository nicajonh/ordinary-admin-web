/**
 * 用户信息相关的API
 */
import request from '@/util/request'

export function pageUserList(data) {
    return request({
        url: '/user',
        method: 'get',
        params: data
    })
}
