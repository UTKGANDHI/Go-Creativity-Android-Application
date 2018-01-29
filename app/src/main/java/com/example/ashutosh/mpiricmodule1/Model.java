package com.example.ashutosh.mpiricmodule1;

/**
 * Created by Gandhi on 27-Dec-16.
 */

public class Model
{
    String name;
    int value;
    Model(String name,int value)
    {
        this.name=name;
        this.value=value;
    }
    public String getName()
    {
        return this.name;
    }
    public int getValue()
    {
        return this.value;
    }
}
