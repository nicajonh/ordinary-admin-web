<template>
    <el-container class="home-container">
        <el-header>
            <div>
                <el-avatar :size="50" :src="circleUrl"></el-avatar>
                <span>管理系统</span>
            </div>
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
    data() {
        return {
            circleUrl:
                'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        }
    },
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
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #fff;
    font-size: 20px;
    > div {
        display: flex;
        align-items: center;
        > span {
            margin-left: 15px;
        }
    }
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
