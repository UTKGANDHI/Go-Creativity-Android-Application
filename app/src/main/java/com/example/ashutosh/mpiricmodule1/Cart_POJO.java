package com.example.ashutosh.mpiricmodule1;

/**
 * Created by Gandhi on 02-Mar-17.
 */

public class Cart_POJO {

    //private variables
    int prod_id;
    String u_name;

    public Cart_POJO(){}

    public Cart_POJO(int prod_id,String u_name)
    {
        this.prod_id=prod_id;
        this.u_name=u_name;
    }

    public int getProd_id()
    {
        return this.prod_id;
    }

    public void setProd_id(int prod_id)
    {
        this.prod_id=prod_id;
    }

    public String getU_name()
    {
        return this.u_name;
    }

    public void setU_name(String u_name)
    {
        this.u_name=u_name;
    }

}
