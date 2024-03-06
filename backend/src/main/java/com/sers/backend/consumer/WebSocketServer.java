package com.sers.backend.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sers.backend.mapper.UserMapper;
import com.sers.backend.pojo.User;
import com.sers.backend.utils.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
@ServerEndpoint("/websocket2/{token}")
public class WebSocketServer {
    final public static ConcurrentHashMap<Integer,WebSocketServer> users=new ConcurrentHashMap<>();
    private Session session=null;
    private User user;
    public static UserMapper userMapper;
    private boolean connected=false;
    private ReentrantLock lock=new ReentrantLock();

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper=userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session=session;
        System.out.println("connected!");
        Integer userId= JwtAuthentication.getUserId(token);//解析token
        this.user=userMapper.selectById(userId);
        if(this.user!=null){
            if(users.get(user.getId())!=null){
                JSONObject resp=new JSONObject();
                resp.put("event","error");
                users.get(user.getId()).sendMessage(resp.toJSONString());
                users.get(user.getId()).session.close();
            }
            users.put(userId,this);
        }
        else{
            this.session.close();
            return;
        }
        new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(5000);
                        lock.lock();
                        if(!connected) {
                            //System.out.println("disheartbeat");
                            if(session!=null){
                                session.close();
                            }else if (user != null) {
                                users.remove(user.getId());
                            }
                            break;
                        }
                        else{
                            connected=false;
                        }
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }.start();
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected!");
        if (this.user != null) {
            users.remove(this.user.getId());
        }
    }

    private  void sendDialog(Integer receiveId){
        if(receiveId==0){
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("admin",1);
            User admin=userMapper.selectOne(queryWrapper);
            receiveId=admin.getId();
        }
        if(users.get(receiveId)!=null){
            JSONObject resp=new JSONObject();
            resp.put("event","receive_dialog");
            resp.put("send_id",user.getId());
            users.get(receiveId).sendMessage(resp.toJSONString());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        //System.out.println("receive message!");
        JSONObject data= JSON.parseObject(message);
        String event=data.getString("event");
        if("send_dialog".equals(event)){
            sendDialog(data.getInteger("receive_id"));
        } else if("heartbeat".equals(event)){
            try {
                lock.lock();
                connected=true;
                //System.out.println("heartbeat");
                JSONObject resp=new JSONObject();
                resp.put("event","heartbeat");
                if(users.get(user.getId())!=null) {
                    sendMessage(resp.toJSONString());
                }
            }finally {
                lock.unlock();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message){
        synchronized (this.session){
            try {
                if(this.session!=null)
                    this.session.getBasicRemote().sendText(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}