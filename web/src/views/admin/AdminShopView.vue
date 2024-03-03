<template>
    <AdminNavbar></AdminNavbar>
    <ContentField>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#add_product" style="border-radius: 0px;">添加产品</button>
        <div class="modal fade" id="add_product" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">添加产品</h1>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="formFile" class="form-label">产品图片</label>
                            <input @change="add_img" class="form-control" type="file" accept="image/*" id="formFile">
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">产品名称</label>
                            <textarea v-model="new_product.name" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入名称"></textarea>
                            <div style="float: right;">{{ new_product.name.length }}/100</div>
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">产品简介</label>
                            <textarea v-model="new_product.description" class="form-control" id="exampleFormControlTextarea1" rows="10" placeholder="请输入简介"></textarea>
                            <div style="float: right;">{{ new_product.description.length }}/1000</div>
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">产品定价</label>
                            <textarea v-model="new_product.price" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入定价"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="error-message" style="float: right;color: red;">{{ new_product.message }}</div>
                        <button type="button" class="btn btn-primary" @click="add_product">添加</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive"> 
            <table class="table table-hover" style="text-align: center;">
                <thead>
                    <tr>
                        <th>图片</th>
                        <th>名称</th>
                        <th>简介</th>
                        <th>定价</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="product in products" :key="product.id">
                        <td>
                            <img :src="require('@/assets/images/'+product.id+'.png')" alt="" width="50px">
                        </td>
                        <td>
                            <span>{{ product.name.slice(0,10)+(product.name.length<=10?"":"...") }}</span>
                        </td>
                        <td>
                            <span>{{ product.description.slice(0,10)+(product.description.length<=10?"":"...") }}</span>
                        </td>
                        <td>
                            <span>{{ product.price }}</span>
                        </td>
                        <td>
                            <div style="display: flex;justify-content: center;">                                        
                                <div class="op" data-bs-toggle="modal" :data-bs-target="'#update_product'+product.id">修改</div>        
                                <div class="modal fade" :id="'update_product'+product.id" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-lg" style="text-align: left;">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">修改产品</h1>
                                            </div>
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label for="formFile" class="form-label">产品图片</label>
                                                    <input @change="update_img($event,product)" class="form-control" type="file" accept="image/*" id="formFile">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="exampleFormControlTextarea1" class="form-label">产品名称</label>
                                                    <textarea v-model="product.name" class="form-control" id="exampleFormControlTextarea1" rows="2" placeholder="请输入名称"></textarea>
                                                    <div style="float: right;">{{ product.name.length }}/100</div>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="exampleFormControlTextarea1" class="form-label">产品简介</label>
                                                    <textarea v-model="product.description" class="form-control" id="exampleFormControlTextarea1" rows="12" placeholder="请输入简介"></textarea>
                                                    <div style="float: right;">{{ product.description.length }}/1000</div>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="exampleFormControlTextarea1" class="form-label">产品定价</label>
                                                    <textarea v-model="product.price" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入定价"></textarea>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <div class="error-message" style="float: right;color: red;">{{ product.message }}</div>
                                                <button type="button" class="btn btn-primary" @click="update_product(product)">修改</button>
                                                <button type="button" class="btn btn-secondary" @click="cancel(product.id)">取消</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>                                 
                                    <div class="op" @click="remove_product(product.id)">删除</div>   
                                </div>          
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
        let products=ref([]);
        let total_products=0;
        let pages=ref([]);
        let current_page=1;
        const new_product=reactive({
            photofile:null,
            name:"",
            description:"",
            price:"",
            message:"",
        });
        const add_img=(e)=>{
            new_product.photofile=e.target.files[0];
        }
        const update_img=(e,product)=>{
            product.photofile=e.target.files[0];
        }
        const add_product=()=>{
            new_product.message="";
            if(new_product.photofile==null){
                new_product.message="请选择图片";
                return;
            }
            if(new_product.photofile.size/1024/1000>1){
                new_product.message="图片大小不能超过1MB";
                return;
            }
            let formData=new FormData();
            formData.append("photo",new_product.photofile);
            formData.append("name",new_product.name)
            formData.append("description",new_product.description)
            formData.append("price",new_product.price)
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/shop/add/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:formData,
                contentType:false,
                processData: false,
                success(resp){
                    if(resp.message==="successful"){
                        Modal.getInstance("#add_product").hide();
                        new_product.photofile=null;
                        new_product.name="";
                        new_product.description="";
                        new_product.price="";
                        pull_page(1);
                    }
                    else{
                        new_product.message=resp.message;
                    }
                },
            })
        }
        const update_product=(product)=>{
            product.message="";
            if(product.photofile==null){
                product.message="请选择图片";
                return;
            }
            if(product.photofile.size/1024/1000>1){
                product.message="图片大小不能超过1MB";
                return;
            }
            let formData=new FormData();
            formData.append("id",product.id)
            formData.append("photo",product.photofile);
            formData.append("name",product.name)
            formData.append("description",product.description)
            formData.append("price",product.price)
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/shop/update/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:formData,
                contentType:false,
                processData: false,
                success(resp){
                    if(resp.message==="successful"){
                        Modal.getInstance('#update_product'+product.id).hide();
                        pull_page(current_page);
                    }
                    else{
                        product.message=resp.message;
                    }
                },
            })
        }
        const remove_product=(id)=>{
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/shop/remove/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:id,
                },
                success(resp){
                    if(resp.message==="successful"){
                        pull_page(1);
                    }
                },
            })
        }
        const cancel=(id)=>{
            Modal.getInstance('#update_product'+id).hide();
            pull_page(current_page);
        }
        const click_page=page=>{
            let max_pages=parseInt(Math.ceil(total_products/8));
            if(page===-2)page=1;
            else if(page===-1)page=max_pages;
            if(page>=1&&page<=max_pages){
                pull_page(page);
            }
        }
        const update_pages=()=>{
            let max_pages=parseInt(Math.ceil(total_products/8));
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
                url:"http://127.0.0.1:3000/api/shop/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    page:page,
                    number:20,
                },
                success(resp){
                    products.value=resp.products;
                    total_products=resp.products_count;
                    update_pages();
                },
            })
        }
        pull_page(current_page);
        return{
            new_product,
            products,
            total_products,
            pages,
            add_product,
            update_product,
            remove_product,
            cancel,
            click_page,
            add_img,
            update_img,
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