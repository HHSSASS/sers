<template>
    <div class="container" style="width: 1100px;">
        <div style="background-color: rgb(7, 83, 169);width: 250px;height: 120px;margin-top: 20px;text-align: center;">
            <div style="padding-top: 35px;font-size: 30px; color: white;">实时监测图像</div>
        </div>
        <div>
            <img src="@/assets/images/保留时间.png" alt="">  
            <img src="@/assets/images/相关系数R.png" alt="">  
        </div>
        <div>
            <img src="@/assets/images/母离子.png" alt="">  
            <img src="@/assets/images/定量离子.png" alt="">  
        </div>
        <div>
            <img src="@/assets/images/DP.png" alt="">  
            <img src="@/assets/images/CE.png" alt="">  
        </div>
        <div>
            <img src="@/assets/images/LOQ.png" alt="">  
            <img src="@/assets/images/回收率.png" alt="">
        </div>
        <div style="width: 100%;height: 100px;"></div>  
    </div>
</template>

<script>
export default{
    beforeMount () {
        document.querySelector('body').setAttribute('style', "background-image: url("+require("@/assets/images/photo.png")+");background-size: cover;")
    },
    beforeUnmount () {
        document.querySelector('body').removeAttribute('style')
    }
}
</script>

<style scoped>
div>img{
    width: 500px;
    margin-right: 35px;
    margin-top: 50px;
}
</style>