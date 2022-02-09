package com.yang;

/**
 * @author 默烦
 * @date 2020/8/6
 */
public class Person implements Comparable<Person> {
    private String name;
    private int boneBreak;

    public Person(String name, int boneBreak) {
        this.name = name;
        this.boneBreak = boneBreak;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", boneBreak=" + boneBreak +
                '}';
    }

    @Override
    public int compareTo(Person person) {
        // boneBreak 越大，优先级越高
        return this.boneBreak - person.boneBreak;
    }
}
