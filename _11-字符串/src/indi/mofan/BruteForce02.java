package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/13 19:53
 */
public class BruteForce02 {
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0 || plen > tlen) return -1;

        int tiMax = tlen - plen;
        for (int ti = 0; ti <= tiMax; ti++) {
            int pi = 0;
            for (; pi < plen; pi++) {
                if (textChars[ti + pi] != patternChars[pi]) break;
            }
            /*
             * pi 有两种可能：
             * 1. 由于前面的 break 然后走到这，此时 pi != plen
             * 2. 前面的 for 循环结束，自然退出，此时 pi == plen
             */
            if (pi == plen) return ti;
        }
        return -1;
    }
}
