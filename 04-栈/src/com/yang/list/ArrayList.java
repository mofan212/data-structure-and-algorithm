package com.yang.list;

/**
 * @author 默烦
 * @date 2020/6/17
 */
// 动态数组
/*使用范型后，会出现内存管理问题*/
public class ArrayList<E> extends AbstractList<E> {

    // 所有的元素 存放的地址 而非元素本身1
    private E[] elements;

    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;
    private static final int ELEMENT_NOT_FOUND = -1;

    // capacity 动态数组容量
    public ArrayList(int capacity) {
        capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
        // Java 中所有的类都继承至 java.lang.Object
        // 注意是：Object ， 不是Objects
        elements = (E[]) new Object[capacity];
    }

    public ArrayList() {
//        elements = new int[DEFAULT_CAPACITY];
        this(DEFAULT_CAPACITY);
    }

    // 清除所有元素
    /*坚持一个思想：
    *   能够循环利用的空间留下
    *   不能循环利用的空间销毁
    * */
    public void clear() {
        // 使用范型后
        // 将数组中指向的内存空间进行销毁
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        // 表面情况，实际只是size = 0
        size = 0;
    }

    // 获取index位置的元素
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    // 设置index位置的元素
    public E set(int index, E element){
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    // 向index位置添加一个元素 且默认支持插入空数据null
    public void add(int index, E element){
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
    }

    // 删除index位置的元素
    public E remove(int index){
        rangeCheck(index);
        E old = elements[index];
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        /*size--;
        elements[size] = null;*/
        // 合并为
        elements[--size] = null;
        return old;
    }

    // 可以根据传递的元素进行删除
   /* public void remove(E element){
        remove(indexOf(element));
    }*/

    // 查看元素的索引
    public int indexOf(E element){
        // 处理空值
        if (element == null){
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        }else {
            for (int i = 0; i < size; i++) {
                /*if (elements[i] == element) return i;*/
                /* 使用这种方式可以自定义比较方式
                 *  使用这种方式时，实体类需要重写equals()方法才可以自定义
                 * */
                // 注意：是element调用的equals()方法，因为这个时候element一定不为空
                if (element.equals(elements[i])) return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    // 保证要有capacity的容量(不考虑线程安全)
    private void ensureCapacity(int capacity){
        // 当前elements.length 为 10，因为默认容量为10
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        // 新容量为新容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
        System.out.println(oldCapacity+"扩容为"+newCapacity);
    }

    @Override
    public String toString() {
        // 打印效果 size = 3, [99,88,77]
        StringBuilder builder = new StringBuilder();
        builder.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0){
                builder.append(", ");
            }
            builder.append(elements[i]);
           /* if (i != size - 1){
                builder.append(", ");
            }*/
        }
        builder.append("]");
        return builder.toString();
    }
}
