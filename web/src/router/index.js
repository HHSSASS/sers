import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/home/HomeView.vue'
import NoticeView from '../views/notice/NoticeView'
import NoticeContentView from '../views/notice/NoticeContentView'
import ShopView from '../views/shop/ShopView'
import ServiceView from '../views/service/ServiceView'
import SuperviseView from '../views/supervise/SuperviseView'
import UserView from '../views/user/UserView'
import UserLoginView from '../views/user/UserLoginView'
import UserRegisterView from '../views/user/UserRegisterView'
import NotFound from "../views/error/NotFound"

const routes = [
  {
    path: '/',
    name: 'home',
    redirect:"/home/"
  },
  {
    path: '/home/',
    name: 'home_index',
    component:HomeView
  },
  {
    path: '/notice/',
    name: 'notice_index',
    component:NoticeView
  },
  {
    path:'/notice/:noticeId',
    name:'notice_content',
    component:NoticeContentView,
  },
  {
    path: '/shop/',
    name: 'shop_index',
    component:ShopView
  },
  {
    path: '/service/',
    name: 'service_index',
    component:ServiceView
  },
  {
    path: '/supervise/',
    name: 'supervise_index',
    component:SuperviseView
  },
  {
    path: '/user/',
    name: 'user_index',
    component:UserView
  },
  {
    path: '/user/login/',
    name: 'user_login',
    component:UserLoginView
  },
  {
    path: '/user/register/',
    name: 'user_register',
    component:UserRegisterView
  },
  {
    path:"/404/",
    name:"404",
    component:NotFound,
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

export default router
