package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/11 21:36
 */
public class BruteForce01 {
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0 || plen > tlen) return -1;

        int pi = 0, ti = 0, lenDelta = tlen - plen;
        while (pi < plen && ti - pi <= lenDelta) {
            if (patternChars[pi] == textChars[ti]) {
                ti++;
                pi++;
            } else {
                ti -= pi - 1;
                pi = 0;
            }
        }
        return pi == plen ? ti - pi : -1;
    }

    public static int indexOf02(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0 || plen > tlen) return -1;

        int pi = 0, ti = 0;
        while (pi < plen && ti < tlen) {
            if (patternChars[pi] == textChars[ti]) {
                ti++;
                pi++;
            } else {
                ti -= pi - 1;
                pi = 0;
            }
        }
        return pi == plen ? ti - pi : -1;
    }
}
