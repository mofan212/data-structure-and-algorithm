package indi.mofan;

/**
 * @author mofan
 * @date 2022/11/20 19:07
 */
public class LIS {
    public static void main(String[] args) {
        int[] nums = new int[]{10, 2, 2, 5, 1, 7, 101, 18};
        System.out.println(lengthOfLIS_1(nums));
    }

    static int lengthOfLIS_1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
