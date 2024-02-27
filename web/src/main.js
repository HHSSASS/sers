import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/font/font.less'

document.title='SERS'
createApp(App).use(store).use(router).mount('#app')
