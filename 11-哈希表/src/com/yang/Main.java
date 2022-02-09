package com.yang;

import com.yang.map.HashMap_v0;
import com.yang.map.Map;
import com.yang.model.Key;
import com.yang.model.Person;
import com.yang.model.SubKey1;
import com.yang.model.SubKey2;

/**
 * @author 默烦
 * @date 2020/7/22
 */
public class Main {

    static void test1() {
        String string = "jack"; // 3254239
        int len = string.length();
        int hashCode = 0;
        for (int i = 0; i < len; i++) {
            char c = string.charAt(i);
            hashCode = hashCode * 31 + c;
//            hashCode = (hashCode << 5) - hashCode + c;

        }
        System.out.println(hashCode);
        System.out.println(string.hashCode());
    }

    static void test2() {
        Integer a = 110;
        Float b = 10.6f;
        Long c = 156l;
        Double d = 10.9;
        String e = "mofan";

        System.out.println(a.hashCode()); // 110
        System.out.println(b.hashCode()); // 1093245338
        System.out.println(c.hashCode()); // 156
        System.out.println(d.hashCode()); // -1930887167
        System.out.println(e.hashCode()); // 104071729
    }

    static void test3() {
        Person p1 = new Person(20, 1.76f, "mofan");
        Person p2 = new Person(20, 1.76f, "mofan");
        // 和内存地址有关 因此两个输出并不相等
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());

//        Map<Object, Object> map = new HashMap<>();
//        map.put(p1,"abc");
//        map.put("test","ccc");
//        map.put(p2,"bcd");
//        System.out.println(map.size());
    }

    static void test4() {
        Person p1 = new Person(20, 1.76f, "mofan");
        Person p2 = new Person(20, 1.76f, "mofan");
        Map<Object, Integer> map = new HashMap_v0<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack", 3);
        map.put("rose", 4);
        map.put("jack", 5);
        map.put(null, 6);
//        System.out.println(map.size());
//        System.out.println(map.remove("jack"));
//        System.out.println(map.size());
        System.out.println(map.containsKey(p1));
        System.out.println(map.containsKey(null));
        System.out.println(map.containsValue(6));
        System.out.println(map.containsValue(1));
//        System.out.println(map.size());
//        System.out.println(map.get("jack"));
//        System.out.println(map.get("rose"));
//        System.out.println(map.get(null));
//        System.out.println(map.get(p1));
//        map.traversal(new Map.Visitor<Object, Integer>() {
//            @Override
//            public boolean visit(Object key, Integer value) {
//                System.out.println(key + "_" + value);
//                return false;
//            }
//        });
    }

    static void test5() {
        HashMap_v0<Object, Integer> map = new HashMap_v0<>();
        for (int i = 1; i <= 19; i++) {
            // 这 19 个元素都在同一个红黑树上
            map.put(new Key(i), i);
        }
//        map.traversal(new Map.Visitor<Object, Integer>() {
//            @Override
//            public boolean visit(Object key, Integer value) {
//                System.out.println(key + "_" + value);
//                return false;
//            }
//        });
//        System.out.println(map.size());
//        map.print();
        map.put(new Key(4), 100);
        Asserts.test(map.size() == 19);
        Asserts.test(map.get(new Key(4)) == 100);
        Asserts.test(map.get(new Key(18)) == 18);
    }

    static void test6() {
        Person p1 = new Person(10, 1.7f, "jack");
        Person p2 = new Person(10, 1.8f, "rose");
        System.out.println(p1.equals(p2)); // false
        System.out.println(p1.compareTo(p2)); // 0
    }

    static void test7() {
        SubKey1 key1 = new SubKey1(1);
        SubKey2 key2 = new SubKey2(1);
        HashMap_v0<Object, Integer> map = new HashMap_v0<>();
        map.put(key1, 1);
        map.put(key2, 2);
//        System.out.println(key2.equals(key1)); // true
//        System.out.println(map.size());
        System.out.println(key1.equals(key2));
        System.out.println(key2.equals(key1));
    }


    public static void main(String[] args) {
        test7();
    }
}
