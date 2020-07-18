import request from '@/util/request'

const prefix = '/code-gen/'

function tableList() {
    return request({
        url: prefix + 'tables',
        method: 'get'
    })
}
function codeGen(data) {
    return request({
        url: prefix + 'gen',
        method: 'post',
        data
    })
}
export default {
    tableList,
    codeGen
}
