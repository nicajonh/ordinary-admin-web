<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card>
            <el-row :gutter="15">
                <el-col :span="6" :xl="4">
                    <el-input placeholder="请输入内容">
                        <el-button
                            slot="append"
                            icon="el-icon-search"
                        ></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button type="primary">添加用户</el-button>
                </el-col>
            </el-row>

            <el-table :data="pageObj.list" style="width: 100%" border >
                <el-table-column
                    prop="username"
                    label="用户名"
                    width="180"
                ></el-table-column>

                <el-table-column label="操作" width="180"></el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
import { pageUserList } from '@/api/user.js'
export default {
    data() {
        return {
            queryInfo: {
                pageNumber: 0,
                pageSize: 2,
                orderField: undefined,
                orderType: undefined,
                model: undefined
            },
            pageObj: {
                list: [],
                totalPage: 0,
                totalEle: 0
            }
        }
    },
    methods: {
        getUserList() {
            pageUserList(this.queryInfo).then(resp => {
                this.pageObj.list = resp.data.content
                this.pageObj.totalPage = resp.data.totalPages
                this.pageObj.totalEle = resp.data.totalElements
            })
        }
    },
    created() {
        this.getUserList()
    }
}
</script>

<style></style>
