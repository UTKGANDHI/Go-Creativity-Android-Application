package com.example.ashutosh.mpiricmodule1;

/**
 * Created by Gandhi on 21-Dec-16.
 */

public class Card
{
    private String name,type,email;
    private Integer price,prod_id;
    private String thumbnail,url;


    public Card() {
    }

    public Card(String name, Integer numOfSongs, String thumbnail, String type,Integer prod_id, String email,String url) {
        this.name = name;
        this.price = numOfSongs;
        this.thumbnail = thumbnail;
        this.type=type;
        this.prod_id=prod_id;
        this.email=email;
        this.url=url;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumOfSongs() {
        return price;
    }

    public void setNumOfSongs(Integer numOfSongs) {
        this.price = numOfSongs;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getProd_id() {
        return prod_id;
    }

    public void setProd_id(Integer prod_id) {
        this.prod_id = prod_id;
    }

    public String getUrl(){return url;}

    public void setUrl(String url){this.url=url;}
}
