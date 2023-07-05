package com.example.shiyan2;

public class Author {
    String name;
    boolean now;
    public Author(String name,boolean now){
        this.name=name;
        this.now=now;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNow() {
        return now;
    }

    public void setNow(boolean now) {
        this.now = now;
    }

    public String toString(){
        return this.name;
    }

}
