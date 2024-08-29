package com.yang;

/**
 * @author 默烦
 * @date 2020/6/17
 */
public class Main {
    public static void main(String[] args) {
        /*ArrayList<Student> list = new ArrayList<>();
        list.add(new Student(10,"Tom"));
        list.add(null);
        list.add(new Student(12, "Jerry"));
        list.add(null);
        System.out.println(list.indexOf(null));
        System.out.println(list);
        list.clear();
        System.gc();*/
        /*如果我们想在动态数组中使用多个类型
         * 可以：使用ArrayList<Object> list = new ArrayList<>();
         * */
       ArrayList2<Integer> list = new ArrayList2<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        for (int i = 0; i < 50; i++) {
            list.remove(0);
        }
        System.out.println(list);
    }
}