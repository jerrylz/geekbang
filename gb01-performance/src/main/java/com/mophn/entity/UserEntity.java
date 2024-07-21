package com.mophn.entity;

import java.util.List;

public class UserEntity {
    private String name;
    private List<Address> address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
