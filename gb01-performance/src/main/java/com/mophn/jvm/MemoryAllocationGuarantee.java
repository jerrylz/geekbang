package com.mophn.jvm;

/**
 * 内存担保案例:
 *      当新生代无法分配内存的时候，我们先把新生代的老对象转移到老年代，然后把新对象放入腾空的新生代
 */
public class MemoryAllocationGuarantee {
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) {
        memoryAllocation();
    }

    /**
     * 内存分配
     */
    private static void memoryAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[1 * _1MB];
        allocation2 = new byte[1 * _1MB];
        allocation3 = new byte[1 * _1MB];
        allocation4 = new byte[5 * _1MB];
        System.out.println("分配完毕");

    }
}
