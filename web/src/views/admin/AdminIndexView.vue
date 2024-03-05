<template>
    <AdminNavbar>
        <div style="text-align: center;">尊敬的管理员您好，欢迎进入管理系统！</div>
    </AdminNavbar>
</template>

<script>
import AdminNavbar from '@/components/AdminNavbar.vue'

export default{
    components:{
        AdminNavbar,
    },
    setup(){
        
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