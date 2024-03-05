<template>
    <AdminNavbar>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#add_notice" style="border-radius: 0px;">发布通知</button>
        <div class="modal fade" id="add_notice" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">发布通知</h1>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">标题</label>
                            <textarea v-model="new_notice.title" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入标题"></textarea>
                            <div style="float: right;">{{ new_notice.title.length }}/100</div>
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">内容</label>
                            <textarea v-model="new_notice.content" class="form-control" id="exampleFormControlTextarea1" rows="10" placeholder="请输入内容"></textarea>
                            <div style="float: right;">{{ new_notice.content.length }}/1000</div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="error-message" style="float: right;color: red;">{{ new_notice.message }}</div>
                        <button type="button" class="btn btn-primary" @click="add_notice">发布</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover" style="text-align: center;">
                <thead>
                    <tr>
                        <th>标题</th>
                        <th>发布时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="notice in notices" :key="notice.id">
                        <td>
                            <span>{{ notice.title.slice(0,20)+(notice.title.length<=20?"":"...") }}</span>
                        </td>
                        <td>
                            <span>{{ notice.time.slice(0,10) }}</span>
                        </td>
                        <td>
                            <div style="display: flex;justify-content: center;">
                                <div class="op" @click="open_notice(notice.id)">查看</div>                                         
                                <div class="op" data-bs-toggle="modal" :data-bs-target="'#update_notice'+notice.id">修改</div>        
                                <div class="modal fade" :id="'update_notice'+notice.id" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg" style="text-align: left;">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">修改通知</h1>
                                        </div>
                                        <div class="modal-body">
                                            <div class="mb-3">
                                                <label for="exampleFormControlTextarea1" class="form-label">标题</label>
                                                <textarea v-model="notice.title" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入标题"></textarea>
                                                <div style="float: right;">{{ notice.title.length }}/100</div>
                                            </div>
                                            <div class="mb-3">
                                                <label for="exampleFormControlTextarea1" class="form-label">内容</label>
                                                <textarea v-model="notice.content" class="form-control" id="exampleFormControlTextarea1" rows="10" placeholder="请输入内容"></textarea>
                                                <div style="float: right;">{{ notice.content.length }}/1000</div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <div class="error-message" style="float: right;color: red;">{{ notice.message }}</div>
                                            <button type="button" class="btn btn-primary" @click="update_notice(notice)">修改</button>
                                            <button type="button" class="btn btn-secondary" @click="cancel(notice.id)">取消</button>
                                        </div>
                                    </div>
                                </div>
                            </div>                                 
                                <div class="op" @click="remove_notice(notice.id)">删除</div>   
                            </div>                                      
                        </td>
                    </tr>
                </tbody>
            </table>
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
    </AdminNavbar>
</template>

<script>
import AdminNavbar from '@/components/AdminNavbar.vue'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import $ from 'jquery'
import { ref,reactive } from 'vue'
import { useStore } from 'vuex'
import router from '@/router'

export default{
    components:{
        AdminNavbar,
    },
    setup(){
        const store=useStore();
        let notices=ref([]);
        let total_notices=0;
        let pages=ref([]);
        let current_page=1;
        const new_notice=reactive({
            title:"",
            content:"",
            message:"",
        });
        const add_notice=()=>{
            new_notice.message="";
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/notice/add/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    title:new_notice.title,
                    content:new_notice.content,
                },
                success(resp){
                    if(resp.message==="successful"){
                        Modal.getInstance("#add_notice").hide();
                        pull_page(1);
                        new_notice.title="";
                        new_notice.content="";
                    }
                    else{
                        new_notice.message=resp.message;
                    }
                },
            })
        }
        const update_notice=(notice)=>{
            notice.message="";
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/notice/update/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:notice.id,
                    title:notice.title,
                    content:notice.content,
                },
                success(resp){
                    if(resp.message==="successful"){
                        Modal.getInstance('#update_notice'+notice.id).hide();
                        pull_page(current_page);
                    }
                    else{
                        notice.message=resp.message;
                    }
                },
            })
        }
        const remove_notice=(id)=>{
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/notice/remove/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:id,
                },
                success(resp){
                    if(resp.message==="successful"){
                        pull_page(current_page);
                    }
                },
            })
        }
        const cancel=(id)=>{
            Modal.getInstance('#update_notice'+id).hide();
            pull_page(current_page);
        }
        const click_page=page=>{
            let max_pages=parseInt(Math.ceil(total_notices/20));
            if(page===-2)page=1;
            else if(page===-1)page=max_pages;
            if(page>=1&&page<=max_pages){
                pull_page(page);
            }
        }
        const update_pages=()=>{
            let max_pages=parseInt(Math.ceil(total_notices/20));
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
                    number:20,
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
            new_notice,
            notices,
            total_notices,
            pages,
            click_page,
            open_notice,
            add_notice,
            update_notice,
            remove_notice,
            cancel,
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
div.table-responsive>table>thead>tr>th {
    white-space: nowrap;
}
div.table-responsive>table>tbody>tr>td {
    white-space: nowrap;
}
div.op{
    color: rgb(0, 119, 255) ;
    cursor: pointer;
    margin-right: 10px;
}
div.op:hover{
    text-decoration: underline;
}
</style>