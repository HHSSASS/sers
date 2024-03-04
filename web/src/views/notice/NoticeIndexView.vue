<template>
    <div class="container" style="width: 1100px;">
        <div style="background-color: rgb(7, 83, 169);width: 250px;height: 120px;margin-top: 20px;text-align: center;">
            <div style="padding-top: 35px;font-size: 30px; color: white;">通知公告</div>
        </div>
        <div class="card border-dark mb-3" v-for="notice in notices" :key="notice.id" @click="open_notice(notice.id)" style="margin-top: 20px;cursor: pointer;">
            <div class="row g-0">
                <div class="col-md-3">
                    <img src="@/assets/images/photo2.png" class="img-fluid rounded-start" alt="" width="180px">
                </div>
                <div class="col-md-9">
                    <div class="card-body">
                        <h5 class="card-title">{{ notice.title }}</h5>
                        <p class="card-text">{{ notice.content.slice(0,45)+(notice.content.length<=45?"":"...") }}</p>
                        <p class="card-text"><small class="text-muted">{{ notice.time.slice(0,10) }}</small></p>
                    </div>
                </div>
            </div>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination" style="float: right;">
                <li @click="click_page(-2)" class="page-item"><a class="page-link" href="#">首页</a></li>
                <li @click="click_page(page.number)" :class="'page-item '+page.is_active" v-for="page in pages" :key="page.number">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>
                <li @click="click_page(-1)" class="page-item"><a class="page-link" href="#">尾页</a></li>
            </ul>
        </nav>
    </div>
</template>

<script>
import '@/assets/iconfont/iconfont.css'
import { ref } from "vue";
import $ from 'jquery';
import router from '@/router';

export default{
    setup(){
        let notices=ref([]);
        let total_notices=0;
        let pages=ref([]);
        let current_page=1;

        const click_page=page=>{
            let max_pages=parseInt(Math.ceil(total_notices/10));
            if(page===-2)page=1;
            else if(page===-1)page=max_pages;
            if(page>=1&&page<=max_pages){
                pull_page(page);
            }
        }
        const update_pages=()=>{
            let max_pages=parseInt(Math.ceil(total_notices/10));
            let new_pages=[];
            for(let i=current_page-2;i<=current_page+2;++i){
                if(i>=1&&i<=max_pages){
                    new_pages.push({
                        number:i,
                        is_active:i===current_page?"active":"",
                    });
                }
            }
            pages.value=new_pages;
        }
        const pull_page=page=>{
            current_page=page;
            $.ajax({
                url:"http://127.0.0.1:3000/api/notice/getlist/",
                type:"get",
                data:{
                    page:page,
                    number:10,
                },
                success(resp){
                    notices.value=resp.notices;
                    total_notices=resp.notices_count;
                    update_pages();
                },
            })
        }
        pull_page(current_page);

        const open_notice=noticeId=>{
            router.push({
                name:"notice_content",
                params:{
                    noticeId:noticeId,
                }
            })
        }
        
        return{
            notices,
            total_notices,
            pages,
            click_page,
            open_notice,
        }
    }
}
</script>

<style scoped>
div.card:hover{
    background-color:rgb(230, 230, 230);
}
</style>