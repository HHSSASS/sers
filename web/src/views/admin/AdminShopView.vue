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
                            <input class="form-control" type="file" accept="image/*" id="formFile">
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">产品名称</label>
                            <textarea v-model="product.name" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入名称"></textarea>
                            <div style="float: right;">{{ product.name.length }}/100</div>
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">产品简介</label>
                            <textarea v-model="product.description" class="form-control" id="exampleFormControlTextarea1" rows="10" placeholder="请输入简介"></textarea>
                            <div style="float: right;">{{ product.description.length }}/1000</div>
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">产品定价</label>
                            <textarea v-model="product.price" class="form-control" id="exampleFormControlTextarea1" rows="1" placeholder="请输入定价"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="error-message" style="float: right;color: red;">{{ product.message }}</div>
                        <button type="button" class="btn btn-primary" @click="add_product">添加</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script>
import AdminNavbar from '@/components/AdminNavbar.vue'
import ContentField from '@/components/ContentField.vue'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import $ from 'jquery'
import { reactive } from 'vue'
import { useStore } from 'vuex'

export default{
    components:{
        AdminNavbar,
        ContentField,
    },
    setup(){
        const store=useStore();
        const product=reactive({
            name:"",
            description:"",
            price:"",
            message:"",
        });
        const add_product=()=>{
            product.message="";
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/product/add/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    name:product.title,
                    description:product.content,
                    price:product.price,
                },
                success(resp){
                    if(resp.message==="successful"){
                        Modal.getInstance("#add").hide();
                        product.name="";
                        product.description="";
                        product.price="";
                    }
                    else{
                        product.message=resp.message;
                    }
                },
            })
        }
        return{
            product,
            add_product,
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