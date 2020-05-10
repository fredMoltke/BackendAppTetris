package com.example.backendapptetris.REST;

public class Post {

    private String studienr;
    private String password;

    public Post(String studienr, String password) {
        this.studienr = studienr;
        this.password = password;
    }

    public String getStudienr() {
        return studienr;
    }

    public String getPassword() {
        return password;
    }
}
