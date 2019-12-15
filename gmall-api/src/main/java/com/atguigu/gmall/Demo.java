package com.atguigu.gmall;

import java.util.Set;
import java.util.TreeSet;

public class Demo {
    public static void main(String[] args) {

        Set<Study> set = new TreeSet<Study>();
        set.add(new Study("邹志强",21));
        set.add(new Study("邹志强",2));
        set.add(new Study("邹志强",31));
        set.add(new Study("邹志强",101));
        System.out.println("hello world");
        for (Study study : set) {
            System.out.println(study);
        }
    }
}

class Study implements  Comparable<Study>{

    String name;
    int age;

    public Study(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Study{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int compareTo(Study o) {
        return o.age - this.age;
    }
}
