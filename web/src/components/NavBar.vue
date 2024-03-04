<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
    <div class="container">
      <router-link class="navbar-brand" :to="{name:'home'}">
        <img src="@/assets/images/logo.png" alt="" width="30px">
        SERS
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link :class="route_name == 'home_index' ? 'nav-link active' : 'nav-link'" :to="{name:'home_index'}">首页</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'notice_index' ? 'nav-link active' : 'nav-link'" :to="{name:'notice_index'}">通知公告</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'shop_index' ? 'nav-link active' : 'nav-link'" :to="{name:'shop_index'}">线上商城</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'service_index' ? 'nav-link active' : 'nav-link'" :to="{name:'service_index'}">会员服务</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'monitor_index' ? 'nav-link active' : 'nav-link'" :to="{name:'monitor_index'}">监测平台</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.user.admin">
            <router-link :class="route_name == 'admin_index' ? 'nav-link active' : 'nav-link'" :to="{name:'admin_index'}">管理系统</router-link>
          </li>
        </ul>
        <ul class="navbar-nav" v-if="$store.state.user.is_login">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              {{$store.state.user.username}}
            </a>
            <ul class="dropdown-menu">
              <li><router-link class="dropdown-item" :to="{name:'user_order_index'}">我的订单</router-link></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#" @click="logout">退出登录</a></li>
            </ul>
          </li>
        </ul>
        <ul class="navbar-nav" v-else-if="!$store.state.user.is_login">
          <li class="nav-item">
            <router-link class='nav-link' :to="{name:'user_account_login'}">登录</router-link>
          </li>
          <li class="nav-item">
            <router-link class='nav-link' :to="{name:'user_account_register'}">注册</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import { useStore } from 'vuex';

export default{//取当前在哪个页面
  setup(){
    const store=useStore();
    const route=useRoute();
    let route_name=computed(()=>route.name)
    const logout=()=>{
      store.dispatch("logout");
    }
    return{
      route_name,
      logout,
    }
  }
}
</script>

<style scoped>
</style>