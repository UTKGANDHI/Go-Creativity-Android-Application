package com.example.ashutosh.mpiricmodule1;

/**
 * Created by Gandhi on 21-Dec-16.
 */

public class  VideoUrlCard {
    private String name, type, email;
    private String format, quality, videourl;
    private String thumbnail, url, status;


    public VideoUrlCard() {
    }

    public VideoUrlCard(String name, String format, String quality, String videourl) {
        this.name = name;
        this.format = format;
        this.quality = quality;
        this.videourl = videourl;
    }

    public String getName() {
        return name;
    }

    public String getformat() {
        return format;
    }

    public void setformat(String format) {
        this.format = format;
    }

    public void setquality(String quality) {
        this.quality = quality;
    }

    public String getquality() {
        return quality;
    }


    public String getUrl() {
        return videourl;
    }

    public void setUrl(String videourl) {
        this.videourl = videourl;
    }

}