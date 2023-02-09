package com.jeongho.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
public class BoardDto {

    private Long no;

    private String title;

    private String content;

    private String userName;

    public BoardDto(Long no, String title, String content, String userName) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.userName = userName;
    }
}
