<template>
    <div>
        <el-dialog
            title="添加角色"
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
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="addForm.roleName"></el-input>
                </el-form-item>
                <el-form-item label="显示顺序" prop="orderNum">
                    <el-input-number
                        v-model="addForm.orderNum"
                        type="number"
                        :min="0"
                        :max="99"
                    ></el-input-number>
                </el-form-item>
                <el-form-item label="备注" prop="roleName">
                    <el-input v-model="addForm.remark"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-tree
                        :data="treeData"
                        show-checkbox
                        node-key="id"
                        ref="tree"
                        @check="clickTreeNode"
                        highlight-current
                        :props="treeProps"
                    ></el-tree>
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
import { addModel } from '@/api/role'
import { fetchTreeData } from '@/api/permission'
export default {
    name: 'AddRoleDialog',
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
                roleName: '',
                orderNum: 0,
                permIds: []
            },
            addFormRules: {},
            treeData: [],
            treeProps: {
                children: 'children',
                label: 'permName'
            }
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
                        // this.addForm.permIds = []
                    }
                })
            })
        },
        addFormClosed(refresh) {
            // 初始化数据
            this.$refs['addFormRef'].resetFields()
            this.$emit('closeDialog', refresh)
            this.addForm = {
                remark: '',
                roleName: '',
                orderNum: 0,
                permIds: []
            }
            this.$refs.tree.setChecked()
        },
        getTreeData() {
            fetchTreeData().then(resp => {
                if (resp.data) {
                    this.treeData.push(resp.data)
                }
            })
        },
        clickTreeNode() {
            this.addForm.permIds = []
            let selectedNodes = this.$refs.tree.getCheckedKeys()
            this.addForm.permIds = selectedNodes
        }
    },
    created() {
        this.getTreeData()
    }
}
</script>

<style></style>
