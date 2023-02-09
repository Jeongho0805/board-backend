package com.jeongho.board.controller;

import com.jeongho.board.domain.Reply;
import com.jeongho.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/replys")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping
    public ResponseEntity<Object> getReplys() {
        List<Reply> replys = replyService.findAll();
        return ResponseEntity.ok().body(replys);
    }

    @GetMapping("/{replyNo}")
    public ResponseEntity<Object> getReplyByNo(@PathVariable Long replyNo) {
        Reply reply = replyService.findByNo(replyNo);
        return ResponseEntity.ok().body(reply);
    }

//    @GetMapping("/{boardNo}")
//    public ResponseEntity<Object> getReplysByBoardNo(@Path)


}
