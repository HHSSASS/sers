<template>
    <AdminNavbar></AdminNavbar>
    <ContentField>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#add_gps" style="border-radius: 0px;">添加芯片</button>
        <div class="modal fade" id="add_gps" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">添加芯片</h1>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">芯片序列号</label>
                            <textarea v-model="new_gps.number" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入序列号"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">芯片型号</label>
                            <textarea v-model="new_gps.type" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入型号"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="error-message" style="float: right;color: red;">{{ new_gps.message }}</div>
                        <button type="button" class="btn btn-primary" @click="add_gps">添加</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover" style="text-align: center;">
                <thead>
                    <tr>
                        <th>序列号</th>
                        <th>型号</th>
                        <th>添加时间</th>
                        <th>激活用户</th>
                        <th>激活时间</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="gps in gpss" :key="gps.id">
                        <td>
                            <span>{{ gps.number }}</span>
                        </td>
                        <td>
                            <span>{{ gps.type }}</span>
                        </td>
                        <td>
                            <span>{{ gps.addTime.slice(0,10) }}</span>
                        </td>
                        <td>
                            <span>{{ gps.userId==null?"未激活":gps.userId }}</span>
                        </td>
                        <td>
                            <span>{{ gps.activeTime==null?"未激活":gps.activeTime.slice(0,10) }}</span>
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
    </ContentField>
</template>

<script>
import AdminNavbar from '@/components/AdminNavbar.vue'
import ContentField from '@/components/ContentField.vue'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import $ from 'jquery'
import { ref,reactive } from 'vue'
import { useStore } from 'vuex'

export default{
    components:{
        AdminNavbar,
        ContentField,
    },
    setup(){
        const store=useStore();
        let gpss=ref([])
        let total_gpss=0;
        let pages=ref([]);
        let current_page=1;
        const new_gps=reactive({
            number:"",
            type:"",
            message:"",
        });
        const add_gps=()=>{
            new_gps.message="";
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/gps/add/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    number:new_gps.number,
                    type:new_gps.type,
                },
                success(resp){
                    if(resp.message==="successful"){
                        Modal.getInstance("#add_gps").hide();
                        new_gps.number="";
                        new_gps.type="";
                        pull_page(1);
                    }
                    else{
                        new_gps.message=resp.message;
                    }
                },
            })
        }
        const click_page=page=>{
            let max_pages=parseInt(Math.ceil(total_gpss/20));
            if(page===-2)page=1;
            else if(page===-1)page=max_pages;
            if(page>=1&&page<=max_pages){
                pull_page(page);
            }
        }
        const update_pages=()=>{
            let max_pages=parseInt(Math.ceil(total_gpss/20));
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
                url:"http://127.0.0.1:3000/api/admin/gps/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    page:page,
                },
                success(resp){
                    console.log(resp);
                    gpss.value=resp.gpss;
                    total_gpss=resp.gpss_count;
                    update_pages();
                },
            })
        }
        pull_page(current_page);
        return{
            new_gps,
            gpss,
            total_gpss,
            pages,
            click_page,
            add_gps,
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
</style>