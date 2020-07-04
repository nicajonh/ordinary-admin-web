<template>
    <div>
        <el-card>
            <el-row :gutter="15">
                 <el-col :span="2">
                    <el-button
                        type="primary"
                        @click="addPermDialogVisiable = true"
                    >
                        添加权限
                    </el-button>
                </el-col>
                <el-table :data="pageObj.list" style="width: 100%">
                    <el-table-column
                        prop="permName"
                        label="权限名称"
                        width="180"
                    ></el-table-column>

                    <el-table-column
                        prop="orderNum"
                        label="显示顺序"
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
        <AddPermDialog
            @closeDialog="closeAddPermDialog"
            :visiable="addPermDialogVisiable"
        />
    </div>
</template>

<script>
import { pageList } from '@/api/permission'
import AddPermDialog from './Add'
export default {
    components: { AddPermDialog },
    data() {
        return {
            addPermDialogVisiable: false,
            queryInfo: {
                pageNumber: 1,
                pageSize: 5,
                orderField: 'updated_at',
                orderType: 'asc',
                model: {
                    permName: ''
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
        fetchPermList() {
            pageList(this.queryInfo).then(resp => {
                this.pageObj.list = resp.data.content
                this.pageObj.totalPage = resp.data.totalPages
                this.pageObj.totalEle = resp.data.totalElements
            })
        },
        closeAddPermDialog(refresh) {
            this.addPermDialogVisiable = false
            if (refresh) {
                this.fetchPermList()
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
        this.fetchPermList()
    }
}
</script>

<style></style>
