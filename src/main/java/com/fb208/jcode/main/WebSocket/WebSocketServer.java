package com.fb208.jcode.main.WebSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * socket推送消息(服务端)
 */
@ServerEndpoint(value = "/socket/websocket/{idno}")
//@ServerEndpoint(value = "/socket/websocket/{idno}")
@Component
public class WebSocketServer {

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private static int onlineCount = 0;
    private static CopyOnWriteArrayList<WebSocketServer> webSockets = new CopyOnWriteArrayList<>();
    private static ConcurrentMap<String, WebSocketServer> socketConcurrentHashMap = new ConcurrentHashMap<>();
    private Session session;
    private String idno;
    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(@PathParam("idno")String idno, Session session){
        this.session = session;
        this.idno = idno;
        socketConcurrentHashMap.put(idno,this);
        webSockets.add(this);     //加入set中
        addOnlineCount();
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose(){
        if (!idno.equals("")) {
            socketConcurrentHashMap.remove(idno);  //从set中删除
        }
        webSockets.remove(this);
        subOnlineCount();
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        logger.info("client message:"+message);
        if(StringUtils.isEmpty(idno)){  //群发消息
            sendAll(message);
        }else{  //单独推送
            if(message.equalsIgnoreCase("ping")){
                heartCheck(message);
            }else{
                sendToUser(message);
            }
        }
    }

    private void sendAll(String message){
        for (String userId:socketConcurrentHashMap.keySet()) {
            try {
                socketConcurrentHashMap.get(userId).sendMessage(message);
            } catch (IOException e) {
                logger.error("WebSocketServer|sendAll[error]:e={}",e.getMessage());
            }
        }
    }
    private void heartCheck(String message){
        if(socketConcurrentHashMap.get(idno)!=null){
            try {
                socketConcurrentHashMap.get(idno).sendMessage(message);
            } catch (IOException e) {
                logger.error("WebSocketServer|heartCheck[error]:e={}",e.getMessage());
            }
        }
    }
    private void sendToUser(String message){
        if(socketConcurrentHashMap.get(idno)!=null){
            for (String userId:socketConcurrentHashMap.keySet()) {
                if(userId.equals(this.idno)){
                    continue;
                }
                try {
                    socketConcurrentHashMap.get(userId).sendMessage(message);
                } catch (IOException e) {
                    logger.error("WebSocketServer|sendToUser[error]:e={}",e.getMessage());
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable e){
//        throwable.printStackTrace();
        logger.error("WebSocketServer|onError[error]:e={}",e.getMessage());
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
}
