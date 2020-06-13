/**
 * 部门信息相关的API
 */
import request from '@/util/request'

const prefix = '/dept/'

export function pageList(data) {
    return request({
        url: prefix + 'list',
        method: 'post',
        data
    })
}

export function addModel(data) {
    return request({
        url: prefix,
        method: 'post',
        data
    })
}

export function fetchOne(entityId) {
    return request({
        url: prefix + entityId,
        method: 'get'
    })
}
export function updateEntityById(data) {
    return request({
        url: prefix + 'update',
        method: 'put',
        data
    })
}

export function removeEntityById(entityId) {
    return request({
        url: prefix + 'delete/' + entityId,
        method: 'delete'
    })
}

export function getTreeInfo() {
    return request({
        url: prefix + 'tree'  ,
        method: 'get'
    })
}
