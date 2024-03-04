<template>
    <AdminNavbar></AdminNavbar>
    <ContentField>
        <div class="table-responsive">
            <table class="table table-hover" style="text-align: center;">
                <thead>
                    <tr>
                        <th>用户名</th>
                        <th>产品名称</th>
                        <th>数量</th>
                        <th>收货地址</th>
                        <th>支付金额</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>完成时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="order in orders" :key="order.id">
                        <td>
                            <span>{{ order.username }}</span>
                        </td>
                        <td>
                            <span>{{ order.name }}</span>
                        </td>
                        <td>
                            <span>{{ order.number }}</span>
                        </td>
                        <td>
                            <span>{{ order.address }}</span>
                        </td>
                        <td>
                            <span>{{ order.price*order.number }}</span>
                        </td>
                        <td>
                            <span>{{ order.status }}</span>
                        </td>
                        <td>
                            <span>{{ order.createTime }}</span>
                        </td>
                        <td>
                            <span>{{ order.finishTime==null?"未完成":order.finishTime }}</span>
                        </td>
                        <td>
                            <button type="button" class="btn btn-primary" @click="update_order(order.id)">发货</button>                                 
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
import $ from 'jquery'
import { ref } from 'vue'
import { useStore } from 'vuex'

export default{
    components:{
        AdminNavbar,
        ContentField,
    },
    setup(){
        const store=useStore();
        let orders=ref([]);
        let total_orders=0;
        let pages=ref([]);
        let current_page=1;
        const update_order=id=>{
            id;
            let a=1;
            if(a==1) return;
        }
        const click_page=page=>{
            let max_pages=parseInt(Math.ceil(total_orders/20));
            if(page===-2)page=1;
            else if(page===-1)page=max_pages;
            if(page>=1&&page<=max_pages){
                pull_page(page);
            }
        }
        const update_pages=()=>{
            let max_pages=parseInt(Math.ceil(total_orders/20));
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
                url:"http://127.0.0.1:3000/api/admin/order/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    page:page,
                    condition:"全部",
                },
                success(resp){
                    console.log(resp);
                    orders.value=resp.orders;
                    total_orders=resp.orders_count;
                    update_pages();
                },
            })
        }
        pull_page(current_page);

        return{
            orders,
            total_orders,
            pages,
            click_page,
            update_order,
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
</style>