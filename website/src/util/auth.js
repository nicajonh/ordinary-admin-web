import Cookies from 'js-cookie'

const TokenKey = 'website_auth_token'
const AccessToken = 'website_access_token'
const RefreshToken = 'website_refresh_token'

export function getToken() {
    return Cookies.get(TokenKey)
}
export function getAccessToken() {
    return Cookies.get(AccessToken)
}
export function getRefreshToken() {
    return Cookies.get(RefreshToken)
}

export function setAccessToken(accessToken) {
    return Cookies.set(AccessToken, accessToken)
}

export function setRefreshToken(refreshToken) {
    return Cookies.set(RefreshToken, refreshToken)
}

export function setToken(token) {
    return Cookies.set(TokenKey, token)
}

export function removeToken() {
    Cookies.remove(AccessToken)
    Cookies.remove(RefreshToken)
    return Cookies.remove(TokenKey)
}
