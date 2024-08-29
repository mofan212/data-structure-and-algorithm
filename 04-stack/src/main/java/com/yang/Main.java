package com.yang;

/**
 * @author 默烦
 * @date 2020/6/26
 */
public class Main {
    public static void main(String[] args) {
        java.util.Stack stack1 = new java.util.Stack();
        Stack<Integer> stack = new Stack<>();
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
}