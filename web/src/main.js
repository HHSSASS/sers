import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/assets/font/font.less'
import '@/assets/iconfont/iconfont.css'
import '@/assets/iconfont/iconfont.js'

document.title='SERS'
createApp(App).use(store).use(router).mount('#app')
