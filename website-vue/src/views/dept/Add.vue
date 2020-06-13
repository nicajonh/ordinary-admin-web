<template>
    <div>
        <el-dialog
            title="添加部门"
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
                <el-form-item label="上级部门">
                    <el-button
                        size="medium"
                        @click="selectFatherDept"
                        :type="btnType"
                        round
                    >
                        {{ btnText }}
                    </el-button>
                </el-form-item>

                <el-form-item label="部门名称" prop="deptName">
                    <el-input v-model="addForm.deptName"></el-input>
                </el-form-item>

                <el-form-item label="显示顺序" prop="orderNum">
                    <el-input-number
                        v-model="addForm.orderNum"
                        type="number"
                        :min="0"
                        :max="99"
                    ></el-input-number>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addFormClosed()">取 消</el-button>
                <el-button type="primary" @click="handleAddForm()">
                    确 定
                </el-button>
            </span>
        </el-dialog>
        <el-dialog
            title="选择上级部门"
            :visible.sync="dialogFatherDept"
            width="25%"
            @close="dialogFatherDept = false"
        >
            <el-input
                placeholder="输入关键字进行过滤"
                v-model="filterText"
            ></el-input>
            <el-tree
                class="filter-tree"
                :data="treeData"
                :props="defaultProps"
                default-expand-all
                highlight-current
                :filter-node-method="filterNode"
                @node-click="handleNodeClick"
                ref="tree"
            ></el-tree>
        </el-dialog>
    </div>
</template>

<script>
import { addModel } from '@/api/dept'
export default {
    name: 'AddDeptDialog',
    data() {
        return {
            btnText: '选择上级部门',
            btnType: '',
            filterText: '',
            addFormRules: {
                deptName: [
                    {
                        required: true,
                        message: '请输入部门名',
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
            dialogFatherDept: false,
            addForm: {
                orderNum: 0,
                deptName: '',
                parentId: ''
            },
            defaultProps: {
                children: 'children',
                label: 'deptName'
            }
        }
    },
    props: {
        visiable: {
            type: Boolean,
            default: false,
            required: true
        },
        treeData: {
            type: Array,
            required: true
        }
    },
    methods: {
        addFormClosed(refresh) {
            // 初始化数据
            this.btnText = '选择上级部门'
            this.addForm = {
                orderNum: 0,
                deptName: '',
                parentId: ''
            }
            this.btnType = ''
            this.$refs['addFormRef'].resetFields()
            this.$emit('closeDialog', refresh)
        },
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
        selectFatherDept() {
            this.dialogFatherDept = true
        },
        filterNode(value, data) {
            if (!value) return true
            return data.deptName.indexOf(value) !== -1
        },
        handleNodeClick(data) {
            this.dialogFatherDept = false
            this.addForm.parentId = data.id
            this.addForm.orderNum = data.orderNum + 1
            this.btnText = `已选择部门：${data.deptName}`
            this.btnType = 'success'
        }
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val)
        }
    }
}
</script>

<style></style>
