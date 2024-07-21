package com.mophn;

import com.fasterxml.jackson.annotation.JsonAlias;

public class User {
    @JsonAlias({"username", "user_name", "userName"})
    private String name;
 
    // getters and setters
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
}