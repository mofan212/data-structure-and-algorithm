package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/15 20:50
 */
public class KMP {
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0 || plen > tlen) return -1;

        // 构建 next 表
        int[] next = next(pattern);

        int pi = 0, ti = 0, lenDelta = tlen - plen;
        while (pi < plen && ti - pi <= lenDelta) {
            // 模式串索引 0 位置的字符失配或两个字符匹配成功
            if (pi < 0 || patternChars[pi] == textChars[ti]) {
                ti++;
                pi++;
            } else {
                // 失配时使用 next 表
                pi = next[pi];
            }
        }
        return pi == plen ? ti - pi : -1;
    }

    private static int[] next(String pattern) {
        char[] chars = pattern.toCharArray();
        int[] next = new int[chars.length];

        next[0] = -1;
        int i = 0, n = -1, iMax = chars.length - 1;
        while (i < iMax) {
            if (n < 0 || chars[i] == chars[n]) {
                ++i;
                ++n;

                if (chars[i] == chars[n]) {
                    next[i] = next[n];
                } else {
                    next[i] = n;
                }
            } else {
                n = next[n];
            }
        }
        return next;
    }

    private static int[] next2(String pattern) {
        char[] chars = pattern.toCharArray();
        int[] next = new int[chars.length];

        next[0] = -1;
        int i = 0, n = -1, iMax = chars.length - 1;
        while (i < iMax) {
            if (n < 0 || chars[i] == chars[n]) {
                next[++i] = ++n;
            } else {
                n = next[n];
            }
        }
        return next;
    }
}
