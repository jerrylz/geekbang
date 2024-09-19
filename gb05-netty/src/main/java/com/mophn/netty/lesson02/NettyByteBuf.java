package com.mophn.netty.lesson02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class NettyByteBuf {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4))
                .addLast(new LoggingHandler());

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        write(buffer, "Hello world!");
        write(buffer, "how are you!");
        channel.writeAndFlush(buffer);
    }

    private static void write(ByteBuf buffer, String content) {
        buffer.writeInt(content.length()).writeBytes(content.getBytes());
    }
}
