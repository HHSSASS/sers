<template>
    <AdminNavbar></AdminNavbar>
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