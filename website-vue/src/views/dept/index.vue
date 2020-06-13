<template>
    <div>
        <el-card>
            <el-row :gutter="15">
                <el-col :span="6" :xl="4">
                    <el-input
                        placeholder="请输入部门名称"
                        v-model="queryInfo.model.deptName"
                    >
                        <el-button
                            slot="append"
                            icon="el-icon-search"
                            @click="fetchDeptList()"
                        ></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button
                        type="primary"
                        @click="addDeptDialogVisiable = true"
                    >
                        添加部门
                    </el-button>
                </el-col>
            </el-row>
            <el-table
                :data="treeData"
                style="width: 100%"
                default-expand-all
                row-key="id"
                border
            >
                <el-table-column type="index"></el-table-column>
                <el-table-column
                    prop="deptName"
                    label="部门名称"
                    width="180"
                ></el-table-column>
                <el-table-column
                    prop="orderNum"
                    label="显示顺序"
                    width="180"
                ></el-table-column>

                <el-table-column label="操作" width="180">
                    <template v-slot="scope">
                        <el-button
                            v-if="scope.row.id"
                            type="primary"
                            icon="el-icon-edit"
                            size="mini"
                            
                            @click="showEditDialog(scope.row)"
                        ></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <!-- 添加部门弹出框 -->
        <AddDeptDialog
            @closeDialog="closeAddDeptDialog"
            :visiable="addDeptDialogVisiable"
            :treeData="treeData"
        />
        <EditDeptDialog
            @closeDialog="closeEditDeptDialog"
            :visiable="editDeptDialogVisiable"
            :treeData="treeData"
            :editModel="editModel"
            ref="editDialogRef"
        />
    </div>
</template>
<script>
import { pageList, addModel, getTreeInfo } from '@/api/dept'
import { Message } from 'element-ui'
import AddDeptDialog from './Add'
import EditDeptDialog from './Edit'
export default {
    components: { AddDeptDialog, EditDeptDialog },
    data() {
        return {
            addDeptDialogVisiable: false,
            editDeptDialogVisiable: false,
            editModel: {},
            addDialogVisible: false,
            addForm: {
                orderNum: 0,
                deptName: ''
            },
            addFormRules: {},
            queryInfo: {
                pageNumber: 1,
                pageSize: 5,
                orderField: 'updatedAt',
                orderType: 'asc',
                model: {
                    deptName: ''
                }
            },
            pageObj: {
                list: [],
                totalPage: 0,
                totalEle: 0
            },
            treeData: []
        }
    },
    methods: {
        showEditDialog(editModel) {
            this.editModel = editModel
            this.editDeptDialogVisiable = true
            this.$refs.editDialogRef.getFatherNode()
        },
        closeAddDeptDialog(refresh) {
            this.addDeptDialogVisiable = false
            if (refresh) {
                this.fetchDeptTree()
            }
        },
        closeEditDeptDialog(refresh) {
            this.editDeptDialogVisiable = false
            if (refresh) {
                this.fetchDeptTree()
            }
        },
        fetchDeptList() {
            pageList(this.queryInfo).then(resp => {
                this.pageObj.list = resp.data.content
                this.pageObj.totalPage = resp.data.totalPages
                this.pageObj.totalEle = resp.data.totalElements
            })
        },
        fetchDeptTree() {
            getTreeInfo().then(resp => {
                this.treeData = []
                this.treeData.push(resp.data)
            })
        },
        // 处理分页大小变化
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize
            this.fetchDeptList()
        },
        // 处理当前页变更
        handleCurrentChange(curPage) {
            this.queryInfo.pageNumber = curPage
            this.fetchDeptList()
        },
        // 关闭新增弹出框
        addFormClosed() {
            this.$refs['addFormRef'].resetFields()
            this.addDialogVisible = false
        },
        // 提交新增表单的信息
        handleAddForm() {
            this.$refs['addFormRef'].validate(valid => {
                if (!valid) return
                addModel(this.addForm).then(() => {
                    Message({
                        message: '部门添加成功',
                        type: 'success',
                        duration: 5 * 1000
                    })
                    this.$refs['addFormRef'].resetFields()
                    this.addDialogVisible = false
                    this.fetchDeptList()
                })
            })
        }
    },
    created() {
        this.fetchDeptTree()
    }
}
</script>
