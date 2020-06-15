<template>
    <div>
        <el-card>
            <el-row :gutter="15">
                <el-col :span="6" :xl="4">
                    <el-input
                        placeholder="请输入角色名"
                        v-model="queryInfo.model.roleName"
                    >
                        <el-button
                            slot="append"
                            icon="el-icon-search"
                            @click="getUserList()"
                        ></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2">
                    <el-button type="primary">
                        添加角色
                    </el-button>
                </el-col>
            </el-row>
        </el-card>
    </div>
</template>

<script>
import { pageList } from '@/api/role'
export default {
    data() {
        return {
            queryInfo: {
                pageNumber: 1,
                pageSize: 5,
                orderField: 'updatedAt',
                orderType: 'asc',
                model: {
                    roleName: ''
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
        fetchDeptList() {
            pageList(this.queryInfo).then(resp => {
                this.pageObj.list = resp.data.content
                this.pageObj.totalPage = resp.data.totalPages
                this.pageObj.totalEle = resp.data.totalElements
            })
        }
    }
}
</script>

<style></style>
