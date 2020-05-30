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
                    <el-button type="primary" @click="addDialogVisible = true">
                        添加用户
                    </el-button>
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
                    <template v-slot="scope">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            size="mini"
                            @click="showEditDialog(scope.row.id)"
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
        <!-- 修改用户信息弹出框 -->
        <el-dialog
            title="修改用户信息"
            :visible.sync="editDialogVisible"
            width="30%"
            @close="editFormClosed"
        >
            <el-form
                :model="editForm"
                :rules="editFormRules"
                ref="editFormRef"
                label-width="100px"
            >
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="editForm.username"></el-input>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="editForm.email"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editFormClosed()">取 消</el-button>
                <el-button type="primary" @click="handleEditForm">
                    确 定
                </el-button>
            </span>
        </el-dialog>

        <!-- 添加用户弹出框 -->
        <el-dialog
            title="添加用户"
            :visible.sync="addDialogVisible"
            width="30%"
            @close="addFormClosed"
        >
            <el-form
                :model="addForm"
                :rules="addFormRules"
                ref="addFormRef"
                label-width="100px"
            >
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="addForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="addForm.password"></el-input>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="addForm.email"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addFormClosed()">取 消</el-button>
                <el-button type="primary" @click="handleAddForm">
                    确 定
                </el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { pageUserList, addUser, fetchUser, updateUser } from '@/api/user.js'
import { Message } from 'element-ui'
export default {
    data() {
        var checkEmail = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('邮箱不能为空'))
            }
            var regx = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/
            if (regx.test(value)) {
                return callback()
            }
            return callback(new Error('请输入正确的邮箱地址'))
        }
        return {
            addDialogVisible: false,
            editDialogVisible: false,
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
            },
            addForm: {
                // TODO 这里将数据清空
                username: 'Jerry',
                password: '123',
                email: '848719061@qq.com'
            },
            editForm: {},
            addFormRules: {
                username: [
                    {
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 15,
                        message: '长度在 3 到 15 个字符',
                        trigger: 'blur'
                    }
                ],
                password: [
                    {
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 15,
                        message: '长度在 3 到 15 个字符',
                        trigger: 'blur'
                    }
                ],
                email: [
                    {
                        validator: checkEmail,
                        trigger: 'blur',
                        required: true
                    }
                ]
            },
            editFormRules: {
                username: [
                    {
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 15,
                        message: '长度在 3 到 15 个字符',
                        trigger: 'blur'
                    }
                ],
                email: [
                    {
                        validator: checkEmail,
                        trigger: 'blur',
                        required: true
                    }
                ]
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
        },
        addFormClosed() {
            this.$refs['addFormRef'].resetFields()
            this.addDialogVisible = false
        },
        editFormClosed() {
            this.$refs['editFormRef'].resetFields()
            this.editDialogVisible = false
        },
        handleAddForm() {
            this.$refs['addFormRef'].validate(valid => {
                if (!valid) return
                addUser(this.addForm).then(() => {
                    Message({
                        message: '用户添加成功',
                        type: 'success',
                        duration: 5 * 1000
                    })
                    this.$refs['addFormRef'].resetFields()
                    this.addDialogVisible = false
                    this.getUserList()
                })
            })
        },
        handleEditForm() {
            this.$refs['editFormRef'].validate(valid => {
                if (!valid) return
                updateUser(this.editForm).then(() => {
                    this.editDialogVisible = false
                    this.getUserList()
                })
            })
        },
        showEditDialog(id) {
            fetchUser(id)
                .then(resp => {
                    this.editForm = resp.data

                    this.editDialogVisible = true
                })
                .catch(err => {
                    Message({
                        message: '获取用户信息失败！',
                        type: 'error',
                        duration: 5 * 1000
                    })
                    console.error(' ', err)
                })
        }
    },
    created() {
        this.getUserList()
    }
}
</script>

<style></style>
