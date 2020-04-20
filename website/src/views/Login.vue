<template>
    <div class="login-containter">
        <div class="login-box">
            <div class="avatar-box">
                <img src="../assets/logo.png" />
            </div>
            <el-form
                ref="loginFormRef"
                class="login-form"
                :model="loginForm"
                :rules="loginFormRules"
            >
                <el-form-item prop="username">
                    <el-input
                        prefix-icon="fa fa-user-circle-o"
                        v-model="loginForm.username"
                    />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        prefix-icon="fa fa-key"
                        v-model="loginForm.password"
                        type="password"
                    />
                </el-form-item>
                <el-form-item class="btns">
                    <el-button type="primary" @click="handleLogin">
                        登录
                    </el-button>
                    <el-button type="info" @click="resetLoginForm">
                        重置
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { login } from '@/api/auth'
import { setAccessToken } from '@/util/auth'
export default {
    data() {
        return {
            loginForm: {
                username: 'Tom',
                password: '123'
            },
            loginFormRules: {
                username: [
                    {
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 5,
                        message: '长度在 3 到 5 个字符',
                        trigger: 'blur'
                    }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    {
                        min: 3,
                        max: 5,
                        message: '长度在 3 到 5 个字符',
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    methods: {
        resetLoginForm() {
            // console.log(this)
            this.$refs.loginFormRef.resetFields()
        },
        handleLogin() {
            this.$refs.loginFormRef.validate(valid => {
                if (!valid) return
                login(this.loginForm).then(resp => {
                    setAccessToken(resp.data.accessToken)
                    this.$router.push('/')
                    this.$message.success('登录成功！')
                })

                
            })
        }
    }
}
</script>

<style lang="less" scoped>
.login-containter {
    background-color: #2b4b6b;
    height: 100%;
}
.login-box {
    width: 450px;
    height: 300px;
    background-color: #fff;
    border-radius: 3px;
    // 调整位置
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    .avatar-box {
        height: 130px;
        width: 130px;
        border: 1px solid #eee;
        border-radius: 50%;
        padding: 5px;
        box-shadow: 0 0 10px #ddd;
        position: absolute;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: #fff;
        img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            background-color: #eee;
        }
    }
    .login-form {
        position: absolute;
        bottom: 0;
        width: 100%;
        padding: 0 20px;
        box-sizing: border-box;
    }
    .btns {
        display: flex;
        justify-content: flex-end;
    }
}
</style>
