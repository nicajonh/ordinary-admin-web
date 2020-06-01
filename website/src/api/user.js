/**
 * 用户信息相关的API
 */
import request from '@/util/request'

export function pageUserList(data) {
    return request({
        url: '/user/list',
        method: 'post',
        data
    })
}

export function addUser(data) {
    return request({
        url: '/user',
        method: 'post',
        data
    })
}

export function fetchUser(userId) {
    return request({
        url: '/user/' + userId,
        method: 'get'
    })
}
export function updateUser(data) {
    return request({
        url: '/user/update',
        method: 'post',
        data
    })
}

export function removeUser(id) {
    return request({
        url: '/user/remove/' + id,
        method: 'get'
    })
}
