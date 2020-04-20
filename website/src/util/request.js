import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import { getToken, getRefreshToken, removeToken } from '@/util/auth'
// create an axios instance
const service = axios.create({
    baseURL: 'http://localhost:7777/dev-api', // url = base url + request url

    // withCredentials: true, // send cookies when cross-domain requests
    timeout: 5000 // request timeout
})

// request interceptor

service.interceptors.request.use(
    config => {
        if (getToken() && getRefreshToken()) {
            config.headers['authorization'] = getToken()
            config.headers['refresh_token'] = getRefreshToken()
        }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)
// response interceptor
service.interceptors.response.use(response => {
    const res = response.data
    if (res.code !== 200000) {
        Message({
            message: res.msg || 'Error',
            type: 'error',
            duration: 5 * 1000
        })
        // TODO 这里的异常代码没有规划好！
        if (res.code >= 401010 && res.code <= 401090) {
            MessageBox.confirm(
                'You have been logged out, you can cancel to stay on this page, or log in again',
                'Confirm logout',
                {
                    confirmButtonText: 'Re-Login',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }
            ).then(() => {
                // store.dispatch('user/resetToken').then(() => {
                //     location.reload()
                // })
                // TODO 此处需要测试
                removeToken()
                location.reload()
            })
            return Promise.reject(new Error(res.msg || 'Error'))
        }
    } else {
        return res
    }
})
export default service
