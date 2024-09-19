package com.mophn.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class NettyChatClient {
    public void run() throws Exception {
        try(NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup()) {
            Bootstrap bootstrap = new Bootstrap();
            Channel channel = bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new SimpleChannelInboundHandler<String>(){
                                @Override
                                protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
                                    System.out.println(msg);
                                }

                            });
                        }

                    }).connect("127.0.0.1", 9980).sync().channel();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }


        }
    }

    public static void main(String[] args) throws Exception {
        NettyChatClient nettyChatClient = new NettyChatClient();
        nettyChatClient.run();
    }
}
