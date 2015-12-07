package com.acer.recipes;

public class Product {
    private int id;
    private String name;
    private float ccal;

    Product(int _id, String _name, float _ccal)
    {
        id = _id;
        name = _name;
        ccal = _ccal;
    }
    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public float getCcal(){
        return ccal;
    }
}
