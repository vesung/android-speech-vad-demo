package com.github.vesung.vadsdk.asr;

import java.util.Arrays;

/**
 * Created by wangjing.dc on 2018/9/12.
 */

class VadBuffer {

    // buffer最大尺寸
    private int size = 3000;
    // buffer实例
    private byte[] mybuffer = new byte[size];
    // buffer下标
    private int index = 0;
    // buffer增长幅度
    private int step = 1000;

    public void add(byte[] buffer) {
        // 调整step
        if(step < buffer.length * 10){
            step = buffer.length * 10;
        }
        if(buffer.length >= (size-index)){
            size += step;
            mybuffer = Arrays.copyOf(mybuffer, size);
        }

        System.arraycopy(buffer, 0, mybuffer, index, buffer.length);
        index += buffer.length;
    }

    public byte[] asByte() {
        return Arrays.copyOfRange(mybuffer, 0, index);
    }

    public static void main(String[] args){
        VadBuffer buf = new VadBuffer();
        byte[] a = new byte[]{'a', 'b', 'c'};
        System.out.println(Arrays.toString(a));
        buf.add(a);
        buf.add(a);
        buf.add(new byte[]{'1', '1', '5', '6', '7'});
        System.out.println(Arrays.toString(buf.asByte()));
        System.out.println(buf.size);

    }
}
