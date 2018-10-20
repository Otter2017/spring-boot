package org.springframework.boot.peach.test;

import java.util.concurrent.locks.ReentrantLock;

public class Phone implements Cloneable {
    private String brand;
    private int size;
    private double price;

    public ReentrantLock lock;

    public Phone() {
        lock = new ReentrantLock();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    protected Phone clone() throws CloneNotSupportedException {
        return (Phone) super.clone();
    }

    @Override
    public int hashCode() {
        if (brand == null)
            return 0;
        return brand.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Phone) {
            return brand.equals(((Phone) obj).brand);
        }
        return false;
    }
}
