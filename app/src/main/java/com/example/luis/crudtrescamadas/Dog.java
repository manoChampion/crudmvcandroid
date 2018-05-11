package com.example.luis.crudtrescamadas;

import java.io.Serializable;
import java.util.Arrays;

public class Dog implements Serializable {
    private static final long serialVersionUID=1L;

    public Long _id;
    public String name;
    public String breed;
    public String sex;
    public String size;
    public byte[] image;

    @Override
    public String toString() {
        return "Dog{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", sex='" + sex + '\'' +
                ", size='" + size + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
