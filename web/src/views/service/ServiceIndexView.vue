<template>
    <ErrorBoard v-if="error"></ErrorBoard>
    <div class="mess_dialog">
        <div class="dlog_header">
            <div class="iconfont icon-rengongkefu" style="margin-top: 10px;margin-left: 20px; font-size: 30px;"></div>
            <div style="font-size: 25px;">客服</div>
        </div>
        <div class="dlog_content" id="content">
            <div class="content"  v-for="(dialog,index) in dialogs" :key="dialog.id" >
                <div v-if="index===0||(new Date(dialogs[index].time)-new Date(dialogs[index-1].time))/(1000 * 60)>5" class="time">{{ dialog.time }}</div>
                <div v-if="dialog.sendUserId===0">客服</div>
                <div v-else style="align-self: flex-end;">{{ $store.state.user.username }}</div>
                <div v-if="dialog.sendUserId===0" class="content_other">{{ dialog.content }}</div>
                <div v-else class="content_me">{{ dialog.content }}</div>                    
            </div>
        </div>
        <div class="dlog_footer">
            <textarea v-model="content" class="form-control" rows="5" style="resize: none;border-bottom: 1px solid rgb(204, 232, 255);"></textarea>
            <div style="display: flex;float: right;">
                <div style="color:red;padding-top: 15px;">{{ message }}</div>
                <div style="padding-top: 15px;">{{ content.length }}/500</div>
                <button @click="submit" class="btn btn-lg">发送</button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import ErrorBoard from '@/components/ErrorBoard.vue'
import { onMounted,onUnmounted } from 'vue'

export default{
    components:{
        ErrorBoard
    },
    setup(){
        const store=useStore();
        const socketurl=`ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        let socket=null;
        let heartbeat_id;
        let content=ref('');
        let message=ref('');
        let dialogs=ref([]);
        let error=ref(false);
        const pull_dialogs=()=>{
            $.ajax({
                url:"http://127.0.0.1:3000/api/dialog/getlist/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                success(resp){
                    if(resp.message==="successful"){
                        dialogs.value=resp.data;
                        setTimeout(()=>{
                            let dia = document.getElementById('content');
                            dia.scrollTop = dia.scrollHeight;
                        },100)
                    }
                },
            })
        }
        pull_dialogs();

        const submit=()=>{
            message.value="";
            $.ajax({
                url:"http://127.0.0.1:3000/api/dialog/add/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                   content:content.value,
                },
                success(resp){
                    if(resp.message==="successful"){
                        store.state.user.socket.send(JSON.stringify({
                            event:"send_dialog",
                            receive_id:0,
                        }))
                        pull_dialogs();
                        content.value="";
                    }
                    else{
                        message.value=resp.message;
                    }
                },
            })
        }
        onMounted(()=>{
            socket=new WebSocket(socketurl);
            socket.onopen=()=>{
                console.log("connected!");
                store.commit("updateSocket",socket);
                heartbeat_id=setInterval(()=>{
                    socket.send(JSON.stringify({
                         event:"heartbeat",
                     }));
                },1000);
            }
            socket.onmessage=msg=>{
                const data=JSON.parse(msg.data);
                if(data.event==="receive_dialog"){
                    pull_dialogs();
                }else if(data.event==="heartbeat"){
                    //console.log("heartbeat");
                }else if(data.event==="error"){
                    error.value=true;
                    setTimeout(()=>{
                        store.dispatch("logout");
                        location.reload();
                    },2000);
                }
            }
            socket.onclose=()=>{
                console.log("disconnected!");
                clearInterval(heartbeat_id);
            }
        })
        onUnmounted(()=>{
            socket.close();
        })
        return{
            content,
            message,
            dialogs,
            error,
            submit,
        }
    },
    beforeMount () {
        document.querySelector('body').setAttribute('style', "background-image: url("+require("@/assets/images/photo.png")+");background-size: cover;")
    },
    beforeUnmount () {
      document.querySelector('body').removeAttribute('style')
    },
    //SELECT （发送人，count(*)） FROM 表 where 发送人！=客服  and 未读=1  GROUP BY 发送人
}
</script>

<style scoped>
.mess_dialog{
    border-radius: 8px;
    background-clip: padding-box;
    margin:80px auto;   
    width: 800px;
    height: 700px;
    box-shadow: 0 0 10px #9b9393;
    background-color:white;
}
.dlog_header{
    width: 100%;
    height: 50px;
    border-bottom: 1px solid rgb(204, 232, 255);
    display: flex;
    align-items: center;
}
.dlog_content{
    width: 100%;
    height: 470px;
    border-bottom: 1px solid rgb(204, 232, 255);
    overflow-y: scroll;
    font-size: 20px;
}
.dlog_footer{
    width: 100%;
    height: 180px;
}
.time{
    background-color: rgb(230, 230, 230);
    border-radius: 10px;
    width: 150px;
    height: 20px;
    margin: 0 auto;
    text-align: center;
    font-size: 15px;
}
.content{
    display: flex;
    flex-direction: column;
    margin-left: 10px;
    margin-right: 10px;
}
.content_other{
    background-color: rgb(230, 230, 230);
    border-radius: 5px;
    max-width: 80%;
    align-self: flex-start;
    word-wrap: break-word;
    padding-left: 10px;
    padding-right: 10px;
    margin-bottom: 10px;
}
.content_me{
    background-color:rgb(204, 232, 255);
    border-radius: 5px;
    max-width: 80%;
    align-self: flex-end;
    word-wrap: break-word;
    padding-left: 10px;
    padding-right: 10px;
    margin-bottom: 10px;
}
.btn{
    border-radius:0px;
    border-bottom-right-radius: 8px;
    background-color: rgb(204, 232, 255);
}
</style>