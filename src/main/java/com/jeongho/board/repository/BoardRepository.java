package com.jeongho.board.repository;

import com.jeongho.board.domain.Board;
import com.jeongho.board.dto.BoardUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final BoardMapper boardMapper;

    public Board save(Board board) {
        boardMapper.save(board);
        return board;
    }

    public int update(BoardUpdateDto boardUpdateDto) {
        return boardMapper.update(boardUpdateDto);
    }

    public int delete(Long boardNo) {
        return boardMapper.delete(boardNo);
    }

    public Board findByNo(Long boardNo) {
        return boardMapper.findByNo(boardNo);
    }

    public List<Board> findAll(int offset, int limit, String searchQuery) {
        return boardMapper.findAll(offset, limit, searchQuery);
    }

    public long getTotalCount(String searchQuery) {
        return boardMapper.getTotalCount(searchQuery);
    }
}
