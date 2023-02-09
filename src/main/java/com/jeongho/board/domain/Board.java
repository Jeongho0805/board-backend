package com.jeongho.board.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class Board {

    private Long no;

    @NotEmpty(message = "게시글 제목은 필수 입력 값입니다.")
    private String title;
    @NotEmpty(message = "게시글 본문은 필수 입력 값입니다.")
    private String content;

    private Long userNo;

    public Board(String title, String content, Long userNo) {
        this.title = title;
        this.content = content;
        this.userNo = userNo;
    }

    public Board(Long no, String title, String content, Long userNo) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.userNo = userNo;
    }
}
