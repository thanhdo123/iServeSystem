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

    public void setupConnection(String host, int port){
        if(!isLoad) {
            try {
                iConnectionManager = new ClientConnectionManager(host, port);
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
}
