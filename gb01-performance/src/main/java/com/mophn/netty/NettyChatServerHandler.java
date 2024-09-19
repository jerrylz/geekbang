package com.mophn.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static final DefaultChannelGroup clientGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        clientGroup.add(channel);
        System.out.println(channel.remoteAddress().toString() + " 上线");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        clientGroup.remove(channel);
        System.out.println(channel.remoteAddress().toString() + " 下线");
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        String newMsg = ctx.channel().remoteAddress().toString()+": "+msg;
        System.out.println(newMsg);
        clientGroup.stream().filter(channel -> ctx.channel() != channel)
                    .forEach(channel -> channel.writeAndFlush(newMsg));

    }

}
