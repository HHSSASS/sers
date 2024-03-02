import { createRouter, createWebHistory } from 'vue-router'
import HomeIndexView from '../views/home/HomeIndexView.vue'
import NoticeIndexView from '../views/notice/NoticeIndexView'
import NoticeContentView from '../views/notice/NoticeContentView'
import ShopIndexView from '../views/shop/ShopIndexView'
import ServiceIndexView from '../views/service/ServiceIndexView'
import MonitorIndexView from '../views/monitor/MonitorIndexView'
import MonitorContentView from '../views/monitor/MonitorContentView'
import UserAccountLoginView from '../views/user/account/UserAccountLoginView'
import UserAccountRegisterView from '../views/user/account/UserAccountRegisterView'
import NotFound from "../views/error/NotFound"
import AdminIndexView from "../views/admin/AdminIndexView"
import AdminPermissionView from "../views/admin/AdminPermissionView"
import AdminNoticeView from "../views/admin/AdminNoticeView"
import AdminShopView from "../views/admin/AdminShopView"
import AdminGpsView from "../views/admin/AdminGpsView"
import AdminOrderView from "../views/admin/AdminOrderView.vue"
import AdminServiceView from "../views/admin/AdminServiceView"
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
      requestAuth:true,
    },
  },
  {
    path: '/service/',
    name: 'service_index',
    component:ServiceIndexView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path: '/monitor/',
    name: 'monitor_index',
    component:MonitorIndexView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path: '/monitor/:gpsId',
    name: 'monitor_content',
    component:MonitorContentView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path:'/admin/',
    name:'admin_index',
    component:AdminIndexView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path:'/admin/permission/',
    name:'admin_permission',
    component:AdminPermissionView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path:'/admin/notice/',
    name:'admin_notice',
    component:AdminNoticeView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path:'/admin/shop/',
    name:'admin_shop',
    component:AdminShopView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path:'/admin/gps/',
    name:'admin_gps',
    component:AdminGpsView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path:'/admin/order/',
    name:'admin_order',
    component:AdminOrderView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path:'/admin/service/',
    name:'admin_service',
    component:AdminServiceView,
    meta:{
      requestAuth:true,
    },
  },
  {
    path: '/user/account/login/',
    name: 'user_account_login',
    component:UserAccountLoginView,
    meta:{
      requestAuth:false,
    },
  },
  {
    path: '/user/account/register/',
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
  if(!store.state.user.is_login){
    const jwt_token=localStorage.getItem("jwt_token");
    if(jwt_token){
      store.commit("updateToken",jwt_token);
      store.dispatch("getinfo",{
          success(){
              next();
          },
          error(){
            if(to.meta.requestAuth){
              next({name:"user_account_login"});
            }
            else{
              next();
            }
          },
      })
    }else{
      if(to.meta.requestAuth){
        next({name:"user_account_login"});
      }
      else{
        next();
      }
    }
  }else{
    next();
  }
})

export default router
