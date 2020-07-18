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
    </div>
</template>

<script>
import CodeGenAPI from '@/api/code-gen'
import Entity from './_entity'
export default {
    data() {
        return {
            formModel: Entity.entity,
            formModelValidtion: Entity.entityValidtion
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
                    alert('submit!' + CodeGenAPI.codeGen)
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields()
        },
        addFormClosed(refresh){
            // 初始化数据
            this.formModel = Entity.entity
            this.$refs['addFormRef'].resetFields()
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
