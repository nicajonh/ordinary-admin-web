<template>
    <div>
        <el-dialog
            title="修改字典"
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
                <el-form-item label="字典名称" prop="dictName">
                    <el-input v-model="addForm.dictName"></el-input>
                </el-form-item>
                <el-form-item label="字典类型" prop="dictName">
                    <el-input v-model="addForm.dictType"></el-input>
                </el-form-item>
                <el-form-item label="系统内置" prop="dictName">
                    <el-switch v-model="addForm.internalFlag"></el-switch>
                </el-form-item>
                <el-form-item label="描述" prop="dictName">
                    <el-input v-model="addForm.remark"></el-input>
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
import { updateEntityById, fetchOne } from '@/api/dict-type'
export default {
    name: 'EditDialog',
    props: {
        visiable: {
            type: Boolean,
            default: false,
            required: true
        },
       
    },
    data() {
        return {
            addForm: {
                dictName: '',
                dictType: '',
                remark: '',
                internalFlag: false
            },
            addFormRules: {
                dictName: [
                    {
                        required: true,
                        message: '请输入字典名称',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 15,
                        message: '长度在 3 到 15 个字符',
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods: {
        addFormClosed(refresh) {
            // 初始化数据
            this.addForm = {
                dictName: '',
                dictType: '',
                remark: '',
                internalFlag: false
            }
            this.$refs['addFormRef'].resetFields()
            this.$emit('closeDialog', refresh)
        },
        getUpdateModel(entityId) {
            fetchOne(entityId).then(resp => {
                if (resp.data) {
                    this.addForm = resp.data
                }
            })
        },
        handleAddForm() {
            this.$refs['addFormRef'].validate(valid => {
                if (!valid) return
                updateEntityById(this.addForm).then(resp => {
                    if (resp.data) {
                        this.$message('修改成功！')
                        this.addFormClosed(true)
                    }
                })
            })
        }
    }
}
</script>

<style></style>
