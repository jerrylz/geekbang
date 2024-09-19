package com.mophn.netty.lesson01;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

public class NettyPromise {
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = nioEventLoopGroup.next();
    }
}
