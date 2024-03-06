<template>
    <div class="card">
        <div class="card-body">
            <div style="padding-left: 80px;font-size: 40px;">
                <img src="@/assets/images/logo.png" alt="" width="50px">
                SERS
            </div>
            <form @submit.prevent="register" style="margin-top: 20px;">
                <div class="mb-3">
                    <label for="username" class="form-label">用户名</label>
                    <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">密码</label>
                    <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">确认密码</label>
                    <input v-model="confirmPassword" type="password" class="form-control" id="confirmPassword" placeholder="请再次输入密码">
                </div>
                <div class="error-message">{{message}}</div>
                <div @click="login" class="login">已有账号？立即登录！</div>
                <button type="submit" class="btn btn-primary">注册</button>
            </form>
        </div>
    </div>
</template>

<script>
import { ref } from "vue";
import router from '@/router/index'
import $ from 'jquery'

export default{
    setup(){
        let username=ref('');
        let password=ref('');
        let confirmPassword=ref('');
        let message=ref('');
        const register=()=>{
            $.ajax({
                url:"https://app6418.acapp.acwing.com.cn/api2/user/account/register/",
                type:"post",
                data:{
                    username:username.value,
                    password:password.value,
                    confirmPassword:confirmPassword.value,
                },
                success(resp){
                    if(resp.message==="successful"){
                        router.push({name:"user_account_login"});
                    }
                    else{
                        message.value=resp.message;
                    }
                },
            })
        }
        const login=()=>{
            router.push({name:'user_account_login'});
        }
        return{
            username,
            password,
            confirmPassword,
            message,
            register,
            login,
        }
    },
    beforeMount () {
        document.querySelector('body').setAttribute('style', "background-image: url("+require("@/assets/images/photo.png")+");background-size: cover;")
    },
    beforeUnmount () {
      document.querySelector('body').removeAttribute('style')
    }
}
</script>

<style scoped>
div.card{
    float: right;
    margin-right: 10%;
    margin-top: 10%;
    width: 400px;
    height: 480px;
}
button{
    width:100%;
}
div.error-message{
    color:red;
}
div.login{
    color: rgb(0, 119, 255) ;
    cursor: pointer;
}
</style>