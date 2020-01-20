package com.example.fluperapplication.Model;

public class Product {


    public String getName() {
        return name;
    }

    public String getDisc() {
        return disc;
    }

    public String getRp() {
        return rp;
    }

    public String getSp() {
        return sp;
    }


    String name, disc;
    String rp,sp,color,store;

    public String getColor() {
        return color;
    }

    public String getStore() {
        return store;
    }

    public byte[] getImage() {
        return image;
    }

    byte[] image;

    public Product( String name, String disc, String rp, String sp,String color,byte[] image,String store) {

        this.name = name;
        this.disc = disc;
        this.rp = rp;
        this.sp = sp;
        this.color = color;
        this.image = image;
        this.store = store;
    }


}
