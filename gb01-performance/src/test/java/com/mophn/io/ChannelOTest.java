package com.mophn.io;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelOTest {

    public static void main(String[] args) {
        try(FileInputStream in = new FileInputStream("/Users/jerrylz/Downloads/正版激活码.txt")) {
            FileChannel channel = in.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(in.available());
            channel.read(buffer);
            System.out.println(new String(buffer.array()));
        } catch (Exception e) {
            // ignore
        }
    }
}
