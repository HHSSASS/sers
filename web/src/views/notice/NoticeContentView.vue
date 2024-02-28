<template>
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <div style="background-color: rgb(7, 83, 169);width: 250px;height: 120px;margin-top: 20px;text-align: center;">
                <div style="padding-top: 35px;font-size: 30px; color: white;">通知公告</div>
            </div>
            <div v-if="notice!=undefined" style="margin-top: 20px;">
                <div style="background-color:rgb(220, 220, 220);width: 100%;height:100px;text-align: center;">
                    <div style="padding-top: 20px; font-size: 30px;">{{ notice.title }}</div>
                    <div>发布时间：{{ notice.time.slice(0,10) }}</div>
                </div>
            </div>
        </div>
        <div class="col-2"></div>
        <div class="row"  v-if="notice!=undefined" >
            <div class="col-3"></div>
            <div class="col-6">
                <div style="padding-top: 50px; font-size: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ notice.content }}</div>
            </div>
            <div class="col--3"></div>
        </div>
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