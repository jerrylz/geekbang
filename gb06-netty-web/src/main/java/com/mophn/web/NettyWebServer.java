package com.mophn.web;

import com.mophn.web.api.HeroServlet;
import com.mophn.web.handler.HttpHeroHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NettyWebServer {
    private final Map<String, String> nameToClassNameMap = new HashMap<>();
    private final Map<String, HeroServlet> nameToServletMap = new ConcurrentHashMap<>();

    private final String basePackage;

    public NettyWebServer(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() throws Exception {
        // 加载servlet
        cacheClass(basePackage);
        run();

    }

    private void cacheClass(String basePackage) {
        URL resource = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        if (resource == null) {
            return;
        }
        File dir = new File(resource.getFile());
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                cacheClass(basePackage + "." + file.getName());
            } else {
                String simpleName = file.getName().replace(".class", "").trim();
                nameToClassNameMap.put(simpleName.toLowerCase(), basePackage + "." + simpleName);
            }
        }

    }


    public void run() throws Exception {
        try (NioEventLoopGroup boosGroup = new NioEventLoopGroup();
             NioEventLoopGroup workGroup = new NioEventLoopGroup();) {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler())
                                    .addLast(new HttpServerCodec())
                                    .addLast(new HttpHeroHandler(nameToClassNameMap, nameToServletMap));

                        }
                    }).bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        NettyWebServer nettyWebServer = new NettyWebServer("com.mophn");
        nettyWebServer.start();
    }

}
