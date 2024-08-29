package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/11 21:35
 */
public class Main {
    public static void main(String[] args) {
        assert KMP.indexOf("Hello World", "or") == 7;
        assert KMP.indexOf("Hello World", "abc") == -1;
        assert KMP.indexOf("Hello World", "H") == 0;
        assert KMP.indexOf("Hello World", "d") == 10;
    }
}
