package indi.mofan;

/**
 * @author mofan
 * @date 2022/11/27 16:37
 */
public class LCSubstring {
    public static void main(String[] args) {
        String str1 = "ABDCBA";
        String str2 = "ABBA";
        System.out.println(lcs(str1, str2));
    }

    static int lcs(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;

        char[] closChars = chars1, rowsChars = chars2;
        if (chars1.length < chars2.length) {
            closChars = chars2;
            rowsChars = chars1;
        }
        int[] dp = new int[rowsChars.length + 1];
        int max = 0;

        for (int i = 1; i <= closChars.length; i++) {
            // 从大到下递减
            for (int j = rowsChars.length; j >= 1; j--) {
                if (closChars[i - 1] != rowsChars[j - 1]) {
                    dp[j] = 0;
                } else {
                    dp[j] = dp[j - 1] + 1;
                    max = Math.max(dp[j], max);
                }
            }
        }
        return max;
    }

    static int lcs2(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;

        char[] closChars = chars1, rowsChars = chars2;
        if (chars1.length < chars2.length) {
            closChars = chars2;
            rowsChars = chars1;
        }
        int[] dp = new int[rowsChars.length + 1];
        int max = 0;
        for (int i = 1; i <= closChars.length; i++) {
            int cur = 0;
            for (int j = 1; j <= rowsChars.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (closChars[i - 1] != rowsChars[j - 1]) {
                    dp[j] = 0;
                } else {
                    dp[j] = leftTop + 1;
                    max = Math.max(dp[j], max);
                }
            }
        }
        return max;
    }

    static int lcs1(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;

        int[][] dp = new int[chars1.length + 1][chars2.length + 1];
        int max = 0;
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                if (chars1[i - 1] != chars2[j - 1]) continue;
                dp[i][j] = dp[i - 1][j - 1] + 1;
                max = Math.max(dp[i][j], max);
            }
        }
        return max;
    }
}
