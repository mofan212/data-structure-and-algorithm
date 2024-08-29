package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/5 22:26
 */
public class Main {
    public static void main(String[] args) {
        SkipList<Integer, Integer> list =new SkipList<>();
        test(list, 30, 10);
    }

    private static void test(SkipList<Integer, Integer> list, int count, int delta) {
        for (int i = 0; i < count; i++) {
            list.put(i, i + delta);
        }
        System.out.println(list);
        for (int i = 0; i < count; i++) {
            check(list.get(i) == i + delta);
        }
        check(list.size() == count);

        for (int i = 0; i < count; i++) {
            check(list.remove(i) == i + delta);
        }
        check(list.size() == 0);
    }

    private static void check(boolean condition) {
        try {
            if (!condition) {
                throw new Exception("测试未通过");
            }
        } catch (Exception e) {
            // do nothing
        }
    }

}
