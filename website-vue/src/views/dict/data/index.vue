<template>
    <div>
        <el-row :gutter="15">
            <el-col :span="6" :xl="4">
                <el-input
                    placeholder="请输入字典类型名"
                    v-model="queryInfo.model.dictLabel"
                >
                    <el-button
                        slot="append"
                        icon="el-icon-search"
                        @click="fetchDictTypeList()"
                    ></el-button>
                </el-input>
            </el-col>
            <el-col :span="2">
                <el-button type="primary" @click="addDialogVisiable = true">
                    添加字典类型
                </el-button>
            </el-col>
        </el-row>
        <el-table :data="pageObj.list" style="width: 100%" row-key="id" border>
            <el-table-column type="index"></el-table-column>
            <el-table-column
                prop="dictLabel"
                label="字典标签"
                width="180"
            ></el-table-column>

            <el-table-column
                prop="dictValue"
                label="字典键值"
                width="180"
            ></el-table-column>
            <el-table-column
                prop="orderNum"
                label="显示顺序"
                width="180"
            ></el-table-column>

            <el-table-column
                prop="defaultFlag"
                label="默认值"
                width="180"
            ></el-table-column>
            <el-table-column
                prop="remark"
                label="描述"
                width="180"
            ></el-table-column>
            <el-table-column label="操作" width="180">
                <template v-slot="scope">
                    <el-button
                        v-if="scope.row.id"
                        type="primary"
                        icon="el-icon-edit"
                        size="mini"
                        circle
                        @click="showEditDialog(scope.row.id)"
                    ></el-button>
                    <el-button
                        type="danger"
                        icon="el-icon-delete"
                        circle
                        size="mini"
                        @click="removeCurrentRow(scope.row.id)"
                    ></el-button>
                </template>
            </el-table-column>
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
        <AddDialog
            @closeDialog="closeAddDialog"
            :visiable="addDialogVisiable"
            :dictTypeName="pathParm"
        />
        <EditDialog
            @closeDialog="closeEditDialog"
            :visiable="editDialogVisiable"
            :dictTypeName="$route.params.typeName"
            ref="editDialogRef"
        />
    </div>
</template>

<script>
import { pageList, removeEntityById } from '@/api/dict-data'
import AddDialog from './Add'
import EditDialog from './Edit'
export default {
    name: 'dict-data-index',
    components: { AddDialog, EditDialog },
    data() {
        return {
            addDialogVisiable: false,
            editDialogVisiable: false,
            editModelId: '',
            queryInfo: {
                pageNumber: 1,
                pageSize: 5,
                orderField: 'updatedAt',
                orderType: 'asc',
                model: {
                    dictType: this.$route.params.typeName,
                    dictLabel: ''
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
        // 处理分页大小变化
        handleSizeChange(newSize) {
            this.queryInfo.pageSize = newSize
            this.fetchDictDataList()
        },
        // 处理当前页变更
        handleCurrentChange(curPage) {
            this.queryInfo.pageNumber = curPage
            this.fetchDictDataList()
        },
        showEditDialog(editId) {
            this.editDialogVisiable = true
            this.editModelId = editId
            this.$refs.editDialogRef.getUpdateModel(editId)
        },
        fetchDictDataList() {
            pageList(this.queryInfo).then(resp => {
                this.pageObj.list = resp.data.content
                this.pageObj.totalPage = resp.data.totalPages
                this.pageObj.totalEle = resp.data.totalElements
            })
        },

        closeAddDialog(refresh) {
            this.addDialogVisiable = false
            if (refresh) {
                this.fetchDictDataList()
            }
        },
        removeCurrentRow(rowId) {
            this.$confirm('此操作将删除此条数据，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(() => {
                    removeEntityById(rowId).then(resp => {
                        if (resp.data) {
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            })
                            this.fetchDictTypeList()
                        } else {
                            this.$message({
                                type: 'info',
                                message: '删除失败'
                            })
                        }
                    })
                })
                .catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                })
        },
        closeEditDialog(refresh) {
            this.editDialogVisiable = false
            if (refresh) {
                this.fetchDictDataList()
            }
        }
    },
    created() {
        this.fetchDictDataList()
    },
    computed: {
        pathParm: {
            get() {
                return this.$route.params.typeName
            },
            set() {}
        }
    }
}
</script>

<style></style>
