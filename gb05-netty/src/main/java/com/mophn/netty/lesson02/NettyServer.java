package com.mophn.netty.lesson02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
    public static void main(String[] args) throws Exception {
        try (NioEventLoopGroup boss = new NioEventLoopGroup();
             NioEventLoopGroup work = new NioEventLoopGroup()) {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler())
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast("h1", new SimpleChannelInboundHandler<String>() {
                                        @Override
                                        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
                                            log.debug("收到[{}]信息: {}", ch.remoteAddress(), msg);
                                            ctx.fireChannelRead("下一个处理谢谢");
                                        }
                                    }).addLast("h2", new SimpleChannelInboundHandler<String>() {
                                        @Override
                                        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
                                            log.debug("进一步处理{}", msg);
                                        }
                                    });
                        }
                    }).bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        }
    }
}
