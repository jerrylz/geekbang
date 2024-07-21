package com.mophn.jvm;

import org.openjdk.jol.info.ClassLayout;

public class ObjectLock02 {
    public static void main(String[] args) {
        People p1 = new People();
        System.out.println(ClassLayout.parseInstance(p1).toPrintable());

        p1.setAge(30);
        p1.setNan(true);
        p1.setName("杨某");
        System.out.println(ClassLayout.parseInstance(p1).toPrintable());
    }

    static class People{

        private String name;
        private int age;
        private boolean isNan;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isNan() {
            return isNan;
        }

        public void setNan(boolean nan) {
            isNan = nan;
        }
    }
}
