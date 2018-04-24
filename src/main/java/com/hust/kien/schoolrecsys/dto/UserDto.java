package com.hust.kien.schoolrecsys.dto;

public class UserDto {
    private Integer id;
    private String username;
    private String password;

    public UserDto(Integer id) {
        this.id = id;
    }

    public UserDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
