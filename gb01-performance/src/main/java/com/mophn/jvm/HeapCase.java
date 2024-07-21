package com.mophn.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeapCase {
    public static void main(String[] args){
        List<Picture> pictures = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pictures.add(new Picture(new Random().nextInt(1024*1024)));
        }
    }


}

class Picture{
    private byte[] pixels;

    public Picture(int len) {
        this.pixels = new byte[len];
    }
}
