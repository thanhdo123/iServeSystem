package com.i.serve.iservesystem.socket;

import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Tang_HN on 5/27/2015.
 */
public class ClientConnectionManager implements IConnectionManager {

    private static final String TAG = ClientConnectionManager.class.getSimpleName();

    private static final int DEFAULT_DELAY = 5000;

    private String _host;
    private InetAddress _hostInetAddress;
    private int _port;
    private int _timeOut;
    private ConnectionEstablisher _establishingThread;
    private int _delay;
    private Socket _socket;
    private Lock _writeLock;
    private volatile boolean _active;

    public ClientConnectionManager( String host, int port, int timeOut ) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        _host = host;
        _timeOut = timeOut;
        _port = port;
        _delay = DEFAULT_DELAY;
        _active = false;
        _writeLock = new ReentrantLock( true );
    }

    public void setupSocket() throws IOException {
        if ( _establishingThread == null || !_establishingThread.isAlive() ) {
            _hostInetAddress = InetAddress.getByName( _host );
            _establishingThread = new ConnectionEstablisher();
            _establishingThread.start();
        }
    }

    public void stop() throws IOException {
        _establishingThread.shutdown();
    }

    @Override
    public String toString() {
        String str = "Client mode: " + _hostInetAddress + ":" + _port + ".";
        if ( _establishingThread != null && _establishingThread.isAlive() ) {
            str += " Establishing.";
        }
        return str;
    }

    private class ConnectionEstablisher extends Thread {
        private volatile boolean _shutdown;

        public ConnectionEstablisher() {
            _shutdown = false;
        }

        @Override
        public void run() {
            while ( !_shutdown && !Thread.currentThread().isInterrupted() ) {
                try {
                    _socket = new Socket();
                    // Connects this socket to the server with a specified timeout value
                    // If timeout occurs, SocketTimeoutException is thrown
                    _socket.connect(new InetSocketAddress(_hostInetAddress, _port), _timeOut);
                    Log.d(TAG, "Established connection: " + _host + ":" + _port);
                    _active = true;
                    break;
                } catch ( Exception e ) {
                    Log.d(TAG, "Failed to establish connection: " + _host + ":" + _port + ". Will retry later. Reason: {}" + e.toString());
                    _active = false;
                }

                if ( _shutdown ) {
                    break;
                }

                try {
                    Thread.sleep( _delay );
                } catch ( InterruptedException ie ) {
                    break;
                }
            }
        }

        public void shutdown() {
            _active = false;
            _shutdown = true;
            this.interrupt();

            try {
                this.join( 5000 );
            }
            catch ( InterruptedException ie ) {
                Log.d(TAG, "Connection establishing thread failed to finish in 5 seconds, it is possible that it will stay in dangling state.");
            }
        }
    }

    public String writeAndWaitReply( String message ){
        if (_socket != null && _active) {
            Log.d(TAG, "--write message port " + _socket.getPort());
            Log.d(TAG, "--write message host " + _socket.getInetAddress().getHostAddress());
            _writeLock.lock();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(_socket.getOutputStream());
                oos.writeObject(message);

                ObjectInputStream ois = new ObjectInputStream(_socket.getInputStream());
                String response = (String) ois.readObject();
                Log.d(TAG, "server response: " + response);
                return response;
            } catch (Exception e) {
                Log.d(TAG, "error: ", e);
                return "error";
            } finally {
                _writeLock.unlock();
            }
        }
        return "error";
    }

}
