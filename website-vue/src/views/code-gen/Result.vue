<template>
    <div>
        <el-dialog
            title="代码生成结果"
            :visible.sync="visiable"
            width="60%"
            @close="visiableFunc(false)"
        >
            <el-tabs>
                <el-tab-pane
                    v-for="(value, name, index) in codeMap"
                    :key="index"
                    :label="name"
                    :name="name"
                >
                    <el-button type="primary" @click="handlerCopyBtn(value)">
                        copy
                    </el-button>
                    <pre
                        >{{ value }}
                    </pre>
                </el-tab-pane>
            </el-tabs>
        </el-dialog>
    </div>
</template>

<script>
import { Notification } from 'element-ui'
export default {
    data() {
        return {
            visiable: false
        }
    },
    props: {
        codeMap: {
            type: Object,
            required: true
        }
    },
    methods: {
        visiableFunc(visiable) {
            if (visiable) this.visiable = true
            else this.visiable = false
        },
        handlerCopyBtn(text) {
            this.$copyText(text).then(
                () => {
                    Notification({
                        message: 'copied',
                        type: 'success'
                    })
                },
                e => {
                    Notification({
                        message: 'Can not copy',
                        type: 'error'
                    })
                    console.error(e)
                }
            )
        }
    }
}
</script>

<style></style>
