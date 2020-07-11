/**
 * ${modelDescrption}的API
 *
 * CreatedAt: ${.now?datetime}
 *
 * @author ${auth}
 */
import request from '@/util/request'

const prefix = '${urlPrefix}'

module.exports={
    removeEntityById(entityId) {
        return request({
            url: prefix + 'delete/' + entityId,
            method: 'delete'
        })
    },
    updateEntityById(data) {
        return request({
            url: prefix + 'update',
            method: 'put',
            data
        })
    },
    fetchOne(entityId) {
        return request({
            url: prefix + entityId,
            method: 'get'
        })
    },
    addModel(data) {
        return request({
            url: prefix,
            method: 'post',
            data
        })
    },
    pageList(data) {
        return request({
            url: prefix + 'page',
            method: 'post',
            data
        })
    }
}
