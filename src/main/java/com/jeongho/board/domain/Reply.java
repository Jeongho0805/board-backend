package com.jeongho.board.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Reply {

    private Long no;

    private String content;

    private String writer;

    private Long boardNo;


    public Reply(String content, String writer, Long boardNo) {
        this.content = content;
        this.writer = writer;
        this.boardNo = boardNo;
    }

    public Reply(Long no, String content, String writer, Long boardNo) {
        this.no = no;
        this.content = content;
        this.writer = writer;
        this.boardNo = boardNo;
    }
}
