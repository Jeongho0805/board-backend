package com.jeongho.board.controller;

import com.jeongho.board.domain.Board;
import com.jeongho.board.domain.User;
import com.jeongho.board.dto.BoardDto;
import com.jeongho.board.dto.BoardUpdateDto;
import com.jeongho.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Object> getBoard(int offset, int limit, String searchQuery) {
        log.info(searchQuery);

        List<BoardDto> boards = boardService.findAll(offset, limit, searchQuery);
        long count = boardService.getTotalCount(searchQuery);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("boards", boards);
        map.put("count", count);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<Object> getBoardByNo(@PathVariable Long boardNo) {
        Board board = boardService.findByNo(boardNo);
        return ResponseEntity.ok().body(board);
    }

    @PostMapping
    public ResponseEntity<Object> saveBoard(@AuthenticationPrincipal User user, @Valid @RequestBody Board board, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
        }
        board.setUserNo(user.getNo());
        boardService.save(board);
        return ResponseEntity.ok().body("게시글 등록이 완료되었습니다.");
    }

    @PutMapping
    public ResponseEntity<Object> updateBoard(@AuthenticationPrincipal User user, @Valid @RequestBody BoardUpdateDto boardUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
        }
        boardService.update(boardUpdateDto, user);
        return ResponseEntity.ok().body("게시글 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{boardNo}")
    public ResponseEntity<Object> deleteBoard(@AuthenticationPrincipal User user, @PathVariable Long boardNo) {
        boardService.delete(boardNo, user);
        return ResponseEntity.ok().body("게시글 삭제가 완료되었습니다.");
    }
}
