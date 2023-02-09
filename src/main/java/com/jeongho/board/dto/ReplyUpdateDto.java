package com.jeongho.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReplyUpdateDto {

    private Long no;

    private String content;

    public ReplyUpdateDto(Long no, String content) {
        this.no = no;
        this.content = content;
    }
}
