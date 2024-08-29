package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/3 17:10
 */
public class Main {
    public static void main(String[] args) {
        int n = 1_00_0000;
        BloomFilter<Integer> filter = new BloomFilter<>(n, 0.01);
        for (int i = 1; i <= n; i++) {
            filter.put(i);
        }

        for (int i = 1; i <= n; i++) {
            assert filter.contains(i);
        }

        int count = 0;
        for (int i = n + 1; i <= 2_000_0000; i++) {
            if (filter.contains(i)) {
                count++;
            }
        }
        System.out.println(count); // 1829434
    }
}
