package com.yang;

/**
 * @author 默烦
 * 2020/9/3
 */
public class ClimbStairs {
    /**
     * 假设楼梯有 2 阶，可以很快地计算出上台阶的方式
     * 但如果阶数多了，就不是那么容易计算了
     * 根据这个现象，可以考虑使用递归来解决
     */
    int climbStairs(int n){
        if (n <= 2) return n;
//        return climbStairs(n -1) + climbStairs(n - 2);
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++){
            second = first + second;
            first = second - first;
        }
        return second;
    }


}
