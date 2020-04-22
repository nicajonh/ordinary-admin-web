<template>
    <div class="home">
        <el-button type="info" @click="logout">logout</el-button>
    </div>
</template>

<script>
import { removeToken } from '@/util/auth'

export default {
    name: 'Home',
    methods: {
        logout() {
            removeToken()
            this.$router.push('/login')
        }
    }
}
</script>
