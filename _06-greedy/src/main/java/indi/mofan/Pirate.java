package indi.mofan;

import java.util.Arrays;

/**
 * @author mofan
 * @date 2022/11/12 14:24
 */
public class Pirate {
    public static void main(String[] args) {
        int[] weights = {3, 5, 4, 10, 7, 14, 2, 11};
        // 先排序
        Arrays.sort(weights);

        int capacity = 30, weight = 0, count = 0;

        for (int i = 0; i < weights.length && weight < capacity; i++) {
            int newWeight = weight + weights[i];
            if (newWeight <= capacity) {
                weight = newWeight;
                count++;
            }
        }

        System.out.println("选取的货物数量为: " + count);
    }
}
