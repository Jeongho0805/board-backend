package com.jeongho.board.dto;

import lombok.Data;

@Data
public class UserUpdateDto {

    private String name;

    private String id;

    private String password;

    public UserUpdateDto() {
    }

    public UserUpdateDto(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }
}
