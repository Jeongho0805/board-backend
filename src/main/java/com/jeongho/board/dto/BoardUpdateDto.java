package com.jeongho.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class BoardUpdateDto {

    @NotNull(message = "게시글 번호는 필수 입력 값입니다.")
    Long no;
    String title;
    String content;

    public BoardUpdateDto(Long no, String title, String content) {
        this.no = no;
        this.title = title;
        this.content = content;
    }
}
