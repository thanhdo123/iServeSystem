package com.i.serve.iservesystem.application;

import android.app.Application;

import com.i.serve.iservesystem.socket.ClientConnectionManager;
import com.i.serve.iservesystem.socket.IConnectionManager;

import java.io.IOException;

/**
 * Created by Tang_HN on 5/27/2015.
 */
public class IServeApplication extends Application {
    private IConnectionManager iConnectionManager = null;
    private boolean isLoad = false;
    private boolean isLogin = false;

    public void setupConnection(String host, int port, int timeOut){
        if(!isLoad) {
            try {
                iConnectionManager = new ClientConnectionManager(host, port, timeOut);
                iConnectionManager.setupSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isLoad = true;
        }
    }

    public String writeAndWaitReply( String message ){
        return iConnectionManager.writeAndWaitReply(message);
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
