<template>
    <div>
        <el-dialog
            title="添加权限"
            :visible.sync="visiable"
            width="30%"
            @close="addFormClosed"
        >
            <el-form
                :model="addForm"
                :rules="addFormRules"
                ref="addFormRef"
                label-width="100px"
            >
                <el-form-item label="权限名称" prop="permName">
                    <el-input v-model="addForm.permName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addFormClosed()">取 消</el-button>
                <el-button type="primary" @click="handleAddForm()">
                    确 定
                </el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { addModel } from '@/api/permission'
export default {
    name: 'AddPermDialog',
    props: {
        visiable: {
            type: Boolean,
            default: false,
            required: true
        }
    },
    data() {
        return {
            addForm: {
                remark: '',
                orderNum: 0,
                permName:''
            },
            addFormRules: {}
        }
    },
    methods: {
        handleAddForm() {
            this.$refs['addFormRef'].validate(valid => {
                if (!valid) return
                addModel(this.addForm).then(resp => {
                    if (resp.data) {
                        this.$message('添加成功！')
                        this.addFormClosed(true)
                    }
                })
            })
        },
        addFormClosed(refresh) {
            // 初始化数据
            this.$refs['addFormRef'].resetFields()
            this.$emit('closeDialog', refresh)
        }
    }
}
</script>

<style></style