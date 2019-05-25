package hw_8;

import java.io.File;
import java.io.FileOutputStream;

public class BitOutputStream {
    private byte internal;
    private int position;
    private FileOutputStream output;

    BitOutputStream(File file) throws Exception {
        output = new FileOutputStream(file);
        internal = 0;
        position = 0;

        writeBit("010000100100001001101");

        close();
    }

    void writeBit(char bit) throws Exception {
        internal <<= 1;
        if (bit == '1') {
            internal += 0b1;
        }
        position++;
        if (position == 8) {
            output.write(internal);
            internal = 0;
            position = 0;
        }
    }

    void writeBit(String bit) throws Exception {
        for (char ch : bit.toCharArray()) {
            writeBit(ch);
        }
    }

    void close() throws Exception {
        if (internal != 0) output.write(internal);
        output.close();
        internal = 0b00000000;
    }

    public static void main(String[] args) {
        try {
            new BitOutputStream(new File("C:\\Users\\16B1SEAS2873\\Desktop\\School\\2019 Spring\\java_hw_second\\src\\hw_8\\Exercise17_17.bat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
