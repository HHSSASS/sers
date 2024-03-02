<template>
    <div class="offcanvas offcanvas-start show" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
        <div style="height: 57.83px;"></div>
        <div>
            <div @click="to_home" class="function" :style="route_name=='admin_index'?'background-color:rgb(100,100,100);':'background-color:rgb(78,80,86);'">首页</div>
            <div @click="to_admin" class="function" :style="route_name=='admin_permission'?'background-color:rgb(100,100,100);':'background-color:rgb(78,80,86);'">权限管理</div>
            <div @click="to_notice" class="function" :style="route_name=='admin_notice'?'background-color:rgb(100,100,100);':'background-color:rgb(78,80,86);'">通知管理</div>
            <div @click="to_shop" class="function" :style="route_name=='admin_shop'?'background-color:rgb(100,100,100);':'background-color:rgb(78,80,86);'">产品管理</div>
            <div @click="to_gps" class="function" :style="route_name=='admin_gps'?'background-color:rgb(100,100,100);':'background-color:rgb(78,80,86);'">芯片管理</div>
            <div @click="to_order" class="function" :style="route_name=='admin_order'?'background-color:rgb(100,100,100);':'background-color:rgb(78,80,86);'">订单管理</div>
            <div @click="to_service" class="function" :style="route_name=='admin_service'?'background-color:rgb(100,100,100);':'background-color:rgb(78,80,86);'">客服系统</div>
        </div>
    </div>
</template>
  
<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import router from '@/router';

export default{
setup(){
    const route=useRoute();
    let route_name=computed(()=>route.name)
    const to_home=()=>{
        router.push({name:'admin_index'});
    }
    const to_admin=()=>{
        router.push({name:'admin_permission'});
    }
    const to_notice=()=>{
        router.push({name:'admin_notice'});
    }
    const to_shop=()=>{
        router.push({name:'admin_shop'});
    }
    const to_gps=()=>{
        router.push({name:'admin_gps'});
    }
    const to_order=()=>{
        router.push({name:'admin_order'});
    }
    const to_service=()=>{
        router.push({name:'admin_service'});
    }
    return{
        to_home,
        to_admin,
        to_notice,
        to_shop,
        to_gps,
        to_order,
        to_service,
        route_name,
    }
}
}
</script>

<style scoped>
div.offcanvas{
    background-color: rgb(43,48,54);
    width: 150px;
  }
  div.function{
    width: 100%;
    height: 50px;
    color: white;
    border-bottom: 1px solid rgb(43,48,54);
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
  }
  </style>