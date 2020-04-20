import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import { getAccessToken } from '@/util/auth'
Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
            import(/* webpackChunkName: "about" */ '../views/About.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
//
/**
 * 挂载路由导航守卫
 * to 将要访问的路径
 * from 来自于哪个路径
 * next 函数对象。表示放行（到哪个路径）
 */
router.beforeEach((to, from, next) => {
    if (to.path === '/login') return next()
    if (!getAccessToken()) return next('/login')
    next()
})

export default router
