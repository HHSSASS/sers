<template>
  <NavBar></NavBar>
  <router-view></router-view>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

export default{
  components:{
    NavBar
  },
}
</script>

<style>
.row {
    --bs-gutter-x: 0;
}
</style>
