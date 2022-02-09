package com.yang;

import com.yang.map.Map;
import com.yang.map.TreeMap;
import com.yang.set.Set;
import com.yang.set.TreeSet;

/**
 * @author 默烦
 * @date 2020/7/20
 */
public class Main {

    static void test1(){
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 2);
        map.put("a", 5);
        map.put("b", 6);
        map.put("a", 8);
        // 遍历出来结果是从小到大的
        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    static void test2(){
        Set<String> set = new TreeSet<>();
        set.add("a");
        set.add("c");
        set.add("b");
        set.add("c");
        set.add("c");
        set.traversal(new Set.Visitor<String>() {
            @Override
            public boolean visit(String element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void main(String[] args) {
        test2();
    }
}
