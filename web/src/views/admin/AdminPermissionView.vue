<template>
    <AdminNavbar>
        <div class="table-responsive">
            <table class="table table-hover" style="text-align: center;">
                <thead>
                    <tr>
                        <th>用户名</th>
                        <th>权限</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in users" :key="user.id">
                        <td>
                            <span>{{ user.username }}</span>
                        </td>
                        <td>
                            <span>{{ user.admin?"管理员":"普通用户" }}</span>
                        </td>
                        <td>
                            <div v-if="!user.admin" class="op" @click="update_admin(user.id)">转让权限</div>                                                                      
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
import { useStore } from 'vuex';
import $ from 'jquery'
import { ref } from 'vue'
import router from '@/router';

export default{
    components:{
        AdminNavbar,
    },
    setup(){
        const store=useStore();
        let users=ref([]);
        const update_admin=(id)=>{
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/permission/update/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:id,
                },
                success(resp){
                    if(resp.message==="successful"){
                        store.state.user.admin=false;
                        router.push({name:"home"});
                    }
                },
            })
        }
        const pull_users=()=>{
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/user/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                success(resp){
                    if(resp.message==="successful"){
                        users.value=resp.data;
                    }
                },
            })
        }
        pull_users();

        return{
            users,
            update_admin,
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