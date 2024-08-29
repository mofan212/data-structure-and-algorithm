package indi.mofan;


import com.yang.Trie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author mofan
 * @date 2024/8/29 15:23
 */
public class TrieTest {
    @Test
    public void test() {
        Trie<Integer> trie = new Trie<>();
        trie.add("cat", 1);
        trie.add("dog", 2);
        trie.add("catalog", 3);
        trie.add("cast", 4);
        trie.add("默烦", 5);

        assertEquals(5, trie.size());
        assertTrue(trie.startsWith("do"));
        assertTrue(trie.startsWith("c"));
        assertTrue(trie.startsWith("ca"));
        assertTrue(trie.startsWith("cat"));
        assertTrue(trie.startsWith("cata"));
        assertFalse(trie.startsWith("hehe"));

        assertEquals(5, trie.get("默烦"));
        assertEquals(1, trie.remove("cat"));
        assertEquals(3, trie.remove("catalog"));
        assertEquals(4, trie.remove("cast"));

        assertEquals(2, trie.size());
        assertTrue(trie.startsWith("默"));
        assertTrue(trie.startsWith("do"));
        assertFalse(trie.startsWith("c"));
    }
}
