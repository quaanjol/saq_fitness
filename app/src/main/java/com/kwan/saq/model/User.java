package com.kwan.saq.model;

public class User {
    private String name;
    private String email;
    private String updated_at;
    private String created_at;
    private int id;

    public User(String name, String email, String updated_at, String created_at, int id) {
        this.name = name;
        this.email = email;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
