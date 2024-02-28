<template>
    <div>
        <video muted autoplay="autoplay" loop="loop" width="100%" disablePictureInPicture>
            <source src="@/assets/video/video.mp4" type="video/mp4">
        </video>
    </div>
    <div class="row" style="padding-top: 50px;">
        <div class="col-8">
            <div class="title">关于我们</div>
            <div class="iconfont icon-hengxian1"></div>
            <div style="display: flex; padding-left: 50px;padding-top:50px;">
                <img src="@/assets/images/logo.jpg" alt="" width="40%" height="40%">
                <div class="content">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    我们立足于全球水质监测领域，致力于通过科技创新和专业服务为环境保护和水质安全贡献智慧与力量。水是人类生活和生态系统中不可或缺的资源，对人类健康和环境保护至关重要。然而，由于人类活动和自然因素的影响，水质受到了严重的威胁和污染。因此，我们不断追求卓越，探索前沿技术，力求为社会提供高效、准确、可靠的水质监测解决方案。
                    面对水质污染日益严重的挑战，我们聚焦于水体抗生素等有害物质的监测问题。通过深入研究和积极创新，我们提出了一系列独特的解决方案，旨在提高水质监测的效率和准确性。我们的团队将继续努力，探索创新技术，为社会提供更清洁、更安全、可持续的水资源。
                </div>
            </div>
        </div>
        <div class="col-4">
            <div style="display: flex;">
                <div class="title">通知公告</div>
                <div class="link" @click="to_notice">查看更多</div>
            </div>
            <div class="iconfont icon-hengxian1"></div>
            <div style="padding-top: 40px; padding-left: 25px;color: rgb(220, 220, 220);">
                <div @click="open_notice(notice.id)" v-for="notice in notices" :key="notice.id" style="padding-bottom: 15px;cursor: pointer;">
                    <div style="font-size: 20px;">{{ notice.title.slice(0,20)+(notice.title.length<=10?"":"...") }}</div>
                    <div>{{ notice.time.slice(0,10) }}</div>
                </div>
            </div>
        </div>
    </div>
    <div style="padding-top: 50px;">
        <div class="title">线上商城</div>
        <div class="iconfont icon-hengxian1"></div>
    </div>
    <div style="padding-top: 50px;">
        <div class="title">会员服务</div>
        <div class="iconfont icon-hengxian1"></div>
    </div>
    <div style="padding-top: 50px;">
        <div class="title">技术监督</div>
        <div class="iconfont icon-hengxian1"></div>
    </div>
    <div @click="to_chat" class="iconfont icon-rengongkefu"></div>
</template>

<script>
import '@/assets/iconfont/iconfont.css'
import router from '@/router/index'
import { ref } from 'vue'
import $ from 'jquery'
 
export default{
    setup(){
        let notices=ref([]);
        const pull_notice=()=>{
            $.ajax({
                url:"http://127.0.0.1:3000/api/notice/getlist/",
                type:"get",
                data:{
                    page:1,
                    number:6,
                },
                success(resp){
                    notices.value=resp.notices;
                },
            })
        }
        pull_notice();

        const open_notice=noticeId=>{
            router.push({
                name:"notice_content",
                params:{
                    noticeId:noticeId,
                }
            })
        }
        const to_notice=()=>{
            router.push({name:'notice_index'});
        }
        const to_chat=()=>{
            router.push({name:'chat_index'});
        }
        return{
            notices,
            open_notice,
            to_notice,
            to_chat,
        }
    },
    beforeMount () {
        document.querySelector('body').setAttribute('style', "background-color:rgb(5,5,5);")
    },
    beforeUnmount () {
      document.querySelector('body').removeAttribute('style')
    }
}
</script>

<style scoped>
div.title{
    padding-left: 20px;
    font-size:30px ;
    color: rgb(220, 220, 220);
}
div.iconfont.icon-hengxian1{
    color: rgb(7, 83, 169) ;
    font-size: 15px;
}
div.iconfont.icon-rengongkefu{
    border-radius: 100%;
    background-color: white;
    font-size: 40px;
    width: 40px;
    height: 40px;
    text-align: center;
    position: fixed;
    right: 2%;
    top: 80%;
    cursor: pointer;
}
div.content{
    padding-top: 20px;
    padding-left: 50px;
    padding-right: 100px;
    font-size: 20px;
    color: rgb(220, 220, 220);
}
div.link{
    cursor: pointer;
    padding-top: 15px;
    padding-left: 20px; 
    color: rgb(0, 119, 255) ;
}
</style>