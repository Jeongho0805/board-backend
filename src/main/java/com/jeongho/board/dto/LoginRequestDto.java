package com.jeongho.board.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String userId;
    private String password;
}
