<template>
    <div>
        <el-table :data="tableList" style="width: 100%">
            <el-table-column type="index" width="50"></el-table-column>
            <el-table-column
                prop="tableName"
                label="表名"
                min-width="180"
            ></el-table-column>
            <el-table-column
                prop="tableType"
                label="类型"
                min-width="180"
            ></el-table-column>
            <el-table-column>
                <template v-slot="scope">
                    <el-button
                        type="primary"
                        icon="el-icon-edit"
                        size="mini"
                        circle
                        @click="showEditDialog(scope.row.tableName)"
                    ></el-button>
                </template>
            </el-table-column>
        </el-table>
        <DialogConfig
            :visiable="dialogVisiable"
            :tableName="tableNameForDialog"
        />
    </div>
</template>

<script>
import DialogConfig from './CodeGenConfig'
import CodeGenAPI from '@/api/code-gen'
export default {
    components: { DialogConfig },
    data() {
        return {
            dialogVisiable: false,
            tableNameForDialog: '',
            tableList: []
        }
    },
    methods: {
        fetchTables() {
            CodeGenAPI.tableList().then(resp => {
                this.tableList = resp.data
            })
        },
        showEditDialog(tableName) {
            this.tableNameForDialog = tableName
            this.dialogVisiable = true
        },
        closeDialog(refresh) {
            this.dialogVisiable = false
            if (refresh) {
                this.fetchTables()
            }
        }
    },
    created() {
        this.fetchTables()
    }
}
</script>

<style></style>
