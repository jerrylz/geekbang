package com.mophn;

import com.mophn.web.NettyWebServer;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        NettyWebServer nettyWebServer = new NettyWebServer("com.mophn.sevlet");
        nettyWebServer.start();
    }
}
