package com.mophn.netty.lesson02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttp {
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
                                    .addLast(new HttpServerCodec())
                                    .addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                        @Override
                                        protected void messageReceived(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                            log.debug("请求路径 {}", msg.uri());
                                            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                            byte[] content = "<h1>hello world!</h1>".getBytes();
                                            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(content.length));
                                            httpResponse.content().writeBytes(content);
                                            ctx.writeAndFlush(httpResponse);
                                        }
                                    });
                        }
                    }).bind(8080).sync();

            channelFuture.channel().closeFuture().sync();

        }
    }
}
