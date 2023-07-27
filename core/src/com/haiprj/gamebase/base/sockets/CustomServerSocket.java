package com.haiprj.gamebase.base.sockets;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketImpl;

public class CustomServerSocket extends ServerSocket {
    protected CustomServerSocket(SocketImpl impl) {
        //noinspection Since15
        super(impl);
    }

    public CustomServerSocket() throws IOException {
    }

    public CustomServerSocket(int port) throws IOException {
        super(port);
    }

    public CustomServerSocket(int port, int backlog) throws IOException {
        super(port, backlog);
    }

    public CustomServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {
        super(port, backlog, bindAddr);
    }
}
