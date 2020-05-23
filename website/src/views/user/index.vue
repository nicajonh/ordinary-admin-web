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
                    <el-button type="primary" @click="dialogVisible = true">添加用户</el-button>
                </el-col>
            </el-row>

            <el-table :data="pageObj.list" style="width: 100%" border>
                <el-table-column type="index"></el-table-column>
                <el-table-column
                    prop="username"
                    label="用户名"
                    width="180"
                ></el-table-column>

                <el-table-column prop="accountStatus" label="状态" width="180">
                    <template v-slot="scope">
                        <el-tag v-if="scope.row.accountStatus == 0" type="info">
                            未激活
                        </el-tag>
                        <el-tag
                            v-else-if="scope.row.accountStatus == 1"
                            type="success"
                        >
                            激活
                        </el-tag>
                        <el-tag
                            v-else-if="scope.row.accountStatus == 2"
                            type="warning"
                        >
                            锁定
                        </el-tag>
                        <el-tag
                            v-else-if="scope.row.accountStatus == 3"
                            type="danger"
                        >
                            删除
                        </el-tag>
                        <el-tag v-else type="danger">未知状态</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="150px">
                    <template>
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            size="mini"
                        ></el-button>

                        <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete-solid"
                        ></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="queryInfo.pageNumber"
                :page-sizes="[2, 5, 10, 50]"
                :page-size="queryInfo.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageObj.totalEle"
            ></el-pagination>
        </el-card>

 
        <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="30%"
            
        >
            <span>这是一段信息</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false">
                    确 定
                </el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { pageUserList } from '@/api/user.js'
export default {
    data() {
        return {
            dialogVisible:false,
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
        },
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize
            this.getUserList()
        },
        handleCurrentChange(curPage) {
            this.queryInfo.pageNumber = curPage
            this.getUserList()
        }
    },
    created() {
        this.getUserList()
    }
}
</script>

<style></style>
