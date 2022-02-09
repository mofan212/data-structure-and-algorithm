package com.yang.model;

/**
 * @author 默烦
 * @date 2020/7/23
 */
public class Person implements Comparable<Person> {
    private int age;
    private float height;
    private String name;

    public Person(int age, float height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(age);
        hashCode = hashCode * 31 + Float.hashCode(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0 );
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) { // 是否相等
        // 内存地址相等时
        if (obj == this) return true;
        // 传递对象为 null 时，传递对象与调用对象类型不同时
        if (obj == null || obj.getClass() != getClass()) return false;
//        if (obj == null || !(obj instanceof Person)) return false;
        Person person = ((Person) obj);
        return person.age == age && person.height == height
                && valueEquals(person.name, name);
    }

    private boolean valueEquals(Object v1, Object v2){
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    // age 较大的对象更大
    @Override
    public int compareTo(Person o) { // 比大小
        return age - o.age;
    }
}
