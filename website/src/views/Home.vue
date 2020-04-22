<template>
    <el-container class="home-container">
        <el-header>
            Header
            <el-button type="info" @click="logout">logout</el-button>
        </el-header>
        <el-container>
            <el-aside width="200px">Aside</el-aside>
            <el-main>Main</el-main>
        </el-container>
    </el-container>
</template>

<script>
import { removeToken } from '@/util/auth'

export default {
    name: 'Home',
    methods: {
        logout() {
            removeToken()
            this.$router.push('/login')
        }
    }
}
</script>
<style lang="less" scoped>
.el-header {
    background-color: #373d41;
}
.el-aside {
    background-color: #333744;
}
.el-main {
    background-color: #eaedf1;
}
.home-container {
    height: 100%;
}
</style>
