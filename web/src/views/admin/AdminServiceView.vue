<template>
    <ErrorBoard v-if="error"></ErrorBoard>
    <AdminNavbar></AdminNavbar>
    <div class="container">
        <div class="mess">
            <div class="mess_user_list">
                <div class="user">用户列表</div>
                <div class="user_list">
                    <div v-for="user in users" :key="user.id" @click="pull_dialogs(user)" class="user_list_item">
                        <div style="padding-left: 10px;">{{ user.username }}</div>
                    </div>
                </div>
            </div>
            <div v-if="current_user!=undefined" class="mess_dialog">
                <div class="dlog_header">
                    <div style="margin-left: 20px;font-size: 25px;">{{ current_user.username }}</div>
                </div>
                <div class="dlog_content" id="content">
                    <div class="content" v-for="(dialog,index) in dialogs" :key="dialog.id">
                        <div v-if="index===0||(new Date(dialogs[index].time)-new Date(dialogs[index-1].time))/(1000 * 60)>5" class="time">{{ dialog.time }}</div>
                        <div v-if="dialog.sendUserId===0" style="align-self: flex-end;">客服</div>
                        <div v-else >{{ $store.state.user.username }}</div>
                        <div v-if="dialog.sendUserId===0" class="content_me">{{ dialog.content }}</div>
                        <div v-else class="content_other">{{ dialog.content }}</div>                    
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
            <div v-else class="mess_dialog_false">
                <div>请选择用户对象</div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import { onMounted,onUnmounted } from 'vue'
import router from '@/router'
import ErrorBoard from '@/components/ErrorBoard.vue'
import AdminNavbar from '@/components/AdminNavbar.vue'

export default{
    components:{
        AdminNavbar,
        ErrorBoard,
    },
    setup(){
        const store=useStore();
        const socketurl=`ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        let socket=null;
        let heartbeat_id;
        let content=ref('')
        let message=ref('');
        let users=ref([]);
        let current_user=ref()
        let dialogs=ref([]);
        let error=ref(false);
        const pull_users=()=>{
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/getlistuser/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                success(resp){
                    if(resp.message==="successful"){
                        users.value=resp.data;
                    }
                },
            })
        }
        pull_users();
        
        const pull_dialogs=user=>{
            current_user.value=user;
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/getlistdialog/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:user.id,
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
        const submit=()=>{
            message.value="";
            $.ajax({
                url:"http://127.0.0.1:3000/api/admin/adddialog/",
                type:"post",
                headers:{
                    Authorization:"Bearer "+store.state.user.token,
                },
                data:{
                    id:current_user.value.id,
                    content:content.value,
                },
                success(resp){
                    if(resp.message==="successful"){
                        store.state.user.socket.send(JSON.stringify({
                            event:"send_dialog",
                            receive_id:current_user.value.id,
                        }))
                        pull_dialogs(current_user.value);
                        content.value="";
                    }
                    else{
                        message.value=resp.message;
                    }
                },
            })
        }

        onMounted(()=>{
            if(!store.state.user.admin){
                router.push({name:"home"});
            }
            else{
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
                        console.log(data.send_id);
                        console.log(current_user.value.id)
                        if(data.send_id===current_user.value.id){
                            pull_dialogs(current_user.value);
                        }
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
            }
        })
        onUnmounted(()=>{
            socket.close();
        })
        return{
            content,
            message,
            users,
            current_user,
            dialogs,
            error,
            pull_dialogs,
            submit,
        }
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
.mess{
    border-radius: 8px;
    background-clip: padding-box;
    margin:80px auto;   
    width: 850px;
    height: 700px;
    box-shadow: 0 0 10px #9b9393;
    background-color:white;
    display: flex;
}
.mess_user_list{
    width: 170px;
    background-color: rgb(204, 232, 255) ;
    border-right: 1px solid rgb(204, 232, 255);
    border-top-left-radius:8px;
    border-bottom-left-radius:8px;
}
.user{
    height: 5%;
    text-align: center;
    padding-top: 5px;
}
.user_list{
    height: 95%;
    overflow-y: scroll;
}
.user_list_item{
    height: 60px;
    background-color: white;
    border-right: 1px solid rgb(204, 232, 255);
    border-bottom: 1px solid rgb(204, 232, 255);
    display: flex;
    align-items: center;
    cursor: pointer;
}
.mess_dialog{
    width: 680px;
    height: 600px;
}
.mess_dialog_false{
  width: 680px;
  height: 600px;
  text-align: center;
  line-height: 500px;
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
.btn.btn-lg{
    border-radius:0px;
    border-bottom-right-radius: 8px;
    background-color: rgb(204, 232, 255);
}
.function{
    height: 5%;
    text-align: center;
    padding-top: 5px;
    cursor: pointer;
    background-color: white;
    border-top: 1px solid rgb(204, 232, 255);
    border-bottom-left-radius:8px;
}
.admin{
    margin-left: auto;
    margin-right: 10px;
    justify-content: flex-end;
    cursor: pointer;
}
</style>