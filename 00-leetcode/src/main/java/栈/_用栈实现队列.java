package 栈;

import java.util.Stack;

/**
 * @author 默烦
 * @date 2020/7/2
 */
public class _用栈实现队列 {
    private Stack<Integer> inStack;
    private Stack<Integer> outStack;

    public _用栈实现队列() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    // 入队
    public void push(int x){
        inStack.push(x);
    }

    //出队
    public int pop(){
        checkOutStack();
        return outStack.pop();
    }

    // 获取对头元素
    public int peek(){
        checkOutStack();
        return outStack.peek();
    }

    // 是否为空
    public boolean empty(){
        return inStack.empty() && outStack.empty();
    }

    private void checkOutStack(){
        if (outStack.empty()){
            while (!inStack.empty()){
                outStack.push(inStack.pop());
            }
        }
    }

}
