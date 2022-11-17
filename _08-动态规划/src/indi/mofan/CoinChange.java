package indi.mofan;

/**
 * LeetCode 322 零钱兑换
 *
 * @author mofan
 * @date 2022/11/17 22:15
 */
public class CoinChange {
    public static void main(String[] args) {
        /*
         * 假设有 4 枚硬币，面值分别是：25 20 5 1
         * 如何选取最小枚的硬币使总面值达到指定金额。
         */
        System.out.println(coins_5(41, new int[]{1, 5, 20, 25}));
    }

    static int coins_5(int n, int[] faces) {
        if (n < 1 || faces == null || faces.length == 0) return -1;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int face : faces) {
                // 减去 face 后的剩余面额也无法被恰好凑齐
                if (i < face || dp[i - face] < 0) continue;
                min = Math.min(dp[i - face], min);
            }
            // 如果没有进前面的循环，证明无法恰好凑得目标金额
            if (min == Integer.MAX_VALUE) {
                dp[i] = -1;
            } else {
                dp[i] = min + 1;
            }
        }
        return dp[n];
    }

    static int coins_4(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        // faces[i] 表示凑够 i 分时第一次选择的硬币面值
        int[] faces = new int[dp.length];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            if (i >= 1 && dp[i - 1] < min) {
                min = dp[i - 1];
                faces[i] = 1;
            }
            if (i >= 5 && dp[i - 5] < min) {
                min = dp[i - 5];
                faces[i] = 5;
            }
            if (i >= 20 && dp[i - 20] < min) {
                min = dp[i - 20];
                faces[i] = 20;
            }
            if (i >= 25 && dp[i - 25] < min) {
                min = dp[i - 25];
                faces[i] = 25;
            }
            dp[i] = min + 1;
            print(faces, i);
        }
        return dp[n];
    }

    static void print(int[] faces, int n) {
        System.out.print("[" + n + "] = ");
        while (n > 0) {
            System.out.print(faces[n] + " ");
            n -= faces[n];
        }
        System.out.println();
    }

    static int coins_3(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
//            int min = Integer.MAX_VALUE;
//            if (i >= 1) min = Math.min(dp[i - 1], min);
            int min = dp[i - 1];
            if (i >= 5) min = Math.min(dp[i - 5], min);
            if (i >= 20) min = Math.min(dp[i - 20], min);
            if (i >= 25) min = Math.min(dp[i - 25], min);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    /**
     * 记忆化搜索（自顶向下的调用）
     *
     * @param n 目标金额
     * @return 最少硬币数量
     */
    static int coins_2(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        int[] faces = {1, 5, 20, 25};
        // 按已知金额与目标金额进行初始化
        for (int face : faces) {
            if (n < face) break;
            dp[face] = 1;
        }
        return coins_2(n, dp);
    }

    static int coins_2(int n, int[] dp) {
        if (n < 1) return Integer.MAX_VALUE;
        // 未计算过的子问题
        if (dp[n] == 0) {
            int min1 = Math.min(coins_2(n - 25, dp), coins_2(n - 20, dp));
            int min2 = Math.min(coins_2(n - 5, dp), coins_2(n - 1, dp));
            dp[n] = Math.min(min1, min2) + 1;
        }
        // 计算过的子问题
        return dp[n];
    }

    /**
     * 暴力递归（自顶向下的调用，会出现重叠子问题）
     *
     * @param n 目标金额
     * @return 最少硬币数量
     */
    static int coins_1(int n) {
        if (n < 1) return Integer.MAX_VALUE;
        if (n == 25 || n == 20 || n == 5 || n == 1) return 1;
        int min1 = Math.min(coins_1(n - 25), coins_1(n - 20));
        int min2 = Math.min(coins_1(n - 5), coins_1(n - 1));
        return Math.min(min1, min2) + 1;
    }
}
