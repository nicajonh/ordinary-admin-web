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
            <el-aside :width="isCollapse ? '65px' : '200px'">
                <div class="toggle-btn" @click="toggleCollapse">|||</div>
                <el-menu
                    :collapse="isCollapse"
                    :collapse-transition="false"
                    unique-opened
                    router
                    background-color="#545c64"
                    text-color="#fff"
                    active-text-color="#409eff"
                    :default-active="activePath"
                >
                    <!-- <el-submenu index="1">
                        <template slot="title">
                            <i class="el-icon-location"></i>
                            <span>导航一</span>
                        </template>
                        <el-menu-item-group>
                            <template slot="title">分组一</template>
                            <el-menu-item index="1-1">选项1</el-menu-item>
                            <el-menu-item index="1-2">选项2</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group title="分组2">
                            <el-menu-item index="1-3">选项3</el-menu-item>
                        </el-menu-item-group>
                        <el-submenu index="1-4">
                            <template slot="title">选项4</template>
                            <el-menu-item index="1-4-1">选项1</el-menu-item>
                        </el-submenu>
                    </el-submenu> -->
                    <el-menu-item
                        index="/user"
                        @click="saveActivePath('/user')"
                    >
                        <i class="el-icon-menu"></i>
                        <span slot="title">用户管理</span>
                    </el-menu-item>
                    <el-menu-item index="/dept" @click="saveActivePath('/dept')">
                        <i class="el-icon-document"></i>
                        <span slot="title">部门管理</span>
                    </el-menu-item>
                    <el-menu-item index="/role" @click="saveActivePath('/role')">
                        <i class="el-icon-s-custom"></i>
                        <span slot="title">角色管理</span>
                    </el-menu-item>
                    <el-menu-item index="/permission" @click="saveActivePath('/permission')">
                        <i class="el-icon-s-custom"></i>
                        <span slot="title">权限管理</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
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
                'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
            isCollapse: false,
            // 被激活的链接
            activePath: ''
        }
    },
    created(){
        // 初始化被选中的链接
        this.activePath =window.localStorage.getItem("ActivePath")
    },
    methods: {
        logout() {
            removeToken()
            this.$router.push('/login')
        },
        toggleCollapse() {
            this.isCollapse = !this.isCollapse
        },
        saveActivePath(path) {
            window.localStorage.setItem("ActivePath",path)
            this.activePath = path
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
    border-right: none;
}
.el-main {
    background-color: #eaedf1;
}
.home-container {
    height: 100%;
}
.toggle-btn {
    background-color: #4a5064;
    font-size: 13px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
}
</style>
