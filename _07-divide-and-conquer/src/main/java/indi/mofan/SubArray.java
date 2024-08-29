package indi.mofan;

/**
 * @author mofan
 * @date 2022/11/12 19:52
 */
public class SubArray {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums));

        System.out.println(maxSubArray(nums, 0, nums.length));
    }

    static int maxSubArray(int[] nums, int begin, int end) {
        if (end - begin < 2) {
            return nums[begin];
        }
        int mid = (begin + end) >>> 1;

        int leftMax = nums[mid - 1];
        int leftSum = 0;
        for (int i = mid - 1; i >= begin; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax, leftSum);
        }

        int rightMax = nums[mid];
        int rightSum = 0;
        for (int i = mid; i < end; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax, rightSum);
        }

        return Math.max(
                leftMax + rightMax,
                Math.max(
                        maxSubArray(nums, begin, mid),
                        maxSubArray(nums, mid, end)
                )
        );
    }

    static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    static int maxSubArray1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                // sum 是 begin 到 end 之间子序列的和
                int sum = 0;
                for (int i = begin; i < end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}
