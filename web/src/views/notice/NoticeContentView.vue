<template>
    <div class="container" style="width: 1100px;">
        <div style="background-color: rgb(7, 83, 169);width: 250px;height: 120px;margin-top: 20px;text-align: center;">
            <div style="padding-top: 35px;font-size: 30px; color: white;">通知公告</div>
        </div>
        <div v-if="notice!=undefined" style="margin-top: 20px;">
            <div style="background-color:rgb(220, 220, 220);width: 100%;text-align: center;">
                <div style="font-size: 30px;">{{ notice.title }}</div>
                <div>发布时间：{{ notice.time.slice(0,10) }}</div>
            </div>
        </div>
    </div>
    <div  v-if="notice!=undefined" class="container" style="width: 900px;">
        <div style="padding-top: 50px; font-size: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ notice.content }}</div>
    </div>
</template>

<script>
import '@/assets/iconfont/iconfont.css'
import $ from 'jquery'
import { ref } from 'vue';

export default{
    setup(){
        let notice=ref();
        const pull_notice=()=>{
            let index = window.location.href.lastIndexOf('/');
            let id=window.location.href.slice(index+1)
            $.ajax({
                url:"http://127.0.0.1:3000/api/notice/get/",
                type:"get",
                data:{
                    id:id,
                },
                success(resp){
                    notice.value=resp.notice;
                }
            })
        }
        pull_notice();

        return{
            notice,
        }
    }
}
</script>

<style scoped>
</style>