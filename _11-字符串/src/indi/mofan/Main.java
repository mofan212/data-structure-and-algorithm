package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/11 21:35
 */
public class Main {
    public static void main(String[] args) {
        assert BruteForce02.indexOf("Hello World", "or") == 7;
        assert BruteForce02.indexOf("Hello World", "abc") == -1;
        assert BruteForce02.indexOf("Hello World", "H") == 0;
        assert BruteForce02.indexOf("Hello World", "d") == 10;
    }
}
