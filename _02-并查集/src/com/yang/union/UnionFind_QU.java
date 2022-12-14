package com.yang.union;

/**
 * @author 默烦
 * @date 2020/8/18
 */
public class UnionFind_QU extends UnionFind {
    public UnionFind_QU(int capacity) {
        super(capacity);
    }

    /**
     * 通过 parents 链表不断向上找，直到找到根节点
     */
    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }

    /**
     * 将 v1 的根节点嫁接到 v2 的根节点上
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        parents[p1] = p2;
    }
}
