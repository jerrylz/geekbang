package com.mophn.netty.lesson02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class NettyClient {
    public static void main(String[] args) throws Exception {
        try (NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup()) {
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture channelFuture = bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast("h1", new SimpleChannelInboundHandler<String>() {
                                        @Override
                                        protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
                                            log.debug("客户端[{}] 收到消息: {}", ch.remoteAddress(), msg);
                                            ctx.fireChannelRead("12234");
                                        }
                                    });
                        }
                    }).connect("127.0.0.1", 8080).sync();

            Channel channel = channelFuture.channel();
            log.debug("------------------------------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }
        }
    }
}
