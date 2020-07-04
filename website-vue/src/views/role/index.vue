<template>
    <div>
        <el-card>
            <el-row :gutter="15">
                <el-col :span="6" :xl="4">
                    <el-input
                        placeholder="请输入角色名"
                        v-model="queryInfo.model.roleName"
                    >
                        <el-button
                            slot="append"
                            icon="el-icon-search"
                            @click="getUserList()"
                        ></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button
                        type="primary"
                        @click="addRoleDialogVisiable = true"
                    >
                        添加角色
                    </el-button>
                </el-col>
                <el-table :data="pageObj.list" style="width: 100%">
                    <el-table-column
                        prop="roleName"
                        label="角色名称"
                        width="180"
                    ></el-table-column>

                    <el-table-column
                        prop="orderNum"
                        label="显示顺序"
                        width="180"
                    ></el-table-column>
                    <el-table-column
                        prop="dataScope"
                        label="数据范围"
                        width="180"
                    ></el-table-column>

                    <el-table-column
                        prop="remark"
                        label="备注"
                        width="180"
                    ></el-table-column>
                </el-table>
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pageNumber"
                    :page-sizes="[5, 10, 50]"
                    :page-size="queryInfo.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="pageObj.totalEle"
                ></el-pagination>
            </el-row>
        </el-card>
        <AddRoleDialog
            @closeDialog="closeAddRoleDialog"
            :visiable="addRoleDialogVisiable"
        />
    </div>
</template>

<script>
import { pageList } from '@/api/role'
import AddRoleDialog from './Add'
export default {
    components: { AddRoleDialog },
    data() {
        return {
            addRoleDialogVisiable: false,
            queryInfo: {
                pageNumber: 1,
                pageSize: 5,
                orderField: 'updated_at',
                orderType: 'asc',
                model: {
                    roleName: ''
                }
            },
            pageObj: {
                list: [],
                totalPage: 0,
                totalEle: 0
            }
        }
    },
    methods: {
        fetchRoleList() {
            pageList(this.queryInfo).then(resp => {
                this.pageObj.list = resp.data.content
                this.pageObj.totalPage = resp.data.totalPages
                this.pageObj.totalEle = resp.data.totalElements
            })
        },
        closeAddRoleDialog(refresh) {
            this.addRoleDialogVisiable = false
            if (refresh) {
                this.fetchRoleList()
            }
        },
        // 处理分页大小变化
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize
            this.getUserList()
        },
        // 处理当前页变更
        handleCurrentChange(curPage) {
            this.queryInfo.pageNumber = curPage
            this.getUserList()
        }
    },
    created() {
        this.fetchRoleList()
    }
}
</script>

<style></style>
