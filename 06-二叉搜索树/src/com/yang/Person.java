package com.yang;

/**
 * @author 默烦
 * @date 2020/7/4
 */
public class Person implements Comparable<Person> {
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Person person) {
        return age - person.age;
    }

    @Override
    public String toString() {
        return  "age=" + age;
    }
}
