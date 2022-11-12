package indi.mofan;

import java.util.Arrays;

/**
 * @author mofan
 * @date 2022/11/12 14:37
 */
public class CoinChange {
    public static void main(String[] args) {
        coinChange(new Integer[]{25, 10, 5, 1}, 41);
        coinChange(new Integer[]{25, 20, 5, 1}, 41);
    }

    static void coinChange(Integer[] faces, int money) {
        // 从大到小排序
        Arrays.sort(faces, (o1, o2) -> o2 - o1);

        int coins = 0, i = 0;

        while (i < faces.length) {
            if (money < faces[i]) {
                i++;
                continue;
            }

            money -= faces[i];
            coins++;
        }

        System.out.println("最终的硬币个数为: " + coins);
    }
}
