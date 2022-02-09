package com.yang.union;

/**
 * Quick Union - 基于 rank 的优化 - 路径压缩 (Path Compression)
 * @author 默烦
 * @date 2020/8/19
 */
public class UnionFind_QU_R_PC extends UnionFind_QU_R{
    public UnionFind_QU_R_PC(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);

        if (parents[v] != v) {
            // 从 v 到根节点路径上所有节点的父节点都指向根节点
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
