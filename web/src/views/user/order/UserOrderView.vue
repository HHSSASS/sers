<template>
    <ContentField>
        <div style="display: flex;margin-bottom: 10px;justify-content: flex-end;">
            <div>筛选：</div>
            <div class="form-check" v-for="(sta,index) in status" :key="index">
                <input v-model="check" @change="pull_page(1)" class="form-check-input" type="radio" name="exampleRadios" :id="'Radios'+index" :value="index">
                <label class="form-check-label" :for="'Radios'+index">{{ sta }}</label>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover" style="text-align: center;">
                <thead>
                    <tr>
                        <th>订单id</th>
                        <th>产品名称</th>
                        <th>数量</th>
                        <th>支付金额</th>
                        <th>创建时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in items" :key="item.order.id">
                        <td>
                            <span>{{ item.order.id }}</span>
                        </td>
                        <td>
                            <span>{{ item.product_name==null?"产品已下架":(item.product_name.slice(0,10)+(item.product_name.length<=10?"":"...")) }}</span>
                        </td>
                        <td>
                            <span>{{ item.order.number }}</span>
                        </td>
                        <td>
                            <span>￥{{ item.order.price }}</span>
                        </td>
                        <td>
                            <span>{{ item.order.createTime }}</span>
                        </td>
                        <td>
                            <span>{{ status[item.order.status] }}</span>
                        </td>
                        <td>
                            <button type="button" class="btn out btn-secondary" data-bs-toggle="modal" :data-bs-target="'#open_order'+item.order.id">查看</button>         
                            <div class="modal fade" :id="'open_order'+item.order.id" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg" style="text-align: left;white-space:normal">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">订单详情</h1>
                                        </div>
                                        <div class="modal-body">
                                            <div class="item">订单id：{{ item.order.id }}</div>
                                            <div class="item">产品名称：{{ item.product_name==null?"产品已下架":item.product_name }}</div>
                                            <div class="item">数量：{{ item.order.number }}</div>
                                            <div class="item">电话号码：{{ item.order.phone }}</div>
                                            <div class="item">收货地址：{{ item.order.address }}</div>
                                            <div class="item">支付金额：￥{{ item.order.price }}</div>
                                            <div class="item">支付方式：{{ method[item.order.method] }}</div>
                                            <div class="item">创建时间：{{ item.order.createTime }}</div>
                                            <div class="item" v-if="item.order.sendTime!=null">发货时间：{{ item.order.sendTime }}</div>
                                            <div class="item" v-if="item.order.finishTime!=null">完成时间：{{ item.order.finishTime }}</div>
                                            <div class="item" v-if="item.order.refundTime!=null">退款时间：{{ item.order.refundTime }}</div>
                                            <div class="item">状态：{{ status[item.order.status] }}</div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                                        </div>
                                    </div>
                                </div>   
                            </div>                  
                            <button @click="update_order(item.order.id,3)" v-if="item.order.status==2" type="button" class="btn out btn-primary">确认收货</button>                                                           
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
import ContentField from '@/components/ContentField.vue'
import $ from 'jquery'
import { ref } from 'vue'
import { useStore } from 'vuex'

export default{
    components:{
        ContentField,
    },
    setup(){
        const store=useStore();
        let items=ref([]);
        let total_items=0;
        let pages=ref([]);
        let current_page=1;
        let check=ref(0);
        const status=["全部","待发货","已发货","已完成","已退款"];
        const method=["微信支付","支付宝支付"];
        const update_order=(id,sta)=>{
            $.ajax({
                url:"https://app6418.acapp.acwing.com.cn/api2/order/update/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:id,
                    status:sta,
                },
                success(resp){
                    if(resp.message=="successful"){
                        pull_page(current_page);
                    }
                },
            })
        }
        const click_page=page=>{
            let max_pages=parseInt(Math.ceil(total_items/20));
            if(page===-2)page=1;
            else if(page===-1)page=max_pages;
            if(page>=1&&page<=max_pages){
                pull_page(page);
            }
        }
        const update_pages=()=>{
            let max_pages=parseInt(Math.ceil(total_items/20));
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
            let condition=check.value;
            $.ajax({
                url:"https://app6418.acapp.acwing.com.cn/api2/order/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    page:page,
                    condition:condition,
                },
                success(resp){
                    if(resp.message=="successful"){
                        items.value=resp.items;
                        total_items=resp.items_count;
                        update_pages();
                    }
                },
            })
        }
        pull_page(current_page);

        return{
            items,
            total_items,
            pages,
            check,
            status,
            method,
            click_page,
            pull_page,
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
.th {
    white-space: nowrap;
}
.td {
    white-space: nowrap;
}
div.form-check{
    margin-right: 10px;
}
.btn.out{
    border-radius: 0px;
    margin-right: 10px;
}
div.item{
    margin-bottom: 10px;
}
</style>