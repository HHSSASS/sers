import $ from 'jquery'

export default{
    state: {
        id:"",
        username:"",
        token:"",
        is_login:false,
    },
    getters: {
    },
    mutations: {
        updateUser(state,user){
            state.id=user.id;
            state.username=user.username;
            state.is_login=user.is_login;
        },
        updateToken(state,token){
            state.token=token;
        },
        logout(state){
            state.id="";
            state.username="";
            state.token="";
            state.is_login=false;
        },
    },
    actions: {
        login(context,data){
            $.ajax({
                url:"http://127.0.0.1:3000/api/user/account/login/",
                type:"post",
                data:{
                  username:data.username,
                  password:data.password,
                },
                success(resp){
                    if(resp.message==="successful"){
                        localStorage.setItem("jwt_token",resp.token);
                        context.commit("updateToken",resp.token);
                        data.success(resp);
                    }
                    else{
                        data.error(resp);
                    }
                },
                error(resp){
                  data.error(resp);
                }
            })
        },
        getinfo(context,data){
            $.ajax({
                url:"http://127.0.0.1:3000/api/user/account/info/",
                type:"get",
                headers:{
                    Authorization:"Bearer "+context.state.token,
                },
                success(resp){
                    if(resp.message==="successful"){
                        context.commit("updateUser",{
                            ...resp,
                            is_login:true,
                        })
                        data.success(resp);
                    }
                    else{
                        data.error(resp);
                    }
                },
                error(resp){
                    data.error(resp);
                }
            })
        },
        logout(context){
            localStorage.removeItem("jwt_token");
            context.commit("logout");
        },
    },
    modules: {
    }
}