package com.yang.union;

/**
 * Quick Union - 基于 rank 的优化 - 路径减半 (Path Halving)
 * @author 默烦
 * @date 2020/8/19
 */
public class UnionFind_QU_R_PH extends UnionFind_QU_R {
    public UnionFind_QU_R_PH(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            parents[v] = parents[parents[v]];
            v = parents[v];
        }
        return v;
    }
}
