package com.yang;

/**
 * @author 默烦
 * @date 2020/8/11
 */
public class Student implements Comparable<Student> {
    /**
     * 为了测试，我们将访问修饰符设置为public
     * 正常开发中应该是私有的，具备封装性
     * 然后使用Get/Set方法来进行修改
     */
    public int score;
    public int age;

    public Student(int score, int age) {
        this.score = score;
        this.age = age;
    }

    @Override
    public int compareTo(Student o) {
        return age - o.age;
    }
}
