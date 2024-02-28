<template>
    <div class="mess_dialog">
        <div class="dlog_header">
            <div class="iconfont icon-rengongkefu" style="margin-top: 10px;margin-left: 20px; font-size: 30px;"></div>
            <div style="font-size: 25px;">客服</div>
        </div>
        <div class="dlog_content">
            <div class="content">
                <div class="time">2024.2.28 20:30</div>
                <div>客服</div>
                <div class="content_other">hello</div>                    
            </div>
            <div class="content">
                <div style="align-self: flex-end;">{{ $store.state.user.username }}</div>
                <div class="content_me">hellohellohellohellohellohellohellohellohello</div>
            </div>
        </div>
        <div class="dlog_footer">
            <textarea v-model="content" class="form-control" rows="5" style="resize: none;"></textarea>
            <div style="color:red;float: right;">
                {{ message }}
                <button @click="submit" class="btn btn-secondary btn-lg">发送</button>
            </div>
        </div>
    </div>
</template>

<script>
import '@/assets/iconfont/iconfont.css'
import { ref } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'

export default{
    setup(){
        const store=useStore();
        let content=ref('')
        let message=ref('');
        const submit=()=>{
            message.value="";
            $.ajax({
                url:"http://127.0.0.1:3000/api/chat/add/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                   content:content.value,
                },
                success(resp){
                    if(resp.message==="successful"){
                        message.value="";
                    }
                    else{
                        message.value=resp.message;
                    }
                },
            })
        }
        return{
            content,
            message,
            submit,
        }
    },
    beforeMount () {
        document.querySelector('body').setAttribute('style', "background-image: url("+require("@/assets/images/photo.png")+");background-size: cover;")
    },
    beforeUnmount () {
      document.querySelector('body').removeAttribute('style')
    }
    //SELECT （发送人，count(*)） FROM 表 where 发送人！=客服  and 未读=1  GROUP BY 发送人
}
</script>

<style scoped>
.mess_dialog{
    border-radius: 8px;
    background-clip: padding-box;
    margin:80px auto;   
    width: 800px;
    height: 600px;
    border: 1px #8a8282;
    box-shadow: 0 0 10px #9b9393;
    background-color:white;
}
.dlog_header{
    width: 100%;
    height: 50px;
    border-bottom: 1px solid #8a8282;
    display: flex;
    align-items: center;
}
.dlog_content{
    width: 100%;
    height: 370px;
    border-bottom: 1px solid #8a8282;
    overflow-y: scroll;
    font-size: 20px;
 
}
.dlog_footer{
    width: 100%;
    height: 180px;
}
.time{
    background-color:#9b9393;
    border-radius: 10px;
    width: 20%;
    height: 20px;
    margin: 0 auto;
    text-align: center;
    font-size: 15px;
}
.content{
    display: flex;
    flex-direction: column;
    margin-left: 10px;
    margin-right: 10px;
}
.content_other{
    background-color: rgb(230, 230, 230);
    border-radius: 5px;
    max-width: 300px;
    align-self: flex-start;
    word-wrap: break-word;
    padding-left: 10px;
    padding-right: 10px;
}
.content_me{
    background-color:bisque;
    border-radius: 5px;
    max-width: 300px;
    align-self: flex-end;
    word-wrap: break-word;
    padding-left: 10px;
    padding-right: 10px;
}
</style>