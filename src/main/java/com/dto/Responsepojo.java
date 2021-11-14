package com.dto;

import lombok.Data;

@Data
public class Responsepojo<T> {

    private String message;
    private int statusCode = 200;
    private T data;

    public Responsepojo(){

    }

}
