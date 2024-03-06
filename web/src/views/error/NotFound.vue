<template>
    <ContentField>
        <div>404 Not Found</div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'

export default{
    components:{
        ContentField,
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