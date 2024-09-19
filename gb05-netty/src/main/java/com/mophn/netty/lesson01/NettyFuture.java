package com.mophn.netty.lesson01;

import cn.hutool.core.thread.ThreadUtil;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyFuture {
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = nioEventLoopGroup.next();
        Future<Integer> future = eventLoop.submit(() -> {
            log.info("===> 执行逻辑3s");
            ThreadUtil.sleep(3000);
            return 10;

        });
        future.addListener(f -> log.info("===> 结果{}", f.getNow()));

    }
}
