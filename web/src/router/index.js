import { createRouter, createWebHistory } from 'vue-router'
import HomeIndexView from '../views/home/HomeIndexView.vue'
import NoticeIndexView from '../views/notice/NoticeIndexView'
import NoticeContentView from '../views/notice/NoticeContentView'
import ShopIndexView from '../views/shop/ShopIndexView'
import ServiceIndexView from '../views/service/ServiceIndexView'
import SuperviseIndexView from '../views/supervise/SuperviseIndexView'
import UserIndexView from '../views/user/UserIndexView'
import UserAccountLoginView from '../views/user/account/UserAccountLoginView'
import UserAccountRegisterView from '../views/user/account/UserAccountRegisterView'
import NotFound from "../views/error/NotFound"
import ChatIndexView from "../views/chat/ChatIndexView"
import store from '../store/index'

const routes = [
  {
    path: '/',
    name: 'home',
    redirect:"/home/"
  },
  {
    path: '/home/',
    name: 'home_index',
    component:HomeIndexView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path: '/notice/',
    name: 'notice_index',
    component:NoticeIndexView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path:'/notice/:noticeId',
    name:'notice_content',
    component:NoticeContentView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path: '/shop/',
    name: 'shop_index',
    component:ShopIndexView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path: '/service/',
    name: 'service_index',
    component:ServiceIndexView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path: '/supervise/',
    name: 'supervise_index',
    component:SuperviseIndexView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path:'/chat/',
    name:'chat_index',
    component:ChatIndexView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path: '/user/',
    name: 'user_index',
    component:UserIndexView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path: '/user/login/',
    name: 'user_account_login',
    component:UserAccountLoginView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path: '/user/register/',
    name: 'user_account_register',
    component:UserAccountRegisterView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path:"/404/",
    name:"404",
    component:NotFound,
    meta:{
      requestAuth:false,
    },
  },
  {
    path:"/:catchAll(.*)",
    redirect:"/404/",
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to,from,next)=>{
  if(to.meta.requestAuth&&!store.state.user.is_login){
    const jwt_token=localStorage.getItem("jwt_token");
    if(jwt_token){
      store.commit("updateToken",jwt_token);
      store.dispatch("getinfo",{
          success(){
              next();
          },
          error(){
              next({name:"user_account_login"});
          },
      })
    }else{
      next({name:"user_account_login"});
    }
  }else{
    next();
  }
})

export default router
