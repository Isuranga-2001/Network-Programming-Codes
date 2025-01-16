package Lecture6;

import java.nio.ByteBuffer;

public class BufferExample {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put((byte) 10);
        buffer.put((byte) 20);
        buffer.put((byte) 30);

        buffer.mark();

        buffer.put((byte) 40);
        buffer.put((byte) 50);

        byte a = buffer.get();
        byte b = buffer.get();

        buffer.reset();

        byte c = buffer.get();
        byte d = buffer.get();

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
    }
}
