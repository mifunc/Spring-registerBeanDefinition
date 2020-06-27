package com.rumenz;

public class RumenzA {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RumenzA{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public  RumenzA() {
        System.out.println("RumenzA 无参构造方法");
    }

}
