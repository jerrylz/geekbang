package com.mophn.jvm;

import org.openjdk.jol.info.ClassLayout;

public class ObjectLock01 {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}
