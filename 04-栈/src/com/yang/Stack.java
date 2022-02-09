package com.yang;

import com.yang.list.ArrayList;
import com.yang.list.List;

/**
 * @author 默烦
 * @date 2020/6/26
 */
public class Stack<E> {
    // 使用我们之前编写的ArrayList，而非JDK的
    private List<E> list = new ArrayList<>();

    public int size(){
        return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void push(E element){
        list.add(element);
    }

    public E pop(){
        return list.remove(list.size() - 1);
    }

    public E top(){
        return list.get(list.size() - 1);
    }
}
