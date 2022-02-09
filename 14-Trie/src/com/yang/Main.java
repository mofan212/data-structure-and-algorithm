package com.yang;

/**
 * @author 默烦
 * @date 2020/8/7
 */
public class Main {

    static void test(){
        Trie<Integer> trie = new Trie<>();
        trie.add("cat", 1);
        trie.add("dog", 2);
        trie.add("catalog", 3);
        trie.add("cast", 4);
        trie.add("默烦", 5);
        System.out.println(trie.size() == 5);
        System.out.println(trie.startsWith("do"));
        System.out.println(trie.startsWith("c"));
        System.out.println(trie.startsWith("ca"));
        System.out.println(trie.startsWith("cat"));
        System.out.println(trie.startsWith("cata"));
        System.out.println(!trie.startsWith("hehe"));
        System.out.println(trie.get("默烦") == 5);
        System.out.println(trie.remove("cat") == 1);
        System.out.println(trie.remove("catalog") == 3);
        System.out.println(trie.remove("cast") == 4);
        System.out.println(trie.size() == 2);
        System.out.println(trie.startsWith("默"));
        System.out.println(trie.startsWith("do"));
        System.out.println(!trie.startsWith("c"));
    }

    public static void main(String[] args) {
            test();
    }
}
