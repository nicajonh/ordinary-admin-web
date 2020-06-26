<template>
    <div>
        <el-dialog
            title="修改字典数据"
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
                <el-form-item label="字典标签" prop="dictLabel">
                    <el-input v-model="addForm.dictLabel"></el-input>
                </el-form-item>

                <el-form-item label="字典键值" prop="dictLabel">
                    <el-input v-model="addForm.dictValue"></el-input>
                </el-form-item>

                <el-form-item label="显示顺序" prop="dictLabel">
                    <el-input-number
                        v-model="addForm.orderNum"
                    ></el-input-number>
                </el-form-item>
                <el-form-item label="默认值" prop="dictLabel">
                    <el-switch v-model="addForm.defaultFlag"></el-switch>
                </el-form-item>
                <el-form-item label="描述" prop="dictLabel">
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
import { updateEntityById, fetchOne } from '@/api/dict-data'
export default {
    name: 'EditDictDataDialog',
    data() {
        return {
            addFormRules: {
                dictLabel: [
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
            },
            addForm: {
                dictLabel: '',
                dictType: this.dictTypeName,
                dictValue: '',
                remark: '',
                orderNum: 0,
                defaultFlag: false
            }
        }
    },
    props: {
        visiable: {
            type: Boolean,
            default: false,
            required: true
        },
        dictTypeName: {
            type: String,
            required: true
        }
    },
    methods: {
        addFormClosed(refresh) {
            // 初始化数据
            this.addForm = {
                dictLabel: '',
                dictType: this.dictTypeName,
                dictValue: '',
                orderNum: 0,
                remark: '',
                defaultFlag: false
            }
            this.$refs['addFormRef'].resetFields()
            this.$emit('closeDialog', refresh)
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
        },
        getUpdateModel(entityId) {
            fetchOne(entityId).then(resp => {
                if (resp.data) {
                    this.addForm = resp.data
                }
            })
        }
    }
}
</script>

<style></style>
