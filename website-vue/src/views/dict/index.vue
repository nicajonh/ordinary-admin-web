<template>
    <div>
        <el-card>
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
            <el-table
                :data="pageObj.list"
                style="width: 100%"
                row-key="id"
                border
            >
                <el-table-column type="index"></el-table-column>
                <el-table-column
                    prop="dictName"
                    label="字典名称"
                    width="180"
                ></el-table-column>
                <el-table-column prop="dictType" label="字典类型" width="180">
                    <template v-slot="scope">
                        <router-link
                            :to="{
                                path: '/dict-data/' + scope.row.dictType
                            }"
                        >
                            {{ scope.row.dictType }}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column label="是否系统内置" width="180">
                    <template v-slot="scope">
                        <el-tag v-if="scope.row.internalFlag" type="success">
                            是
                        </el-tag>
                        <el-tag v-else type="info">否</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="remark"
                    label="描述"
                    width="180"
                ></el-table-column>
                <el-table-column label="操作" width="180">
                    <template v-slot="scope">
                        <el-button
                            type="primary"
                            icon="el-icon-edit"
                            size="mini"
                            circle
                            @click="showEditDialog(scope.row.id)"
                        ></el-button>
                        <el-tooltip
                            :enterable="false"
                            content="查看对应数据信息"
                        >
                            <el-button
                                type="warning"
                                icon="el-icon-info"
                                circle
                                size="mini"
                                @click="gotoDictDataPage(scope.row.dictType)"
                            ></el-button>
                        </el-tooltip>
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
        </el-card>
        <AddDialog
            @closeDialog="closeAddDialog"
            :visiable="addDialogVisiable"
        />
        <EddDialog
            @closeDialog="closeEditDialog"
            :visiable="editDialogVisiable"
            ref="editDialogRef"
        />
    </div>
</template>

<script>
import { pageList, removeEntityById } from '@/api/dict-type'
import AddDialog from './type/Add'
import EddDialog from './type/Edit'
export default {
    name: 'DictPage',
    components: { AddDialog, EddDialog },
    data() {
        return {
            addDialogVisiable: false,
            editDialogVisiable: false,
            editModelId: '',
            queryInfo: {
                pageNumber: 1,
                pageSize: 5,
                orderField: 'updated_at',
                orderType: 'asc',
                model: {
                    dictType: '',
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
            this.fetchDeptList()
        },
        // 处理当前页变更
        handleCurrentChange(curPage) {
            this.queryInfo.pageNumber = curPage
            this.fetchDeptList()
        },
        fetchDictTypeList() {
            pageList(this.queryInfo).then(resp => {
                this.pageObj.list = resp.data.content
                this.pageObj.totalPage = resp.data.totalPages
                this.pageObj.totalEle = resp.data.totalElements
            })
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
        showEditDialog(editId) {
            this.editDialogVisiable = true
            this.editModelId = editId
            this.$refs.editDialogRef.getUpdateModel(editId)
        },
        closeAddDialog(refresh) {
            this.addDialogVisiable = false
            if (refresh) {
                this.fetchDictTypeList()
            }
        },
        gotoDictDataPage(dictTypeName) {
            this.$router.push(`/dict-data/${dictTypeName}`)
        },
        closeEditDialog(refresh) {
            this.editDialogVisiable = false
            if (refresh) {
                this.fetchDictTypeList()
            }
        }
    },
    created() {
        this.fetchDictTypeList()
    }
}
</script>

<style></style>
