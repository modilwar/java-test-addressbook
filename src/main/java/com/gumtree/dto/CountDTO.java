package com.gumtree.dto;

public class CountDTO {

    public CountDTO(){};

    public CountDTO(int count){
        this.count = count;
    };

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
