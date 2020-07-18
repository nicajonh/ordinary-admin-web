<template>
    <div>
        <el-dialog
            title="代码生成配置"
            :visible.sync="visiable"
            width="40%"
            @close="addFormClosed"
        >
            <el-form
                ref="form"
                :rules="formModelValidtion"
                :model="formModel"
                label-width="80px"
            >
                <el-form-item label="模块简述" prop="modelDescription">
                    <el-input v-model="formModel.modelDescription"></el-input>
                </el-form-item>
                <el-form-item label="url前缀" prop="urlPrefix">
                    <el-input v-model="formModel.urlPrefix"></el-input>
                </el-form-item>

                <!-- 
                <el-form-item label="数据库表名" prop="tableName">
                    <el-input v-model="formModel.tableName"></el-input>
                </el-form-item>
                 -->
                <el-form-item label="java包名" prop="javaPackage">
                    <el-input v-model="formModel.javaPackage"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('form')">
                        立即创建
                    </el-button>
                    <el-button @click="resetForm('form')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <ResultDialog :codeMap="codeGenResult" ref="resultDialogRef" />
    </div>
</template>

<script>
import CodeGenAPI from '@/api/code-gen'
import Entity from './_entity'
import ResultDialog from './Result'
export default {
    components: { ResultDialog },
    data() {
        return {
            formModel: {
                modelDescription: '',
                urlPrefix: '',
                tableName: '',
                javaPackage: undefined
            },
            formModelValidtion: Entity.entityValidtion,
            codeGenResult: {}
        }
    },
    props: {
        visiable: {
            type: Boolean,
            default: false,
            required: true
        },
        tableName: {
            type: String,
            required: true
        }
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    CodeGenAPI.codeGen(this.formModel).then(resp => {
                        if (resp.data) {
                            this.codeGenResult = resp.data
                            this.$refs['resultDialogRef'].visiableFunc(true)
                        }
                    })
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        resetForm(formName) {
            console.log(this.$refs['form'].resetFields())
            this.formModel = Entity.entity
            this.$refs['form'].resetFields()
            this.$refs[formName].resetFields()
        },
        addFormClosed(refresh) {
            // 初始化数据
            this.formModel = Entity.entity
            this.$refs['form'].resetFields()
            this.$emit('closeDialog', refresh)
        }
    },
    watch: {
        tableName: function(val) {
            this.formModel.tableName = val
        }
    }
}
</script>

<style></style>
