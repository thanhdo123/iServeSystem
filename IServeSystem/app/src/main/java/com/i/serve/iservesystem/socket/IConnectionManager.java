package com.i.serve.iservesystem.socket;

import java.io.IOException;

/**
 * Created by Tang_HN on 5/27/2015.
 */
public interface IConnectionManager {

    public void setupSocket() throws IOException;

    public void stop() throws IOException;

    public String writeAndWaitReply( String message );

}
