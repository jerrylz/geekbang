package com.mophn.netty.lesson01;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyServer01 {
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(2);
        System.out.println(nioEventLoopGroup.next());
        System.out.println(nioEventLoopGroup.next());

        nioEventLoopGroup.next().execute(() -> {
            System.out.println("ok");
        });

        nioEventLoopGroup.next().scheduleAtFixedRate(() -> {
            System.out.println("ooo");
        }, 1,1, TimeUnit.SECONDS);

    }
}
